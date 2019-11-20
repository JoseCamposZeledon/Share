package controller.partida;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import controller.partida.hostEventos.EventoReady;
import model.account.Account;
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
	
	
	private PartidaHostController(String pMapPath, Account pHost) {
		
		host = pHost;
		mapPath = pMapPath;
		
		vista = new VistaPartidaHost();
		
		loadMap();
		
		// Evento READY
		vista.getReadyHostLabel().addMouseListener(new EventoReady(this));
		
		vista.getInfoHost().setText(host.getCorreo() + " | " + host.getCounterVictorias());
		vista.getInfoClient().setText("USUARIO DESCONECTADO");
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


	@Override
	public synchronized void run() {
		try {
			
			// Crea un servidor en el puerto 9999
			ServerSocket server = new ServerSocket(HOST_PORT);
			Socket clientConnected, connect;
			while (true) {
				clientConnected = server.accept();
				// Conecta al host de la partida con el servidor del cliente
				if (clientConnected.isConnected()) {
					connect = new Socket(IP, CLIENT_PORT);
					break;
				}
			}
			
			ObjectOutputStream oOS = new ObjectOutputStream(connect.getOutputStream());
			oOS.writeObject(this);
			oOS.close();
			
			ObjectInputStream oIS = new ObjectInputStream(clientConnected.getInputStream());
			PartidaClientController controllerClient = (PartidaClientController) oIS.readObject();
			
			this.getVista().getInfoClient().setText(
					controllerClient.getClient().getCorreo() + " | " + 
					controllerClient.getClient().getCounterVictorias());
			
			this.getVista().revalidate();
			this.getVista().repaint();
			
			oIS.close();
			
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


	public String getMapPath() {
		return mapPath;
	}


	public void setMapPath(String mapPath) {
		this.mapPath = mapPath;
	}

}	
