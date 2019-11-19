package view.menu;

import java.awt.Image;
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
	
	JLabel background, logo;
	
	public VistaMenuPrincipal() {
		this.getContentPane().setLayout(null);
		this.setSize(LARGO_MENU, ANCHO_MENU);
		this.setTitle("PROYECTO FINAL");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		
		this.setContentPane(new ContentSeleccionMapa());
		
		logo = new JLabel();
		logo.setIcon(new ImageIcon(new ImageIcon(".\\static\\media\\images\\logo.png")
				.getImage().getScaledInstance(550, 200, Image.SCALE_SMOOTH)));
		logo.setBounds(200, 10, 550, 300);
		this.add(logo);
		
		background = new JLabel();
		background.setIcon(new ImageIcon(new ImageIcon(".\\static\\media\\images\\background.gif")
				.getImage().getScaledInstance(LARGO_MENU, ANCHO_MENU, Image.SCALE_DEFAULT)));
		background.setBounds(0, 0, LARGO_MENU, ANCHO_MENU);
		this.add(background);
		
		this.setVisible(true);
	}
	
	public static void main(String[] args) {
		VistaMenuPrincipal test = new VistaMenuPrincipal();
	}
}
