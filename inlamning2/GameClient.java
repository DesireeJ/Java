package Mia2;

/** 
 * @author Désirée
 */

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;


public class GameClient extends JFrame implements ActionListener, Runnable {
	Socket gSocket = null;
	PrintWriter out = null;
	BufferedReader in = null;
	JButton rockBtn = new JButton("Sten");
	JButton sciBtn = new JButton("Sax");
	JButton bagBtn = new JButton("Påse");
	JLabel messLbl = new JLabel();
	JPanel buttonPanel = new JPanel();
	boolean playerSatus = false;
	int clientScore = 0, serverScore = 0;
	int clientSelection = -1;

	final int SELECTED_ROCK = 0;
	final int SELECTED_SCISSERS = 1;
	final int SELECTED_BAG = 2;
	final int AWAIT_OPONENT = 3;
	final int CLIENT_WIN = 4;
	final int SERVER_WIN = 5;
	final int EQUAL = 6;
	final int GAME_OVER = 7;

	public GameClient(String ipAddress, int port) {
		try {
			gSocket = new Socket(ipAddress, port);
			out = new PrintWriter(gSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(
					gSocket.getInputStream()));

		} catch (IOException e) {
			System.out.println("e");
		}
		/*
		 * Skapa ett socket-objekt med hjälp av konstruktorns argument. Skapa
		 * ett PrintWriter objekt och koppla socket-objektets utmatningsström
		 * till PrintWriter-objektet. Skapa ett Bufferedreader objekt och koppla
		 * socket-objektets inmatningsström till BufferedSrea-objektet. Även ett
		 * Thred-objekt ska skapas som ska exekvera klassens run-metod. Starta
		 * exekveringen. Hantera eventuella felmeddelanden.
		 */
		new Thread(this).start();
		setupWidgets();
	}

	/**
	 * Skapar alla grafiska komponenter
	 */
	private void setupWidgets() {
		this.setTitle("Game client");
		this.setBounds(50, 50, 300, 250);
		this.setLayout(new BorderLayout());

		buttonPanel.setLayout(new GridLayout(3, 1));
		rockBtn.setPreferredSize(new Dimension(200, 40));
		sciBtn.setPreferredSize(new Dimension(200, 40));
		bagBtn.setPreferredSize(new Dimension(200, 40));
		messLbl.setPreferredSize(new Dimension(buttonPanel.getSize().width, 40));

		JPanel p1 = new JPanel();
		p1.add(rockBtn);
		buttonPanel.add(p1);
		JPanel p2 = new JPanel();
		p2.add(sciBtn);
		buttonPanel.add(p2);
		JPanel p3 = new JPanel();
		p3.add(bagBtn);
		buttonPanel.add(p3);

		rockBtn.addActionListener(this);
		sciBtn.addActionListener(this);
		bagBtn.addActionListener(this);

		this.add(buttonPanel, BorderLayout.CENTER);
		this.add(messLbl, BorderLayout.SOUTH);
		this.setVisible(true);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void run() {
		int message = 0;
		String fromServer;

		try {
			// Läser meddelanden från serverapplikaitonen
			while ((fromServer = in.readLine()) != null) {
				message = Integer.parseInt(fromServer);
				
				// Läser av meddelandet från servern och utför en lämplig operation.
				if (message == AWAIT_OPONENT) {
					disableButtons();
				} else if (message == CLIENT_WIN) {
					clientScore++;
					messLbl.setText(getScore());
				} else if (message == SERVER_WIN) {
					serverScore++;
					messLbl.setText(getScore());
				} else if (message == SELECTED_ROCK) {
					enableButtons();
				} else if (message == SELECTED_SCISSERS) {
					enableButtons();
				} else if (message == SELECTED_BAG) {
					enableButtons();
				} else if (message == GAME_OVER) {
					messLbl.setText(getScore() + " --GAME OVER--");
					out.println(GAME_OVER);
					disableButtons();
					break;
				}
			}

			gSocket.close();
			out.close();
			in.close();
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	/*
	 * Hanterar knapp-händelser. Vid knapptryckning skickas ett meddelande till
	 * servern.
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == rockBtn) {
			out.println(SELECTED_ROCK);
		} else if (e.getSource() == sciBtn) {
			out.println(SELECTED_SCISSERS);
		} else if (e.getSource() == bagBtn) {
			out.println(SELECTED_BAG);
		}
	}

	public void disableButtons() {
		rockBtn.setEnabled(false);
		sciBtn.setEnabled(false);
		bagBtn.setEnabled(false);
	}

	public void enableButtons() {
		rockBtn.setEnabled(true);
		sciBtn.setEnabled(true);
		bagBtn.setEnabled(true);
	}

	public String getScore() {
		return "Client: " + clientScore + ", Server: " + serverScore;
	}

	public static void main(String args[]) throws SocketException {
		new GameClient("localhost", 4444);
	}
}
