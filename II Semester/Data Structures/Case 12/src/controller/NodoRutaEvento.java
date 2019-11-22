package controller;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;

public class NodoRutaEvento extends MouseAdapter{
	
	private ControllerPrincipal controller;
	
	private static JLabel origen, destino;
	
	public NodoRutaEvento(ControllerPrincipal pController) {
		controller = pController;
	}
	
	public void mouseClicked(MouseEvent e) {
		
		if(controller.getVista().getEstadoOrigen()) {
			if(controller.getNodoOrigen() != null) origen.setBackground(Color.RED);
			origen = (JLabel) e.getComponent();
			origen.setBackground(Color.green);
			controller.setNodoOrigen(origen);
		} else {
			if(controller.getNodoDestino() != null) destino.setBackground(Color.RED);
			destino = (JLabel) e.getComponent();
			destino.setBackground(Color.YELLOW);
			controller.setNodoDestino(destino);
		}
		
	}
	
}
