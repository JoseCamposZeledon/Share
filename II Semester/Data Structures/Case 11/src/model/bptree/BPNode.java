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
	public boolean tieneHijos() {
		return hijos.length != 0;
	}
	
	
	public boolean esHoja() {
		for (int i = 0; i < llavesMax; i++) {
			if (hijos[i] != null) return false;
		}
		return true;
	}
	
	
	public boolean isEmpty(Object[] pArray) {
		return pArray[0] == null;
	}
	
	
	public boolean isFull (Object[] pArray) {
		return !Arrays.asList(pArray).contains(null);
	}
	
	/*
	 * Metodo necesario para hacer la clase comparable
	 */
	@Override
	public int compareTo(BPNode<T> o) {
		// Compara la menor llave de cada nodo 
		return this.llaves[0].compareTo(o.llaves[0]);
	}
	
	public T remover(T pLlave) {
		T llave = null;
		
		/*
		 * Binary Search 
		 */
		
		return llave;
	}
	
	/*
	 * Retorna true si se pudo incluir la nueva llave, false de lo contrario
	 */
	public boolean nuevaLlave(T pLlave) { 
		if (this.isEmpty(this.llaves)) { // Caso 1, nodo vacio
			this.getLlaves()[0] = pLlave;
			return true;
			
		} else if(this.isFull(this.llaves)) { // Caso 2, nodo lleno
			System.out.println("FAIL");
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

}
