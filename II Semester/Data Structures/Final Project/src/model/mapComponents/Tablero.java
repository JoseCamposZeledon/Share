package model.mapComponents;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import controller.partida.PartidaClientController;
import controller.partida.PartidaHostController;
import controller.partida.clientEventos.EventoClientCorona;
import controller.partida.clientEventos.EventoClientGrupo;
import controller.partida.hostEventos.EventoHostCorona;
import controller.partida.hostEventos.EventoHostGrupo;

public class Tablero extends JLayeredPane implements view.IConstants, controller.partida.IConstants{
	
	public Tablero() {
		
		int groupHostCounter = 0;
		int groupClientCounter = 0;
		
		for (int i = 0; i < (MAP_LARGO / TILE_SIZE); i++) {
			for (int j = 0; j < (MAP_ANCHO/ TILE_SIZE); j++) {
				MapTile tile;
				
				if ((i == 0 || i == 31) && (j == 0 || j == 12 || j == 24)) {
					tile = new CrownTile();
					
					if (i == 0) tile.addMouseListener(new EventoHostCorona((CrownTile) tile));
					else if (i == 31) tile.addMouseListener(new EventoClientCorona((CrownTile) tile));
				}
				else if ((i == 1 || i ==30) && (j == 0 || j == 12 || j == 24)) {
					tile = new GroupTile();
					
					if (i == 1) {
						tile.addMouseListener(new EventoHostGrupo((GroupTile) tile, groupHostCounter));
						groupHostCounter++;
					} else if (i == 30) {
						tile.addMouseListener(new EventoClientGrupo((GroupTile) tile, groupClientCounter));
						groupClientCounter++;
					}
				}
				else {
					tile = new MapTile();
				}
				
				tile.setBounds(i * TILE_SIZE, j * TILE_SIZE, TILE_SIZE, TILE_SIZE);
				
			
				
				this.add(tile, 0);
			}
		}
	}
	
	
	public void test() {};
}
