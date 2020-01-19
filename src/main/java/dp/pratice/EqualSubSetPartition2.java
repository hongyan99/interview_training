package dp.pratice;

import java.util.Arrays;
import java.util.BitSet;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EqualSubSetPartition2 {
	public static void main(String[] args) {
		Integer[] arr = new Integer[] {1, -1};
		List<Boolean> result = equalSubSetSumPartition(Arrays.asList(arr));
		System.out.println(Arrays.toString(result.toArray(new Boolean[result.size()])));
	}
	
	public static List<Boolean> equalSubSetSumPartition(List<Integer> s) {
		// if the total sum is a odd number, it is not possible to divide
		long sum = 0;
		for(int i = 0; i < s.size(); i++) {
			sum += s.get(i);
		}
		long hSum = sum/2;
		if(sum % 2 == 0) {
			Map<Long, BitSet> values = new HashMap<>();
			Map<Long, BitSet> valuesNew = new HashMap<>();
			values.put(0L, new BitSet(s.size()));
			for(int i = 0; i < s.size(); i++) {
				for(Long k : values.keySet()) {
					BitSet bs = values.get(k);
					BitSet bs1 = new BitSet(s.size());
					bs1.or(bs);
					bs1.set(i);
					Long newK = k + s.get(i);
					valuesNew.put(newK, bs1);
					valuesNew.put(k, bs);
					if(newK==hSum) {
						if(bs1.cardinality()==s.size()) {
							return Collections.emptyList();
						}
						Boolean[] returns = new Boolean[s.size()];
						Arrays.fill(returns, Boolean.FALSE);
						for(int j = 0; j < bs1.length(); j++) {
							returns[j] = bs1.get(j);
						}
						return Arrays.asList(returns);
					}
				}
				values = valuesNew;
				valuesNew = new HashMap<>();
			}
		}
		
		return Collections.emptyList();
    }
}
