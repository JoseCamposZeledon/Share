package model.ubicacion;

public class Distrito extends Ubicacion{
	
	public Distrito() {}
	
	public Distrito(String pNombre) {
		super(pNombre);
	}

	public String toString() {
		return "DISTRITO: " + nombre;
	}
	
}
