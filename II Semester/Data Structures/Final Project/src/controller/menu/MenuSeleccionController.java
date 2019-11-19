package controller.menu;

import controller.menu.eventosSeleccion.EventoCargarMapa;
import controller.menu.eventosSeleccion.EventoVolver;
import model.account.Account;
import view.menu.VistaSeleccionMapa;

public class MenuSeleccionController {
	
	VistaSeleccionMapa vista = new VistaSeleccionMapa();
	Account usuario;
	String pathSeleccionado;
	
	public MenuSeleccionController(Account pUsuario) {
		usuario = pUsuario;
		vista.getUserLabel().setText(usuario.toString());
		
		// Evento cargar mapa
		vista.getBtnCargarMapa().addMouseListener(new EventoCargarMapa(this));
		
		// Evento Hostear partida
		
		
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
