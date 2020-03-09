package review.backtracking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ParencesePairs {
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

	private static void solve(int left, int right, int count, char[] chars, List<String> result) {
		if(left==0) {
			for(int k = count; k<chars.length; k++) {
				chars[k]=')';
			}
			result.add(new String(chars));
			return;
		}

		// since we keep track of the index of the array (count), there is not need to unset.
		chars[count] = '(';
		solve(left-1, right, count+1, chars, result);
		if(left<right) {
			chars[count] = ')';
			solve(left, right-1, count+1, chars, result);
		}
	}
}
