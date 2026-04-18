package model.clientes;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import model.cartelera.Funcion;
import model.infraestructura.Asiento;

/**
 * Clase que representa una reserva de asientos para una funcion de cine.
 * Gestiona toda la informacion relacionada con la reserva: codigo de confirmacion,
 * fechas, asientos reservados, calculo de totales, descuentos, estado de la reserva,
 * metodo de pago y generacion de QR.
 * 
 * @author ISC Israel de Jesus Mar Parada
 * @version 1.0
 * @see Cliente
 * @see Funcion
 * @see Asiento
 */
public class Reserva {

    // ==================== ATRIBUTOS ====================
    
    /** Identificador unico de la reserva. */
    private String idReserva;
    
    /** Codigo de confirmacion de la reserva (formato: CINE-XXXXXXXX). */
    private String codigoConfirmacion;
    
    /** Fecha y hora en que se realizo la reserva. */
    private LocalDateTime fechaReserva;
    
    /** Fecha limite para realizar el pago (15 minutos despues de la reserva). */
    private LocalDateTime fechaLimitePago;
    
    /** Identificador de la funcion reservada. */
    private String idFuncion;
    
    /** Lista de identificadores de asientos reservados. */
    private List<String> listaAsientosReservados;
    
    /** Cantidad de asientos reservados. */
    private int cantidadAsientos;
    
    /** Tipo de cliente (REGULAR, VIP). */
    private String tipoCliente;
    
    /** Subtotal de la reserva sin descuentos. */
    private BigDecimal subtotalReserva;
    
    /** Descuento aplicado a la reserva. */
    private BigDecimal descuentoAplicado;
    
    /** Total de la reserva despues de aplicar descuento. */
    private BigDecimal totalReserva;
    
    /** Estado de la reserva (PENDIENTE, CONFIRMADA, CANCELADA). */
    private String estadoReserva;
    
    /** Metodo de pago utilizado (Tarjeta, Efectivo, etc.). */
    private String metodoPago;
    
    /** Fecha y hora en que se realizo el pago. */
    private LocalDateTime fechaPago;
    
    /** Correo electronico para enviar confirmacion. */
    private String emailConfirmacion;
    
    /** Telefono de contacto del cliente. */
    private String telefonoContacto;
    
    /** Codigo QR generado para la reserva. */
    private String qrReserva;
    
    /** Usuario que registro la reserva. */
    private String usuarioRegistro;

    /** Referencia a la funcion reservada. */
    private Funcion funcion;
    
    /** Referencia al cliente que hizo la reserva. */
    private Cliente cliente;
    
    /** Lista de asientos reservados. */
    private List<Asiento> asientos;

    // ==================== CONSTRUCTOR ====================
    
    /**
     * Constructor para crear una nueva reserva.
     * Inicializa la reserva con fecha actual, fecha limite de pago en 15 minutos,
     * estado PENDIENTE y genera un codigo de confirmacion unico.
     * 
     * @param idReserva Identificador unico de la reserva
     * @param idFuncion Identificador de la funcion reservada
     */
    public Reserva(String idReserva, String idFuncion) {
        this.idReserva = idReserva;
        this.idFuncion = idFuncion;
        this.fechaReserva = LocalDateTime.now();
        this.fechaLimitePago = fechaReserva.plusMinutes(15);
        this.estadoReserva = "PENDIENTE";
        this.codigoConfirmacion = generarCodigo();
    }

    // ==================== METODOS PRIVADOS ====================
    
