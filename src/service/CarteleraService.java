package service;

import java.util.List;
import model.cartelera.Funcion;
import model.cartelera.Pelicula;
import repository.DataStore;

/**
 * Clase de servicio que gestiona la cartelera de cine.
 * Proporciona funcionalidades para mostrar la cartelera completa,
 * listar funciones disponibles, buscar funciones y peliculas,
 * y mostrar detalles especificos de una funcion.
 * 
 * @author ISC Israel de Jesus Mar Parada
 * @version 1.0
 * @see Pelicula
 * @see Funcion
 * @see DataStore
 */
public class CarteleraService {

    // ==================== ATRIBUTOS ====================
    
    /** Almacenamiento de datos del sistema. */
    private DataStore dataStore;

    // ==================== CONSTRUCTOR ====================
    
    /**
     * Constructor del servicio de cartelera.
     * Obtiene la instancia unica del DataStore.
     */
    public CarteleraService() {
        this.dataStore = DataStore.getInstance();
    }

    // ==================== METODOS DE VISUALIZACION ====================
    
    /**
     * Muestra la cartelera completa de cine.
     * Lista todas las peliculas disponibles con su informacion:
     * genero, duracion, clasificacion, formato, idioma,
     * y las funciones disponibles para cada una con horarios,
     * sala, asientos disponibles y precio.
     */
    public void mostrarCartelera() {
        System.out.println("\n=== CARTELERA DE CINE ===");
        
        List<Pelicula> peliculas = dataStore.getAllPeliculas();
        for (Pelicula p : peliculas) {
            System.out.println("\n " + p.getTitulo());
            System.out.println("   Genero: " + p.getGenero());
            System.out.println("   Duracion: " + p.getDuracionMinutos() + " min");
            System.out.println("   Clasificacion: " + p.ObtenerClasificacion());
            System.out.println("   Formato: " + p.getFormato() + " - " + p.getIdioma() + 
                             (p.isSubtitulos() ? " (Subtitulada)" : ""));
            
            List<Funcion> funciones = dataStore.getFuncionesPorPelicula(p.getIdPelicula());
            if (!funciones.isEmpty()) {
                System.out.println("   Horarios:");
                for (Funcion f : funciones) {
                    System.out.println("     -> " + f.getFechaFuncion().toLocalDate() + 
                                     " " + f.getHoraInicio() + 
                                     " - Sala: " + f.getSala().getNombreSala() +
                                     " - Disponibles: " + f.getAsientosDisponibles() +
                                     " - $" + f.getPrecioBaseFuncion());
                }
            }
        }
    }

    /**
     * Muestra todas las funciones que actualmente tienen asientos disponibles.
     * Incluye informacion de pelicula, fecha, hora, sala,
     * cantidad de asientos disponibles y precio.
     */
    public void mostrarFuncionesDisponibles() {
        System.out.println("\n FUNCIONES DISPONIBLES");
        
        List<Funcion> disponibles = dataStore.getFuncionesDisponibles();
        for (Funcion f : disponibles) {
            System.out.println(f.getIdFuncion() + " - " + f.getPelicula().getTitulo() + 
                             " | " + f.getFechaFuncion().toLocalDate() + " " + f.getHoraInicio() +
                             " | Sala: " + f.getSala().getNombreSala() +
                             " | Asientos: " + f.getAsientosDisponibles() + "/" + f.getSala().getCapacidadTotal() +
                             " | $" + f.getPrecioBaseFuncion());
        }
    }

    /**
     * Muestra el detalle completo de una funcion especifica.
     * Incluye pelicula, fecha, horario (inicio y fin), sala,
     * tecnologias de imagen y sonido, disponibilidad de asientos,
     * precio base y estado de la funcion.
     * 
     * @param idFuncion Identificador de la funcion a consultar
     */
    public void mostrarDetalleFuncion(String idFuncion) {
        Funcion f = dataStore.getFuncion(idFuncion);
        if (f == null) {
            System.out.println("Funcion no encontrada");
            return;
        }

        System.out.println("\n=== DETALLE DE FUNCION ===");
        System.out.println("Pelicula: " + f.getPelicula().getTitulo());
        System.out.println("Fecha: " + f.getFechaFuncion().toLocalDate());
        System.out.println("Hora: " + f.getHoraInicio() + " - " + f.getHoraFin());
        System.out.println("Sala: " + f.getSala().getNombreSala());
        System.out.println("Tecnologia: " + f.getSala().getTecnologiaImagen() + 
                         " / " + f.getSala().getTecnologiaSonido());
        System.out.println("Asientos disponibles: " + f.getAsientosDisponibles() + 
                         " de " + f.getSala().getCapacidadTotal());
        System.out.println("Precio base: $" + f.getPrecioBaseFuncion());
        System.out.println("Estado: " + f.getEstadoFuncion());
    }

    // ==================== METODOS DE BUSQUEDA ====================
    
    /**
     * Busca una funcion por su identificador.
     * 
     * @param idFuncion Identificador de la funcion a buscar
     * @return Funcion encontrada o null si no existe
     */
    public Funcion buscarFuncion(String idFuncion) {
        return dataStore.getFuncion(idFuncion);
    }

    /**
     * Busca una pelicula por su identificador.
     * 
     * @param idPelicula Identificador de la pelicula a buscar
     * @return Pelicula encontrada o null si no existe
     */
    public Pelicula buscarPelicula(String idPelicula) {
        return dataStore.getPelicula(idPelicula);
    }
    
}//fin de la clase