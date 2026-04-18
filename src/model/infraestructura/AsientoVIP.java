package model.infraestructura;

import java.math.BigDecimal;

/**
 * Clase que representa un asiento VIP en la sala de cine.
 * Hereda de Asiento e incluye beneficios como bebida y comida (mantenida),
 * ademas de mayor espacio para piernas (120 cm) y reposabrazos.
 * El precio se calcula con un multiplicador base de 2.5x sobre el precio base,
 * mas los costos adicionales de bebida ($5,000) y comida ($3,000).
 * 
 * @author ISC Israel de Jesus Mar Parada
 * @version 1.0
 * @see Asiento
 * @see AsientoNormal
 * @see AsientoPremium
 */
public class AsientoVIP extends Asiento {

    // ==================== ATRIBUTOS ====================
    
    /** Indica si el asiento incluye bebida en el precio. */
    private boolean incluyeBebida;
    
    /** Indica si el asiento incluye comida (mantenida) en el precio. */
    private boolean incluyeMantenida;

    // ==================== CONSTRUCTOR ====================
    
    /**
     * Constructor para crear un asiento VIP.
     * El tipo de asiento se establece automaticamente como "VIP".
     * Por defecto incluye bebida y comida, tiene 120 cm de espacio para piernas
     * y reposabrazos incluido.
     * 
     * @param idAsiento Identificador unico del asiento
     * @param fila Letra de la fila del asiento
     * @param numero Numero del asiento dentro de la fila
     * @param precioBase Precio base del asiento sin extras
     */
    public AsientoVIP(String idAsiento, String fila, int numero, BigDecimal precioBase) {
        super(idAsiento, fila, numero, precioBase);
        this.tipoAsiento = "VIP";
        this.incluyeBebida = true;
        this.incluyeMantenida = true;
        this.espacioPiernas = 120;
        this.tieneReposabrazos = true;
    }

    // ==================== METODOS SOBRESCRITOS ====================
    
    /**
     * Calcula el precio final del asiento VIP.
     * Formula: (precioBase x 2.5) + ($5,000 si incluye bebida) + ($3,000 si incluye comida)
     * 
     * @return Precio final del asiento VIP
     */
    @Override
    public BigDecimal calcularPrecioFinal() {
        BigDecimal precio = precioBase.multiply(new BigDecimal("2.5"));
        
        if (incluyeBebida) {
            precio = precio.add(new BigDecimal("5000"));
        }
        if (incluyeMantenida) {
            precio = precio.add(new BigDecimal("3000"));
        }
        
        return precio;
    }

    // ==================== GETTERS Y SETTERS ====================
    
    /** @return true si incluye bebida, false en caso contrario */
    public boolean isIncluyeBebida() { return incluyeBebida; }
    
    /** @param incluyeBebida Nueva condicion de bebida incluida */
    public void setIncluyeBebida(boolean incluyeBebida) { this.incluyeBebida = incluyeBebida; }

    /** @return true si incluye comida (mantenida), false en caso contrario */
    public boolean isIncluyeMantenida() { return incluyeMantenida; }
    
    /** @param incluyeMantenida Nueva condicion de comida incluida */
    public void setIncluyeMantenida(boolean incluyeMantenida) { this.incluyeMantenida = incluyeMantenida; }
    
}//fin de la clase