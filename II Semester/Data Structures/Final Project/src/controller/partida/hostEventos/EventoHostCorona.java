package controller.partida.hostEventos;

import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import controller.partida.PartidaHostController;
import model.mapComponents.CrownTile;

public class EventoHostCorona extends MouseAdapter{
	
	private static ArrayList<CrownTile> tiles = new ArrayList<CrownTile>();
	private CrownTile myTile;
	
	private static ImageIcon tileTexture = new ImageIcon(
			new ImageIcon(".\\static\\media\\images\\flag_display.png")
			.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH));
	
	private static JLabel crown = new JLabel("");
	
	public EventoHostCorona(CrownTile pTile) {
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
			
			else if (actual.isSelected()) {
				PartidaHostController.getInstance().getMapaNodos().get(new Point(
						myTile.getX(),
						myTile.getY()
						)).getValor().setActivo(0);
			}
			
			// Limpia el tile
			actual.removeAll();
			actual.setSelected(false);
		}
		
		PartidaHostController.getInstance().getHostPlayer().setCrownPlaced(true);
		PartidaHostController.getInstance().getMapaNodos().get(new Point(
				myTile.getX(),
				myTile.getY()
				)).getValor().setActivo(2);
		PartidaHostController.getInstance().notifyView();
		
	}
}
