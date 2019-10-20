package controller;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Hashtable;

import javax.swing.JLabel;

import model.grafo.Grafo;
import model.grafo.Nodo;

public class CalcularRutaEvento extends MouseAdapter{
	
	private ControllerPrincipal controller;
	
	
	public CalcularRutaEvento(ControllerPrincipal pController) {
		controller = pController;
	}
	
	public void mouseClicked(MouseEvent e) {
		
		if (controller.getNodoOrigen() == null || controller.getNodoDestino()== null) {
			System.out.println("VACIOS");
			return;
		}
		
		Nodo<Point> nodoOrigen = controller.getVista().getTable().get(controller.getNodoOrigen());
		Nodo<Point> nodoDestino = controller.getVista().getTable().get(controller.getNodoDestino());
		
		for(Nodo<Point> actual : controller.getModelo().buscarOrden(nodoOrigen, nodoDestino)) {
			System.out.println(actual);
		}
	}
	
}
