package service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import model.cartelera.Funcion;
import model.clientes.Cliente;
import model.clientes.Reserva;
import model.clientes.Visualizacion;
import model.infraestructura.Asiento;
import repository.DataStore;

/**
 * Clase de servicio que gestiona el proceso de reserva de asientos.
 * Proporciona funcionalidades para iniciar una reserva, seleccionar asientos,
 * aplicar descuentos por membresia, procesar pago, consultar y cancelar reservas,
 * y mostrar el historial de reservas del cliente actual.
 * 
 * @author ISC Israel de Jesus Mar Parada
 * @version 1.0
 * @see Reserva
 * @see Cliente
 * @see Funcion
 * @see Asiento
 * @see Visualizacion
 * @see DataStore
 */
public class ReservaService {

    // ==================== ATRIBUTOS ====================
    
    /** Almacenamiento de datos del sistema. */
    private DataStore dataStore;
    
    /** Herramienta de visualizacion para mapas y tickets. */
    private Visualizacion visualizacion;
    
    /** Cliente actualmente autenticado en sesion. */
    private Cliente clienteActual;

    // ==================== CONSTRUCTOR ====================
    
    /**
     * Constructor del servicio de reservas.
     * Obtiene la instancia unica del DataStore e inicializa la visualizacion.
     */
    public ReservaService() {
        this.dataStore = DataStore.getInstance();
        this.visualizacion = new Visualizacion();
    }

    // ==================== METODOS DE SESION ====================
    
    /**
     * Establece el cliente actual para la sesion de reservas.
     * 
     * @param cliente Cliente autenticado
     */
    public void setClienteActual(Cliente cliente) {
        this.clienteActual = cliente;
    }

    // ==================== METODO PRINCIPAL DE RESERVA ====================
    
    /**
     * Inicia el proceso de reserva para una funcion especifica.
     * Permite al cliente seleccionar asientos del mapa, aplica descuentos
     * segun su nivel de membresia, calcula el total, procesa el pago
     * y confirma la reserva. Si se cancela, libera los asientos.
     * 
     * @param scanner Scanner para entrada de datos del usuario
     * @param funcion Funcion para la cual se hace la reserva
     */
    public void iniciarReserva(Scanner scanner, Funcion funcion) {
        if (clienteActual == null) {
            System.out.println("Debe iniciar sesion primero");
            return;
        }

        System.out.println("\n INICIAR RESERVA");
        System.out.println("Pelicula: " + funcion.getPelicula().getTitulo());
        System.out.println("Fecha: " + funcion.getFechaFuncion().toLocalDate() + " " + funcion.getHoraInicio());
        System.out.println("Sala: " + funcion.getSala().getNombreSala());
        System.out.println("Precio base por asiento: $" + funcion.getPrecioBaseFuncion());

        visualizacion.DibujarMapa(funcion.getSala().getMapaAsientos());

        List<Asiento> asientosSeleccionados = new ArrayList<>();
        boolean seleccionando = true;

        while (seleccionando) {
            System.out.print("Ingrese ID del asiento (ej: A5) o 'FIN' para terminar: ");
            String idAsiento = scanner.nextLine();

            if (idAsiento.equalsIgnoreCase("FIN")) {
                seleccionando = false;
                continue;
            }

            Asiento asiento = funcion.getSala().buscarAsiento(idAsiento);
            if (asiento == null) {
                System.out.println("Asiento no encontrado");
                continue;
            }

            if (!asiento.estaDisponible()) {
                System.out.println("Asiento no disponible");
                continue;
            }

            asientosSeleccionados.add(asiento);
            System.out.println("Asiento " + idAsiento + " seleccionado. Precio: $" + asiento.calcularPrecioFinal());
        }

        if (asientosSeleccionados.isEmpty()) {
            System.out.println("Reserva cancelada");
            return;
        }

        String idReserva = dataStore.generarIdReserva();
        Reserva reserva = new Reserva(idReserva, funcion.getIdFuncion());
        reserva.setCliente(clienteActual);
        reserva.setFuncion(funcion);
        reserva.setAsientos(asientosSeleccionados);
        
        List<String> idsAsientos = new ArrayList<>();
        for (Asiento a : asientosSeleccionados) {
            idsAsientos.add(a.getIdAsiento());
            a.reservar();
        }
        reserva.setListaAsientosReservados(idsAsientos);
        
        BigDecimal descuento = clienteActual.CalcularDescuento();
        if (descuento.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal montoDescuento = reserva.getSubtotalReserva().multiply(descuento);
            reserva.setDescuentoAplicado(montoDescuento);
            System.out.println("Descuento aplicado: " + (descuento.multiply(new BigDecimal("100"))) + "%");
        }
        
        reserva.CalcularTotal();
        
        System.out.println("\n=== RESUMEN DE RESERVA ===");
        System.out.println(visualizacion.GenerarTicket(reserva));
        
        System.out.print("Confirmar reserva? (S/N): ");
        String confirmar = scanner.nextLine();
        
        if (confirmar.equalsIgnoreCase("S")) {
            System.out.println("Metodos de pago:");
            System.out.println("1. Efectivo");
            System.out.println("2. Tarjeta");
            System.out.println("3. Transferencia");
            System.out.print("Seleccione: ");
            int metodo = scanner.nextInt();
            scanner.nextLine();
            
            String metodoPago = switch(metodo) {
                case 1 -> "EFECTIVO";
                case 2 -> "TARJETA";
                case 3 -> "TRANSFERENCIA";
                default -> "EFECTIVO";
            };
            
            reserva.confirmarPago(metodoPago);
            
            for (Asiento a : asientosSeleccionados) {
                a.ocupar();
            }
            
            funcion.ocuparAsientos(asientosSeleccionados.size());
            funcion.ActualizarDisponibilidad();
            
            dataStore.addReserva(reserva);
            dataStore.updateFuncion(funcion);
            clienteActual.agregarReserva(reserva.getIdReserva());
            clienteActual.AcumularPuntos(reserva.getTotalReserva());
            dataStore.updateCliente(clienteActual);
            
            System.out.println("Reserva confirmada!");
        } else {
            for (Asiento a : asientosSeleccionados) {
                a.liberar();
            }
            System.out.println("Reserva cancelada");
        }
    }

