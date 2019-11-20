package model.threadsPool;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import controller.partida.PartidaClientController;
import controller.partida.PartidaHostController;

public class ThreadManager extends Thread {
	
	private static ThreadManager instancia = null;
	
	private ExecutorService pool = Executors.newFixedThreadPool(5);
	
	private ThreadManager() {
		
	}
	
	public static ThreadManager getInstance() {
		if (instancia == null) {
			instancia = new ThreadManager();
		}
		return instancia;
	}
	
	public void startThread(Runnable pStart) {
		pool.execute(pStart);
	}

	public ExecutorService getPool() {
		return pool;
	}

	public void setPool(ExecutorService pool) {
		this.pool = pool;
	}

}
