package controller.partida.clientEventos;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import controller.partida.PartidaClientController;
import controller.partida.PartidaHostController;

public class EventoClientPersonaje extends MouseAdapter {
	private int idThis;
	
	private static ArrayList<JLabel> labels = new ArrayList<JLabel>();
	
	public EventoClientPersonaje(int pIdThis, JLabel pLabel) {
		idThis = pIdThis;
		labels.add(pLabel);
	}
	
	public void mouseClicked(MouseEvent e) {
		PartidaClientController.getInstance().setIdPersonajeSelected(idThis);
		for (int i = 0; i < labels.size(); i++) {
			labels.get(i).setBorder(null);
		}
		
		labels.get(idThis).setBorder(BorderFactory.createLineBorder(Color.YELLOW));
		
		PartidaClientController.getInstance().notifyView();
	}
	
}	
