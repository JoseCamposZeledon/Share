package model.player;

import java.util.LinkedList;
import java.util.Random;

import javax.swing.JLabel;

import controller.partida.JuegoController;
import controller.partida.PartidaHostController;
import model.grafo.GrafoTile;
import model.grafo.Nodo;
import model.personajes.Archer;
import model.personajes.Brawler;
import model.personajes.Knight;
import model.personajes.Personaje;

public class Group {
	
	private int id;
	private LinkedList<Nodo<GrafoTile>> ruta;
	private LinkedList<Personaje> personajes;
	private int vidaTeam;
	private Nodo<GrafoTile> nodoActual;
	private Random r;
	private boolean vivo;
	private boolean enConflicto;
	private Group conflictoCon;
	
	private JLabel personajesLabel;

	public Group(int id) {
		personajes = new LinkedList<Personaje>();
		ruta = new LinkedList<Nodo<GrafoTile>>();
		vidaTeam = 0;
		r = new Random();
		vivo = true;
		enConflicto = false;
		this.id = id;
	}
	
	public LinkedList<Personaje> getPersonajes() {
		return personajes;
	}
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setPersonajes(LinkedList<Personaje> personajes) {
		this.personajes = personajes;
	}

	public int getVidaTeam() {
		return vidaTeam;
	}

	public void setVidaTeam(int vidaTeam) {
		this.vidaTeam = vidaTeam;
	}
	
	public boolean isVivo() {
		return vivo;
	}

	public void setVivo(boolean vivo) {
		this.vivo = vivo;
	}

	public LinkedList<Nodo<GrafoTile>> getRuta() {
		return ruta;
	}

	public void setRuta(LinkedList<Nodo<GrafoTile>> ruta) {
		this.ruta = ruta;
	}

	public Nodo<GrafoTile> getNodoActual() {
		return nodoActual;
	}

	public void setNodoActual(Nodo<GrafoTile> nodoActual) {
		this.nodoActual = nodoActual;
	}
	
	public boolean isEnConflicto() {
		return enConflicto;
	}

	public void setEnConflicto(boolean enConflicto) {
		this.enConflicto = enConflicto;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Group other = (Group) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public Random getR() {
		return r;
	}

	public void setR(Random r) {
		this.r = r;
	}
	
	public Group getConflictoCon() {
		return conflictoCon;
	}

	public void setConflictoCon(Group conflictoCon) {
		this.conflictoCon = conflictoCon;
	}
	
	
	
	public void agregarPersonaje(Personaje pPersonaje) throws IndexOutOfBoundsException {
		if (personajes.size() == 4) {
			throw new IndexOutOfBoundsException();
		} else {
			personajes.add(pPersonaje);
			vidaTeam += 100;
		}
	}
	
	public int danoPorMedioSegundo() {
		int total = 0;
		for (Personaje personaje : personajes) {
			total += personaje.getAtaque().ataque();
		}
		return total;
	}
	
	public boolean restarVida(int vida) {
		vidaTeam -= vida;
		if (vida <= 0) {
			return true;
		} 
		while (((vidaTeam-1) / 100) + 1 < personajes.size()) {
			int index = r.nextInt(personajes.size());
			personajes.remove(index);
		}
		return false;
	}
	
	public boolean calcularRuta(Nodo<GrafoTile> nodoDestino) {
		ruta = JuegoController.getInstance().getGrafoNodos().dijkstra(nodoActual, nodoDestino);
		if (ruta.isEmpty()) {
			return false;
		} else {
			ruta.remove(0);
			return true;
		}
	}
	
	public JLabel getPersonajesLabel() {
		return personajesLabel;
	}

	public void setPersonajesLabel(JLabel personajesLabel) {
		this.personajesLabel = personajesLabel;
	}
	
	public static void main(String[] args) {
		
		Group g = new Group(1);
		g.agregarPersonaje(new Archer());
		g.agregarPersonaje(new Brawler());
		g.agregarPersonaje(new Knight());
		g.agregarPersonaje(new Archer());
		
		System.out.println(g.getVidaTeam());
		System.out.println(g.danoPorMedioSegundo());
		System.out.println(g.danoPorMedioSegundo());
		g.restarVida(100);
		System.out.println(g.danoPorMedioSegundo());
		System.out.println(g.danoPorMedioSegundo());
		System.out.println(g.danoPorMedioSegundo());
		System.out.println(g.danoPorMedioSegundo());
		
	}
}
