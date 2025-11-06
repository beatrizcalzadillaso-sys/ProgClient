package app;

/**
 * Línea de carrito/pedido.
 * - Referencia al catálogo (ProductoItem)
 * - cantidad en [1-20] .
 * - subtotalEnCentimos se DERIVA SIEMPRE: producto.precioEnCentimos * cantidad.
 *
 * No se usan métodos @Override (toString/equals/hashCode).
 * Para imprimir, usar aTextoDeLinea().
 */
public class LineaProductoItem {

    // 
    // Campos
    // 
    private ProductoItem producto; // no nulo
    private int cantidad; // [1..20]
    private int subtotalEnCentimos; // derivado: producto.precio * cantidad

    // 
    // Constructores
    // 

    public LineaProductoItem() {
        // Vacío: configurar con setters validados y luego revisar
        // isCompletamenteValida()
    }

    /**
     * Constructor con validaciones por condicionales.
     * Si producto es nulo o cantidad no está en [1..20], no se asigna y el objeto
     * quedará inválido.
     * Verificar con isCompletamenteValida() tras construir.
     */
    public LineaProductoItem(ProductoItem producto, int cantidad) {
        setProducto(producto);
        setCantidad(cantidad); // recalcula subtotal si el producto ya es válido
        recalcularSubtotal(); // asegura consistencia si el orden de llamadas cambia
    }

    // 
    // Setters validados
    // 

    /**
     * Asigna el producto si no es nulo.
     * Devuelve true si aplica; false si rechaza.
     */
    public boolean setProducto(ProductoItem producto) {
        boolean valido = false;

        if (producto != null) {
            this.producto = producto;
            recalcularSubtotal();
            valido = true;
        }

        return valido;
    }

    /**
     * Asigna la cantidad si está en [1..20].
     * Devuelve true si aplica; false si rechaza.
     */
    public boolean setCantidad(int cantidad) {
        boolean valido = false;

        if (cantidad >= 1 && cantidad <= 20) {
            this.cantidad = cantidad;
            recalcularSubtotal();
            valido = true;
        }

        return valido;
    }

    /**
     * Suma (o resta) cantidad respetando el tope [1..20].
     * - delta > 0 para sumar, delta < 0 para restar.
     * - Devuelve la variación realmente aplicada (puede ser menor si se topa en 1 o
     * 20).
     * Ej.: si cantidad=19 y delta=5, aplicará +1 (tope 20) y devolverá 1.
     */
    public int agregarCantidadConTope(int delta) {
        int variacionAplicada = 0; // valor por defecto

        if (delta != 0) {
            int original = this.cantidad;
            int nueva = original + delta;

            if (nueva < 1) {
                nueva = 1;
            } else if (nueva > 20) {
                nueva = 20;
            }

            this.cantidad = nueva;
            recalcularSubtotal();
            variacionAplicada = (nueva - original);
        }

        return variacionAplicada;
    }

    //
    // Getters
    // 

    public ProductoItem getProducto() {
        return producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public int getSubtotalEnCentimos() {
        return subtotalEnCentimos;
    }

    /**
     * Devuelve el código de producto (tres cifras) si hay producto; en otro caso 0.
     */
    public int getCodProd() {
        int codigo = 0;

        if (producto != null) {
            codigo = producto.getCodProd();
        }

        return codigo;
    }

    /**
     * Devuelve el nombre del producto o cadena vacía si no hay producto.
     */
    public String getNomProd() {
        String nombre = "";

        if (producto != null) {
            nombre = producto.getNomProd();
        }

        return nombre;
    }

    /**
     * Devuelve el tipoNum del producto o 0 si no hay producto.
     */
    public int getTipoNum() {
        int tipo = 0;

        if (producto != null) {
            tipo = producto.getTipoNum();
        }

        return tipo;
    }

    /**
     * Devuelve el precio unitario EN CÉNTIMOS o 0 si no hay producto.
     */
    public int getPrecioUnitarioEnCentimos() {
        int precio = 0;

        if (producto != null) {
            precio = producto.getPrecioEnCentimos();
        }

        return precio;
    }

    // 
    // Estado y formato
    // 

    /**
     * Recalcula el subtotal como (precioUnitario * cantidad) si hay producto y
     * cantidad válida.
     * Si no, lo deja en 0 para evitar el error al multiplicar por zero.
     */
    private void recalcularSubtotal() {
        if (producto != null && cantidad >= 1 && cantidad <= 20) {
            this.subtotalEnCentimos = producto.getPrecioEnCentimos() * cantidad;
        } else {
            this.subtotalEnCentimos = 0;
        }
    }

    /**
     * Devuelve true si:
     * - hay producto,
     * - cantidad en [1-20],
     * - y subtotal consistente (>= 0).
     */
    public boolean isCompletamenteValida() {
        boolean valida = false;

        if (producto != null && cantidad >= 1 && cantidad <= 20 && subtotalEnCentimos >= 0) {
            valida = true;
        }

        return valida;
    }

    /**
     * Formatea la línea para impresión en consola (sin IVA).
     * Ej.: "105 | Botella de agua | cant=3 | p.u.=0.60 | sub=1.80"
     */
    public String aTextoDeLinea() {
        String pu = ProductoItem.formatearCentimos(getPrecioUnitarioEnCentimos());
        String sub = ProductoItem.formatearCentimos(getSubtotalEnCentimos());
        return getCodProd() + " | " + getNomProd()
                + " | cant=" + cantidad
                + " | p.u.=" + pu
                + " | sub=" + sub;
    }
}
