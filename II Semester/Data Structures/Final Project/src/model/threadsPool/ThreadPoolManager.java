package model.threadsPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolManager {
	
	ExecutorService pool = Executors.newFixedThreadPool(5);
	
	public ThreadPoolManager() {
		
	}
	
}
