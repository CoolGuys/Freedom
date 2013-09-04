package com.freedom.utilities.multiplayer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.freedom.model.GameField;

public class Server {
	private int port;
	private DataInputStream in;
	private DataOutputStream out;
	private ServerSocket ss;
	
	public Server(int prt){
		this.port=prt;
	}
	
	public void start(){
		try {
			ss = new ServerSocket(port);
			Socket socket = ss.accept();
			InputStream sin = socket.getInputStream();
			OutputStream sout = socket.getOutputStream();
			this.in = new DataInputStream(sin);
			this.out = new DataOutputStream(sout);
			/*while (true) {
				out.writeUTF(line);
				out.flush();
			}*/
			GameField.otherThreads.execute(new Executor());
		} catch (Exception x) {
			x.printStackTrace();
		}
	}

	public void setyx(int x, int y) {
		try {
			out.writeUTF("s");
			out.flush();
			out.writeUTF(String.valueOf(x));
			out.flush();
			out.writeUTF(String.valueOf(y));
		} catch (IOException e) {
			// TODO Автоматически созданный блок catch
			e.printStackTrace();
		}
	}	
	
	
	private class Executor implements Runnable {

		@Override
		public void run() {
			Thread.currentThread().setName("Executor");
			try {
				ss.accept();
			} catch (IOException e) {
				// TODO Автоматически созданный блок catch
				e.printStackTrace();
			}
		}
	}
	
}
