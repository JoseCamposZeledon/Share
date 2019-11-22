package controller.partida.clientEventos;

import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import controller.partida.IConstants;
import controller.partida.PartidaClientController;
import controller.partida.PartidaHostController;

public class EventoReady extends MouseAdapter implements IConstants{

	private PartidaClientController controller;
	
	public EventoReady(PartidaClientController pController) {
		controller = pController;
	}
	
	public void mouseClicked(MouseEvent e) {
		if (PartidaClientController.getInstance().getClientPlayer().isCrownPlaced()) {
		
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
				
				DataOutputStream streamOS = new DataOutputStream(socketEvento.getOutputStream());
				streamOS.writeBoolean(controller.isReadyClient());
				streamOS.close();
				
			} catch (UnknownHostException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else {
			JOptionPane.showMessageDialog(null, "Coloque la corona");
		}
	}	
	
}
