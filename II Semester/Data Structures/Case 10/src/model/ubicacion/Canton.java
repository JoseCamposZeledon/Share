package model.ubicacion;

public class Canton extends Ubicacion{
	
	public Canton() {}
	
	public Canton(String pNombre) {
		super(pNombre);
	}
	
	public String toString() {
		return "CANTON: " + nombre;
	}
}
