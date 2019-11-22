package controller.partida;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import javax.swing.JOptionPane;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import controller.menu.eventosInicio.EventoGetNodo;
import controller.partida.hostEventos.EventoPersonajeHost;
import controller.partida.hostEventos.EventoReady;
import model.account.Account;
import model.grafo.Grafo;
import model.grafo.GrafoTile;
import model.grafo.Nodo;
import model.json.MapParser;
import model.mapComponents.CrownTile;
import model.mapComponents.GroupTile;
import model.mapComponents.ObstaculoGrafico;
import model.personajes.Archer;
import model.personajes.Brawler;
import model.personajes.Knight;
import model.player.Group;
import model.player.Player;
import model.threadsPool.ThreadManager;
import view.partida.VistaPartidaHost;
import view.partida.VistaPartidaUser;

public class PartidaHostController implements Runnable, IConstants {
	
	private static PartidaHostController instancia = null;
	
	private Account host, client;
	private VistaPartidaHost vista;

	private String mapPath;
	
	private boolean readyHost, readyClient;
	
	private HashMap<Point, Nodo<GrafoTile>> mapaNodos;
	private Grafo<GrafoTile> grafoNodos;
	
	private HashMap<Nodo<GrafoTile>, Group> mapaGrupos;
	
	private boolean conectado = false;
	
	private Player hostPlayer = new Player();

	private int addBuffer = 1000;
	private int idPersonajeSelected = 0;
	
	private PartidaHostController(String pMapPath, Account pHost) {
		host = pHost;
		mapPath = pMapPath;
		
		vista = new VistaPartidaHost();
		loadMap();
		
		readyHost = false;
		
		// Evento READY
		vista.getReadyHostLabel().addMouseListener(new EventoReady(this));
		
		vista.getInfoHost().setText(host.getCorreo() + " | " + host.getCounterVictorias());
		vista.getInfoClient().setText("USUARIO DESCONECTADO");
		
		mapaNodos = new HashMap<Point, Nodo<GrafoTile>>();
		grafoNodos = new Grafo<GrafoTile>();
		mapaGrupos = new HashMap<Nodo<GrafoTile>, Group>();
		
		vista.getArcherLabel().addMouseListener(new EventoPersonajeHost(ID_ARCHER, vista.getArcherLabel()));
		vista.getKnightLabel().addMouseListener(new EventoPersonajeHost(ID_KNIGHT, vista.getKnightLabel()));
		vista.getBrawlerLabel().addMouseListener(new EventoPersonajeHost(ID_BRAWLER, vista.getBrawlerLabel()));
		
		this.getVista().getTableroPane().addMouseListener(new EventoGetNodo());
		
		this.generarGrafo(mapPath);
		
		vista.getTableroPane().removeEvents(1);
		
//		for (Nodo<GrafoTile> actual : this.grafoNodos.dijkstra(
//				mapaNodos.get(new Point(32, 0)), mapaNodos.get(new Point(0, 128))
//				)) {
//			System.out.println("Actual: X: " + actual.getValor().getX1() + " Y: " + actual.getValor().getY1());
//		}
		
		
		//hostPlayer.getGrupos()[0].calcularRuta(mapaNodos.get(new Point(960,384)));
		//hostPlayer.getGrupos()[0].calcularRuta(mapaNodos.get(new Point(960,768)));
		
	}
	
