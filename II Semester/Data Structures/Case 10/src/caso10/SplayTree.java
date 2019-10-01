package caso10;

public class SplayTree<T extends Comparable<T>> {
	
	private NodoSplay<T> raiz;
	private int cantidad;
	
	public NodoSplay<T> getRaiz() {
		return raiz;
	}
	public void setRaiz(NodoSplay<T> raiz) {
		this.raiz = raiz;
	}
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	
	public void convertirEnRaiz(NodoSplay<T> pNodo) {
		// Función recursiva para convertir por medio de rotaciones a un nodo en raiz.
		if (pNodo.esRaiz()) {
			// Nodo ya es raiz
			return;
		} else if (pNodo.getPadre().esRaiz()) {
			// Nodo se encuentra en el nivel 1
			pNodo.zig(pNodo.esHijoIzquierdo());
		} else if (pNodo.esHijoIzquierdo() != pNodo.getPadre().esHijoIzquierdo()) {
			// Nodo se encuentra en algún nivel del árbol y se diferencia con su padre en su posición como hijo
			pNodo.zigZag(pNodo.esHijoIzquierdo());
			convertirEnRaiz(pNodo);
		} else {
			// Nodo se encuentra en algún nivel del árbol pero su posición es igual a la del padre
			pNodo.zigZig(pNodo.esHijoIzquierdo());
			convertirEnRaiz(pNodo);
		}
	}
	
	
	public void agregar(T pValor) {
		// Función para agregar una variable de tipo T al árbol, notar que esta es la que debe ser usada
		// preferiblemente ya que hace validaciones y crea el nodo, a diferencia de llamar directamente a la otra versión de esta
		NodoSplay<T> nodo = new NodoSplay<T>(pValor);
		if (getRaiz() == null) {
			setRaiz(nodo);
		} else {
			agregar(nodo, getRaiz());
		}
	}
	
	
	public void agregar(NodoSplay<T> pNodo, NodoSplay<T> currentNodo) {
		// Función que correrá recursivamente y comparará el currentNodo con el pNodo
		int resultado = pNodo.compareTo(currentNodo);
		if (resultado == 0) {
			// Son iguales y como es un splay tree no sirve tener duplicados
			return;
		} else if (resultado < 0) {
			// Es menor, si existe hijo correr función con él, de lo contrario agregarlo como hijo izquierdo del currentNodo
			if (currentNodo.getHijoIzquierdo() != null) {
				agregar(pNodo, currentNodo.getHijoIzquierdo());
			} else {
				currentNodo.setHijoIzquierdo(pNodo);
			}
		} else {
			// Es mayor, si existe hijo correr función con él, de lo contrario agregarlo como hijo derecho del currentNodo
			if (currentNodo.getHijoDerecho() != null) {
				agregar(pNodo, currentNodo.getHijoDerecho());
			} else {
				currentNodo.setHijoDerecho(pNodo);
			}
		}
	}
	
	
	public NodoSplay<T> buscar(T pValor) {
		NodoSplay<T> nodo = new NodoSplay<T>(pValor);
		return buscar(nodo, getRaiz());
	}
	
	
	public NodoSplay<T> buscar(NodoSplay<T> pNodo, NodoSplay<T> currentNodo) {
		if (currentNodo == null) {
			// Se llegó a una hoja y no se encontró pNodo
			return null;
		}
		int resultado = pNodo.compareTo(currentNodo);
		if (resultado == 0) {
			// Son iguales por lo tanto existe en el arbol y retorna currentNodo
			return currentNodo;
		} else if (resultado < 0) {
			// Es menor, correr función en el hijo izquierdo de él
			buscar(pNodo, currentNodo.getHijoIzquierdo());
		} else {
			// Es mayor, correr función con en el hijo derecho de él
			buscar(pNodo, currentNodo.getHijoDerecho());
		}
		return null;
	}
	
	
	public void imprimirArbol() {
		// Imprime los elementos de menor a mayor desde la raiz
		imprimirArbol(getRaiz());
	}
	
	
	public void imprimirArbol(NodoSplay<T> pNodo) {
		// Imprime los elementos de menor a mayor desde el nodo
		if (pNodo != null) { 
			imprimirArbol(pNodo.getHijoIzquierdo());
			System.out.printf(pNodo + " ");
			imprimirArbol(pNodo.getHijoDerecho());
		}
	}
	
	
	public static void main(String args[]) {
		SplayTree<String> arbol = new SplayTree<String>();
		arbol.agregar("b");
		arbol.agregar("a");
		arbol.agregar("d");
		arbol.agregar("c");
		arbol.agregar("e");
		arbol.agregar("f");
		arbol.agregar("d");
		
		NodoSplay<String> nodito = new NodoSplay<String>();
		nodito = arbol.buscar("f");
		System.out.println(nodito.getHijoIzquierdo());
		
		arbol.imprimirArbol();
	}
}
