package controller.menu.eventosSeleccion;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

import controller.menu.MenuSeleccionController;
import controller.partida.PartidaHostController;

public class EventoHostear extends MouseAdapter {
	
	private MenuSeleccionController controller;
	
	public EventoHostear(MenuSeleccionController pController) {
		controller = pController;
	}
	
	public void mouseClicked(MouseEvent e) {
		
		if (controller.getPathSeleccionado() == null) {
			JOptionPane.showMessageDialog(null, "Seleccione un mapa");
			return;
		}
		
		controller.getVista().dispose();
		
		PartidaHostController newController = new PartidaHostController(
				controller.getPathSeleccionado(), 
				controller.getUsuario());
		
	}
	
}
