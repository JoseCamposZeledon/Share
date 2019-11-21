package controller.partida;

import java.awt.Point;
import java.util.HashMap;

import model.grafo.Grafo;
import model.grafo.GrafoTile;
import model.grafo.Nodo;
import model.json.MapParser;
import model.mapComponents.ObstaculoGrafico;
import model.player.Group;
import model.player.Player;

public class JuegoController {
	
	private Player playerCasa;
	private Player playerVisita;
	
	private static JuegoController instance;
	
	private HashMap<Point, Nodo<GrafoTile>> mapaNodos;
	private Grafo<GrafoTile> grafoNodos;
	
	private String mapPath;
	private HashMap<Nodo<GrafoTile>, Group> mapaGrupos;
	private boolean conectado = false;
	private int addBuffer = 1000;
	
	private JuegoController() {
		mapaNodos = new HashMap<Point, Nodo<GrafoTile>>();
		grafoNodos = new Grafo<GrafoTile>();
		mapaGrupos = new HashMap<Nodo<GrafoTile>, Group>();
	}
	
	public static JuegoController getInstance() {
		if (instance == null) {
			instance = new JuegoController();
		}
		return instance;
	}
	
	public Player getPlayerCasa() {
		return playerCasa;
	}

	public void setPlayerCasa(Player playerCasa) {
		this.playerCasa = playerCasa;
	}

	public Player getPlayerVisita() {
		return playerVisita;
	}

	public void setPlayerVisita(Player playerVisita) {
		this.playerVisita = playerVisita;
	}

	public void setMapaGrupos(HashMap<Nodo<GrafoTile>, Group> mapaGrupos) {
		this.mapaGrupos = mapaGrupos;
	}
	
	public HashMap<Point, Nodo<GrafoTile>> getMapaNodos() {
		return mapaNodos;
	}

	public void setMapaNodos(HashMap<Point, Nodo<GrafoTile>> mapaNodos) {
		this.mapaNodos = mapaNodos;
	}

	public Grafo<GrafoTile> getGrafoNodos() {
		return grafoNodos;
	}

	public void setGrafoNodos(Grafo<GrafoTile> grafoNodos) {
		this.grafoNodos = grafoNodos;
	}

	public String getMapPath() {
		return mapPath;
	}

	public void setMapPath(String mapPath) {
		this.mapPath = mapPath;
	}

	public boolean isConectado() {
		return conectado;
	}

	public void setConectado(boolean conectado) {
		this.conectado = conectado;
	}

	public int getAddBuffer() {
		return addBuffer;
	}

	public void setAddBuffer(int addBuffer) {
		this.addBuffer = addBuffer;
	}

	public HashMap<Nodo<GrafoTile>, Group> getMapaGrupos() {
		return mapaGrupos;
	}

	public static void setInstance(JuegoController instance) {
		JuegoController.instance = instance;
	}

	private void generarGrafoNodos() {
		int x1 = 0;
		while (x1 < 1024) {
			int y1 = 0;
			while (y1 < 800) {
				Nodo<GrafoTile> nodito = new Nodo<GrafoTile>(new GrafoTile(x1, y1, false));
				mapaNodos.put(new Point(x1, y1), nodito);
				grafoNodos.agregarNodo(nodito);
				y1 += 32;
			}
			x1 += 32;
		}
	}
	
	private void conectarNodoAux(Nodo<GrafoTile> nodo, Nodo<GrafoTile> nodoDestino) {
		if (!conectado) {
			if (!nodo.getValor().isEsObstaculo() && !nodoDestino.getValor().isEsObstaculo()) {
				nodo.conectar(nodoDestino, 1);
			}
		} else {
			int arco = 1;
			if (nodo.getValor().isEsObstaculo() || nodoDestino.getValor().isEsObstaculo()) {
				arco = addBuffer;
			}
			nodo.conectar(nodoDestino, arco);
		}
	}
	
	private void conectarGrafoNodos() {
		
		for (Nodo<GrafoTile> nodo : grafoNodos.getNodos()) {
			
			int x = nodo.getValor().getX1();
			int y = nodo.getValor().getY1();
			
			if (y >= 32) {
				// arriba
				conectarNodoAux(nodo, mapaNodos.get(new Point(x, y-32)));
				// arriba derecha
				if (x < 992) {
					conectarNodoAux(nodo, mapaNodos.get(new Point(x+32, y-32)));
				}
				// arriba izquierda 
				if (x >= 32) {
					conectarNodoAux(nodo, mapaNodos.get(new Point(x-32, y-32)));
				}
			}
			if (y < 768) {
				// abajo
				conectarNodoAux(nodo, mapaNodos.get(new Point(x, y+32)));
				// abajo derecha
				if (x < 992) {
					conectarNodoAux(nodo, mapaNodos.get(new Point(x+32, y+32)));
				}
				// abajo izquierda 
				if (x >= 32) {
					conectarNodoAux(nodo, mapaNodos.get(new Point(x-32, y+32)));
				}
			}
			if (x >= 32) {
				// izquierda
				conectarNodoAux(nodo, mapaNodos.get(new Point(x-32, y)));
			}
			if (x < 992) {
				// derecha
				conectarNodoAux(nodo, mapaNodos.get(new Point(x+32, y)));
			}
			
		}
	}
	
	private void computarNodosObstaculos(String pPath) {
		for(ObstaculoGrafico obstaculo: MapParser.getInstance().loadMap(pPath).getObstaculos()) {
			int x1 = obstaculo.getX1();
			int truncatedX = (x1 / 32) * 32;
			while (truncatedX < obstaculo.getX2()) {
				int y1 = obstaculo.getY1();
				int truncatedY = (y1 / 32) * 32;
				while (truncatedY < obstaculo.getY2()) {
					Nodo<GrafoTile> nodo = mapaNodos.get(new Point(truncatedX, truncatedY));
					nodo.getValor().setEsObstaculo(true);
					truncatedY += 32;
				}
				truncatedX += 32;
			}
		}
	}
	
	public void generarGrafo(String pPath) {
		generarGrafoNodos();
		computarNodosObstaculos(pPath);
		conectarGrafoNodos();
	}
	
	public void iniciar(String pPath) {
		generarGrafo(pPath);
	}
	
}
