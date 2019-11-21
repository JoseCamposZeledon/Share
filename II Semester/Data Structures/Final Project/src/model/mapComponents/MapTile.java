package model.mapComponents;

import java.awt.Image;
import java.io.Serializable;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class MapTile extends JLabel implements view.IConstants, Serializable{

	private static ImageIcon tileTexture = new ImageIcon(
			new ImageIcon(".\\static\\media\\images\\game_tile1.png")
			.getImage().getScaledInstance(TILE_SIZE, TILE_SIZE, Image.SCALE_SMOOTH));
	
	
	public MapTile() {
		this.setIcon(tileTexture);
	}
	
}
