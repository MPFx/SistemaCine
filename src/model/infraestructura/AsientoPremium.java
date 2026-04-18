package model.infraestructura;

import java.math.BigDecimal;

/**
 * Clase que representa un asiento Premium en la sala de cine.
 * Hereda de Asiento y ofrece la maxima experiencia con butaca reclinable,
 * calefaccion, la mayor distancia para piernas (150 cm) y ajuste de precio
 * segun la distancia a la pantalla.
 * El precio se calcula con un multiplicador base de 4.0x sobre el precio base,
 * mas costo adicional por calefaccion ($2,000) y ajuste si esta muy cerca de la pantalla.
 * 
 * @author ISC Israel de Jesus Mar Parada
 * @version 1.0
 * @see Asiento
 * @see AsientoNormal
 * @see AsientoVIP
 */
public class AsientoPremium extends Asiento {

    // ==================== ATRIBUTOS ====================
    
    /** Indica si la butaca es reclinable. */
    private boolean butacaReclinable;
    
    /** Indica si el asiento tiene calefaccion. */
    private boolean calefaccion;
    
    /** Distancia en metros del asiento a la pantalla. */
    private BigDecimal distanciaPantalla;

    // ==================== CONSTRUCTOR ====================
    
    /**
     * Constructor para crear un asiento Premium.
     * El tipo de asiento se establece automaticamente como "PREMIUM".
     * Por defecto incluye butaca reclinable, calefaccion, distancia a pantalla de 5.5 metros
     * y espacio para piernas de 150 cm (maximo).
     * 
     * @param idAsiento Identificador unico del asiento
     * @param fila Letra de la fila del asiento
     * @param numero Numero del asiento dentro de la fila
     * @param precioBase Precio base del asiento sin extras
     */
    public AsientoPremium(String idAsiento, String fila, int numero, BigDecimal precioBase) {
        super(idAsiento, fila, numero, precioBase);
        this.tipoAsiento = "PREMIUM";
        this.butacaReclinable = true;
        this.calefaccion = true;
        this.distanciaPantalla = new BigDecimal("5.5");
        this.espacioPiernas = 150;
    }

    // ==================== METODOS SOBRESCRITOS ====================
    
    /**
     * Calcula el precio final del asiento Premium.
     * Formula: (precioBase x 4.0) + ($2,000 si tiene calefaccion)
     * Si la distancia a la pantalla es menor a 3 metros, se aplica un recargo del 30%.
     * 
     * @return Precio final del asiento Premium
     */
    @Override
    public BigDecimal calcularPrecioFinal() {
        BigDecimal precio = precioBase.multiply(new BigDecimal("4.0"));
        
        if (calefaccion) {
            precio = precio.add(new BigDecimal("2000"));
        }
        
        if (distanciaPantalla.compareTo(new BigDecimal("3.0")) < 0) {
            precio = precio.multiply(new BigDecimal("1.3"));
        }
        
        return precio;
    }

    // ==================== GETTERS Y SETTERS ====================
    
    /** @return true si la butaca es reclinable, false en caso contrario */
    public boolean isButacaReclinable() { return butacaReclinable; }
    
    /** @param butacaReclinable Nuevo estado de butaca reclinable */
    public void setButacaReclinable(boolean butacaReclinable) { this.butacaReclinable = butacaReclinable; }

    /** @return true si tiene calefaccion, false en caso contrario */
    public boolean isCalefaccion() { return calefaccion; }
    
    /** @param calefaccion Nuevo estado de calefaccion */
    public void setCalefaccion(boolean calefaccion) { this.calefaccion = calefaccion; }

    /** @return Distancia en metros a la pantalla */
    public BigDecimal getDistanciaPantalla() { return distanciaPantalla; }
    
    /** @param distanciaPantalla Nueva distancia a la pantalla */
    public void setDistanciaPantalla(BigDecimal distanciaPantalla) { this.distanciaPantalla = distanciaPantalla; }
    
}//fin de la clase