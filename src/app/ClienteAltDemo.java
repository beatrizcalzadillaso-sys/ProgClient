package app;

import java.util.ArrayList;
import java.util.List;

/**
 * prueba para ejecutar el flujo del cliente de forma independiente.
 */
public class ClienteAltDemo {

    public static void main(String[] args) {
        // Crear un catálogo de ejemplo con algunos productos
        List<ProductoItem> catalogo = new ArrayList<>();

        ProductoItem p1 = new ProductoItem(112, 1, "Agua natural", 1.26);
        ProductoItem p2 = new ProductoItem(123, 1, "Agua con gas", 1.26);
        ProductoItem p3 = new ProductoItem(234, 2, "Zumo de naranja", 1.35);
        ProductoItem p4 = new ProductoItem(345, 3, "Zumo de tomate", 1.35);
        ProductoItem p5 = new ProductoItem(456, 4, "Café solo", 1.10);

        // Solo añadimos los productos válidos (según las validaciones de ProductoItem)
        if (p1.isCompletamenteValido()) catalogo.add(p1);
        if (p2.isCompletamenteValido()) catalogo.add(p2);
        if (p3.isCompletamenteValido()) catalogo.add(p3);
        if (p4.isCompletamenteValido()) catalogo.add(p4);
        if (p5.isCompletamenteValido()) catalogo.add(p5);

        // Ejecutar el flujo de cliente
        ClienteAlt cliente = new ClienteAlt();
        cliente.ejecutarCliente(catalogo);
    }
}
