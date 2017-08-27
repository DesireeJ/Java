package Mia2;

/** 
 * @author Désirée Jönssong
 */

import java.net.*;
import java.io.*;

/**
 * 
 * Klass ska fungera som ett spelprotokoll och kommer att styra kommunikationen
 * mellan klient och server klasserna.
 *
 */

public class GameProtocol {
	private final int SELECTED_ROCK = 0;
	private final int SELECTED_SCISSERS = 1;
	private final int SELECTED_BAG = 2;
	private final int AWAIT_OPONENT = 3;
	private final int CLIENT_WIN = 4;
	private final int SERVER_WIN = 5;
	private final int EQUAL = 6;
	private final int GAME_OVER = 7;

	private boolean serverMadeSelection = false;
	private int serverSelection = -1;
	private int serverScore = 0;
	private int clientScore = 0;

	public int processClientMessage(int message) {

		if (serverMadeSelection) {
			return comparePlayerSelection(message);
		} else
			return AWAIT_OPONENT;
	/*
		 * Denna metods returvärde kommer att skickas till klient-applikationen.
		 * Implementera och använd metoden comparePlayerSelection till att
		 * jämföra de två spelarnas val samt till att öka poängställningen.
		 */

	}

	public void setServerSelection(int selection) {
		this.serverMadeSelection = true;
		this.serverSelection = selection;
	}

	public void resetServerSelection() {
		this.serverMadeSelection = false;
		this.serverSelection = -1;
	}

	/**
	 * Metoden tar clientens val som argument. Jämför klient-spelarens val
	 * mot server-spelarens val. Öka värdet på följande variabler för att
	 * ändra poängställnigen.
	 * 
	 * Returnera värdet på någon av följande konstanter beroende på
	 * jämförelsernas utgång:
	 * 
	 * Om någon av spelarna fått 10 poäng, returneras GAME_OVER
	 */
	public int comparePlayerSelection(int clientSelection) {
		int toReturn = 0;

		//If-satser beroende på vilket speldrag klienten i jämförelse med server gör
		if (serverSelection == clientSelection) {
			toReturn = EQUAL;
		} else if (clientSelection == SELECTED_ROCK
				&& serverSelection == SELECTED_BAG) {
			serverScore++;
			toReturn = SERVER_WIN;
		} else if (clientSelection == SELECTED_ROCK
				&& serverSelection == SELECTED_SCISSERS) {
			clientScore++;
			toReturn = CLIENT_WIN;
		} else if (clientSelection == SELECTED_BAG
				&& serverSelection == SELECTED_ROCK) {
			clientScore++;
			toReturn = CLIENT_WIN;
		} else if (clientSelection == SELECTED_BAG
				&& serverSelection == SELECTED_SCISSERS) {
			serverScore++;
			toReturn = SERVER_WIN;
		} else if (clientSelection == SELECTED_SCISSERS
				&& serverSelection == SELECTED_BAG) {
			clientScore++;
			toReturn = CLIENT_WIN;
		} else if (clientSelection == SELECTED_SCISSERS
				&& serverSelection == SELECTED_ROCK) {
			serverScore++;
			toReturn = SERVER_WIN;
		}

		if (serverScore == 10 || clientScore == 10) {
			toReturn = GAME_OVER;
		}
		
		return toReturn;
	}

	public String getScore() {
		return "Client: " + clientScore + ", Server: " + serverScore;
	}

	public boolean getScoreIsNotZero() {
		return !((clientScore == 0) && (serverScore == 0));
	}
}
