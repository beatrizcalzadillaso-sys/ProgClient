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
		
		int opMenu = 0;
				
		do {
			System.out.println("Presione el numero para la opcion deseada");
			opMenu = Integer.parseInt(teclado.nextLine());
			
			entrarMenuTipo(opMenu);
			
			agregarCarrito();
			
			verPedido();
			
			modificarPedido();
			
			resumenCompra();
			
			salir(); // poner opMenu = 5 
			
		}while(opMenu!=5);

	}

	private static void entrarMenuTipo(int opMenu) {
		if (opMenu == 1 || opMenu == 2 ||opMenu == 3 ||opMenu == 4) {
			//Imprimir productos tipo 1/2/3/4
			System.out.println("Desea agregar un producto de este grupo?"
					+ "\n 1- Si"
					+ "\n 2- No");
			int respuesta = Integer.parseInt(teclado.nextLine());
			
			if (respuesta == 1) {
				agregarCarrito();
			} else {/* regresar al menu*/ }
		} else {}
		
		
	}
	
	private static void agregarCarrito() {
		do {
			do {
				System.out.println("Introduzca el ID del producto que desea");
				String idAdd = teclado.nextLine();
				boolean idEntero = esEntero(idAdd);
				if (!idEntero) {System.out.println("Ingrese un valor de tipo enetero");}
				} while(!idEntero);
			// idAdd debe existir, mapear el catalogo de productos
		
			do {
				System.out.println("Introduzca la cantidad de producto que desea");
				String cantidadAdd = teclado.next();
				boolean cantidadEntero = esEntero(cantidadAdd);
				
				if (!cantidadEntero) {System.out.println("Ingrese un valor de tipo enetero");}
				} while(!cantidadEntero);
			// cantidadAdd debe ser mayor que zero y menor que 20, hay que verificar
			
		} while (!verificado());
		
	}
	
	private static void verPedido() {}
	
	private static void modificarPedido() {}
	
	private static void resumenCompra() {}
	
	private static void salir() {}
	
	private static boolean verificado(int idAdd, int cantidadAdd) {
		
		
		if () {}
	}
	
	private static boolean esEntero(String texto) {
		boolean esValido= true;
		if (texto == null || texto.trim().isBlank()) {
			esValido = false;
		} else {
			try {
				Integer.parseInt(texto.trim());
			} catch (NumberFormatException e) {
				esValido = false;
			}
		}
		return esValido;
	}
	
}
