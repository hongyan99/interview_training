package dp.pratice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.List;

public class EqualSubSetPartition {
	public static void main(String[] args) {
		Integer[] arr = new Integer[] {100, -100, 99, -99, 22, -22};
		List<Boolean> result = equalSubSetSumPartition(Arrays.asList(arr));
		System.out.println(Arrays.toString(result.toArray(new Boolean[result.size()])));
	}
	
	public static List<Boolean> equalSubSetSumPartition(List<Integer> s) {
		// if the total sum is a odd number, it is not possible to divide
		List<Boolean> result = new ArrayList<Boolean>();
		long sum = 0;
		for(int i = 0; i < s.size(); i++) {
			sum += s.get(i);
		}
		if(sum % 2 == 0) {
			BitSet flags = new BitSet(s.size());
			flags.set(0);
			flags = split(1, flags, s.get(0), sum/2, s);
			if(flags!=null) {
				for(int i = 0; i < s.size(); i++) {
					result.add(flags.get(i));
				}
				return result;
			}
		}
		
		return result;
    }

	private static BitSet split(int index, BitSet flags, long sum, long hsum, List<Integer> s) {
		if(sum==hsum && flags.cardinality()!=s.size()) {
			return flags;
		}
		
		if(index==s.size()) {
			return null;
		}

		BitSet newFlags = split(index+1, flags, sum, hsum, s);
		if(newFlags!=null) {
			return newFlags;
		}
		
		flags.set(index);
		flags.clear(index+1, flags.size());
		sum +=s.get(index);
		return split(index+1, flags, sum, hsum, s);
	}
}
