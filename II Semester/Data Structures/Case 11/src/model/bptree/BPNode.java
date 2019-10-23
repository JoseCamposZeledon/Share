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
	
	
	public boolean tieneHijos() {
		for (int posActual = 0; posActual < this.llavesMax; posActual++) {
			if (this.getHijos()[posActual] != null) {
				return true;
			}
		}
		return false;
	}
	
	
	public boolean llavesLlenas() {
		return !Arrays.asList(llaves).contains(null);
	}
	
	
	public boolean llavesVacias() {
		return this.getLlaves()[0] == null;
	}
	
	
	public boolean hijosLlenos() {
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
	 * Metodo que inserta y ordena las llaves
	 * - True si se logra insertar
	 * - False de lo contrario
	 */
	public boolean insertarLlave(T pLlave) {
		// Caso 1 - Nodo vacio
		if (this.llavesVacias()) {
			getLlaves()[0] = pLlave;
			return true;
		}
		
		// Caso 2 - Nodo con elementos, pero no esta lleno
		else if (!this.llavesLlenas()) {
			int indiceInsertar;
			/* Obtiene el indice en el que se va a insertar para correr los elementos del array
			 * Si es menor que el elemento entonces corre las llaves un espacio y lo inserta
			 * Si se encuentra con un null significa que pLlave es mayor que todas las llaves entonces lo inserta
			 */
		    for(indiceInsertar = 0; indiceInsertar < llaves.length - 1; indiceInsertar++) {
		    	
		    	// Si la llave es mayor a los demás lo inserta al final
		    	if(llaves[indiceInsertar] == null) {
		        	llaves[indiceInsertar] = pLlave;
		        	return true;
		        }
		    	
		    	// Si la llave es menor o igual a los demás lo inserta
		    	else if(pLlave.compareTo(llaves[indiceInsertar]) <= 0) break;      
		    }
		    
		    //Proceso para insertar, corre el contenido 1 espacio
		    for(int posActual = llaves.length-2; posActual >= indiceInsertar; posActual--){
		        llaves[posActual + 1] = llaves[posActual];            
		    }
		    
		    //Corre los hijos 1 espacio
		    for (int posActual = hijos.length - 2; posActual > indiceInsertar; posActual--) {
		    	hijos[posActual + 1] = hijos[posActual];
		    }
		    
		    llaves[indiceInsertar] = pLlave;
			return true;
			
			
		}
		// Caso 3 - Nodo lleno
		else {
			return false;
		}
	}
	
	
	/*
	 * Obtiene la posicion de la llave mayor
	 */
	public int getPosMayor() {
		// Caso 1 - Llaves llenas, entonces es el ultimo
		if (this.llavesLlenas()) return getLlavesMax() - 1;
		
		// Caso 2 - Llaves no estan llenas
		else {
			for (int posActual = 0; posActual < getLlavesMax(); posActual++) {
				if (getHijos()[posActual + 1] == null) return posActual;
			}
			
			return -1;
		}
		
	}
	
	
	/*
	 * Remueve la llave y reacomoda el array
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
	
	public BPNode<T> removerHijo(BPNode<T> pHijo) {
		int hijoPos = buscar(pHijo, getHijos());
		
		// Retorna nulo si el hijo no se encuentra
		if (hijoPos < 0) return null;
		
		BPNode<T> hijo = getHijos()[hijoPos];
		getHijos()[hijoPos] = null;
		
		// Corre los elementos que le seguian a la llave que se removio un espacio hacia atras
		T temp;
		for (int posActual = hijoPos + 1; posActual < this.llavesMax; posActual++) {
			
			// Detiene el ciclo si se encuentra con algun null en el camino
			if (hijos[posActual] == null) break;
			
			// Se intercambian las llaves
			temp = llaves[posActual - 1];
			llaves[posActual - 1] = llaves[posActual];
			llaves[posActual] = temp;
		}
		
		return hijo;
	}
	
	/*
	 * Se inserta el hijo en el lugar adecuado
	 */
	public void insertarHijo(BPNode<T> pNodo) {

		// Caso 1 - Menor valor de la llave es menor o igual a la llave menor del nodo
		if (pNodo.getLlaves()[0].compareTo(getLlaves()[0]) <= 0) {
			getHijos()[0] = pNodo;
			return;
		}
		
		// Caso 2 - Menor valor de la llave es mayor a la llave mayor del nodo
		
		int posMayor = this.getPosMayor();
		if (pNodo.getLlaves()[0].compareTo(getLlaves()[posMayor]) > 0) {
			this.hijos[posMayor + 1] = pNodo;
			return;
		}
		
		// Caso 3 - Se busca entre cuales llaves calza
		for (int posActual = 0; posActual < getLlavesMax(); posActual++) {
			if (getHijos()[posActual + 1] == null) continue;
			if (pNodo.getLlaves()[0].compareTo(getLlaves()[posActual]) < 0) {
				getHijos()[posActual] = pNodo;
			}
		}
		
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
	
	
}
