package controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import view.IConstants;

public class AgregarNodoEvento extends MouseAdapter implements IConstants{
	
	private ControllerPrincipal controlador;
	
	public AgregarNodoEvento(ControllerPrincipal controlador) {
		this.controlador = controlador;
	}
	
	public void mouseClicked(MouseEvent e) {
		controlador.agregarPunto(e.getX() - RADIO, e.getY() - RADIO);
	}
}
