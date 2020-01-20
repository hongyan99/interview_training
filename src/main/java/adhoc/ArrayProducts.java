package adhoc;

import java.util.Arrays;

public class ArrayProducts {
	public static void main(String[] args) {
//		System.out.println(Arrays.toString(getProductArray(new int[] {1, 2, 3, 4, 5})));
//		System.out.println(Arrays.toString(getProductArray(new int[] {0, 2, 3, 4, 5})));
//		System.out.println(Arrays.toString(getProductArray(new int[] {0, 0, 3, 4, 5})));
		System.out.println(Arrays.toString(getProductArray(new int[] {84107501, 258139847, -123134593})));
	}
	
	static int[] getProductArray(int[] nums) {
		// The algorithm:
		// Use the following matrix
		// 0 1 2 3 4 5 ... n
		// 0 1 2 3 4 5 ... n
		// .................
		// 0 1 2 3 4 5 ... n
		// a total of n rows. Remove the diagonal, the product of all numbers on each row
		// gives the result.
		// Thus, we can do two calculations, one is the triangle above the diagonal and the 
		// other below. Each consecutive row since need to multiply an extra number from its
		// neighbor which makes it a O(n) algorithm
		
		// This mod thing from the question, really makes no sense (adds no value!), 
		// but we just take it as is
		
		int[] products = new int[nums.length];
		products[0] = 1;
		final int mod = 1000000007;
		for(int i = 0; i < nums.length-1; i++) {
			nums[i] = nums[i]>0? nums[i]:(nums[i]+mod)%mod;
			products[i+1] = (int)((1L* products[i] * nums[i]) % mod);
		}
		nums[nums.length-1] = nums[nums.length-1]>0? nums[nums.length-1] : (nums[nums.length-1]+mod)%mod;
		long product = 1L;
		for(int i = nums.length-1; i > 0; i--) {
			product = (product * nums[i]) % mod;
			products[i-1] = (int) ((1L * products[i-1] * (int)product) % mod);
		}
		
		return products;
    }
}
