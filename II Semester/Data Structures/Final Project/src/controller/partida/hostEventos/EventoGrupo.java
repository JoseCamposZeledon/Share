package controller.partida.hostEventos;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import controller.partida.PartidaHostController;

public class EventoGrupo extends MouseAdapter {
	
	
	public void mouseClicked(MouseEvent e) {
		PartidaHostController.getInstance().notifyView();
	}

}
