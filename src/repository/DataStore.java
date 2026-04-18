package repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.cartelera.Funcion;
import model.cartelera.Pelicula;
import model.clientes.Cliente;
import model.clientes.Reserva;
import model.infraestructura.Sala;

/**
 * Clase singleton que actua como almacenamiento central de datos del sistema de cine.
 * Simula una base de datos en memoria gestionando salas, peliculas, funciones,
 * clientes y reservas. Contiene datos de prueba precargados para demostracion.
 * 
 * @author ISC Israel de Jesus Mar Parada
 * @version 1.0
 * @see Sala
 * @see Pelicula
 * @see Funcion
 * @see Cliente
 * @see Reserva
 */
public class DataStore {

    // ==================== ATRIBUTOS ====================
    
    /** Instancia unica del singleton DataStore. */
    private static DataStore instance;
    
    /** Mapa de salas indexadas por identificador. */
    private Map<String, Sala> salas;
    
    /** Mapa de peliculas indexadas por identificador. */
    private Map<String, Pelicula> peliculas;
    
    /** Mapa de funciones indexadas por identificador. */
    private Map<String, Funcion> funciones;
    
    /** Mapa de clientes indexadas por identificador. */
    private Map<String, Cliente> clientes;
    
    /** Mapa de reservas indexadas por identificador. */
    private Map<String, Reserva> reservas;
    
    /** Contador para generar IDs unicos de reserva. */
    private int contadorReservas;

    // ==================== CONSTRUCTOR PRIVADO ====================
    
    /**
     * Constructor privado para implementar el patron singleton.
     * Inicializa las estructuras de datos y carga los datos de prueba.
     */
    private DataStore() {
        this.salas = new HashMap<>();
        this.peliculas = new HashMap<>();
        this.funciones = new HashMap<>();
        this.clientes = new HashMap<>();
        this.reservas = new HashMap<>();
        this.contadorReservas = 1000;
        
        inicializarDatosPrueba();
    }

    // ==================== METODOS SINGLETON ====================
    
    /**
     * Obtiene la instancia unica del DataStore.
     * Si no existe, la crea por primera vez.
     * 
     * @return Instancia unica del DataStore
     */
    public static DataStore getInstance() {
        if (instance == null) {
            instance = new DataStore();
        }
        return instance;
    }

    // ==================== INICIALIZACION DE DATOS ====================
    
