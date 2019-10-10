package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import view.VentanaInfo;

public class VentanaInfoController {
	
	VentanaInfo view;
	JButton btnInfoMenu;
	
	public VentanaInfoController(VentanaInfo pView, JButton pBtnInfoMenu) {
		view = pView;
		btnInfoMenu = pBtnInfoMenu;
		
		view.getBuscarInput().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String text = view.getBuscarInput().getText();
				System.out.println(text);
				if (text == "a") {
					JOptionPane.showMessageDialog(null, "No existe sensor con tal ID", 
							"ERROR", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		
		// Reactiva el boton al cerrar
		view.addWindowListener(new WindowAdapter() {
			public void windowClosed(WindowEvent e) {
				btnInfoMenu.setEnabled(true);
			}
		});
		
		
	}
	
}
