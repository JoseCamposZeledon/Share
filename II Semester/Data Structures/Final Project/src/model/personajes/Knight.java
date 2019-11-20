package model.personajes;

public class Knight extends Personaje{
	private final static int FUERZA_ATAQUE = 80;
	private final static int TIEMPO_ATAQUE = 2000;
	
	
	@Override
	public void atacar() {
		this.energia -= FUERZA_ATAQUE;
	}
	
	@Override
	public void recargar() {
		this.energia += FUERZA_ATAQUE / 2;
	}
	
	
}
