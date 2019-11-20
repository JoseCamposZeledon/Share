package model.personajes;

public class Archer extends Personaje{
	private final static int FUERZA_ATAQUE = 40;
	private final static int TIEMPO_ATAQUE = 1500;
	
	
	@Override
	public void atacar() {
		this.energia -= FUERZA_ATAQUE;
	}
	
	@Override
	public void recargar() {
		this.energia += FUERZA_ATAQUE / 2;
	}
}
