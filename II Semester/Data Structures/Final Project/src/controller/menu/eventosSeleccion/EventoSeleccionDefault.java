package controller.menu.eventosSeleccion;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;

import controller.menu.IConstants;
import controller.menu.MenuSeleccionController;

public class EventoSeleccionDefault extends MouseAdapter implements IConstants{
	
	private MenuSeleccionController controller;
	private int seleccionado;
	
	private static Icon borderSelected = new ImageIcon(new ImageIcon(".\\static\\media\\images\\selected_border.png")
			.getImage().getScaledInstance(157, 107, Image.SCALE_SMOOTH));
	
	private static Icon borderNormal = new ImageIcon(new ImageIcon(".\\static\\media\\images\\board_border.png")
			.getImage().getScaledInstance(157, 107, Image.SCALE_SMOOTH));
	
	public EventoSeleccionDefault(MenuSeleccionController pController, int pSeleccionado) {
		controller = pController;
		seleccionado = pSeleccionado;
	}
	
	public void mouseEntered(MouseEvent e) {
		switch (seleccionado) {
			case MAPA_1:
				controller.getVista().getBorderMapa1().setIcon(borderSelected);
				return;
			case MAPA_2:
				controller.getVista().getBorderMapa2().setIcon(borderSelected);
				return;
			case MAPA_3:
				controller.getVista().getBorderMapa3().setIcon(borderSelected);
				return;
		}
	}
	
	public void mouseExited(MouseEvent e) {
		switch (seleccionado) {
		case MAPA_1:
			controller.getVista().getBorderMapa1().setIcon(borderNormal);
			return;
		case MAPA_2:
			controller.getVista().getBorderMapa2().setIcon(borderNormal);
			return;
		case MAPA_3:
			controller.getVista().getBorderMapa3().setIcon(borderNormal);
			return;
		}
	}
	
	public void mouseClicked(MouseEvent e) {
		switch (seleccionado) {
		case MAPA_1:
			controller.getVista().getBorderMapa1().setBorder(BorderFactory.createLineBorder(Color.YELLOW));
			controller.getVista().getBorderMapa2().setBorder(null);
			controller.getVista().getBorderMapa3().setBorder(null);
			
			controller.setPathSeleccionado(".\\static\\maps\\mapa1.json");
			return;
		case MAPA_2:
			controller.getVista().getBorderMapa2().setBorder(BorderFactory.createLineBorder(Color.YELLOW));
			controller.getVista().getBorderMapa3().setBorder(null);
			controller.getVista().getBorderMapa1().setBorder(null);
			
			controller.setPathSeleccionado(".\\static\\maps\\mapa2.json");
			return;
		case MAPA_3:
			controller.getVista().getBorderMapa3().setBorder(BorderFactory.createLineBorder(Color.YELLOW));
			controller.getVista().getBorderMapa1().setBorder(null);
			controller.getVista().getBorderMapa2().setBorder(null);
			
			controller.setPathSeleccionado(".\\static\\maps\\mapa3.json");
			return;
		}
	}
	
}
