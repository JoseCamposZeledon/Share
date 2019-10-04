package model.ubicacion;

public abstract class Ubicacion {
	protected String nombre;
	
	public Ubicacion() {}
	
	public Ubicacion(String pNombre) {
		this.setNombre(pNombre);
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}
