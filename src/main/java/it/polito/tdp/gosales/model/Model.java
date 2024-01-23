package it.polito.tdp.gosales.model;

import java.util.*;

import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.gosales.dao.GOsalesDAO;

public class Model {
	private GOsalesDAO dao;
	private SimpleWeightedGraph<Retailers, DefaultWeightedEdge> grafo;
	private Map<Integer, Retailers> mappa;
	
	
	public Model() {
		this.dao = new GOsalesDAO();
		this.mappa = this.dao.getAllRetailers();
		
	}
	public void creaGrafo(String country, Integer anno, Integer N) {
		this.grafo = new  SimpleWeightedGraph<Retailers, DefaultWeightedEdge> (DefaultWeightedEdge.class);
		Graphs.addAllVertices(this.grafo, this.dao.getVertici(country));
		
		for (Arco a: this.dao.getArchi(country, anno, N, mappa)){
			Graphs.addEdgeWithVertices(this.grafo, a.getR1(), a.getR2(), a.getPeso());
		}
	}

	public List<Integer> getAnno() {
		return this.dao.getAnno();
	}
	public List<String> getCountry(){
		return this.dao.getCountry();
	}
	public int numV() {
		return this.grafo.vertexSet().size();
	}
	public int numA() {
		return this.grafo.edgeSet().size();
	}
	public Set<Retailers> vertxSet() {
		return this.grafo.vertexSet();
	}
	public List<Arco> archi(String country, Integer anno, Integer N){
		return this.dao.getArchi(country, anno, N, mappa);
	}
	
	/**
	 * COMPONENTE CONNESSA
	 * CREO UNA CLASSE COMPONENTE CONNESSA CHE CONTENGA LE COSE CHE DEVO STAMPARE
	 * DIMENSIONE COMPONENTE + PESO
	 */
	public CompConnessa analizzaComopnente(Retailers r) {
		
		//TROVA LA COMPONENTE CONNESSA
		ConnectivityInspector <Retailers, DefaultWeightedEdge> inspector =
				new ConnectivityInspector <Retailers, DefaultWeightedEdge>(this.grafo);
		
		Set<Retailers> connessi = inspector.connectedSetOf(r);
		
//		int nConnessi = connessi.size();
		
		/**
		 * CALCOLARE IL PESO DEGLI ARCHI DELLA COMPONENTE CONNESSA 
		 * PRENDO UN ARCO ALLA VOLTA, VERIFICO CHE I VERTICI APPARTENGONO 
		 * ALLA COMPONENTE CONNESSA
		 * LI AGGIUNGO AL PESO TOTALE
		 */
		
		int peso =  0;
		for (DefaultWeightedEdge e : this.grafo.edgeSet()) {
			if (connessi.contains(this.grafo.getEdgeSource(e)) && 
					connessi.contains(this.grafo.getEdgeTarget(e))) {
				peso += this.grafo.getEdgeWeight(e);
			}
		}
		CompConnessa result = new CompConnessa(connessi, peso);
		return result;
		
	}
	public List<Retailers> getRetailers() {
		return this.dao.getRetailersName();
	}
	
}