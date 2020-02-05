package me.denyol.chatapp.logic;

import javafx.application.Platform;
import me.denyol.chatapp.controller.MainAppController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;

public class ServerThread extends Thread {

	private final MainAppController mainAppController;
	private final int port;
	private DatagramSocket socket;

	public ServerThread(MainAppController mainAppController, int port) throws SocketException {
		super("ServerThread");
		this.mainAppController = mainAppController;
		this.port = port;

		socket = new DatagramSocket(port);
	}

	@Override
	public void run() {
		super.run();

		try {
			byte[] buf = new byte[256];

			InetAddress host = InetAddress.getByName("255.255.255.255");

			for (int i = 0; i < 10; i++) {
				buf = ("Bean" + i).getBytes();
				DatagramPacket packet = new DatagramPacket(buf, buf.length, host, port + 1);
				socket.send(packet);
			}

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
