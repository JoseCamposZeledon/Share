package model.mapComponents;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

public class Tablero extends JLayeredPane implements view.IConstants{
	
	public Tablero() {
		
		for (int i = 0; i < (MAP_LARGO / TILE_SIZE); i++) {
			for (int j = 0; j < (MAP_ANCHO/ TILE_SIZE); j++) {
				MapTile tile;
				
				if ((i == 0 || i == 31) && (j == 0 || j == 12 || j == 24)) {
					tile = new CrownTile();
				}
				else if ((i == 1 || i ==30) && (j == 0 || j == 12 || j == 24)) {
					tile = new GroupTile();
				}
				else {
					tile = new MapTile();
				}
				
				tile.setBounds(i * TILE_SIZE, j * TILE_SIZE, TILE_SIZE, TILE_SIZE);
				
			
				
				this.add(tile, 0);
			}
		}
	}
	
}
