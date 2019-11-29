package recursion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SubsetGenerator {
	public static void main(String[] args) {
		System.out.println(Arrays.toString(generate("xy")));
	}
	
	private static String[] generate(String s) {
		final List<String> result = new ArrayList<String>();
		generate(s.toCharArray(), 0, result, "");
		return result.toArray(new String[result.size()]);
	}

	private static void generate(char[] charArray, int index, List<String> result, String holder) {
		if(index==charArray.length) {
			result.add(holder);
			return;
		}
		generate(charArray, index+1, result, holder);
		generate(charArray, index+1, result, holder+charArray[index]);
	}
}
