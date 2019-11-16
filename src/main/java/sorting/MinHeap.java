package sorting;

import java.util.Arrays;

public class MinHeap {
	private final static int DEFAULT_CAPACITY = 20;
	private Integer[] values;
	private final int capacity;
	private int size;
	
	public static void main(String[] args) {
		int[] arr = new int[] {7, 5, 0, 4, 10, 2};
		MinHeap heap = MinHeap.heapify(arr);
		heap.print();
		heap.pop();
		heap.print();
		heap.pop();
		heap.print();
		heap.pop();
		heap.print();
		heap.pop();
		heap.print();
		heap.pop();
		heap.print();
		heap.pop();
		heap.print();
		heap.pop();
	}
	
	public static MinHeap heapify(int[] arr) {
		int capacity = Math.max(arr.length*2, DEFAULT_CAPACITY);
		MinHeap heap = new MinHeap(capacity);
		for(int i = 0; i<arr.length; i++) {
			heap.insert(arr[i]);
		}
		
		return heap;
	}
	
	public void print() {
		System.out.println(Arrays.toString(values));
	}
	
	public MinHeap() {
		this(DEFAULT_CAPACITY);
	}
	
	public MinHeap(int capacity) {
		this.capacity = capacity;
		this.values = new Integer[this.capacity];
	}

	public static int getDefaultCapacity() {
		return DEFAULT_CAPACITY;
	}

	public int getCapacity() {
		return capacity;
	}

	public int getSize() {
		return size;
	}
	
	public void insert(int newValue) {
		int index = size;
		if(index > capacity*0.8) {
			expand();
		}
		
		values[index] = newValue;
		while(index>0) {
			int parentIndex = (index+1)/2-1;
			if(values[parentIndex]>values[index]) {
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
		int min = values[0];
		this.size--;
		values[0] = values[size];
		values[size]=null;
		int index = 0;
		while(index<size) {
			int left = index*2+1;
			int right = index*2+2;
			int childIndex = getMinIndex(left, right);
			if(childIndex<size && values[index]>values[childIndex]) {
				swap(index, childIndex);
				index = childIndex;
			} else {
				break;
			}
		}
		
		if(size < Math.min(20, capacity*0.3)) {
			shrink();
		}
		return min;
	}
	
	public boolean isEmpty() {
		return size==0;
	}
	
	private int getMinIndex(int left, int right) {
		if(values[right]==null || values[left]<values[right]) {
			return left;
		}
		return right;
	}

	private void expand() {
		Integer[] newValue = new Integer[capacity*2];
		System.arraycopy(values, 0, newValue, 0, size);
		this.values = newValue;
	}
	
	private void shrink() {
		Integer[] newValue = new Integer[Math.max(DEFAULT_CAPACITY, capacity/2)];
		System.arraycopy(values, 0, newValue, 0, size);
		this.values = newValue;
	}

	private void swap(int i, int j) {
		int temp = values[i];
		values[i] = values[j];
		values[j] = temp;
		
	}
}