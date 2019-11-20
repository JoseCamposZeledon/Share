package controller.partida;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

import controller.partida.clientEventos.EventoReady;
import model.account.Account;
import view.partida.VistaPartidaUser;

public class PartidaClientController {
	
	private Account host, client;
	private VistaPartidaUser vista;
	private boolean readyClient;

	public PartidaClientController(Account pClient) throws IOException, UnknownHostException{
		
		try {
			Socket socket = new Socket("192.168.100.5", 9999);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			throw new UnknownHostException();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new IOException();
		}
		
		client = pClient;
		vista = new VistaPartidaUser();
		
		readyClient = false;
		
		vista.getReadyClientLabel().addMouseListener(new EventoReady(this));
		vista.getInfoClient().setText(client.getCorreo() + " | " + client.getCounterVictorias());
	}
	
	
	public Account getHost() {
		return host;
	}


	public void setHost(Account host) {
		this.host = host;
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
	
	
	
}
