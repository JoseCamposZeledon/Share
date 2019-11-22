package controller;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Hashtable;
import java.util.Observable;

import javax.swing.JLabel;

import model.grafo.Nodo;

public class NodoClickedEvento extends MouseAdapter {
	
	Hashtable<JLabel, Nodo<Point>> table;
	JLabel label;
	Nodo<Point> nodo;
	ControllerPrincipal observable;
	
	public NodoClickedEvento(JLabel pLabel, Hashtable<JLabel, Nodo<Point>> pTable, Observable o) {
		this.label = pLabel;
		this.table = pTable;
		this.observable = (ControllerPrincipal) o;
	}
	
	public void mouseClicked(MouseEvent e) {
		Nodo<Point> nodoClicked = table.get(label);
		Nodo<Point> nodoAnterior = observable.getNodoAnterior();
		
		// Conecta dos nodos ya existentes
		if (!(nodoClicked.getAdjacentes().contains(nodoAnterior) ||
			  nodoClicked.equals(nodoAnterior))) {
			nodoClicked.getAdjacentes().add(observable.getNodoAnterior());
			observable.getNodoAnterior().getAdjacentes().add(nodoClicked);
		}
		
		observable.setNodoAnterior(nodoClicked);
	}		
}
