package model.bptree;

import java.util.Arrays;
import java.util.Collections;

/*
 * Nodo Arbol B+
 */
public class BPNode<T extends Comparable<T>> implements Comparable<BPNode<T>> {
	// Atributos 
	private int llavesMax;
	private T llaves[];
	private BPNode<T> padre, siguiente, hijos[];
	
	/*
	 * Inicializa parcialmente un arbol
	 */
	public BPNode() {
		padre = null;
		siguiente = null;
	}
	
	/*
	 * Inicializa un arbol vacio, con la cantidad de llaves que tendra
	 */
	@SuppressWarnings("unchecked")
	public BPNode(int pLlavesMax) {
		this();
		llavesMax = pLlavesMax;
		llaves = (T[]) new Comparable[llavesMax];
		hijos = new BPNode[llavesMax + 1];
	}
	
	
	// Setters & Getters
	public T[] getLlaves() {
		return llaves;
	}

	
	public void setLlaves(T[] llaves) {
		this.llaves = llaves;
	}

	
	public BPNode<T> getPadre() {
		return padre;
	}

	
	public void setPadre(BPNode<T> padre) {
		this.padre = padre;
	}

	
	public BPNode<T> getSiguiente() {
		return siguiente;
	}

	
	public void setSiguiente(BPNode<T> siguiente) {
		this.siguiente = siguiente;
	}

	
	public BPNode<T>[] getHijos() {
		return hijos;
	}

	
	public void setHijos(BPNode<T>[] hijos) {
		this.hijos = hijos;
	}

	
	public int getLlavesMax() {
		return llavesMax;
	}

	// Metodos Creados	
	public boolean esHoja() {
		for (int i = 0; i < llavesMax; i++) {
			if (hijos[i] != null) return false;
		}
		return true;
	}
	
	
	public boolean isEmpty() {
		return llaves[0] == null;
	}
	
	
	public boolean isFull () {
		return !Arrays.asList(llaves).contains(null);
	}
	
	/*
	 * Metodo necesario para hacer la clase comparable
	 */
	@Override
	public int compareTo(BPNode<T> o) {
		// Compara la menor llave de cada nodo 
		return this.llaves[0].compareTo(o.llaves[0]);
	}
	
	/*
	 * Retorna true si se pudo incluir la nueva llave, false de lo contrario
	 */
	public boolean nuevaLlave(T pLlave) { 
		if (this.isEmpty()) { // Caso 1, nodo vacio
			this.getLlaves()[0] = pLlave;
			return true;
			
		} else if(this.isFull()) { // Caso 2, nodo lleno
			
			return false;
			
		} else { // Caso 3, inserción en orden
			int indiceInsertar;
		    for(indiceInsertar = 0; indiceInsertar<llaves.length-1; indiceInsertar++) {
		    	
		    	// Si la llave es mayor a los demás lo inserta al final
		    	if(llaves[indiceInsertar] == null) {
		        	llaves[indiceInsertar] = pLlave;
		        	return true;
		        }
		    	
		    	// Si la llave es menor o igual a los demás lo inserta
		        if(pLlave.compareTo(llaves[indiceInsertar]) <= 0) break;      
		    }
		    
		    //Proceso para insertar, corre el contenido 1 espacio
		    for(int posActual=llaves.length-2; posActual>=indiceInsertar; posActual--){
		        llaves[posActual+1]=llaves[posActual];            
		    }
		    
		    llaves[indiceInsertar] = pLlave;
			return true;
		}
		
	}
	
	
	/*
	 * Se deshace del objeto en las llaves, se reacomodan los hijos
	 */
	public T removerLlave(T pLlave) {
		
		int llavePos = buscar(pLlave, this.llaves);
		// Retorna nulo si la llave no se encuentra
		if (llavePos < 0) return null;
		
		T llave = llaves[llavePos];
		llaves[llavePos] = null;
		
		// Corre los elementos que le seguian a la llave que se removio un espacio hacia atras
		T temp;
		for (int posActual = llavePos + 1; posActual < this.llavesMax; posActual++) {
			
			// Detiene el ciclo si se encuentra con algun null en el camino
			if (llaves[posActual] == null) break;
			
			// Se intercambian las llaves
			temp = llaves[posActual - 1];
			llaves[posActual - 1] = llaves[posActual];
			llaves[posActual] = temp;
		}
		
		return llave;
	}
	
	/*
	 *  Hace un linear search para buscar el elemento, retorna el indice donde se encuentra
	 *  Retorna -1 si no encuentra lo que se busca
	 */
	public int buscar(Object pLlave, Object[] pArray) {
		
		for (int posActual = 0; posActual < pArray.length; posActual++) {
			if (pArray[posActual] == pLlave) return posActual;
		}
		return -1;
	}
	
	/*
	 * Separa el nodo 
	 */
	public BPNode<T> split() {
		
		// Caso 1, se quiere partir la raiz, y es hoja
		if (this.padre == null && this.esHoja()) {
			
			int medio = llaves.length / 2;
			
			BPNode<T> nuevaRaiz = new BPNode<T>(llavesMax);
			nuevaRaiz.nuevaLlave(this.llaves[medio]);
			
			// Crea los dos nodos de cada lado e inserta los valores correspondientes
			BPNode<T> nodoIzquierdo = new BPNode<T>(llavesMax);
			BPNode<T> nodoDerecho = new BPNode<T>(llavesMax);
			
			for(int posIzquierda = 0; posIzquierda <= medio; posIzquierda++) {
				nodoIzquierdo.nuevaLlave(this.llaves[posIzquierda]);
			}
			
			for(int posDerecha = medio + 1; posDerecha < llavesMax; posDerecha++) {
				nodoDerecho.nuevaLlave(this.llaves[posDerecha]);
			}
			
			// Pone los nodos en las posiciones correspondientes
			nuevaRaiz.getHijos()[0] = nodoIzquierdo;
			nuevaRaiz.getHijos()[1] = nodoDerecho;
			return nuevaRaiz;
		}
		
		return null;
		
	}
}