    /**
     * Genera un codigo de confirmacion unico para la reserva.
     * Formato: "CINE-" seguido de 8 caracteres alfanumericos aleatorios.
     * 
     * @return Codigo de confirmacion
     */
    private String generarCodigo() {
        return "CINE-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    // ==================== METODOS DE CALCULO ====================
    
    /**
     * Calcula el total de la reserva.
     * Suma el precio final de cada asiento, aplica ajuste por funcion de estreno (+20%)
     * y resta el descuento aplicado.
     */
    public void CalcularTotal() {
        this.subtotalReserva = BigDecimal.ZERO;
        
        if (asientos != null) {
            for (Asiento asiento : asientos) {
                this.subtotalReserva = this.subtotalReserva.add(asiento.calcularPrecioFinal());
            }
        }
        
        if (funcion != null && funcion.getPrecioBaseFuncion() != null) {
            if (funcion.isEsFuncionEstreno()) {
                this.subtotalReserva = this.subtotalReserva.multiply(new BigDecimal("1.2"));
            }
        }
        
        if (descuentoAplicado == null) {
            this.descuentoAplicado = BigDecimal.ZERO;
        }
        
        this.totalReserva = this.subtotalReserva.subtract(this.descuentoAplicado);
    }

    // ==================== METODOS DE GESTION ====================
    
    /**
     * Genera un codigo QR simulado para la reserva.
     * Formato: "QR-" + idReserva + "-" + codigoConfirmacion
     */
    public void GenerarQR() {
        this.qrReserva = "QR-" + idReserva + "-" + codigoConfirmacion;
    }

    /**
     * Simula el envio de confirmacion de reserva por correo electronico.
     * Muestra en consola la informacion de confirmacion.
     */
    public void EnviarConfirmacion() {
        System.out.println("Enviando confirmacion a: " + emailConfirmacion);
        System.out.println("Codigo: " + codigoConfirmacion);
        System.out.println("Total: $" + totalReserva);
    }

    /**
     * Cancela la reserva.
     * Cambia el estado a CANCELADA y libera todos los asientos reservados.
     */
    public void CancelarReserva() {
        this.estadoReserva = "CANCELADA";
        if (asientos != null) {
            for (Asiento asiento : asientos) {
                asiento.liberar();
            }
        }
    }

    /**
     * Confirma el pago de la reserva.
     * Registra el metodo de pago, fecha de pago, cambia estado a CONFIRMADA,
     * genera el QR y envia la confirmacion.
     * 
     * @param metodo Metodo de pago utilizado (Tarjeta, Efectivo, etc.)
     */
    public void confirmarPago(String metodo) {
        this.metodoPago = metodo;
        this.fechaPago = LocalDateTime.now();
        this.estadoReserva = "CONFIRMADA";
        GenerarQR();
        EnviarConfirmacion();
    }

    // ==================== GETTERS Y SETTERS ====================
    
    /** @return Identificador unico de la reserva */
    public String getIdReserva() { return idReserva; }
    
    /** @param idReserva Nuevo identificador de reserva */
    public void setIdReserva(String idReserva) { this.idReserva = idReserva; }

    /** @return Codigo de confirmacion */
    public String getCodigoConfirmacion() { return codigoConfirmacion; }
    
    /** @param codigoConfirmacion Nuevo codigo de confirmacion */
    public void setCodigoConfirmacion(String codigoConfirmacion) { this.codigoConfirmacion = codigoConfirmacion; }

    /** @return Fecha de la reserva */
    public LocalDateTime getFechaReserva() { return fechaReserva; }
    
    /** @param fechaReserva Nueva fecha de reserva */
    public void setFechaReserva(LocalDateTime fechaReserva) { this.fechaReserva = fechaReserva; }

    /** @return Fecha limite de pago */
    public LocalDateTime getFechaLimitePago() { return fechaLimitePago; }
    
    /** @param fechaLimitePago Nueva fecha limite de pago */
    public void setFechaLimitePago(LocalDateTime fechaLimitePago) { this.fechaLimitePago = fechaLimitePago; }

    /** @return Identificador de la funcion */
    public String getIdFuncion() { return idFuncion; }
    
    /** @param idFuncion Nuevo identificador de funcion */
    public void setIdFuncion(String idFuncion) { this.idFuncion = idFuncion; }

    /** @return Lista de identificadores de asientos reservados */
    public List<String> getListaAsientosReservados() { return listaAsientosReservados; }
    
    /**
     * Establece la lista de asientos reservados.
     * Actualiza automaticamente la cantidad de asientos.
     * 
     * @param listaAsientosReservados Nueva lista de asientos
     */
    public void setListaAsientosReservados(List<String> listaAsientosReservados) { 
        this.listaAsientosReservados = listaAsientosReservados;
        this.cantidadAsientos = listaAsientosReservados.size();
    }

    /** @return Cantidad de asientos reservados */
    public int getCantidadAsientos() { return cantidadAsientos; }
    
    /** @param cantidadAsientos Nueva cantidad de asientos */
    public void setCantidadAsientos(int cantidadAsientos) { this.cantidadAsientos = cantidadAsientos; }

    /** @return Tipo de cliente */
    public String getTipoCliente() { return tipoCliente; }
    
    /** @param tipoCliente Nuevo tipo de cliente */
    public void setTipoCliente(String tipoCliente) { this.tipoCliente = tipoCliente; }

    /** @return Subtotal de la reserva */
    public BigDecimal getSubtotalReserva() { return subtotalReserva; }
    
    /** @param subtotalReserva Nuevo subtotal */
    public void setSubtotalReserva(BigDecimal subtotalReserva) { this.subtotalReserva = subtotalReserva; }

    /** @return Descuento aplicado */
    public BigDecimal getDescuentoAplicado() { return descuentoAplicado; }
    
    /** @param descuentoAplicado Nuevo descuento */
    public void setDescuentoAplicado(BigDecimal descuentoAplicado) { this.descuentoAplicado = descuentoAplicado; }

    /** @return Total de la reserva */
    public BigDecimal getTotalReserva() { return totalReserva; }
    
    /** @param totalReserva Nuevo total */
    public void setTotalReserva(BigDecimal totalReserva) { this.totalReserva = totalReserva; }

    /** @return Estado de la reserva (PENDIENTE, CONFIRMADA, CANCELADA) */
    public String getEstadoReserva() { return estadoReserva; }
    
    /** @param estadoReserva Nuevo estado */
    public void setEstadoReserva(String estadoReserva) { this.estadoReserva = estadoReserva; }

    /** @return Metodo de pago */
    public String getMetodoPago() { return metodoPago; }
    
    /** @param metodoPago Nuevo metodo de pago */
    public void setMetodoPago(String metodoPago) { this.metodoPago = metodoPago; }

    /** @return Fecha de pago */
    public LocalDateTime getFechaPago() { return fechaPago; }
    
    /** @param fechaPago Nueva fecha de pago */
    public void setFechaPago(LocalDateTime fechaPago) { this.fechaPago = fechaPago; }

    /** @return Correo de confirmacion */
    public String getEmailConfirmacion() { return emailConfirmacion; }
    
    /** @param emailConfirmacion Nuevo correo de confirmacion */
    public void setEmailConfirmacion(String emailConfirmacion) { this.emailConfirmacion = emailConfirmacion; }

    /** @return Telefono de contacto */
    public String getTelefonoContacto() { return telefonoContacto; }
    
    /** @param telefonoContacto Nuevo telefono de contacto */
    public void setTelefonoContacto(String telefonoContacto) { this.telefonoContacto = telefonoContacto; }

    /** @return Codigo QR de la reserva */
    public String getQrReserva() { return qrReserva; }
    
    /** @param qrReserva Nuevo codigo QR */
    public void setQrReserva(String qrReserva) { this.qrReserva = qrReserva; }

    /** @return Usuario que registro la reserva */
    public String getUsuarioRegistro() { return usuarioRegistro; }
    
    /** @param usuarioRegistro Nuevo usuario de registro */
    public void setUsuarioRegistro(String usuarioRegistro) { this.usuarioRegistro = usuarioRegistro; }

    /** @return Referencia a la funcion */
    public Funcion getFuncion() { return funcion; }
    
    /**
     * Establece la funcion de la reserva.
     * Actualiza automaticamente el idFuncion.
     * 
     * @param funcion Funcion a asignar
     */
    public void setFuncion(Funcion funcion) { 
        this.funcion = funcion;
        if (funcion != null) {
            this.idFuncion = funcion.getIdFuncion();
        }
    }

    /** @return Referencia al cliente */
    public Cliente getCliente() { return cliente; }
    
    /** @param cliente Cliente a asignar */
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    /** @return Lista de asientos reservados */
    public List<Asiento> getAsientos() { return asientos; }
    
    /**
     * Establece la lista de asientos reservados.
     * Recalcula automaticamente el total de la reserva.
     * 
     * @param asientos Nueva lista de asientos
     */
    public void setAsientos(List<Asiento> asientos) { 
        this.asientos = asientos;
        CalcularTotal();
    }

    // ==================== SOBREESCRITURAS ====================
    
    /**
     * Devuelve una representacion textual de la reserva.
     * Formato: "Reserva codigo - estado - $total"
     * 
     * @return Cadena con la informacion principal de la reserva
     */
    @Override
    public String toString() {
        return "Reserva " + codigoConfirmacion + " - " + estadoReserva + " - $" + totalReserva;
    }
    
}//fin de la clase