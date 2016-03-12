package gui;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Del av gruppuppgift DA343A
 * @author Gustav Frigren
 *
 */
public class Connection extends Thread {

	private Controller controller;
	private int port;
	
	private ThreadPoolExecutor threadPool = (ThreadPoolExecutor) Executors.newCachedThreadPool();
	private ServerSocket serverSocket;
	private Socket socket;

	public Connection(Controller controller, int port) {
		this.controller = controller;
		this.port = port;
	}

	public void run() {
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		while(serverSocket != null) {
			try {
				socket = serverSocket.accept();
				threadPool.execute(new User(controller, socket)); // här skapas ett nytt User-objekt
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}