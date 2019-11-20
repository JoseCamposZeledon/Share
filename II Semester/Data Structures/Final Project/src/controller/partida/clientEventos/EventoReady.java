package controller.partida.clientEventos;

import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.ImageIcon;

import controller.partida.IConstants;
import controller.partida.PartidaClientController;

public class EventoReady extends MouseAdapter implements IConstants{

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
		
		try {
			Socket socketEvento = new Socket(IP, HOST_PORT);
			
			ObjectOutputStream streamOS = new ObjectOutputStream(socketEvento.getOutputStream());
			streamOS.writeObject(controller);
			streamOS.close();
			
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
	}	
	
}
