package controller;

import view.MenuPrincipal;
import view.VentanaConectar;
import view.VentanaInfo;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;

import com.sun.glass.events.WindowEvent;

import model.*;
import model.arbolnario.NodoJTree;
import model.arbolnario.NodoNArio;
import model.sensor.Sensor;

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
					ventanaInfo, view.getBtnVerInfo(), view.getSplay());
			
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
					view.getArbol(), view.getSplay());
			
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
			
			//Pasa los hijos al padre en el GUI
			int cantidadHijos = model.getChildCount(nodoSeleccionado);
			for (int indexActual = 0; indexActual < cantidadHijos; indexActual++) {
				
				NodoJTree hijoActual = (NodoJTree) model.getChild(nodoSeleccionado, 0);
				
				model.insertNodeInto(hijoActual, (MutableTreeNode) nodoSeleccionado.getParent(), 
						nodoSeleccionado.getParent().getChildCount());
				
			}
			
			// Quita el seleccionado del GUI
			model.removeNodeFromParent(nodoSeleccionado);
			
			// Borra del splay
			view.getSplay().borrar(((Sensor) nodoSeleccionado.getNodo().getValor()).getId());
			
		}
		
	}
}
