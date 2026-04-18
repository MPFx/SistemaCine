package model.infraestructura;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Clase que representa una sala de cine dentro del complejo.
 * Contiene informacion sobre la capacidad, tecnologias de sonido e imagen,
 * y el mapa de asientos distribuidos en filas.
 * Permite generar automaticamente los asientos con una distribucion:
 * 10% Premium (ultimas filas), 20% VIP y 70% Normal.
 * 
 * @author ISC Israel de Jesus Mar Parada
 * @version 1.0
 * @see Asiento
 * @see AsientoNormal
 * @see AsientoVIP
 * @see AsientoPremium
 */
public class Sala {

    // ==================== ATRIBUTOS ====================
    
    /** Identificador unico de la sala. */
    private String idSala;
    
    /** Nombre descriptivo de la sala (ej: Sala 1, Sala Premium). */
    private String nombreSala;
    
    /** Capacidad total de la sala (filas x asientosPorFila). */
    private int capacidadTotal;
    
    /** Numero de filas de asientos en la sala. */
    private int filas;
    
    /** Numero de asientos por fila. */
    private int asientosPorFila;
    
    /** Tipo de sala (ESTANDAR, 3D, IMAX, DBOX). */
    private String tipoSala;
    
    /** Tecnologia de sonido (Dolby 7.1, Atmos, etc.). */
    private String tecnologiaSonido;
    
    /** Tecnologia de imagen (2K Digital, 4K, IMAX Laser). */
    private String tecnologiaImagen;
    
    /** Lista de todos los asientos que componen el mapa de la sala. */
    private List<Asiento> mapaAsientos;

    // ==================== CONSTRUCTOR ====================
    
    /**
     * Constructor para crear una nueva sala de cine.
     * Inicializa la sala con tecnologias por defecto: sonido Dolby 7.1,
     * imagen 2K Digital y tipo ESTANDAR.
     * 
     * @param idSala Identificador unico de la sala
     * @param nombreSala Nombre descriptivo de la sala
     * @param filas Numero de filas de asientos
     * @param asientosPorFila Numero de asientos por fila
     */
    public Sala(String idSala, String nombreSala, int filas, int asientosPorFila) {
        this.idSala = idSala;
        this.nombreSala = nombreSala;
        this.filas = filas;
        this.asientosPorFila = asientosPorFila;
        this.capacidadTotal = filas * asientosPorFila;
        this.mapaAsientos = new ArrayList<>();
        this.tecnologiaSonido = "Dolby 7.1";
        this.tecnologiaImagen = "2K Digital";
        this.tipoSala = "ESTANDAR";
    }

    // ==================== METODOS DE GENERACION ====================
    
    /**
     * Genera el mapa completo de asientos de la sala.
     * Distribuye los asientos de la siguiente manera:
     * - 10% Premium (ultimas filas): precio base $15,000
     * - 20% VIP (siguientes filas): precio base $12,000
     * - 70% Normal (resto): precio base $8,000
     * Asigna coordenadas X e Y para la representacion visual
     * y establece la ubicacion de la sala.
     */
    public void GenerarMapaAsientos() {
        mapaAsientos.clear();
        char filaChar = 'A';
        
        for (int i = 0; i < filas; i++) {
            for (int j = 1; j <= asientosPorFila; j++) {
                String id = idSala + "-" + filaChar + j;
                Asiento asiento;
                
                if (i < filas * 0.1) {
                    asiento = new AsientoPremium(id, String.valueOf(filaChar), j, 
                                               new java.math.BigDecimal("15000"));
                } else if (i < filas * 0.3) {
                    asiento = new AsientoVIP(id, String.valueOf(filaChar), j, 
                                           new java.math.BigDecimal("12000"));
                } else {
                    asiento = new AsientoNormal(id, String.valueOf(filaChar), j, 
                                              new java.math.BigDecimal("8000"));
                }
                
                asiento.setCoordenadaX(j * 10);
                asiento.setCoordenadaY(i * 10);
                asiento.setUbicacionSala(nombreSala);
                
                mapaAsientos.add(asiento);
            }
            filaChar++;
        }
    }

