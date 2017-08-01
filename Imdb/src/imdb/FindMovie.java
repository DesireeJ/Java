package imdb;

import java.util.ArrayList;
import java.util.Arrays;

public class FindMovie {

	public int findingMovieLin(ArrayList<Movies> list, String title) {
		for (int i = 0; i < list.size(); i++) {
			if (title.equals(list.get(i).getTitle())) {
				System.out.println("Fungerade jippe Linjärt. Se info nedan---->\n"+ list.get(i).toString1());
				return i;
			} else {

			}
		}
		System.out.println("Nej, sökta filmen finns inte i listan");
		return -1;
	}
	public int findingBinary(ArrayList<Movies> list, String title) {
		SortArray.bubbleSort(list, new Alfa());

		int res = -1, min = 0, max = list.size() - 1, pos;
		while ((min <= max) && (res == -1)) {
			pos = (min + max) / 2;
			if (title.equals(list.get(pos).getTitle())) {
				res = pos;
				System.out.println("Fungerade jippe. Se info nedan---->\n"
						+ list.get(pos).toString1());
				return res;
			} else if (title.compareTo(list.get(pos).getTitle()) < 0) {
				max = pos - 1;
			} else if (title.compareTo(list.get(pos).getTitle()) > 0)
				min = pos + 1;
		}
		System.out.println("Tyvärr filmen finns inte i listan");
		return res;
	}
}