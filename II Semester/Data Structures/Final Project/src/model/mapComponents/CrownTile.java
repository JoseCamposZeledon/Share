package model.mapComponents;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import controller.partida.PartidaHostController;
import controller.partida.hostEventos.EventoHostCorona;

public class CrownTile extends MapTile{
	
	private static ImageIcon tileTexture = new ImageIcon(
			new ImageIcon(".\\static\\media\\images\\crown_tile.png")
			.getImage().getScaledInstance(TILE_SIZE, TILE_SIZE, Image.SCALE_SMOOTH));
	
	private boolean selected;
	
	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public CrownTile() {
		selected = false;
		this.setIcon(tileTexture);
	}
	
}
