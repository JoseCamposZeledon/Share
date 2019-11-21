package model.player;

import java.util.ArrayList;
import java.util.LinkedList;

import controller.partida.PartidaHostController;
import model.grafo.Arco;
import model.grafo.GrafoTile;
import model.grafo.Nodo;
import model.personajes.Archer;
import model.personajes.Brawler;
import model.personajes.Knight;
import model.personajes.Personaje;

public class Player {
	
	private boolean crownPlaced;
	
	private int cantidadArcher = 4;
	private int cantidadBrawler = 6;
	private int cantidadKnight = 2;
	
	private Group[] grupos = new Group[3];
	
	public Player() {
		grupos[0] = new Group();
		grupos[1] = new Group();
		grupos[2] = new Group();
	}
	
	public Group[] getGrupos() {
		return grupos;
	}

	public void setGrupos(Group[] grupos) {
		this.grupos = grupos;
	}

	public boolean calcularRuta(Nodo<GrafoTile> nodoDestino, int pGroupIndex) {
		return grupos[pGroupIndex].calcularRuta(nodoDestino);
	}
	
	public void mover(int pGroupIndex) {
		Group grupo = grupos[pGroupIndex];
		if (!grupo.getRuta().isEmpty() && grupo.isVivo()) { 
			grupo.getNodoActual().getValor().setActivo(0);
			grupo.setNodoActual(grupo.getRuta().get(0));
			PartidaHostController.getInstance().getMapaGrupos().put(grupo.getNodoActual(), grupo);
			grupo.getRuta().get(0).getValor().setActivo(1);
			grupo.getRuta().remove(0);
		}
	}
	
	public ArrayList<Group> revisar(int pGroupIndex) {
		// grupo para atacar
		ArrayList<Group> grupo = new ArrayList<Group>();
		for (Arco<GrafoTile> arco : grupos[pGroupIndex].getNodoActual().getConexiones()) {
			if (arco.getConexion()[1].getValor().getActivo() == 1) {
				grupo.add(PartidaHostController.getInstance().getMapaGrupos().get(arco.getConexion()[1]));
			}
		}
		return grupo;
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
		if (cantidadBrawler == 0) return;
		grupos[pGroupIndex].agregarPersonaje(new Knight());
		cantidadBrawler--;
	}
}
