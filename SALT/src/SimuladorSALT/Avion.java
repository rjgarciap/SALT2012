package SimuladorSALT;
import java.lang.Math;


	/*Clase que modela a cada avion*/
	public class Avion {
		
		/*Tiempo en el que el avion llega a cola*/
		public double t_llegada;
		/*Momento en el que el avion empieza a aterrizar*/
		public double inicioAterrizaje;
		/*Duracion del aterrizaje*/
		public double duracionAterrizaje;

		/*Metodo que crea un objeto de la clase Avion, en el que se genera el tiempo de llegada de
		 *este a la cola de manera aleatoria
		 */
		public Avion(double tiempo,Aeropuerto p) throws Exception{
			if(p.tipo.equals("EXPONENCIAL")){
			t_llegada = tiempo + (p.tasa_llegada*Math.pow(Math.E, -p.tasa_llegada*Math.random())*60);
			}else{
				if(p.tipo.equals("UNIFORME")){
					t_llegada = tiempo + ((Math.random())*60);
				}else{
					
						throw new Exception("Debe indicar correctamente el tipo.");
					
				}
			}
		}
		
		public Avion(double tiempo,Aeropuerto p,double cnt) throws Exception{
			
					if(p.tipo.equals("CONSTANTE")){
						t_llegada = tiempo + cnt;
					}else{
						throw new Exception("Debe indicar correctamente el tipo.");
					}
		}
			
		
		
		

	}

