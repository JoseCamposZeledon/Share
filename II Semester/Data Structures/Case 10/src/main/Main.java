package main;

import java.awt.EventQueue;

import controller.MenuPrincipalController;
import view.MenuPrincipal;

public class Main {
	
	public static void main(String[] args) {
		
		MenuPrincipal view = new MenuPrincipal();
		
		MenuPrincipalController controller = new MenuPrincipalController(view);
		
		view.setVisible(true);
	}
	
}
