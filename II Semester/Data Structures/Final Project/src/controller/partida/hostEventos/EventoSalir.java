package controller.partida.hostEventos;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import controller.partida.PartidaHostController;

public class EventoSalir extends WindowAdapter {
	
	private PartidaHostController controller;
	
	public EventoSalir(PartidaHostController pController) {
		controller = pController;
	}
	
	public void windowClosign(WindowEvent e) {
		try {
			controller.getServer().close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
}
