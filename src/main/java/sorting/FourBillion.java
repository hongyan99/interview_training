package sorting;

import java.util.Arrays;
import java.util.BitSet;
import java.util.List;

public class FourBillion {
	public static void main(String[] args) {
		System.out.println(find_integer(createArray(4294967295L, 399999999L, 0L)));
	}
	
	private static List<Long> createArray(Long...longs) {
		return Arrays.asList(longs);
	}
	
	/**
	 * Implementation copied from IKWeb.
	 * 
	 * @param arr the array of long numbers
	 * @return a long number not in the passed in list of numbers.
	 */
    public static long find_integer(List<Long> arr) {
    	// Algorithm:
    	// Treat each number as composed of two parts, the high 16 bits + low 16 bits
    	// First make an array of buckets to count how many number there is for each high 16 bit.
    	// If the count is less than 2^16, then there must be one number in that bucket is not taken.
    	// If that number (if more than one, find the first of such number) and that is our answer.
        long ans = 0;
        int size = (int) (Math.pow(2, 16));
        // Since we are looking at only the high 16 bits, 2^16 is bit enough to hold all buckets
        int freq[] = new int[size];
        for(long l : arr) {
        	// Here we count how many numbers are in a bucket
            freq[(int) (l>>16)]++;
        }
        // Loop through the buckets
        for(int i = 0; i < size; i++) {
        	// if bucket i has less than 2^16 numbers, it is possible to find a slot (number not taken)
            if(freq[i]<size) {
            	// make an array to mark whether a number is taken
            	// Since within a bucket (we are in bucket i), all numbers have the same high 16 bits,
            	// we only need to look at the lower 16 bits, and thus the array size is 2^16.
                BitSet b = new BitSet(size);
                // Now loop through our numbers again to find which bucket it belongs to
                for(long l : arr) {
                    int bits = (int) (l>>16);
                    // If the number belongs to bucket i (which we currently are working on),
                    // then we mark the lower bits to indicate that this number is already taken
                    if(bits == i) {
                        //we set the corresponding bit for that suffix
                        b.set((int) (l%size));
                    }
                }
                // Now loop through the marks for the lower bits, to find an empty slot
                for(int j = 0; j < size; j++) {
                    if(!b.get(j)) {
                    	// If the bit is not taken, the result if the high 16 bits + the low 16 bits.
                    	// The high 16 bits correspond to i, and the low correspond to j
                        ans = (i<<16) + j;
                        return ans;
                    }
                }
            }
        }
        return ans;
    }
}
