package model.mapComponents;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class MapTile extends JLabel implements view.IConstants {

	private static ImageIcon tileTexture = new ImageIcon(
			new ImageIcon(".\\static\\media\\images\\game_tile1.png")
			.getImage().getScaledInstance(TILE_SIZE, TILE_SIZE, Image.SCALE_SMOOTH));
	
	JLabel tile;
	
	public MapTile() {
		tile = new JLabel("");
		this.setIcon(tileTexture);
	}
	
}
