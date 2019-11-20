package model.player;

import java.util.LinkedList;

import model.personajes.Personaje;

public class Group {
	
	private LinkedList<Personaje> personajes;
	
	public Group() {
		personajes = new LinkedList<Personaje>();
	}
	
	public void agregarPersonaje(Personaje pPersonaje) throws IndexOutOfBoundsException {
		if (personajes.size() == 4) {
			throw new IndexOutOfBoundsException();
		}
		
		personajes.add(pPersonaje);
	}
	
}
