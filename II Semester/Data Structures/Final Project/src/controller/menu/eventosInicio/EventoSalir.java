package controller.menu.eventosInicio;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import controller.menu.MenuPrincipalController;

public class EventoSalir extends MouseAdapter {
	
	private MenuPrincipalController controller;
	
	public EventoSalir(MenuPrincipalController pController) {
		controller = pController;
	}
	
	public void mouseClicked(MouseEvent e) {
		controller.getVista().dispose();
	}
	
}
