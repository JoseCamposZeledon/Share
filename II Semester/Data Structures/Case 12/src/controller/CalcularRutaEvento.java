package controller;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Hashtable;

import javax.swing.JLabel;

import model.grafo.Grafo;
import model.grafo.Nodo;

public class CalcularRutaEvento extends MouseAdapter{
	
	private JLabel origen = null;
	private JLabel destino = null;
	private Hashtable<JLabel, Nodo<Point>> table;
	private Grafo<Nodo<Point>> grafo;
	
	
	public CalcularRutaEvento(JLabel pOrigen, JLabel pDestino, 
			Hashtable<JLabel, Nodo<Point>> pTable,
			Grafo<Nodo<Point>> pGrafo) {
		origen = pOrigen;
		destino = pDestino;
		table = pTable;
		grafo = pGrafo;
	}
	
	public void mouseClicked(MouseEvent e) {
		
		if (origen == null || destino == null) {
			return;
		}
		
		Nodo<Point> nodoOrigen = table.get(origen);
		Nodo<Point> nodoDestino = table.get(destino);
		
		for(Nodo<Point> actual : grafo.buscarOrden(nodoOrigen, nodoDestino)) {
			System.out.println(actual);
		}
	}
	
}
