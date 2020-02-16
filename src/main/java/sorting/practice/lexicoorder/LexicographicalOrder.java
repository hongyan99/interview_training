package sorting.practice.lexicoorder;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class LexicographicalOrder {
	public static void main(String[] args) {
		String[] input = new String[] {"key1 abcd","key2 zzz","key1 hello","key3 world","key1 hello"};
		System.out.println(Arrays.toString(process(input)));
	}
	
	private static String[] process(String[] arr) {
		Map<String, String> lexico = new HashMap<String, String>();
		Map<String, Integer> count = new HashMap<>();
		for(int i = 0; i < arr.length; i++) {
			StringTokenizer st = new StringTokenizer(arr[i]);
			String key = st.nextToken();
			String value = st.nextToken();
			String oldValue = lexico.get(key);
			if(oldValue==null || value.compareTo(oldValue)>0) {
				lexico.put(key, value);
			}
			count.put(key, count.getOrDefault(key, 0)+1);
		}
		
		String[] returns = new String[count.size()];
		int index = 0;
		for(String key : lexico.keySet()) {
			String value = lexico.get(key);
			returns[index++] = key+":"+count.get(key)+","+value;
		}
		return returns;
	}
}
