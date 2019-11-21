package controller.partida;

import java.awt.Image;
import java.io.DataInputStream;
import java.awt.Point;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.rmi.server.ServerRef;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import controller.partida.clientEventos.EventoClientPersonaje;
import controller.partida.clientEventos.EventoReady;
import model.account.Account;
import model.grafo.Grafo;
import model.grafo.GrafoTile;
import model.grafo.Nodo;
import model.json.MapParser;
import model.mapComponents.CrownTile;
import model.mapComponents.ObstaculoGrafico;
import model.player.Player;
import model.threadsPool.ThreadManager;
import view.partida.VistaPartidaUser;

public class PartidaClientController implements Runnable, IConstants{
	
	private static PartidaClientController instancia = null;
	
	private Account host, client;
	private VistaPartidaUser vista;
	private boolean readyClient, readyHost;

	private ArrayList<CrownTile> tilesCorona;
	
	private Player clientPlayer = new Player(2);
	
	private int idPersonajeSelected = 0;

	public int getIdPersonajeSelected() {
		return idPersonajeSelected;
	}

	public void setIdPersonajeSelected(int idPersonajeSelected) {
		this.idPersonajeSelected = idPersonajeSelected;
	}

	private PartidaClientController(Account pClient) throws IOException, UnknownHostException, Exception{
		
		client = pClient;
		tilesCorona = new ArrayList<CrownTile>();
		
		try {
			// Se conecta a ese IP
			Socket socket = new Socket(IP, HOST_PORT);
			
			// Envia el usuario
			ObjectOutputStream oOS = new ObjectOutputStream(socket.getOutputStream());
			oOS.writeObject(this.client);
			oOS.close();
			
			// Empieza el servido en un nuevo hilo
			ThreadManager.getInstance().startThread(this);
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			throw new UnknownHostException();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new IOException();
		}
		
		vista = new VistaPartidaUser();
		
		readyClient = false;
		
		vista.getArcherLabel().addMouseListener(new EventoClientPersonaje(ID_ARCHER, vista.getArcherLabel()));
		vista.getKnightLabel().addMouseListener(new EventoClientPersonaje(ID_KNIGHT, vista.getKnightLabel()));
		vista.getBrawlerLabel().addMouseListener(new EventoClientPersonaje(ID_BRAWLER, vista.getBrawlerLabel()));
		
		vista.getReadyClientLabel().addMouseListener(new EventoReady(this));
		vista.getInfoClient().setText(client.getCorreo() + " | " + client.getCounterVictorias());
		vista.getTableroPane().removeEvents(2);
	}
	
	public static PartidaClientController createInstance(Account pClient) throws IOException, UnknownHostException{
		if (instancia == null) {
			try {
				instancia = new PartidaClientController(pClient);
			} catch (UnknownHostException e) {
				throw new UnknownHostException();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				throw new IOException();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return instancia;
	}
	
	public void addCoronaTile(CrownTile pTile) {
		this.tilesCorona.add(pTile);
	}
	
	public void loadMap(String pPath) {
		for(ObstaculoGrafico obstaculo: MapParser.getInstance().loadMap(pPath).getObstaculos()) {
			vista.getTableroPane().add(obstaculo.getGraphicObstaculo(), 1);
		}
	}
	
	public void notifyView() {
		this.getVista().update();
	}
	
	public Player getClientPlayer() {
		return clientPlayer;
	}

	public void setClientPlayer(Player clientPlayer) {
		this.clientPlayer = clientPlayer;
	}
	
	public static PartidaClientController getInstance() {
		return instancia;
	}

	public Account getClient() {
		return client;
	}


	public void setClient(Account client) {
		this.client = client;
	}


	public VistaPartidaUser getVista() {
		return vista;
	}


	public void setVista(VistaPartidaUser vista) {
		this.vista = vista;
	}
	

	public boolean isReadyClient() {
		return readyClient;
	}

	public void setReadyClient(boolean readyClient) {
		this.readyClient = readyClient;
	}

	@Override
	public synchronized void run() {
		try {
			Socket hostConnected, connect, infoUpdate;
			ServerSocket server = new ServerSocket(CLIENT_PORT, 2);
			
			boolean connected = true;
			boolean inicio = true;
			boolean pintarEnemigo = false;
			
			// Espera a que el host se pueda conectar
			while (true) {
				hostConnected = server.accept();	
				
				// Carga el mapa & la informacion del host de partida
				if (hostConnected.isConnected() && connected == true) {
					
					ObjectInputStream oIS = new ObjectInputStream(hostConnected.getInputStream());
					host = (Account) oIS.readObject();
					
					// Host es igual a client
					
					this.getVista().getInfoHost().setText(
							host.getCorreo() + " | " + 
							host.getCounterVictorias());
					
					String mapaPath = oIS.readUTF();
					
					this.loadMap(mapaPath);
					
					this.getVista().revalidate();
					this.getVista().repaint();
					
					connected = false;
					continue;
				}
				
				// Cambia el estado del boton READY cuando host hace click
				if (hostConnected != null && inicio) {
					if (inicio) {
						DataInputStream oIS = new DataInputStream(hostConnected.getInputStream());
						readyHost = oIS.readBoolean();
						this.updateReadyButton(readyHost);
						oIS.close();
						hostConnected.close();
					}
					
					if (readyClient && readyHost && inicio) {
						
						vista.getReadyClientLabel().removeMouseListener(vista.getReadyClientLabel().getMouseListeners()[0]);
						inicio = false;
						
						Socket socketEvento = new Socket(IP, HOST_PORT);
						
						DataOutputStream streamOS = new DataOutputStream(socketEvento.getOutputStream());
						streamOS.writeBoolean(this.isReadyClient());
						streamOS.close();
						
						
						pintarEnemigo = true;
						continue;
					}
					continue;
				}
				
				// Pinta los tiles del enemigo
				if (pintarEnemigo) {
					
					Socket socketEvento = new Socket(IP, HOST_PORT);
					
					ObjectOutputStream streamOS = new ObjectOutputStream(socketEvento.getOutputStream());
					streamOS.writeObject(this.getVista().getTableroPane().getHostCrownTiles());
					streamOS.writeObject(this.getVista().getTableroPane().getHostGroupTiles());
					streamOS.close();
					
					ObjectOutputStream streamIS = new ObjectOutputStream(socketEvento.getOutputStream());
					streamOS.writeObject(this.getVista().getTableroPane().getHostCrownTiles());
					streamOS.writeObject(this.getVista().getTableroPane().getHostGroupTiles());
					streamOS.close();
					
					pintarEnemigo = false;
				}
				
			}
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void updateReadyButton(boolean pReady) {
		if (!pReady) {
			this.getVista().getReadyHostLabel().setIcon(new ImageIcon(new ImageIcon(".\\static\\media\\images\\notready_button.png")
					.getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH)));
		} else {
			this.getVista().getReadyHostLabel().setIcon(new ImageIcon(new ImageIcon(".\\static\\media\\images\\ready_button.png")
					.getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH)));
		}
		
		this.notifyView();
	}
	 
	
}
