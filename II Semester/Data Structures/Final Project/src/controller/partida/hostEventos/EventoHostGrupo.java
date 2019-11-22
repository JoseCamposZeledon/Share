package controller.partida.hostEventos;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import controller.partida.PartidaHostController;
import model.mapComponents.CrownTile;
import model.mapComponents.GroupTile;
import model.player.Player;

public class EventoHostGrupo extends MouseAdapter implements controller.partida.IConstants{
	
	private static ArrayList<GroupTile> tiles = new ArrayList<GroupTile>();
	private GroupTile myTile;
	private int idTile;
	
	private JLabel personajesGrupo;
	
	public EventoHostGrupo(GroupTile pTile, int pIdTile) {
		tiles.add(pTile);
		myTile = pTile;
		idTile = pIdTile;
		
		personajesGrupo = tiles.get(idTile);
	}
	
	public void mouseClicked(MouseEvent e) {
		JLabel personaje = new JLabel("  ");
		
		// Colores
		switch (PartidaHostController.getInstance().getIdPersonajeSelected()) {
			case(ID_ARCHER):
				personaje.setBorder(BorderFactory.createLineBorder(Color.GREEN));
				break;
			case(ID_KNIGHT):
				personaje.setBorder(BorderFactory.createLineBorder(Color.RED));
				break;
			case(ID_BRAWLER):
				personaje.setBorder(BorderFactory.createLineBorder(Color.GRAY));
				break;
		}
		
		PartidaHostController.getInstance().getHostPlayer()
		.agregar(idTile, PartidaHostController.getInstance().getIdPersonajeSelected());
		
		int size = PartidaHostController.getInstance().getHostPlayer()
				.getGrupos()[idTile].getPersonajes().size();
		
		if (size == 1) {
			personaje.setBounds(20, 4, 8, 8);
			personajesGrupo.add(personaje);
		} else if (size == 2) {
			personaje.setBounds(20, 20, 8, 8);
			personajesGrupo.add(personaje);
		} else if (size == 3) {
			personaje.setBounds(4, 4, 8, 8);
			personajesGrupo.add(personaje);
		} else if (size == 4) {
			personaje.setBounds(4, 20, 8, 8);
			personajesGrupo.add(personaje);
		} else if (size > 4) {
			System.out.println("Hay problemas");
		}
		
		PartidaHostController.getInstance().getHostPlayer().getGrupos()[idTile]
				.setPersonajesLabel(personajesGrupo);
		
		PartidaHostController.getInstance().getVista().getTableroPane().add(personajesGrupo, 3);
		
		PartidaHostController.getInstance().notifyView();
	}

}
