package recursion;

public class SumTarget {
	public static void main(String[] args) {
		System.out.println(check(new long[] {2, 4, 6}, 6));
		System.out.println(check(new long[] {2, 4, 6}, 5));
	}
	
	private static boolean check(long[] arr, long k) {
		return check(arr, k, 0, null);
	}

	private static boolean check(long[] arr, long k, int i, Long sum) {
		if(sum!=null && sum == k) {
			return true;
		}
		
		if(i==arr.length) {
			return false;
		}
		
		if(check(arr, k, i+1, sum)) {
			return true;
		}
		
		if(check(arr, k, i+1, sum(sum,arr[i]))) {
			return true;
		}
		return false;
	}
	
	private static long sum(Long a, long b) {
		if(a==null) {
			a = 0L;
		}
		return a+b;
	}
}
