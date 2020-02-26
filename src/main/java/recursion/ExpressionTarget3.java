package recursion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//Using the given digits, find all expressions that gives the given number.
//Operations can be used are "Join", "Add" and "Multiply".
//Join: 2 Join 3 -> 23
//Add: 2 + 3 -> 5
//Multiply: 2 * 3 = 6
//Note that the operation precedence from high to low is Join->Multiply->Add
public class ExpressionTarget3 {
	public static void main(String[] args) {
		String[] result = findExpressions("222", 24);
		System.out.println(Arrays.toString(result));
		result = findExpressions("1234", 11);
	}
	
	private static String[] findExpressions(String s, long target) {
		// The algorithm
		// Treat the calculation as calculate a+b(*c);
		// Starting with a=0, b=chars[0], c=-1. 
		// Loop through each character from left to right.
		// After each character, there are three different possibilities
		// 1) it is a join, then 
		//		if c==-1: join the digit into b (make sure -1 is treated properly)
		//		else: join into c (make sure -1 is treated properly)
		// 2) it is a '*', then
		//		if c==-1: c=the new char 
		//		else: b=b*c, c=the new char (make sure -1 is treated properly)
		// 3) it is a '+', then
		//		if c==-1: a=a+b, b=the new char (make sure -1 is treated properly)
		//		else: a=a+b*c, b=the new char (make sure -1 is treated properly)
		// The exit condition when i==chars.length
		// 1) if c==-1: a=a+b, store into result list if it equals target
		// 2) else: a=a+b*c, store into result if it equals target

		List<String> result = new ArrayList<>();
		char[] chars = s.toCharArray();
		if(chars.length>0) {
			long a=0L, b=chars[0]-'0', c=-1L;
			char[] buffer = new char[chars.length*2+1];
			buffer[0] = chars[0];
			loop(a, b, c, 1, chars, target, buffer, 1, result);
		}
		return result.toArray(new String[result.size()]);
	}

	// It is critical that we don't modify any of the variables passed in
	// Otherwise, the computation for the different permutations (per join, '*' and '+')
	// would interfere with each other, which is why I intentionally declare
	// all the parameters as final!!!
	private static void loop(final long a, final long b, final long c, 
			final int i, final char[] chars, final long target, 
			final char[] buffer, final int bs, final List<String> result) {
		if(i==chars.length) {
			if(c==-1) {
				if(a+b!=target) {
					return;
				}
			} else if(a+b*c!=target) {
				return;
			}
			result.add(new String(buffer, 0, bs));
			return;
		}

		// process join
		buffer[bs] = chars[i];
		long newNum = chars[i]-'0';
		if(c==-1) {
			loop(a, join(b, newNum), c, i+1, chars, target, buffer, bs+1, result);
		} else {
			loop(a, b, join(c, newNum), i+1, chars, target, buffer, bs+1, result);
		}
		
		// process '*'
		buffer[bs] = '*';
		buffer[bs+1] = chars[i];
		if(c==-1) {
			loop(a, b, newNum, i+1, chars, target, buffer, bs+2, result);
		} else {
			loop(a, b*c, newNum, i+1, chars, target, buffer, bs+2, result);
		}
		
		// process '+'
		buffer[bs] = '+';
		buffer[bs+1] = chars[i];
		if(c==-1) {
			loop(a+b, newNum, c, i+1, chars, target, buffer, bs+2, result);
		} else {
			loop(a+b*c, newNum, -1L, i+1, chars, target, buffer, bs+2, result);
		}
	}

	private static long join(long b, long c) {
		return b==-1? c : b*10 + c;
	}
}
