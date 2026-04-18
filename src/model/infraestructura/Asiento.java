package model.infraestructura;

import java.math.BigDecimal;

/**
 * Clase abstracta que representa un asiento en una sala de cine.
 * Contiene los atributos y comportamientos comunes a todos los tipos de asientos
 * (Normal, VIP, Premium). Gestiona el estado del asiento (disponible, ocupado, reservado)
 * y sus caracteristicas fisicas como fila, numero, coordenadas, reposabrazos y espacio para piernas.
 * 
 * @author ISC Israel de Jesus Mar Parada
 * @version 1.0
 * @see AsientoNormal
 * @see AsientoVIP
 * @see AsientoPremium
 */
public abstract class Asiento {

    // ==================== ATRIBUTOS ====================
    
    /** Identificador unico del asiento. */
    protected String idAsiento;
    
    /** Letra de la fila del asiento (A, B, C, ...). */
    protected String fila;
    
    /** Numero del asiento dentro de la fila. */
    protected int numero;
    
    /** Coordenada X en el mapa de la sala. */
    protected int coordenadaX;
    
    /** Coordenada Y en el mapa de la sala. */
    protected int coordenadaY;
    
    /** Estado actual del asiento (DISPONIBLE, OCUPADO, RESERVADO). */
    protected String estadoAsiento;
    
    /** Precio base del asiento sin extras. */
    protected BigDecimal precioBase;
    
    /** Tipo de asiento (NORMAL, VIP, PREMIUM). */
    protected String tipoAsiento;
    
    /** Ubicacion de la sala a la que pertenece el asiento. */
    protected String ubicacionSala;
    
    /** Indica si el asiento tiene acceso para discapacitados. */
    protected boolean accesibilidadDiscapacitados;
    
    /** Indica si el asiento tiene reposabrazos. */
    protected boolean tieneReposabrazos;
    
    /** Espacio en centimetros para las piernas. */
    protected int espacioPiernas;

    // ==================== CONSTRUCTOR ====================
    
    /**
     * Constructor para crear un nuevo asiento.
     * Inicializa el asiento como DISPONIBLE, con reposabrazos true y espacio para piernas de 80 cm.
     * 
     * @param idAsiento Identificador unico del asiento
     * @param fila Letra de la fila del asiento
     * @param numero Numero del asiento dentro de la fila
     * @param precioBase Precio base del asiento sin extras
     */
    public Asiento(String idAsiento, String fila, int numero, BigDecimal precioBase) {
        this.idAsiento = idAsiento;
        this.fila = fila;
        this.numero = numero;
        this.precioBase = precioBase;
        this.estadoAsiento = "DISPONIBLE";
        this.tieneReposabrazos = true;
        this.espacioPiernas = 80;
    }

    // ==================== METODOS ABSTRACTOS ====================
    
    /**
     * Calcula el precio final del asiento aplicando los recargos o descuentos
     * segun el tipo de asiento.
     * 
     * @return Precio final del asiento
     */
    public abstract BigDecimal calcularPrecioFinal();

    // ==================== METODOS DE ESTADO ====================
    
    /**
     * Verifica si el asiento esta disponible para ser ocupado o reservado.
     * 
     * @return true si el estado es DISPONIBLE, false en caso contrario
     */
    public boolean estaDisponible() {
        return "DISPONIBLE".equals(estadoAsiento);
    }

    /**
     * Cambia el estado del asiento a OCUPADO.
     * Utilizado cuando un cliente ocupa el asiento en una funcion.
     */
    public void ocupar() {
        this.estadoAsiento = "OCUPADO";
    }

    /**
     * Cambia el estado del asiento a RESERVADO.
     * Utilizado cuando un cliente reserva el asiento temporalmente.
     */
    public void reservar() {
        this.estadoAsiento = "RESERVADO";
    }

    /**
     * Cambia el estado del asiento a DISPONIBLE.
     * Utilizado cuando se cancela una reserva o se libera el asiento.
     */
    public void liberar() {
        this.estadoAsiento = "DISPONIBLE";
    }

