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

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.event.AncestorListener;

import model.grafo.Grafo;
import model.grafo.Nodo;
import view.IConstants;
import view.ViewPrincipal;

public class ControllerPrincipal extends Observable implements IConstants{
	private ViewPrincipal vista;
	private Grafo<Nodo<Point>> modelo;
	private Nodo<Point> nodoAnterior;
	
	private JLabel nodoOrigen, nodoDestino;
	
	public ControllerPrincipal(ViewPrincipal pVista, Grafo<Nodo<Point>> pModelo) {
		setVista(pVista);
		setModelo(pModelo);
		nodoAnterior = null;
		this.addObserver(pVista);
		
		vista.setVisible(true);
			
		AgregarNodoEvento evento = new AgregarNodoEvento(this);
		
		vista.getPanel().addMouseListener(evento);
		
		// Quita todos los otros eventos y pone nuevos
		vista.getBtnBuscarRuta().addMouseListener(new BuscarRutaEvento(this, evento));
	}
	
	public void agregarPunto(int pCoordX, int pCoordY) {
		Point punto = new Point(pCoordX, pCoordY);
		Nodo<Point> nodo = new Nodo<Point>(punto);
		
		if (this.getNodoAnterior() != null) {
			this.getNodoAnterior().getAdjacentes().add(nodo);
			nodo.getAdjacentes().add(this.getNodoAnterior());
		}
		
		this.getModelo().agregarNodo(nodo);
		
		this.setNodoAnterior(nodo);
		
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

	public JLabel getNodoOrigen() {
		return nodoOrigen;
	}

	public void setNodoOrigen(JLabel nodoOrigen) {
		this.nodoOrigen = nodoOrigen;
	}

	public JLabel getNodoDestino() {
		return nodoDestino;
	}

	public void setNodoDestino(JLabel nodoDestino) {
		this.nodoDestino = nodoDestino;
	}
	
}
