package controller.menu;

import controller.menu.eventosSeleccion.EventoHostear;
import controller.menu.eventosSeleccion.EventoCargarMapa;
import controller.menu.eventosSeleccion.EventoSeleccionDefault;
import controller.menu.eventosSeleccion.EventoVolver;
import model.account.Account;
import view.menu.VistaSeleccionMapa;

public class MenuSeleccionController implements IConstants{
	
	private VistaSeleccionMapa vista = new VistaSeleccionMapa();
	private Account usuario;
	private String pathSeleccionado;
	
	public MenuSeleccionController(Account pUsuario) {
		usuario = pUsuario;
		vista.getUserLabel().setText(usuario.toString());
		
		// Evento cargar mapa
		vista.getBtnCargarMapa().addMouseListener(new EventoCargarMapa(this));
		
		// Evento seleccion mapas default
		vista.getPreviewMapa1().addMouseListener(new EventoSeleccionDefault(this, MAPA_1));
		vista.getPreviewMapa2().addMouseListener(new EventoSeleccionDefault(this, MAPA_2));
		vista.getPreviewMapa3().addMouseListener(new EventoSeleccionDefault(this, MAPA_3));
		
		// Evento Hostear partida
		vista.getBtnHostear().addMouseListener(new EventoHostear(this));
		
		// Evento volver
		vista.getBtnVolver().addMouseListener(new EventoVolver(this));
		
	}

	public VistaSeleccionMapa getVista() {
		return vista;
	}

	public void setVista(VistaSeleccionMapa vista) {
		this.vista = vista;
	}

	public Account getUsuario() {
		return usuario;
	}

	public void setUsuario(Account usuario) {
		this.usuario = usuario;
	}

	public String getPathSeleccionado() {
		return pathSeleccionado;
	}

	public void setPathSeleccionado(String pathSeleccionado) {
		this.pathSeleccionado = pathSeleccionado;
	}
}
