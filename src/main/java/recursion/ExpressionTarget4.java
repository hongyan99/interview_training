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
public class ExpressionTarget4 {
	public static void main(String[] args) {
		String[] result = findExpressions("222", 24);
		System.out.println(Arrays.toString(result));
		result = findExpressions("1234", 11);
		System.out.println(Arrays.toString(result));
	}
	
	private static String[] findExpressions(String s, long target) {
		// The algorithm
		// Treat the calculation as calculating the formula:    left    op     d
		// where left is the result of iteration i, and can be one of the following:
		// a) n
		// b) m * n
		// c) a + m * n

		// with initial condition, i=0, a=0, m=1, n=chars[0]
		// a), b), c) can be consolidated into c). The formula becomes a+m*n.
		
		// d is the digit for iteration i+1
		
		// op can be one of the following
		// 1) join -> n = n*10+d
		// 2) * -> m=m*n, n=d 
		// 3) + -> a=a+m*n, m=1, n=d
		
		// exit condition: when i==chars.length
		//	if a+m*n==target, add to result list

		List<String> result = new ArrayList<>();
		char[] chars = s.toCharArray();
		if(chars.length>0) {
			long a=0L, m=1L, n=chars[0]-'0';
			char[] buffer = new char[chars.length*2+1];
			buffer[0] = chars[0];
			loop(a, m, n, 1, chars, target, buffer, 1, result);
		}
		return result.toArray(new String[result.size()]);
	}

	// It is critical that we don't modify any of the variables passed in
	// Otherwise, the computation for the different permutations (per join, '*' and '+')
	// would interfere with each other, which is why I intentionally declare
	// all the parameters as final!!!
	private static void loop(final long a, final long m, final long n, 
			final int i, final char[] chars, final long target, 
			final char[] buffer, final int bs, final List<String> result) {
		if(i==chars.length) {
			if(a+m*n==target) {
				result.add(new String(buffer, 0, bs));
			}
			return;
		}

		// process join
		long newNum = chars[i]-'0';
		buffer[bs] = chars[i];
		int i1=i+1, bs1=bs+1, bs2=bs1+1;
		loop(a, m, n*10+newNum, i1, chars, target, buffer, bs1, result);
		
		// process '*'
		buffer[bs] = '*';
		buffer[bs1] = chars[i];
		loop(a, m*n, newNum, i1, chars, target, buffer, bs2, result);
		
		// process '+'
		buffer[bs] = '+';
		buffer[bs1] = chars[i];
		loop(a+m*n, 1, newNum, i1, chars, target, buffer, bs2, result);
	}
}
