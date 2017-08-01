package imdb;


import java.util.ArrayList;
import java.util.Comparator;

public class SortArray {
	
	
	public static void bubbleSort(ArrayList<Movies> list,Comparator<Movies> comp) {
		for (int i = 0; i < list.size() - 1; i++) {
			for (int j = list.size() - 1; j > i; j--) {
				if (comp.compare(list.get(j), list.get(j - 1)) < 0)
					swaping(list, j, j - 1);
			}
		}
	}

	public static void swaping(ArrayList<Movies> list, int i, int j) {
		Movies temp = list.get(i);
		list.set(i, list.get(j));
		list.set(j, temp);

	}
	
	public static void shufflet(ArrayList<Movies> list) {
		int pos;
		for (int i = list.size() - 1; i > 0; i--) {
			pos = (int) (Math.random() * (i + 1));
			swaping(list, i, pos);
		}
	}
	
	public static void quickSort(ArrayList<Movies> list, Comparator<Movies> comp) {
		quickSort(list, 0, list.size() - 1, comp);
	}
	
	private static void quickSort(ArrayList<Movies> list, int left, int right, Comparator<Movies> comp) {
		int pivotIndex;
		if (left < right) {
			pivotIndex = partition(list, left, right, ((left + right) / 2), comp);
			quickSort(list, left, pivotIndex - 1, comp);
			quickSort(list, pivotIndex + 1, right, comp);
		}
	}

	private static int partition(ArrayList<Movies> list, int left, int right, int pivotIndex, Comparator<Movies> comp) {
		Movies pivotValue = list.get(pivotIndex);
		int storeIndex = left;
		swaping(list, pivotIndex, right);
		for (int i = left; i < right; i++) {
			if (comp.compare(list.get(i), pivotValue) <0) {
				swaping(list, i, storeIndex);
				storeIndex++;
			}
		}
		swaping(list, storeIndex, right);
		return storeIndex;
	}
	
	public void print(String txt, int pos) {
		if ((pos >= 0) && (pos < txt.length())) {
			System.out.print(txt.charAt(pos));
			print(txt, pos + 1);
		}
	}
	
}
