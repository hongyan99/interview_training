package review.backtracking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ParenthesesPairs {
	public static void main(String[] args) {
		String[] result = pair(4);
		System.out.println(Arrays.toString(result));
	}
	
	private static String[] pair(int n) {
		List<String> result = new ArrayList<>();
		solve(n, n, 0, new char[n*2], result);
		String[] returns = new String[result.size()];
		return result.toArray(returns);
	}

	private static void solve(int left, int right, int pointer, char[] chars, List<String> result) {
		if(left==0) {
			for(int k = pointer; k<chars.length; k++) {
				chars[k]=')';
			}
			// This is very important, since we
			// "freeze" the result into the result array and thus, 
			// subsequent tries can use chars array freely.
			result.add(new String(chars));
			return;
		}

		// since we keep track of the index of the array (pointer), there is no need to unset.
		chars[pointer] = '(';
		solve(left-1, right, pointer+1, chars, result);
		if(left<right) {
			chars[pointer] = ')';
			solve(left, right-1, pointer+1, chars, result);
		}
	}
}
