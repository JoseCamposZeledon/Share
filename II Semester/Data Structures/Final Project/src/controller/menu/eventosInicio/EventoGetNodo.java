package controller.menu.eventosInicio;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.JOptionPane;

import controller.partida.JuegoController;
import controller.partida.PartidaClientController;
import controller.partida.PartidaHostController;
import model.grafo.Arco;
import model.grafo.GrafoTile;
import model.grafo.Nodo;

public class EventoGetNodo extends MouseAdapter {
		
	public void mouseClicked(MouseEvent e) {
		int x1 = e.getX();
		int y1 = e.getY();

		
		int truncatedX = (x1 / 32) * 32;
		int truncatedY = (y1 /32) * 32;
		
		Nodo<GrafoTile> nodo = JuegoController.getInstance().getMapaNodos().get(new Point(truncatedX, truncatedY));
	}
	
}
