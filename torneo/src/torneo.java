import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class torneo {	
	//funci�n para saber si un valor es num�rico
	public static boolean isNumeric(String cadena) {
        boolean resultado;
        try {
            Integer.parseInt(cadena);
            resultado = true;
        } catch (NumberFormatException excepcion) {
            resultado = false;
        }
        return resultado;
    }
	
	public static void main(String[] args) {
		int n = 0; //variable para coger el n�mero de jugadores
		encuentros e = new encuentros(); //Objetos de la clase encuentros para hacer las funciones
		
		//si no hay parametros
		if(args.length == 0){
            System.out.println("Debe introducir un valor");
            System.exit(0);
        }
		
		//si solo hay un parametro
		else if(args.length == 1){
			//si el valor es -t
			String valor = args[0];
			if (valor.equals("-t")) {
	            System.out.println("Debe introducir un n�mero de jugadores tambi�n");
	            System.exit(0);			
			}
			//si el valor es -h
			else if(valor.equals("-h")) {
	        	System.out.println("SINTAXIS: torneo [-t][-h] n [fichero entrada]\n"
	        			+ "-t Traza la parametrizaci�n de cada invocaci�n recursiva\n"
	        			+ "-h Muestra esta ayuda\n"
	        			+ "n N�mero de jugadores\n"
	        			+ "[fichero entrada] Listado de los nombres de los jugadores del torneo (.txt y jugador por l�nea)");	
	            System.exit(0);			
			}
			else {
				//comprobar si existe fichero en la ruta actual
				File archivo = new File(valor);
				if (archivo.exists()) {
				    try {
				       String tipodeArchivo = Files.probeContentType(archivo.toPath());
					   if(tipodeArchivo.equals("text/plain")) {
						   e.leeArchivo(archivo);
							do {
								//tiene que haber m�s de un jugador
								if(e.lineas <= 1) {
									System.out.println("El valor debe ser mayor a 1\n\n");
					            	System.exit(0);
								}
								else if (e.lineas > e.maxJugadores) {
									System.out.println("El valor excede el tama�o m�ximo permitido. Ingrese un n�mero no mayor a "+e.maxJugadores);
					            	System.exit(0);
								}
							}
							while(e.lineas <= 1 || e.lineas > e.maxJugadores);

						    //creamos la tabla de encuentros
							e.tablaTorneo(e.lineas);

							//y la dibujamos
					        e.imprimirTablaConNombres(e.lineas, e.nomJugadores);
					   }
				    }
				    catch (IOException ioException)	{
				        System.out.println("Error: " + ioException.getMessage());
			            System.exit(0);
				    }
				}
				//si el valor no es num�rico (n�mero de jugadores)
				else if (isNumeric(valor) != true) {
		            System.out.println("Argumento no v�lido");
		            System.exit(0);
				}
				//si es num�rico
				else {
			        n = Integer.parseInt(valor);
					do {
						//tiene que haber m�s de un jugador
						if(n <= 1) {
							System.out.println("El valor debe ser mayor a 1\n\n");
			            	System.exit(0);
						}
						else if (n > e.maxJugadores) {
							System.out.println("El valor excede el tama�o m�ximo permitido. Ingrese un n�mero no mayor a "+e.maxJugadores);
			            	System.exit(0);
						}
					}
					while(n <= 1 || n > e.maxJugadores);

				    //creamos la tabla de encuentros
					e.tablaTorneo(n);

					//y la dibujamos
			        e.imprimirTabla(n);
				}
			}
		}
		
		//si solo hay dos parametros
		else if(args.length == 2){
			//si el valor es -t
			String valor1 = args[0];
			String valor2 = args[1];
			//comprobar si existe fichero en la ruta actual
			File archivo = new File(valor2);
			if (valor1.equals("-t") && archivo.exists()) {
				try {
			       String tipodeArchivo = Files.probeContentType(archivo.toPath());
				   if(tipodeArchivo.equals("text/plain")) {
					   e.leeArchivo(archivo);
						do {
							//tiene que haber m�s de un jugador
							if(e.lineas <= 1) {
								System.out.println("El valor debe ser mayor a 1\n\n");
				            	System.exit(0);
							}
							else if (e.lineas > e.maxJugadores) {
								System.out.println("El valor excede el tama�o m�ximo permitido. Ingrese un n�mero no mayor a "+e.maxJugadores);
				            	System.exit(0);
							}
						}
						while(e.lineas <= 1 || e.lineas > e.maxJugadores);

					    //creamos la traza de encuentros
						e.trazaTorneoNombres(e.lineas, e.nomJugadores);

				   }
			    }
			    catch (IOException ioException)	{
			        System.out.println("Error: " + ioException.getMessage());
		            System.exit(0);
			    }
			}
			
			else if(valor1.equals("-t") && isNumeric(valor2) != true) {
	            System.out.println("Argumentos no v�lidos");
	            System.exit(0);						
			}
			
			else if(valor1.equals("-t") && isNumeric(valor2) == true) {
				n = Integer.parseInt(valor2);
				do {
					//tiene que haber m�s de un jugador
					if(n <= 1) {
						System.out.println("El valor debe ser mayor a 1\n\n");
		            	System.exit(0);
					}
					else if (n > e.maxJugadores) {
						System.out.println("El valor excede el tama�o m�ximo permitido. Ingrese un n�mero no mayor a "+e.maxJugadores);
		            	System.exit(0);
					}
				}
				while(n <= 1 || n > e.maxJugadores);

			    //creamos la traza de encuentros
				e.trazaTorneo(n);
				
			}
			
			else  {
	            System.out.println("Argumentos no v�lidos");
	            System.exit(0);		
			}
		}
		
		//si hay m�s de dos par�metro
		else{
            System.out.println("N�mero de par�metros inv�lido");
            System.exit(0);
        }
				
        System.out.println();
	    System.exit(0); 
	}
}