	public static PartidaHostController createInstance(String pMapPath, Account pHost) {
		if (instancia == null) {
			instancia = new PartidaHostController(pMapPath, pHost);
		}
		//hostPlayer.calcularRuta(mapaNodos.get(new Point(960,0)), 0);
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

	public void Host(boolean readyHost) {
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

	public HashMap<Nodo<GrafoTile>, Group> getMapaGrupos() {
		return mapaGrupos;
	}


	public void setMapaGrupos(HashMap<Nodo<GrafoTile>, Group> mapaGrupos) {
		this.mapaGrupos = mapaGrupos;
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

	public Player getHostPlayer() {
		return hostPlayer;
	}

	public void setHostPlayer(Player hostPlayer) {
		this.hostPlayer = hostPlayer;
	}

	
	@Override
	public synchronized void run() {
		try {
			
			ServerSocket server = new ServerSocket(HOST_PORT, 1);
			Socket clientConnected, connect;
			
			boolean connected = true;
			boolean inicio = true;
			boolean pintarEnemigo = false;
			ArrayList<CrownTile> crowns;
			ArrayList<GroupTile> groups;
			
			boolean first = false;
			boolean second = false;
			boolean caminar = false;
			
			while (true) {
				clientConnected = server.accept();
				
				if (readyClient && !readyHost) {
					first = false;
					second = true;
				} else if (!readyClient && readyHost){
					first = true;
					second = false;
				}
				
				// Conecta al host de la partida con el servidor del cliente
				if (clientConnected.isConnected() && connected == true) {
					
					connect = new Socket(IP, CLIENT_PORT);
					
					ObjectOutputStream oOS = new ObjectOutputStream(connect.getOutputStream());
				
					oOS.writeObject(this.host);
					
					oOS.flush();
					
					oOS.writeUTF(this.mapPath);
					
					oOS.close();
					
					ObjectInputStream oIS = new ObjectInputStream(clientConnected.getInputStream());
					client = (Account) oIS.readObject();
					
					this.getVista().getInfoClient().setText(
							client.getCorreo() + " | " + 
							client.getCounterVictorias());
					
					this.getVista().revalidate();
					this.getVista().repaint();
					
					connected = false;
					oIS.close();
					clientConnected.close();
					continue;
				}
				
				// Actualiza el boton READY en la pantalla del host cuando el client le da click
				if (clientConnected != null && inicio) {
					
					if (inicio) {
						DataInputStream oIS = new DataInputStream(clientConnected.getInputStream());
						readyClient = oIS.readBoolean();
						this.updateReadyButton(readyClient);
						oIS.close();
						clientConnected.close();
					}
					
					// Ambos estan listos para jugar
					if (readyClient && readyHost && inicio) {
						
						vista.getReadyHostLabel().removeMouseListener(vista.getReadyHostLabel().getMouseListeners()[0]);
						inicio = false;
						
						Socket socketEvento = new Socket(IP, CLIENT_PORT);
						
						DataOutputStream streamOS = new DataOutputStream(socketEvento.getOutputStream());
						streamOS.writeBoolean(this.isReadyHost());
						streamOS.close();
						
						// Quita los eventos de los tiles
						for (CrownTile a : vista.getTableroPane().getHostCrownTiles()) {
							a.removeMouseListener(a.getMouseListeners()[0]);
						}
						
						for (GroupTile a : vista.getTableroPane().getHostGroupTiles()) {
							a.removeMouseListener(a.getMouseListeners()[0]);
						}
						
						pintarEnemigo = true;
						
						continue;
					}
					
					continue;
				}
				
				// El cliente envia los valores si es el primero en presionar el boton ready
				if (pintarEnemigo) {
					Socket socketEvento = new Socket(IP, CLIENT_PORT);
					
					ObjectOutputStream streamOS = new ObjectOutputStream(socketEvento.getOutputStream());
					streamOS.writeObject(this.getVista().getTableroPane().getHostCrownTiles());
					streamOS.writeObject(this.getVista().getTableroPane().getHostGroupTiles());
					streamOS.close();
					socketEvento.close();
					
					if (clientConnected.getInputStream().available() > 4) {
						
						Socket socketEvento2 = new Socket(IP, CLIENT_PORT);
						
						ObjectOutputStream streamOS2 = new ObjectOutputStream(socketEvento2.getOutputStream());
						streamOS2.writeObject(this.getVista().getTableroPane().getHostCrownTiles());
						streamOS2.writeObject(this.getVista().getTableroPane().getHostGroupTiles());
						streamOS2.close();

						ObjectInputStream streamIS = new ObjectInputStream(clientConnected.getInputStream());
						crowns = (ArrayList<CrownTile>) streamIS.readObject();
						groups = (ArrayList<GroupTile>) streamIS.readObject();
						
						for (CrownTile a : crowns) {
							this.getVista().getTableroPane().add(a, 3);;
						}
						
						for (GroupTile a : groups) {
							this.getVista().getTableroPane().add(a, 3);
						}
						
						pintarEnemigo = false;
						caminar = true;
						this.notifyView();
						
						if (caminar) {
							this.walk(groups);
							caminar = false;
						}
					}
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

	public void walk(ArrayList<GroupTile> pGroups) {
		JLabel nodo1 = PartidaHostController.getInstance().getHostPlayer().getGrupos()[0].getPersonajesLabel();
		JLabel nodo2 = PartidaHostController.getInstance().getHostPlayer().getGrupos()[1].getPersonajesLabel();
		JLabel nodo3 = PartidaHostController.getInstance().getHostPlayer().getGrupos()[2].getPersonajesLabel();
		
		LinkedList<Nodo<GrafoTile>> camino1 = this.grafoNodos.dijkstra(this.getMapaNodos().get(new Point(32, 0)), 
				this.getMapaNodos().get(new Point(992, 768))); 
		
		this.grafoNodos.limpiar();
		
		LinkedList<Nodo<GrafoTile>> camino2 = this.grafoNodos.dijkstra(this.getMapaNodos().get(new Point(32, 384)), 
				this.getMapaNodos().get(new Point(992, 284))); 
		
		this.grafoNodos.limpiar();
		
		LinkedList<Nodo<GrafoTile>> camino3 = this.grafoNodos.dijkstra(this.getMapaNodos().get(new Point(32, 768)), 
				this.getMapaNodos().get(new Point(992, 0))); 
		
		this.grafoNodos.limpiar();
		
		Thread t = new Thread(new Runnable() {
		    @Override
		    
		    
		    public void run() {
	
		    	
		    	while (true) {
		    		
		    		for (Nodo<GrafoTile> a: camino1) {
		    			nodo1.setLocation(a.getValor().getX1(), a.getValor().getY1());
		    			
		    			try {
							Thread.sleep(500);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		    		}
		    			
		    		for (Nodo<GrafoTile> a: camino2) {
		    			nodo2.setLocation(a.getValor().getX1(), a.getValor().getY1());
		    			
		    			try {
							Thread.sleep(500);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		    		}
		    		
		    		for (Nodo<GrafoTile> a: camino3) {
		    			nodo3.setLocation(a.getValor().getX1(), a.getValor().getY1());
		    			
		    			try {
							Thread.sleep(500);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		    		}
				}
		    }
		});
		t.start();
		
	}
	
	public void notifyView() {
		vista.update();
	}
	
	public void updateReadyButton(boolean pReady) {
		if (!pReady) {
			this.getVista().getReadyClientLabel().setIcon(new ImageIcon(new ImageIcon(".\\static\\media\\images\\notready_button.png")
					.getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH)));
		} else {
			this.getVista().getReadyClientLabel().setIcon(new ImageIcon(new ImageIcon(".\\static\\media\\images\\ready_button.png")
					.getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH)));
		}
		
		this.notifyView();
	}
	
	
	public String getMapPath() {
		return mapPath;
	}

	public int getIdPersonajeSelected() {
		return idPersonajeSelected;
	}

	public void setIdPersonajeSelected(int idPersonajeSelected) {
		this.idPersonajeSelected = idPersonajeSelected;
	}
	
	public void setReadyHost(boolean readyHost) {
		this.readyHost = readyHost;
	}
	
	public void setMapPath(String mapPath) {
		this.mapPath = mapPath;
	}
	
}
