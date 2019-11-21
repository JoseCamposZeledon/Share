package model.mapComponents;

import java.awt.Color;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import controller.partida.hostEventos.EventoHostGrupo;

public class GroupTile extends MapTile {
	
	private static ImageIcon tileTexture = 
			new ImageIcon(new ImageIcon(".\\static\\media\\images\\player_tile.png")
			.getImage().getScaledInstance(TILE_SIZE, TILE_SIZE, Image.SCALE_SMOOTH));
	
	private JLabel group;
	
	public GroupTile() {
		this.setIcon(tileTexture);
		
		group = new JLabel("");
		group.setBounds(0, 0, 32, 32);
		group.setBorder(BorderFactory.createLineBorder(Color.YELLOW));
		this.add(group);
	}
			
}
