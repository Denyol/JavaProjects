package me.denyol.chatapp.logic;

import me.denyol.chatapp.Main;
import me.denyol.chatapp.controller.MainAppController;

import java.io.IOException;
import java.net.*;
import java.util.AbstractQueue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ServerThread extends Thread {

	private final MainAppController mainAppController;
	private DatagramSocket socket;
	private final Main main;

	private volatile boolean isRunning = true;

	private AbstractQueue<String> messageOutQueue;

	public ServerThread(MainAppController mainAppController, Main main) throws SocketException {
		super("ServerThread");
		this.mainAppController = mainAppController;
		this.main = main;

		socket = new DatagramSocket(null);
		socket.setReuseAddress(true);
		socket.bind(new InetSocketAddress(main.getServerPort()));

		socket.setBroadcast(true);

		messageOutQueue = new ConcurrentLinkedQueue<>();
	}

	public AbstractQueue<String> getMessageOutQueue() {
		return messageOutQueue;
	}

	public void stopRunning() {
		this.isRunning = false;
	}

	@Override
	public void run() {
		super.run();

		while (isRunning) {
			try {
				InetAddress host = InetAddress.getByName("255.255.255.255");

				Thread.sleep(500);

				String toSend = this.messageOutQueue.poll();

				if (toSend != null && !toSend.isEmpty()) {
					MessageData metaData = new MessageData();

					metaData.setMessageData(toSend);

					byte[] buf = metaData.produceByteArray();

					DatagramPacket packet = new DatagramPacket(buf, buf.length, host, this.main.getClientPort());
					socket.send(packet);
				}
			} catch(IOException | InterruptedException e){
				e.printStackTrace();
				socket.disconnect();
				socket.close();
			}

		}

		socket.disconnect();
		socket.close();

	}
}
