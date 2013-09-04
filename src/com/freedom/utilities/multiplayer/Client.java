package com.freedom.utilities.multiplayer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Logger;

import com.freedom.gameObjects.base.Stuff;
import com.freedom.model.GameField;

public class Client {

	private static Logger logger = Logger.getLogger("PathFinder");

	private volatile boolean alive = true;
	private int port;
	private String ip;
	private InetAddress addr;
	private DataInputStream in;
	private DataOutputStream out;
	private Stuff stuf;
	private Socket sock;

	/**
	 * 
	 * @param adr
	 *            Ip addr like "127.0.0.1"
	 * @param prt
	 *            port like 6666
	 */
	public Client(String adr, int prt) {
		this.ip = adr;// InetAddress.getByName(adr);
		this.port = prt;
	}

	/**
	 * 
	 * @param prt
	 *            port
	 */
	public Client(int prt) {
		try {
			this.ip = InetAddress.getLocalHost().getHostAddress();
			logger.info("this ip=" + this.ip);
		} catch (UnknownHostException e) {
			// TODO Автоматически созданный блок catch
			e.printStackTrace();
		}
		this.port = prt;
	}

	public void connect(){
		try {
			this.addr = InetAddress.getByName(this.ip);
			this.sock = new Socket(this.addr, this.port);
			InputStream s11 = sock.getInputStream();
			OutputStream s12 = sock.getOutputStream();
			this.in = new DataInputStream(s11);
			this.out = new DataOutputStream(s12);
			GameField.otherThreads.execute(new Listener());
		} catch (Exception e) {
			// TODO Автоматически созданный блок catch
			e.printStackTrace();
		}
	}

	public void setStuff(Stuff s) {
		this.stuf = s;
	}

	private class Listener implements Runnable {

		@Override
		public void run() {
			Thread.currentThread().setName("client listener");
			String line;
			while (alive) {
				try {
					line = in.readUTF();

					if (line.equals("s")) {
						stuf.teleport(Integer.parseInt(in.readUTF()),
								Integer.parseInt(in.readUTF()));
					}
				} catch (IOException e) {
					// TODO Автоматически созданный блок catch
					e.printStackTrace();
				}
			}
		}
	}
}