    // ==================== METODOS DE CONSULTA ====================
    
    /**
     * Consulta y muestra los detalles de una reserva especifica.
     * 
     * @param idReserva Identificador de la reserva a consultar
     */
    public void consultarReserva(String idReserva) {
        Reserva r = dataStore.getReserva(idReserva);
        if (r == null) {
            System.out.println("Reserva no encontrada");
            return;
        }
        System.out.println(visualizacion.GenerarTicket(r));
    }

    /**
     * Cancela una reserva existente.
     * Solo permite cancelar si la reserva pertenece al cliente actual.
     * Libera los asientos asociados a la reserva.
     * 
     * @param idReserva Identificador de la reserva a cancelar
     */
    public void cancelarReserva(String idReserva) {
        Reserva r = dataStore.getReserva(idReserva);
        if (r == null) {
            System.out.println("Reserva no encontrada");
            return;
        }
        
        if (r.getCliente() != clienteActual) {
            System.out.println("No puede cancelar una reserva de otro cliente");
            return;
        }
        
        r.CancelarReserva();
        System.out.println("Reserva cancelada exitosamente");
    }

    /**
     * Muestra todas las reservas del cliente actual.
     * Lista cada reserva con codigo de confirmacion, pelicula, estado y total.
     */
    public void mostrarMisReservas() {
        if (clienteActual == null) {
            System.out.println("No hay sesion activa");
            return;
        }

        System.out.println("\n=== MIS RESERVAS ===");
        for (String idReserva : clienteActual.getHistorialReservas()) {
            Reserva r = dataStore.getReserva(idReserva);
            if (r != null) {
                System.out.println(r.getCodigoConfirmacion() + " - " + 
                                 r.getFuncion().getPelicula().getTitulo() + " - " +
                                 r.getEstadoReserva() + " - $" + r.getTotalReserva());
            }
        }
    }
    
}//fin de la clase