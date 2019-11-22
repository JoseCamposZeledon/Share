package view;

import java.awt.Color;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;

import model.arbolnario.ArbolNArio;
import model.arbolnario.NodoJTree;
import model.arbolnario.NodoNArio;
import model.jsonreader.JSonReader;
import model.sensor.Sensor;
import model.splayTree.SplayTree;

public class MenuPrincipal extends JFrame {

	private JPanel contentPane;
	private JButton btnConectar, btnDesconectar, btnVerInfo, btnLeerJson;
	private JTree tree;
	private ArbolNArio<Sensor> arbol;
	private SplayTree<String> splay;
	private JSonReader reader;
	
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
		arbol = new ArbolNArio<Sensor>();
		
		// Splay
		splay = new SplayTree<String>();
		
		// JReader
		reader = new JSonReader("save.json");
		
		// JTree
		tree = new JTree();
		
		// Quita los nodos por defecto
		DefaultTreeModel modelTree = (DefaultTreeModel) tree.getModel();
		modelTree.setRoot(null);
		modelTree.reload();
		
		// Agrega el Sensor al GUI
		
		if(reader.getNodo() != null) {
			NodoJTree<Sensor> sensorJTree = new NodoJTree<Sensor>(reader.getNodo());
			arbol.cambiarRaizNula(reader.getNodo());
			modelTree.setRoot(sensorJTree);
			loadNodosModel(modelTree, sensorJTree);
		}
		
		tree.setBorder(BorderFactory.createLineBorder(Color.gray));
		tree.setBounds(15, 15, 510, 590);
	
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		
		// Render
		contentPane.add(btnConectar);
		contentPane.add(btnDesconectar);
		contentPane.add(btnVerInfo);
		contentPane.add(tree);

	}
	
	
	private void loadNodosModel(DefaultTreeModel modelTree, NodoJTree<Sensor> parent) {
		splay.agregar(parent);
		for (NodoNArio<Sensor> nodo : parent.getNodo().getHijos()) {
			NodoJTree nodoTree = new NodoJTree(nodo);
			nodoTree.getNodo().setPadre(parent.getNodo());
			modelTree.insertNodeInto(nodoTree, parent, parent.getChildCount());
			parent.agregarHijo(nodoTree);
			modelTree.reload();
			loadNodosModel(modelTree, nodoTree);
		}
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
	
	public SplayTree<String> getSplay() {
		return splay;
	}

	public void setSplay(SplayTree<String> splay) {
		this.splay = splay;
	}
	
	

	public JSonReader getReader() {
		return reader;
	}


	public void setReader(JSonReader reader) {
		this.reader = reader;
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
