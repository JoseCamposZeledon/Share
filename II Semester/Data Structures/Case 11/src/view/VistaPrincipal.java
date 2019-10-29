package view;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;

public class VistaPrincipal extends JFrame {
	
	private static VistaPrincipal instancia = null;
	private JTextField busquedaInput;
	private JTree informacionTree;
	private JLabel labelBuscado, labelComparaciones, labelComparacionesTotal;
	
	//Constructor
	private VistaPrincipal() {
		this.setSize(600, 550);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.getContentPane().setLayout(null);
		this.setTitle("Busqueda - Caso11");
		this.setResizable(false);
		
		busquedaInput = new JTextField(null);
		busquedaInput.setBounds(5, 5, 570, 25);
		this.add(busquedaInput);
		
		informacionTree = new JTree();
		informacionTree.setModel(null);
		informacionTree.setBounds(5, 50, 570, 400);
		informacionTree.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(5, 60, 570, 400);
		scrollPane.setBorder(null);
		scrollPane.setViewportView(informacionTree);
		this.add(scrollPane);
		
		labelBuscado = new JLabel();
		labelBuscado.setBounds(5, 30, 570, 25);
		this.add(labelBuscado);
		
		labelComparaciones = new JLabel("Comparaciones: ");
		labelComparaciones.setBounds(5, 475, 100, 25);
		this.add(labelComparaciones);
		
		// Este es el que cambia de acuerdo a la cantidad de comparaciones
		labelComparacionesTotal = new JLabel("----");
		labelComparacionesTotal.setBounds(110, 475, 300, 25);
		this.add(labelComparacionesTotal);
	}

	// Instancia Singleton
	public static VistaPrincipal getInstance() {
		if (instancia == null) {
			instancia = new VistaPrincipal();
		}
		return instancia;
	}

	
	/*
	 * Getters & Setters 
	 */
	
	public JLabel getLabelComparacionesTotal() {
		return this.labelComparacionesTotal;
	}
	
	public JTextField getBusquedaInput() {
		return busquedaInput;
	}

	public JTree getInformacionTree() {
		return informacionTree;
	}	

	public JLabel getLabelBuscado() {
		return labelBuscado;
	}
}
