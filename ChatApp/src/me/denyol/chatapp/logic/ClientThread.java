package me.denyol.chatapp.logic;

import me.denyol.chatapp.controller.MainAppController;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;

public class ClientThread extends Thread {

	private final MainAppController mainAppController;
	private final int port;
	private DatagramSocket socket;

	public ClientThread(MainAppController mainAppController, int port) {
		super("ClientThread");
		this.mainAppController = mainAppController;
		this.port = port;
	}

	@Override
	public void run() {
		super.run();

		try {
			socket = new DatagramSocket(port + 1);
		} catch (Exception e) {
			e.printStackTrace();
		}

		DatagramPacket packet = new DatagramPacket(new byte[256], 250);

		while (true) {
			try {
				socket.receive(packet);

				System.out.println("Recieved: " + packet.getAddress());

				byte[] fromServer = packet.getData();

				System.out.println(new String(fromServer, StandardCharsets.UTF_8));

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