    // ==================== GETTERS Y SETTERS ====================
    
    /** @return Identificador unico del asiento */
    public String getIdAsiento() { return idAsiento; }
    
    /** @param idAsiento Nuevo identificador del asiento */
    public void setIdAsiento(String idAsiento) { this.idAsiento = idAsiento; }

    /** @return Letra de la fila del asiento */
    public String getFila() { return fila; }
    
    /** @param fila Nueva letra de fila */
    public void setFila(String fila) { this.fila = fila; }

    /** @return Numero del asiento */
    public int getNumero() { return numero; }
    
    /** @param numero Nuevo numero de asiento */
    public void setNumero(int numero) { this.numero = numero; }

    /** @return Coordenada X del asiento */
    public int getCoordenadaX() { return coordenadaX; }
    
    /** @param coordenadaX Nueva coordenada X */
    public void setCoordenadaX(int coordenadaX) { this.coordenadaX = coordenadaX; }

    /** @return Coordenada Y del asiento */
    public int getCoordenadaY() { return coordenadaY; }
    
    /** @param coordenadaY Nueva coordenada Y */
    public void setCoordenadaY(int coordenadaY) { this.coordenadaY = coordenadaY; }

    /** @return Estado actual del asiento (DISPONIBLE, OCUPADO, RESERVADO) */
    public String getEstadoAsiento() { return estadoAsiento; }
    
    /** @param estadoAsiento Nuevo estado del asiento */
    public void setEstadoAsiento(String estadoAsiento) { this.estadoAsiento = estadoAsiento; }

    /** @return Precio base del asiento */
    public BigDecimal getPrecioBase() { return precioBase; }
    
    /** @param precioBase Nuevo precio base */
    public void setPrecioBase(BigDecimal precioBase) { this.precioBase = precioBase; }

    /** @return Tipo de asiento (NORMAL, VIP, PREMIUM) */
    public String getTipoAsiento() { return tipoAsiento; }
    
    /** @param tipoAsiento Nuevo tipo de asiento */
    public void setTipoAsiento(String tipoAsiento) { this.tipoAsiento = tipoAsiento; }

    /** @return Ubicacion de la sala */
    public String getUbicacionSala() { return ubicacionSala; }
    
    /** @param ubicacionSala Nueva ubicacion de sala */
    public void setUbicacionSala(String ubicacionSala) { this.ubicacionSala = ubicacionSala; }

    /** @return true si tiene accesibilidad para discapacitados */
    public boolean isAccesibilidadDiscapacitados() { return accesibilidadDiscapacitados; }
    
    /** @param accesibilidadDiscapacitados Nueva condicion de accesibilidad */
    public void setAccesibilidadDiscapacitados(boolean accesibilidadDiscapacitados) { this.accesibilidadDiscapacitados = accesibilidadDiscapacitados; }

    /** @return true si tiene reposabrazos */
    public boolean isTieneReposabrazos() { return tieneReposabrazos; }
    
    /** @param tieneReposabrazos Nueva condicion de reposabrazos */
    public void setTieneReposabrazos(boolean tieneReposabrazos) { this.tieneReposabrazos = tieneReposabrazos; }

    /** @return Espacio en centimetros para piernas */
    public int getEspacioPiernas() { return espacioPiernas; }
    
    /** @param espacioPiernas Nuevo espacio para piernas */
    public void setEspacioPiernas(int espacioPiernas) { this.espacioPiernas = espacioPiernas; }

    // ==================== SOBREESCRITURAS ====================
    
    /**
     * Devuelve una representacion textual del asiento.
     * Formato: "fila + numero (tipo) - $precioBase"
     * 
     * @return Cadena con la informacion principal del asiento
     */
    @Override
    public String toString() {
        return fila + numero + " (" + tipoAsiento + ") - $" + precioBase;
    }
}//fin de la clase