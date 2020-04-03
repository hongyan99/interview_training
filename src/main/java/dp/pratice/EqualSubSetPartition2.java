package dp.pratice;

import java.util.Arrays;
import java.util.BitSet;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Given an array s of n integers. Your task is to partition the given set s 
 * into two non-empty subsets, say s1 and s2 such that sum of all elements 
 * in s1 is equal to the sum of all elements in set s2. If it is not possible 
 * to partition the array s then returns a blank array else return a boolean 
 * array of size n where i (0<=i<n) element is true if it is part of s1 and 
 * false if it is part of s2.
 * 
 * @author Hongyan
 *
 */
public class EqualSubSetPartition2 {
	public static void main(String[] args) {
		Integer[] arr = new Integer[] {0, 0};
		List<Boolean> result = equalSubSetSumPartition(Arrays.asList(arr));
		System.out.println(Arrays.toString(result.toArray(new Boolean[result.size()])));
	}
	
	public static List<Boolean> equalSubSetSumPartition(List<Integer> s) {
		// if the total sum is a odd number, it is not possible to divide
		long sum = 0;
		for(int i = 0; i < s.size(); i++) {
			sum += s.get(i);
		}
		Long hSum = sum/2;
		if(sum % 2 == 0) {
			//The idea is to loop through the list of numbers in one path, calculate 
			//cumulatively the sum of the numbers by either include or exclude the 
			//number at the current iteration. Store all calculated values in a map. 
			//Since we care about only one result, it is OK if a two different summations 
			//collides (have the same summation). We can either skip or override with the 
			//new value. In this implementation, I simply override with the later one.
			//The key is the new summation, while the value is a BitSet that records the
			//indices of the values that are included in the calculation. Whenever we
			//find that sum equals the half sum of the whole list, exit the loop and
			//return the result.
			
			//Our DP table: stores the result of each iteration. 
			Map<Long, BitSet> values = new HashMap<>();
			BitSet bs = new BitSet(s.size());
			bs.set(0);
			values.put(0L+s.get(0), bs);
			start: {
				Map<Long, BitSet> valuesNew = new HashMap<>(values);
				for(int i = 0; i < s.size(); i++) {
					for(Long k : values.keySet()) {
						Long newK = k + s.get(i);
						bs = values.get(k);
						BitSet bs1 = (BitSet)bs.clone();
						bs1.set(i);
						if(values.containsKey(newK)) {
							continue;
						}
						valuesNew.put(newK, bs1);
						valuesNew.put(k, bs);
						if(newK==hSum) {
							break start;
						}
					}
					values = valuesNew;
					valuesNew = new HashMap<>(values);
				}
			}
			bs = values.get(hSum);
			if(bs==null || bs.cardinality()==0 || bs.cardinality()==s.size()) {
				return Collections.emptyList();
			}
			Boolean[] returns = new Boolean[s.size()];
			Arrays.fill(returns, Boolean.FALSE);
			for(int j = 0; j < bs.length(); j++) {
				returns[j] = bs.get(j);
			}
			return Arrays.asList(returns);
		}
		
		return Collections.emptyList();
    }
}
