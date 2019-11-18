package view.partida;

import java.awt.Color;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import view.IConstants;

public class VistaPartida extends JFrame implements IConstants{
	
	JPanel tableroPane;
	
	public VistaPartida() {
		this.getContentPane().setLayout(null);
		this.setSize(GAME_WINDOW_LARGO, GAME_WINDOW_ANCHO);
		
		// TABLERO
		tableroPane = new JPanel();
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
		
		// TILES DE TABLERO
		ImageIcon tileTexture = new ImageIcon(new ImageIcon(".\\static\\media\\images\\game_tile1.png")
				.getImage().getScaledInstance(TILE_SIZE, TILE_SIZE, Image.SCALE_SMOOTH));
		
		for (int i = 0; i < (MAP_LARGO / TILE_SIZE); i++) {
			for (int j = 0; j < (MAP_ANCHO/ TILE_SIZE); j++) {
				
				JLabel tile = new JLabel();
				tile.setBounds(i * TILE_SIZE, j * TILE_SIZE, TILE_SIZE, TILE_SIZE);
				tile.setIcon(tileTexture);
				tableroPane.add(tile);
			}
		}
		
		this.setVisible(true);
	}
	
	// INFO JUGADORES
	
	
	public static void main(String[] args) {
		VistaPartida test = new VistaPartida();
	}
}