    /**
     * Inicializa datos de prueba para demostracion.
     * Crea:
     * - 3 salas (3D, VIP, Estandar)
     * - 3 peliculas (Avatar 2, Super Mario Bros, Wonka)
     * - 7 funciones distribuidas en diferentes dias y horarios
     * - 3 clientes con diferentes niveles de membresia
     */
    private void inicializarDatosPrueba() {
        // Crear salas
        Sala sala1 = new Sala("S01", "Sala 1 - 3D", 8, 12);
        sala1.GenerarMapaAsientos();
        salas.put(sala1.getIdSala(), sala1);

        Sala sala2 = new Sala("S02", "Sala 2 - VIP", 6, 10);
        sala2.GenerarMapaAsientos();
        salas.put(sala2.getIdSala(), sala2);

        Sala sala3 = new Sala("S03", "Sala 3 - Estandar", 10, 14);
        sala3.GenerarMapaAsientos();
        salas.put(sala3.getIdSala(), sala3);

        // Crear peliculas
        Pelicula peli1 = new Pelicula("P001", "Avatar 2", 192, "MAYORES 12");
        peli1.setGenero("Ciencia Ficcion");
        peli1.setFormato("3D");
        peli1.setIdioma("Ingles");
        peli1.setSubtitulos(true);
        peliculas.put(peli1.getIdPelicula(), peli1);

        Pelicula peli2 = new Pelicula("P002", "Super Mario Bros", 92, "TODO PUBLICO");
        peli2.setGenero("Animacion");
        peli2.setFormato("2D");
        peli2.setIdioma("Espanol");
        peli2.setSubtitulos(false);
        peliculas.put(peli2.getIdPelicula(), peli2);

        Pelicula peli3 = new Pelicula("P003", "Wonka", 116, "MAYORES 7");
        peli3.setGenero("Musical");
        peli3.setFormato("2D");
        peli3.setIdioma("Espanol");
        peli3.setSubtitulos(false);
        peliculas.put(peli3.getIdPelicula(), peli3);

        // Crear funciones
        crearFuncion("F001", LocalDateTime.now().plusDays(1), LocalTime.of(14, 30), peli1, sala1);
        crearFuncion("F002", LocalDateTime.now().plusDays(1), LocalTime.of(17, 0), peli1, sala1);
        crearFuncion("F003", LocalDateTime.now().plusDays(1), LocalTime.of(15, 0), peli2, sala2);
        crearFuncion("F004", LocalDateTime.now().plusDays(1), LocalTime.of(16, 30), peli2, sala3);
        crearFuncion("F005", LocalDateTime.now().plusDays(1), LocalTime.of(18, 0), peli3, sala2);
        crearFuncion("F006", LocalDateTime.now().plusDays(2), LocalTime.of(20, 0), peli1, sala1);
        crearFuncion("F007", LocalDateTime.now().plusDays(2), LocalTime.of(19, 30), peli3, sala3);

        // Crear clientes
        Cliente cliente1 = new Cliente("C001", "Juan Perez", "12345678");
        cliente1.setEmailCliente("juan@email.com");
        cliente1.setTelefonoCliente("3001234567");
        cliente1.setNivelMembresia("PLATA");
        cliente1.setPuntosAcumulados(750);
        clientes.put(cliente1.getIdCliente(), cliente1);

        Cliente cliente2 = new Cliente("C002", "Maria Gomez", "87654321");
        cliente2.setEmailCliente("maria@email.com");
        cliente2.setTelefonoCliente("3007654321");
        cliente2.setNivelMembresia("ORO");
        cliente2.setPuntosAcumulados(2500);
        cliente2.setEsVIP(true);
        clientes.put(cliente2.getIdCliente(), cliente2);

        Cliente cliente3 = new Cliente("C003", "Carlos Lopez", "11223344");
        cliente3.setEmailCliente("carlos@email.com");
        cliente3.setTelefonoCliente("3011122334");
        cliente3.setNivelMembresia("BASICA");
        clientes.put(cliente3.getIdCliente(), cliente3);
    }

    /**
     * Metodo auxiliar para crear una funcion con sus relaciones.
     * 
     * @param id Identificador de la funcion
     * @param fecha Fecha de la funcion
     * @param hora Hora de inicio
     * @param peli Pelicula a proyectar
     * @param sala Sala donde se proyecta
     */
    private void crearFuncion(String id, LocalDateTime fecha, LocalTime hora, Pelicula peli, Sala sala) {
        Funcion funcion = new Funcion(id, fecha, hora);
        funcion.setPelicula(peli);
        funcion.setSala(sala);
        funcion.setPrecioBaseFuncion(new BigDecimal("10000"));
        funcion.setTipoIdiomaFuncion(peli.getIdioma());
        funcion.ActualizarDisponibilidad();
        
        // Marcar algunos asientos como ocupados para simular
        if (sala.getMapaAsientos().size() > 5) {
            for (int i = 0; i < 5; i++) {
                sala.getMapaAsientos().get(i).ocupar();
            }
        }
        
        funcion.ActualizarDisponibilidad();
        funciones.put(funcion.getIdFuncion(), funcion);
    }

    // ==================== METODOS CRUD PARA SALAS ====================
    
    /** @param id Identificador de la sala
     *  @return Sala encontrada o null si no existe */
    public Sala getSala(String id) { return salas.get(id); }
    
    /** @return Lista de todas las salas */
    public List<Sala> getAllSalas() { return new ArrayList<>(salas.values()); }

    // ==================== METODOS CRUD PARA PELICULAS ====================
    
