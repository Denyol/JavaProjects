package me.denyol.chatapp.logic;

import me.denyol.chatapp.Main;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class MessageData {

	private byte[] metaData = new byte[Main.METADATA_BYTE_LENGTH];
	private String messageData;

	public MessageData() {}

	public MessageData(byte[] byteArrayMessage, int length) {
		this.setMetaData(byteArrayMessage);
		this.setMessageData(byteArrayMessage, length);
	}

	private void setMetaData(byte[] byteArrayMessage) {
		for (int i = 0; i < metaData.length; i++) {
			this.metaData[i] = byteArrayMessage[i];
		}
	}

	private void setMessageData(byte[] byteArrayMessage, int length) {
		byte[] messageArray = Arrays.copyOfRange(byteArrayMessage, Main.METADATA_BYTE_LENGTH, length);

		this.messageData = new String(messageArray, StandardCharsets.UTF_8);
	}

	public String getMessage() {
		return this.messageData;
	}

	public void setMessageData(String messageData) {
		this.messageData = messageData;
	}

	public void addMetaData(byte data, int index) {
		metaData[index] = data;
	}

	public byte[] produceByteArray() {
		if (messageData.isEmpty())
			return this.metaData;

		byte[] messageAsByteArray = messageData.getBytes(StandardCharsets.UTF_8);

		byte[] result = new byte[messageAsByteArray.length + Main.METADATA_BYTE_LENGTH];

		for (int i = 0; i < result.length; i++) {
			if (i < Main.METADATA_BYTE_LENGTH) {
				result[i] = metaData[i];
			} else if (i - Main.METADATA_BYTE_LENGTH < messageAsByteArray.length) {
				result[i] = messageAsByteArray[i - Main.METADATA_BYTE_LENGTH];
			}
		}

		return result;
	}

}
