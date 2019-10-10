package model.arbolnario;

import java.util.ArrayList;

import javax.swing.tree.DefaultMutableTreeNode;

import model.sensor.Sensor;

public class NodoJTree<T> extends DefaultMutableTreeNode{
	NodoNArio<T> nodo;
	
	private NodoJTree<T> padre;
	
	private ArrayList<NodoJTree<T> > hijos;
	
	public NodoJTree(NodoNArio<T> pNodo) {
		nodo = pNodo;
		
		padre = null;
		hijos = new ArrayList<NodoJTree<T>>();
	}
	
	public String toString() {
		return "<" + ((Sensor) nodo.getValor()).getId() + ">  " + ((Sensor) nodo.getValor()).getLugar().getNombre();
	}
	
	public NodoNArio<T> getNodo() {
		return nodo;
	}
	
	
	public NodoJTree<T> getPadre() {
		return padre;
	}

	public void setPadre(NodoJTree<T> padre) {
		this.padre = padre;
	}

	public ArrayList<NodoJTree<T>> getHijos() {
		return hijos;
	}

	public void setHijos(ArrayList<NodoJTree<T>> hijos) {
		this.hijos = hijos;
	}

	public void agregarHijo(NodoJTree<T> pNodo) { 
		hijos.add(pNodo);
		pNodo.setPadre(this);
	}
	
	public NodoJTree<T> quitarHijo(NodoJTree<T> pNodo) {
		hijos.remove(pNodo);
		hijos.trimToSize();
		return pNodo;
	}
	
	public NodoJTree<T> borrarNodo() {
		
		for (NodoJTree<T> hijoActual : this.getHijos()) {
			hijoActual.setPadre(padre);
			padre.agregarHijo(hijoActual);
		}
		
		return this;
	}
}
