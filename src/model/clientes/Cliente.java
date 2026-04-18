package model.clientes;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa un cliente del cine.
 * Contiene informacion personal del cliente, su membresia, puntos acumulados,
 * preferencias y metodos para calcular descuentos, acumular puntos, verificar edad
 * y validar si puede ver peliculas segun su clasificacion.
 * 
 * @author ISC Israel de Jesus Mar Parada
 * @version 1.0
 * @see Reserva
 */
public class Cliente {

    // ==================== ATRIBUTOS ====================
    
    /** Identificador unico del cliente. */
    private String idCliente;
    
    /** Nombre completo del cliente. */
    private String nombreCliente;
    
    /** Documento de identidad (cedula, NIT, pasaporte). */
    private String documentoIdentidad;
    
    /** Correo electronico del cliente. */
    private String emailCliente;
    
    /** Numero de telefono del cliente. */
    private String telefonoCliente;
    
    /** Tipo de cliente (REGULAR, VIP). */
    private String tipoCliente;
    
    /** Fecha de nacimiento del cliente. */
    private LocalDateTime fechaNacimiento;
    
    /** Puntos acumulados por compras (1 punto por cada $1,000 gastados). */
    private int puntosAcumulados;
    
    /** Nivel de membresia (BASICA, PLATA, ORO, PLATINO). */
    private String nivelMembresia;
    
    /** Lista de identificadores de reservas realizadas por el cliente. */
    private List<String> historialReservas;
    
    /** Fecha de registro del cliente en el sistema. */
    private LocalDateTime fechaRegistro;
    
    /** Genero de pelicula preferido por el cliente. */
    private String preferenciaGenero;
    
    /** Idioma preferido para las funciones. */
    private String preferenciaIdioma;
    
    /** Indica si el cliente acepto el tratamiento de datos personales. */
    private boolean consentimientoDatos;
    
    /** Indica si el cliente tiene estatus VIP. */
    private boolean esVIP;

    // ==================== CONSTRUCTOR ====================
    
    /**
     * Constructor para crear un nuevo cliente.
     * Inicializa al cliente como tipo REGULAR, membresia BASICA,
     * puntos en cero, historial vacio, fecha de registro actual
     * y consentimiento de datos activado por defecto.
     * 
     * @param idCliente Identificador unico del cliente
     * @param nombreCliente Nombre completo del cliente
     * @param documentoIdentidad Documento de identidad
     */
    public Cliente(String idCliente, String nombreCliente, String documentoIdentidad) {
        this.idCliente = idCliente;
        this.nombreCliente = nombreCliente;
        this.documentoIdentidad = documentoIdentidad;
        this.tipoCliente = "REGULAR";
        this.puntosAcumulados = 0;
        this.nivelMembresia = "BASICA";
        this.historialReservas = new ArrayList<>();
        this.fechaRegistro = LocalDateTime.now();
        this.esVIP = false;
        this.consentimientoDatos = true;
    }

    // ==================== METODOS DE DESCUENTOS Y PUNTOS ====================
    
    /**
     * Calcula el descuento aplicable al cliente segun su nivel de membresia y puntos acumulados.
     * Descuentos por membresia: PLATA 5%, ORO 10%, PLATINO 15%.
     * Descuento adicional del 2% si tiene mas de 1000 puntos.
     * 
     * @return Porcentaje de descuento como BigDecimal (ej: 0.10 = 10%)
     */
    public BigDecimal CalcularDescuento() {
        BigDecimal descuento = BigDecimal.ZERO;
        
        switch (nivelMembresia) {
            case "PLATA":
                descuento = new BigDecimal("0.05");
                break;
            case "ORO":
                descuento = new BigDecimal("0.10");
                break;
            case "PLATINO":
                descuento = new BigDecimal("0.15");
                break;
        }
        
        if (puntosAcumulados > 1000) {
            descuento = descuento.add(new BigDecimal("0.02"));
        }
        
        return descuento;
    }

    /**
     * Acumula puntos segun el monto gastado.
     * Regla: 1 punto por cada $1,000 gastados.
     * Actualiza automaticamente el nivel de membresia segun los puntos:
     * - PLATINO: mas de 5000 puntos
     * - ORO: mas de 2000 puntos
     * - PLATA: mas de 500 puntos
     * 
     * @param monto Monto gastado para calcular puntos
     */
    public void AcumularPuntos(BigDecimal monto) {
        int puntosGanados = monto.divide(new BigDecimal("1000"), 0, java.math.RoundingMode.DOWN).intValue();
        this.puntosAcumulados += puntosGanados;
        
        if (puntosAcumulados > 5000) {
            this.nivelMembresia = "PLATINO";
        } else if (puntosAcumulados > 2000) {
            this.nivelMembresia = "ORO";
        } else if (puntosAcumulados > 500) {
            this.nivelMembresia = "PLATA";
        }
    }

    // ==================== METODOS DE VALIDACION ====================
    
    /**
     * Verifica si el cliente es mayor de edad (18 años o mas).
     * 
     * @return true si tiene 18 años o mas, false en caso contrario
     */
    public boolean EsMayorEdad() {
        if (fechaNacimiento == null) return true;
        int edad = Period.between(fechaNacimiento.toLocalDate(), LocalDateTime.now().toLocalDate()).getYears();
        return edad >= 18;
    }

