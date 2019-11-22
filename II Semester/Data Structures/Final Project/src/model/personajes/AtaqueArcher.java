package model.personajes;

public class AtaqueArcher implements Ataque {
	
	private double tiempoEspera = 0;
	private double limite = 1.5;
	
	public double getTiempoEspera() {
		return tiempoEspera;
	}

	public void setTiempoEspera(double tiempoEspera) {
		this.tiempoEspera = tiempoEspera;
	}
	
	public void incrementarEspera() {
		tiempoEspera += 0.5;
	}
	
	@Override
	public int ataque() {
		incrementarEspera();
		if (tiempoEspera == limite) {
			tiempoEspera = 0;
			return 40;	
		}
		return 0;
	}

}
