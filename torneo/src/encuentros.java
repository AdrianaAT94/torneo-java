import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

class encuentros {

	int maxJugadores = 30; //número máximo de jugadores permitidos
	int encuentros[][] = new int[maxJugadores][maxJugadores]; //matriz de dos dimensiones para jugadores y dias		
	int lineas = 0; //lineas que se leen del archivo en caso de haberlo
	String nomJugadores[] = new String[lineas]; //array de los nombres del archivo en caso de haberlo
	
	//función para saber si un número es potencia de dos
	public static boolean potenciaDos (int n) 	{
		int potencia = 2;
		while(potencia <= n) {
			if (n == potencia)
				return true;
			potencia *= 2;
		}
		return false;
	}
	//función para leer archivo
	public void leeArchivo (File archivo) throws FileNotFoundException, IOException {
		String cadena;
		int i=0;
        FileReader f = new FileReader(archivo);
        BufferedReader b = new BufferedReader(f);
        //primero contamos las lineas
        while((cadena = b.readLine())!=null) {
        	lineas = lineas+1;
        }
        b.close();
        //volvemos a leer el archivo
        FileReader f2 = new FileReader(archivo);
        BufferedReader b2 = new BufferedReader(f2);
        //metemos los nombres en un array
        nomJugadores = new String[lineas];	
		while((cadena = b2.readLine())!=null) {	
			 nomJugadores[i] = cadena;
			 i++;
        }
        b2.close();
	}
		
	//función que crea la tabla de encuentros a partir del número de jugadores
	public void tablaTorneo (int n) {
		int jugador, dia;
		//si solo hay 2 jugadores solo se jugará un día
		if (n==2) {
			encuentros[0][0]=2;
			encuentros[1][0]=1;
		}
		//si es potencia de dos
		else if (potenciaDos(n)) {
			//iremos dividiendo hasta llegar a solo dos jugadores
			tablaTorneo(n/2);
			//cuando ya los tenemos, llenamos el cuadrante superior derecho
			for (jugador=0; jugador<n/2; jugador++) {
				for (dia=n/2-1; dia<n-1; dia++) {
					encuentros[jugador][dia] = jugador+1 + dia+1;
					if (encuentros[jugador][dia] > n)
						encuentros[jugador][dia] = encuentros[jugador][dia] - n/2;
				}
			}
			//llenamos el cuadrante inferior derecho
			for (jugador=n/2; jugador<n; jugador++) {
				for (dia=n/2-1; dia<n-1; dia++) {
					encuentros[jugador][dia] = jugador+1 - (dia+1);
					if (encuentros[jugador][dia] <= 0)
						encuentros[jugador][dia] = encuentros[jugador][dia] + n/2;
				}
			}
			//llenamos el cuadrante inferior izquierdo 
			for (jugador=n/2; jugador<n; jugador++) {
				for (dia=0; dia < n/2 - 1; dia++) {
					encuentros[jugador][dia] = encuentros[jugador-n/2][dia] + n/2;
				}
			}
		}
		//si no es potencia de 2 pero el número de jugadores es par
		else if (n%2 == 0) {
			//iremos dividiendo hasta llegar a solo dos jugadores
			tablaTorneo(n/2);
			//cuando ya los tenemos, llenamos el cuadrante superior derecho
			for (jugador=0; jugador<n/2; jugador++) {
				for (dia=n/2; dia<n-1; dia++) {
					encuentros[jugador][dia] = jugador+1 + dia+1;
					if (encuentros[jugador][dia] > n)
						encuentros[jugador][dia] = encuentros[jugador][dia] - n/2;
				}
			}
			//llenamos el cuadrante inferior derecho
			for (jugador=n/2; jugador<n; jugador++) {
				for (dia=n/2; dia<n-1; dia++) {
					encuentros[jugador][dia] = jugador+1 - (dia+1);
					if (encuentros[jugador][dia] <= 0)
						encuentros[jugador][dia] = encuentros[jugador][dia] + n/2;
				}
			}
			//llenamos el cuadrante inferior izquierdo 
			for (jugador=n/2; jugador<n; jugador++) {
				for (dia=0; dia<n/2; dia++) {
					if (encuentros[jugador-n/2][dia] != 0)
						encuentros[jugador][dia] = encuentros[jugador - n/2][dia] + n/2;
				}
			}
			//llenamos el cuadrante superior izquierdo reemplazando los valores 0
			for (jugador=0; jugador<n/2; jugador++) {
				for (dia=0; dia<n/2; dia++) {
					if (encuentros[jugador][dia] == 0)
						encuentros[jugador][dia] = jugador+1 + n/2 ;
				}
			}
			//llenamos el cuadrante inferior izquierdo reemplazando los valores 0
			for (jugador=n/2; jugador<n; jugador++) {
				for (dia=0; dia<n/2; dia++) {
					if (encuentros[jugador][dia] == 0)
						encuentros[jugador][dia] = jugador+1 - n/2;
				}
			}
		}
		//si el número de jugadores es impar
		else {
			//si n es impar, le sumamos 1 para volverlo par y que en la llamada recursiva podamos llegar a 2 jugadores
			tablaTorneo(n+1);
			//se eliminan los valores que sobran por llamar a la función con n+1 que serán los días de descanso
			for (jugador=0; jugador<n; jugador++) {
				for (dia=0; dia<n; dia++) {
					if (encuentros[jugador][dia]==n+1)
						encuentros[jugador][dia] = 0;
				}
			}
		}
	}
	
