package model.bptree;

import java.util.ArrayList;

/*
 * Nodo Arbol B+
 */
public class BPNode<T> {
	// Atributos 
	private int cantidadLlaves, llavesMax, cantidadHijos;
	private BPNode<T> padre, vecinoDrch, vecinoIzq;
	private ArrayList<BPInternalNode<T>> elementos;
 	
	// Constructores
	public BPNode() {
		padre = null;
		vecinoDrch = null;
		vecinoIzq = null;
	}
	
	public BPNode(int pLlavesMax) {
		this();
		llavesMax = pLlavesMax;
	}

	// Getters & Setters
	
	public int getCantidadLlaves() {
		return cantidadLlaves;
	}

	public void setCantidadLlaves(int cantidadLlaves) {
		this.cantidadLlaves = cantidadLlaves;
	}

	public int getLlavesMax() {
		return llavesMax;
	}

	public void setLlavesMax(int llavesMax) {
		this.llavesMax = llavesMax;
	}

	public int getCantidadHijos() {
		return cantidadHijos;
	}

	public void setCantidadHijos(int cantidadHijos) {
		this.cantidadHijos = cantidadHijos;
	}

	public BPNode<T> getPadre() {
		return padre;
	}

	public void setPadre(BPNode<T> padre) {
		this.padre = padre;
	}

	public BPNode<T> getVecinoDrch() {
		return vecinoDrch;
	}

	public void setVecinoDrch(BPNode<T> vecinoDrch) {
		this.vecinoDrch = vecinoDrch;
	}

	public BPNode<T> getVecinoIzq() {
		return vecinoIzq;
	}

	public void setVecinoIzq(BPNode<T> vecinoIzq) {
		this.vecinoIzq = vecinoIzq;
	}

	public ArrayList<BPInternalNode<T>> getElementos() {
		return elementos;
	}

	public void setElementos(ArrayList<BPInternalNode<T>> elementos) {
		this.elementos = elementos;
	}
	
	// Metodos Creados
	
}
