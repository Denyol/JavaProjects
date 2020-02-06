package me.denyol.chatapp.logic;

import javafx.application.Platform;
import me.denyol.chatapp.Main;
import me.denyol.chatapp.controller.MainAppController;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;

public class ClientThread extends Thread {

	private final MainAppController mainAppController;
	private DatagramSocket socket;
	private final Main main;

	private volatile boolean isRunning = true;

	public ClientThread(MainAppController mainAppController, Main main) throws SocketException {
		super("ClientThread");
		this.mainAppController = mainAppController;
		this.main = main;

		socket = new DatagramSocket(null);
		socket.setReuseAddress(true);
		socket.bind(new InetSocketAddress(main.getClientPort()));
	}

	public void stopRunning() {
		this.isRunning = false;
	}

	@Override
	public void run() {
		super.run();

		while (isRunning) {
			try {
				DatagramPacket packet = new DatagramPacket(new byte[1024], 1024);

				socket.receive(packet);

				System.out.println("Recieved from " + packet.getAddress() + ":" + packet.getPort());

				new Thread() {
					@Override
					public void run() {
						super.run();
						MessageData messageData = new MessageData(packet.getData(), packet.getLength());

						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								mainAppController.addText(messageData.getMessage());
							}
						});
					}
				}.start();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		socket.disconnect();
		socket.close();
	}
}
