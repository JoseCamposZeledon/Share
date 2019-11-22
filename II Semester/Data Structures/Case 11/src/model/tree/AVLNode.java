package model.tree;

public class AVLNode<T extends Comparable<T>> {
	private T valor;
	private AVLNode<T> padre, hijoIzquierdo, hijoDerecho;
	private int altura, balance;
	
	/*
	 * CONSTRUCTORES
	 */
	public AVLNode() {
		padre = null;
		hijoIzquierdo = null;
		hijoDerecho = null;
	}
	
	public AVLNode(T pValor) {
		this();
		valor = pValor;
	}

	/*
	 * GETTERS & SETTERS
	 */
	
	
	public T getValor() {
		return valor;
	}

	public void setValor(T valor) {
		this.valor = valor;
	}

	public AVLNode<T> getPadre() {
		return padre;
	}

	public void setPadre(AVLNode<T> padre) {
		this.padre = padre;
	}

	public AVLNode<T> getHijoIzquierdo() {
		return hijoIzquierdo;
	}

	public void setHijoIzquierdo(AVLNode<T> hijoIzquierdo) {
		this.hijoIzquierdo = hijoIzquierdo;
	}

	public AVLNode<T> getHijoDerecho() {
		return hijoDerecho;
	}

	public void setHijoDerecho(AVLNode<T> hijoDerecho) {
		this.hijoDerecho = hijoDerecho;
	}

	public int getAltura() {
		return altura;
	}

	public void setAltura(int altura) {
		this.altura = altura;
	}
	
	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}
	
	/*
	 * METODOS CREADOS
	 */

	public boolean menor(T pValor) {
		return this.getValor().compareTo(pValor) < 0;
	}
	
	public boolean mayor(T pValor) {
		return this.getValor().compareTo(pValor) > 0;
	}
	
	public boolean igual(T pValor) {
		return this.getValor().compareTo(pValor) == 0;
	}
}
