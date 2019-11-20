package controller.partida;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import controller.partida.hostEventos.EventoReady;
import model.account.Account;
import model.json.MapParser;
import model.mapComponents.ObstaculoGrafico;
import view.partida.VistaPartidaHost;

public class PartidaHostController implements Runnable {
	
	private Account host, user;
	private VistaPartidaHost vista;
	
	private ServerSocket server;
	
	private boolean readyHost;
	
	
	public PartidaHostController(String pPath, Account pHost) {
		Thread hilo = new Thread(this);
		
		host = pHost;
		
		vista = new VistaPartidaHost();
		
		for(ObstaculoGrafico obstaculo: MapParser.getInstance().loadMap(pPath).getObstaculos()) {
			vista.getTableroPane().add(obstaculo.getGraphicObstaculo(), 1);
		}
		
		// Evento READY
		vista.getReadyHostLabel().addMouseListener(new EventoReady(this));
		
		vista.getInfoHost().setText(host.getCorreo() + " | " + host.getCounterVictorias());
		vista.getInfoClient().setText("USUARIO DESCONECTADO");
		
		hilo.run();
	}
	
	@Override
	public void run() {
		
		try {
			// Abre el puerto	
			server = new ServerSocket(9999);
			// Acepta las conexiones que vienen
			while (true) {
				Socket conectado = server.accept();
				if (conectado.isConnected()) {
					System.out.println("CONECTADO");
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ServerSocket getServer() {
		return server;
	}

	public void setServer(ServerSocket server) {
		this.server = server;
	}

	public Account getHost() {
		return host;
	}

	public void setHost(Account host) {
		this.host = host;
	}

	public Account getUser() {
		return user;
	}

	public void setUser(Account user) {
		this.user = user;
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

}	
