package main;

import java.awt.EventQueue;

import controller.MenuPrincipalController;
import model.sensor.Sensor;
import view.MenuPrincipal;

public class Main {
	
	public static void main(String[] args) {
		
		MenuPrincipal view = new MenuPrincipal();
		
		MenuPrincipalController controller = new MenuPrincipalController(view);
		
		
		(new Thread(new Runnable(){
			   public void run(){
					while (true){
						controller.actualizarConsumos(view.getArbol().getRaiz());
						controller.detectarInalcanzables(view.getArbol().getRaiz());
						
						System.out.println("----");
						try {
							Thread.sleep(5000);
						} catch (Exception ex) {
							
						}
					}		
			   }
			})).start();
		
		view.setVisible(true);
	}
	
}
