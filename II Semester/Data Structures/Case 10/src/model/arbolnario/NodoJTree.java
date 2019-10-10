package model.arbolnario;

import javax.swing.tree.DefaultMutableTreeNode;

import model.sensor.Sensor;

public class NodoJTree<T> extends DefaultMutableTreeNode{
	NodoNArio<T> nodo;
	
	public NodoJTree(NodoNArio<T> pNodo) {
		nodo = pNodo;
	}
	
	public String toString() {
		return "<" + ((Sensor) nodo.getValor()).getId() + ">  " + ((Sensor) nodo.getValor()).getLugar().getNombre();
	}
	
	public NodoNArio<T> getNodo() {
		return nodo;
	}
	
}
