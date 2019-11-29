package recursion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExpressionTarget {
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
		findExpressions(s.toCharArray(), target, 1, new Join(s.charAt(0)), result, String.valueOf(s.charAt(0)));
		return result.toArray(new String[result.size()]);
	}

	private static void findExpressions(char[] str, long target, int index, Operation operation, List<String> result, String expression) {
		if(index==str.length) {
			if(target== operation.evaluate()) {
				result.add(expression);
			}
			return;
		}
		
		findExpressions(str, target, index+1, operation.join(str[index]), result, expression+str[index]);
		findExpressions(str, target, index+1, operation.add(str[index]), result, expression+"+"+str[index]);
		findExpressions(str, target, index+1, operation.multiply(str[index]), result, expression+"*"+str[index]);
	}
	
	// Addition has lower precedence than Multiplication operation, and thus we must evaluate 
	// Multiplication first if there is any such Operation in the expression. We only need to 
	// do this for the RHS since when we reach the "+" operator, we already have all we 
	// need to calculate the LHS.
	private static class Addition extends Operation {
		private final long left;
		private final long right;
		private Operation multiplication;
		
		public Addition(long left, long right) {
			this.left = left;
			this.right = right;
		}

		@Override
		public Operation join(char next) {
			Addition add = new Addition(left, right);
			if(multiplication==null) {
				// If we reach a Join operation which has even higher precedence than 
				// multiplication, we simply create a dummy multiplication operation 
				// so that we can reuse the logic implemented in the Multiplication 
				// Operation to handle join. 
				add.multiplication = new Multiplication(1, join(right, next));
			} else {
				add.multiplication = multiplication.join(next);
			} 
			return add;
		}

		@Override
		public Operation multiply(char next) {
			Addition add = new Addition(left, right);
			if(multiplication==null) {
				add.multiplication = new Multiplication(right, charToLong(next));
			} else {
				add.multiplication = multiplication.multiply(next);
			}
			return add;
		}

		@Override
		public Operation add(char next) {
			if(multiplication==null) {
				return new Addition(this.left + right, charToLong(next));
			} else {
				return new Addition(left + multiplication.evaluate(), charToLong(next));
			}
		}

		@Override
		public long evaluate() {
			if(multiplication==null) {
				return left + right;
			}
			
			return left + multiplication.evaluate();
		}
	}
	
	// Multiplication has lower precedence than Join operation, and thus we must evaluate 
	// Join first if there is any such Operation in the expression. We only need to 
	// do this for the RHS since when we reach the "*" operator, we already have all we 
	// need to calculate the LHS.
	private static class Multiplication extends Operation {
		private final long left;
		private final long right;
		private Operation join;
		
		public Multiplication(long left, long right) {
			this.left = left;
			this.right = right;
		}

		@Override
		public Operation join(char next) {
			Multiplication multi = new Multiplication(left, right);
			if(join==null) {
				multi.join = new Join(join(right, next));
			} else {
				multi.join = join.join(next);
			}
			return multi;
		}

		@Override
		public Operation multiply(char next) {
			if(join==null) {
				return new Multiplication(this.left * right, charToLong(next));
			} else {
				return new Multiplication(this.left * join.evaluate(), charToLong(next));
			}
		}

		@Override
		public Operation add(char next) {
			if(join==null) {
				return new Addition(left * right, charToLong(next));
			} else {
				return new Addition(left * join.evaluate(), charToLong(next));
			}
		}

		@Override
		public long evaluate() {
			if(join==null) {
				return left * right;
			}
			return left * join.evaluate();
		}
	}
	
	private static class Join extends Operation{
		private final long left;
		
		public Join(char c) {
			this.left = charToLong(c); 
		}
		
		public Join(long left) {
			this.left = left; 
		}
		
		@Override
		public Operation join(char next) {
			return new Join(join(left, next));
		}

		@Override
		public Operation multiply(char next) {
			return new Multiplication(left, charToLong(next));
		}

		@Override
		public Operation add(char next) {
			return new Addition(left, charToLong(next));
		}

		@Override
		public long evaluate() {
			return left;
		}
	}
	
	private static abstract class Operation {
		public abstract Operation join(char next);
		public abstract Operation multiply(char next);
		public abstract Operation add(char next);
		public abstract long evaluate();
		
		static long join(long l, char c) {
			return 10*l + (int)c-48;
		}
		
		static long charToLong(char c) {
			return (int)c-48;
		}
	}
}