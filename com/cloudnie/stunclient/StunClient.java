package com.cloudnie.stunclient;

import java.io.IOException;  
import java.io.InterruptedIOException;  
import java.net.DatagramPacket;  
import java.net.DatagramSocket;  
import java.net.InetAddress; 

class StunClient {
	private String mSTUNServerName = "stun.ekiga.net";
	private short mSTUNServerPort = 3478;
	private int mTimeout = 500;

	public int sendBindingRequest() throws IOException {
		StunMessage request = StunMessage.getBindingRequest();
		byte raw[] = request.getRaw();

		InetAddress addr = InetAddress.getByName(mSTUNServerName);
		DatagramSocket socket = new DatagramSocket();
		socket.setSoTimeout(mTimeout);

		DatagramPacket sendPacket=new DatagramPacket(raw, raw.length,
								addr, mSTUNServerPort);

		socket.send(sendPacket);
		socket.close();

		return 0;
	}

	public StunMessage getResponse() throws IOException {

		InetAddress addr = InetAddress.getByName(mSTUNServerName);
		DatagramSocket socket = new DatagramSocket();
		socket.setSoTimeout(mTimeout);

		DatagramPacket receivePacket=new DatagramPacket(new byte[1024], 1024);
		socket.receive(receivePacket);
		byte raw[] = receivePacket.getData();
		StunMessage response = new StunMessage(raw);
		socket.close();

		return response;
	}

	public static void main(String[] args) {
		System.out.println("hello, world");
	}
};
