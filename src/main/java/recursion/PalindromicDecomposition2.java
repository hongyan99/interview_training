package recursion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PalindromicDecomposition2 {
	public static void main(String[] args) {
		System.out.println(Arrays.toString(decompose("eeeeeeeeeeeeeeeeeeee")));
	}

	// The idea is to mark the characters with 1 or 0, consecutive 1 or 0 indicates 
	// the characters for one single palindrome. For example, "1001110" means, something 
	// like "a|bb|cdc|e".
	private static String[] decompose(String s) {
		char[] array = s.toCharArray();
		List<String> decomposed = new ArrayList<String>();
		// Since "1001110" and "0110001" really means the same thing in this context, and 
		// so we start the marker String with "1" always. 
		Map<String, Boolean> cache = new HashMap<>();
		decompose(array, 1, "1", decomposed, cache);
		return decomposed.toArray(new String[decomposed.size()]); 
	}

	private static void decompose(char[] array, final int index, String comb, List<String> result, Map<String, Boolean> cache) {
		if(index==array.length) {
			String checked = check(array, comb, cache);
			if(checked != null) {
				result.add(checked);
			}
			return;
		}
		decompose(array, index+1, comb+'0', result, cache);
		decompose(array, index+1, comb+'1', result, cache);
	}

	private static String check(char[] array, String comb, Map<String, Boolean> cache) {
		int start = 0;
		char marker = '1';
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < array.length; i++) {
			if(comb.charAt(i)!=marker) {
				if(!check(array, start, i, cache)) {
					return null;
				}
				marker=marker=='1'? '0' : '1';
				start = i;
				sb.append('|');
			}
			sb.append(array[i]);
		}
		if(!check(array, start, array.length, cache)) {
			return null;
		}
		return sb.toString();
	}
	
	private static boolean check(char[] array, int start, int end, Map<String, Boolean> cache) {
		Boolean cached = cache.get(""+start+"_"+"end");
		if(Boolean.TRUE.equals(cached)) {
			return true;
		}
		int left = start; 
		int right = end-1;
		while(left<right) {
			if(array[left++]!=array[right--]) {
				return false;
			}
		}
		return true;
	}
}
