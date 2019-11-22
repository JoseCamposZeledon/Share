package view.partida;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import model.json.MapParser;
import model.mapComponents.ObstaculoGrafico;
import model.mapComponents.Tablero;
import view.IConstants;

public class VistaPartidaUser extends JFrame implements IConstants {
	
	private Tablero tableroPane;
	
	private JLabel columnaHost, columnaClient;
	
	private JLabel infoHost, infoClient;
	
	private	JLabel readyHostLabel, readyClientLabel;
	private JLabel archerLabel, knightLabel, brawlerLabel, flagLabel;
	
	public VistaPartidaUser() {
			this.getContentPane().setLayout(null);
			this.setSize(GAME_WINDOW_LARGO, GAME_WINDOW_ANCHO);
			this.setDefaultCloseOperation(EXIT_ON_CLOSE);
			this.setResizable(false);
			
			// TABLERO
			tableroPane = new Tablero();
			tableroPane.setLayout(null);
			tableroPane.setBounds(238, 10, MAP_LARGO, MAP_ANCHO);
			this.add(tableroPane);
			
			// BORDE DE TABLERO
			ImageIcon borderTexture = new ImageIcon(new ImageIcon(".\\static\\media\\images\\board_border.png")
					.getImage().getScaledInstance(MAP_LARGO + 20, MAP_ANCHO + 21, Image.SCALE_SMOOTH));
			
			JLabel boardBorder = new JLabel();
			boardBorder.setBounds(228, 0, MAP_LARGO + 20, MAP_ANCHO + 21);
			boardBorder.setIcon(borderTexture);
			
			this.add(boardBorder);
			
			// INFO JUGADORES
			readyHostLabel = new JLabel("");
			readyHostLabel.setBounds(82, 665, 75, 75);
			readyHostLabel.setIcon(new ImageIcon(new ImageIcon(".\\static\\media\\images\\notready_button.png")
					.getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH)));
			this.add(readyHostLabel);
			
