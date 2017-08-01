package imdb;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.Random;

import javax.swing.*;

public class GUI extends JPanel implements ActionListener {

	private ArrayList<Movies> list = MovieList
			.readMovies("src/imdb/Movies.txt");

	private JLabel label = new JLabel("Film information");
	private JLabel sortAlternativeLabel = new JLabel("Ändra/ta bort info:");

	private JPanel panelButtons = new JPanel(new GridLayout(7, 1));
	private JPanel panelRadioButtons = new JPanel(new GridLayout(5, 1));
	private JPanel panelSouthGrid = new JPanel(new GridLayout(1, 3));
	private JPanel panelCenterMovie = new JPanel(new GridLayout(8, 1));

	private Font fontLabels = new Font("SansSerif", Font.PLAIN, 18);

	// I East
	private JButton btnAvtagande = new JButton("Sortera avtagande bubbligt");
	private JButton btnVaxande = new JButton("Sortera växande bubbligt");
	private JButton btnAvtagandeQuick = new JButton("Sortera avtagande Q");
	private JButton btnVaxandeQuick = new JButton("Sortera växande Q");
	private JButton btnPrintList = new JButton("Skriv ut lista");
	private JButton btnSlumpa = new JButton("Slumpa ordningen");
	private JButton btnWrite = new JButton("Skriv lite");

	// I West
	private JTextField typeMovieToChange = new JTextField(
			"Index där ändring ska ske");
	private JRadioButton changeMovie = new JRadioButton("Ändra Film");
	private JRadioButton removeMovie = new JRadioButton("Ta bort film");
	private JButton btnChangeMovie = new JButton("Genomför ändringen");

	// Komponenter i South border
	private JTextField typeMovie = new JTextField("Skriv in film här...");
	private JRadioButton btnLinear = new JRadioButton("Linjär");
	private JRadioButton btnBinary = new JRadioButton("Binär");
	private JButton btnCheckforMovie = new JButton("Sök specifik film i listan");

	// Komponenter i Center
	private JLabel labelNewMovie = new JLabel("Lägg till film i listan");
	private JTextField typeNewTitle = new JTextField("Skriv in titel här...");
	private JTextField typeNewType = new JTextField("Skriv in typ här...");
	private JTextField typeNewGenre = new JTextField("Skriv in genre här...");
	private JTextField typeNewActors = new JTextField("Skriv in Actors här...");
	private JTextField typeNewLength = new JTextField("0");
	private JTextField typeNewGrade = new JTextField("0");
	private JButton btnAddNewMovie = new JButton("Spara film i lista");