	//función para sacar la tabla por pantalla con número
	public void imprimirTabla(int n)  {
		int dias, i, j, sdias, sjugador;
		//si n es par la cantidad de dias es n-1, si es impar la cantidad de dias es n y hay día de descanso para los jugadores
		if(n%2 == 0)
			dias = n-1;
		else
			dias = n;

		System.out.println("\nTABLA DE ENCUENTROS DEL TORNEO DE TENIS PARA "+n+" JUGADORES EN "+dias+" DIAS:\n");

		System.out.print("  ");
		for(i=0; i<dias; i++) {
			sdias = i+1;
			if(i+1 < 10)
				System.out.print("   d"+sdias+" ");
			else
				System.out.print("  d"+sdias+"  ");
		}

	    //imprimimos los valores
		if(n%2 != 0) {
			for(i=0; i<dias; i++) {
				sjugador = i+1;
				if(i+1 < 10)
					System.out.print("\nJ"+sjugador);
				else
					System.out.print("\nJ"+sjugador);

				for(j=0; j<dias; j++) {
					if(encuentros[i][j] == 0)
						System.out.print(" |  - ");
					else if(encuentros[i][j] < 10)
						System.out.print(" |  "+encuentros[i][j]+" ");
					else
						System.out.print(" |  "+encuentros[i][j]+"  ");
				}
				System.out.print(" | ");
			}
		}
		else {
			for(i=0; i<dias+1; i++) {
				sjugador = i+1;
				if(i+1 < 10)
					System.out.print("\nJ"+sjugador);
				else
					System.out.print("\nJ"+sjugador);
				for(j=0; j<dias; j++) {
					if(encuentros[i][j] == 0)
						System.out.print(" |  - ");
					else if(encuentros[i][j] < 10)
						System.out.print(" |  "+encuentros[i][j]+" ");
					else
						System.out.print(" |  "+encuentros[i][j]+"  ");
				}
				System.out.print(" | ");
			}
		}
	}

	//función para sacar la tabla por pantalla con nombres
	public void imprimirTablaConNombres(int n, String[] nombres)  {
		int dias, i, j, sdias;
		//si n es par la cantidad de dias es n-1, si es impar la cantidad de dias es n y hay día de descanso para los jugadores
		if(n%2 == 0)
			dias = n-1;
		else
			dias = n;

		System.out.println("\nTABLA DE ENCUENTROS DEL TORNEO DE TENIS PARA "+n+" JUGADORES EN "+dias+" DIAS:\n");
		
		System.out.print("  ");
		for(i=0; i<dias; i++) {
			sdias = i+1;
			if(i+1 < 10)
				System.out.print("      d"+sdias+"  ");
			else
				System.out.print("     d"+sdias+"   ");
		}

	    //imprimimos los valores
		if(n%2 != 0) {
			for(i=0; i<dias; i++) {
				if(i+1 < 10)
					System.out.print("\n"+nombres[i]);
				else
					System.out.print("\n"+nombres[i]);

				for(j=0; j<dias; j++) {
					if(encuentros[i][j] == 0)
						System.out.print(" |  - ");
					else if(encuentros[i][j] < 10)
						System.out.print(" |  "+nombres[encuentros[i][j]-1]+" ");
					else
						System.out.print(" |  "+nombres[encuentros[i][j]-1]+"  ");
				}
				System.out.print(" | ");
			}
		}
		else {
			for(i=0; i<dias+1; i++) {
				if(i+1 < 10)
					System.out.print("\n"+nombres[i]);
				else
					System.out.print("\n"+nombres[i]);
				for(j=0; j<dias; j++) {
					if(encuentros[i][j] == 0)
						System.out.print(" |  - ");
					else if(encuentros[i][j] < 10)
						System.out.print(" |  "+nombres[encuentros[i][j]-1]+" ");
					else
						System.out.print(" |  "+nombres[encuentros[i][j]-1]+"  ");
				}
				System.out.print(" | ");
			}
		}
	}
	
