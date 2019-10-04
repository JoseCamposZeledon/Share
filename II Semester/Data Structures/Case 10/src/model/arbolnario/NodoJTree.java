package model.arbolnario;

import javax.swing.tree.DefaultMutableTreeNode;

public class NodoJTree<T> extends DefaultMutableTreeNode{
	NodoNArio<T> nodo;
	
	public NodoJTree(NodoNArio<T> pNodo) {
		nodo = pNodo;
	}
	
	public String toString() {
		return nodo.getValor().toString();
	}
	
	public NodoNArio<T> getNodo() {
		return nodo;
	}
	
}
