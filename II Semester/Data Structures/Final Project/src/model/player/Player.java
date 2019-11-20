package model.player;

import java.util.LinkedList;

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