    /** @param id Identificador de la pelicula
     *  @return Pelicula encontrada o null si no existe */
    public Pelicula getPelicula(String id) { return peliculas.get(id); }
    
    /** @return Lista de todas las peliculas */
    public List<Pelicula> getAllPeliculas() { return new ArrayList<>(peliculas.values()); }

    // ==================== METODOS CRUD PARA FUNCIONES ====================
    
    /** @param id Identificador de la funcion
     *  @return Funcion encontrada o null si no existe */
    public Funcion getFuncion(String id) { return funciones.get(id); }
    
    /** @return Lista de todas las funciones */
    public List<Funcion> getAllFunciones() { return new ArrayList<>(funciones.values()); }

    // ==================== METODOS CRUD PARA CLIENTES ====================
    
    /** @param id Identificador del cliente
     *  @return Cliente encontrado o null si no existe */
    public Cliente getCliente(String id) { return clientes.get(id); }
    
    /**
     * Busca un cliente por su numero de documento de identidad.
     * 
     * @param documento Documento de identidad del cliente
     * @return Cliente encontrado o null si no existe
     */
    public Cliente getClienteByDocumento(String documento) {
        return clientes.values().stream()
                .filter(c -> c.getDocumentoIdentidad().equals(documento))
                .findFirst()
                .orElse(null);
    }
    
    /** @param cliente Cliente a agregar */
    public void addCliente(Cliente cliente) {
        clientes.put(cliente.getIdCliente(), cliente);
    }  
    
    /** @return Lista de todos los clientes */
    public List<Cliente> getAllClientes() { return new ArrayList<>(clientes.values()); }

    // ==================== METODOS CRUD PARA RESERVAS ====================
    
    /** @param id Identificador de la reserva
     *  @return Reserva encontrada o null si no existe */
    public Reserva getReserva(String id) { return reservas.get(id); }
    
    /** @return Lista de todas las reservas */
    public List<Reserva> getAllReservas() { return new ArrayList<>(reservas.values()); }

    /**
     * Genera un nuevo identificador unico para una reserva.
     * Formato: "R" + numero incremental (empieza en 1000).
     * 
     * @return Nuevo identificador de reserva
     */
    public String generarIdReserva() {
        return "R" + (contadorReservas++);
    }

    /** @param reserva Reserva a agregar */
    public void addReserva(Reserva reserva) {
        reservas.put(reserva.getIdReserva(), reserva);
    }

    // ==================== METODOS DE ACTUALIZACION ====================
    
    /** @param funcion Funcion a actualizar */
    public void updateFuncion(Funcion funcion) {
        funciones.put(funcion.getIdFuncion(), funcion);
    }

    /** @param cliente Cliente a actualizar */
    public void updateCliente(Cliente cliente) {
        clientes.put(cliente.getIdCliente(), cliente);
    }

    // ==================== METODOS DE CONSULTA ESPECIALIZADA ====================
    
    /**
     * Obtiene todas las funciones de una pelicula especifica.
     * 
     * @param idPelicula Identificador de la pelicula
     * @return Lista de funciones de esa pelicula
     */
    public List<Funcion> getFuncionesPorPelicula(String idPelicula) {
        return funciones.values().stream()
                .filter(f -> idPelicula.equals(f.getIdPelicula()))
                .toList();
    }

    /**
     * Obtiene todas las funciones en una sala especifica.
     * 
     * @param idSala Identificador de la sala
     * @return Lista de funciones en esa sala
     */
    public List<Funcion> getFuncionesPorSala(String idSala) {
        return funciones.values().stream()
                .filter(f -> idSala.equals(f.getIdSala()))
                .toList();
    }

    /**
     * Obtiene todas las funciones que aun tienen asientos disponibles.
     * 
     * @return Lista de funciones con disponibilidad
     */
    public List<Funcion> getFuncionesDisponibles() {
        return funciones.values().stream()
                .filter(f -> f.getAsientosDisponibles() > 0)
                .toList();
    }
    
}//fin de la clase