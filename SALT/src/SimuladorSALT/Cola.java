package SimuladorSALT;

import java.util.*;
	/*Clase que modela las diferentes colas a generar*/
	public class Cola extends Vector {

		/*Metodo que anade un avion a la cola*/
		public double anadirAvion(double tiempo,Aeropuerto p) throws Exception{

			Avion elem;


				elem = new Avion(tiempo,p);
			
			this.addElement(elem);

			return elem.t_llegada;

		}
		/*Método que añade un avión a la cola en un tiempo constante*/
		public double anadirAvionCnt(double tiempo,Aeropuerto p,double cnt) throws Exception{

			Avion elem;

			
			elem = new Avion(tiempo,p,cnt);

			this.addElement(elem);

			return elem.t_llegada;

		}

		/*Metodo que devuelve true si hay algun avion en cola, o false si la cola esta vacia*/
		public boolean tieneAviones(){

			Enumeration enume = this.elements();

			return enume.hasMoreElements();

		}
		
		
		/*Metodo que se encarga de dar servicio a cada avion, en un tiempo modelado por una constante*/
		public Avion procesarAviones(Vector colaProcesados, double cnt){

			Avion elem =(Avion)this.elementAt(0);

			elem.duracionAterrizaje =  cnt;

			colaProcesados.addElement(elem);

			this.removeElementAt(0);

			return elem;

		}
		
		/*Metodo que se encarga de dar servicio a cada avion, en un tiempo modelado por una variable
		 *aleatoria uniforme
		 **/
		public Avion procesarAvionesUni(Vector colaProcesados){

			Avion elem =(Avion)this.elementAt(0);

			elem.duracionAterrizaje = Math.random()*10;

			colaProcesados.addElement(elem);

			this.removeElementAt(0);

			return elem;

		}
		
		
		/*Metodo que se encarga de dar sevicio a cada avion, en un tiempo modelador por una variable exponencial*/
		public Avion procesarAvionesExp(Vector colaProcesados,double lambda){

			Avion elem =(Avion)this.elementAt(0);

			elem.duracionAterrizaje = 1+(lambda*Math.pow(Math.E, -lambda*Math.random())*60);

			colaProcesados.addElement(elem);

			this.removeElementAt(0);

			return elem;

		}

	}





