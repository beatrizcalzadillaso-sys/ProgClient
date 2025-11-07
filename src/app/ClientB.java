package app;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class ClientB {

	private static Scanner teclado = new Scanner(System.in);
	private static ArrayList<LineaProductoItem> shopBasket = new ArrayList<>();
	
	
	
	public static void main(String[] args) {
		 		
		ArrayList<ProductoItem> menuElegido = null; // se setea en "Visualizar y escoger"
        boolean siCompro = false;
		
		int opMenu = 0;
				
		do {
			System.out.println("Presione el numero para la opcion deseada");
			opMenu = Integer.parseInt(teclado.nextLine());
						
			while(opMenu!=5) {
				System.out.println(
						 "\n 1- Visualizar y escoger el tipo de producto"
						+ "\n 2 - Confirmar la seleccion de su menu de producto"
						+ "\n 3 - Agregar a la cesta de compra el producto del menu"		 
						+ "\n 4- Visualizar estado de la cesta de compra"
						+ "\n 5- Modificar las cantidades"
						+ "\n 6- Resumen de Compra"
						+ "\n 7- Salir");
				opMenu = Integer.parseInt(teclado.nextLine());
				
				switch(opMenu) { //menu guapo
				case 1: {
						menuElegido= selectMenu();
						siCompro = false;}
				
				case 2: {
						if (menuElegido == null) {
							System.out.println("Debe escoger primero un menu a visualizar");
							break;} 
								
						siCompro = entrarMenuTipo(menuElegido);
						}
				
				case 3: {
						if (menuElegido == null) {
							System.out.println("Debe escoger primero un menu a visualizar"); 
							break;}							
						
						if (!siCompro) {System.out.println("Debe comfirmar su seleccion antes de pasar a agregar productos"); 
							break;}
						
						if (siCompro && menuElegido!=null) {
							agregarCesta(siCompro, menuElegido);
							}
						}
				case 4: if (menuElegido!=null) {
							verPedido();} 
						else {System.out.println("Su cesta de compra esta vacia");}
						break;
				case 5: modificarPedido(); break;
				case 6: resumenCompra(); break;
				case 7: salir(); // poner opMenu = 5 
				}
			}
			
		}while(opMenu!=7);

	}
	//listas de PRUEBA, CAMBIAR POR ArrayList para hacerlas dinamicas, para la hora de integrar con admin
	
		private static ArrayList<ProductoItem> selectMenu(){
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
				
				System.out.println("Elija que tipo de producto desea"
						+ "\n 1- Agua"
						+ "\n 2- Bebida"
						+ "\n 3- Salado"
						+ "\n 4- Dulce");
				
				ArrayList<ProductoItem> menuElegido= new ArrayList<>();
				
				int menuTipo = Integer.parseInt(teclado.nextLine());
				switch(menuTipo) {
					case 1: menuElegido = menu1;break;
					case 2: menuElegido = menu2;break;
					case 3: menuElegido = menu3;break;
					case 4: menuElegido = menu4;break;
				}
				return menuElegido;
		}

	private static boolean entrarMenuTipo(ArrayList<ProductoItem>menuElegido) { 
			for (ProductoItem p : menuElegido) {
			System.out.println(p.aTextoDeCatalogo());}
			System.out.println("\nDesea agregar un producto de este grupo?"
					+ "\n 1- Si"
					+ "\n 2- No");
			boolean siCompro = false;
			int respuesta = Integer.parseInt(teclado.nextLine()); // no tiene excepcion de errores, hay que ingresar si o si 1 ó 2
			
			if (respuesta == 1) {
				siCompro= true;       // TRATAR DE EVITAR QUE DENTRO DE UNA FUNCION NO SE LLAME OTRA FUNCION QUE NO SEA DE VALIDACION O DE COMPROBACION
			}
			return siCompro;
		
		
	
		
	}
	/*
	 * En el estado final deberia ser capaz de recibir cualquier menu y a partir de visualizar, el usuario agregaria a la cesta de compra
	 */
	private static void agregarCesta(boolean confirma, ArrayList<ProductoItem>menuElegido) {
		String idAdd; 
		String cantidadAdd;
		
		boolean idEntero = false;
	    boolean cantidadEntero = false;
		
	    if (confirma) {
			do {
				
			    // se deberia imprimir nuevamente el catalogo del menu seleccionado
				do {
					System.out.println("\nIntroduzca el ID del producto que desea");
					idAdd = teclado.nextLine();
					idEntero = esEntero(idAdd);
					if (!idEntero) {System.out.println("\n Ingrese un valor de tipo entero");
						
						} else {System.out.println("\nEl identificador es un numero entero");}
					} while(!idEntero);
				// idAdd debe existir, mapear el catalogo de productos
			
				do {
					System.out.println("\nIntroduzca la cantidad de producto que desea");
					cantidadAdd = teclado.nextLine();
					cantidadEntero = esEntero(cantidadAdd);
					
					if (!cantidadEntero) {
						System.out.println("\nIngrese un valor de tipo entero \n");
	
					    } else {System.out.println("\nLa cantidad introducida es un numero entero");}
					} while(!cantidadEntero);
				// cantidadAdd debe ser mayor que zero y menor que 20, hay que verificar
				
			} while (!verificado (idAdd, cantidadAdd, menuElegido)); // ERROR repite la funcion verificado tres veces si no existe el id en el menu
			
			
			
			int idAgg = Integer.parseInt(idAdd);
			int cantidadAgg = Integer.parseInt(cantidadAdd);
			ProductoItem prodAgg= ProductoItem.getProductoPorId(menuElegido, idAgg);
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
			    System.out.println("\nSe ha modificado la cantidad en una Línea de Producto");
			} else {
			    // No existe,  agregar nueva linea
			    shopBasket.add(lineaAgg);
			    System.out.println("\nSe ha agregado una nueva Línea de Producto");
			}
		} else {System.out.println("No ha confirmado que desea comprar de este menu ");}
		
	}
	
	/*
	 * Imprime el pedido por consola
	 */
	private static void verPedido() {
		for (LineaProductoItem p : shopBasket) {
		    System.out.println(p.aTextoDeLinea());}
	}
	
	
	/*
	 * Accede por id a los elementos LineaProductoItem para modificar su cantidad
	 */
	private static void modificarPedido() {
		System.out.println("===SU CESTA DE COMPRA===");
		for (LineaProductoItem p : shopBasket) {
		    System.out.println(p.aTextoDeLinea());}
		
		String idChangeText;
		boolean idChangeEntero = false;
		boolean idInShopBasket= false;
		int idChangeInt = 0;
		
		// bucle para verificar que el id esta en shopBasket
		do {
			// bucle para recoger un id entero
			do {
				System.out.println("\nSeleccione el id del producto que desea agregar más unidades");      
				idChangeText= teclado.nextLine();
				idChangeEntero = esEntero(idChangeText);
				if (!idChangeEntero) {
					System.out.println("\nSu id no es valido, procederemos a comenzar su seleccion");
					} else {
							System.out.println("\nSu id es valido, procederemos a comprobar si se encuentra en shopBasket");
							}
				} while(!idChangeEntero);
			// parseo idChangeText a entero
			idChangeInt = Integer.parseInt(idChangeText);
			
			// bucle para verificar que el id existe en el shopBasket	
			for (LineaProductoItem p: shopBasket) {
				if (p.getCodProd() == idChangeInt) {
					idInShopBasket= true;
					System.out.println("\nCorrecto, el id introducido está en shopBasket");
					break;
					}
				}
			System.out.println("\nEl id introducido no existe en shopBasket,procederemos a comenzar el proceso");
		} while(!idInShopBasket);
		
		
		
		String cantidadTexto;
		boolean cantidadIntValida= false;
		boolean cantidadValida= false;
		int cantidadInt=0;
			
		// bucle para verificar que la cantidad es un numero entero y esta entre 1 y 20
		do {
			System.out.println("\nTeclee la nueva cantidad de unidades que desea para ese producto");
			cantidadTexto= teclado.nextLine();
			cantidadIntValida= esEntero(cantidadTexto);
			if (cantidadIntValida) {
				cantidadInt = Integer.parseInt(cantidadTexto);
				if (cantidadInt >= 1 && cantidadInt <= 20) {
					cantidadValida = true;
					System.out.println("\nLa cantidad introducida está dentro de los limites admisibles");
					} else {System.out.println("\nLa cantidad debe estar entre 1 y 20");}
				} else {System.out.println("\nLa cantidad introducida no es un valor entero");}
			} while(!cantidadValida); 
		
		//cambiando la cantidad en la linea de producto en la cesta
		for (LineaProductoItem p: shopBasket) {
			if (p.getCodProd() == idChangeInt) {
				p.setCantidad(cantidadInt);
				System.out.println("\nLa linea de pedido "+p.aTextoDeLinea()+" ha modificado su cantidad");
				break;
			}
		}
		
		
		
		
	}
	
	private static void resumenCompra() { 
		// imprime ademas del pedido, el total a pagar mas el IVA 
		 
		double sumandoPrecio= 0; 
		 
		for (LineaProductoItem p: shopBasket) { 
			sumandoPrecio= sumandoPrecio + p.getSubtotalEnCentimos(); 
		}  
 
		double TotalIVACentimos= sumandoPrecio + (0.21* sumandoPrecio); 
		double Total= sumandoPrecio/100; 
		double TotalIVA= TotalIVACentimos/100; 
		 
		System.out.println("===RESUMEN DE PEDIDO==="); 
		for (LineaProductoItem p : shopBasket) { 
		    System.out.println(p.aTextoDeLinea());} 
		 
		System.out.printf("\nTotal: %.2f €%n", Total); 
		System.out.printf("Total + IVA: %.2f €%n", TotalIVA); 
				 
		System.out.println("Desea proceder con el pago?"
				+ "\n 1-Si"
				+ "\n 2-No. El estado de su carrito de compra permanecera, a menos que salga del programa"); 
		 
		int[] denoms = new int[] {1000,500,200,100,50,20,10,5,2,1 }; 
        String[] etiquetas = new String[] {"10€","5€","2€","1€","0.50€","0.20€","0.10€","0.05€","0.02€","0.01€"}; 
        int[] cantBill = new int[10]; 
        int coin; 
        double pago; 
        double cambio; 
		 
		int respuesta = Integer.parseInt(teclado.nextLine()); 
		if (respuesta == 1) { 
			System.out.println("Ingrese la cantidad con la que va a pagar"); 
			pago = Integer.parseInt(teclado.nextLine()); 
			cambio = (pago*100) - TotalIVACentimos;
			double imprimible = cambio/100;
			for (int i = 0; i<= denoms.length-1 ; i++) { 
				coin = (int)cambio/denoms[i]; 
				cantBill[i] = coin; 
				cambio = cambio%denoms[i];      // ERROR todavia no esta del todo bien por las aproximaciones
				} 
			
			System.out.printf("Su cambio es %.2f €%n", imprimible); 
			for (int i = 0; i<= denoms.length-1 ; i++) { 
				System.out.println("\nLa cantidad de billetes/monedas de "+etiquetas[i]+" es "+cantBill[i]); 
			}
		}
		} 
		 
	
	private static void salir() {
		//termina el programa cliente y regresa al menu de login
		
	}
	
	/*
	 * funcion que verifica que el id introducido este en el menu y la cantidad este en la norma 
	 */
	private static boolean verificado(String idAdd, String cantidadAdd, List<ProductoItem> menuElegido) { // ERROR ESTA MIRANDO SOLAMENTE 
		
		boolean existe = false;
		boolean cantCorrecta= false;
		boolean checked = false;
		int idAddInt = Integer.parseInt(idAdd);
		int cantidadAddInt = Integer.parseInt(cantidadAdd);
		
		for (ProductoItem p: menuElegido) {
			if (p.getCodProd() == idAddInt) {
				existe = true;
				System.out.println("\nSu producto se encuentra en existencias");
				if (cantidadAddInt > 0 && cantidadAddInt <=20) {
					cantCorrecta = true;
					checked= cantCorrecta && existe;
					break;
					}
					else {
						System.out.println("\nLa cantidad introducida no es correcta");
					}
				}
			}
		if (!existe) {
			System.out.println("\nSu producto no está en existencias");;
		}
		
		return checked;
	}

	
	private static boolean esEntero(String texto) {
		boolean esValido= false;
		String sinEspacios= texto.trim();
			try {
				int temp = Integer.parseInt(sinEspacios); 
				esValido= true;
			} catch (NumberFormatException e) {
				System.out.println("\nEste caracter no es un numero entero");
			}
		
		return esValido;
	}
	
}
