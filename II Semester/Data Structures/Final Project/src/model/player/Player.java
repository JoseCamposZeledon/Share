package model.player;

import java.util.ArrayList;
import java.util.LinkedList;

import controller.partida.JuegoController;
import controller.partida.PartidaHostController;
import model.grafo.Arco;
import model.grafo.GrafoTile;
import model.grafo.Nodo;
import model.personajes.Archer;
import model.personajes.Brawler;
import model.personajes.Knight;
import model.personajes.Personaje;

public class Player implements controller.partida.IConstants {
	
	private boolean crownPlaced;
	
	private int id;
	private int cantidadArcher = 4;
	private int cantidadBrawler = 6;
	private int cantidadKnight = 2;
	
	private Group[] grupos = new Group[3];
	
	public Player(int id) {
		this.id = id;
		grupos[0] = new Group(id);
		grupos[1] = new Group(id);
		grupos[2] = new Group(id);
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Group[] getGrupos() {
		return grupos;
	}

	public void setGrupos(Group[] grupos) {
		this.grupos = grupos;
	}

	public boolean isCrownPlaced() {
		return crownPlaced;
	}

	public void setCrownPlaced(boolean crownPlaced) {
		this.crownPlaced = crownPlaced;
	}

	public boolean calcularRuta(Nodo<GrafoTile> nodoDestino, int pGroupIndex) {
		return grupos[pGroupIndex].calcularRuta(nodoDestino);
	}
	
	public boolean enSeguimiento() {
		return !grupos[0].getRuta().isEmpty() || 
		       !grupos[1].getRuta().isEmpty() ||
		       !grupos[2].getRuta().isEmpty();
	}
	
	public boolean mover(int pGroupIndex) {
		Group grupo = grupos[pGroupIndex];
		if (!grupo.getRuta().isEmpty() && grupo.isVivo()) { 
			grupo.getNodoActual().getValor().setActivo(0);
			grupo.setNodoActual(grupo.getRuta().get(0));
			JuegoController.getInstance().getMapaGrupos().put(grupo.getNodoActual(), grupo);
			grupo.getRuta().get(0).getValor().setActivo(1);
			grupo.getRuta().remove(0);
			return true;
		} else {
			return false;
		}
	}
	
	public Player playerOpuesto() {
		if (this.equals(JuegoController.getInstance().getPlayerCasa())) {
			return JuegoController.getInstance().getPlayerVisita();
		} else {
			return JuegoController.getInstance().getPlayerCasa();
		}
	}
	
	public boolean revisar(int pGroupIndex) {
		// grupo para atacar
		ArrayList<Group> grupo = new ArrayList<Group>();
		for (Arco<GrafoTile> arco : grupos[pGroupIndex].getNodoActual().getConexiones()) {
			if (arco.getConexion()[1].getValor().getActivo() == 1) {
				boolean factible = true;
				for (int i = 0; i<3; i++) {
					Nodo<GrafoTile> nodoDestino =  arco.getConexion()[1];
					System.out.println("-----------------------------------------------------------");
					System.out.println("Grupo Actual " + i + " se encuentra en x: " +  grupos[pGroupIndex].getNodoActual().getValor().getX1() + " y: " + grupos[pGroupIndex].getNodoActual().getValor().getY1());
					System.out.println("-----------------------------------------------------------");
					if (JuegoController.getInstance().getMapaGrupos().get(nodoDestino).getId() == id) {
						factible = false;
						System.out.println("-----------------------------------------------------------");
						System.out.println("Grupo Destino Amigo " + i + " se encuentra en x: " + nodoDestino.getValor().getX1() + " y: " + nodoDestino.getValor().getY1());
						System.out.println("-----------------------------------------------------------");
					} else {
						System.out.println("-----------------------------------------------------------");
						System.out.println("Grupo Enemigo " + i + " se encuentra en x: " + nodoDestino.getValor().getX1() + " y: " + nodoDestino.getValor().getY1());
						System.out.println("-----------------------------------------------------------");
					}
					System.out.println();
				}
				if (factible) {
					grupo.add(JuegoController.getInstance().getMapaGrupos().get(arco.getConexion()[1]));
				}
			}
		}
		if (!grupo.isEmpty()) {
			grupos[pGroupIndex].setConflictoCon(grupo.get(grupo.get(0).getR().nextInt(grupo.size())));
			return true; 
		} else {
			return false;
		}

	}
	
	public void agregar(int pIdGrupo, int pIdAgregar) {
		switch (pIdAgregar) {
			case(ID_ARCHER):
				this.agregarArcher(pIdGrupo);
				break;
			case(ID_KNIGHT):
				this.agregarKnight(pIdGrupo);
				break;
			case(ID_BRAWLER):
				this.agregarBrawler(pIdGrupo);
				break;
		}
	}
	
	public void agregarArcher(int pGroupIndex) {
		if(cantidadArcher == 0) return;
		grupos[pGroupIndex].agregarPersonaje(new Archer());
		cantidadArcher--;
	}
	
	public void agregarBrawler(int pGroupIndex) {
		if (cantidadBrawler == 0) return;
		grupos[pGroupIndex].agregarPersonaje(new Brawler());
		cantidadBrawler--;
	}
	
	public void agregarKnight(int pGroupIndex) {
		if (cantidadKnight== 0) return;
		grupos[pGroupIndex].agregarPersonaje(new Knight());
		cantidadKnight--;
	}
}
