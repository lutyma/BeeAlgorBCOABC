package edu.asu.emit.qyan.alg.control;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Reclutamiento {

	private ArrayList<Abeja> listaAbejas;
	private float rmd; 
	private int pasofinal;

	public Reclutamiento(ArrayList<Abeja> listaAbejas, float rmd, int pasofinal) {
		super();
		this.listaAbejas = listaAbejas;
		this.rmd = rmd;
		this.pasofinal = pasofinal;
	}



	public ArrayList<Abeja> reclutarAbejas(){

		ordenarListaAbejas(listaAbejas);
		int menorBloqueo = listaAbejas.get(0).getContadorBloqueo();

		for(Abeja ab:listaAbejas) {
			//	System.out.println("lista abejas ordenadas:"+ ab);
		}

		ArrayList<Abeja> respuesta = new ArrayList<Abeja>();
		ArrayList<Abeja> seguidoras = new ArrayList<Abeja>();
		ArrayList<Abeja> reclutadoras = new ArrayList<Abeja>();
		ArrayList<Abeja> reclutadoraspb = new ArrayList<Abeja>();
		double sumatoriapb = 0;

		for (int i = 0; i < listaAbejas.size(); i++) {			


			if( listaAbejas.get(i).getContadorBloqueo() == menorBloqueo && listaAbejas.get(i).getPb() > rmd) {
				reclutadoras.add(listaAbejas.get(i));
				System.out.println("abejas recultadoras:"+ listaAbejas.get(i));

			}
			else {

				seguidoras.add(listaAbejas.get(i));
				System.out.println("abejas seguidoras:"+ listaAbejas.get(i));

			}

		}

		for(Abeja ab:seguidoras) {

			//	System.out.println("lista de abejas seguidoras:"+ ab + "tamaño:"+ ab.getDemandas().size());
		}
		System.out.println();

		if(!reclutadoras.isEmpty()) {

			for (int j = 0; j < reclutadoras.size(); j++) {
				sumatoriapb = sumatoriapb + reclutadoras.get(j).getPb();

				//	System.out.println("Numero de abejas reclutadoras"+ reclutadoras.size());
			}

			for (int k = 0; k < reclutadoras.size(); k++) {
				Abeja auxiliar = new Abeja();

				double pbreclutamiento = (float)reclutadoras.get(k).getOb() / sumatoriapb;
				pbreclutamiento = Math.round(pbreclutamiento * 100) / 100d; 
				//	System.out.println("pbreclutamiento:"+ pbreclutamiento);

				auxiliar = reclutadoras.get(k);	
				auxiliar.setReclut(pbreclutamiento);
				reclutadoraspb.add(auxiliar);
				respuesta.add(auxiliar);
			}
		}
		else if (reclutadoras.isEmpty()) {
			respuesta = seguidoras;


		}
		for(Abeja ab:reclutadoraspb) {

			//	System.out.println("lista de abejas reclutadoras:"+ ab + "tamaño:"+ ab.getDemandas().size());
		}
		//	System.out.println();

		if(!seguidoras.isEmpty()) {
			double sumapb = 0;
			double numeroAleatorio = 0;
			//	double sumaseleccion = 0;

			for(int a = 0; a < reclutadoraspb.size(); a++) {

				sumapb = sumapb + reclutadoraspb.get(a).getReclut();
			}

			for (int z = 0; z < seguidoras.size(); z++) {		
				numeroAleatorio = (Math.random() * sumapb);
				double sumaseleccion = 0;
				Abeja abemutada = new Abeja();
				int x = 0;
				int su = 0;
				//	System.out.println("sumapb:"+ sumapb);
				//	System.out.println("random:"+ numeroAleatorio);
				while( x < reclutadoraspb.size() ){
					sumaseleccion = sumaseleccion + reclutadoraspb.get(x).getReclut();
					if(sumaseleccion >= numeroAleatorio) {
						//	System.out.println("CAMINO PARA CAMBIAR:"+ reclutadoraspb.get(x).getDemandas().get(x).getCaminoElegido());
						abemutada = mutacion(seguidoras.get(z), reclutadoraspb.get(x));
						abemutada.setReclut(0);
						su = funcionObjetivo(abemutada.getG());
						abemutada.setFuncionObjetivo(su);
						x = reclutadoraspb.size();     
					}
					x++;
				}
				respuesta.add(abemutada);
			} 
		}


		return respuesta;
	}

	/*	public Abeja mutacion(Abeja seguidora, Abeja reclutadora) {
		int aux = 0;
		List<Request> lista = new ArrayList<Request>();
		for(int a = 0; a < reclutadora.getDemandas().size(); a++) {
			Request auxiliar = new Request(reclutadora.getDemandas().get(a).getOrigen(), reclutadora.getDemandas().get(a).getDestino(), reclutadora.getDemandas().get(a).getFs());
		    lista.add(auxiliar);
		}
		Abeja respuesta_nueva_abeja = seguidora;
		AsignacionDemanda ordenar = new AsignacionDemanda();
		ArrayList<Request> demanNuevas = new ArrayList<Request>();
		ArrayList<Identificador> vecauxiliar = new ArrayList<Identificador>();
		respuesta_nueva_abeja.setG(reclutadora.getG()); 
		respuesta_nueva_abeja.setDemandas(lista);
		List<Request> demandasactuales = lista;
		//   System.out.println("demanda a copiar" + reclutadora.getDemandas());

		// se vuelve a ordenar las demandas restante de la abeja seguidora
		for(int i = pasofinal+1; i < seguidora.getDemandas().size(); i++) {
			// 	 System.out.println("pasofinal:"+ pasofinal);

			Identificador auxiliarId = new Identificador();
			Request auxiliar = new Request(seguidora.getDemandas().get(i).getOrigen(), seguidora.getDemandas().get(i).getDestino(), seguidora.getDemandas().get(i).getFs());
			int numeroAleatorio = (int) (Math.random() * 5) + 1;
			auxiliarId.setRandom(numeroAleatorio);
			auxiliar.setFs((auxiliar.getFs()*numeroAleatorio));
			auxiliarId.setRequest(auxiliar);
			auxiliarId.setId(i);
			vecauxiliar.add(auxiliarId); 
			//	System.out.println("demandas a ordenar:"+ vecauxiliar);
		}
		demanNuevas = ordenar.conversionfsnormal(vecauxiliar);
		//	System.out.println("demandas reordenadas:"+ demanNuevas);
		//    System.out.println("tamaño:"+ demanNuevas.size()); 
		for(int j = pasofinal+1; j < seguidora.getDemandas().size(); j++) {

			demandasactuales.set(j, demanNuevas.get(aux)) ;
			aux++;

		}
		respuesta_nueva_abeja.setDemandas(demandasactuales);

		return respuesta_nueva_abeja;
	}
	 */
	public Abeja mutacion(Abeja seguidora, Abeja reclutadora) {

		Abeja abemutada = new Abeja();
		List<Request> listaDemanda = new ArrayList<Request>();
		for(int i =0; i < reclutadora.getDemandas().size(); i++) {

			int random = (int) (Math.random() * 2);

			System.out.println("numero randomico para cambiar o no:" + random);
			if (random == 0 && reclutadora.getDemandas().get(i).getCaminoElegido() != null) {
				System.out.println("CAMINO PARA CAMBIAR:"+ reclutadora.getDemandas().get(i).getCaminoElegido());

				int banderaDesasignar = 0;
				if(seguidora.getDemandas().get(i).getCaminoElegido() != null) {
					banderaDesasignar = 1;
					DesasignarCaminoCambiado(seguidora.getG(), seguidora.getDemandas().get(i).getId(), seguidora.getDemandas().get(i).getCaminoElegido());
				}
				BuscarSlot r = new BuscarSlot(seguidora.getG(), reclutadora.getDemandas().get(i).getCaminoElegido());
				resultadoSlot res = r.concatenarCaminos(reclutadora.getDemandas().get(i).getFs(), 0);
				System.out.println("res2 : " + res);
				if(res != null) {
					Asignacion asignar = new Asignacion(seguidora.getG(), res);
					asignar.marcarSlotUtilizados(seguidora.getDemandas().get(i).getId());
					Request auxiliar = new Request(reclutadora.getDemandas().get(i).getOrigen(), reclutadora.getDemandas().get(i).getDestino(), reclutadora.getDemandas().get(i).getFs(), reclutadora.getDemandas().get(i).getCaminoElegido(), reclutadora.getDemandas().get(i).getId());	
					listaDemanda.add(auxiliar);
				}
				else if(banderaDesasignar == 1){
					BuscarSlot p = new BuscarSlot(seguidora.getG(), seguidora.getDemandas().get(i).getCaminoElegido());
					resultadoSlot res2 = r.concatenarCaminos(reclutadora.getDemandas().get(i).getFs(), 0);
					System.out.println("res2desasignado : " + res);
					if(res2 != null) {
						Asignacion asignar = new Asignacion(seguidora.getG(), res2);
						asignar.marcarSlotUtilizados(seguidora.getDemandas().get(i).getId());
						Request auxiliar = new Request(seguidora.getDemandas().get(i).getOrigen(), seguidora.getDemandas().get(i).getDestino(), seguidora.getDemandas().get(i).getFs(), seguidora.getDemandas().get(i).getCaminoElegido(), seguidora.getDemandas().get(i).getId());	
						listaDemanda.add(auxiliar);
					}
					else {

						Request auxiliar = new Request(seguidora.getDemandas().get(i).getOrigen(), seguidora.getDemandas().get(i).getDestino(), seguidora.getDemandas().get(i).getFs(), seguidora.getDemandas().get(i).getCaminoElegido(), seguidora.getDemandas().get(i).getId());	
						listaDemanda.add(auxiliar);
					}
				}
				else {
					Request auxiliar = new Request(seguidora.getDemandas().get(i).getOrigen(), seguidora.getDemandas().get(i).getDestino(), seguidora.getDemandas().get(i).getFs(), seguidora.getDemandas().get(i).getCaminoElegido(), seguidora.getDemandas().get(i).getId());	
					listaDemanda.add(auxiliar);
				}
			}

			else {
				Request auxiliar = new Request(seguidora.getDemandas().get(i).getOrigen(), seguidora.getDemandas().get(i).getDestino(), seguidora.getDemandas().get(i).getFs(), seguidora.getDemandas().get(i).getCaminoElegido(), seguidora.getDemandas().get(i).getId());	
				listaDemanda.add(auxiliar);
			}


		}
		abemutada.setDemandas(listaDemanda);
		abemutada.setG(seguidora.getG());
		abemutada.setId(seguidora.getId());
		return abemutada;

	}
	public void DesasignarCaminoCambiado(GrafoMatriz g, int id, String camino) {

		System.out.println("camino para desasignar: " + camino);
		String[] caminosLista;
		caminosLista = camino.split(",");
		for (int i=0; i < caminosLista.length-1; i++) {

			int k = Integer.parseInt(caminosLista[i]);
			System.out.println("primer digito camino cambio" + k);
			int l = Integer.parseInt(caminosLista[i+1]);
			System.out.println("segundo digito camino cambio" + l);
			int n1 = g.posicionNodo(k);
			int n2 = g.posicionNodo(l);

			//	g.grafo[n1][n2].listafs[0].libreOcupado = 1;
			//	g.grafo[n1][n2].listafs[1].libreOcupado = 1;
			//	g.grafo[n1][n2].listafs[2].libreOcupado = 1;
			for (int x = 0; x < g.grafo[n1][n2].listafs.length; x++) {

				if (g.grafo[n1][n2].listafs[x].id == id) {
					g.grafo[n1][n2].listafs[x].libreOcupado = 0;
					g.grafo[n2][n1].listafs[x].libreOcupado = 0;
				}
			}
		}
	}    

	public void ordenarListaAbejas(ArrayList<Abeja> listaAbejas) {

		Collections.sort(listaAbejas, new Comparator<Abeja>(){

			@Override
			public int compare(Abeja o1, Abeja o2) {
				return String.valueOf(o1.getContadorBloqueo()).compareToIgnoreCase(String.valueOf(o2.getContadorBloqueo()));
			}
		});

	}

	public static int funcionObjetivo(GrafoMatriz grafo) {
		int auxiliar = 0;
		for(int i = 0; i < grafo.getGrafo().length; i++) {
			for(int j = 0; j < grafo.getGrafo().length; j++) {
				for(int k = 0; k < grafo.getGrafo()[i][j].listafs.length; k++){
					if (grafo.getGrafo()[i][j].listafs[k].libreOcupado == 1 && k >= auxiliar) {

						auxiliar = k;
					}
				}

			}

		}


		return auxiliar;
	}

}
