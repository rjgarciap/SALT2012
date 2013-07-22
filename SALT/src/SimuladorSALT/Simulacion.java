package SimuladorSALT;


import java.lang.Math;
import java.util.*;


	/*Metodo encargado de realizar la simulacion*/
	public class Simulacion {

		private static double tiempo;

		private static double horaLibre;


		public static void main(String args[]) {
			
	        
		 
		
			System.out.println("Inicio de Simulacion");
			
			
			/*Entrada de datos para la simulacion"*/   
			Scanner entrada=new Scanner(System.in);
			
			System.out.print("Ingrese el tipo de variable de tiempo de llegada(EXPONENCIAL,UNIFORME,CONSTANTE):");
			
			//Duracion de la simulacion en minutos.
			String tipoLlegada=entrada.next();   
			
			Aeropuerto barajas = null;
			double constanteLlegada=0;
			if(tipoLlegada.equals("EXPONENCIAL")){
				String estacion = null;
				System.out.print("Ingrese la estacion del ano(VERANO, OTONO, INVIERNO, PRIMAVERA o MANUAL): ");
				//Estacion del año donde se desarrolla la simulacion   
				estacion = entrada.next();
			if(estacion.equals("MANUAL")){
				System.out.print("Ingrese la tasa de llegada: ");
				   
				double tasaLlegada= entrada.nextInt();

				try {
					barajas = new Aeropuerto(tasaLlegada,tipoLlegada);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else{
				try {
					barajas = new Aeropuerto(estacion,tipoLlegada);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			}else{
				if(tipoLlegada.equals("UNIFORME")){
					
					try{	
						barajas = new Aeropuerto(1,tipoLlegada);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else{
					if(tipoLlegada.equals("CONSTANTE")){
						System.out.print("Ingrese la constante de llegada: ");
						   
						constanteLlegada= entrada.nextDouble();
						try {
							barajas = new Aeropuerto(1,tipoLlegada);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
			System.out.print("Ingrese la duracion de la simulacion(Tiempo en minutos, Ej.: 24 Horas = 1440 min):");
			
			//Duracion de la simulacion en minutos.
			int duracion=entrada.nextInt();
			
			System.out.print("Ingrese el tipo de variable de tiempo de aterrizaje(EXPONENCIAL, UNIFORME, CONSTANTE): ");
			//Tipo de variable aleatoria del tiempo de aterrizaje   
			String tipoAterrizaje = entrada.next();
			double tasaAterrizaje = 0;
			if(tipoAterrizaje.equals("EXPONENCIAL")) {
			System.out.print("Ingrese la tasa de aterrizaje: ");
			//Tasa media de aterrizaje   
			tasaAterrizaje = entrada.nextDouble();		
			}
			double constante=2;
			if(tipoAterrizaje.equals("CONSTANTE")){
				System.out.print("Ingrese la constante: ");
				//CONSTANTE DE ATERRIZAJE
				constante = entrada.nextDouble();					
			}
			
			
			Cola cola = new Cola();

			Vector colaProcesada = new Vector();
			
			ArrayList<Double> tamanoCola = new ArrayList<Double>();
			ArrayList<Double> tiempoCola = new ArrayList<Double>();
			
			


			/*Variable que permiten calcular datos estadisticos*/
			int aterrizados=0;
			int i=60;
			int hsaterrizado=0;
			ArrayList<Double> aterrizadosHora = new ArrayList<Double>();
			ArrayList<Double> tiempoEspera = new ArrayList<Double>();
			ArrayList<Double> numeroAterrizaje = new ArrayList<Double>();
			ArrayList<Double> tiempoAterrizaje = new ArrayList<Double>();
			double nAterrizaje=0;
			
			while (tiempo < duracion) {

					try {
						if(barajas.tipo.equals("CONSTANTE")){
						tiempo = cola.anadirAvionCnt(tiempo,barajas,constanteLlegada);
						}else{
							tiempo= cola.anadirAvion(tiempo, barajas);
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					System.out.println("Tiempo:" + tiempo+ " Tamano de cola: " + cola.size());
					tamanoCola.add((double) cola.size());
					tiempoCola.add(tiempo);
					while ((horaLibre < tiempo) && (cola.tieneAviones())) {
						System.out.println("Pista libre");
						Avion procesado = null;
						if(tipoAterrizaje.equals("UNIFORME")){
						procesado = cola.procesarAvionesUni(colaProcesada);
						}else{
							if(tipoAterrizaje.equals("EXPONENCIAL")){
								

								procesado= cola.procesarAvionesExp(colaProcesada, tasaAterrizaje);
							}else{
								if(tipoAterrizaje.equals("CONSTANTE")){
									procesado=cola.procesarAviones(colaProcesada,constante);
								}else{
									try {
										throw new Exception("Tipo de Aterrizaje incorrecto");
									} catch (Exception e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
							
						}
							}
						procesado.inicioAterrizaje = Math.max(horaLibre, procesado.t_llegada);

						horaLibre = procesado.inicioAterrizaje + procesado.duracionAterrizaje;

						System.out.println("Tiempo:" + tiempo+ " Tamano de Cola: " + cola.size()+ " Hora de llegada: " + procesado.t_llegada
								+ " Inicio de Aterrizaje: " + procesado.inicioAterrizaje +" Duracion aterrizaje: " + procesado.duracionAterrizaje);
						
						tiempoEspera.add(procesado.inicioAterrizaje-procesado.t_llegada);
						tiempoAterrizaje.add((procesado.inicioAterrizaje-procesado.t_llegada)+procesado.duracionAterrizaje );
						numeroAterrizaje.add(nAterrizaje++);
						
						tamanoCola.add((double) cola.size());
						tiempoCola.add(procesado.inicioAterrizaje+procesado.duracionAterrizaje);
						aterrizados++;
						if(tiempo>i||tiempo==i){
							hsaterrizado++;
							aterrizadosHora.add((double)aterrizados);
							i=i+60;
							aterrizados=0;
						}
					}


			}
				
			
				double mediaAterrizados=0;
				double sumaA = 0;
				double dividendoA=0;
				for(int a=0;a<hsaterrizado;a++){
					sumaA+=aterrizadosHora.get(a);
					dividendoA++;
				}
				mediaAterrizados=sumaA/dividendoA;
				
				System.out.println("El numero medio de aviones que aterrizan por hora: "+mediaAterrizados+" aviones/hora.");
				System.out.println("Han aterrizado en total: "+sumaA+" aviones.");
				
				double sumaC=0;
				double dividendoC=0;
				for(int b=0;b<tamanoCola.size();b++){
					sumaC+=tamanoCola.get(b);		
					dividendoC++;
				}
				double mediaCola=sumaC/dividendoC;
				System.out.println("El numero medio de aviones en cola: "+mediaCola+" aviones/hora.");
				
				
				double sumaVarianza=0;
				double dividendoVariaza=0;
				for(int c=0;c<tamanoCola.size();c++){
					sumaVarianza+= (tamanoCola.get(c)*tamanoCola.get(c));
					dividendoVariaza++;
				}
				double varianza=(sumaVarianza/dividendoVariaza)-(mediaCola*mediaCola);
				System.out.println("La varianza del numero de aviones en cola: "+varianza+".");
				
				
				double sumaTiempoEspera=0;
				double dividendoTiempoEspera=0;
				for(int d=0;d<tiempoEspera.size();d++){
					sumaTiempoEspera+=tiempoEspera.get(d);
					dividendoTiempoEspera++;					
				}
				double mediaTiempoEspera=(sumaTiempoEspera/dividendoTiempoEspera);
				System.out.println("El tiempo de espera medio es de: "+mediaTiempoEspera+" minutos.");
				
				double sumaTiempoAterrizaje=0;
				double dividendoTiempoAterrizaje=0;
				for(int d=0;d<tiempoAterrizaje.size();d++){
					sumaTiempoAterrizaje+=tiempoAterrizaje.get(d);
					dividendoTiempoAterrizaje++;					
				}
				double mediaTiempoAterrizaje=(sumaTiempoAterrizaje/dividendoTiempoAterrizaje);
				System.out.println("El tiempo de aterrizaje medio es de: "+mediaTiempoAterrizaje+" minutos.");
				
				ventanaGrafica graficaCola = new ventanaGrafica(tamanoCola,tiempoCola,1);
			    graficaCola.setSize(900,500);
			    graficaCola.show();
			    ventanaGrafica miventana = new ventanaGrafica(tiempoEspera,numeroAterrizaje,2);
			    miventana.setSize(900,500);
			    miventana.show();
			    ventanaGrafica aterrizaje = new ventanaGrafica(tiempoAterrizaje,numeroAterrizaje,3);
			    aterrizaje.setSize(900,500);
			    aterrizaje.show();
			    entrada.close();


	}
	

}

