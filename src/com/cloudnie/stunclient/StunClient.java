package com.cloudnie.stunclient;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Arrays;

public class StunClient {
	private String mSTUNServerName = "stun.ekiga.net";
	private short mSTUNServerPort = 3478;
	private int mTimeout = 5000;
	DatagramSocket mSocketPrimary;
	DatagramSocket mSocketSecondary;

	public int bindLocalPort() {

		for (int port = 40000; port < 65530; port++) {
			try {
				mSocketPrimary = new DatagramSocket(port);
				mSocketPrimary.setSoTimeout(mTimeout);
				break;
			} catch (SocketException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {

			}
		}

		for (int port = 30480; port < 65530; port++) {
			try {
				mSocketSecondary = new DatagramSocket(port);
				mSocketSecondary.setSoTimeout(mTimeout);
				break;
			} catch (SocketException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {

			}
		}

		return 0;
	}

	public int sendBindingRequest() throws IOException {
		StunMessage request = StunMessage.getBindingRequest();
		byte raw[] = request.getRaw();

		InetAddress addr = InetAddress.getByName(mSTUNServerName);

		DatagramPacket sendPacket = new DatagramPacket(raw, raw.length, addr,
				mSTUNServerPort);
		mSocketPrimary.send(sendPacket);

		return 0;
	}

	public StunMessage getResponse() throws IOException {

		byte __recv[] = new byte[1024];
		Arrays.fill(__recv, (byte) 0);
		DatagramPacket receivePacket = new DatagramPacket(__recv, 1024);
		try {
			mSocketPrimary.receive(receivePacket);
			byte raw[] = receivePacket.getData();
			StunMessage response = new StunMessage(raw);
			return response;
		} catch (SocketTimeoutException exception) {
			System.out.println(exception);
		} catch (Exception exception) {

		} finally {

		}

		return null;
	}

	//Determining NAT Mapping Behavior
	public int determinNATMappingBehavior() {
		return 0;
	}
	
	//Determining NAT Filtering Behavior
	public int determinNATFilteringBehavior() {
		return 0;
	}
	
	
	public int testUDPConnectivity() {
		try {
			bindLocalPort();
			sendBindingRequest();
			StunMessage response = getResponse();
			if (response != null) {
				byte raw[] = response.getRaw();
				System.out.println(Arrays.toString(raw));
				return 0;
			}
		
		} catch (Exception exception) {
			System.out.println(exception);
		}

		return -1;
	}

	public static void main(String[] args) {
		StunClient client = new StunClient();
		client.testUDPConnectivity();
	}
};
