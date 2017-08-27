package inlamning1;

/**
 * @author Désirée Jönsson
 */

import java.net.*;
import java.util.*;
import java.io.IOException;

/**
 * Innehåller inre klasserna RecieveBees och SendBees. Kör två trådar i samma
 * klass genom att koppla vars ett trådobjekt till de två inre klasserna.
 * Klassen SpiderInTheNet kan skicka och ta emot datagram samtidigt.
 */

public class SpiderInTheNet {

	// Instansvariabler
	private DatagramSocket socket;
	private DatagramQueue beeQueue;
	private Thread recieveTh;
	private Thread sendTh;
	private int port;
	private boolean ready;

	// Konstruktorn
	public SpiderInTheNet(int port) {
		this.port = port;
		beeQueue = new DatagramQueue();
		ready = false;
		try {
			socket = new DatagramSocket(this.port);
			recieveTh = new Thread(new RecieveBees());
			sendTh = new Thread(new SendBees());
			recieveTh.start();
			sendTh.start();
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	/**
	 * Klassen tar emot datagram och beräknar sannolikheten för att det ska få
	 * skickas tillbaka till avsändaren eller inte men hjälp av en
	 * slumpgenerator.
	 */
	private class RecieveBees implements Runnable {
		private Random randGen = new Random(System.currentTimeMillis());
		private byte[] buffer = new byte[30];
		DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

		public void recieve() throws IOException {
			int countBees = 0;
			for (int i = 1; i < 31; i++) {
				try {
					socket.receive(packet);
				} catch (IOException e) {

				}

				if (randGen.nextInt(10) < 3) {
					countBees++;
					System.out.println(new String(packet.getData())
							+ " fastnade i spindelns nät!");
				} else {
					int arrSize = packet.getData().length;
					byte[] beeInfo = Arrays.copyOf(packet.getData(), arrSize);
					DatagramPacket retPacket = new DatagramPacket(beeInfo,
							beeInfo.length, packet.getAddress(),
							packet.getPort());
					beeQueue.enqueue(retPacket);

				}
			}
			System.out.println("Spindeln har fångat " + countBees
					+ " bin i sitt nät");
		}

		public void run() {
			recieveTh.setPriority(Thread.MAX_PRIORITY);

			try {
				ready = true;
				recieve();
			} catch (IOException e) {
				System.out.println("Tyvärr fel sorry");
				e.printStackTrace();
			}

		}

	}

	public class SendBees implements Runnable {
		public void run() {
			sendTh.setPriority(Thread.MIN_PRIORITY);
			while ((!beeQueue.isEmpty()) || (!ready)) {
				try {
					socket.send(beeQueue.dequeue());
				} catch (IOException e) {
					System.out.println(e);
				}
			}
		}
	}

	public static void main(String[] args) {
		SpiderInTheNet ref = new SpiderInTheNet(4444);
	}
}
