package model.parser;

public class SitioDerivado extends Sitio {
	
	SitioDerivado(int profundidadActual, int anchuraActual, String link, SitioPadre padre) {
		super(profundidadActual, anchuraActual, link);
		this.padre = padre;
	}
	
}
