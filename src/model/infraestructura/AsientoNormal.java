package model.infraestructura;

import java.math.BigDecimal;

/**
 * Clase que representa un asiento normal en la sala de cine.
 * Hereda de Asiento y puede tener la caracteristica adicional de ser preferencial,
 * lo que incrementa su precio en un 20%.
 * 
 * @author ISC Israel de Jesus Mar Parada
 * @version 1.0
 * @see Asiento
 * @see AsientoVIP
 * @see AsientoPremium
 */
public class AsientoNormal extends Asiento {

    // ==================== ATRIBUTOS ====================
    
    /** Indica si el asiento es preferencial (ubicacion privilegiada). */
    private boolean esPreferencial;

    // ==================== CONSTRUCTOR ====================
    
    /**
     * Constructor para crear un asiento normal.
     * El tipo de asiento se establece automaticamente como "NORMAL".
     * Por defecto no es preferencial.
     * 
     * @param idAsiento Identificador unico del asiento
     * @param fila Letra de la fila del asiento
     * @param numero Numero del asiento dentro de la fila
     * @param precioBase Precio base del asiento sin extras
     */
    public AsientoNormal(String idAsiento, String fila, int numero, BigDecimal precioBase) {
        super(idAsiento, fila, numero, precioBase);
        this.tipoAsiento = "NORMAL";
        this.esPreferencial = false;
    }

    // ==================== METODOS SOBRESCRITOS ====================
    
    /**
     * Calcula el precio final del asiento normal.
     * Si el asiento es preferencial, aplica un recargo del 20%.
     * Si no, mantiene el precio base.
     * 
     * @return Precio final del asiento
     */
    @Override
    public BigDecimal calcularPrecioFinal() {
        if (esPreferencial) {
            return precioBase.multiply(new BigDecimal("1.2"));
        }
        return precioBase;
    }

    // ==================== GETTERS Y SETTERS ====================
    
    /** @return true si el asiento es preferencial, false en caso contrario */
    public boolean isEsPreferencial() { return esPreferencial; }
    
    /** @param esPreferencial Nuevo estado de preferencial */
    public void setEsPreferencial(boolean esPreferencial) { this.esPreferencial = esPreferencial; }
    
}//fin de la clase