package view.menu;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import view.IConstants;

public class VistaMenuPrincipal extends JFrame implements IConstants{
	
	private JLabel background, logo, userLabel;
	private JButton btnHostear, btnConectar, btnSalir;
	
	public VistaMenuPrincipal() {
		this.getContentPane().setLayout(null);
		this.setSize(LARGO_MENU, ANCHO_MENU);
		this.setTitle("PROYECTO FINAL");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		
		userLabel = new JLabel("");
		userLabel.setBounds(10, 10, LARGO_MENU, 25);
		userLabel.setForeground(Color.yellow);
		this.add(userLabel);
		
		btnHostear = new JButton("Hostear Partida");
		btnHostear.setBounds(375, 225, 250, 25);
		this.add(btnHostear);
		
		btnConectar = new JButton("Conectar a Partida");
		btnConectar.setBounds(375, 270, 250, 25);
		this.add(btnConectar);
		
		btnSalir = new JButton("Salir");
		btnSalir.setBounds(375, 315, 250, 25);
		this.add(btnSalir);
		
		logo = new JLabel();
		logo.setIcon(new ImageIcon(new ImageIcon(".\\static\\media\\images\\logo.png")
				.getImage().getScaledInstance(LOGO_LARGO, LOGO_ANCHO, Image.SCALE_SMOOTH)));
		logo.setBounds(220, 10, 550, 300);
		this.add(logo);
		
		background = new JLabel();
		background.setIcon(new ImageIcon(new ImageIcon(".\\static\\media\\images\\background.gif")
				.getImage().getScaledInstance(LARGO_MENU, ANCHO_MENU, Image.SCALE_DEFAULT)));
		background.setBounds(0, 0, LARGO_MENU, ANCHO_MENU);
		this.add(background);
		
		this.setVisible(true);
	}
	

	public JLabel getUserLabel() {
		return userLabel;
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
