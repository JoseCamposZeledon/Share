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

import javax.swing.JOptionPane;

import javax.swing.ImageIcon;

import controller.menu.eventosInicio.EventoGetNodo;
import controller.partida.hostEventos.EventoPersonajeHost;
import controller.partida.hostEventos.EventoReady;
import model.account.Account;
import model.grafo.Grafo;
import model.grafo.GrafoTile;
import model.grafo.Nodo;
import model.json.MapParser;
import model.mapComponents.CrownTile;
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
	
	private Player hostPlayer = new Player(1);

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
		
		
		vista.getArcherLabel().addMouseListener(new EventoPersonajeHost(ID_ARCHER, vista.getArcherLabel()));
		vista.getKnightLabel().addMouseListener(new EventoPersonajeHost(ID_KNIGHT, vista.getKnightLabel()));
		vista.getBrawlerLabel().addMouseListener(new EventoPersonajeHost(ID_BRAWLER, vista.getBrawlerLabel()));
		
		this.getVista().getTableroPane().addMouseListener(new EventoGetNodo());
		
		JuegoController.getInstance().iniciar(mapPath);
		
		
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
	
	public Player getHostPlayer() {
		return hostPlayer;
	}

	public void setHostPlayer(Player hostPlayer) {
		this.hostPlayer = hostPlayer;
	}

	
	@Override
	public synchronized void run() {
		try {
			
			HashMap<Point, Nodo<GrafoTile>> mapaNodos = JuegoController.getInstance().getMapaNodos();
			
			mapaNodos.get(new Point(512, 512)).getValor().setActivo(2);;
			
			Player clients = new Player(2);
			Player clientePlayerLogica = clients;
						
			Player hostPlayerLogica = hostPlayer; 
			
			JuegoController.getInstance().setPlayerCasa(hostPlayerLogica);
			JuegoController.getInstance().setPlayerCasa(clientePlayerLogica);
			
			clientePlayerLogica.agregarArcher(0);
			clientePlayerLogica.agregarArcher(0);
			clientePlayerLogica.agregarBrawler(0);
			clientePlayerLogica.agregarKnight(0);
			
			clientePlayerLogica.agregarKnight(1);
			clientePlayerLogica.agregarArcher(1);
			clientePlayerLogica.agregarBrawler(1);
			clientePlayerLogica.agregarKnight(1);
			
			clientePlayerLogica.getGrupos()[0].setNodoActual(mapaNodos.get(new Point(960,0)));
			clientePlayerLogica.getGrupos()[1].setNodoActual(mapaNodos.get(new Point(960,384)));
			clientePlayerLogica.getGrupos()[2].setNodoActual(mapaNodos.get(new Point(960,768)));
			
			clientePlayerLogica.agregarBrawler(2);
			clientePlayerLogica.agregarArcher(2);
			clientePlayerLogica.agregarBrawler(2);
			clientePlayerLogica.agregarKnight(2);
			
			boolean state;
			
			state = clientePlayerLogica.calcularRuta(mapaNodos.get(new Point(32,0)), 0);
			
			state = clientePlayerLogica.calcularRuta(mapaNodos.get(new Point(32,384)), 1);

			state = clientePlayerLogica.calcularRuta(mapaNodos.get(new Point(32,768)), 2);
	
			
			hostPlayerLogica.agregarArcher(0);
			hostPlayerLogica.agregarArcher(0);
			hostPlayerLogica.agregarBrawler(0);
			hostPlayerLogica.agregarKnight(0);
			
			hostPlayerLogica.agregarKnight(1);
			hostPlayerLogica.agregarArcher(1);
			hostPlayerLogica.agregarBrawler(1);
			hostPlayerLogica.agregarKnight(1);
			
			hostPlayerLogica.agregarBrawler(2);
			hostPlayerLogica.agregarArcher(2);
			hostPlayerLogica.agregarBrawler(2);
			hostPlayerLogica.agregarKnight(2);
			
			hostPlayerLogica.getGrupos()[0].setNodoActual(mapaNodos.get(new Point(32,0)));
			hostPlayerLogica.getGrupos()[1].setNodoActual(mapaNodos.get(new Point(32,384)));
			hostPlayerLogica.getGrupos()[2].setNodoActual(mapaNodos.get(new Point(32,768)));
			
			state = hostPlayerLogica.calcularRuta(mapaNodos.get(new Point(960,0)), 0);
		
			state = hostPlayerLogica.calcularRuta(mapaNodos.get(new Point(960,384)), 1);
	
			state = hostPlayerLogica.calcularRuta(mapaNodos.get(new Point(960,768)), 2);
			
			
			int turno = 0;
			boolean clientGane, hostGane;
			clientGane = false;
			hostGane = false;
			
			while (hostPlayerLogica.enSeguimiento() || clientePlayerLogica.enSeguimiento() && !clientGane && !hostGane) {
				
				for (int i = 0; i < 3; i++) {
					Group grupo = hostPlayerLogica.getGrupos()[i];
					Nodo<GrafoTile> nodoActual = hostPlayerLogica.getGrupos()[i].getNodoActual();
					System.out.println("Grupo Aliado " + i + " se encuentra en x: " + nodoActual.getValor().getX1() + " y: " + nodoActual.getValor().getY1());
					if (grupo.isVivo()) {
						System.out.println("Grupo Aliado" + i +  " esta vivo.");
						if (!grupo.isEnConflicto()) {
							if (turno%2==0) {
								boolean states;
								states = hostPlayerLogica.mover(i);
								if (states ) {
									System.out.println("Grupo Aliado" + i +  " moviendose.");
								}
							} else {
								System.out.println("Grupo Aliado" + i +  " estatico.");
							}
								
						} else {
							System.out.println("Grupo Aliado " + i +  " en conflicto. ");
							int dano = grupo.danoPorMedioSegundo();
							System.out.println("Dano es " + dano);
							boolean vida = grupo.getConflictoCon().restarVida(dano);
							if (vida) {
								System.out.append("Que ha pasado?");
								grupo.getConflictoCon().setVivo(false);
								grupo.getConflictoCon().setEnConflicto(false);
							}
						}
					} else {
						System.out.println("Grupo Aliado " + i +  " esta muerto.");
					}
				}
				
				for (int i = 0; i < 3; i++) {
					Player player = clientePlayerLogica;
					Group grupo = player.getGrupos()[i];
					Nodo<GrafoTile> nodoActual = player.getGrupos()[i].getNodoActual();
					System.out.println("Grupo Enemigo " + i + " se encuentra en x: " + nodoActual.getValor().getX1() + " y: " + nodoActual.getValor().getY1());
					if (grupo.isVivo()) {
						System.out.println("Grupo Enemigo " + i +  " esta vivo.");
						if (!grupo.isEnConflicto()) {
							if (turno%2==0) {
								boolean states;
								states = player.mover(i);
								if (states) {
									System.out.println("Grupo Enemigo " + i +  " moviendose.");
									
								} 
							} else {
								System.out.println("Grupo Enemigo " + i +  " estatico.");
							}
								
						} else {
							System.out.println("Grupo Enemigo " + i +  " en conflicto.");
							int dano = grupo.danoPorMedioSegundo();
							System.out.println("Dano es " + dano);
							boolean vida = grupo.getConflictoCon().restarVida(dano);
							if (vida) {
								System.out.append("Que ha pasado?");
								grupo.getConflictoCon().setVivo(false);
								grupo.getConflictoCon().setEnConflicto(false);
							}
						}
					} else {
						System.out.println("Grupo Enemigo " + i +  " esta muerto.");
					}
				}
				
				HashMap<Group, Integer> mapaDano = new HashMap<Group, Integer>();
				
				
				
				for (int i = 0; i < 3; i++) {
					if (!hostPlayerLogica.getGrupos()[i].isEnConflicto() && hostPlayerLogica.getGrupos()[i].isVivo()) {
						if (hostPlayerLogica.getGrupos()[i].getNodoActual().getValor().getActivo() == 2) {
							hostGane = true;
							System.out.println("Host Gana.");
							
						} else if (hostPlayerLogica.revisar(i)) {
							hostPlayerLogica.getGrupos()[i].setEnConflicto(true);
						} else {
							// no gana y no en conlflcito
						}
					}
					if (!clientePlayerLogica.getGrupos()[i].isEnConflicto() && clientePlayerLogica.getGrupos()[i].isVivo()) {
						if (clientePlayerLogica.getGrupos()[i].getNodoActual().getValor().getActivo() == 2) {
							clientGane = true;
							System.out.println("Host Gana.");
							
						} else if (clientePlayerLogica.revisar(i)) {
							clientePlayerLogica.getGrupos()[i].setEnConflicto(true);
						} else {
							// no gana y no en conlflcito
						}
					}
				}
				
				turno++;
			}
			
			
			ServerSocket server = new ServerSocket(HOST_PORT, 1);
			Socket clientConnected, connect;
			
			boolean connected = true;
			boolean inicio = true;
			
			while (true) {
				clientConnected = server.accept();
				
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
					DataInputStream oIS = new DataInputStream(clientConnected.getInputStream());
					readyClient = oIS.readBoolean();
					this.updateReadyButton(readyClient);
					oIS.close();
					clientConnected.close();
					
					// Ambos estan listos para jugar
					if (readyClient && readyHost && inicio) {
						vista.getReadyHostLabel().removeMouseListener(vista.getReadyHostLabel().getMouseListeners()[0]);
						inicio = false;
						
						continue;
					}
					
					continue;
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
		
		this.getVista().revalidate();
		this.getVista().repaint();
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
	
	public static void main(String[] args) {
		Account acc1 = new Account("a@a.com", "123");
		PartidaHostController.createInstance(".//static//maps//mapa1.json", acc1);
		ThreadManager.getInstance().startThread(PartidaHostController.getInstance());
		
	}
}	
