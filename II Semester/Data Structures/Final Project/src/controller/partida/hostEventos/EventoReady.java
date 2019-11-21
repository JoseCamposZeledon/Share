package controller.partida.hostEventos;

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
import model.threadsPool.ThreadManager;

public class EventoReady extends MouseAdapter implements IConstants{

	private PartidaHostController controller;
	
	public EventoReady(PartidaHostController pController) {
		controller = pController;
	}
	
	public void mouseClicked(MouseEvent e) {
		if (PartidaHostController.getInstance().getHostPlayer().isCrownPlaced()) {
			if (controller.isReadyHost()) {
				controller.getVista().getReadyHostLabel().setIcon(new ImageIcon(new ImageIcon(".\\static\\media\\images\\notready_button.png")
						.getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH)));
				controller.setReadyHost(false);
			} else {
				controller.getVista().getReadyHostLabel().setIcon(new ImageIcon(new ImageIcon(".\\static\\media\\images\\ready_button.png")
						.getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH)));
				controller.setReadyHost(true);
			}
			
			// Conecta un nuevo socket para actualizar el estado en la pantalla del client
			try {
				Socket socketEvento = new Socket(IP, CLIENT_PORT);
				DataOutputStream streamOS = new DataOutputStream(socketEvento.getOutputStream());
				streamOS.writeBoolean(controller.isReadyHost());
				streamOS.close();
			} catch (UnknownHostException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		
		} else {
		
			JOptionPane.showMessageDialog(null, "Coloque la corona");
		}
	}
}
