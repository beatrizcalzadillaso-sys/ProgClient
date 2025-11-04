package app;

/**
 *
 */
public class ProductoItem {

    // propiedades
    private int codProd; // tres cifras 100-499
    private int tipoNum; // 1,2,3,4
    private String nomProd; // debe ser string
    private int precioEnCentimos; // almacenamiento interno (entero)

    /**
     * Constructores
     */

    public ProductoItem() {
        // Constructor vacío para crear el objeto sin darle valor a sus propiedades
    }

    /**
     * Constructor con validaciones con las propiedades
     * Si algún dato no cumple, no se asigna (queda en valor por defecto).
     * Verificar luego con isCompletamenteValido().
     */
    public ProductoItem(int codProd, int tipoNum, String nomProd, double precioEnEuros) {
        setCodProd(codProd);
        setTipoNum(tipoNum);
        setNomProd(nomProd);
        setPrecioDesdeEuros(precioEnEuros);
    }

    /**
     * Setters con validaciones
     */

    /**
     * codProd debe ser de TRES cifras: 100..499.
     */
    public boolean setCodProd(int codProd) {
        boolean valido = false;

        if (codProd >= 100 && codProd <= 499) {
            this.codProd = codProd;
            valido = true;
        }

        return valido;
    }

    /**
     * tipoNum válido: 1,2,3,4.
     */
    public boolean setTipoNum(int tipoNum) {
        boolean valido = false;

        if (tipoNum >= 1 && tipoNum <= 4) {
            this.tipoNum = tipoNum;
            valido = true;
        }

        return valido;
    }

    /**
     * valida que no se introdujo espacios " " en el nombre de producto
     */
    public boolean setNomProd(String nomProd) {
        boolean valido = false;

        if (nomProd != null) {
            String textoLimpio = nomProd.trim(); // quita espacios al inicio y final
            if (!textoLimpio.isEmpty()) { // si queda algo de texto
                this.nomProd = textoLimpio;
                valido = true;
            }
        }

        return valido;
    }

    /**
     * valida que el precio sea mayor que zero
     */
    public boolean setPrecioDesdeEuros(double precioEnEuros) {
        boolean valido = false;

        if (precioEnEuros >= 0) {
            this.precioEnCentimos = (int) Math.round(precioEnEuros * 100);
            valido = true;
        }

        return valido;
    }

    /** precio interno directamente en céntimos (para compatibilidad) */
    public boolean setPrecioEnCentimos(int precioEnCentimos) {
        boolean valido = false;

        if (precioEnCentimos >= 0) {
            this.precioEnCentimos = precioEnCentimos;
            valido = true;
        }

        return valido;
    }

    /**
     * Getters
     */

    public int getCodProd() {
        return codProd;
    }

    public int getTipoNum() {
        return tipoNum;
    }

    public String getNomProd() {
        return nomProd;
    }

    public int getPrecioEnCentimos() {
        return precioEnCentimos;
    }

    /** Devuelve el precio como double en euros (para mostrarlo si se desea) */
    public double getPrecioEnEuros() {
        return precioEnCentimos / 100.0;
    }

    /**
     * Verifica que TODOS los campos cumplen sus reglas.
     */
    public boolean isCompletamenteValido() {
        boolean validaciones = (codProd >= 100 && codProd <= 499)
                && (tipoNum >= 1 && tipoNum <= 4)
                && (nomProd != null && !nomProd.trim().isEmpty())
                && (precioEnCentimos >= 0);
        return validaciones;
    }

     /** Formatea un valor en céntimos como texto con dos decimales */
    public static String formatearCentimos(int centimos) {
        return String.format("%.2f€", centimos / 100.0);
    }

    public String aTextoDeCatalogo() {
        return String.format("Código: %03d | Tipo: %d | %s | Precio: %s (sin IVA)",
            codProd, tipoNum, nomProd, formatearCentimos(precioEnCentimos));
    }
}