	//función que crea la tabla de encuentros a partir del número de jugadores y muestra la traza
	public void trazaTorneo (int n) {
		int jugador, dia;
		//si solo hay 2 jugadores solo se jugará un día
		if (n==2) {
			encuentros[0][0]=2;
			encuentros[1][0]=1;
		}
		//si es potencia de dos
		else if (potenciaDos(n)) {
			//iremos dividiendo hasta llegar a solo dos jugadores
			trazaTorneo(n/2);
			//cuando ya los tenemos, llenamos el cuadrante superior derecho
			for (jugador=0; jugador<n/2; jugador++) {
				for (dia=n/2-1; dia<n-1; dia++) {
					encuentros[jugador][dia] = jugador+1 + dia+1;
					if (encuentros[jugador][dia] > n)
						encuentros[jugador][dia] = encuentros[jugador][dia] - n/2;
				}
			}
			//llenamos el cuadrante inferior derecho
			for (jugador=n/2; jugador<n; jugador++) {
				for (dia=n/2-1; dia<n-1; dia++) {
					encuentros[jugador][dia] = jugador+1 - (dia+1);
					if (encuentros[jugador][dia] <= 0)
						encuentros[jugador][dia] = encuentros[jugador][dia] + n/2;
				}
			}
			//llenamos el cuadrante inferior izquierdo 
			for (jugador=n/2; jugador<n; jugador++) {
				for (dia=0; dia < n/2 - 1; dia++) {
					encuentros[jugador][dia] = encuentros[jugador-n/2][dia] + n/2;
				}
			}
		}
		//si no es potencia de 2 pero el número de jugadores es par
		else if (n%2 == 0) {
			//iremos dividiendo hasta llegar a solo dos jugadores
			trazaTorneo(n/2);
			//cuando ya los tenemos, llenamos el cuadrante superior derecho
			for (jugador=0; jugador<n/2; jugador++) {
				for (dia=n/2; dia<n-1; dia++) {
					encuentros[jugador][dia] = jugador+1 + dia+1;
					if (encuentros[jugador][dia] > n)
						encuentros[jugador][dia] = encuentros[jugador][dia] - n/2;
				}
			}
			//llenamos el cuadrante inferior derecho
			for (jugador=n/2; jugador<n; jugador++) {
				for (dia=n/2; dia<n-1; dia++) {
					encuentros[jugador][dia] = jugador+1 - (dia+1);
					if (encuentros[jugador][dia] <= 0)
						encuentros[jugador][dia] = encuentros[jugador][dia] + n/2;
					}
			}
			//llenamos el cuadrante inferior izquierdo 
			for (jugador=n/2; jugador<n; jugador++) {
				for (dia=0; dia<n/2; dia++) {
					if (encuentros[jugador-n/2][dia] != 0)
						encuentros[jugador][dia] = encuentros[jugador - n/2][dia] + n/2;
				}
			}
			//llenamos el cuadrante superior izquierdo reemplazando los valores 0
			for (jugador=0; jugador<n/2; jugador++) {
				for (dia=0; dia<n/2; dia++) {
					if (encuentros[jugador][dia] == 0)
						encuentros[jugador][dia] = jugador+1 + n/2 ;
				}
			}
			//llenamos el cuadrante inferior izquierdo reemplazando los valores 0
			for (jugador=n/2; jugador<n; jugador++) {
				for (dia=0; dia<n/2; dia++) {
					if (encuentros[jugador][dia] == 0)
						encuentros[jugador][dia] = jugador+1 - n/2;
				}
			}
		}
		//si el número de jugadores es impar
		else {
			//si n es impar, le sumamos 1 para volverlo par y que en la llamada recursiva podamos llegar a 2 jugadores
			trazaTorneo(n+1);
			//se eliminan los valores que sobran por llamar a la función con n+1 que serán los días de descanso
			for (jugador=0; jugador<n; jugador++) {
				for (dia=0; dia<n; dia++) {
					if (encuentros[jugador][dia]==n+1)
						encuentros[jugador][dia] = 0;
				}
			}
		}
		imprimirTabla(n);		
	}	
	
