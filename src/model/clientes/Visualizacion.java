package model.clientes;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.List;
import model.infraestructura.Asiento;

/**
 * Clase que representa la visualizacion y presentacion de informacion al usuario.
 * Se encarga de dibujar el mapa de asientos con sus respectivos colores,
 * generar tickets de reserva y gestionar elementos visuales como colores,
 * promociones y formatos de ticket.
 * 
 * @author ISC Israel de Jesus Mar Parada
 * @version 1.0
 * @see Asiento
 * @see Reserva
 */
public class Visualizacion {

    // ==================== ATRIBUTOS ====================
    
    /** Representacion visual del mapa de asientos. */
    private Object mapaVisualAsientos;
    
    /** Color para asientos disponibles (VERDE). */
    private String colorAsientoDisponible;
    
    /** Color para asientos ocupados (ROJO). */
    private String colorAsientoOcupado;
    
    /** Color para asientos seleccionados (AZUL). */
    private String colorAsientoSeleccionado;
    
    /** Color para asientos VIP (DORADO). */
    private String colorAsientoVIP;
    
    /** Color para asientos Premium (PURPURA). */
    private String colorAsientoPremium;
    
    /** Tiempo limite en segundos para seleccion de asientos (60). */
    private int tiempoLimiteSeleccion;
    
    /** Lista de mensajes de error para mostrar al usuario. */
    private List<String> mensajesError;
    
    /** Lista de promociones activas. */
    private List<String> promocionesActivas;
    
    /** Formato del ticket (ESTANDAR, DETALLADO). */
    private String formatoTicket;

    // ==================== CONSTRUCTOR ====================
    
    /**
     * Constructor de la visualizacion.
     * Inicializa los colores por defecto: VERDE (disponible), ROJO (ocupado),
     * AZUL (seleccionado), DORADO (VIP), PURPURA (Premium).
     * Tiempo limite de seleccion: 60 segundos.
     * Formato de ticket: ESTANDAR.
     */
    public Visualizacion() {
        this.colorAsientoDisponible = "VERDE";
        this.colorAsientoOcupado = "ROJO";
        this.colorAsientoSeleccionado = "AZUL";
        this.colorAsientoVIP = "DORADO";
        this.colorAsientoPremium = "PURPURA";
        this.tiempoLimiteSeleccion = 60;
        this.formatoTicket = "ESTANDAR";
    }

    // ==================== METODOS DE VISUALIZACION ====================
    
    /**
     * Dibuja el mapa de asientos en consola.
     * Muestra una leyenda de colores por tipo de asiento y el estado de cada asiento
     * con su letra de fila, numero y estado (D=Disponible, O=Ocupado, R=Reservado).
     * 
     * @param asientos Lista de asientos a dibujar en el mapa
     */
    public void DibujarMapa(List<Asiento> asientos) {
        System.out.println("\n=== MAPA DE ASIENTOS ===");
        System.out.println("Disponible: " + colorAsientoDisponible + 
                         " | Ocupado: " + colorAsientoOcupado +
                         " | VIP: " + colorAsientoVIP +
                         " | Premium: " + colorAsientoPremium);
        System.out.println("Tiempo limite de seleccion: " + tiempoLimiteSeleccion + " segundos");
        System.out.println();
        
        String filaActual = "";
        for (Asiento asiento : asientos) {
            if (!asiento.getFila().equals(filaActual)) {
                filaActual = asiento.getFila();
                System.out.println();
                System.out.print("Fila " + filaActual + ": ");
            }
            
            String estado = asiento.getEstadoAsiento().substring(0, 1); // D, O, R
            System.out.print("[" + estado + asiento.getNumero() + "] ");
        }
        System.out.println("\n");
    }

    /**
     * Obtiene el color correspondiente segun el tipo de asiento.
     * 
     * @param tipo Tipo de asiento (VIP, PREMIUM, NORMAL)
     * @return Color asignado al tipo de asiento
     */
    public String obtenerColorPorTipo(String tipo) {
        switch (tipo) {
            case "VIP": return colorAsientoVIP;
            case "PREMIUM": return colorAsientoPremium;
            default: return colorAsientoDisponible;
        }
    }

    // ==================== METODOS DE GENERACION DE TICKET ====================
    
