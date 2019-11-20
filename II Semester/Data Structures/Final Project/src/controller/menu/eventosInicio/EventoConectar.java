package controller.menu.eventosInicio;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.JOptionPane;

import controller.menu.MenuPrincipalController;
import controller.partida.PartidaClientController;
import model.threadsPool.ThreadManager;

public class EventoConectar extends MouseAdapter {
	
	private MenuPrincipalController controller;
	
	public EventoConectar(MenuPrincipalController pController) {
		controller = pController;
	}
	
	public void mouseClicked(MouseEvent e) {
		try {
			PartidaClientController newController = PartidaClientController.createInstance(controller.getUsuario());
		} catch (IOException ex) {
			JOptionPane.showMessageDialog(null, "No se encuentra ninguna partida");
			return;
		}
		
		controller.getVista().dispose();
	}
}
