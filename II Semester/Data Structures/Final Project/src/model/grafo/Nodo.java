package model.grafo;

import java.util.ArrayList;

public class Nodo<T>{
	private ArrayList<Arco<T>> conexiones = new ArrayList<Arco<T>>();
	private boolean visitado = false;
	private Nodo<T> previo;
	private T valor;
	
	public Nodo(T pValor) {
		valor = pValor;
		previo = null;
	}
	
	public void conectar(Nodo<T> pNodo, int pPeso) {
		conexiones.add(new Arco<T>(this, pNodo, pPeso));
	}

	public ArrayList<Arco<T>> getConexiones() {
		return conexiones;
	}

	public void setConexiones(ArrayList<Arco<T>> conexiones) {
		this.conexiones = conexiones;
	}

	public T getValor() {
		return valor;
	}

	public void setValor(T valor) {
		this.valor = valor;
	}

	public boolean isVisitado() {
		return visitado;
	}

	public void setVisitado(boolean visitado) {
		this.visitado = visitado;
	}

	public Nodo<T> getPrevio() {
		return previo;
	}

	public void setPrevio(Nodo<T> previo) {
		this.previo = previo;
	}
}
