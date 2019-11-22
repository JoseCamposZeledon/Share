package view.menu;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import view.IConstants;

public class VistaSeleccionMapa extends JFrame implements IConstants {
	
	JLabel previewMapa1, previewMapa2, previewMapa3, background, logo;
	JLabel borderMapa1, borderMapa2, borderMapa3, userLabel;
	JButton btnHostear, btnVolver, btnCargarMapa;
	JTextField mapaPathInput;
	
	public VistaSeleccionMapa() {
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
		
		previewMapa1 = new JLabel("");
		previewMapa1.setIcon(new ImageIcon(new ImageIcon(".\\static\\media\\images\\mapa1_preview.png")
				.getImage().getScaledInstance(150, 100, Image.SCALE_SMOOTH)));
		previewMapa1.setBounds(200, 275, 150, 100);
		this.add(previewMapa1);
		
		previewMapa1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		borderMapa1 = new JLabel("");
		borderMapa1.setIcon(new ImageIcon(new ImageIcon(".\\static\\media\\images\\board_border.png")
				.getImage().getScaledInstance(157, 107, Image.SCALE_SMOOTH)));
		borderMapa1.setBounds(197, 272, 157, 107);
		this.add(borderMapa1);
		
		previewMapa2 = new JLabel("");
		previewMapa2.setIcon(new ImageIcon(new ImageIcon(".\\static\\media\\images\\mapa2_preview.png")
				.getImage().getScaledInstance(150, 100, Image.SCALE_SMOOTH)));
		previewMapa2.setBounds(425, 275, 150, 100);
		this.add(previewMapa2);
		
		previewMapa2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		borderMapa2 = new JLabel("");
		borderMapa2.setIcon(new ImageIcon(new ImageIcon(".\\static\\media\\images\\board_border.png")
				.getImage().getScaledInstance(157, 107, Image.SCALE_SMOOTH)));
		borderMapa2.setBounds(422, 272, 157, 107);
		this.add(borderMapa2);
		
		previewMapa3 = new JLabel("");
		previewMapa3.setIcon(new ImageIcon(new ImageIcon(".\\static\\media\\images\\mapa3_preview.png")
				.getImage().getScaledInstance(150, 100, Image.SCALE_SMOOTH)));
		previewMapa3.setBounds(650, 275, 150, 100);
		this.add(previewMapa3);
		
		previewMapa3.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		borderMapa3 = new JLabel("");
		borderMapa3.setIcon(new ImageIcon(new ImageIcon(".\\static\\media\\images\\board_border.png")
				.getImage().getScaledInstance(157, 107, Image.SCALE_SMOOTH)));
		borderMapa3.setBounds(647, 272, 157, 107);
		this.add(borderMapa3);
		
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
	
	public JLabel getPreviewMapa1() {
		return previewMapa1;
	}

	public void setPreviewMapa1(JLabel previewMapa1) {
		this.previewMapa1 = previewMapa1;
	}

	public JLabel getPreviewMapa2() {
		return previewMapa2;
	}

	public void setPreviewMapa2(JLabel previewMapa2) {
		this.previewMapa2 = previewMapa2;
	}

	public JLabel getPreviewMapa3() {
		return previewMapa3;
	}

	public void setPreviewMapa3(JLabel previewMapa3) {
		this.previewMapa3 = previewMapa3;
	}

	public void setBackground(JLabel background) {
		this.background = background;
	}

	public JLabel getLogo() {
		return logo;
	}

	public void setLogo(JLabel logo) {
		this.logo = logo;
	}

	public JLabel getBorderMapa1() {
		return borderMapa1;
	}

	public void setBorderMapa1(JLabel borderMapa1) {
		this.borderMapa1 = borderMapa1;
	}

	public JLabel getBorderMapa2() {
		return borderMapa2;
	}

	public void setBorderMapa2(JLabel borderMapa2) {
		this.borderMapa2 = borderMapa2;
	}

	public JLabel getBorderMapa3() {
		return borderMapa3;
	}

	public void setBorderMapa3(JLabel borderMapa3) {
		this.borderMapa3 = borderMapa3;
	}

	public JButton getBtnHostear() {
		return btnHostear;
	}

	public void setBtnHostear(JButton btnHostear) {
		this.btnHostear = btnHostear;
	}

	public JButton getBtnVolver() {
		return btnVolver;
	}

	public void setBtnVolver(JButton btnVolver) {
		this.btnVolver = btnVolver;
	}

	public JButton getBtnCargarMapa() {
		return btnCargarMapa;
	}

	public void setBtnCargarMapa(JButton btnCargarMapa) {
		this.btnCargarMapa = btnCargarMapa;
	}

	public JTextField getMapaPathInput() {
		return mapaPathInput;
	}

	public void setMapaPathInput(JTextField mapaPathInput) {
		this.mapaPathInput = mapaPathInput;
	}
		
	
}
