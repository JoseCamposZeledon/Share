package controller;

import view.MenuPrincipal;
import view.VentanaConectar;
import view.VentanaInfo;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;

import model.*;
import model.arbolnario.NodoJTree;

public class MenuPrincipalController {
	
	MenuPrincipal view;
	
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
				model.reload();
				model.setRoot(null);
				return;
			}
			
			// Mueve los nodos hijos al padre 
			view.getArbol().quitarNodo(nodoSeleccionado.getNodo());
			
			int cantidadHijos = model.getChildCount(nodoSeleccionado);
			for (int indexActual = 0; indexActual < cantidadHijos; indexActual++) {
				NodoJTree hijoActual = (NodoJTree) model.getChild(nodoSeleccionado, 0);
				
				model.insertNodeInto(hijoActual, (MutableTreeNode) nodoSeleccionado.getParent(), 
						nodoSeleccionado.getParent().getChildCount());
				
			}
			
			model.removeNodeFromParent(nodoSeleccionado);
			
		}
		
	}
}
