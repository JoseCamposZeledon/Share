package model.cola;

public class Nodo <T>{
	private T value;
	private Nodo<T> siguiente;
	private int prioridad;
	
	public Nodo(T pValue) {
		this.value = pValue;
		siguiente = null;
	}

	public T getValue() {
		return value;
	}

	public Nodo<T> getSiguiente() {
		return siguiente;
	}

	public void setSiguiente(Nodo<T> siguiente) {
		this.siguiente = siguiente;
	}

	public int getPrioridad() {
		return prioridad;
	}

	public void setPrioridad(int prioridad) {
		this.prioridad = prioridad;
	}
	
	
	
}