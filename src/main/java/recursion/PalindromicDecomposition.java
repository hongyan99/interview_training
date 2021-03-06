package recursion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PalindromicDecomposition {
	public static void main(String[] args) {
		System.out.println(Arrays.toString(decompose("abracadabra")));
	}

	// The idea is to mark the characters with 1 or 0, consecutive 1 or 0 indicates 
	// the characters for one single palindrome. For example, "1001110" means, something 
	// like "a|bb|cdc|e".
	private static String[] decompose(String s) {
		char[] array = s.toCharArray();
		List<String> decomposed = new ArrayList<String>();
		// Since "1001110" and "0110001" really means the same thing in this context, and 
		// so we start the marker String with "1" always. 
		decompose(array, 1, "1", decomposed);
		return decomposed.toArray(new String[decomposed.size()]); 
	}

	private static void decompose(char[] array, final int index, String comb, List<String> result) {
		if(index==array.length) {
			String checked = check(array, comb);
			if(checked != null) {
				result.add(checked);
			}
			return;
		}
		decompose(array, index+1, comb+'0', result);
		decompose(array, index+1, comb+'1', result);
	}

	private static String check(char[] array, String comb) {
		int start = 0;
		char marker = '1';
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < array.length; i++) {
			if(comb.charAt(i)!=marker) {
				if(!check(array, start, i)) {
					return null;
				}
				marker=marker=='1'? '0' : '1';
				start = i;
				sb.append('|');
			}
			sb.append(array[i]);
		}
		if(!check(array, start, array.length)) {
			return null;
		}
		return sb.toString();
	}
	
	private static boolean check(char[] array, int start, int end) {
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
