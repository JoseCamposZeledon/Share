package model.tree;

import java.util.ArrayList;
import java.util.HashMap;

import model.parser.PalabrasRepetidas;

public class AVLTree2<T extends Comparable<T>> {
	
	public static HashMap<String, Link> mapaLinks;
	private AVLNode<T> raiz;
	private int cantidadNodos;
	public static int iteraciones;
	
	/*
	 * CONSTRUCTORES
	 */
	public AVLTree2() {
		raiz = null;
		iteraciones = 0;
		mapaLinks = new HashMap<String, Link>();
	}
	
	public AVLTree2(T pValor) {
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

	
	/*
	 * METODOS CREADOS
	 */
	
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
	
	public T buscarMin(AVLNode<T> pRoot) {

		while(pRoot.getHijoDerecho() != null) {
			pRoot = pRoot.getHijoIzquierdo();
		}
		
		return pRoot.getValor();
	}
	
	public HashMap<String, Integer> get5Max(String link) {
		HashMap<String, Integer> resultado = new HashMap<String, Integer>();
		ArrayList<PalabrasRepetidas> aux = new ArrayList<PalabrasRepetidas>();
		getMaxAux(raiz, aux, mapaLinks.get(link).getMax());
		for (PalabrasRepetidas lista : aux) {
			for (String palabra : lista.getPalabras()) {
				System.out.println(palabra);
				
			}
		} 
		while (resultado.size() <= 5) {
			for (PalabrasRepetidas lista : aux) {
				if (resultado.size() <= 5) {
					for (String palabra : lista.getPalabras()) {
						if (resultado.size() <= 5) {
							resultado.put(palabra, lista.getRepeticiones() - mapaLinks.get(link).getMin());
						}
					}
				}
			}
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
	
	public ArrayList<Link> getMinMax(int min, int max) {
		ArrayList<Link> resultado = new ArrayList<Link>();
		for (Link link : mapaLinks.values()) {
			int currentMin, currentMax;
			if (min + link.getMin() <= link.getMax()) {
				currentMin = min + link.getMin();
				if (max + link.getMin() <= link.getMax()) {
					currentMax = max + link.getMin();
				} else {
					currentMax = link.getMax();
				}
				if (getMinMaxAux((AVLNode<PalabrasRepetidas>) raiz, currentMin, currentMax)) {
					resultado.add(link);
				}
			} 
		}
		return null;
	}
	
	public boolean getMinMaxAux(AVLNode<PalabrasRepetidas> pRoot, int min, int max) {
		iteraciones++;
		// Caso 1 - No existe
		if (pRoot == null) {
			return false;
		}
		
		if (pRoot.getValor().getRepeticiones() >= min && pRoot.getValor().getRepeticiones() <= max) {
			return true;
		}
		
		
		else if (pRoot.getValor().getRepeticiones() < min) {
			return getMinMaxAux(pRoot.getHijoDerecho(), min, max);
		}
		
		else {
			return getMinMaxAux(pRoot.getHijoIzquierdo(), min, max);
		}
	}
	
	public T buscarMin(AVLNode<T> pRoot) {

		while(pRoot.getHijoDerecho() != null) {
			pRoot = pRoot.getHijoIzquierdo();
		}
		
		return pRoot.getValor();
	}
	
	public void insertar(T pValue) {
		if (pValue == null) return;
		if (buscar(pValue) == null) {
			raiz = insertar(raiz, pValue);
			cantidadNodos++;
			return;
	    }
	}
	
	// Inserta a pValue en el nodo necesario
	private AVLNode<T> insertar(AVLNode<T> pRoot, T pValue) {

		// Caso 1 - Se encontro la posicion
		if (pRoot == null) return new AVLNode<T>(pValue);

		// Caso 2 - pValue es menor que el valor en pRoot
		if (this.menor(pRoot.getValor(), pValue)) {
			pRoot.setHijoIzquierdo(insertar(pRoot.getHijoIzquierdo(), pValue));

			// Caso 3 - pValue es mayor que el valor en pRoot
		} else {
			pRoot.setHijoDerecho(insertar(pRoot.getHijoDerecho(), pValue));
		}

		    // Actualiza el balancear y la altura del nodo
		actualizarAlturas(pRoot);

		    // Se balanceara el arbol
		return balancear(pRoot);
		}
	// Balanceo
	
	 // Re-Balancea el arbol
	private AVLNode<T> balancear(AVLNode<T> pRoot) {

		// Izquierda dominante
		if (pRoot.getBalance() == -2) {

			// IZQ - IZQ
			if (pRoot.getHijoIzquierdo().getBalance() <= 0) {
				return izquierdaIzquierda(pRoot);

				// IZQ - DRCH
			} else {
				return izquierdaDerecha(pRoot);
			}

			// Derecha dominante
		} else if (pRoot.getBalance() == 2) {

			// DRCH - DRCH
			if (pRoot.getHijoDerecho().getBalance() >= 0) {
				return derechaDerecha(pRoot);

				// DRCH - IZQ
			} else {
				return derechaIzquierda(pRoot);
			}
		}

		// Esta balanceado
		return pRoot;
	}
	
	 // Actualiza las alturas y balances de pRoot
	private void actualizarAlturas(AVLNode<T> pRoot) {
		int alturaIzq;

		if (pRoot.getHijoIzquierdo() == null) {
			alturaIzq = -1;
		} else {
			alturaIzq = pRoot.getHijoIzquierdo().getAltura();
		}
		int alturaDrch; 
		if (pRoot.getHijoDerecho() == null) {
			alturaDrch = -1;
		} else {
			alturaDrch = pRoot.getHijoDerecho().getAltura();
		}

		// Actualiza la altura
		pRoot.setAltura(1 + Math.max(alturaIzq, alturaDrch));

		// Actualiza el balance
		pRoot.setBalance(alturaDrch - alturaIzq);
	}
			
	// Rotaciones
	private AVLNode<T> izquierdaIzquierda(AVLNode<T> node) {
		return rotacionDerecha(node);
	}
	
	private AVLNode<T> izquierdaDerecha(AVLNode<T> node) {
		node.setHijoIzquierdo(rotacionIzquierda(node.getHijoIzquierdo()));
		return izquierdaIzquierda(node);
	}

	private AVLNode<T> derechaDerecha(AVLNode<T> node) {
		return rotacionIzquierda(node);
	}

	private AVLNode<T> derechaIzquierda(AVLNode<T> node) {
		node.setHijoDerecho(rotacionDerecha(node.getHijoDerecho()));
		return derechaDerecha(node);
	}

	private AVLNode<T> rotacionIzquierda(AVLNode<T> node) {
		AVLNode<T> nuevoNodo = node.getHijoDerecho();
		node.setHijoDerecho(nuevoNodo.getHijoIzquierdo());
		nuevoNodo.setHijoIzquierdo(node);
		actualizarAlturas(node);
		actualizarAlturas(nuevoNodo);
		return nuevoNodo;
	}

	private AVLNode<T> rotacionDerecha(AVLNode<T> node) {
		AVLNode<T> nuevoNodo = node.getHijoIzquierdo();
	    node.setHijoIzquierdo(nuevoNodo.getHijoDerecho());
	    nuevoNodo.setHijoDerecho(node);
	    actualizarAlturas(node);
	    actualizarAlturas(nuevoNodo);
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
