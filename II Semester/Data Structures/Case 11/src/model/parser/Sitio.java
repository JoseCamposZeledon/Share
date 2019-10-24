package model.parser;

public class Sitio {
	
	private int profundidadActual;
	private int anchuraActual;
	private String link;
	protected SitioPadre padre;
	
	public Sitio() {
		this.profundidadActual = 1;
		this.anchuraActual = 1;
	}
	
	public Sitio(String link) {
		this();
		this.link = link;
	}

	public Sitio(int profundidadActual, int anchuraActual, String link) {
		this.profundidadActual = profundidadActual;
		this.anchuraActual = anchuraActual;
		this.link = link;
	}

	public int getProfundidadActual() {
		return profundidadActual;
	}
	
	public void setProfundidadActual(int profundidadActual) {
		this.profundidadActual = profundidadActual;
	}
	
	public int getAnchuraActual() {
		return anchuraActual;
	}
	
	public void setAnchuraActual(int anchuraActual) {
		this.anchuraActual = anchuraActual;
	}
	
	public String getLink() {
		return link;
	}
	
	public void setLink(String link) {
		this.link = link;
	}
	
	public SitioPadre getPadre() {
		return padre;
	}

	public void setPadre(SitioPadre padre) {
		this.padre = padre;
	}

	public void incrementarAnchura() {
		anchuraActual++;
	}
	
}
