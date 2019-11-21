package model.mapComponents;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import controller.partida.hostEventos.EventoGrupo;

public class GroupTile extends MapTile {
	
	private static ImageIcon tileTexture = 
			new ImageIcon(new ImageIcon(".\\static\\media\\images\\player_tile.png")
			.getImage().getScaledInstance(TILE_SIZE, TILE_SIZE, Image.SCALE_SMOOTH));
	
	public GroupTile() {

		this.setIcon(tileTexture);
		
		this.addMouseListener(new EventoGrupo());
	}
			
}
