package model.cola;

public class Lista<T> {
	private Nodo<T> start;
	private Nodo<T> end;
	private int cantidad;
	
	public Lista() {
		start = end = null;
		cantidad = 0;
	}
	
	public void addEnd(Nodo<T> nodo) {
		if (start==null) {
			start = nodo;
			end = nodo;
		} else {
			end.setSiguiente(nodo);
			end = nodo;
		}
		cantidad++;
	}
	
	public void addStart(Nodo<T> pNodo) {
		if (start==null) {
			addEnd(pNodo);
		} else {
			pNodo.setSiguiente(start);
			start = pNodo;
			cantidad++;
		}
	}
	
	public Nodo<T> removeStart() {
		if (cantidad == 0) {
			return start;
		} else if(start.getSiguiente() == null) {
			Nodo<T> first = start;
			start = null;
			end = null;
			cantidad--;
			return first;
		} else {
			Nodo<T> first = this.getInicio();
			start = this.getInicio().getSiguiente();
			first.setSiguiente(null);
			cantidad--;
			return first;
		}
	}
	
	// Retorna la posicion
	public T remove(T pValor) {
		if (this.getInicio().getValue() == pValor) {
			return this.removeStart().getValue();
		}
		
		if (this.getEnd().getValue() == pValor) {
			return this.removeEnd().getValue();
		}
		
		Nodo<T> actual = this.getInicio();
		T valor = null;
		
		for (int i = 0; i < this.getCantidad(); i++) {
			if (actual.getSiguiente().getValue() == pValor) {
				
				valor = actual.getSiguiente().getValue();
				actual.getSiguiente().setSiguiente(null);
				actual.setSiguiente(actual.getSiguiente().getSiguiente());
				
				break;
			}
			
			actual = actual.getSiguiente();
		}
		
		return valor;
	}
	
	public Nodo<T> removeEnd() {
		if(cantidad == 0) {
			return end;
		} else if(start.getSiguiente() == end) {
			Nodo<T> ultimo = end;
			end = start;
			start.setSiguiente(null);
			cantidad--;
			return ultimo;
		} else {
			Nodo<T> ultimo = this.getEnd();
			end = this.getNodo(cantidad - 2);
			end.setSiguiente(null);
			cantidad--;
			return ultimo;
		}
	}
	
	public void insert(Nodo<T> pNodo, int pPos) {
		if (pPos==0) {
			addStart(pNodo);
		} else if (pPos>=cantidad) {
			addEnd(pNodo);
		} else {
			Nodo<T> current = start;
			int currentPosition = 0;
			while (currentPosition<pPos-1) {
				current=current.getSiguiente();
				currentPosition++;
			}
			
			pNodo.setSiguiente(current.getSiguiente());
			current.setSiguiente(pNodo);
			cantidad++;
		}
	}
	
	public Nodo<T> getNodo(int pNodo) {
		if (pNodo == 0) {
			return this.getInicio();
		} else if (pNodo == cantidad - 1) {
			return this.getEnd();
		} else if (pNodo >= cantidad) {
			return null;
		} else {
			Nodo<T> currentNodo = start;
			int currentPosition = 0;
			while (currentPosition < pNodo) {
				currentNodo = currentNodo.getSiguiente();
				currentPosition++;
			}
			return currentNodo;
		}
	}
	
	public Nodo<T> getInicio() {
		return start;
	}
	
	public Nodo<T> getEnd() {
		return end;
	}
	
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	
	public int getCantidad() {
		return cantidad;
	}
	
	public void showAll() {
		Nodo<T> current = start;
		int currentPosition = 0;
		while (currentPosition < cantidad) {
			System.out.println("Nodo " + currentPosition + " : " + current.getValue());
			current=current.getSiguiente();
			currentPosition++;
		}
	}
}
