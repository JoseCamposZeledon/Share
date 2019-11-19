package view.menu;

import javax.swing.JButton;
import javax.swing.JPanel;

import view.IConstants;

public class ContentInicio extends JPanel implements IConstants{

	JButton btnHostear, btnConectar, btnSalir;
	
	public ContentInicio() {
		this.setLayout(null);
		this.setSize(LARGO_MENU, ANCHO_MENU);
		
		btnHostear = new JButton("Hostear Partida");
		btnHostear.setBounds(375, 225, 250, 25);
		this.add(btnHostear);
		
		btnConectar = new JButton("Conectar a Partida");
		btnConectar.setBounds(375, 270, 250, 25);
		this.add(btnConectar);
		
		btnSalir = new JButton("Salir");
		btnSalir.setBounds(375, 315, 250, 25);
		this.add(btnSalir);
		
	}

	public JButton getBtnHostear() {
		return btnHostear;
	}

	public JButton getBtnConectar() {
		return btnConectar;
	}

	public JButton getBtnSalir() {
		return btnSalir;
	}

}
