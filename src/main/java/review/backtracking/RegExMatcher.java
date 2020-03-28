package review.backtracking;

/**
 * Given an input string (s) and a pattern (p), implement regular expression matching 
 * with support for '.' and '*'.
 * 
 * '.' Matches any single character.
 * '*' Matches zero or more of the preceding element.
 * 
 * The solution here uses the backtracking method.
 * 
 * @author Hongyan
 *
 */
public class RegExMatcher {
	public static void main(String[] args) {
		System.out.println(match("", "a*b*"));
		System.out.println(match("mississippi", "mis*is*p*."));
		System.out.println(match("aab", "c*a*b"));
		System.out.println(match("ab", ".*"));
		System.out.println(match("aa", "a"));
		System.out.println(match("a", "ab*"));
	}
	
	static boolean match(String s, String p) {
		return match(s.toCharArray(), 0, p.toCharArray(), 0);
	}
	
	static boolean match(char[] s, int i, char[] p, int j) {
		if(i==s.length && j==p.length) {
			return true;
		} else if(j==p.length) {
			return false;
		}

		// This should be very similar to the Parentheses Pairs problem, except that
		// the constraint is the matching criteria instead of left<right
		if (j<p.length-1 && p[j+1]=='*') {
			return match(s, i, p, j+1);
		} else {
			switch(p[j]) {
			case '.':
				return match(s, i+1, p, j+1);
			case '*':
				boolean matched = false;
				if(j>0) {
					char lastChar = p[j-1];
					int ii = i;
					if(lastChar=='.') {
						do {
							matched=match(s, ii++, p, j+1);
							if(matched) break;
						} while (ii<=s.length);
					} else {
						do {
							matched=match(s, ii, p, j+1);
							if(matched) break;
						} while (ii<=s.length-1 && s[ii++]==lastChar);
					}
				}
				return matched;
			default:
				return i<s.length && s[i]==p[j] && match(s, i+1, p, j+1);
			}
		}
	}
}
