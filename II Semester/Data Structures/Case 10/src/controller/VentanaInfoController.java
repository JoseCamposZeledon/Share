package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import model.splayTree.NodoSplay;
import model.splayTree.SplayTree;
import view.VentanaInfo;

public class VentanaInfoController {
	
	VentanaInfo view;
	JButton btnInfoMenu;
	SplayTree<String> splay;
	
	public VentanaInfoController(VentanaInfo pView, JButton pBtnInfoMenu, SplayTree<String> pSplay) {
		view = pView;
		btnInfoMenu = pBtnInfoMenu;
		splay = pSplay;
		
		view.getBuscarInput().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String text = view.getBuscarInput().getText();
				NodoSplay<String> nodo = splay.buscar(text);
				if (nodo == null) {
					JOptionPane.showMessageDialog(null, "No existe sensor con tal ID", 
							"ERROR", JOptionPane.ERROR_MESSAGE);
				} else {
					view.getIdSensor().setText(nodo.getValor());
					view.getNombreLugarSensor().setText(nodo.getNodo().getValor().getLugar().getNombre());
					view.getTipoUbicacionSensor().setText(nodo.getNodo().getValor().getLugar().getClass().getSimpleName());;
					view.getConsumoActualSensor().setText(Integer.toString(nodo.getNodo().getValor().getConsumoActual()));
					if (nodo.getNodo().getPadre() != null) {
						view.getFuenteSensor().setText(nodo.getNodo().getPadre().getValor().getId());
					} else {
						view.getFuenteSensor().setText("Ninguno");
					}
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
