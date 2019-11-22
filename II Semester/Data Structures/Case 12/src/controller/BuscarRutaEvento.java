package controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JRadioButton;

import view.ViewPrincipal;

public class BuscarRutaEvento extends MouseAdapter{

	private ControllerPrincipal controller;
	private MouseListener eventoRemover;
	
	public BuscarRutaEvento(ControllerPrincipal pController, MouseListener evento) {
		controller = pController;
		eventoRemover = evento;
	}
	
	public void mouseClicked(MouseEvent e) {
		ViewPrincipal vista = controller.getVista();
		
		// Aparece el boton de calcular ruta, creado en el
		JButton btnCalcularRuta = new JButton("Calcular Ruta");
		
		btnCalcularRuta.addMouseListener(new CalcularRutaEvento(controller));
		
		btnCalcularRuta.setBounds(0, 0, 150, 25);
		vista.add(btnCalcularRuta);
		
		// Crea botones de radio para seleccionar origen y destino
		ButtonGroup botones = new ButtonGroup();
		
		JRadioButton origen = new JRadioButton("Origen", false);
		origen.setBounds(0, 30, 150, 25);
		origen.setOpaque(false);
		
		JRadioButton destino = new JRadioButton("Destino", false);
		destino.setBounds(0, 60, 150, 25);
		destino.setOpaque(false);
		vista.add(origen);
		vista.add(destino);
	
		origen.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				destino.setSelected(false);
				origen.setSelected(true);
				controller.getVista().setEstadoOrigen(origen.isSelected());
			}
		});
		
		destino.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				origen.setSelected(false);
				destino.setSelected(true);
				controller.getVista().setEstadoOrigen(origen.isSelected());
			}
		});
		
		botones.add(origen);
		botones.add(destino);
		
		// Quita el evento del panel y de cada nodoLabel
		for (JLabel nodoLabel : vista.getLabelNodos()) {
			MouseListener[] eventos = nodoLabel.getListeners(MouseListener.class);
			nodoLabel.removeMouseListener(eventos[0]);
			nodoLabel.addMouseListener(new NodoRutaEvento(controller));
		}
		
		vista.getPanel().removeMouseListener(eventoRemover);
		
		vista.remove(vista.getBtnBuscarRuta());
		
		vista.repaint();
	}
}

