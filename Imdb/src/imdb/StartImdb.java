package imdb;

import javax.swing.JFrame;

public class StartImdb {
	
	
	public static void main(String[] args) {
		JFrame frame = new JFrame("MovieTime");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(new GUI());
		frame.pack();
		frame.setVisible(true);
	}

}
