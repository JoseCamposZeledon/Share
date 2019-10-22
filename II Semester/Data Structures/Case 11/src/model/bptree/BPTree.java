package model.bptree;

import java.util.Arrays;

/*
 * Arbol B+
 */
public class BPTree<T extends Comparable<T>> {
	//Atributos 
	private static final int DEFAULT_KEY_AMOUNT = 9;
	private static BPTree<?> instanciaSingleton = null;
	private int llavesMax;
	private BPNode<T> raiz;
	
	// Constructores 
	private BPTree() {
		instanciaSingleton = this;
	}
	
	private BPTree(int pLlavesMax) {
		this();
		this.setLlavesMax(pLlavesMax);
		BPNode<T> nodoRaiz = new BPNode<T>(pLlavesMax);
		this.raiz = nodoRaiz;
	}
	
	// Setters & Getters
	public BPNode<T> getRaiz() {
		return raiz;
	}


	public void setRaiz(BPNode<T> raiz) {
		this.raiz = raiz;
	}
	
	public int getLlavesMax() {
		return llavesMax;
	}

	public void setLlavesMax(int llavesMax) {
		this.llavesMax = llavesMax;
	}
	
	/*
	 * Inserta el valor, iniciando la busqueda de donde insertar desde la raiz
	 */
	public void insertar(T pLlave) {
		// Caso 1 - Raiz es hoja y no esta llena
		if (getRaiz().esHoja() && getRaiz().insertarLlave(pLlave)) return; 
		
		// Caso 2 - Raiz es hoja y esta llena
		if (getRaiz().esHoja() && !getRaiz().insertarLlave(pLlave)) {
			raiz = this.splitNodo(raiz);
		}
		
		// Caso 3 - Se tiene que insertar en el lugar especifico
		insertar(pLlave, raiz);
	}
	
	
	/*
	 * Inserta el valor en el nodo especifico
	 */
	public void insertar(T pLlave, BPNode<T> pRoot) {
		// Caso 1 - pRoot es hoja
		if (pRoot.esHoja()) {
			// Caso 1.1 - pRoot tiene espacio para insertar
			if (pRoot.insertarLlave(pLlave)) return;
			
			// Caso 1.2 - pRoot no tiene espacio apara insertar
			pRoot = splitNodo(pRoot);
		}
		
		// Caso 3 - pRoot no es hoja entonces se tiene que buscar el lugar apropiado
			
		// Caso 3.1 - pLlave es menor o igual a pRoot[0] (Menor llave)
		if (pLlave.compareTo(pRoot.getLlaves()[0]) <= 0) {
			insertar(pLlave, pRoot.getHijos()[0]);
			return;
		}
		// Caso 3.2 - pLlave es mayor a la mayor llave de pRoot
		int posMayor = pRoot.getPosMayor();
			
		if (pLlave.compareTo(pRoot.getLlaves()[posMayor - 1]) > 0) {
			insertar(pLlave, pRoot.getHijos()[posMayor]);
		}
			
		// Caso 3.3 - pLlave esta en medio de dos valores
		else {
			for (int posActual = 0; posActual < pRoot.getLlavesMax(); posActual++) {
				// Validacion para que no itere sobre llaves vacias
				if (pRoot.getLlaves()[posActual + 1] == null) break;
				
				if(pLlave.compareTo(pRoot.getLlaves()[posActual]) > 0 &&
					pLlave.compareTo(pRoot.getLlaves()[posActual + 1]) <= 0) {
					insertar(pLlave, pRoot.getHijos()[posActual + 1]);
					break;
				}
			}
		}
	}
	
	
	/*
	 * Separa el nodo en 2
	 */
	public BPNode<T> splitNodo(BPNode<T> pNodo) {
		int mitad = pNodo.getLlavesMax() / 2;
		
		// Caso 1 - El nodo que se parte es raiz
		if (getRaiz() == pNodo) {
			
			BPNode<T> nuevaRaiz = new BPNode<T>(getLlavesMax());
			nuevaRaiz.insertarLlave(pNodo.getLlaves()[mitad]);
			
			BPNode<T> hijoDerecho = new BPNode<T>(getLlavesMax());
			
			// No se necesita llamar a insertarLlave porque ya estan ordenados
			this.splitNodoAux(pNodo, hijoDerecho);
			
			nuevaRaiz.getHijos()[0] = pNodo;
			nuevaRaiz.getHijos()[1] = hijoDerecho;
			
			pNodo.setSiguiente(hijoDerecho);
			
			pNodo.setPadre(nuevaRaiz);
			hijoDerecho.setPadre(nuevaRaiz);
			
			return nuevaRaiz;
		}
		
		// Caso 2 - El nodo que se parte no es raiz y no es hoja
		else if(pNodo.getPadre() != null && !pNodo.esHoja()) {
			
			return pNodo;
		}
		
		// Caso 3 - El nodo que se parte no es raiz y es hoja
		else {
			BPNode<T> padre = pNodo.getPadre();
			BPNode<T> hijoDerecho = new BPNode<T>(this.getLlavesMax());
			
			if (padre.insertarLlave(pNodo.getLlaves()[mitad])) {
				pNodo = splitNodoAux(pNodo, hijoDerecho);
			}
			padre.insertarHijo(hijoDerecho);
			
			pNodo.setSiguiente(hijoDerecho);
			hijoDerecho.setPadre(padre);
			return padre;
		}
		
	}
	
