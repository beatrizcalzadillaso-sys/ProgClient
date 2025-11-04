package app;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Flujo de cliente (consola).
 * - Trabaja con ProductoItem (codProd, tipoNum, nomProd, precioEnCentimos).
 * - Carrito con LineaProductoItem (referencia al ProductoItem y cantidad 1..20).
 * - Cálculos en céntimos; formateo con ProductoItem.formatearCentimos().
 * - Validaciones con condicionales (sin excepciones).
 */
public class ClienteAlt {

    private final Scanner sc = new Scanner(System.in);

    /* ========== Entrada segura ========== */

    private int leerEntero() {
        // Lee una línea y trata de convertirla a int; si falla, devuelve Integer.MIN_VALUE para indicar inválido
        String linea = sc.nextLine();
        try {
            return Integer.parseInt(linea.trim());
        } catch (Exception e) {
            return Integer.MIN_VALUE;
        }
    }

    private void pausar(String msg) {
        System.out.println(msg);
        sc.nextLine();
    }

    /* ========== Menús ========== */

    /** Muestra menú de tipos (1..4) y retorna el elegido. Repite hasta ser válido. */
    private int pedirTipo() {
        int tipo = -1;
        boolean valido = false;

        while (!valido) {
            System.out.println("\n=== TIPOS DE PRODUCTO ===");
            System.out.println("1) Tipo 1");
            System.out.println("2) Tipo 2");
            System.out.println("3) Tipo 3");
            System.out.println("4) Tipo 4");
            System.out.print("Seleccione (1-4): ");

            int in = leerEntero();
            if (in >= 1 && in <= 4) {
                tipo = in;
                valido = true;
            } else {
                System.out.println("Valor inválido, debe introducir el numero de los grupos disponibles. Intente de nuevo.");
            }
        }
        return tipo;
    }

    /** Lista productos de un tipo dado. Devuelve true si hay al menos uno. */
    private boolean listarProductosPorTipo(List<ProductoItem> catalogo, int tipoNum) {
        System.out.println("\n=== PRODUCTOS DEL TIPO " + tipoNum + " ===");
        boolean hay = false;
        for (ProductoItem p : catalogo) {
            if (p != null && p.getTipoNum() == tipoNum) {
                System.out.println(p.aTextoDeCatalogo()); // "cod | tipo | nombre | precioSinIVA"
                hay = true;
            }
        }
        if (!hay) {
            System.out.println("(No hay productos para este tipo)");
        }
        return hay;
    }

    /** Muestra el carrito (sin IVA). */
    private void verPedido(List<LineaProductoItem> carrito) {
        System.out.println("\n=== PEDIDO (sin IVA) ===");
        if (carrito.isEmpty()) {
            System.out.println("(Carrito vacío)");
            return;
        }
        int subtotal = 0;
        for (LineaProductoItem l : carrito) {
            if (l != null) {
                System.out.println(l.aTextoDeLinea()); // "cod | nombre | cant=n | p.u.=X | sub=Y"
                subtotal += l.getSubtotalEnCentimos();
            }
        }
        System.out.println("Subtotal (sin IVA): " + ProductoItem.formatearCentimos(subtotal));
    }

    /** Busca un ProductoItem por código exacto (tres cifras) dentro del catálogo. */
    private ProductoItem buscarPorCodigo(List<ProductoItem> catalogo, int cod) {
        for (ProductoItem p : catalogo) {
            if (p != null && p.getCodProd() == cod) {
                return p;
            }
        }
        return null;
    }

    /** Añade una línea al carrito o suma cantidad si ya existe, respetando tope 20. */
    private void addProductoAlCarrito(List<LineaProductoItem> carrito, ProductoItem prod, int cantidad) {
        // ¿ya existe en carrito?
        for (LineaProductoItem l : carrito) {
            if (l != null && l.getProducto() != null && l.getProducto().getCodProd() == prod.getCodProd()) {
                // suma con tope
                int aplicado = l.agregarCantidadConTope(cantidad);
                if (aplicado < cantidad) {
                    System.out.println("Se aplicaron solo " + aplicado + " unidades (tope 20).");
                }
                return;
            }
        }
        // no existe, crear nueva línea validada
        LineaProductoItem nueva = new LineaProductoItem(prod, cantidad);
        if (!nueva.isCompletamenteValida()) {
            System.out.println("No se pudo añadir la línea (verifique cantidad 1..20).");
            return;
        }
        carrito.add(nueva);
    }

