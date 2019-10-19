package main;

import java.awt.EventQueue;

import controller.MenuPrincipalController;
import model.arbolnario.NodoJTree;
import model.sensor.Sensor;
import view.MenuPrincipal;

public class Main implements IConstants{
	
	public static void main(String[] args) {
		
		MenuPrincipal view = new MenuPrincipal();
		
		MenuPrincipalController controller = new MenuPrincipalController(view);
		
		(new Thread(new Runnable(){
			   public void run(){
					while (true){
						
						NodoJTree<Sensor> root = (NodoJTree<Sensor>) view.getTree().getModel().getRoot();
						
						controller.actualizarConsumos(root);
						controller.detectarInalcanzables(root);
						controller.actualizarNodos();
						
						
						
						try {
							Thread.sleep(DELAY);
						} catch (Exception ex) {
							
						}
					}		
			   }
			})).start();
		
		view.setVisible(true);
	}
	
}
