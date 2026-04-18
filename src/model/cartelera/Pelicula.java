package model.cartelera;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Clase que representa una pelicula en la cartelera del cine.
 * Contiene informacion detallada sobre la pelicula: titulo, genero, duracion,
 * clasificacion por edad, idioma, formato, director, reparto, sinopsis,
 * fechas de estreno y retiro, y recursos multimedia como imagen y trailer.
 * 
 * @author ISC Israel de Jesus Mar Parada
 * @version 1.0
 * @see Funcion
 */
public class Pelicula {

    // ==================== ATRIBUTOS ====================
    
    /** Identificador unico de la pelicula. */
    private String idPelicula;
    
    /** Titulo de la pelicula. */
    private String titulo;
    
    /** Genero cinematografico (Accion, Comedia, Drama, etc.). */
    private String genero;
    
    /** Duracion en minutos de la pelicula. */
    private int duracionMinutos;
    
    /** Clasificacion por edad (TODO PUBLICO, MAYORES 7, 12, 15, 18). */
    private String clasificacionEdad;
    
    /** Idioma original de la pelicula. */
    private String idioma;
    
    /** Indica si la pelicula tiene subtitulos. */
    private boolean subtitulos;
    
    /** Formato de proyeccion (2D, 3D, IMAX, 4DX). */
    private String formato;
    
    /** Nombre del director de la pelicula. */
    private String director;
    
    /** Lista de actores principales del reparto. */
    private List<String> repartoPrincipal;
    
    /** Resumen de la trama de la pelicula. */
    private String sinopsis;
    
    /** Fecha de estreno de la pelicula. */
    private LocalDateTime fechaEstreno;
    
    /** Fecha de retiro de la cartelera (null si sigue en cartelera). */
    private LocalDateTime fechaRetiro;
    
    /** URL o ruta de la imagen del cartel. */
    private String cartelImagen;
    
    /** URL del trailer de la pelicula. */
    private String trailerUrl;

    // ==================== CONSTRUCTOR ====================
    
    /**
     * Constructor basico para crear una nueva pelicula.
     * Inicializa el idioma como "Español", formato como "2D"
     * y fecha de estreno como la fecha actual.
     * 
     * @param idPelicula Identificador unico de la pelicula
     * @param titulo Titulo de la pelicula
     * @param duracionMinutos Duracion en minutos
     * @param clasificacionEdad Clasificacion por edad
     */
    public Pelicula(String idPelicula, String titulo, int duracionMinutos, String clasificacionEdad) {
        this.idPelicula = idPelicula;
        this.titulo = titulo;
        this.duracionMinutos = duracionMinutos;
        this.clasificacionEdad = clasificacionEdad;
        this.idioma = "Español";
        this.formato = "2D";
        this.fechaEstreno = LocalDateTime.now();
    }

    // ==================== METODOS DE VALIDACION ====================
    
    /**
     * Verifica si la pelicula esta disponible en cartelera en una fecha especifica.
     * Una pelicula esta en cartelera si la fecha es posterior o igual al estreno
     * y anterior o igual a la fecha de retiro (si existe).
     * 
     * @param fecha Fecha a verificar
     * @return true si la pelicula esta en cartelera en esa fecha
     */
    public boolean EstaEnCartelera(LocalDateTime fecha) {
        return !fecha.isBefore(fechaEstreno) && 
               (fechaRetiro == null || !fecha.isAfter(fechaRetiro));
    }

    /**
     * Obtiene la descripcion textual de la clasificacion por edad.
     * 
     * @return Texto descriptivo de la clasificacion
     */
    public String ObtenerClasificacion() {
        switch (clasificacionEdad) {
            case "TODO PUBLICO": return "Apta para todo público";
            case "MAYORES 7": return "Apta para mayores de 7 años";
            case "MAYORES 12": return "Apta para mayores de 12 años";
            case "MAYORES 15": return "Apta para mayores de 15 años";
            case "MAYORES 18": return "Apta para mayores de 18 años";
            default: return "Clasificacion no definida";
        }
    }

    /**
     * Determina si la pelicula es un estreno reciente.
     * Se considera estreno durante la primera semana desde su fecha de lanzamiento.
     * 
     * @return true si la pelicula se estreno en los ultimos 7 dias
     */
    public boolean esEstreno() {
        LocalDateTime hoy = LocalDateTime.now();
        return hoy.isBefore(fechaEstreno.plusDays(7));
    }

