package controller.partida;

import java.awt.Event;
import java.awt.Image;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;

import javax.swing.JOptionPane;

import javax.swing.ImageIcon;

import controller.partida.hostEventos.EventoReady;
import model.account.Account;
import model.grafo.Grafo;
import model.grafo.GrafoTile;
import model.grafo.Nodo;
import model.json.MapParser;
import model.mapComponents.ObstaculoGrafico;
import model.threadsPool.ThreadManager;
import view.partida.VistaPartidaHost;
import view.partida.VistaPartidaUser;

public class PartidaHostController implements Runnable, Serializable, IConstants {
	
	private static PartidaHostController instancia = null;
	
	private Account host, client;
	private VistaPartidaHost vista;
	
	private String mapPath;
	
	private boolean readyHost;
	
	private PartidaClientController controllerClient;
	private HashMap<Point, Nodo<GrafoTile>> mapaNodos;
	private Grafo<GrafoTile> grafoNodos;
	
	private int addBuffer = 1000;
	
	
	private PartidaHostController(String pMapPath, Account pHost) {
		
		host = pHost;
		mapPath = pMapPath;
		
		vista = new VistaPartidaHost();
		
		loadMap();
		
		// Evento READY
		vista.getReadyHostLabel().addMouseListener(new EventoReady(this));
		
		vista.getInfoHost().setText(host.getCorreo() + " | " + host.getCounterVictorias());
		vista.getInfoClient().setText("USUARIO DESCONECTADO");
		
		mapaNodos = new HashMap<Point, Nodo<GrafoTile>>();
		grafoNodos = new Grafo<GrafoTile>();
	}
	
	
	public static PartidaHostController createInstance(String pMapPath, Account pHost) {
		if (instancia == null) {
			instancia = new PartidaHostController(pMapPath, pHost);
		}
		return instancia;
	}
	
	public static PartidaHostController getInstance() {
		return instancia;
	}

	public void loadMap() {
		for(ObstaculoGrafico obstaculo: MapParser.getInstance().loadMap(mapPath).getObstaculos()) {
			vista.getTableroPane().add(obstaculo.getGraphicObstaculo(), 1);
		}
	}
	
	public Account getHost() {
		return host;
	}

	public void setHost(Account host) {
		this.host = host;
	}

	public VistaPartidaHost getVista() {
		return vista;
	}

	public void setVista(VistaPartidaHost vista) {
		this.vista = vista;
	}

	public boolean isReadyHost() {
		return readyHost;
	}

	public void setReadyHost(boolean readyHost) {
		this.readyHost = readyHost;
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


	public int getAddBuffer() {
		return addBuffer;
	}


	public void setAddBuffer(int addBuffer) {
		this.addBuffer = addBuffer;
	}


	private void generarArbolNodos() {
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
		int arco = 1;
		if (nodo.getValor().isEsObstaculo() && nodoDestino.getValor().isEsObstaculo()) {
			arco = addBuffer;
		}
		nodo.conectar(nodoDestino, arco);
	}
	
	private void conectarArbolNodos() {
		
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
			int y1 = obstaculo.getY1();
			
			while (x1 <= obstaculo.getX2()) {
				while (y1 <= obstaculo.getY2()) {
					int truncatedX = (x1 / 32) * 32;
					int truncatedY = (y1 / 32) * 32;
					Nodo<GrafoTile> nodo = mapaNodos.get(new Point(truncatedX, truncatedY));
					nodo.getValor().setEsObstaculo(true);
					y1 += 32;
				}
				x1 += 32;
			}
			// vista.getTableroPane().add(obstaculo.getGraphicObstaculo(), 1);
		}
	}
	public void generarArbol(String pPath) {
		generarArbolNodos();
		computarNodosObstaculos(pPath);
		conectarArbolNodos();
	}

	@Override
	public synchronized void run() {
		try {
			// Crea un servidor en el puerto 9999
			ServerSocket server = new ServerSocket(HOST_PORT, 1);
			Socket clientConnected, connect;
			
			boolean connected = true;
			
			while (true) {
				clientConnected = server.accept();
				// Conecta al host de la partida con el servidor del cliente
				if (clientConnected.isConnected() && connected == true) {
					connect = new Socket(IP, CLIENT_PORT);
					ObjectOutputStream oOS = new ObjectOutputStream(connect.getOutputStream());
					oOS.writeObject(this);
					oOS.close();
					
					ObjectInputStream oIS = new ObjectInputStream(clientConnected.getInputStream());
					controllerClient = (PartidaClientController) oIS.readObject();
					
					this.getVista().getInfoClient().setText(
							controllerClient.getClient().getCorreo() + " | " + 
							controllerClient.getClient().getCounterVictorias());
					
					this.getVista().revalidate();
					this.getVista().repaint();
					
					connected = false;
					continue;
				}
				
				// Actualiza el boton READY en la pantalla del host cuando el client le da click
				if (clientConnected != null) {
					ObjectInputStream oIS = new ObjectInputStream(clientConnected.getInputStream());
					controllerClient = (PartidaClientController) oIS.readObject();
					this.updateReadyButton(controllerClient.isReadyClient());
					oIS.close();
					clientConnected.close();
				}
				
			}
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void updateReadyButton(boolean pReady) {
		if (!pReady) {
			this.getVista().getReadyClientLabel().setIcon(new ImageIcon(new ImageIcon(".\\static\\media\\images\\notready_button.png")
					.getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH)));
		} else {
			this.getVista().getReadyClientLabel().setIcon(new ImageIcon(new ImageIcon(".\\static\\media\\images\\ready_button.png")
					.getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH)));
		}
		
		this.getVista().revalidate();
		this.getVista().repaint();
	}
	
	
	public String getMapPath() {
		return mapPath;
	}


	public void setMapPath(String mapPath) {
		this.mapPath = mapPath;
	}
	
	public static void main(String[] args) {
		PartidaHostController.createInstance(".//static//maps//mapa1.json", new Account("a@a.com", "123"));
		PartidaHostController.getInstance().generarArbol(".//static//maps//mapa1.json");
	}
	
	
}	
