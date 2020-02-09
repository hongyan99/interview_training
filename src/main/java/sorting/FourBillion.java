package sorting;

import java.util.Arrays;
import java.util.List;

public class FourBillion {
	public static void main(String[] args) {
		System.out.println(find_integer(createList(4294967295L, 399999999L, 0L)));
	}
	
	private static List<Long> createList(Long... nums) {
		return Arrays.asList(nums);
	}
	
	public static long find_integer(List<Long> arr) {
		long mask = 0L;
		for(long l : arr) {
			mask |= l;
		}
		
		long bit = 1L;
		for(int i = 0; i<32; i++) {
			if((mask & bit)==0) {
				mask |= bit;
				return mask;
			}
			bit=bit<<1;
		}
		
		return 0;
    }
}