    // ==================== GETTERS Y SETTERS ====================
    
    /** @return Identificador unico de la pelicula */
    public String getIdPelicula() { return idPelicula; }
    
    /** @param idPelicula Nuevo identificador de pelicula */
    public void setIdPelicula(String idPelicula) { this.idPelicula = idPelicula; }

    /** @return Titulo de la pelicula */
    public String getTitulo() { return titulo; }
    
    /** @param titulo Nuevo titulo */
    public void setTitulo(String titulo) { this.titulo = titulo; }

    /** @return Genero de la pelicula */
    public String getGenero() { return genero; }
    
    /** @param genero Nuevo genero */
    public void setGenero(String genero) { this.genero = genero; }

    /** @return Duracion en minutos */
    public int getDuracionMinutos() { return duracionMinutos; }
    
    /** @param duracionMinutos Nueva duracion */
    public void setDuracionMinutos(int duracionMinutos) { this.duracionMinutos = duracionMinutos; }

    /** @return Clasificacion por edad */
    public String getClasificacionEdad() { return clasificacionEdad; }
    
    /** @param clasificacionEdad Nueva clasificacion */
    public void setClasificacionEdad(String clasificacionEdad) { this.clasificacionEdad = clasificacionEdad; }

    /** @return Idioma de la pelicula */
    public String getIdioma() { return idioma; }
    
    /** @param idioma Nuevo idioma */
    public void setIdioma(String idioma) { this.idioma = idioma; }

    /** @return true si tiene subtitulos */
    public boolean isSubtitulos() { return subtitulos; }
    
    /** @param subtitulos Nuevo estado de subtitulos */
    public void setSubtitulos(boolean subtitulos) { this.subtitulos = subtitulos; }

    /** @return Formato de proyeccion */
    public String getFormato() { return formato; }
    
    /** @param formato Nuevo formato */
    public void setFormato(String formato) { this.formato = formato; }

    /** @return Director de la pelicula */
    public String getDirector() { return director; }
    
    /** @param director Nuevo director */
    public void setDirector(String director) { this.director = director; }

    /** @return Lista de actores principales */
    public List<String> getRepartoPrincipal() { return repartoPrincipal; }
    
    /** @param repartoPrincipal Nueva lista de reparto */
    public void setRepartoPrincipal(List<String> repartoPrincipal) { this.repartoPrincipal = repartoPrincipal; }

    /** @return Sinopsis de la pelicula */
    public String getSinopsis() { return sinopsis; }
    
    /** @param sinopsis Nueva sinopsis */
    public void setSinopsis(String sinopsis) { this.sinopsis = sinopsis; }

    /** @return Fecha de estreno */
    public LocalDateTime getFechaEstreno() { return fechaEstreno; }
    
    /** @param fechaEstreno Nueva fecha de estreno */
    public void setFechaEstreno(LocalDateTime fechaEstreno) { this.fechaEstreno = fechaEstreno; }

    /** @return Fecha de retiro de cartelera */
    public LocalDateTime getFechaRetiro() { return fechaRetiro; }
    
    /** @param fechaRetiro Nueva fecha de retiro */
    public void setFechaRetiro(LocalDateTime fechaRetiro) { this.fechaRetiro = fechaRetiro; }

    /** @return URL o ruta del cartel */
    public String getCartelImagen() { return cartelImagen; }
    
    /** @param cartelImagen Nueva URL del cartel */
    public void setCartelImagen(String cartelImagen) { this.cartelImagen = cartelImagen; }

    /** @return URL del trailer */
    public String getTrailerUrl() { return trailerUrl; }
    
    /** @param trailerUrl Nueva URL del trailer */
    public void setTrailerUrl(String trailerUrl) { this.trailerUrl = trailerUrl; }

    // ==================== SOBREESCRITURAS ====================
    
    /**
     * Devuelve una representacion textual de la pelicula.
     * Formato: "titulo (formato) - duracion min - clasificacion"
     * 
     * @return Cadena con la informacion principal de la pelicula
     */
    @Override
    public String toString() {
        return titulo + " (" + formato + ") - " + duracionMinutos + "min - " + clasificacionEdad;
    }
    
}//fin de la clase