    /**
     * Genera un ticket de reserva en formato texto.
     * Incluye codigo de confirmacion, fecha, informacion de la pelicula,
     * sala, horario, asientos seleccionados, subtotal, descuentos aplicados,
     * total y codigo QR.
     * 
     * @param reserva Reserva para la cual generar el ticket
     * @return String con el ticket formateado
     */
    public String GenerarTicket(Reserva reserva) {
        StringBuilder ticket = new StringBuilder();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        
        ticket.append("\n================================\n");
        ticket.append("CINE COLOMBIA - TICKET\n");
        ticket.append("================================\n");
        ticket.append("Codigo: ").append(reserva.getCodigoConfirmacion()).append("\n");
        ticket.append("Fecha: ").append(reserva.getFechaReserva().format(formatter)).append("\n");
        
        if (reserva.getFuncion() != null && reserva.getFuncion().getPelicula() != null) {
            ticket.append("Pelicula: ").append(reserva.getFuncion().getPelicula().getTitulo()).append("\n");
            ticket.append("Sala: ").append(reserva.getFuncion().getSala().getNombreSala()).append("\n");
            ticket.append("Fecha funcion: ").append(reserva.getFuncion().getFechaFuncion().toLocalDate()).append("\n");
            ticket.append("Hora: ").append(reserva.getFuncion().getHoraInicio()).append("\n");
        }
        
        ticket.append("Asientos: ");
        if (reserva.getAsientos() != null) {
            for (Asiento a : reserva.getAsientos()) {
                ticket.append(a.getFila()).append(a.getNumero()).append(" ");
            }
        }
        ticket.append("\n");
        
        ticket.append("Cantidad: ").append(reserva.getCantidadAsientos()).append("\n");
        ticket.append("Subtotal: $").append(reserva.getSubtotalReserva()).append("\n");
        
        if (reserva.getDescuentoAplicado().compareTo(BigDecimal.ZERO) > 0) {
            ticket.append("Descuento: -$").append(reserva.getDescuentoAplicado()).append("\n");
        }
        
        ticket.append("TOTAL: $").append(reserva.getTotalReserva()).append("\n");
        ticket.append("================================\n");
        ticket.append(" ").append(reserva.getQrReserva()).append(" \n");
        ticket.append("================================\n");
        
        if (promocionesActivas != null && !promocionesActivas.isEmpty()) {
            ticket.append("\nPromociones: ").append(String.join(", ", promocionesActivas)).append("\n");
        }
        
        return ticket.toString();
    }

    // ==================== GETTERS Y SETTERS ====================
    
    /** @return Mapa visual de asientos */
    public Object getMapaVisualAsientos() { return mapaVisualAsientos; }
    
    /** @param mapaVisualAsientos Nuevo mapa visual de asientos */
    public void setMapaVisualAsientos(Object mapaVisualAsientos) { this.mapaVisualAsientos = mapaVisualAsientos; }

    /** @return Color para asientos disponibles */
    public String getColorAsientoDisponible() { return colorAsientoDisponible; }
    
    /** @param colorAsientoDisponible Nuevo color para asientos disponibles */
    public void setColorAsientoDisponible(String colorAsientoDisponible) { this.colorAsientoDisponible = colorAsientoDisponible; }

    /** @return Color para asientos ocupados */
    public String getColorAsientoOcupado() { return colorAsientoOcupado; }
    
    /** @param colorAsientoOcupado Nuevo color para asientos ocupados */
    public void setColorAsientoOcupado(String colorAsientoOcupado) { this.colorAsientoOcupado = colorAsientoOcupado; }

    /** @return Color para asientos seleccionados */
    public String getColorAsientoSeleccionado() { return colorAsientoSeleccionado; }
    
    /** @param colorAsientoSeleccionado Nuevo color para asientos seleccionados */
    public void setColorAsientoSeleccionado(String colorAsientoSeleccionado) { this.colorAsientoSeleccionado = colorAsientoSeleccionado; }

    /** @return Color para asientos VIP */
    public String getColorAsientoVIP() { return colorAsientoVIP; }
    
    /** @param colorAsientoVIP Nuevo color para asientos VIP */
    public void setColorAsientoVIP(String colorAsientoVIP) { this.colorAsientoVIP = colorAsientoVIP; }

    /** @return Color para asientos Premium */
    public String getColorAsientoPremium() { return colorAsientoPremium; }
    
    /** @param colorAsientoPremium Nuevo color para asientos Premium */
    public void setColorAsientoPremium(String colorAsientoPremium) { this.colorAsientoPremium = colorAsientoPremium; }

    /** @return Tiempo limite de seleccion en segundos */
    public int getTiempoLimiteSeleccion() { return tiempoLimiteSeleccion; }
    
    /** @param tiempoLimiteSeleccion Nuevo tiempo limite de seleccion */
    public void setTiempoLimiteSeleccion(int tiempoLimiteSeleccion) { this.tiempoLimiteSeleccion = tiempoLimiteSeleccion; }

    /** @return Lista de mensajes de error */
    public List<String> getMensajesError() { return mensajesError; }
    
    /** @param mensajesError Nueva lista de mensajes de error */
    public void setMensajesError(List<String> mensajesError) { this.mensajesError = mensajesError; }

    /** @return Lista de promociones activas */
    public List<String> getPromocionesActivas() { return promocionesActivas; }
    
    /** @param promocionesActivas Nueva lista de promociones */
    public void setPromocionesActivas(List<String> promocionesActivas) { this.promocionesActivas = promocionesActivas; }

    /** @return Formato del ticket */
    public String getFormatoTicket() { return formatoTicket; }
    
    /** @param formatoTicket Nuevo formato de ticket */
    public void setFormatoTicket(String formatoTicket) { this.formatoTicket = formatoTicket; }
    
}//fin de la clase