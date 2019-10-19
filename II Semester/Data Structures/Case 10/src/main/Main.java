package main;

import java.awt.EventQueue;

import controller.MenuPrincipalController;
import model.arbolnario.NodoJTree;
import model.sensor.Sensor;
import view.MenuPrincipal;

public class Main {
	
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
						
						System.out.println("----");
						
						try {
							Thread.sleep(3000);
						} catch (Exception ex) {
							
						}
					}		
			   }
			})).start();
		
		view.setVisible(true);
	}
	
}
