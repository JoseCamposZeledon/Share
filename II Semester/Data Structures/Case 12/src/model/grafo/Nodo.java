package model.grafo;

import java.util.ArrayList;

public class Nodo {
	
	private static int ipCount = 0;
	
	private int ip; 
	private ArrayList<Nodo> adjacentes;
	private int cordX;
	private int cordY;
	
	public Nodo() {
		setIp(ipCount);
		ipCount++;
		setAdjacentes(new ArrayList<Nodo>());
	}
	
	public Nodo(int cordX, int cordY) {
		this();
		setCordX(cordX);
		setCordY(cordY);
	}
	
	public Nodo(int cordX, int cordY, ArrayList<Nodo> adjacentes) {
		this(cordX, cordY);
		setAdjacentes(adjacentes);
	}
	
	public int getIp() {
		return ip;
	}

	public void setIp(int ip) {
		this.ip = ip;
	}

	public ArrayList<Nodo> getAdjacentes() {
		return adjacentes;
	}
	
	public void setAdjacentes(ArrayList<Nodo> adjacentes) {
		this.adjacentes = adjacentes;
	}
	
	public int getCordX() {
		return cordX;
	}
	
	public void setCordX(int cordX) {
		this.cordX = cordX;
	}
	
	public int getCordY() {
		return cordY;
	}
	
	public void setCordY(int cordY) {
		this.cordY = cordY;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Nodo other = (Nodo) obj;
		if (ip != other.ip)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Nodo ip: " + getIp();
	}
	
}