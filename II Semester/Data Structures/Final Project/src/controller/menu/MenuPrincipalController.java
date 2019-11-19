package controller.menu;

import controller.menu.eventosInicio.EventoHostear;
import controller.menu.eventosInicio.EventoSalir;
import model.account.Account;
import view.menu.VistaMenuPrincipal;

public class MenuPrincipalController {
	VistaMenuPrincipal vista = new VistaMenuPrincipal();
	
	Account usuario;
	
	public MenuPrincipalController(Account pUsuario) {
		usuario = pUsuario;
		vista.getUserLabel().setText(usuario.toString());
		// Evento salir
		vista.getBtnSalir().addMouseListener(new EventoSalir(this));
		
		// Evento conectar
		
		// Evento hostear
		vista.getBtnHostear().addMouseListener(new EventoHostear(this));
	}
	
	
	
	public VistaMenuPrincipal getVista() {
		return vista;
	}

	
	
	public Account getUsuario() {
		return usuario;
	}



	public static void main(String[] args) {
		MenuPrincipalController a = new MenuPrincipalController(new Account("correo@ac.om", "A"));
	}
}
