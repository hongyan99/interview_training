package sorting;

import java.util.Arrays;

/**
 * Problem: <p/>
 * 
 * Given an array of integer arrays, assuming that each individual array is sorted 
 * in ascending order, merge them into one single integer array.
 * 
 * The output is expected to be an int array.
 * 
 * A similar implementation but works with input as InputStream array is implemented in 
 * {@link ArrayStreamMerger}.
 * 
 * @author Hongyan
 *
 */
public class ArrayMerger {
	public static void main(String[] args) {
		int[] a1 = new int[] {2, 4, 5, 7, 10};
		int[] result = merge(a1);
		System.out.println(Arrays.toString(result));
		int[] a2 = new int[] {1, 2, 6, 20};
		int[] a3 = new int[] {};
		int[] a4 = new int[] {7, 8, 19};
		result = merge(a1, a2, a3, a4);
		System.out.println(Arrays.toString(result));
	}
	
//	1. Place element 0 of all arrays in the a min heap, together with each element, reference to the array it belongs to
//	2. Get the min from the heap, add the next element the min belongs to to the heap
//	3. Repeat the above until all elements of all arrays are used.
//	
//	The time complexity of this algorithm is N*K*logK since we have to go through all N*K elements,
//	and each element we insert into the heap takes logK time, fetch an element from the heap is 
//	also logK time.
//	
//	The space complexity is the size of the min heap K.	
	private static int[] merge(int[] ... arrays) {
		MinHeap heap = new MinHeap();
		int count = 0;
		for(int k = 0; k < arrays.length; k++) {
			if(arrays[k].length>0) {
				heap.insert(new Element(arrays[k]));
				count += arrays[k].length;
			}
		}
		int[] result = new int[count];
		int index = 0;
		while(!heap.isEmpty()) {
			result[index++] = heap.pop();
		}
		
		return result;
	}
	
	private static class Element {
		private int index = 0;
		private final int[] array;
		
		public Element(int[] array) {
			this.array = array;
		}
		
		public int getValue() {
			if(ended()) {
				throw new IndexOutOfBoundsException();
			}
			return array[index];
		}
		
		public boolean inc() {
			this.index++;
			return !ended();
		}
		
		public boolean ended() {
			return this.index<array.length? false : true;
		}
		
		public String toString() {
			return String.valueOf(getValue());
		}
	}
	
	private static class MinHeap {
		private final static int DEFAULT_CAPACITY = 20;
		private Element[] values;
		private final int capacity;
		private int size;
		
		public MinHeap() {
			this(DEFAULT_CAPACITY);
		}
		
		public MinHeap(int capacity) {
			this.capacity = capacity;
			this.values = new Element[this.capacity];
		}
		
		public void insert(Element newValue) {
			int index = size;
			if(index > capacity*0.8) {
				expand();
			}
			
			values[index] = newValue;
			while(index>0) {
				int parentIndex = (index+1)/2-1;
				if(values[parentIndex].getValue()>values[index].getValue()) {
					swap(index, parentIndex);
				} else {
					break;
				}
				index = parentIndex;
			}
			
			this.size++;
			return;
		}
		
		public int pop() {
			if(isEmpty()) {
				throw new IllegalArgumentException("Heap empty");
			}
			Element min = values[0];
			int value = min.getValue();
			if(!min.inc()) {
				this.size--;
				values[0] = values[size];
				values[size]=null;
			}
			int index = 0;
			while(index<size) {
				int left = index*2+1;
				int right = index*2+2;
				int childIndex = getMinIndex(left, right);
				if(childIndex<size && values[index].getValue()>values[childIndex].getValue()) {
					swap(index, childIndex);
					index = childIndex;
				} else {
					break;
				}
			}
			
			if(size < Math.min(20, capacity*0.3)) {
				shrink();
			}
			return value;
		}
		
		public boolean isEmpty() {
			return size==0;
		}
		
		private int getMinIndex(int left, int right) {
			if(values[right]==null || values[left].getValue()<values[right].getValue()) {
				return left;
			}
			return right;
		}

		private void expand() {
			Element[] newValue = new Element[capacity*2];
			System.arraycopy(values, 0, newValue, 0, size);
			this.values = newValue;
		}
		
		private void shrink() {
			Element[] newValue = new Element[Math.max(DEFAULT_CAPACITY, capacity/2)];
			System.arraycopy(values, 0, newValue, 0, size);
			this.values = newValue;
		}

		private void swap(int i, int j) {
			Element temp = values[i];
			values[i] = values[j];
			values[j] = temp;
			
		}
	}
}
