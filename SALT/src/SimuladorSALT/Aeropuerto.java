package SimuladorSALT;

	
	public class Aeropuerto {
	
		/*Periodo de estacion del ano en el que se realizara la simulacion*/
		String periodo_anual;
		/*Tasa de llegada, numero medio de aviones por hora*/
		double tasa_llegada;
		/*Tipo de va que modele el tiempo de llegada de los aviones*/
		String tipo;
	
		/*Constructor que genera el aeropuerto con sus parametros*/
		public Aeropuerto(String periodo_anual,String tipo)throws Exception{
			this.tipo=tipo;
			this.periodo_anual=periodo_anual;
			if(periodo_anual.equals("VERANO")){
				tasa_llegada=50;	
			}
			else{
				if(periodo_anual.equals("OTONO")){
				tasa_llegada=15;
				}
				else{
					if(periodo_anual.equals("INVIERNO")){
						tasa_llegada=35;
					}
					else{
						if(periodo_anual.equals("PRIMAVERA")){
						tasa_llegada=25;
						}
						else{
							throw new Exception("Estacion no valida");
						
						}
					}
				}
			}
		}
		
		public Aeropuerto(double tasa,String tipo)throws Exception{
			this.tipo=tipo;
			if(tasa>0){
			this.tasa_llegada=tasa;
			}
			else{
			    throw new Exception("Tasa negativa no valida");
						
			}
		}
	
		/*Metodo que genera un numero entre 0 y 1*/
		public double uniforme(){
			double numero;
			numero=Math.random();
			return numero;				
		}
	
		/*Tiempo que tarda en llegar un avion a la cola*/
		public double tiempoDeLlegada(double variable){
		
			double tiempo=(this.tasa_llegada*Math.pow(Math.E, -tasa_llegada*variable));
			return tiempo;		
		}
		
	}
	