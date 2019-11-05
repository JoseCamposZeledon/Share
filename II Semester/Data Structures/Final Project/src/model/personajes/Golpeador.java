package model.personajes;

public class Golpeador extends Personaje{
	private final static int FUERZA_ATAQUE = 20;
	private final static int TIEMPO_ATAQUE = 1000;
	
	
	@Override
	public void atacar() {
		this.energia -= FUERZA_ATAQUE;	
	}
	
	@Override
	public void recargar() {
		this.energia += FUERZA_ATAQUE / 2;
	}
}
