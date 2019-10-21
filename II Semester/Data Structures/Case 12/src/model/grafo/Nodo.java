package model.grafo;

import java.util.ArrayList;

public class Nodo<T> {
	
	private static int ipCount = 0;
	
	private int ip; 
	private ArrayList<Nodo<T>> adjacentes;
	
	private T valor;
	
	public Nodo() {
		setIp(ipCount);
		ipCount++;
		setAdjacentes(new ArrayList<Nodo<T>>());
	}
	
	public Nodo(T pValor) {
		this();
		setValor(pValor);
	}
	
	public Nodo(T valor, ArrayList<Nodo<T>> adjacentes) {
		this(valor);
		setAdjacentes(adjacentes);
	}
	
	public int getIp() {
		return ip;
	}

	public void setIp(int ip) {
		this.ip = ip;
	}

	public ArrayList<Nodo<T>> getAdjacentes() {
		return adjacentes;
	}
	
	public void setAdjacentes(ArrayList<Nodo<T>> adjacentes) {
		this.adjacentes = adjacentes;
	}

	public void setValor(T pValor) {
		this.valor = pValor;
	}
	
	public T getValor() {
		return valor;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Nodo<T> other = (Nodo<T>) obj;
		if (ip != other.ip)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Nodo ip: " + getIp();
	}
	
}