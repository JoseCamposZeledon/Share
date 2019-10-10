package model.splayTree;

import model.arbolnario.NodoNArio;
import model.sensor.Sensor;

public class NodoSplay<T extends Comparable<T>> 
implements Comparable<NodoSplay<T>> {
	
	private NodoSplay<T> padre;
	private NodoSplay<T> hijoDerecho;
	private NodoSplay<T> hijoIzquierdo;
	private T valor;
	private NodoNArio<Sensor> nodo;
	
	public NodoSplay() {
	}
	
	public NodoSplay(T pValor) {
		setValor(pValor);
	}
	
	public NodoSplay(T pValor, NodoNArio<Sensor> pNodo) {
		this(pValor);
		setNodo(pNodo);
	}
	
	public NodoSplay<T> getPadre() {
		return padre;
	}
	public void setPadre(NodoSplay<T> padre) {
		this.padre = padre;
	}
	public NodoSplay<T> getHijoDerecho() {
		return hijoDerecho;
	}
	public void setHijoDerecho(NodoSplay<T> hijoDerecho) {
		this.hijoDerecho = hijoDerecho;
	}
	public NodoSplay<T> getHijoIzquierdo() {
		return hijoIzquierdo;
	}
	public void setHijoIzquierdo(NodoSplay<T> hijoIzquierdo) {
		this.hijoIzquierdo = hijoIzquierdo;
	}
	public T getValor() {
		return valor;
	}
	public void setValor(T valor) {
		this.valor = valor;
	}
	public NodoNArio<Sensor> getNodo() {
		return nodo;
	}
	public void setNodo(NodoNArio<Sensor> nodo) {
		this.nodo = nodo;
	}

	public String toString() {
		return getValor().toString();
	}
	
	@Override
	public int compareTo(NodoSplay<T> obj) {
		return getValor().compareTo(obj.getValor());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NodoSplay<T> other = (NodoSplay<T>) obj;
		if (valor == null) {
			if (other.valor != null)
				return false;
		} else if (!valor.equals(other.valor))
			return false;
		return true;
	}

	
	public boolean esHijoIzquierdo() {
		return this.equals(getPadre().getHijoIzquierdo());
	}
	
	
	public boolean esRaiz() {
		return getPadre() == null;
	}
	
	
	public void rotacionIzquierda() {
		NodoSplay<T> abuelo = getPadre().getPadre();
		// Mueve los nodos en la dirección opuesta a las manecillas del reloj
		// El hijo derecho del nodo padre se convierte en el hijo izquierdo del actual
		getPadre().setHijoDerecho(getHijoIzquierdo());
		if (getHijoIzquierdo() != null) {
			getHijoIzquierdo().setPadre(getPadre());
		}
		// Determina si el padre es un hijo derecho o un hijo izquierdo e intercambia
		// el hijo del abuelo que le corresponde al padre por el actual, notar que no es necesario si el padre es raiz
		if (abuelo != null) {
			if (getPadre().esHijoIzquierdo()) {
				abuelo.setHijoIzquierdo(this);
			} else {
				abuelo.setHijoDerecho(this);
			}
		}
		// El padre se convierte en el hijo izquierdo del actual
		setHijoIzquierdo(getPadre());
		getPadre().setPadre(this);
		// El abuelo del actual se convierte en su padre
		setPadre(abuelo);
	}
	
	
	public void rotacionDerecha() {
		NodoSplay<T> abuelo = getPadre().getPadre();
		// Mueve los nodos en la dirección de las manecillas del reloj
		// El hijo derecho del nodo padre se convierte en el hijo izquierdo del actual, notar que no es necesario si el padre es raiz
		getPadre().setHijoIzquierdo(getHijoDerecho());
		if (getHijoDerecho() != null) {
			getHijoDerecho().setPadre(getPadre());
		}
		// Determina si el padre es un hijo derecho o un hijo izquierdo e intercambia
		// el hijo del abuelo que le corresponde al padre por el actual
		if (abuelo != null) {
			if (getPadre().esHijoIzquierdo()) {
				abuelo.setHijoIzquierdo(this);
			} else {
				abuelo.setHijoDerecho(this);
			}
		}
		// El padre se convierte en el hijo izquierdo del actual
		setHijoDerecho(getPadre());
		getPadre().setPadre(this);
		// El abuelo del actual se convierte en su padre
		setPadre(abuelo);
	}
	
	
	public void zigZag(boolean derechaIzquierda) {
		if (derechaIzquierda) {
			rotacionDerecha();
			rotacionIzquierda();
		} else {
			rotacionIzquierda();
			rotacionDerecha();
		}
	}
	
	
	public void zigZig(boolean izquierdaIzquierda) {
		if (izquierdaIzquierda) {
			getPadre().rotacionDerecha();
			rotacionDerecha();
		} else {
			getPadre().rotacionIzquierda();
			rotacionIzquierda();
		}
	}
	
	
	public void zig(boolean izquierda) {
		if (izquierda) {
			rotacionDerecha();
		} else {
			rotacionIzquierda();
		}
	}
	
	
	public static void main(String args[]) {
		NodoSplay<String> dab = new NodoSplay<String>("aaaa");
		NodoSplay<String> jeff = new NodoSplay<String>("bbb");
		System.out.println(dab.compareTo(jeff));
	}
	
}
