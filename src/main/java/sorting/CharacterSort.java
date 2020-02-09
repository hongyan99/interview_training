package sorting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CharacterSort {
	public static void main(String[] args) {
		print(sort_array(createList('a', 's', 'd', 'f', 'g', '*', '&', '!', 'z', 'y')));
	}
	
	private static void print(List<Character> sort_array) {
		sort_array.forEach(c-> {
			System.out.println(c);
		});
	}

	private static List<Character> createList(Character... characters) {
		return Arrays.asList(characters);
	}
	
	public static List<Character> sort_array(List<Character> arr) {
		@SuppressWarnings("unchecked")
		List<Character>[] sorted = new List[123];
		arr.forEach(c-> {
			if(sorted[(int)c]==null) {
				sorted[(int)c] = new ArrayList<Character>();
			}
			sorted[(int)c].add(c);
		});
		List<Character> result = new ArrayList<>();
		for(List<Character> l : sorted) {
			if(l!=null) {
				l.forEach(c-> {
					result.add(c);
				});
			}
		}
		return result;
    }
}
