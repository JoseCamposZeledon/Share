package model.personajes;

public abstract class Personaje {
	protected int energia = 100;
	protected int fuerzaAtaque = 10;
	
	public abstract void atacar();
	
	public abstract void recargar();

	public int getEnergia() {
		return energia;
	}

	public void setEnergia(int energia) {
		this.energia = energia;
	}
}
