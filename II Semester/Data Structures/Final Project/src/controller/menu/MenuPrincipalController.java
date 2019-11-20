package controller.menu;

import controller.menu.eventosInicio.EventoConectar;
import controller.menu.eventosInicio.EventoHostear;
import controller.menu.eventosInicio.EventoSalir;
import model.account.Account;
import view.menu.VistaMenuPrincipal;

public class MenuPrincipalController {
	private VistaMenuPrincipal vista = new VistaMenuPrincipal();
	
	private Account usuario;
	
	public MenuPrincipalController(Account pUsuario) {
		usuario = pUsuario;
		vista.getUserLabel().setText(usuario.toString());
		// Evento salir
		vista.getBtnSalir().addMouseListener(new EventoSalir(this));
		
		// Evento conectar
		vista.getBtnConectar().addMouseListener(new EventoConectar(this));
		
		// Evento hostear
		vista.getBtnHostear().addMouseListener(new EventoHostear(this));
	}
	
	
	
	public VistaMenuPrincipal getVista() {
		return vista;
	}

	
	
	public Account getUsuario() {
		return usuario;
	}
}
