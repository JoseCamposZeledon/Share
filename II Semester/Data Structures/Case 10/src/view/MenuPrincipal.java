package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

import controller.MenuPrincipalController;
import model.arbolnario.ArbolNArio;
import model.arbolnario.NodoJTree;
import model.arbolnario.NodoNArio;
import model.sensor.Sensor;

public class MenuPrincipal extends JFrame {

	private JPanel contentPane;
	private JButton btnConectar, btnDesconectar, btnVerInfo, btnLeerJson;
	private JTree tree;
	private ArbolNArio<Sensor> arbol;
	
	/**
	 * Create the frame.
	 */
	public MenuPrincipal() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 550, 700);
		setResizable(false);
		setTitle("Sistema de Tuberías");
		
		// Ventana
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		// Botones
		btnConectar= new JButton("Conectar Sensor");
		btnConectar.setBounds(15, 620, 150, 30);
		
		btnDesconectar= new JButton("Desconectar Sensor");
		btnDesconectar.setBounds(180, 620, 180, 30);
		
		btnVerInfo = new JButton("Ver Información");
		btnVerInfo.setBounds(375, 620, 150, 30);
		
		// Arbol
		Sensor sensor = new Sensor("heredia01", 3, "HEREDIA", 350000);
		NodoNArio<Sensor> nodoSensor = new NodoNArio<Sensor>(sensor);
		
		arbol = new ArbolNArio<Sensor>(sensor);
			
		// JTree
		NodoJTree<Sensor> top = new NodoJTree<Sensor>(nodoSensor);
		tree = new JTree(top);
		tree.setBorder(BorderFactory.createLineBorder(Color.gray));
		tree.setBounds(15, 15, 510, 590);
	
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		
		// Render
		contentPane.add(btnConectar);
		contentPane.add(btnDesconectar);
		contentPane.add(btnVerInfo);
		contentPane.add(tree);
	}
	
	public JButton getBtnVerInfo() {
		return btnVerInfo;
	}
	
	public JButton getBtnConectar() {
		return btnConectar;
	}
	
	public JTree getTree() {
		return tree;
	}
	
	public ArbolNArio<Sensor> getArbol() {
		return arbol;
	}
	
	public void addBtnVerInfoListener(ActionListener listenerBtnVerInfo) {
		btnVerInfo.addActionListener(listenerBtnVerInfo);
	}
	
	public void addBtnConectarListener(ActionListener listenerBtnConectar) {
		btnConectar.addActionListener(listenerBtnConectar);
	}
	
	public void addBtnDesconectarListener(ActionListener listenerBtnDesconectar) {
		btnDesconectar.addActionListener(listenerBtnDesconectar);
	}
	
}
