package inlamning1;

/** 
 * @author Désirée Jönsson
 */

import java.io.*;
import java.net.*;

/**
 * Klassen innehåller två inre klasser med namnen SendBees och RecieveBees för
 * att kunna köra trådar i samma klass kan. Applikationen kan sända och ta emot
 * datagram samtidigt. Den ena inre klassen skapar och skicka datagramen och den
 * andra tar emot datagramen.
 */
public class BeeNest {

	private static String ipAdress = "192.168.1.88";
	private static int port = 4444;
	DatagramSocket socket;

	// Konstruktorn
	public BeeNest(String ipAdress, int port) throws SocketException {
		socket = new DatagramSocket();

		new Thread(new SendBees()).start();
		new Thread(new RecieveBees()).start();
	}

	public class SendBees implements Runnable {

		/**
		 * Metoden skapar och skickar ut 30 DatagramPacket objekt över
		 * socket-objektet som finns tillgängliga i klassen.
		 */
		void send() throws IOException {
			for (int i = 1; i < 31; i++) {
				String temp = "Bi nummer " + (i);
				byte[] beeMess = temp.getBytes();
				int messLength = beeMess.length;
				DatagramPacket packet = new DatagramPacket(beeMess, messLength,
						InetAddress.getByName(ipAdress), port);
				socket.send(packet);
			}
		}

		public void run() {
			try {
				send();
			} catch (IOException e) {
				System.out.println("Fail");
			}
		}
	}

	public class RecieveBees implements Runnable {
		
		/**
		 * Metoden exekveras av den tråd som vi skapat i BeeNest klassens konstruktor. Evighetesloop
		 *  användas till att lyssna efter inkommande datagram.
		 */
		public void run() {
			byte[] buffer = new byte[30];
			while (true) {

				DatagramPacket recPacket = new DatagramPacket(buffer,
						buffer.length);

				try {
					socket.receive(recPacket);
				} catch (IOException e) {

				}
				System.out.println(new String(recPacket.getData())
						+ "Klarade sig hem!");
			}
		}
	}

	public static void main(String[] args) throws SocketException {
		BeeNest bee = new BeeNest(ipAdress, port);
	}
}
