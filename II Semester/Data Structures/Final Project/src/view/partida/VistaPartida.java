package view.partida;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;

import view.IConstants;

public class VistaPartida extends JFrame implements IConstants{
	
	public VistaPartida() {
		this.getContentPane().setLayout(null);
		this.setSize(LARGO + TILE_SIZE, ANCHO + TILE_SIZE);
		
		for (int columna = 0; columna <= LARGO / TILE_SIZE; columna++) {
			for (int fila = 0; fila <= ANCHO / TILE_SIZE; fila++) {
				
				JLabel tile = new JLabel(columna + "");
				tile.setBounds(columna * TILE_SIZE, fila * TILE_SIZE, TILE_SIZE, TILE_SIZE);
				tile.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				this.add(tile);
			}
		}
		
		this.setVisible(true);
	}
	
	
	public static void main(String[] args) {
		VistaPartida test = new VistaPartida();
	}
}
