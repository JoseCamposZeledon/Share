package controller;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Hashtable;
import java.util.Observable;

import javax.swing.JComponent;
import javax.swing.JLabel;

import model.grafo.Grafo;
import model.grafo.Nodo;
import view.IConstants;
import view.ViewPrincipal;

public class ControllerPrincipal extends Observable implements IConstants{
	private ViewPrincipal vista;
	private Grafo<Nodo<Point>> modelo;
	private Nodo<Point> nodoAnterior;
	private Hashtable<JLabel, Nodo<Point>> table = new Hashtable<JLabel, Nodo<Point>>();
	
	public ControllerPrincipal(ViewPrincipal pVista, Grafo<Nodo<Point>> pModelo) {
		setVista(pVista);
		setModelo(pModelo);
		nodoAnterior = null;
		
		this.addObserver(pVista);
		
		vista.setVisible(true);
			
		vista.getPanel().addMouseListener(new MouseAdapter()
				{
				public void mouseClicked(MouseEvent e) {
					agregarPunto(e.getX() - RADIO, e.getY() - RADIO);
				}
			});
		
		vista.getBtnBuscarRuta().addMouseListener(new MouseAdapter()
				{
				public void mouseClicked(MouseEvent e) {
					Nodo<Point> nodo1 = vista.getNodosSelected().get(0);
					Nodo<Point> nodo2 = vista.getNodosSelected().get(1);
					
					getModelo().buscarOrden(nodo1, nodo2);
					
				}
			});
	}
	
	public void agregarPunto(int pCoordX, int pCoordY) {
		Point punto = new Point(pCoordX, pCoordY);
		Nodo<Point> nodo = new Nodo<Point>(punto);
		
		if (this.getNodoAnterior() == null) {
			this.setNodoAnterior(nodo);
		} else {
			this.getNodoAnterior().getAdjacentes().add(nodo);
			nodo.getAdjacentes().add(this.getNodoAnterior());
			this.setNodoAnterior(nodo);
		}
		
		this.getModelo().agregarNodo(nodo);
		this.setChanged();
		this.notifyObservers(nodo);
	}
	
	
	
	public ViewPrincipal getVista() {
		return vista;
	}

	public Nodo<Point> getNodoAnterior() {
		return nodoAnterior;
	}

	public void setNodoAnterior(Nodo<Point> nodoAnterior) {
		this.nodoAnterior = nodoAnterior;
	}

	public void setVista(ViewPrincipal vista) {
		this.vista = vista;
	}

	public Grafo<Nodo<Point>> getModelo() {
		return modelo;
	}

	public void setModelo(Grafo<Nodo<Point>> modelo) {
		this.modelo = modelo;
	}
	
	
	
}
