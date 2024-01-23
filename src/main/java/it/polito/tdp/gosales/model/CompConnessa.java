package it.polito.tdp.gosales.model;

import java.util.Set;

public class CompConnessa {
	private Set<Retailers> connessi;
	private int peso;
	public CompConnessa(Set<Retailers> connessi, int peso) {
		super();
		this.connessi = connessi;
		this.peso = peso;
	}
	public Set<Retailers> getConnessi() {
		return connessi;
	}
	public void setConnessi(Set<Retailers> connessi) {
		this.connessi = connessi;
	}
	public int getPeso() {
		return peso;
	}
	public void setPeso(int peso) {
		this.peso = peso;
	}
	@Override
	public String toString() {
		return "Dimensione componente connessa: " + this.connessi.size() + " e peso: "+ peso + "\n";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((connessi == null) ? 0 : connessi.hashCode());
		result = prime * result + peso;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CompConnessa other = (CompConnessa) obj;
		if (connessi == null) {
			if (other.connessi != null)
				return false;
		} else if (!connessi.equals(other.connessi))
			return false;
		if (peso != other.peso)
			return false;
		return true;
	}
	
}