    // ==================== METODOS DE CONSULTA ====================
    
    /**
     * Obtiene la lista de asientos actualmente disponibles en la sala.
     * 
     * @return Lista de asientos con estado DISPONIBLE
     */
    public List<Asiento> ObtenerAsientosDisponibles() {
        return mapaAsientos.stream()
                .filter(Asiento::estaDisponible)
                .collect(Collectors.toList());
    }

    /**
     * Valida si la cantidad solicitada de asientos no supera la capacidad total.
     * 
     * @param cantidad Cantidad de asientos a validar
     * @return true si la cantidad es menor o igual a la capacidad total
     */
    public boolean ValidarCapacidad(int cantidad) {
        return cantidad <= capacidadTotal;
    }

    /**
     * Busca un asiento por su identificador unico.
     * 
     * @param idAsiento Identificador del asiento a buscar
     * @return Asiento encontrado o null si no existe
     */
    public Asiento buscarAsiento(String idAsiento) {
        return mapaAsientos.stream()
                .filter(a -> a.getIdAsiento().equals(idAsiento))
                .findFirst()
                .orElse(null);
    }

    // ==================== GETTERS Y SETTERS ====================
    
    /** @return Identificador unico de la sala */
    public String getIdSala() { return idSala; }
    
    /** @param idSala Nuevo identificador de la sala */
    public void setIdSala(String idSala) { this.idSala = idSala; }

    /** @return Nombre descriptivo de la sala */
    public String getNombreSala() { return nombreSala; }
    
    /** @param nombreSala Nuevo nombre de la sala */
    public void setNombreSala(String nombreSala) { this.nombreSala = nombreSala; }

    /** @return Capacidad total de la sala */
    public int getCapacidadTotal() { return capacidadTotal; }
    
    /** @param capacidadTotal Nueva capacidad total */
    public void setCapacidadTotal(int capacidadTotal) { this.capacidadTotal = capacidadTotal; }

    /** @return Numero de filas de asientos */
    public int getFilas() { return filas; }
    
    /** @param filas Nuevo numero de filas */
    public void setFilas(int filas) { this.filas = filas; }

    /** @return Numero de asientos por fila */
    public int getAsientosPorFila() { return asientosPorFila; }
    
    /** @param asientosPorFila Nuevo numero de asientos por fila */
    public void setAsientosPorFila(int asientosPorFila) { this.asientosPorFila = asientosPorFila; }

    /** @return Tipo de sala (ESTANDAR, 3D, IMAX, DBOX) */
    public String getTipoSala() { return tipoSala; }
    
    /** @param tipoSala Nuevo tipo de sala */
    public void setTipoSala(String tipoSala) { this.tipoSala = tipoSala; }

    /** @return Tecnologia de sonido */
    public String getTecnologiaSonido() { return tecnologiaSonido; }
    
    /** @param tecnologiaSonido Nueva tecnologia de sonido */
    public void setTecnologiaSonido(String tecnologiaSonido) { this.tecnologiaSonido = tecnologiaSonido; }

    /** @return Tecnologia de imagen */
    public String getTecnologiaImagen() { return tecnologiaImagen; }
    
    /** @param tecnologiaImagen Nueva tecnologia de imagen */
    public void setTecnologiaImagen(String tecnologiaImagen) { this.tecnologiaImagen = tecnologiaImagen; }

    /** @return Lista completa de asientos de la sala */
    public List<Asiento> getMapaAsientos() { return mapaAsientos; }
    
    /** @param mapaAsientos Nueva lista de asientos */
    public void setMapaAsientos(List<Asiento> mapaAsientos) { this.mapaAsientos = mapaAsientos; }
    
}//fin de la clase