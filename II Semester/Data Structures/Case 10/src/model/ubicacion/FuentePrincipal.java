package model.ubicacion;

public class FuentePrincipal extends Ubicacion{
	
	public FuentePrincipal() {}
	
	public FuentePrincipal(String pNombre) {
		super(pNombre);
	}

	public String toString() {
		return "FUENTE_PRINCIPAL: " + nombre;
	}
}
