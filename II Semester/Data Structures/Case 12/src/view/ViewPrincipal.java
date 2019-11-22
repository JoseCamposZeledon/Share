package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.NodoClickedEvento;
import model.grafo.Nodo;

public class ViewPrincipal extends JFrame implements Observer, IConstants{
	private JButton btnBuscarRuta;

	private JLabel backgroundLabel;
	private ImagePanel panel;
	ArrayList<JLabel> labelNodos;
	
	private boolean estadoOrigen;
	
	private Hashtable<JLabel, Nodo<Point>> table = new Hashtable<JLabel, Nodo<Point>>();

	private ArrayList<Nodo<Point>> nodosSelected = new ArrayList<Nodo<Point>>();

	public ViewPrincipal() {
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(1000, 600);
		
		this.setResizable(false);
		
		BufferedImage background = null;
		try {
			background = ImageIO.read(new File("D:\\Caso-10 ED\\University-Shared\\II Semester\\Data Structures\\Case 12\\static\\MAPA.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		labelNodos = new ArrayList<JLabel>();
		
		Image backgroundImage = background.getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH);
		panel = new ImagePanel(backgroundImage);
		
		this.setContentPane(panel);
		
		btnBuscarRuta = new JButton("Buscar Ruta");
		btnBuscarRuta.setBounds(0, 0, 150, 25);

		this.add(btnBuscarRuta);
	}
	
	public ArrayList<Nodo<Point>> getNodosSelected() {
		return nodosSelected;
	}

	public void setNodosSelected(ArrayList<Nodo<Point>> nodosSelected) {
		this.nodosSelected = nodosSelected;
	}
	
	public ImagePanel getPanel() {
		return panel;
	}

	public void setPanel(ImagePanel panel) {
		this.panel = panel;
	}
	
	public JButton getBtnBuscarRuta() {
		return btnBuscarRuta;
	}

	public void setBtnBuscarRuta(JButton btnBuscarRuta) {
		this.btnBuscarRuta = btnBuscarRuta;
	}

	public JLabel getBackgroundLabel() {
		return backgroundLabel;
	}

	public void setBackgroundLabel(JLabel backgroundLabel) {
		this.backgroundLabel = backgroundLabel;
	}
	
	public Hashtable<JLabel, Nodo<Point>> getTable() {
		return table;
	}

	public void setTable(Hashtable<JLabel, Nodo<Point>> table) {
		this.table = table;
	}

	@Override
	public void update(@SuppressWarnings("deprecation") Observable o, Object arg) {
		Nodo<Point> nodo = (Nodo<Point>) arg;
		JLabel labelNodo = dibujarNodo(nodo, o);
		this.add(labelNodo);
		
		this.repaint();
	}
	
	public JLabel dibujarNodo(Nodo<Point> pNodo, Observable o) {
		JLabel labelNodo = new JLabel();
		labelNodo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		labelNodo.setBounds(pNodo.getValor().x, pNodo.getValor().y, 2 * RADIO, 2 * RADIO);
		
		labelNodo.setBackground(Color.RED);
		labelNodo.setOpaque(true);
		
		NodoClickedEvento evento = new NodoClickedEvento(labelNodo, table, o);
		labelNodo.addMouseListener(evento); 
		
		labelNodos.add(labelNodo);
		this.getTable().put(labelNodo, pNodo);
		
		return labelNodo;
	}

	public ArrayList<JLabel> getLabelNodos() {
		return labelNodos;
	}

	public void setLabelNodos(ArrayList<JLabel> labelNodos) {
		this.labelNodos = labelNodos;
	}

	public boolean getEstadoOrigen() {
		return estadoOrigen;
	}

	public void setEstadoOrigen(boolean estadoOrigen) {
		this.estadoOrigen = estadoOrigen;
	}
}
