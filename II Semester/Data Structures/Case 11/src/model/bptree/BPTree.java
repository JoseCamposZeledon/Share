package model.bptree;

import java.util.Arrays;

/*
 * Arbol B+
 */
public class BPTree<T extends Comparable<T>> {
	//Atributos 
	private int cantidadLlaves;
	private static BPTree<?> instanciaSingleton = null;
	private BPNode<T> raiz;
	
	// Constructores 
	public BPTree() {
		instanciaSingleton = this;
	}
	
	public BPTree(int pLlavesMax, T pLlave) {
		this();
		BPNode<T> nodoRaiz = new BPNode<T>(pLlavesMax);
		this.raiz = nodoRaiz;
		nodoRaiz.nuevaLlave(pLlave);
		
	}
	
	// Setters & Getters
	public BPNode<T> getRaiz() {
		return raiz;
	}


	public void setRaiz(BPNode<T> raiz) {
		this.raiz = raiz;
	}
	
	
	public static BPTree<?> getInstanciaSingleton() {
		return instanciaSingleton;
	}
	

	public static void setInstanciaSingleton(BPTree<?> instanciaSingleton) {
		BPTree.instanciaSingleton = instanciaSingleton;
	}
	
	// Metodos creados
	
	
	
	/*
	 * MAIN
	 */
	public static void main(String[] args) {
		BPTree<Integer> test = new BPTree<Integer>(3, 10);
		
		System.out.println(Arrays.deepToString(test.getRaiz().getLlaves()));
		
		System.out.println(test.getRaiz().buscar(10, test.getRaiz().getLlaves()));
	}
	
}