    /** Fase Productos para un tipo dado. Devuelve true si el cliente decide terminar compra. */
    private boolean faseProductosParaTipo(List<ProductoItem> catalogo, List<LineaProductoItem> carrito, int tipoNum) {
        boolean deseaTerminar = false;
        boolean seguir = true;

        while (seguir) {
            boolean hay = listarProductosPorTipo(catalogo, tipoNum);

            System.out.println("\nOpciones:");
            System.out.println("1) Añadir producto por código");
            System.out.println("2) Ver pedido");
            System.out.println("3) Volver a TIPOS");
            System.out.println("4) Terminar compra (ir a RESUMEN)");
            System.out.print("Seleccione (1-4): ");

            int op = leerEntero();
            if (op == 1) {
                if (!hay) {
                    System.out.println("No hay productos en este tipo.");
                    continue;
                }
                System.out.print("Ingrese código de TRES cifras (100-499): ");
                int cod = leerEntero();

                if (cod < 100 || cod > 499) {
                    System.out.println("Código inválido. Debe tener tres cifras (100-499).");
                    continue;
                }
                ProductoItem prod = buscarPorCodigo(catalogo, cod);
                if (prod == null) {
                    System.out.println("No existe un producto con ese código.");
                    continue;
                }
                if (prod.getTipoNum() != tipoNum) {
                    System.out.println("El producto no pertenece al tipo seleccionado.");
                    continue;
                }
                System.out.print("Cantidad (1-20): ");
                int cant = leerEntero();
                if (cant < 1 || cant > 20) {
                    System.out.println("Cantidad inválida. Debe estar entre 1 y 20.");
                    continue;
                }
                addProductoAlCarrito(carrito, prod, cant);
                System.out.println("Producto añadido.");

            } else if (op == 2) {
                verPedido(carrito);

            } else if (op == 3) {
                seguir = false; // volver al menú de tipos

            } else if (op == 4) {
                deseaTerminar = true;
                seguir = false;

            } else {
                System.out.println("Opción inválida.");
            }
        }
        return deseaTerminar;
    }

    /** RESUMEN (aplica IVA 21%) y confirmación. Devuelve true si el cliente confirma y pasa a pago. */
    private boolean faseResumen(List<LineaProductoItem> carrito) {
        System.out.println("\n=== RESUMEN DE COMPRA ===");
        if (carrito.isEmpty()) {
            System.out.println("(Carrito vacío)");
            return false;
        }
        int subtotal = 0;
        for (LineaProductoItem l : carrito) {
            if (l != null) {
                System.out.println(l.aTextoDeLinea());
                subtotal += l.getSubtotalEnCentimos();
            }
        }
        int iva = (int) Math.round(subtotal * 21 / 100.0);
        int total = subtotal + iva;

        System.out.println("Subtotal (sin IVA): " + ProductoItem.formatearCentimos(subtotal));
        System.out.println("IVA (21%):          " + ProductoItem.formatearCentimos(iva));
        System.out.println("TOTAL:              " + ProductoItem.formatearCentimos(total));

        System.out.print("¿Confirmar compra? (S/N): ");
        String r = sc.nextLine().trim().toUpperCase();
        if (r.equals("S")) {
            return true;
        } else {
            System.out.println("Compra cancelada. Volviendo a bienvenida...");
            return false;
        }
    }

    /** Fase PAGO (pago acumulado y cambio con desglose). Tras pagar muestra “Gracias” y retorna. */
    private void fasePago(int totalEnCentimos) {
        System.out.println("\n=== PAGO ===");
        System.out.println("Total a pagar: " + ProductoItem.formatearCentimos(totalEnCentimos));

        int pagado = 0;
        boolean cubierto = false;

        while (!cubierto) {
            System.out.print("Ingrese importe (en euros, admite decimales, ej. 5.00): ");
            String linea = sc.nextLine().trim();

            // convertir a céntimos sin excepciones
            int aporte = convertirEurosTextoACentimos(linea);
            if (aporte < 0) {
                System.out.println("Importe inválido.");
                continue;
            }

            pagado += aporte;

            if (pagado < totalEnCentimos) {
                int falta = totalEnCentimos - pagado;
                System.out.println("Falta: " + ProductoItem.formatearCentimos(falta));
            } else if (pagado == totalEnCentimos) {
                cubierto = true;
                mostrarGracias();
            } else {
                cubierto = true;
                int cambio = pagado - totalEnCentimos;
                desgloseCambio(cambio);
                mostrarGracias();
            }
        }
    }

