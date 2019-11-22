package main;

import java.awt.Point;
import java.lang.ModuleLayer.Controller;

import controller.ControllerPrincipal;
import model.grafo.Grafo;
import model.grafo.Nodo;
import view.ViewPrincipal;

public class Main {

	public static void main(String[] args) {
		ViewPrincipal vista = new ViewPrincipal();
		Grafo<Nodo<Point>> modelo = new Grafo<Nodo<Point>>();
		ControllerPrincipal controller = new ControllerPrincipal(vista, modelo);
	}

}
