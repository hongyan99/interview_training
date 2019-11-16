package sorting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class CountingSort {
	public static void main(String[] args) {
		int[] arr = new int[] {7, 5, 0, 4, 0, 4, 10, 2};
		sort(arr, 20);
		System.out.println(Arrays.toString(arr));
	}
	
	private static void sort(int[] arr, int range) {
		@SuppressWarnings("unchecked")
		List<Integer>[] sorted = new ArrayList[range];
		for(int i = 0; i < arr.length; i++) {
			List<Integer> elements = sorted[arr[i]];
			if(elements == null) {
				elements = new ArrayList<>();
				sorted[arr[i]] = elements;
			}
			elements.add(arr[i]);
		}
		
		AtomicInteger count = new AtomicInteger();
		for(int i = 0; i < sorted.length && count.get() < sorted.length; i++) {
			List<Integer> elements = sorted[i];
			if(elements!=null) {
				elements.forEach(v->arr[count.getAndIncrement()]=v);
			}
		}
	}
}
