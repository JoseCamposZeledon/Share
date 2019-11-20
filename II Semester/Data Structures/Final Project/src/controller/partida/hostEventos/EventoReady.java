package controller.partida.hostEventos;

import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.ImageIcon;

import controller.partida.IConstants;
import controller.partida.PartidaHostController;

public class EventoReady extends MouseAdapter implements IConstants{

	private PartidaHostController controller;
	
	public EventoReady(PartidaHostController pController) {
		controller = pController;
	}
	
	public void mouseClicked(MouseEvent e) {
		if (controller.isReadyHost()) {
			controller.getVista().getReadyHostLabel().setIcon(new ImageIcon(new ImageIcon(".\\static\\media\\images\\notready_button.png")
					.getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH)));
			controller.setReadyHost(false);
		} else {
			controller.getVista().getReadyHostLabel().setIcon(new ImageIcon(new ImageIcon(".\\static\\media\\images\\ready_button.png")
					.getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH)));
			controller.setReadyHost(true);
		}
		
	}	
	
}