	public GUI() {

		ButtonGroup groupRadio = new ButtonGroup();
		ButtonGroup groupWest = new ButtonGroup();

		groupWest.add(changeMovie);
		groupWest.add(removeMovie);

		groupRadio.add(btnLinear);
		groupRadio.add(btnBinary);

		setPreferredSize(new Dimension(800, 400));
		setLayout(new BorderLayout(10, 10));

		label.setFont(fontLabels);

		// LAYOUTEN
		add(label, BorderLayout.NORTH);
		add(panelButtons, BorderLayout.EAST);
		add(panelCenterMovie, BorderLayout.CENTER);
		add(panelSouthGrid, BorderLayout.SOUTH);

		add(panelRadioButtons, BorderLayout.WEST);
		panelRadioButtons.setPreferredSize(new Dimension(150, 100));

		// Knappar i East delen
		panelButtons.add(btnVaxande);
		panelButtons.add(btnAvtagande);
		panelButtons.add(btnSlumpa);
		panelButtons.add(btnPrintList);
		panelButtons.add(btnAvtagandeQuick);
		panelButtons.add(btnVaxandeQuick);
		panelButtons.add(btnPrintList);
		panelButtons.add(btnWrite);

		// Komponenter i Soutdelen
		panelSouthGrid.add(btnLinear);
		panelSouthGrid.add(btnBinary);
		panelSouthGrid.add(typeMovie);
		panelSouthGrid.add(btnCheckforMovie);

		// Komponenter i West
		panelRadioButtons.add(sortAlternativeLabel);
		panelRadioButtons.add(typeMovieToChange);
		panelRadioButtons.add(changeMovie);
		panelRadioButtons.add(removeMovie);
		panelRadioButtons.add(btnChangeMovie);

		// Komponenter i Center
		panelCenterMovie.add(labelNewMovie);
		panelCenterMovie.add(typeNewTitle);
		panelCenterMovie.add(typeNewType);
		panelCenterMovie.add(typeNewGenre);
		panelCenterMovie.add(typeNewActors);
		panelCenterMovie.add(typeNewLength);
		panelCenterMovie.add(typeNewGrade);
		panelCenterMovie.add(btnAddNewMovie);

		btnVaxande.addActionListener(this);
		btnAvtagande.addActionListener(this);
		btnSlumpa.addActionListener(this);
		btnPrintList.addActionListener(this);
		btnAvtagandeQuick.addActionListener(this);
		btnVaxandeQuick.addActionListener(this);
		btnCheckforMovie.addActionListener(this);
		typeMovie.addActionListener(this);
		btnChangeMovie.addActionListener(this);
		btnAddNewMovie.addActionListener(this);
		btnWrite.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		SortArray sort = new SortArray();
		FindMovie find = new FindMovie();

		// Sorterar filmerna Vaxande genom bubbelsort
		if (e.getSource() == btnVaxande) {
			for (int i = 0; i < list.size(); i++) {
				SortArray.bubbleSort(list, new Alfa());
				for (int in = 0; in < list.size(); in++) {
				}
				System.out.println(list.get(i).toString());
			}
			System.out.println();
		}

		// Sorterar filmerna avtagande genom bubbelsort
		if (e.getSource() == btnAvtagande) {

			for (int i = 0; i < list.size(); i++) {
				SortArray.bubbleSort(list, new Betet());
				for (int in = 0; in < list.size(); in++) {
				}
				System.out.println(list.get(i).toString());
			}
			System.out.println();
		}

		// Slumpar random ordning
		if (e.getSource() == btnSlumpa) {

			SortArray.shufflet(list);
			for (int i = 0; i < list.size(); i++) {
				System.out.println(list.get(i).toString());

			}

		}

		// Skriver ut listan med filmer
		if (e.getSource() == btnPrintList) {
			System.out.println("--------------------------------");
			System.out.println(list.toString());
			System.out.println("--------------------------------");
			for (int i = 0; i < list.size(); i++) {
				System.out.println(list.get(i).toString());
			}

		}

		// Inte gjort Quick ännu
		if (e.getSource() == btnAvtagandeQuick) {

			for (int i = 0; i < list.size(); i++) {
				SortArray.quickSort(list, new Betet());
				for (int in = 0; in < list.size(); in++) {
				}
				System.out.println(list.get(i).toString());
			}
			System.out.println();
		}

		// Inte gjort Quick ännu
		if (e.getSource() == btnVaxandeQuick) {

			for (int i = 0; i < list.size(); i++) {
				SortArray.quickSort(list, new Alfa());
				for (int in = 0; in < list.size(); in++) {
				}
				System.out.println(list.get(i).toString());
			}
			System.out.println();

		}

		// Linjära sökningen efter en films
		if (e.getSource() == btnCheckforMovie && btnLinear.isSelected()) {
			find.findingMovieLin(list, typeMovie.getText());
		}
		System.out.println();

		// Binära sökningen efter en film
		if (e.getSource() == btnCheckforMovie && btnBinary.isSelected()) {
			find.findingBinary(list, typeMovie.getText());
		}
		System.out.println();

		
		if (e.getSource() == btnChangeMovie && changeMovie.isSelected()) {
			String text4 = String.format("Nu har ändringar sparats i listan");
			label.setText(text4);

			Movies newInfo = new Movies(typeNewTitle.getText(),
					typeNewType.getText(), typeNewGenre.getText(),
					typeNewActors.getText(), Integer.parseInt(typeNewLength
							.getText()), Integer.parseInt(typeNewGrade
							.getText()));

			int index = Integer.parseInt(typeMovieToChange.getText());

			list.set(index, newInfo);
			for (int i = 0; i < list.size(); i++) {
				System.out.println(list.get(i).toString());

			}
		}
		System.out.println();

		// Nytt element läggs till i listan längst ner
		if (e.getSource() == btnAddNewMovie) {
			Movies movie1 = new Movies(typeNewTitle.getText(),
					typeNewType.getText(), typeNewGenre.getText(),
					typeNewActors.getText(), Integer.parseInt(typeNewLength
							.getText()), Integer.parseInt(typeNewGrade
							.getText()));

			list.add(movie1);
			for (int i = 0; i < list.size(); i++) {
				System.out.println(list.get(i).toString());
			}
		}
		
		//Objekt försvinner från listan från det index anvädaren väljer
		if (e.getSource() == btnChangeMovie && removeMovie.isSelected()) {
			int index = Integer.parseInt(typeMovieToChange.getText());
			list.remove(index);
			//System.out.println(list);
			for (int i = 0; i < list.size(); i++) {
				System.out.println(list.get(i).toString());
		}
		}
		System.out.println();

		//Skriver ut innhållet i lista från den postition man skriver in som index
		if (e.getSource() == btnWrite) {
			int index = Integer.parseInt(typeMovieToChange.getText());
			sort.print(list.toString(), index);
			System.out.println(list.toString());
		}
	}

	
}
