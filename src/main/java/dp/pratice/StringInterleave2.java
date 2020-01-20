package dp.pratice;

import java.util.ArrayList;
import java.util.List;

/**
 * You're given three strings a, b and i. Write a function that checks whether i is an interleaving of a and b.
 * 
 * String i is said to be interleaving string a and b, if:
 * len(i) = len(a) + len(b).
 * * only contains characters present in a or b.
 * * contains all characters of a. From a, any character a[index] should be added exactly once in i.
 * * contains all characters of b. From b, any character b[index] should be added exactly once in i.
 * 
 * Order of all characters in individual strings (a and b) is preserved.
 * 
 * This implementation appears to be better than that of StringInterleave, but the actual result is
 * much slower for large String.
 * 
 * @author Hongyan
 *
 */
public class StringInterleave2 {
	public static void main(String[] args) {
		System.out.println(doStringsInterleave("123", "456", "123456"));
		System.out.println(doStringsInterleave("AAB", "AAC", "AAAACB"));
		System.out.println(doStringsInterleave("AAB", "AAC", "AAABAC"));
		System.out.println(doStringsInterleave("ABC", "DEF", "ABCABC"));
	}
	
    public static boolean doStringsInterleave(String a, String b, String i) {
    	//With a bottom up approach, we loop through the String i, and record
    	//all combinations that makes it an interleave of substrings of a and b.
    	//The DP table key would be the end index for the substring of i, the
    	//value would be an array that contains all possible combinations of
    	//substrings of a and b that makes the substring of i an interleave.
    	//Each list element would be itself an array of size 2, where index 0 
    	//stores the end index of the substring of a and index 1 stored the
    	//end index of the substring of b.
    	
    	//The base case would be -1->(-1,-1) means
    	//(empty i is an interleave of empty a and empty b)
    	//Iteration expression would be 
    	//at step k+1 for each element in the DP table value for k
    	//compare character at the current index of a with that of i or that 
    	//of b with that of i, if they are NOT equal, then break and return 
    	//false (short circuit), otherwise, store the new value to the DP 
    	//table.
    	
    	//Since we only need to store the DP table value for the prior index 
    	//of i, we don't have to store the value of earlier steps, which 
    	//result in better space efficiency (instead of using a map, we simply 
    	//store a single value.
    	List<int[]> interleave = new ArrayList<>();
    	interleave.add(new int[] {-1, -1});
    	for(int k = 0; k < i.length(); k++) {
        	List<int[]> newValues = new ArrayList<>();
			for(int[] idx : interleave) {
				int n = idx[0]+1;
	    		if(n<a.length() && i.charAt(k)==a.charAt(n)) {
					newValues.add(new int[] {n, idx[1]});
	    		}
				int m = idx[1]+1;
	    		if(m<b.length() && i.charAt(k)==b.charAt(m)) {
					newValues.add(new int[] {idx[0], m});
	    		}
			}
    		if(newValues.size()==0) {
    			return false;
    		}
    		interleave = newValues;
    	}
    	return true;
    }
}
