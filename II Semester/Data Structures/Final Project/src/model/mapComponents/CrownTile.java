package model.mapComponents;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class CrownTile extends MapTile{
	private static ImageIcon tileTexture = new ImageIcon(
			new ImageIcon(".\\static\\media\\images\\crown_tile.png")
			.getImage().getScaledInstance(TILE_SIZE, TILE_SIZE, Image.SCALE_SMOOTH));
	
	JLabel tile;
	
	public CrownTile() {
		tile = new JLabel("");
		this.setIcon(tileTexture);
	}
	
}
