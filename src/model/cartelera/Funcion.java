package model.cartelera;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import model.infraestructura.Sala;

/**
 * Clase que representa una funcion de cine, que es la proyeccion de una pelicula
 * en una sala a una fecha y hora especifica.
 * Gestiona la disponibilidad de asientos, ocupacion, calculo de hora de finalizacion
 * y el estado de la funcion.
 * 
 * @author ISC Israel de Jesus Mar Parada
 * @version 1.0
 * @see Pelicula
 * @see Sala
 */
public class Funcion {

    // ==================== ATRIBUTOS ====================
    
    /** Identificador unico de la funcion. */
    private String idFuncion;
    
    /** Fecha de la funcion (dia, mes, año). */
    private LocalDateTime fechaFuncion;
    
    /** Hora de inicio de la funcion. */
    private LocalTime horaInicio;
    
    /** Hora de finalizacion de la funcion (calculada con duracion de la pelicula). */
    private LocalTime horaFin;
    
    /** Precio base de la funcion sin extras. */
    private BigDecimal precioBaseFuncion;
    
    /** Identificador de la pelicula proyectada. */
    private String idPelicula;
    
    /** Identificador de la sala donde se realiza la funcion. */
    private String idSala;
    
    /** Estado de la funcion (DISPONIBLE, COMPLETA, CANCELADA). */
    private String estadoFuncion;
    
    /** Cantidad de asientos disponibles para la funcion. */
    private int asientosDisponibles;
    
    /** Cantidad de asientos ocupados en la funcion. */
    private int asientosOcupados;
    
    /** Porcentaje de ocupacion de la sala. */
    private BigDecimal porcentajeOcupacion;
    
    /** Indica si es una funcion de estreno. */
    private boolean esFuncionEstreno;
    
    /** Indica si esta disponible la preventa. */
    private boolean esFuncionPreventa;
    
    /** Tipo de idioma de la funcion (Español, Subtitulada, Doblada). */
    private String tipoIdiomaFuncion;

    /** Referencia al objeto Pelicula para navegacion facil. */
    private Pelicula pelicula;
    
    /** Referencia al objeto Sala para navegacion facil. */
    private Sala sala;

    // ==================== CONSTRUCTOR ====================
    
    /**
     * Constructor para crear una nueva funcion.
     * Inicializa la funcion con estado DISPONIBLE, asientos disponibles y ocupados en cero.
     * 
     * @param idFuncion Identificador unico de la funcion
     * @param fechaFuncion Fecha de la funcion
     * @param horaInicio Hora de inicio de la funcion
     */
    public Funcion(String idFuncion, LocalDateTime fechaFuncion, LocalTime horaInicio) {
        this.idFuncion = idFuncion;
        this.fechaFuncion = fechaFuncion;
        this.horaInicio = horaInicio;
        this.estadoFuncion = "DISPONIBLE";
        this.asientosDisponibles = 0;
        this.asientosOcupados = 0;
        this.porcentajeOcupacion = BigDecimal.ZERO;
    }

    // ==================== METODOS DE CALCULO ====================
    
    /**
     * Calcula la hora de finalizacion de la funcion.
     * Suma la duracion de la pelicula (en minutos) a la hora de inicio.
     * Requiere que la pelicula y la hora de inicio esten definidas.
     */
    public void CalcularHoraFin() {
        if (pelicula != null && horaInicio != null) {
            this.horaFin = horaInicio.plusMinutes(pelicula.getDuracionMinutos());
        }
    }

    /**
     * Actualiza la disponibilidad de asientos de la funcion.
     * Recalcula asientos disponibles, ocupados y el porcentaje de ocupacion
     * basado en el estado actual de la sala.
     */
    public void ActualizarDisponibilidad() {
        if (sala != null) {
            this.asientosDisponibles = sala.ObtenerAsientosDisponibles().size();
            this.asientosOcupados = sala.getCapacidadTotal() - asientosDisponibles;
            
            if (sala.getCapacidadTotal() > 0) {
                this.porcentajeOcupacion = new BigDecimal(asientosOcupados)
                        .divide(new BigDecimal(sala.getCapacidadTotal()), 2, java.math.RoundingMode.HALF_UP)
                        .multiply(new BigDecimal(100));
            }
        }
    }

    // ==================== METODOS DE VALIDACION Y OPERACION ====================
    
    /**
     * Valida si hay suficientes asientos disponibles para una reserva.
     * 
     * @param cantidad Numero de asientos que se desea reservar
     * @return true si hay suficientes asientos disponibles
     */
    public boolean ValidarDisponibilidad(int cantidad) {
        return asientosDisponibles >= cantidad;
    }

    /**
     * Marca una cantidad de asientos como ocupados.
     * Incrementa asientos ocupados y disminuye asientos disponibles.
     * 
     * @param cantidad Numero de asientos a ocupar
     */
    public void ocuparAsientos(int cantidad) {
        this.asientosOcupados += cantidad;
        this.asientosDisponibles -= cantidad;
        ActualizarDisponibilidad();
    }

    // ==================== GETTERS Y SETTERS ====================
    
    /** @return Identificador unico de la funcion */
    public String getIdFuncion() { return idFuncion; }
    
    /** @param idFuncion Nuevo identificador de funcion */
    public void setIdFuncion(String idFuncion) { this.idFuncion = idFuncion; }

