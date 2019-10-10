package controller;

import view.MenuPrincipal;
import view.MyTreeCellRenderer;
import view.VentanaConectar;
import view.VentanaInfo;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;

import model.*;
import model.arbolnario.ArbolNArio;
import model.arbolnario.NodoJTree;
import model.arbolnario.NodoNArio;
import model.sensor.Sensor;

public class MenuPrincipalController {
	
	private MenuPrincipal view;
	private int consumoMax;
	private ArrayList<NodoJTree<Sensor>> sensoresInalcanzables = new ArrayList<NodoJTree<Sensor>>();
	
	public MenuPrincipalController(MenuPrincipal pView) {
		view = pView;
		this.view.addBtnVerInfoListener(new ListenerBtnVerInfo());
		this.view.addBtnConectarListener(new ListenerBtnConectar());
		this.view.addBtnDesconectarListener(new ListenerBtnDesconectar());
	}
	
	
	
	class ListenerBtnVerInfo implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			VentanaInfo ventanaInfo = new VentanaInfo();
			
			view.getBtnVerInfo().setEnabled(false);
			
			VentanaInfoController controllerInfo = new VentanaInfoController(
					ventanaInfo, view.getBtnVerInfo());
			
			ventanaInfo.setVisible(true);
			
		}
		
	}
	
	class ListenerBtnConectar implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			if (view.getTree().getLastSelectedPathComponent() == null &&
					!view.getArbol().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Seleccione la ubicación del sensor", 
						"ERROR", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			VentanaConectar conectarSensor = new VentanaConectar();
			
			view.getBtnConectar().setEnabled(false);
			
			VentanaConectarController controllerConectar = new VentanaConectarController(
					conectarSensor, view.getBtnConectar(), view.getTree(), 
					(NodoJTree) view.getTree().getLastSelectedPathComponent(),
					view.getArbol());
			
			conectarSensor.setVisible(true);
		}
		
	}
	
	class ListenerBtnDesconectar implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			NodoJTree nodoSeleccionado = (NodoJTree) view.getTree().getLastSelectedPathComponent();
			if (nodoSeleccionado == null) {
				JOptionPane.showMessageDialog(null, "Seleccione el sensor que se va a desconectar", 
						"ERROR", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			DefaultTreeModel model = (DefaultTreeModel)view.getTree().getModel();
			
			// Se deshace de todo si se remuve la raiz
			if (nodoSeleccionado.isRoot()) {
				nodoSeleccionado.removeAllChildren();
				view.getArbol().quitarNodo(nodoSeleccionado.getNodo());
				model.setRoot(null);
				model.reload();
				return;
			}
			
			// Mueve los nodos hijos al padre en el arbol
			view.getArbol().quitarNodo(nodoSeleccionado.getNodo());
			nodoSeleccionado.borrarNodo();
			
			//Pasa los hijos al padre en el GUI
			int cantidadHijos = model.getChildCount(nodoSeleccionado);
			for (int indexActual = 0; indexActual < cantidadHijos; indexActual++) {
				
				NodoJTree hijoActual = (NodoJTree) model.getChild(nodoSeleccionado, 0);
				
				model.insertNodeInto(hijoActual, (MutableTreeNode) nodoSeleccionado.getParent(), 
						nodoSeleccionado.getParent().getChildCount());
				
			}
			
			// Quita el seleccionado del GUI
			model.removeNodeFromParent(nodoSeleccionado);
			model.reload();
		}
	}
	
	// Funcion para actualizar todos los consumos cada cierto tiempo
	public void actualizarConsumos(NodoJTree<Sensor> pRoot) {
		try {
			// Revisa si es la raiz
			if (pRoot.getNodo().getPadre() == null) {
				// Actualiza cada sensor pero no la raiz
				
				pRoot.getNodo().getValor().actualizarConsumo();
				
				for (NodoJTree<Sensor> hijoActual : pRoot.getHijos()) {
					this.actualizarConsumos(hijoActual);
				}
				
			} else if (pRoot.getNodo().tieneHijos()){ // Caso en el que no es la raiz y tiene hijos				
				
				pRoot.getNodo().getValor().actualizarConsumo();
				
				for (NodoJTree<Sensor> hijoActual : pRoot.getHijos()) {
					this.actualizarConsumos(hijoActual);
				}
				
			} else { // Caso en el que no es raiz y no tiene hijos
				pRoot.getNodo().getValor().actualizarConsumo();
			}
			
		} catch (NullPointerException e) {

		}
	}
	
	public void detectarInalcanzables(NodoJTree<Sensor> pRoot) {
		sensoresInalcanzables.clear();
		sensoresInalcanzables.trimToSize();
		try {
			if (pRoot.getNodo().tieneHijos()) {
				for (NodoJTree<Sensor> hijoActual : pRoot.getHijos()) {
					this.detectarInalcanzables(hijoActual, 0);
				}
			}
		} catch (NullPointerException e) {

		}
	}
	
	// 
	public void detectarInalcanzables(NodoJTree<Sensor> pRoot, int pCantidadActual) {
		
		consumoMax = view.getArbol().getRaiz().getValor().getConsumoBase();
		
		// Caso en que un hijo tenga un consumo mayor a la fuente principal
		if (pRoot.getNodo().getValor().getConsumoActual() > consumoMax) { 
			
			sensoresInalcanzables.add(pRoot);
			
		} else if (pRoot.getNodo().tieneHijos()) {
			for (NodoJTree<Sensor> hijoActual : pRoot.getHijos()) {
				
				// Caso en que la suma del consumo del hijo con el de toda la rama sea mas que el consumo maximo
				if (hijoActual.getNodo().getValor().getConsumoActual() + pRoot.getNodo().getValor().getConsumoActual()
						> consumoMax) {
					sensoresInalcanzables.add(hijoActual);	
					
				} else {
					this.detectarInalcanzables(hijoActual, pCantidadActual + pRoot.getNodo().getValor().getConsumoActual());
				}	
			}	
		}
	}
	
	public void actualizarNodos() {
		MyTreeCellRenderer render = new MyTreeCellRenderer();
		render.setSensores(sensoresInalcanzables);
		
		
		if (!view.getArbol().isEmpty()) {
			view.getTree().setCellRenderer(render);
		}
	}
}
