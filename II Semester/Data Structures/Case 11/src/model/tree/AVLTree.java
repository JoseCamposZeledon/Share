package model.tree;

import java.util.ArrayList;
import java.util.HashMap;

import model.parser.PalabrasRepetidas;

public class AVLTree<T extends Comparable<T>> {
	
	private HashMap<String, Link> mapaLinks;
	private AVLNode<T> raiz;
	private int cantidadNodos;
	private int iteraciones;
	
	/*
	 * CONSTRUCTORES
	 */
	public AVLTree() {
		raiz = null;
		iteraciones = 0;
		mapaLinks = new HashMap<String, Link>();
	}
	
	public AVLTree(T pValor) {
		this();
		raiz = new AVLNode<T>(pValor);
	}
	
	/*
	 * GETTERS & SETTERS
	 */
	public AVLNode<T> getRaiz() {
		return raiz;
	}
	public void setRaiz(AVLNode<T> raiz) {
		this.raiz = raiz;
	}
	public int getCantidadNodos() {
		return cantidadNodos;
	}
	
	public int getIteraciones() {
		return iteraciones;
	}
	
	public void resetIteraciones() {
		iteraciones = 0;
	}
	
	/*
	 * METODOS CREADOS
	 */
	
	public HashMap<String, Link> getMapaLinks() {
		return mapaLinks;
	}

	public void setMapaLinks(HashMap<String, Link> mapaLinks) {
		this.mapaLinks = mapaLinks;
	}

	public void setCantidadNodos(int cantidadNodos) {
		this.cantidadNodos = cantidadNodos;
	}

	public void setIteraciones(int iteraciones) {
		this.iteraciones = iteraciones;
	}

	// Busqueda
	public T buscar(T pValor) {
		// Caso 1 - Raiz nula
		if (raiz == null) return null;
		// Caso 2 - Buscar dentro de la raiz
		
		return buscar(raiz, pValor);
	}
	
	public T buscar(AVLNode<T> pRoot ,T pValor) {
		iteraciones++;
		// Caso 1 - No existe
		if (pRoot == null) {
			return null;
		}
		
		// Caso 2 - pValor es igual al valor guardado en pRoot
		else if (iguales(pValor, pRoot.getValor())) {
			return pValor;
		}
		
		// Caso 3 - pValor es menor al valor guardado en pRoot
		else if (menor(pValor, pRoot.getValor())) {
			return buscar(pRoot.getHijoIzquierdo(), pValor);
		}
		
		// Caso 4 - pValor es mayor al valor guardado en pRoot
		else {
			return buscar(pRoot.getHijoDerecho(), pValor);
		}
	}

	public T buscarMax(AVLNode<T> pRoot) {

		while(pRoot.getHijoDerecho() != null) {
			pRoot = pRoot.getHijoDerecho();
		}
		
		return pRoot.getValor();
	}
	
	public ArrayList<PalabrasRepetidas> get5Max(String link) {
		ArrayList<PalabrasRepetidas> resultado = new ArrayList<PalabrasRepetidas>();
		getMaxAux(raiz, resultado, mapaLinks.get(link).getMax());
		for (PalabrasRepetidas lista : resultado) {
			
		}
		return resultado;
	}
	
	private void getMaxAux(AVLNode<T> nodoActual, ArrayList<PalabrasRepetidas> array, int maxValue) {
		if (nodoActual != null) {
			iteraciones++;
			if (((PalabrasRepetidas) nodoActual.getValor()).getRepeticiones() <= maxValue) {
				getMaxAux(nodoActual.getHijoDerecho(), array, maxValue);
				array.add((PalabrasRepetidas) nodoActual.getValor());
				getMaxAux(nodoActual.getHijoIzquierdo(), array, maxValue);
			}
		}
	}
	
	public T buscarMin(AVLNode<T> pRoot) {

		while(pRoot.getHijoDerecho() != null) {
			pRoot = pRoot.getHijoIzquierdo();
		}
		
		return pRoot.getValor();
	}
	
	// Insercion
	public void insertar(T pValor) {
		// Caso 1 - Raiz nula
		if (raiz == null) {
			raiz = new AVLNode<T>(pValor);
		}
		// Caso 2 - Insercion
		else {
			raiz = insertar(raiz, pValor);
		}
		this.cantidadNodos++;
	}
	
	public AVLNode<T> insertar(AVLNode<T> pRoot, T pValor) {
		// Caso 1 - Se encuentra la posicion
		if (pRoot == null) {
			pRoot = new AVLNode<T>(pValor);
			return pRoot;
		}
		// Caso 2 - pValor mayor al valor guardado en pRoot
		else if (mayor(pValor, pRoot.getValor())) {
			pRoot.setHijoDerecho(insertar(pRoot.getHijoDerecho(), pValor));
		}
		
		// Caso 3 - pValor menor al valor guardado en pRoot
		else {
			pRoot.setHijoIzquierdo(insertar(pRoot.getHijoIzquierdo(), pValor));
		}
		
		return balancear(pRoot);
	}
	
	
	
	// Balanceo
	
