package imdb;

import java.awt.Component;
import java.util.ArrayList;

public class Movies {

	private String title;
	private String type;
	private String genre;
	private String actors;
	private int length;
	private int grade;


	public Movies(String titleIn, String typeIn, String genreIn,
			String actorsIn, int lengthIn, int gradeIn) {
		this.title = titleIn;
		this.type = typeIn;
		this.genre = genreIn;
		this.actors = actorsIn;
		this.length = lengthIn;
		this.grade = gradeIn;
	}


	public String getTitle() {
		return this.title;
	}

	public String getGenre() {
		return this.genre;
	}

	public String getType() {
		return this.type;
	}

	public String getActors() {
		return this.actors;
	}

	public int getLength() {
		return this.length;
	}

	public int getGrade() {
		return this.grade;
	}

	
	public void setTitle(String titleIn) {
		this.title = titleIn;
	}

	public void setType(String typeIn) {
		this.type = typeIn;
	}

	public void setGenre(String genreIn) {
		this.genre = genreIn;
	}

	public void setActors(String actorsIn) {
		this.actors = actorsIn;
	}

	public void setLength(int lengthIn) {
		this.length = lengthIn;
	}

	public void setGrade(int gradeIn) {
		this.grade = gradeIn;
	}

	public String toString1() {
		return ("Titel: " + this.title + "\nType: " + this.type + "\nGenre: "
				+ this.genre + "\nActors: " + this.actors + "\nLängd min: "
				+ this.length + "\nBetyg: " + this.grade);
	}
	
	public String toString2() {
		return ("Titel: " + this.title + " Type: " + this.type + " Genre: "
				+ this.genre + " Actors: " + this.actors + " Längd min: "
				+ this.length + " Betyg: " + this.grade);
	}

	public String toString() {
		return String.format("%s  %s  %s  %s  %d  %d", this.title, this.type,
				this.genre, this.actors, this.length, this.grade);
	}

}
