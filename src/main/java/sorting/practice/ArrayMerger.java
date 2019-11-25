package sorting.practice;

import java.util.Arrays;

import sorting.ArrayStreamMerger;

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
		int[] a1 = new int[] {34, 26, 20, 13, 11, 7, 4, 4};
		int[] a2 = new int[] {41, 34, 27, 23, 19, 10, 8, 0};
		int[] a3 = new int[] {26, 25, 19, 12, 7, 7, 7, 5};
		int[] a4 = new int[] {46, 39, 35, 33, 27, 19, 12, 9};
		int[] a5 = new int[] {33, 24, 22, 18, 18, 10, 3, 0};
		int[] a6 = new int[] {42, 35, 35, 30, 21, 20, 12, 9};
		int[] a7 = new int[] {42, 33, 24, 21, 12, 12, 8, 7};
		int[] a8 = new int[] {29, 23, 21, 18, 18, 11, 8, 7};
		int[] a9 = new int[] {35, 30, 30, 23, 15, 14, 8, 7};
		int[] a10 = new int[] {20, 18, 17, 16, 12, 11, 5, 4};
		int[] result = merge(a1, a2, a3, a4,a5,a6,a7,a8,a9,a10);
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
	private static int[] merge(int[] ... arr) {
		int dir = 0;
		int kk = 0;
		while(dir==0 && kk<arr.length) {
			dir = getDirection(arr[kk++]);
		}

		MinHeap heap = new MinHeap();
		int count = 0;
		for(int k = 0; k < arr.length; k++) {
			if(arr[k].length>0) {
				if(getDirection(arr[k])>0) {
					reverse(arr[k]);
				}
				heap.insert(new Element(arr[k]));
				count += arr[k].length;
			}
		}
		int[] result = new int[count];
		int index = 0;
		while(!heap.isEmpty()) {
			result[index++] = heap.pop();
		}

		if(dir>0) {
			reverse(result);
		}
		return result;
	}
	
	private static int getDirection(int[] arr) {
		if(arr.length==0) return 0;
		if(arr[0]==arr[arr.length-1]) {
			return 0;
		} else if(arr[0]>arr[arr.length-1]) {
			return 1;
		} else {
			return -1;
		}
	}
	
	private static void reverse(int[] a) {
		if(a.length>0) {
			for(int i=0; i<a.length; i++) {
				int temp = a[i];
				a[i] = a[a.length-i-1];
				a[a.length-i-1] = temp;
			}
		}
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
