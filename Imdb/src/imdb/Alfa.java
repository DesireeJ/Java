package imdb;

import java.util.ArrayList;
import java.util.Comparator;

public class Alfa implements Comparator {

	public int compare(Object obj1, Object obj2) {
		Movies title1 = (Movies) obj1;
		Movies title2 = (Movies) obj2;
		String name1 = title1.getTitle();
		String name2 = title2.getTitle();
		if (name1.compareTo(name2) > 0) {
			return 1;
		} else if (name1.compareTo(name2) < 0) {
			return -1;
		} else {
			return 0;
		}
	}	
}
