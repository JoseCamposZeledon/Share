package controller;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Queue;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import model.grafo.Grafo;
import model.grafo.Nodo;

public class CalcularRutaEvento extends MouseAdapter implements IConstants{
	
	private ControllerPrincipal controller;
	
	
	public CalcularRutaEvento(ControllerPrincipal pController) {
		controller = pController;
	}
	
	public void mouseClicked(MouseEvent e) {

		// Validacion para que exista un origen y un destino
		if (controller.getNodoOrigen() == null || controller.getNodoDestino()== null) {
			JOptionPane.showMessageDialog(null, "Debe haber un origen y un destino");
			return;
		}
		
		Hashtable<JLabel, Nodo<Point>> table = controller.getVista().getTable();
		
		Nodo<Point> nodoOrigen = table.get(controller.getNodoOrigen());
		Nodo<Point> nodoDestino = table.get(controller.getNodoDestino());
		
		Queue<Nodo<Point>> puntosRecorridos = controller.getModelo().buscarOrden(nodoOrigen, nodoDestino);
		
		
		// Thread para pintar la ruta con un delay
		(new Thread(new Runnable(){
			   public void run(){
				   for(JLabel actual : controller.getVista().getLabelNodos()) {
						if (puntosRecorridos.contains(table.get(actual))) {
							actual.setBackground(Color.ORANGE);
						}
						
						try {
							Thread.sleep(DELAY);
						} catch (Exception ex) {
							
						}
						
					}
			   }
			})).start();
			
	}
	
}
