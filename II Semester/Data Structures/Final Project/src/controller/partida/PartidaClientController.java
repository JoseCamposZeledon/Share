package controller.partida;

import java.awt.Image;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.rmi.server.ServerRef;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import controller.partida.clientEventos.EventoReady;
import model.account.Account;
import model.json.MapParser;
import model.mapComponents.ObstaculoGrafico;
import model.threadsPool.ThreadManager;
import view.partida.VistaPartidaUser;

public class PartidaClientController implements Runnable, Serializable, IConstants{
	
	private static PartidaClientController instancia = null;
	
	private Account host, client;
	private VistaPartidaUser vista;
	private boolean readyClient;
	
	private PartidaHostController hostController;

	private PartidaClientController(Account pClient) throws IOException, UnknownHostException, Exception{
		
		client = pClient;
		
		try {
			// Se conecta a ese IP
			Socket socket = new Socket(IP, HOST_PORT);
			
			// Envia el usuario
			ObjectOutputStream oOS = new ObjectOutputStream(socket.getOutputStream());
			oOS.writeObject(this);
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
		
		vista.getReadyClientLabel().addMouseListener(new EventoReady(this));
		vista.getInfoClient().setText(client.getCorreo() + " | " + client.getCounterVictorias());
		
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
	
	public void loadMap(String pPath) {
		for(ObstaculoGrafico obstaculo: MapParser.getInstance().loadMap(pPath).getObstaculos()) {
			vista.getTableroPane().add(obstaculo.getGraphicObstaculo(), 1);
		}
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
			ServerSocket server = new ServerSocket(CLIENT_PORT);
			
			// Espera a que el host se pueda conectar
			while (true) {
				hostConnected = server.accept();	
				if (hostConnected.isConnected()) {
					break;
				}
			}
			
			ObjectInputStream oIS = new ObjectInputStream(hostConnected.getInputStream());
			
			hostController = (PartidaHostController) oIS.readObject();
			
			this.getVista().getInfoHost().setText(
					hostController.getHost().getCorreo() + " | " + 
					hostController.getHost().getCounterVictorias());
			this.loadMap(hostController.getMapPath());
			
			this.getVista().revalidate();
			this.getVista().repaint();
			oIS.close();
			
			// Actualiza el Boton Ready cada vez que se le hace click 

		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void updateReadyButton(boolean pReady) {
		if (pReady) {
			hostController.getVista().getReadyHostLabel().setIcon(new ImageIcon(new ImageIcon(".\\static\\media\\images\\notready_button.png")
					.getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH)));
			hostController.setReadyHost(false);
		} else {
			hostController.getVista().getReadyHostLabel().setIcon(new ImageIcon(new ImageIcon(".\\static\\media\\images\\ready_button.png")
					.getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH)));
			hostController.setReadyHost(true);
		}
		
		this.getVista().revalidate();
	}
	 
	
}
