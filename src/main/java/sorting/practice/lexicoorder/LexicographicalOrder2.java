package sorting.practice.lexicoorder;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class LexicographicalOrder2 {
	public static void main(String[] args) {
		String[] input = new String[] {"key1 abcd","key2 zzz","key1 hello","key3 world","key1 hello"};
		System.out.println(Arrays.toString(process(input)));
	}
	
	private static String[] process(String[] arr) {
		Map<String, LexicoOrder> tracker = new HashMap<>();
		for(int i = 0; i < arr.length; i++) {
			int idx = arr[i].indexOf(' ');
			String key = arr[i].substring(0, idx).trim();
			LexicoOrder order = tracker.get(key);
			if(order == null) {
				order = new LexicoOrder();
				tracker.put(key, order);
			} 
			order.add(arr[i].substring(idx).trim());
		}
		String[] returns = new String[tracker.size()];
		int idx = 0;
		for(Entry<String, LexicoOrder> entry : tracker.entrySet()) {
			returns[idx++] = entry.getKey()+':'+entry.getValue().count+','+entry.getValue().string;
		}
		return returns;
	}
	
	private static class LexicoOrder {
		String string;
		int count;
		LexicoOrder() {
		}
		
		void add(String str) {
			if(this.string==null || str.compareTo(this.string)>0) {
				this.string = str;
			}
			this.count++;
		}
	}
}