	public AVLNode<T> balancear(AVLNode<T> pRoot) {
		actualizarBalance(pRoot);
		
		// Caso 1 - Derecho dominante
		if (pRoot.getBalance() == 2) {
			
			// Caso 1.1 - [Derecha - Derecha]
			if (pRoot.getHijoDerecho().getBalance() >= 0) {
				return rotacionIzquierda(pRoot);
			}
			// Caso 1.2 - [Derecha - Izquierda]
			else {
				pRoot.setHijoDerecho(rotacionDerecha(pRoot.getHijoDerecho()));
				return rotacionIzquierda(pRoot);
			}
		}
		// Caso 2 - Izquierdo dominante
		else if (pRoot.getBalance() == -2) {
			
			// Caso 2.1 - [Izquierda - Izquierda]
			if (pRoot.getHijoIzquierdo().getBalance() <= 0) {
				return rotacionDerecha(pRoot);
			}
			// Caso 2.2 - [Izquierda - Derecha]
			else {
				pRoot.setHijoIzquierdo(rotacionIzquierda(pRoot.getHijoIzquierdo()));
				return rotacionIzquierda(pRoot);
			}
		}
		
		// Caso 3 - Balanceado
		return pRoot;
	}
	
	public void actualizarBalance(AVLNode<T> pRoot) {
		// Se necesita obtener la altura de los hijos para calcular el balance
		int alturaIzq;
		int alturaDrch;
		
		if (pRoot.getHijoIzquierdo() == null) {
			alturaIzq = -1;
		} else { 
			alturaIzq = pRoot.getHijoIzquierdo().getAltura();
		}
		
		if (pRoot.getHijoDerecho() == null) {
			alturaDrch = -1;
		} else {
			alturaDrch = pRoot.getHijoDerecho().getAltura();
		}
		
		// Cambia el balance del nodo
		pRoot.setBalance(alturaDrch - alturaIzq);
		
		// Actualiza la altura del nodo
		pRoot.setAltura(1 + Math.max(alturaDrch, alturaIzq));
	}
	
	// Rotaciones
	
	public AVLNode<T> rotacionIzquierda(AVLNode<T> pNodo) {
		
		AVLNode<T> nuevoNodo = pNodo.getHijoDerecho();
		pNodo.setHijoDerecho(nuevoNodo.getHijoIzquierdo());
		nuevoNodo.setHijoIzquierdo(pNodo);
		
		actualizarBalance(nuevoNodo);
		actualizarBalance(pNodo);
		
		return nuevoNodo;
	}
	
	public AVLNode<T> rotacionDerecha(AVLNode<T> pNodo) {
		
		AVLNode<T> nuevoNodo = pNodo.getHijoIzquierdo();
		pNodo.setHijoIzquierdo(nuevoNodo.getHijoDerecho());
		nuevoNodo.setHijoDerecho(pNodo);
		
		actualizarBalance(nuevoNodo);
		actualizarBalance(pNodo);
		
		return nuevoNodo;
	}
	
	// Remover
	public AVLNode<T> remover(T pValor) {
		// Caso 1 - Raiz nula
		if (raiz == null) {
			return null;
		}
		
		// Caso 2 - Buscar el valor a remover desde la raiz
		else {
			raiz = remover(raiz, pValor);
			this.cantidadNodos--;
			return raiz;
		}
	}
	
	public AVLNode<T> remover(AVLNode<T> pRoot, T pValor) {
		// Caso 1 - No existe el nodo
		if (pRoot == null) {
			return null;
		}
		
		// Caso 2 - pValor menor al valor guardado en pRoot
		else if (menor(pValor, pRoot.getValor())) {
			pRoot.setHijoIzquierdo(remover(pRoot.getHijoIzquierdo(), pValor));
		}
		
		// Caso 3 - pValor mayor al valor guardado en pRoot
		else if (mayor(pValor, pRoot.getValor())) {
			pRoot.setHijoDerecho(remover(pRoot.getHijoDerecho(), pValor));
		}
		
		// Caso 4 - Se encontro el valor que se quiere remover
		else {
			
			// Caso 4.1 - No hay hijo derecho
			if (pRoot.getHijoDerecho() == null) {
				return pRoot.getHijoIzquierdo();
			}
			
			// Caso 4.2 - No hay hijo izquierdo
			else if (pRoot.getHijoIzquierdo() == null) {
				return pRoot.getHijoDerecho();
			}
			
			// Caso 4.3 - Tiene ambos hijos
			else {
				
				// Caso 4.3.1 Sub-Arbol izquierdo es mas alto
				if (pRoot.getHijoIzquierdo().getAltura() > pRoot.getHijoDerecho().getAltura()) {
					
					T nuevoValor = buscarMax(pRoot.getHijoIzquierdo());
					pRoot.setValor(nuevoValor);
					
					pRoot.setHijoIzquierdo(remover(pRoot.getHijoIzquierdo(), nuevoValor));
				} 
				// Caso 4.3.2 Sub-Arbol derecho mas o igual de alto
				else {
					
					T nuevoValor = buscarMin(pRoot.getHijoDerecho());
					pRoot.setValor(nuevoValor);
					
					pRoot.setHijoDerecho(remover(pRoot.getHijoDerecho(), nuevoValor));
					
				}
			}	
		}
		
		return balancear(pRoot);
	}
	
	
	// Comparacion de valores
	public boolean menor(T pValor1, T pValor2) {
		return pValor1.compareTo(pValor2) < 0;
	}
	
	public boolean mayor(T pValor1, T pValor2) {
		return pValor1.compareTo(pValor2) > 0;
	}
	
	public boolean iguales(T pValor1, T pValor2) {
		return pValor1.compareTo(pValor2) == 0;
	}
}