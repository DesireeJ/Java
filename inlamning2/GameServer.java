package Mia2;

/** 
 * @author Désirée Jönsson
 */

import javax.swing.*;


import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;

public class GameServer extends JFrame implements ActionListener, Runnable {
	ServerSocket serverSocket = null;
	Socket clientSocket = null;
	PrintWriter out = null;
	BufferedReader in = null;
	JButton rockBtn = new JButton("Sten");
	JButton sciBtn = new JButton("Sax");
	JButton bagBtn = new JButton("PÃ¥se");
	JLabel messLbl = new JLabel();
	JPanel buttonPanel = new JPanel();
	GameProtocol gp = new GameProtocol();
	int clientMess = -1, response = -1;

	final int SELECTED_ROCK = 0;
	final int SELECTED_SCISSERS = 1;
	final int SELECTED_BAG = 2;
	final int AWAIT_OPONENT = 3;
	final int CLIENT_WIN = 4;
	final int SERVER_WIN = 5;
	final int EQUAL = 6;
	final int GAME_OVER = 7;

	// Här skapas ett SocketServer samt ett Thread objekt och trådobjektet ska exekvera klassens run metod.
	public GameServer(int port) throws IOException {
		try {
			serverSocket = new ServerSocket(port);

		} catch (IOException e) {
			System.out.println("e");
		}
		setupWidgets();
		handleClient();
		new Thread(this).start(); 
		
	}

	//Komponeterna i GUI
	private void setupWidgets() {
		this.setTitle("Game server");
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

	private void handleClient() throws IOException{
		clientSocket = serverSocket.accept();
		out = new PrintWriter(clientSocket.getOutputStream(), true);
		in = new BufferedReader(new InputStreamReader(
				clientSocket.getInputStream()));
		
		/*
		 * Använd ServerSocket objektets metod accept till att vänta på att
		 * klient-applikationen ska ansluta sig. Metoden accept returnerar ett
		 * socket-objekt.
		 */
	}

	public void run() {
		String fromClient;

		try {
			//Läser från klienten så länge svaret inte är null.
			while ((fromClient = in.readLine()) != null) {
				clientMess = Integer.parseInt(fromClient);
				//Behandla meddelandet från klienten och generera svar
				response = gp.processClientMessage(clientMess);
				//Skicka svar till clienten
				out.println(response);

				//Om poängställningen ändras, uppdatera label
				if ((response == CLIENT_WIN) || (response == SERVER_WIN)) {
					messLbl.setText(gp.getScore());
					//Om svaret till klienten är GAME_OVER, avsluta
				} else if (response == GAME_OVER) {
					messLbl.setText(gp.getScore() + " --GAME OVER--");
					break;
					/*
					 * Om klienten ska vänta på serverns val behövas denna
					 * information vid knapptryckningar. Återställ variabeln
					 * endast om klienten inte kommer att vänta
					 */
				} else if (response != AWAIT_OPONENT) {
					response = -1;
				}

				gp.resetServerSelection();
				enableButtons();
			}

			in.close();
			out.close();
			clientSocket.close();
			serverSocket.close();

		} catch (IOException e) {
			System.out.println(e);
		}
	}

	//Hanterar händelserna av knapparna.
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == rockBtn) {
			gp.setServerSelection(SELECTED_ROCK);
			handleButtonEvent(SELECTED_ROCK);
		} else if (e.getSource() == sciBtn) {
			gp.setServerSelection(SELECTED_SCISSERS);
			handleButtonEvent(SELECTED_SCISSERS);
		} else if (e.getSource() == bagBtn) {
			gp.setServerSelection(SELECTED_BAG);
			handleButtonEvent(SELECTED_BAG);
		}
	}

	/*
	 * Används till att skicka meddelanden till klienten vid knapptryckningar.
	 * Hanterar implementations detaljer vid knapptryckningar.
	 */
	public void handleButtonEvent(int selection) {
		if (response == AWAIT_OPONENT) {
			out.println(selection);
			response = gp.processClientMessage(clientMess);
			out.println(response);
			if (gp.getScoreIsNotZero())
				messLbl.setText(gp.getScore());
			gp.resetServerSelection();
			clientMess = -1;
			response = -1;
		} else if (response == -1) {
			disableButtons();
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

	public static void main(String args[]) throws NumberFormatException,
			IOException {
		new GameServer(4444);
	}
}
