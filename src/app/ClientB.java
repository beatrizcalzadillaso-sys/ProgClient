package app;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ClientB {

	private static Scanner teclado = new Scanner(System.in);
	
	public static void main(String[] args) {
		System.out.println("Elija que tipo de producto desea"
				+ "\n 1- Agua"
				+ "\n 2- Bebida"
				+ "\n 3- Salado"
				+ "\n 4- Dulce"
				+ "\n 5- Salir");
		
		LineaProductoItem[] carrito= new LineaProductoItem[50];
		
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
			
			agregarCarrito(menu1, null);
			
			verPedido(null);
			
			modificarPedido(null);
			
			resumenCompra();
			
			salir(); // poner opMenu = 5 
			
		}while(opMenu!=5);

	}

	private static void entrarMenuTipo(int opMenu, List<ProductoItem> menu1) {
		if (opMenu == 1 || opMenu == 2 ||opMenu == 3 ||opMenu == 4) {
			for (ProductoItem p : menu1) {
			    System.out.println(p.aTextoDeCatalogo());}
			System.out.println("Desea agregar un producto de este grupo?"
					+ "\n 1- Si"
					+ "\n 2- No");
			int respuesta = Integer.parseInt(teclado.nextLine());
			
			if (respuesta == 1) {
				agregarCarrito(menu1, null);
			} else {/* regresar al menu*/ }
		} else {}
		
		
	
		
	}
	
	private static void agregarCarrito(List<ProductoItem> menu1, List<LineaProductoItem> carrito) {
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
					
					}
				} while(!idEntero);
			// idAdd debe existir, mapear el catalogo de productos
		
			do {
				System.out.println("Introduzca la cantidad de producto que desea");
				cantidadAdd = teclado.nextLine();
				cantidadEntero = esEntero(cantidadAdd);
				
				if (!cantidadEntero) {System.out.println("Ingrese un valor de tipo entero \n");

				    }
				} while(!cantidadEntero);
			// cantidadAdd debe ser mayor que zero y menor que 20, hay que verificar
			
		} while (!verificado (idAdd, cantidadAdd, menu1));
		
		//error java.lang.Null.PointerException pq carrito esta vacio
		
		int idAgg = Integer.parseInt(idAdd);
		int cantidadAgg = Integer.parseInt(cantidadAdd);
		ProductoItem prodAgg= ProductoItem.getProductoPorId(menu1, idAgg);
		LineaProductoItem lineaAgg = new LineaProductoItem(prodAgg, cantidadAgg);
		
		//error al empezar el for, no está viendo el carrito que se inicializo con 50 elementos al principio
		for (int i = 0; i>= carrito.size(); i++) { 
            if (carrito.get(i)== null) {//si esta vacio, se agrega el nuevo elemento de LineaProdItem
            	//si se quiere agregar el mismo producto, se debe verificar que existe y sumar las cantidades
            	carrito.set(i, lineaAgg);
            	System.out.println("Se ha agregado una nueva Linea de Producto");
                break;
            } else if (carrito.get(i)!=null && carrito.get(i).getCodProd() == idAgg) {
            	//1ro si ya existe se suman las cantidades en LineaProdItem, 2do si no existia ese producto se añade como nuevo 
            	carrito.get(i).agregarCantidadConTope(cantidadAgg);
                System.out.println("Se ha modificado la cantidad en una Linea de Producto");
                break;
            	}
            
        }
		
	}
	
	private static void verPedido(List<LineaProductoItem>carrito) {
		for (LineaProductoItem p : carrito) {
		    System.out.println(p.aTextoDeLinea());}
	}
	
	private static void modificarPedido(List<LineaProductoItem>carrito) {
		//accede a la linea de pedido por el ID del producto y modifica su cantidad
		System.out.println("===SU CARRITO DE COMPRA===");
		for (LineaProductoItem p : carrito) {
		    System.out.println(p.aTextoDeLinea());}
		
		
		
	}
	
	private static void resumenCompra() {
		// imprime ademas del pedido, el total a pagar mas el IVA
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
		System.out.println("Su producto se encuentra en existencias y será añadido a su carrito de compra");
		return existe;
	}

	
	private static boolean esEntero(String texto) {
		boolean esValido= true;
		String sinEspacios= texto.trim();
		if (texto == null || sinEspacios.isBlank()) { //faltaria validar que no se introduzca texto, pero estan las validaciones del mapeo por codigo de producto y las cantidades maximas y minimas
			esValido = false;
		} else {
			try {
				Integer.parseInt(texto.trim());
			} catch (NumberFormatException e) {
				esValido= true;
			}
		}
		return esValido;
	}
	
}
