package view.menu;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import view.IConstants;

public class ContentSeleccionMapa extends JPanel implements IConstants {
	
	JLabel previewMapa1, previewMapa2, previewMapa3;
	JButton btnHostear, btnVolver, btnCargarMapa;
	JTextField mapaPathInput;
	
	public ContentSeleccionMapa() {
		this.setLayout(null);
		this.setSize(LARGO_MENU, ANCHO_MENU);
		
		// Previews mapas
		previewMapa1 = new JLabel("");
		previewMapa1.setIcon(new ImageIcon(new ImageIcon(".\\static\\media\\images\\mapa1_preview.png")
				.getImage().getScaledInstance(150, 100, Image.SCALE_SMOOTH)));
		previewMapa1.setBounds(200, 275, 150, 100);
		this.add(previewMapa1);
		
		previewMapa2 = new JLabel("");
		previewMapa2.setIcon(new ImageIcon(new ImageIcon(".\\static\\media\\images\\mapa2_preview.png")
				.getImage().getScaledInstance(150, 100, Image.SCALE_SMOOTH)));
		previewMapa2.setBounds(425, 275, 150, 100);
		this.add(previewMapa2);
		
		previewMapa3 = new JLabel("");
		previewMapa3.setIcon(new ImageIcon(new ImageIcon(".\\static\\media\\images\\mapa3_preview.png")
				.getImage().getScaledInstance(150, 100, Image.SCALE_SMOOTH)));
		previewMapa3.setBounds(650, 275, 150, 100);
		this.add(previewMapa3);
		
		// Input
		mapaPathInput = new JTextField("");
		mapaPathInput.setBounds(200, 460, 440, 25);
		this.add(mapaPathInput);
		
		// Botones
		btnCargarMapa = new JButton("Cargar Mapa");
		btnCargarMapa.setBounds(650, 460, 150, 25);
		this.add(btnCargarMapa);
		
		btnHostear = new JButton("Hostear Partida");
		btnHostear.setBounds(200, 500, 440, 25);
		this.add(btnHostear);
		
		btnVolver = new JButton("Volver");
		btnVolver.setBounds(650, 500, 150, 25);
		this.add(btnVolver);
	}
	
	
	
}
