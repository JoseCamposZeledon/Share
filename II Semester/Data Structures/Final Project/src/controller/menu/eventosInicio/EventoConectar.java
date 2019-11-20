package controller.menu.eventosInicio;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;

import controller.menu.MenuPrincipalController;
import controller.partida.PartidaClientController;

public class EventoConectar extends MouseAdapter {
	
	private MenuPrincipalController controller;
	
	public EventoConectar(MenuPrincipalController pController) {
		controller = pController;
	}
	
	public void mouseClicked(MouseEvent e) {
		try {
			PartidaClientController newController = new PartidaClientController(controller.getUsuario());
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "No se encuentra ninguna partida");
			return;
		}
		
		controller.getVista().dispose();
	}
}
