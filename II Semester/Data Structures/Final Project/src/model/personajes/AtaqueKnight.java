package model.personajes;

public class AtaqueKnight implements Ataque {
	
	private double tiempoEspera = 0;
	private double limite = 2;
	
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
			return 80;	
		}
		return 0;
	}
}
