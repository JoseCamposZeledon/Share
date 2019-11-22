package model.personajes;

public abstract class Personaje {
	
	static int idCounter;
	protected int id;
	protected Ataque ataque; 
	
	public Personaje() {
		id = idCounter;
		idCounter++;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Ataque getAtaque() {
		return ataque;
	}

	public void setAtaque(Ataque ataque) {
		this.ataque = ataque;
	}
	
	
}