			readyClientLabel = new JLabel("");
			readyClientLabel.setBounds(1345, 665, 75, 75);
			readyClientLabel.setIcon(new ImageIcon(new ImageIcon(".\\static\\media\\images\\notready_button.png")
					.getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH)));
			this.add(readyClientLabel);
			
			readyClientLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			
			infoHost = new JLabel("");
			infoHost.setBounds(15, 10, 217, 25);
			infoHost.setForeground(Color.YELLOW);
			this.add(infoHost);
			
			infoClient = new JLabel("");
			infoClient.setBounds(GAME_WINDOW_LARGO - 217, 10, 217, 25);
			infoClient.setForeground(Color.YELLOW);
			this.add(infoClient);
			
			flagLabel = new JLabel("");
			flagLabel.setBounds(1330, 50, 100, 100);
			flagLabel.setIcon(new ImageIcon(new ImageIcon(".\\static\\media\\images\\flag_display.png")
					.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
			this.add(flagLabel);
			
			flagLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			
			JLabel borderLabel0 = new JLabel("");
			borderLabel0.setBounds(1321, 40, 120, 120);
			borderLabel0.setIcon(new ImageIcon(new ImageIcon(".\\static\\media\\images\\selected_border.png")
					.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH)));
			this.add(borderLabel0);
			
			archerLabel = new JLabel("");
			archerLabel.setBounds(1330, 230, 100, 100);
			archerLabel.setIcon(new ImageIcon(new ImageIcon(".\\static\\media\\images\\archer_display.png")
					.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
			this.add(archerLabel);
			
			archerLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			
			JLabel borderLabel1 = new JLabel("");
			borderLabel1.setBounds(1321, 220, 120, 120);
			borderLabel1.setIcon(new ImageIcon(new ImageIcon(".\\static\\media\\images\\selected_border.png")
					.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH)));
			this.add(borderLabel1);
			
			knightLabel = new JLabel("");
			knightLabel.setBounds(1330, 370, 100, 100);
			knightLabel.setIcon(new ImageIcon(new ImageIcon(".\\static\\media\\images\\knight_display.png")
					.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
			this.add(knightLabel);
			
			knightLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			
			JLabel borderLabel2 = new JLabel("");
			borderLabel2.setBounds(1321, 360, 120, 120);
			borderLabel2.setIcon(new ImageIcon(new ImageIcon(".\\static\\media\\images\\selected_border.png")
					.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH)));
			this.add(borderLabel2);
			
			brawlerLabel = new JLabel("");
			brawlerLabel.setBounds(1330, 510, 100, 100);
			brawlerLabel.setIcon(new ImageIcon(new ImageIcon(".\\static\\media\\images\\brawler_display.png")
					.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
			this.add(brawlerLabel);
			
			brawlerLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			
			JLabel borderLabel3 = new JLabel("");
			borderLabel3.setBounds(1321, 500, 120, 120);
			borderLabel3.setIcon(new ImageIcon(new ImageIcon(".\\static\\media\\images\\selected_border.png")
					.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH)));
			this.add(borderLabel3);
			
			columnaHost = new JLabel("");
			columnaHost.setBounds(0, 0, 237, GAME_WINDOW_ANCHO);
			columnaHost.setIcon(new ImageIcon(new ImageIcon(".\\static\\media\\images\\column.png")
					.getImage().getScaledInstance(237, GAME_WINDOW_ANCHO, Image.SCALE_SMOOTH)));
			this.add(columnaHost);
			
			columnaClient = new JLabel("");
			columnaClient.setBounds(GAME_WINDOW_LARGO - 237, 0, 237, GAME_WINDOW_ANCHO);
			columnaClient.setIcon(new ImageIcon(new ImageIcon(".\\static\\media\\images\\column.png")
					.getImage().getScaledInstance(237, GAME_WINDOW_ANCHO, Image.SCALE_SMOOTH)));
			this.add(columnaClient);
			
			
			
		this.setVisible(true);
	}



	public Tablero getTableroPane() {
		return tableroPane;
	}

	public JLabel getColumnaHost() {
		return columnaHost;
	}

	public void setColumnaHost(JLabel columnaHost) {
		this.columnaHost = columnaHost;
	}

	public JLabel getColumnaClient() {
		return columnaClient;
	}

	public void setColumnaClient(JLabel columnaClient) {
		this.columnaClient = columnaClient;
	}

	public JLabel getInfoHost() {
		return infoHost;
	}

	public void setInfoHost(JLabel infoHost) {
		this.infoHost = infoHost;
	}

	public JLabel getInfoClient() {
		return infoClient;
	}

	public void setInfoClient(JLabel infoClient) {
		this.infoClient = infoClient;
	}

	public JLabel getReadyHostLabel() {
		return readyHostLabel;
	}

	public void setReadyHostLabel(JLabel readyHostLabel) {
		this.readyHostLabel = readyHostLabel;
	}

	public JLabel getReadyClientLabel() {
		return readyClientLabel;
	}

	public void setReadyClientLabel(JLabel readyClientLabel) {
		this.readyClientLabel = readyClientLabel;
	}

	public JLabel getArcherLabel() {
		return archerLabel;
	}

	public void setArcherLabel(JLabel archerLabel) {
		this.archerLabel = archerLabel;
	}

	public JLabel getKnightLabel() {
		return knightLabel;
	}

	public void setKnightLabel(JLabel knightLabel) {
		this.knightLabel = knightLabel;
	}

	public JLabel getBrawlerLabel() {
		return brawlerLabel;
	}

	public void setBrawlerLabel(JLabel brawlerLabel) {
		this.brawlerLabel = brawlerLabel;
	}

	public JLabel getFlagLabel() {
		return flagLabel;
	}

	public void setFlagLabel(JLabel flagLabel) {
		this.flagLabel = flagLabel;
	}
	
	public void update() {
		this.revalidate();
		this.repaint();
	}
}
