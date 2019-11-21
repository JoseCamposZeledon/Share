package controller.partida.hostEventos;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JLabel;

import controller.partida.PartidaHostController;
import model.mapComponents.CrownTile;
import model.mapComponents.GroupTile;
import model.player.Player;

public class EventoHostGrupo extends MouseAdapter implements controller.partida.IConstants{
	
	private static ArrayList<GroupTile> tiles = new ArrayList<GroupTile>();
	private GroupTile myTile;
	private int idTile;
	
	private JLabel personajesGrupo = new JLabel("");
	
	public EventoHostGrupo(GroupTile pTile, int pIdTile) {
		tiles.add(pTile);
		myTile = pTile;
		idTile = pIdTile;
		
		personajesGrupo.setBounds(0, 0, 32, 32);
		myTile.add(personajesGrupo);
	}
	
	public void mouseClicked(MouseEvent e) {
		System.out.println("ENTRA");
		
		JLabel personaje = new JLabel("");
		
		// Colores
		switch (PartidaHostController.getInstance().getIdPersonajeSelected()) {
			case(ID_ARCHER):
				personaje.setBackground(Color.GREEN);
				break;
			case(ID_KNIGHT):
				personaje.setBackground(Color.BLUE);
				break;
			case(ID_BRAWLER):
				personaje.setBackground(Color.ORANGE);
				break;
		}
		
		int size = PartidaHostController.getInstance().getHostPlayer()
				.getGrupos()[idTile].getPersonajes().size();
		
		System.out.println(size);
		
		if (size == 0) {
			personaje.setBounds(20, 4, 8, 8);
			personajesGrupo.add(personaje);
		} else if (size == 1) {
			
		} else if (size == 2) {
			
		} else if (size == 3) {
			
		}
		
		PartidaHostController.getInstance().getHostPlayer()
		.agregar(idTile, PartidaHostController.getInstance().getIdPersonajeSelected());
		
		PartidaHostController.getInstance().notifyView();
	}

}
