package controller.menu.eventosSeleccion;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import controller.menu.MenuPrincipalController;
import controller.menu.MenuSeleccionController;

public class EventoVolver extends MouseAdapter {
	
	private MenuSeleccionController controller;
	
	public EventoVolver(MenuSeleccionController pController) {
		controller = pController;
	}
	
	public void mouseClicked(MouseEvent e) {
		controller.getVista().dispose();
		
		MenuPrincipalController newController = new MenuPrincipalController(controller.getUsuario());
	}
	
}
