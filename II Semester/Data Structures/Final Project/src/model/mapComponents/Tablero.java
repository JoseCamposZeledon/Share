package model.mapComponents;

import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import controller.partida.PartidaClientController;
import controller.partida.PartidaHostController;
import controller.partida.clientEventos.EventoClientCorona;
import controller.partida.clientEventos.EventoClientGrupo;
import controller.partida.hostEventos.EventoHostCorona;
import controller.partida.hostEventos.EventoHostGrupo;

public class Tablero extends JLayeredPane implements view.IConstants, controller.partida.IConstants{
	
	ArrayList<CrownTile> hostCrownTiles = new ArrayList<CrownTile>();
	ArrayList<CrownTile> clientCrownTiles = new ArrayList<CrownTile>();
	ArrayList<GroupTile> hostGroupTiles = new ArrayList<GroupTile>();
	ArrayList<GroupTile> clientGroupTiles = new ArrayList<GroupTile>();
	
	public Tablero() {
		
		int groupHostCounter = 0;
		int groupClientCounter = 0;
		
		for (int i = 0; i < (MAP_LARGO / TILE_SIZE); i++) {
			for (int j = 0; j < (MAP_ANCHO/ TILE_SIZE); j++) {
				MapTile tile;
				
				if ((i == 0 || i == 31) && (j == 0 || j == 12 || j == 24)) {
					tile = new CrownTile();
					
					if (i == 0) {
						tile.addMouseListener(new EventoHostCorona((CrownTile) tile));
						hostCrownTiles.add((CrownTile) tile);
					}
					else if (i == 31) {
						tile.addMouseListener(new EventoClientCorona((CrownTile) tile));
						clientCrownTiles.add((CrownTile) tile);
					}
				}
				else if ((i == 1 || i ==30) && (j == 0 || j == 12 || j == 24)) {
					tile = new GroupTile();
					
					if (i == 1) {
						tile.addMouseListener(new EventoHostGrupo((GroupTile) tile, groupHostCounter));
						hostGroupTiles.add((GroupTile) tile);
						groupHostCounter++;
						
					} else if (i == 30) {
						tile.addMouseListener(new EventoClientGrupo((GroupTile) tile, groupClientCounter));
						clientGroupTiles.add((GroupTile) tile);
						groupClientCounter++;
					}
				}
				else {
					tile = new MapTile();
				}
				
				tile.setBounds(i * TILE_SIZE, j * TILE_SIZE, TILE_SIZE, TILE_SIZE);
				
			
				
				this.add(tile, 0);
			}
		}
	}
	
	public ArrayList<CrownTile> getHostCrownTiles() {
		return hostCrownTiles;
	}

	public void setHostCrownTiles(ArrayList<CrownTile> hostCrownTiles) {
		this.hostCrownTiles = hostCrownTiles;
	}

	public ArrayList<CrownTile> getClientCrownTiles() {
		return clientCrownTiles;
	}

	public void setClientCrownTiles(ArrayList<CrownTile> clientCrownTiles) {
		this.clientCrownTiles = clientCrownTiles;
	}

	public ArrayList<GroupTile> getHostGroupTiles() {
		return hostGroupTiles;
	}

	public void setHostGroupTiles(ArrayList<GroupTile> hostGroupTiles) {
		this.hostGroupTiles = hostGroupTiles;
	}

	public ArrayList<GroupTile> getClientGroupTiles() {
		return clientGroupTiles;
	}

	public void setClientGroupTiles(ArrayList<GroupTile> clientGroupTiles) {
		this.clientGroupTiles = clientGroupTiles;
	}
	
	public void removeEvents(int pId) {
		
		if (pId == 1) {
			for (CrownTile a : clientCrownTiles) {
				a.removeMouseListener(a.getMouseListeners()[0]);
			}
			
			for (GroupTile a : clientGroupTiles) {
				a.removeMouseListener(a.getMouseListeners()[0]);
			}
		} else if (pId == 2) {
			for (CrownTile a : hostCrownTiles) {
				a.removeMouseListener(a.getMouseListeners()[0]);
			}
			
			for (GroupTile a : hostGroupTiles) {
				a.removeMouseListener(a.getMouseListeners()[0]);
			}
		}
		
	}
}