    /** Conversión robusta de texto en euros ("12.34") a céntimos. Devuelve -1 si no es número. */
    private int convertirEurosTextoACentimos(String txt) {
        // Acepta "12", "12.3", "12.34". Quita espacios.
        String t = txt.replace(",", ".").trim();
        boolean ok = true;

        // Validación simple: solo dígitos y a lo sumo un punto
        int puntos = 0;
        for (int i = 0; i < t.length(); i++) {
            char ch = t.charAt(i);
            if (ch == '.') {
                puntos++;
                if (puntos > 1) {
                    ok = false;
                    break;
                }
            } else if (!Character.isDigit(ch)) {
                ok = false;
                break;
            }
        }
        if (!ok || t.length() == 0) {
            return -1;
        }

        // Separar parte entera y decimal
        String parteEntera = t;
        String parteDec = "0";
        int idx = t.indexOf('.');
        if (idx >= 0) {
            parteEntera = t.substring(0, idx);
            parteDec = t.substring(idx + 1);
        }

        if (parteEntera.length() == 0) parteEntera = "0";
        if (parteDec.length() == 0) parteDec = "0";
        if (parteDec.length() == 1) parteDec = parteDec + "0";
        if (parteDec.length() > 2) parteDec = parteDec.substring(0, 2);

        try {
            int euros = Integer.parseInt(parteEntera);
            int cent = Integer.parseInt(parteDec);
            if (euros < 0 || cent < 0) return -1;
            return euros * 100 + cent;
        } catch (Exception e) {
            return -1;
        }
    }

    /** Desglose óptimo de cambio (greedy) en céntimos. */
    private void desgloseCambio(int cambio) {

        int[] denoms = new int[] { 20000,10000,5000,2000,1000,500,200,100,50,20,10,5,2,1 };
        String[] etiquetas = new String[] {
            "200€","100€","50€","20€","10€","5€","2€","1€","0.50€","0.20€","0.10€","0.05€","0.02€","0.01€"
        };

        for (int i = 0; i < denoms.length; i++) {
            int d = denoms[i];
            int n = cambio / d;
            if (n > 0) {
                System.out.println("  " + etiquetas[i] + " x " + n);
                cambio = cambio % d;
            }
        }
    }

    private void mostrarGracias() {
        System.out.println("\n¡Gracias por su compra!");
        try {
            Thread.sleep(10_000); // 10 segundos
        } catch (InterruptedException e) {
            // ignorar
        }
    }

    /* ========== Orquestador del flujo de cliente ========== */

    /**
     * Ejecuta el flujo completo del cliente:
     * - Bienvenida
     * - Tipos -> Productos (añadir/ver/volver/terminar)
     * - Resumen -> Confirmar -> Pago
     *
     * Recibe un catálogo (lista) de ProductoItem.
     */
    public void ejecutarCliente(List<ProductoItem> catalogo) {
        // Bienvenida
        System.out.println("Bienvenido/a. Pulse ENTER para continuar...");
        sc.nextLine();

        List<LineaProductoItem> carrito = new ArrayList<>();

        boolean quiereTerminar = false;
        while (!quiereTerminar) {
            int tipo = pedirTipo();
            boolean deseaTerminar = faseProductosParaTipo(catalogo, carrito, tipo);

            if (deseaTerminar) {
                if (faseResumen(carrito)) {
                    // calcular total y pasar a pago
                    int subtotal = 0;
                    for (LineaProductoItem l : carrito) {
                        if (l != null) subtotal += l.getSubtotalEnCentimos();
                    }
                    int iva = (int) Math.round(subtotal * 21 / 100.0);
                    int total = subtotal + iva;

                    fasePago(total);
                    // al terminar pago, volvemos a bienvenida (fin de una compra)
                    carrito.clear();
                    // tras volver, se podría repetir todo de nuevo o salir
                    System.out.print("\n¿Desea iniciar una nueva compra? (S/N): ");
                    String r = sc.nextLine().trim().toUpperCase();
                    if (!"S".equals(r)) {
                        quiereTerminar = true;
                    }
                } else {
                    // No confirmó -> volver a bienvenida (carrito se vacía por política tuya si lo deseas)
                    carrito.clear();
                    pausar("Pulse ENTER para volver a la bienvenida...");
                }
            }
            // Si no deseaTerminar, simplemente continúa el bucle y vuelve a elegir tipo
        }
    }
}