    /**
     * Verifica si el cliente puede ver una pelicula segun su clasificacion.
     * 
     * @param clasificacion Clasificacion de la pelicula (TODO PUBLICO, MAYORES 7, 12, 15, 18)
     * @return true si la edad del cliente cumple con la clasificacion
     */
    public boolean puedeVerPelicula(String clasificacion) {
        if (fechaNacimiento == null) return true;
        
        int edad = Period.between(fechaNacimiento.toLocalDate(), LocalDateTime.now().toLocalDate()).getYears();
        
        switch (clasificacion) {
            case "TODO PUBLICO": return true;
            case "MAYORES 7": return edad >= 7;
            case "MAYORES 12": return edad >= 12;
            case "MAYORES 15": return edad >= 15;
            case "MAYORES 18": return edad >= 18;
            default: return true;
        }
    }

    // ==================== METODOS DE GESTION ====================
    
    /**
     * Agrega una reserva al historial del cliente.
     * 
     * @param idReserva Identificador de la reserva
     */
    public void agregarReserva(String idReserva) {
        historialReservas.add(idReserva);
    }

    // ==================== GETTERS Y SETTERS ====================
    
    /** @return Identificador unico del cliente */
    public String getIdCliente() { return idCliente; }
    
    /** @param idCliente Nuevo identificador del cliente */
    public void setIdCliente(String idCliente) { this.idCliente = idCliente; }

    /** @return Nombre completo del cliente */
    public String getNombreCliente() { return nombreCliente; }
    
    /** @param nombreCliente Nuevo nombre del cliente */
    public void setNombreCliente(String nombreCliente) { this.nombreCliente = nombreCliente; }

    /** @return Documento de identidad */
    public String getDocumentoIdentidad() { return documentoIdentidad; }
    
    /** @param documentoIdentidad Nuevo documento de identidad */
    public void setDocumentoIdentidad(String documentoIdentidad) { this.documentoIdentidad = documentoIdentidad; }

    /** @return Correo electronico */
    public String getEmailCliente() { return emailCliente; }
    
    /** @param emailCliente Nuevo correo electronico */
    public void setEmailCliente(String emailCliente) { this.emailCliente = emailCliente; }

    /** @return Telefono de contacto */
    public String getTelefonoCliente() { return telefonoCliente; }
    
    /** @param telefonoCliente Nuevo telefono */
    public void setTelefonoCliente(String telefonoCliente) { this.telefonoCliente = telefonoCliente; }

    /** @return Tipo de cliente (REGULAR, VIP) */
    public String getTipoCliente() { return tipoCliente; }
    
    /** @param tipoCliente Nuevo tipo de cliente */
    public void setTipoCliente(String tipoCliente) { this.tipoCliente = tipoCliente; }

    /** @return Fecha de nacimiento */
    public LocalDateTime getFechaNacimiento() { return fechaNacimiento; }
    
    /** @param fechaNacimiento Nueva fecha de nacimiento */
    public void setFechaNacimiento(LocalDateTime fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    /** @return Puntos acumulados */
    public int getPuntosAcumulados() { return puntosAcumulados; }
    
    /** @param puntosAcumulados Nuevos puntos acumulados */
    public void setPuntosAcumulados(int puntosAcumulados) { this.puntosAcumulados = puntosAcumulados; }

    /** @return Nivel de membresia (BASICA, PLATA, ORO, PLATINO) */
    public String getNivelMembresia() { return nivelMembresia; }
    
    /** @param nivelMembresia Nuevo nivel de membresia */
    public void setNivelMembresia(String nivelMembresia) { this.nivelMembresia = nivelMembresia; }

    /** @return Lista de identificadores de reservas */
    public List<String> getHistorialReservas() { return historialReservas; }
    
    /** @param historialReservas Nueva lista de reservas */
    public void setHistorialReservas(List<String> historialReservas) { this.historialReservas = historialReservas; }

    /** @return Fecha de registro */
    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
    
    /** @param fechaRegistro Nueva fecha de registro */
    public void setFechaRegistro(LocalDateTime fechaRegistro) { this.fechaRegistro = fechaRegistro; }

    /** @return Genero de pelicula preferido */
    public String getPreferenciaGenero() { return preferenciaGenero; }
    
    /** @param preferenciaGenero Nueva preferencia de genero */
    public void setPreferenciaGenero(String preferenciaGenero) { this.preferenciaGenero = preferenciaGenero; }

    /** @return Idioma preferido */
    public String getPreferenciaIdioma() { return preferenciaIdioma; }
    
    /** @param preferenciaIdioma Nueva preferencia de idioma */
    public void setPreferenciaIdioma(String preferenciaIdioma) { this.preferenciaIdioma = preferenciaIdioma; }

    /** @return true si acepto tratamiento de datos */
    public boolean isConsentimientoDatos() { return consentimientoDatos; }
    
    /** @param consentimientoDatos Nuevo estado de consentimiento */
    public void setConsentimientoDatos(boolean consentimientoDatos) { this.consentimientoDatos = consentimientoDatos; }

    /** @return true si es VIP */
    public boolean isEsVIP() { return esVIP; }
    
    /** @param esVIP Nuevo estado VIP */
    public void setEsVIP(boolean esVIP) { this.esVIP = esVIP; }

    // ==================== SOBREESCRITURAS ====================
    
    /**
     * Devuelve una representacion textual del cliente.
     * Formato: "nombre (tipo) - Puntos: puntos"
     * 
     * @return Cadena con la informacion principal del cliente
     */
    @Override
    public String toString() {
        return nombreCliente + " (" + tipoCliente + ") - Puntos: " + puntosAcumulados;
    }
    
}//fin de la clase