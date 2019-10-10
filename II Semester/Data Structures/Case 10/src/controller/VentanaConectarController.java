package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import model.arbolnario.ArbolNArio;
import model.arbolnario.NodoJTree;
import model.arbolnario.NodoNArio;
import model.sensor.Sensor;
import view.VentanaConectar;

public class VentanaConectarController {
	
	VentanaConectar view;
	JButton btnConectarMenu;
	JTree tree;
	ArbolNArio<Sensor> arbol;
	NodoJTree<Sensor> node;
	
	public VentanaConectarController(VentanaConectar pView, 
			JButton pBtnConectarMenu, JTree pTree, NodoJTree<Sensor> pNode,
			ArbolNArio<Sensor> pArbol) {
		
		view = pView;
		btnConectarMenu = pBtnConectarMenu;
		tree = pTree;
		node = pNode;
		arbol = pArbol;
		
		DefaultComboBoxModel<String> tipoUbicacion;
		
		Vector<String> ubicaciones = new Vector<String>();
		
		if (arbol.isEmpty()) {
			ubicaciones.add("Fuente Principal");
		} else {
			ubicaciones.add("Canton");
			ubicaciones.add("Distrito");
			ubicaciones.add("Barrio");
			view.setFuenteText(node.toString());
		}
		
		tipoUbicacion = new DefaultComboBoxModel<String>(ubicaciones);
		view.getTipoUbicacionInput().setModel(tipoUbicacion);
	
		this.view.addBtnConectarListener(new btnConectarListener());
		
		// Reactiva el boton al cerrar la ventana
		view.addWindowListener(new WindowAdapter() {
			public void windowClosed(WindowEvent e) {
				btnConectarMenu.setEnabled(true);
			}
		});
		
	}
	
	class btnConectarListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String id = view.getIdText();
			String nombre = view.getNombreLugarText();
			int consumo = view.getConsumoInput();
			int tipoUbicacion = view.getComboUbicacion();
			
			if (id.isEmpty() || nombre.isEmpty()) {
				JOptionPane.showMessageDialog(null, "INGRESE LA INFORMACI�N NECESARIA", 
						"ERROR", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			if (consumo == Integer.MIN_VALUE || consumo <= 0) {
				JOptionPane.showMessageDialog(null, "INGRESE UN N�MERO V�LIDO", 
						"ERROR", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			// Crea un nuevo sensor dependiendo de la cantidad de nodos en el arbol
			Sensor sensorNuevo;
			
			if (!arbol.isEmpty()) {
				sensorNuevo = new Sensor(id, tipoUbicacion, nombre, consumo);
			} else {
				sensorNuevo = new Sensor(id, 3, nombre, consumo);
			}
			
			NodoNArio<Sensor> sensorNodo = new NodoNArio<Sensor>(sensorNuevo);
			
			// Agrega el hijo en el arbol
			if (arbol.isEmpty()) {	
				arbol.cambiarRaizNula(sensorNodo);
			} else {
				arbol.agregarNodo(node.getNodo(), sensorNodo);
			}
				
			// Agrega el Sensor al GUI
			NodoJTree<Sensor> sensorJTree = new NodoJTree<Sensor>(sensorNodo);
			
			DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
			
			if(arbol.getCantidadNodos() == 1) {
				model.setRoot(sensorJTree);
			} else {
				model.insertNodeInto(sensorJTree, node, node.getChildCount());
				System.out.println("Hijos del Seleccionado. " + node.getNodo().getCantidadHijos());
			}
			
			
			System.out.println("NODOS EN ARBOL: " + arbol.getCantidadNodos());
			
			view.dispose();
		}
		
	}
}
