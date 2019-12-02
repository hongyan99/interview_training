package recursion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

//Using the given digits, find all expressions that gives the given number.
//Operations can be used are "Join", "Add" and "Multiply".
//Join: 2 Join 3 -> 23
//Add: 2 + 3 -> 5
//Multiply: 2 * 3 = 6
//Note that the operation precedence from high to low is Join->Multiply->Add
public class ExpressionTarget2 {
	public static void main(String[] args) {
		String[] result = findExpressions("222", 24);
		System.out.println(Arrays.toString(result));
		result = findExpressions("1234", 11);
		System.out.println(Arrays.toString(result));
//		String[] result = findExpressions("0000000001", 0);
//		System.out.println(Arrays.toString(result));
//		System.out.println();
//		result = findExpressions("1232537859", 995);
//		System.out.println(Arrays.toString(result));
//		System.out.println();
		result = findExpressions("00000000001", 1);
		System.out.println(Arrays.toString(result));
		System.out.println(result.length);
//		result = findExpressions("6666666666666", 6);
//		System.out.println(Arrays.toString(result));
	}
	
	private static String[] findExpressions(String s, long target) {
		List<String> result = new ArrayList<>();
		findExpressions(s.toCharArray(), target, 1, result, String.valueOf(s.charAt(0)), new HashMap<String, Long>());
		return result.toArray(new String[result.size()]);
	}

	private static void findExpressions(char[] str, long target, int index, List<String> result, String expression, Map<String, Long> cache) {
		if(index==str.length) {
			if(target== evaluate(expression, cache)) {
				result.add(expression);
			}
			return;
		}
		
		findExpressions(str, target, index+1, result, expression+"+"+str[index], cache);
		findExpressions(str, target, index+1, result, expression+"*"+str[index], cache);
		findExpressions(str, target, index+1, result, expression+str[index], cache);
	}

	private static long evaluate(String expression, Map<String, Long> cahce) {
		StringTokenizer st = new StringTokenizer(expression, "+");
		long result = 0;
		while(st.hasMoreTokens()) {
			result += evaluateMulti(st.nextToken(), cahce);
		}
		return result;
	}

	private static long evaluateMulti(String expression, Map<String, Long> cache) {
		Long cached = cache.get(expression);
		if(cached!=null) return cached;
		StringTokenizer st = new StringTokenizer(expression, "*");
		long result = 1;
		while(st.hasMoreTokens()) {
			result *= Long.parseLong(st.nextToken());
		}
		cache.put(expression, result);
		return result;
	}
}
