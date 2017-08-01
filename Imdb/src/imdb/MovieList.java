package imdb;

import java.util.*;
import java.io.*;

public class MovieList {

	public static ArrayList<Movies> readMovies(String fileName) {
		ArrayList<Movies> list = new ArrayList<Movies>();

		try {

			BufferedReader br = new BufferedReader(new FileReader(fileName));
			String[] parts;
			Movies movies;
			String txt = br.readLine();
			while (txt != null) {
				parts = txt.split(",");
				movies = new Movies(parts[0], parts[1], parts[2], parts[3],
						Integer.parseInt(parts[4]), Integer.parseInt(parts[5]));
				list.add(movies);
				txt = br.readLine();
			}
			br.close();
		} catch (IOException e) {
			System.out.println("readMovies: " + e);
		}
		return list;
	}
}
