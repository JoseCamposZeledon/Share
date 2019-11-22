package controller.partida.clientEventos;

import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import controller.partida.PartidaClientController;
import controller.partida.PartidaHostController;
import model.mapComponents.CrownTile;

public class EventoClientCorona extends MouseAdapter{
	
	private static ArrayList<CrownTile> tiles = new ArrayList<CrownTile>();
	private CrownTile myTile;
	
	private static ImageIcon tileTexture = new ImageIcon(
			new ImageIcon(".\\static\\media\\images\\flag_display.png")
			.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH));
	
	private static JLabel crown = new JLabel("");
	
	public EventoClientCorona(CrownTile pTile) {
		tiles.add(pTile);
		myTile = pTile;
		
		crown.setBounds(4, 4, 24, 24);
		crown.setIcon(tileTexture);
	}
	
	public void mouseClicked(MouseEvent e) {
		
		for (CrownTile actual : tiles) {
			if (actual.equals(myTile)) {
				myTile.setSelected(true);
				myTile.add(crown);
				continue;
			}
			
			// Limpia el tile
			actual.removeAll();
			actual.setSelected(false);
		}
		
		PartidaClientController.getInstance().getClientPlayer().setCrownPlaced(true);
		PartidaClientController.getInstance().notifyView();
		
	}
}