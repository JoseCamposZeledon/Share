package model.tree;

public class AVLTree<T extends Comparable<T>> {

	private AVLNode<T> raiz;
	private int cantidadNodos;
	
	/*
	 * CONSTRUCTORES
	 */
	public AVLTree() {
		raiz = null;
	}
	
	public AVLTree(T pValor) {
		raiz = new AVLNode<T>(pValor);
	}
	
	/*
	 * SETTERS & GETTERS
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

	public void setCantidadNodos(int cantidadNodos) {
		this.cantidadNodos = cantidadNodos;
	}
	
	/*
	 * METODOS CREADOS
	 */
	
	public int getAlturaArbol() {
		if (raiz == null) return 0;
		return this.raiz.getAltura();
	}
	
	public boolean esVacio() {
		return raiz == null;
	}
	
	/*
	 * Busca el valor desde la raiz, 
	 * Si no lo encuentra retorna null
	 */
	public T buscar(T pValor) {
		return buscar(raiz, pValor);
	}
	
	
	/*
	 * Busca el valor desde un nodo especifico,
	 * Si no lo encuentra retorna null
	 */
	public T buscar(AVLNode<T> pRoot, T pValor) {
		// Caso 1 - Nodo vacio [No existe el valor]
		if (pRoot == null) return null;
		
		// Caso 2 - pValor es igual al valor de pRoot
		else if(pRoot.igual(pValor)) return pValor; 
		
		// Caso 3 - pValor es menor al valor dentro de pRoot
		else if(pRoot.mayor(pValor)) {
			return buscar(pRoot.getHijoIzquierdo(), pValor);
		}
		
		// Caso 4 - pValor es mayor al valor dentro de pRoot
		else {
			return buscar(pRoot.getHijoDerecho(), pValor);
		}
		
	}
	
	/*
	 * Inserta un valor nuevo, empezando de la raiz
	 */
	public void insertar(T pValor) {
		// Caso 1 - Raiz Vacia
		if (raiz == null) {
			raiz = new AVLNode<T>(pValor);
			return;
		}
		// Caso 2 - Ya existe raiz
		else {
			raiz = insertar(raiz, pValor);
		}
	}
	
	
	/*
	 * Inserta un valor nuevo en un nodo especifico
	 */
	public AVLNode<T> insertar(AVLNode<T> pRoot, T pValor) {
		// Caso 1 - pRoot es nulo, quiere decir que se encontro el lugar de insercion
		if (pRoot == null) {
			pRoot = new AVLNode<T>(pValor);
			return pRoot;
		}
		
		// Caso 2 - pValor es menor a pRoot
		else if (pRoot.menor(pValor)) {
			pRoot.setHijoIzquierdo(insertar(pRoot.getHijoIzquierdo(), pValor));
		}
		
		// Caso 3 - pValor es mayor a pRoot
		else if (pRoot.mayor(pValor)) {
			pRoot.setHijoDerecho(insertar(pRoot.getHijoDerecho(), pValor));
		}
		
		pRoot.setAltura(pRoot.getAltura() + 1);
		
		pRoot = balancear(pRoot);
		return pRoot;
	}
	
	/*
	 * Balancea el arbol de ser necesario
	 */
	public AVLNode<T> balancear(AVLNode<T> pRoot) {
		int balance = calcularNuevoBalance(pRoot);
		
		// Caso 1 -  Izquierdo dominante
		if (balance == -2) {
			
			// Caso 1.1 - [Izquierdo - Izquierdo]
			if (pRoot.getHijoIzquierdo().getBalance() <= 0) {
				pRoot = rotacionDerecha(pRoot);
			}
			
			// Caso 1.2 - [Izquierdo - Derecho]
			else {
				pRoot.setHijoIzquierdo(rotacionIzquierda(pRoot.getHijoIzquierdo()));
				pRoot = rotacionIzquierda(pRoot);
			}

		}
		
		// Caso 2 - Derecho dominante
		else if (balance == 2) {
			
			// Caso 2.1 - [Derecho - Derecho]
			if (pRoot.getHijoDerecho().getBalance() >= 0) {
				pRoot = rotacionIzquierda(pRoot);
			}			
			
			// Caso 2.2 - [Derecho - Izquierdo]
			else {
				pRoot.setHijoDerecho(rotacionDerecha(pRoot.getHijoDerecho()));
				pRoot = rotacionDerecha(pRoot);
			}
			
		}
		
		return pRoot;
	}
	
	/*
	 *  Calcula el nuevo balance de pRoot
	 */
	public int calcularNuevoBalance(AVLNode<T> pRoot) {
		
		// Se tienen que calcular las alturas de los hijos de pRoot
		int alturaIzquierda;
		int alturaDerecha;
		
		if (pRoot.getHijoDerecho() == null) { // Se calcula la altura del derecho
			alturaDerecha = -1;
		} else {
			alturaDerecha = pRoot.getHijoDerecho().getAltura();
		}
		
		if (pRoot.getHijoIzquierdo() == null) { // Se calcula la altura del izquierdo
			alturaIzquierda = -1;
		} else {
			alturaIzquierda = pRoot.getHijoIzquierdo().getAltura();
		}
		
		// Calculo del factor de balance
		int nuevoBalance = alturaDerecha - alturaIzquierda;
		pRoot.setBalance(nuevoBalance);
		return nuevoBalance;
	}
	
	/*
	 * Rotacion hacia la derecha
	 */
	public AVLNode<T> rotacionDerecha(AVLNode<T> pRoot) {
		AVLNode<T> nuevoPadre = pRoot.getHijoIzquierdo();
		
		pRoot.setHijoIzquierdo(nuevoPadre.getHijoDerecho());
		
		nuevoPadre.setHijoDerecho(pRoot);
		
		pRoot.setBalance(calcularNuevoBalance(pRoot));
		nuevoPadre.setBalance(calcularNuevoBalance(nuevoPadre));
		return nuevoPadre;
	}
	
	/*
	 * Rotacion hacia la izquierda
	 */
	public AVLNode<T> rotacionIzquierda(AVLNode<T> pRoot) {
		AVLNode<T> nuevoPadre = pRoot.getHijoDerecho();
		
		pRoot.setHijoDerecho(nuevoPadre.getHijoIzquierdo());
		
		nuevoPadre.setHijoIzquierdo(pRoot);
		
		pRoot.setBalance(calcularNuevoBalance(pRoot));
		nuevoPadre.setBalance(calcularNuevoBalance(nuevoPadre));
		return nuevoPadre;
	}
	
	public static void main(String[] args) {
		AVLTree<Integer> test = new AVLTree<Integer>(6);
		test.insertar(14);
		test.insertar(20);
		test.insertar(134);
		test.insertar(133);
		
		System.out.println(test.getRaiz().getValor());
		System.out.println(test.getRaiz().getHijoIzquierdo().getValor());
	}
}
	