	//función que crea la tabla de encuentros a partir del número de jugadores y muestra la traza
	public void trazaTorneoNombres (int n, String[] nombres) {
		int jugador, dia;
		//si solo hay 2 jugadores solo se jugará un día
		if (n==2) {
			encuentros[0][0]=2;
			encuentros[1][0]=1;
		}
		//si es potencia de dos
		else if (potenciaDos(n)) {
			//iremos dividiendo hasta llegar a solo dos jugadores
			trazaTorneoNombres(n/2, nombres);
			//cuando ya los tenemos, llenamos el cuadrante superior derecho
			for (jugador=0; jugador<n/2; jugador++) {
				for (dia=n/2-1; dia<n-1; dia++) {
					encuentros[jugador][dia] = jugador+1 + dia+1;
					if (encuentros[jugador][dia] > n)
						encuentros[jugador][dia] = encuentros[jugador][dia] - n/2;
				}
			}
			//llenamos el cuadrante inferior derecho
			for (jugador=n/2; jugador<n; jugador++) {
				for (dia=n/2-1; dia<n-1; dia++) {
					encuentros[jugador][dia] = jugador+1 - (dia+1);
					if (encuentros[jugador][dia] <= 0)
						encuentros[jugador][dia] = encuentros[jugador][dia] + n/2;
				}
			}
			//llenamos el cuadrante inferior izquierdo 
			for (jugador=n/2; jugador<n; jugador++) {
				for (dia=0; dia < n/2 - 1; dia++) {
					encuentros[jugador][dia] = encuentros[jugador-n/2][dia] + n/2;
				}
			}
		}
		//si no es potencia de 2 pero el número de jugadores es par
		else if (n%2 == 0) {
			//iremos dividiendo hasta llegar a solo dos jugadores
			trazaTorneoNombres(n/2, nombres);
			//cuando ya los tenemos, llenamos el cuadrante superior derecho
			for (jugador=0; jugador<n/2; jugador++) {
				for (dia=n/2; dia<n-1; dia++) {
					encuentros[jugador][dia] = jugador+1 + dia+1;
					if (encuentros[jugador][dia] > n)
						encuentros[jugador][dia] = encuentros[jugador][dia] - n/2;
				}
			}
			//llenamos el cuadrante inferior derecho
			for (jugador=n/2; jugador<n; jugador++) {
				for (dia=n/2; dia<n-1; dia++) {
					encuentros[jugador][dia] = jugador+1 - (dia+1);
					if (encuentros[jugador][dia] <= 0)
						encuentros[jugador][dia] = encuentros[jugador][dia] + n/2;
					}
			}
			//llenamos el cuadrante inferior izquierdo 
			for (jugador=n/2; jugador<n; jugador++) {
				for (dia=0; dia<n/2; dia++) {
					if (encuentros[jugador-n/2][dia] != 0)
						encuentros[jugador][dia] = encuentros[jugador - n/2][dia] + n/2;
				}
			}
			//llenamos el cuadrante superior izquierdo reemplazando los valores 0
			for (jugador=0; jugador<n/2; jugador++) {
				for (dia=0; dia<n/2; dia++) {
					if (encuentros[jugador][dia] == 0)
						encuentros[jugador][dia] = jugador+1 + n/2 ;
				}
			}
			//llenamos el cuadrante inferior izquierdo reemplazando los valores 0
			for (jugador=n/2; jugador<n; jugador++) {
				for (dia=0; dia<n/2; dia++) {
					if (encuentros[jugador][dia] == 0)
						encuentros[jugador][dia] = jugador+1 - n/2;
				}
			}
		}
		//si el número de jugadores es impar
		else {
			//si n es impar, le sumamos 1 para volverlo par y que en la llamada recursiva podamos llegar a 2 jugadores
			trazaTorneoNombres(n+1, nombres);
			//se eliminan los valores que sobran por llamar a la función con n+1 que serán los días de descanso
			for (jugador=0; jugador<n; jugador++) {
				for (dia=0; dia<n; dia++) {
					if (encuentros[jugador][dia]==n+1)
						encuentros[jugador][dia] = 0;
				}
			}
		}imprimirTablaConNombres(n, nombres);
	}	
}
