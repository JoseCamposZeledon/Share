package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import model.sensor.Sensor;
import view.VentanaConectar;

public class VentanaConectarController {
	
	VentanaConectar view;
	JButton btnConectarMenu;
	
	public VentanaConectarController(VentanaConectar pView, 
			JButton pBtnConectarMenu) {
		
		view = pView;
		btnConectarMenu = pBtnConectarMenu;
		
		this.view.addBtnConectarListener(new btnConectarListener());
		
		// Reactiva el boton al cerrar la ventana
		view.addWindowListener(new WindowAdapter() {
			public void windowClosed(WindowEvent e) {
				btnConectarMenu.setEnabled(true);
			}
		});
		
	}
	
	class btnConectarListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String id = view.getIdText();
			String nombre = view.getNombreLugarText();
			int consumo = view.getConsumoInput();
			int tipoUbicacion = view.getComboUbicacion();
			
			if (id.isEmpty() || nombre.isEmpty()) {
				JOptionPane.showMessageDialog(null, "INGRESE LA INFORMACIÓN NECESARIA", 
						"ERROR", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			if (consumo == Integer.MIN_VALUE || consumo <= 0) {
				JOptionPane.showMessageDialog(null, "INGRESE UN NÚMERO VÁLIDO", 
						"ERROR", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			Sensor sensorNuevo = new Sensor(id, tipoUbicacion, nombre, consumo);
			
			System.out.println(sensorNuevo.getLugar().getClass());
			
			view.dispose();
		}
		
	}
}
