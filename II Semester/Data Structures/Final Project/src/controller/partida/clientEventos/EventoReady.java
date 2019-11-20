package controller.partida.clientEventos;

import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;

import controller.partida.PartidaClientController;

public class EventoReady extends MouseAdapter {

	private PartidaClientController controller;
	
	public EventoReady(PartidaClientController pController) {
		controller = pController;
	}
	
	public void mouseClicked(MouseEvent e) {
		if (controller.isReadyClient()) {
			controller.getVista().getReadyClientLabel().setIcon(new ImageIcon(new ImageIcon(".\\static\\media\\images\\notready_button.png")
					.getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH)));
			controller.setReadyClient(false);
		} else {
			controller.getVista().getReadyClientLabel().setIcon(new ImageIcon(new ImageIcon(".\\static\\media\\images\\ready_button.png")
					.getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH)));
			controller.setReadyClient(true);
			
		}
		
	}	
	
}
