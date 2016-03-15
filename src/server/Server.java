package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * This is the server class
 * Del av gruppuppgift DA343A
 * @author Gustav Frigren, Karl-Ebbe J�nsson
 *
 */
public class Server extends Thread {

	private Controller controller;
	private int port;
	
	private ThreadPoolExecutor threadPool = (ThreadPoolExecutor) Executors.newCachedThreadPool();
	private ServerSocket serverSocket;
	private Socket socket;
	private Thread thread;

	public Server(Controller controller, int port) {
		this.controller = controller;
		this.port = port;
	}

	public void stopServer(){
		this.interrupt();
	}
	
	public void run() {
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		while(!Thread.interrupted()) {
			try {
				socket = serverSocket.accept();
				threadPool.execute(new User(controller, socket));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}