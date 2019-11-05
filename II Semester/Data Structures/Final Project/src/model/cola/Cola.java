package model.cola;

public class Cola<T> extends Lista<T>{
	
	private int menorPrioridad = Integer.MIN_VALUE;
	
	public void enqueue(T pValor) {
		this.enqueue(pValor, menorPrioridad);
	}
	
	public void enqueue(T pValor, int prioridad) {
		
		Nodo<T> nuevoNodo = new Nodo<T>(pValor);
		
		nuevoNodo.setPrioridad(prioridad);
		
		if (this.getCantidad() == 0 || nuevoNodo.getPrioridad() == menorPrioridad) {
		
			this.addEnd(nuevoNodo);
			
		} else {
			// Numero de prioridad mayor o igual al ultimo
			if (nuevoNodo.getPrioridad() >= this.getEnd().getPrioridad() ) { 

				this.addEnd(nuevoNodo);
			
			// Numero de prioridad menor al inicio de la cola
			} else if (nuevoNodo.getPrioridad() < this.getInicio().getPrioridad()) {
				
				this.addStart(nuevoNodo);
			
			} else { // Caso general
				
				Nodo<T> actual = this.getInicio();

				while (actual != null) {
					
					if (actual.getPrioridad() <= nuevoNodo.getPrioridad() &&
						actual.getSiguiente().getPrioridad() > nuevoNodo.getPrioridad()) {
			
						nuevoNodo.setSiguiente(actual.getSiguiente());
						actual.setSiguiente(nuevoNodo);
						
						this.setCantidad(this.getCantidad() + 1);
						break;
						
					} else {
						
						actual = actual.getSiguiente();
						
					}
				}
				
			}
		}
	}
	
	public T dequeue() {
		return this.removeStart().getValue();
	}
	
	public boolean isEmpty() {
		return this.getCantidad() == 0;
	}
	
	public void cambiarPrioridad(T pValor, int nuevaPrioridad) {
	
		this.enqueue(this.remove(pValor), nuevaPrioridad);
		
	}

}
