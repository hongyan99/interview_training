package recursion;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class BracketPairs {
	public static void main(String[] args) {
		System.out.println(Arrays.toString(computePairs(4)));
	}
	
	// Given a positive integer n, find ALL well formed round brackets string of length 2*n.
	private static String[] computePairs(int n) {
		Set<String> result = new HashSet<>();
		work(n, n, "", result);
		System.out.println(result.size());
		return result.toArray(new String[result.size()]);
	}
	
	private static void work(int left, int right, String work, Set<String> result) {
		if(left==0) {
			StringBuilder sb = new StringBuilder(work);
			for(int k = 0; k < right; k++) {
				sb.append(')');
			}
			result.add(sb.toString());
			return;
		}

		if(right==0) {
			StringBuilder sb = new StringBuilder(work);
			for(int k = 0; k < left; k++) {
				sb.insert(0,'(');
			}
			result.add(sb.toString());
			return;
		}

		work(left-1, right, work+"(", result);
		if(left<right) {
			work(left, right-1, work+")", result);
		}
	}
}