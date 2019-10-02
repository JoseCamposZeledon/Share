package model.arbolNArio;

public class ArbolNArio<T> {

	private NodoNArio<T> raiz;
	
	// Constructores
	
	public ArbolNArio() {
		raiz = null;
	}
	
	public ArbolNArio(T pValor) {
		raiz = new NodoNArio<T>();
		raiz.setValor(pValor);
	}

	// Getters & Setters generados
	public NodoNArio<T> getRaiz() {
		return raiz;
	}

	public void setRaiz(NodoNArio<T> raiz) {
		this.raiz = raiz;
	}
	
	//Metodos creados
	
	public int getCantidadNodos() {
		int cantidadNodos = 0;
		
		//terminar
		
		return cantidadNodos;
	}
	
	public boolean isEmpty() {
		return raiz == null;
	}
}
