package recursion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PalindromicDecomposition3 {
	public static void main(String[] args) {
		System.out.println(Arrays.toString(decompose("abracadabra")));
	}

	private static String[] decompose(String s) {
		char[] chars = s.toCharArray();
		List<String> decomposed = new ArrayList<String>();
		
		// The algorithm
		// Formula: left, c
		// 	left = a, b for iteration i:
		//		where a is 0 or more palindromes separated by '|' and stored in an char array, 
		//		and with a total length of p.
		//		b is another char array of length q. It stores the palindrome currently composing.
		// 	c is the character for iteration i+1
		
		// Initialize a={}, b=chars[0], i=0
		
		// There are two possibilities 
		//	1) if b forms a new palindrome:
		//		a) a={a, b}, b=c
		//		b) b=b+c
		//  2) else, b=b+c
		
		// Note that 1.b) and 2) can be combined as one.
		
		// Exit condition: when i==chars.length
		//	if b is a palindrome add to the collection
		
		if(chars.length>0) {
			char[] a = new char[chars.length*2-1];
			char[] b = new char[chars.length];
			b[0] = chars[0];
			loop(1, chars, a, 0, b, 1, decomposed);
		}
		
		return decomposed.toArray(new String[decomposed.size()]); 
	}

	private static void loop(final int i, final char[] chars, final char[] a, int p, 
			final char[] b, final int q, final List<String> decomposed) {
		if(i==chars.length) {
			if(isPalindrome(b, q)) {
				decomposed.add(createResult(new String(a, 0, p), new String(b, 0, q)));
			}
			return;
		}
		
		if(isPalindrome(b, q)) {
			// handle b is palindrome
			if(p>0) {
				a[p]='|';
				System.arraycopy(b, 0, a, p+1, q);
			} else {
				System.arraycopy(b, 0, a, p, q);
			}
			char[] newB = new char[chars.length];
			newB[0] = chars[i];
			loop(i+1, chars, a, p==0? q : p+q+1, newB, 1, decomposed);
		}

		// handle b=b+c
		b[q] = chars[i];
		loop(i+1, chars, a, p, b, q+1, decomposed);
	}

	private static String createResult(String a, String b) {
		return a.length()>0? a + '|' + b : b;
	}

	private static boolean isPalindrome(char[] b, int len) {
		int l=0, r=len-1;
		while(l<r) {
			if(b[l]!=b[r]) {
				return false;
			}
			l++;
			r--;
		}
		return true;
	}
}
