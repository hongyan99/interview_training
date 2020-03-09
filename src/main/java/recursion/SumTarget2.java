package recursion;

public class SumTarget2 {
	public static void main(String[] args) {
		System.out.println(check(new long[] {2, 4, 6}, 6));
		System.out.println(check(new long[] {2, 4, 6}, 5));
	}
	
	private static boolean check(long[] arr, long k) {
		return check(arr, k, 0, 0);
	}

	private static boolean check(long[] arr, long k, int i, int includedCount) {
		if(k==0 && includedCount>0) {
			return true;
		}
		
		if(i==arr.length) {
			return false;
		}
		
		return check(arr, k, i+1, includedCount) || check(arr, k-arr[i], i+1, includedCount+1);
	}
}
