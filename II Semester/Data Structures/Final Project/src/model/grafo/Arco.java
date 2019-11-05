package model.grafo;

public class Arco<T> {
	private int peso;
	
	// Dirigido: conexion[0] -> conexion[1]
	@SuppressWarnings("unchecked")
	private Nodo<T> conexion[] = new Nodo[2];
	
	public Arco(Nodo<T> pInicio, Nodo<T> pFin) {
		conexion[0] = pInicio;
		conexion[1] = pFin;
		setPeso(Integer.MAX_VALUE);
	}
	
	public Arco(Nodo<T> pInicio, Nodo<T> pFin, int pPeso) {
		this(pInicio, pFin);
		peso = pPeso;
	}
	
	public Nodo<T>[] getConexion() {
		return conexion;
	}

	public int getPeso() {
		return peso;
	}

	public void setPeso(int peso) {
		this.peso = peso;
	}
}
