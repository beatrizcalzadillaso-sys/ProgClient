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
		
		
		int opMenu = 0;
				
		do {
			System.out.println("Presione el numero para la opcion deseada");
			opMenu = Integer.parseInt(teclado.nextLine());
			
			entrarMenuTipo(opMenu, menu1);
			
			agregarCarrito(menu1);
			
			verPedido();
			
			modificarPedido();
			
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
				agregarCarrito(menu1);
			} else {/* regresar al menu*/ }
		} else {}
		
		
	
		
	}
	
	private static void agregarCarrito(List<ProductoItem> menu1) {
		String idAdd; 
		String cantidadAdd;
		
		boolean idEntero = false;
	    boolean cantidadEntero = false;
		
		do {
			
		    
			do {
				System.out.println("Introduzca el ID del producto que desea");
				idAdd = teclado.nextLine();
				idEntero = esEntero(idAdd);
				if (!idEntero) {System.out.println("Ingrese un valor de tipo entero");
					//idAdd = null;
					//idEntero = false;
					}
				} while(!idEntero);
			// idAdd debe existir, mapear el catalogo de productos
		
			do {
				System.out.println("Introduzca la cantidad de producto que desea");
				cantidadAdd = teclado.nextLine();
				cantidadEntero = esEntero(cantidadAdd);
				
				if (!cantidadEntero) {System.out.println("Ingrese un valor de tipo entero \n");
				   // cantidadEntero = false;
				   // cantidadAdd = null;
				    }
				} while(!cantidadEntero);
			// cantidadAdd debe ser mayor que zero y menor que 20, hay que verificar
			
		} while (!verificado (idAdd, cantidadAdd, menu1));
		
	}
	
	private static void verPedido() {}
	
	private static void modificarPedido() {}
	
	private static void resumenCompra() {}
	
	private static void salir() {}
	
	
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
		if (texto == null || sinEspacios.isBlank()) {
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
