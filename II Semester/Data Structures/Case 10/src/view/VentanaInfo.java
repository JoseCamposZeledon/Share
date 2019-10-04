package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class VentanaInfo extends JFrame {

	private JPanel contentPane;

	private JLabel idLabel, tipoUbicacionLabel, nombreLugarLabel, 
					consumoActualLabel, fuenteLabel, buscarLabel;
	
	private JLabel idSensor, tipoUbicacionSensor, nombreLugarSensor,
					consumoActualSensor, fuenteSensor;
	
	private JSeparator separador;
	
	private JTextField buscarInput;

	/**
	 * Create the frame.
	 */
	public VentanaInfo() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 250);
	
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		setResizable(false);
		setTitle("Información");
		
		// TextField
		buscarInput = new JTextField();
		buscarInput.setBounds(100, 10, 310, 20);
		
		// Separador
		separador = new JSeparator();
		separador.setBounds(10, 35, 400, 10);
		
		
		// Labels
		buscarLabel = new JLabel("Buscar por <>:");
		buscarLabel.setBounds(10, 10, 85, 20);
		
		idLabel = new JLabel("ID:");
		idLabel.setBounds(10, 40, 110, 20);
		
		idSensor = new JLabel();
		idSensor.setBounds(90, 40, 130, 20);
		
		nombreLugarLabel = new JLabel("Nombre del lugar:");
		nombreLugarLabel.setBounds(10, 70, 110, 20);
		
		nombreLugarSensor = new JLabel();
		nombreLugarSensor.setBounds(90, 70, 130, 20);
		
		tipoUbicacionLabel = new JLabel("Tipo de ubicación:");
		tipoUbicacionLabel.setBounds(10, 100, 130, 20);
		
		tipoUbicacionSensor = new JLabel();
		tipoUbicacionSensor.setBounds(90, 100, 130, 20);
		
		consumoActualLabel = new JLabel("Consumo actual:");
		consumoActualLabel.setBounds(10, 130, 110, 20);
		
		consumoActualSensor = new JLabel();
		consumoActualSensor.setBounds(90, 130, 130, 20);
		
		fuenteLabel = new JLabel("Fuente de agua:");
		fuenteLabel.setBounds(10, 160, 110, 20);

		fuenteSensor = new JLabel();
		fuenteSensor.setBounds(90, 160, 110, 20);
		
		// Render
		contentPane.add(buscarLabel);
		
		contentPane.add(buscarInput);
		
		contentPane.add(separador);
		
		contentPane.add(idLabel);
		contentPane.add(idSensor);
		
		contentPane.add(nombreLugarLabel);
		contentPane.add(nombreLugarSensor);
		
		contentPane.add(tipoUbicacionLabel);
		contentPane.add(tipoUbicacionSensor);
		
		contentPane.add(consumoActualLabel);
		contentPane.add(consumoActualSensor);
	
		contentPane.add(fuenteLabel);
		contentPane.add(fuenteSensor);
	}

}
