package model.bptree;

import java.util.Arrays;

/*
 * Arbol B+
 */
public class BPTree<T extends Comparable<T>> {
	//Atributos 
	private static BPTree<?> instanciaSingleton = null;
	private BPNode<T> raiz;
	
	// Constructores 
	private BPTree() {
		instanciaSingleton = this;
	}
	
	private BPTree(int pLlavesMax) {
		this();
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
	
	// Metodos creados
	
	
	/*
	 * Si no hay instancia entonces crea una nueva con los parametros pasados
	 */
	@SuppressWarnings("rawtypes")
	public static BPTree<?> getInstanciaSingleton(int pLlavesMax) {
		if (instanciaSingleton == null) {
			instanciaSingleton = new BPTree(pLlavesMax);
		}
		return instanciaSingleton;
	}
	
	
	/*
	 * Inserta un valor desde la raiz
	 */
	public void insertar(T pValor) {
		// Caso 1, la raiz es hoja y no está llena
		if (raiz.esHoja() && raiz.nuevaLlave(pValor)) return;
		
		// Caso 2, la raíz es hoja pero está llena
		else if (raiz.esHoja() && raiz.isFull()) {
			raiz = raiz.split();
		} 
		
		// El valor a insertar es menor o igual a la menor llave
		if (pValor.compareTo(raiz.getLlaves()[0]) <= 0) {
			insertar(pValor, raiz.getHijos()[0]);
			return;
		} else if (raiz.getLlaves()[raiz.getLlavesMax() - 1] != null) {
			
			return;
		}
		
		// El valor a insertar es mayor a la primer llave
		for (int posActual = 0; posActual < raiz.getLlaves().length; posActual++) {			
			
			if (raiz.getLlaves()[posActual] == null) { // Valor a insertar mayor que mayor llave, nodo no lleno
				insertar(pValor, raiz.getHijos()[posActual]);
				break;
			}
			
			
		}
		
	}
	
	/*
	 * Inserta un valor desde un nodo especifico
	 */
	public void insertar(T pValor, BPNode<T> pRoot) {
		
		// Caso 1, el nodo es hoja y tiene espacio
		if (pRoot.esHoja() && pRoot.nuevaLlave(pValor)) return;
		
		// Caso 2, el nodo es hoja pero no tiene espacio
		
		// Caso 3, el nodo no es hoja
		else {
			
		}
	}
	
	/*
	 * MAIN
	 */
	public static void main(String[] args) {
		BPTree<Integer> test = (BPTree<Integer>) BPTree.getInstanciaSingleton(5);
		
		test.insertar(13);
		test.insertar(-3);
		test.insertar(15);
		test.insertar(-3);
		test.insertar(18);
		test.insertar(-1);
		test.insertar(300);
		
		System.out.println("Raiz: " + Arrays.deepToString(test.getRaiz().getLlaves()));
		System.out.println("Hijo 0: " + Arrays.deepToString(test.getRaiz().getHijos()[0].getLlaves()));
		System.out.println("Hijo 1: " + Arrays.deepToString(test.getRaiz().getHijos()[1].getLlaves()));
	}
	
}
