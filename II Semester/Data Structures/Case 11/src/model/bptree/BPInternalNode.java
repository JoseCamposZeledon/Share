package model.bptree;

/*
 * Nodo interno de BPNode
 */
public class BPInternalNode<T> {
	
	// Atributos
	private T value;
	private BPNode<T> nodoIzq, nodoDrch;
	
	// Constructores
	public BPInternalNode() {
		nodoIzq = null;
		nodoDrch = null;
	}
	
	public BPInternalNode(T pValue) {
		this();
		value = pValue;
	}
	
	// Getters & Setters
	public T getValue() {
		return value;
	}
	public void setValue(T pValue) {
		this.value = pValue;
	}
	public BPNode<T> getNodoIzq() {
		return nodoIzq;
	}
	public void setNodoIzq(BPNode<T> pNodoIzq) {
		this.nodoIzq = pNodoIzq;
	}
	public BPNode<T> getNodoDrch() {
		return nodoDrch;
	}
	public void setNodoDrch(BPNode<T> pNodoDrch) {
		this.nodoDrch = pNodoDrch;
	}
	
	
}
