package inlamning1;

/**
 * @author D�sir�e J�nsson
 */
import java.net.*;
import java.util.*;

public class DatagramQueue {
	private Queue<DatagramPacket> queue = new LinkedList<DatagramPacket>();

	/**
	 * Metoden retunerar antalet element som datastrukturen h�ller.
	 * 
	 * @r storleken p� queue
	 */
	public int size() {
		return queue.size();

	}

	/**
	 * 
	 * @return om que �r tom
	 */
	public Boolean isEmpty() {
		return queue == null;
	}

	/**
	 * 
	 * @param packet
	 */
	public synchronized void enqueue(DatagramPacket packet) {
		queue.add(packet);
		notify();
	}

	/**
	 * 
	 * @return ta bort queue
	 */
	public synchronized DatagramPacket dequeue() {
		while (queue.isEmpty()) {
			try {
				wait();
			} catch (InterruptedException e) {
				return null;
			}
		}
		return queue.remove();
	}
}
