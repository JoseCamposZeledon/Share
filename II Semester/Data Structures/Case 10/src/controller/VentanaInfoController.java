package controller;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;

import view.VentanaInfo;

public class VentanaInfoController {
	
	VentanaInfo view;
	JButton btnInfoMenu;
	
	public VentanaInfoController(VentanaInfo pView, JButton pBtnInfoMenu) {
		view = pView;
		btnInfoMenu = pBtnInfoMenu;
		
		// Reactiva el boton al cerrar
		view.addWindowListener(new WindowAdapter() {
			public void windowClosed(WindowEvent e) {
				btnInfoMenu.setEnabled(true);
			}
		});
		
	}
	
}