    /** @return Fecha de la funcion */
    public LocalDateTime getFechaFuncion() { return fechaFuncion; }
    
    /** @param fechaFuncion Nueva fecha de funcion */
    public void setFechaFuncion(LocalDateTime fechaFuncion) { this.fechaFuncion = fechaFuncion; }

    /** @return Hora de inicio */
    public LocalTime getHoraInicio() { return horaInicio; }
    
    /** @param horaInicio Nueva hora de inicio */
    public void setHoraInicio(LocalTime horaInicio) { this.horaInicio = horaInicio; }

    /** @return Hora de finalizacion */
    public LocalTime getHoraFin() { return horaFin; }
    
    /** @param horaFin Nueva hora de finalizacion */
    public void setHoraFin(LocalTime horaFin) { this.horaFin = horaFin; }

    /** @return Precio base de la funcion */
    public BigDecimal getPrecioBaseFuncion() { return precioBaseFuncion; }
    
    /** @param precioBaseFuncion Nuevo precio base */
    public void setPrecioBaseFuncion(BigDecimal precioBaseFuncion) { this.precioBaseFuncion = precioBaseFuncion; }

    /** @return Identificador de la pelicula */
    public String getIdPelicula() { return idPelicula; }
    
    /** @param idPelicula Nuevo identificador de pelicula */
    public void setIdPelicula(String idPelicula) { this.idPelicula = idPelicula; }

    /** @return Identificador de la sala */
    public String getIdSala() { return idSala; }
    
    /** @param idSala Nuevo identificador de sala */
    public void setIdSala(String idSala) { this.idSala = idSala; }

    /** @return Estado de la funcion */
    public String getEstadoFuncion() { return estadoFuncion; }
    
    /** @param estadoFuncion Nuevo estado */
    public void setEstadoFuncion(String estadoFuncion) { this.estadoFuncion = estadoFuncion; }

    /** @return Asientos disponibles */
    public int getAsientosDisponibles() { return asientosDisponibles; }
    
    /** @param asientosDisponibles Nueva cantidad de asientos disponibles */
    public void setAsientosDisponibles(int asientosDisponibles) { this.asientosDisponibles = asientosDisponibles; }

    /** @return Asientos ocupados */
    public int getAsientosOcupados() { return asientosOcupados; }
    
    /** @param asientosOcupados Nueva cantidad de asientos ocupados */
    public void setAsientosOcupados(int asientosOcupados) { this.asientosOcupados = asientosOcupados; }

    /** @return Porcentaje de ocupacion */
    public BigDecimal getPorcentajeOcupacion() { return porcentajeOcupacion; }
    
    /** @param porcentajeOcupacion Nuevo porcentaje de ocupacion */
    public void setPorcentajeOcupacion(BigDecimal porcentajeOcupacion) { this.porcentajeOcupacion = porcentajeOcupacion; }

    /** @return true si es funcion de estreno */
    public boolean isEsFuncionEstreno() { return esFuncionEstreno; }
    
    /** @param esFuncionEstreno Nuevo estado de estreno */
    public void setEsFuncionEstreno(boolean esFuncionEstreno) { this.esFuncionEstreno = esFuncionEstreno; }

    /** @return true si tiene preventa disponible */
    public boolean isEsFuncionPreventa() { return esFuncionPreventa; }
    
    /** @param esFuncionPreventa Nuevo estado de preventa */
    public void setEsFuncionPreventa(boolean esFuncionPreventa) { this.esFuncionPreventa = esFuncionPreventa; }

    /** @return Tipo de idioma de la funcion */
    public String getTipoIdiomaFuncion() { return tipoIdiomaFuncion; }
    
    /** @param tipoIdiomaFuncion Nuevo tipo de idioma */
    public void setTipoIdiomaFuncion(String tipoIdiomaFuncion) { this.tipoIdiomaFuncion = tipoIdiomaFuncion; }

    /** @return Referencia a la pelicula */
    public Pelicula getPelicula() { return pelicula; }
    
    /**
     * Establece la pelicula de la funcion.
     * Actualiza automaticamente el idPelicula y recalcula la hora de fin.
     * 
     * @param pelicula Pelicula a asignar
     */
    public void setPelicula(Pelicula pelicula) { 
        this.pelicula = pelicula;
        if (pelicula != null) {
            this.idPelicula = pelicula.getIdPelicula();
            CalcularHoraFin();
        }
    }

    /** @return Referencia a la sala */
    public Sala getSala() { return sala; }
    
    /**
     * Establece la sala de la funcion.
     * Actualiza automaticamente el idSala y los asientos disponibles.
     * 
     * @param sala Sala a asignar
     */
    public void setSala(Sala sala) { 
        this.sala = sala;
        if (sala != null) {
            this.idSala = sala.getIdSala();
            this.asientosDisponibles = sala.getCapacidadTotal();
        }
    }

    // ==================== SOBREESCRITURAS ====================
    
    /**
     * Devuelve una representacion textual de la funcion.
     * Formato: "fecha horaInicio - tituloPelicula | Disp: asientosDisponibles"
     * 
     * @return Cadena con la informacion principal de la funcion
     */
    @Override
    public String toString() {
        String peliculaTitulo = (pelicula != null) ? pelicula.getTitulo() : "Sin pelicula";
        return fechaFuncion.toLocalDate() + " " + horaInicio + " - " + peliculaTitulo + 
               " | Disp: " + asientosDisponibles;
    }
    
}//fin de la clase