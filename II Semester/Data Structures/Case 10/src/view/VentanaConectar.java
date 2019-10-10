package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class VentanaConectar extends JFrame {

	private JPanel contentPane;
	
	private JLabel idLabel, nombreLugarLabel, tipoUbicacionLabel,
					consumoLabel, fuenteLabel, fuenteSensor;
	
	private JTextField idInput, nombreLugarInput, consumoInput;
	
	private JComboBox tipoUbicacionInput;
	
	private JButton btnConectar;
	
	/**
	 * Create the frame.
	 */
	public VentanaConectar() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 230);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		setTitle("Conectar Sensor");
		
		// Components
		idLabel = new JLabel("ID:");
		idLabel.setBounds(10, 10, 110, 20);
		
		idInput = new JTextField();
		idInput.setBounds(120, 10, 300, 20);
		
		nombreLugarLabel = new JLabel("Nombre del Lugar:");
		nombreLugarLabel.setBounds(10, 40, 110, 20);
		
		nombreLugarInput = new JTextField();
		nombreLugarInput.setBounds(120, 40, 300, 20);
		
		tipoUbicacionLabel = new JLabel("Tipo de lugar:");
		tipoUbicacionLabel.setBounds(10, 70, 110, 20);
		
		tipoUbicacionInput = new JComboBox<String>();
		tipoUbicacionInput.setBounds(120, 70, 300, 20);
		
		consumoLabel = new JLabel("Consumo:");
		consumoLabel.setBounds(10, 100, 110, 20);
		
		consumoInput = new JTextField();
		consumoInput.setBounds(120, 100, 300, 20);
		
		fuenteLabel = new JLabel("Fuente:");
		fuenteLabel.setBounds(10, 130, 110, 20);
		
		fuenteSensor = new JLabel();
		fuenteSensor.setBounds(120, 130, 300, 20);
		
		btnConectar = new JButton("Conectar Sensor");
		btnConectar.setBounds(10, 160, 410, 20);
		
		// Render
		contentPane.add(idLabel);
		contentPane.add(idInput);
		
		contentPane.add(nombreLugarLabel);
		contentPane.add(nombreLugarInput);
		
		contentPane.add(tipoUbicacionLabel);
		contentPane.add(tipoUbicacionInput);
		
		contentPane.add(consumoLabel);
		contentPane.add(consumoInput);
		
		contentPane.add(fuenteLabel);
		contentPane.add(fuenteSensor);
		
		contentPane.add(btnConectar);
	}
	
	public void setFuenteText(String pTexto) {
		this.fuenteSensor.setText(pTexto);
	}
	
	public void addBtnConectarListener(ActionListener btnConectarListener) {
		btnConectar.addActionListener(btnConectarListener);
	}
	
	public String getIdText() {
		return idInput.getText().trim();
	}
	
	public String getNombreLugarText() {
		return nombreLugarInput.getText().trim();
	}
	
	public JComboBox getTipoUbicacionInput() {
		return tipoUbicacionInput;
	}

	public void setTipoUbicacionInput(JComboBox tipoUbicacionInput) {
		this.tipoUbicacionInput = tipoUbicacionInput;
	}

	public int getComboUbicacion() {
		return tipoUbicacionInput.getSelectedIndex();
	}
	
	public int getConsumoInput() {
		try {
			return Integer.parseInt(consumoInput.getText().trim());
		} catch (NumberFormatException nfe) {
			return Integer.MIN_VALUE;
		}
	}
}