	/*
	 * Obtiene las llaves e hijos que se van a quitar del original y los inserta en el nuevo (derecho)
	 * Retorna el nodoNuevoDerecho con las llaves e hijos que se movieron del original
	 */
	public BPNode<T> splitNodoAux(BPNode<T> nodoOriginal, BPNode<T> nodoNuevoDerecho) {
		int mitad = this.getLlavesMax() / 2;
		
		for (int posNodo = 0, posDerecho = mitad + 1; posDerecho < getLlavesMax(); posNodo++, posDerecho++) {
			nodoNuevoDerecho.getLlaves()[posNodo] = nodoOriginal.removerLlave(nodoOriginal.getLlaves()[mitad + 1]);
			nodoNuevoDerecho.getHijos()[posNodo] = nodoOriginal.removerHijo(nodoOriginal.getHijos()[mitad + 1]);
			
			// Iteracion extra para que se agregue el ultimo hijo
			if (posDerecho == getLlavesMax() - 1) {
				nodoNuevoDerecho.getHijos()[posNodo + 1] = nodoOriginal.removerHijo(nodoOriginal.getHijos()[mitad + 1]);
			}
		}
		
		return nodoNuevoDerecho;
	}
	
	/*
	 *  Instancia el arbol con la cantidad de llaves indicada
	 */
	public static BPTree<?> instanciarTree(int pCantidadLlaves) {
		if (instanciaSingleton == null) {
			instanciaSingleton = new BPTree(pCantidadLlaves);
		}
		return instanciaSingleton;
	}
	
	/*
	 * Retorna el arbol instanciado
	 * - Si no hay arbol crea uno nuevo con la cantidad por defecto de llaves (9)
	 */
	@SuppressWarnings("rawtypes")
	public static BPTree<?> getTree() {
		if (instanciaSingleton == null) {
			instanciaSingleton = new BPTree(DEFAULT_KEY_AMOUNT);
		}
		return instanciaSingleton;
	}
	
	/*
	 * MAIN
	 */
	public static void main(String[] args) {
		
		BPTree<Integer> test = (BPTree<Integer>) BPTree.instanciarTree(3);
		
		test.insertar(13);
		test.insertar(31);
		test.insertar(24);
		test.insertar(39);
		test.insertar(-10);
		test.insertar(21);
		
		System.out.println("Raiz: " + Arrays.deepToString(test.getRaiz().getLlaves()));
		for (int i = 0; i < test.getLlavesMax(); i++) {
			if (test.getRaiz().getHijos()[i] == null) continue;
			System.out.println("Hijo " + i + ": " + Arrays.deepToString(test.getRaiz().getHijos()[i].getLlaves()));
		}
		
	}
	
}
