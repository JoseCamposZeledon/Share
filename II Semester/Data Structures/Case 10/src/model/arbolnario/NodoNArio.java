package model.arbolnario;

import java.util.ArrayList;

public class NodoNArio<T> {

	private T valor;
	private NodoNArio<T> padre;
	
	private ArrayList< NodoNArio<T> > hijos;
	
	// Constructores
	public NodoNArio(T pValor) {
		valor = pValor;
		
		padre = null;
		hijos = new ArrayList<NodoNArio<T>>();
		
	}
	
	public NodoNArio() {
		valor = null;
		padre = null;
		hijos = new ArrayList<NodoNArio<T>>();
	}
	
	// Setters & Getter autogenerados
	public T getValor() {
		return valor;
	}


	public void setValor(T valor) {
		this.valor = valor;
	}


	public NodoNArio<T> getPadre() {
		return padre;
	}


	public void setPadre(NodoNArio<T> padre) {
		this.padre = padre;
	}
	

	public ArrayList<NodoNArio<T>> getHijos() {
		return hijos;
	}


	public void setHijos(ArrayList<NodoNArio<T>> hijos) {
		this.hijos = hijos;
	}
	
	// Metodos creados
	
	public NodoNArio<T> getHijo(int pPos) throws IndexOutOfBoundsException {
			return hijos.get(pPos);
	}
		
	public void setValorHijo(int pPos, T pValor) throws IndexOutOfBoundsException {
		hijos.get(pPos).setValor(pValor);
	}
	
	public int getCantidadHijos() {
		return hijos.size();
	}
	
	public boolean tieneHijos() {
		return !hijos.isEmpty();
	}
	
	public void agregarHijo(NodoNArio<T> pNodo) {
		pNodo.setPadre(this);
		hijos.add(pNodo);
	}
	
	public void agregarHijoEn(int pPos, NodoNArio<T> pNodo) throws IndexOutOfBoundsException{
		hijos.add(pPos, pNodo);
	}
	
	public void removerHijos() {
		hijos = new ArrayList<NodoNArio<T>>();
		hijos.trimToSize();
	}
	
	public void removerHijo(NodoNArio<T> pNodo) {
		hijos.remove(pNodo);
		hijos.trimToSize();
	}
	
	public void removerHijoEn(int pPos) {
		hijos.remove(pPos);
		hijos.trimToSize();
	}
	
}