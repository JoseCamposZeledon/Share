package controller.menu.eventosSeleccion;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JOptionPane;

import controller.menu.MenuSeleccionController;

public class EventoCargarMapa extends MouseAdapter{
	private MenuSeleccionController controller;
	
	public EventoCargarMapa(MenuSeleccionController pController) {
		controller = pController;
	}
	
	public void mouseClicked(MouseEvent e) {
		Path path = Paths.get(controller.getVista().getMapaPathInput().getText().trim());
		
		if (Files.exists(path) && !controller.getVista().getMapaPathInput().getText().trim().equals("")) {
			controller.setPathSeleccionado(controller.getVista().getMapaPathInput().getText().trim());
			controller.getVista().getMapaPathInput().setText("");
			
			controller.getVista().getBorderMapa1().setBorder(null);
			controller.getVista().getBorderMapa2().setBorder(null);
			controller.getVista().getBorderMapa3().setBorder(null);
		} else {
			controller.getVista().getMapaPathInput().setText("");
			JOptionPane.showMessageDialog(null, "Path invalido");
		}
		
		
	}
}
