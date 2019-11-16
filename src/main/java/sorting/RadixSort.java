package sorting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class RadixSort {
	public static void main(String[] args) {
		String[] arr = {"21", "345", "13", "101", "50", "234", "1"};
		sort(arr, 3);
		System.out.println(Arrays.toString(arr));
	}
	
	private static void sort(String[] arr, int width) {
		for(int d = 0; d < width; d++) {
			sortByDigit(arr, d+1);
		}
	}
	
	private static void sortByDigit(String[] arr, int radix) {
		@SuppressWarnings("unchecked")
		List<String>[] sorted = new ArrayList[10];
		
		for(int i = 0; i < arr.length; i++) {
			String value = arr[i];
			int pos = value.length()-radix;
			int digit = pos<0? 0 : (int)value.charAt(pos)-48;

			List<String> elements = getElementsAt(sorted, digit);
			elements.add(value);
		}
		
		AtomicInteger count = new AtomicInteger();
		for(int i = 0; i < sorted.length && count.get()<arr.length; i++) {
			List<String> elements = sorted[i];
			if(elements!=null) {
				elements.forEach(v->arr[count.getAndIncrement()]=v);
			}
		}
	}

	private static List<String> getElementsAt(List<String>[] sorted, int digit) {
		List<String> elements = sorted[digit];
		if(elements==null) {
			elements = new ArrayList<>();
			sorted[digit] = elements;
		}
		return elements;
	}
}
