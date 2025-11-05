package app;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ClientB {

	private static Scanner teclado = new Scanner(System.in);
	private static ArrayList<LineaProductoItem> shopBasket = new ArrayList<>();
	
	public static void main(String[] args) {
		System.out.println("Elija que tipo de producto desea"
				+ "\n 1- Agua"
				+ "\n 2- Bebida"
				+ "\n 3- Salado"
				+ "\n 4- Dulce"
				+ "\n 5- Salir");
		
		
		
		//listas de PRUEBA, CAMBIAR POR ArrayList para hacerlas dinamicas, para la hora de integrar con admin
		
		ArrayList<ProductoItem> menu1 = new ArrayList<>();
		menu1.add(new ProductoItem(112, 1, "Agua natural", 1.44));
		menu1.add(new ProductoItem(123, 1, "Agua con gas", 1.26));
		menu1.add(new ProductoItem(134, 1, "Zumo de naranja", 1.17));
		menu1.add(new ProductoItem(145, 1, "Zumo de tomate", 1.35));
		
		ArrayList<ProductoItem> menu2 = new ArrayList<>();
		menu2.add(new ProductoItem(212, 2, "Agua natural", 1.44));
		menu2.add(new ProductoItem(223, 2, "Agua con gas", 1.26));
		menu2.add(new ProductoItem(234, 2, "Zumo de naranja", 1.17));
		menu2.add(new ProductoItem(245, 2, "Zumo de tomate", 1.35));
		
		ArrayList<ProductoItem> menu3 = new ArrayList<>();
		menu3.add(new ProductoItem(312, 3, "Agua natural", 1.44));
		menu3.add(new ProductoItem(323, 3, "Agua con gas", 1.26));
		menu3.add(new ProductoItem(334, 3, "Zumo de naranja", 1.17));
		menu3.add(new ProductoItem(345, 3, "Zumo de tomate", 1.35));
		
		ArrayList<ProductoItem> menu4 = new ArrayList<>();
		menu4.add(new ProductoItem(412, 4, "Agua natural", 1.44));
		menu4.add(new ProductoItem(423, 4, "Agua con gas", 1.26));
		menu4.add(new ProductoItem(434, 4, "Zumo de naranja", 1.17));
		menu4.add(new ProductoItem(445, 4, "Zumo de tomate", 1.35));
		
		
		
		
		int opMenu = 0;
				
		do {
			System.out.println("Presione el numero para la opcion deseada");
			opMenu = Integer.parseInt(teclado.nextLine());
			
			entrarMenuTipo(opMenu, menu1);
			
			while(opMenu!=5) {
				System.out.println("Menu guapo");
				opMenu = Integer.parseInt(teclado.nextLine());
				
				switch(opMenu) {
				case 1: agregarCesta(menu1, shopBasket); break;
				case 2: verPedido(); break;
				case 3: modificarPedido(null); break;
				case 4: resumenCompra(); break;
				case 5: salir(); // poner opMenu = 5 
				}
			}
			
		}while(opMenu!=5);

	}

	private static void entrarMenuTipo(int opMenu, List<ProductoItem> menu1) { // arreglar los argumentos que recibe, valorar hacer entrarMenu de cada tipo
		if (opMenu == 1 || opMenu == 2 ||opMenu == 3 ||opMenu == 4) {
			for (ProductoItem p : menu1) {
			    System.out.println(p.aTextoDeCatalogo());}
			System.out.println("Desea agregar un producto de este grupo?"
					+ "\n 1- Si"
					+ "\n 2- No");
			int respuesta = Integer.parseInt(teclado.nextLine());
			
			if (respuesta == 1) {
				agregarCesta(menu1, shopBasket);       // TRATAR DE EVITAR QUE DENTRO DE UNA FUNCION NO SE LLAME OTRA FUNCION QUE NO SEA DE VALIDACION O DE COMPROBACION
			} else {/* regresar al menu*/ }
		} else {}
		
		
	
		
	}
	
	private static void agregarCesta(List<ProductoItem> menu1, ArrayList<LineaProductoItem> shopBasket) {
		String idAdd; 
		String cantidadAdd;
		
		boolean idEntero = false;
	    boolean cantidadEntero = false;
		
		do {
			
		    // se deberia imprimir nuevamente el catalogo del menu seleccionado
			do {
				System.out.println("Introduzca el ID del producto que desea");
				idAdd = teclado.nextLine();
				idEntero = esEntero(idAdd);
				if (!idEntero) {System.out.println("Ingrese un valor de tipo entero");
					
					} else {System.out.println("El identificador es un numero entero");}
				} while(!idEntero);
			// idAdd debe existir, mapear el catalogo de productos
		
			do {
				System.out.println("Introduzca la cantidad de producto que desea");
				cantidadAdd = teclado.nextLine();
				cantidadEntero = esEntero(cantidadAdd);
				
				if (!cantidadEntero) {System.out.println("Ingrese un valor de tipo entero \n");

				    } else {System.out.println("La cantidad introducida es un numero entero");}
				} while(!cantidadEntero);
			// cantidadAdd debe ser mayor que zero y menor que 20, hay que verificar
			
		} while (!verificado (idAdd, cantidadAdd, menu1));
		
		//error java.lang.Null.PointerException pq Cesta esta vacio
		
		int idAgg = Integer.parseInt(idAdd);
		int cantidadAgg = Integer.parseInt(cantidadAdd);
		ProductoItem prodAgg= ProductoItem.getProductoPorId(menu1, idAgg);
		LineaProductoItem lineaAgg = new LineaProductoItem(prodAgg, cantidadAgg);
		
				
		// Buscar si ya existe un LineaProductoItem con ese ID
		int index = -1;
		for (int i = 0; i < shopBasket.size(); i++) {
		    if (shopBasket.get(i).getCodProd() == idAgg) {
		        index = i;
		        break;
		    }
		}

		if (index >= 0) {
		    // si ya existe, suma cantidad con tope
		    LineaProductoItem lp = shopBasket.get(index);
		    lp.agregarCantidadConTope(cantidadAgg);
		    System.out.println("Se ha modificado la cantidad en una Línea de Producto");
		} else {
		    // No existe,  agregar nueva linea
		    shopBasket.add(lineaAgg);
		    System.out.println("Se ha agregado una nueva Línea de Producto");
		}

		
	}
	
	private static void verPedido() {
		for (LineaProductoItem p : shopBasket) {
		    System.out.println(p.aTextoDeLinea());}
	}
	
	private static void modificarPedido(ArrayList<LineaProductoItem>shopBasket) {
		//accede a la linea de pedido por el ID del producto y modifica su cantidad
		System.out.println("===SU Cesta DE COMPRA===");
		for (LineaProductoItem p : shopBasket) {
		    System.out.println(p.aTextoDeLinea());}
		
		
		
		
	}
	
	private static void resumenCompra() {
		// imprime ademas del pedido, el total a pagar mas el IVA
		
		int sumandoPrecio= 0;
		
		for (LineaProductoItem p: shopBasket) {
			sumandoPrecio= sumandoPrecio + p.getSubtotalEnCentimos();
		} 
		
		int Total = sumandoPrecio;
		int TotalIVA= Total + (21/100)* Total;
		
		System.out.println("===RESUMEN DE PEDIDO===");
		for (LineaProductoItem p : shopBasket) {
		    System.out.println(p.aTextoDeLinea());}
		System.out.println("Total: "+Total);
		System.out.println("Total + IVA: "+TotalIVA);
				
		
	}
	
	private static void salir() {
		//termina el programa cliente y regresa al menu de login
		
	}
	
	
	private static boolean verificado(String idAdd, String cantidadAdd, List<ProductoItem> menu1) {
		
		boolean existe = false;
		int idAddInt = Integer.parseInt(idAdd);
		int cantidadAddInt = Integer.parseInt(cantidadAdd);
		
		for (ProductoItem p: menu1) {
			if (p.getCodProd() == idAddInt && cantidadAddInt > 0 && cantidadAddInt <=20) {
				existe = true;
				break;
			} else {System.out.println("El id de producto no existe o está introduciendo una cantidad incompatible");}
		} 
		System.out.println("Su producto se encuentra en existencias y será añadido a su Cesta de compra");
		return existe;
	}

	
	private static boolean esEntero(String texto) {
		boolean esValido= true;
		String sinEspacios= texto.trim();
		if (texto == null || sinEspacios.isBlank()) { //faltaria validar que no se introduzca texto, pero estan las validaciones del mapeo por codigo de producto y las cantidades maximas y minimas
			esValido = false;
		} else {
			try {
				Integer.parseInt(texto.trim()); //ver, optimizar para no tener que utilizar el try-catch
			} catch (NumberFormatException e) {
				esValido= true;
			}
		}
		return esValido;
	}
	
}
