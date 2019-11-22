package controller.partida.hostEventos;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import controller.partida.PartidaHostController;

public class EventoPersonajeHost extends MouseAdapter{
	
	private int idThis;
	
	private static ArrayList<JLabel> labels = new ArrayList<JLabel>();
	
	public EventoPersonajeHost(int pIdThis, JLabel pLabel) {
		idThis = pIdThis;
		labels.add(pLabel);
	}
	
	public void mouseClicked(MouseEvent e) {
		PartidaHostController.getInstance().setIdPersonajeSelected(idThis);
		for (int i = 0; i < labels.size(); i++) {
			labels.get(i).setBorder(null);
		}
		
		labels.get(idThis).setBorder(BorderFactory.createLineBorder(Color.YELLOW));
		
		PartidaHostController.getInstance().notifyView();
	}
	
}
