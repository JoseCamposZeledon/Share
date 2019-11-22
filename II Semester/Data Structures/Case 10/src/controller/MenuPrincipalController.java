package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;

import com.google.gson.JsonIOException;

import model.arbolnario.NodoJTree;
import model.sensor.Sensor;
import view.MenuPrincipal;
import view.MyTreeCellRenderer;
import view.VentanaConectar;
import view.VentanaInfo;

public class MenuPrincipalController {
	
	private MenuPrincipal view;
	private int consumoMax;
	private ArrayList<NodoJTree<Sensor>> sensoresInalcanzables = new ArrayList<NodoJTree<Sensor>>();
	
	public MenuPrincipalController(MenuPrincipal pView) {
		view = pView;
		this.view.addBtnVerInfoListener(new ListenerBtnVerInfo());
		this.view.addBtnConectarListener(new ListenerBtnConectar());
		this.view.addBtnDesconectarListener(new ListenerBtnDesconectar());
		this.view.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
					view.getReader().writeToFile(view.getReader().serializer(view.getArbol().getRaiz()));
				} catch (JsonIOException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				};
            	view.getSplay();
                System.exit(0);
            }
        });
		
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
				JOptionPane.showMessageDialog(null, "Seleccione la ubicaci�n del sensor", 
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

            // Borra del splay
			view.getSplay().borrar(((Sensor) nodoSeleccionado.getNodo().getValor()).getId());
		}
	}
	
	// Funcion para actualizar todos los consumos cada cierto tiempo
	public void actualizarConsumos(NodoJTree<Sensor> pRoot) {
		try {
			// Revisa si es la raiz
			if (pRoot.getNodo().getPadre() == null) {
				// Actualiza cada sensor pero no la raiz
				
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
			if (pRoot.getNodo().tieneHijos() && pRoot.getPadre() == null) {
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
					
					this.agregarHijosInalcanzables(hijoActual);
					
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
	
	public void agregarHijosInalcanzables(NodoJTree<Sensor> pRoot) {
		sensoresInalcanzables.add(pRoot);
		
		if (pRoot.getNodo().tieneHijos()) {
			for (NodoJTree<Sensor> hijoActual : pRoot.getHijos()) {
				this.agregarHijosInalcanzables(hijoActual);
			}
		}
	}
}
