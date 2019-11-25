package sorting;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

import org.apache.commons.io.IOUtils;

import sorting.practice.ArrayMerger;

/**
 * Problem: <p/>
 * 
 * Given an array of integer arrays, assuming that each individual array is sorted 
 * in ascending order, merge them into one single integer array.
 * 
 * The input arrays can potentially be very large and thus it is given as InputStreams.
 * The output is also expected to be an InputStream.
 * 
 * A similar implementation but works with input as int array is implemented in 
 * {@link ArrayMerger}.
 * 
 * @author Hongyan
 *
 */
public class ArrayStreamMerger {
	public static void main(String[] args) throws IOException {
		InputStream input1 = toStream("2 4 5 7 10");
		InputStream input2 = toStream("1 2 6 20");
		InputStream input3 = toStream("7 8 19");
		InputStream input4 = toStream("  ");
		InputStream result = merge(input1, input2, input3, input4);
		int[] data = IntsBytesConverter.bytesToInts(IOUtils.toByteArray(result));
		System.out.println(Arrays.toString(data));
	}

	private static InputStream toStream(String data) {
		return new ByteArrayInputStream(data.getBytes());
	}
	
//	1. Place element 0 of all arrays in the a min heap, together with each element, reference to the array it belongs to
//	2. Get the min from the heap, add the next element the min belongs to to the heap
//	3. Repeat the above until all elements of all arrays are used.
//	
//	The time complexity of this algorithm is N*K*logK since we have to go through all N*K elements,
//	and each element we insert into the heap takes logK time, fetch an element from the heap is 
//	also logK time. Since this is implemented as a Stream, the time needed to access to each element is delayed to until
//	when it is accessed. This is different from that of the ArrayMerger.
//	
//	The space complexity is the size of the min heap K.	
	private static InputStream merge(InputStream ... arrays) throws IOException {
		MinHeap heap = new MinHeap();
		for(int k = 0; k < arrays.length; k++) {
			Element element = new Element(arrays[k]);
			if(element.hasMoreElement()) {
				heap.insert(element);
			}
		}
		return new MergedIntegerStream(heap);
	}
	
	private static class MergedIntegerStream extends InputStream {
		private final MinHeap heap;
		private byte buf[] = new byte[0];
		private int pos;
		
		public MergedIntegerStream(MinHeap heap) {
			this.heap = heap;
		}
		
		@Override
		public int read() throws IOException {
	        return hasData() ? (buf[pos++] & 0xff) : -1;
		}

		private boolean hasData() throws IOException {
			if(pos < buf.length) {
				return true;
			}

			return fillBuffer(100);
		}

		private boolean fillBuffer(int size) throws IOException {
			int[] data = new int[100];
			int count = 0;
			while(!heap.isEmpty()) {
				data[count++] = heap.pop();
			}
			
			if(count>0) {
				int[] temp = new int[count];
				System.arraycopy(data, 0, temp, 0, count);
				buf = IntsBytesConverter.intsToBytes(temp);
		        pos = 0;
			}
			
			return count>0;
		}
	}
	
	private static class Element {
		private final IntegerStreamReader reader;
		
		public Element(InputStream stream) throws IOException {
			this.reader = new IntegerStreamReader(stream);
		}
		
		public int getValue() throws IOException {
			if(!hasMoreElement()) {
				throw new IndexOutOfBoundsException();
			}
			return reader.nextValue();
		}
		
		public boolean inc() {
			return reader.inc();
		}
		
		public boolean hasMoreElement() throws IOException {
			return reader.hasMoreValue();
		}
	}
	
	private static class IntegerStreamReader {
		private final BufferedReader reader;
		private DataBuffer buffer;
		
		public IntegerStreamReader(InputStream stream) throws IOException {
			this.reader = new BufferedReader(new InputStreamReader(stream));
			fillBuffer();
		}
		
		public boolean hasMoreValue() throws IOException {
			if(buffer.hasMoreValue()) {
				return true;
			}

			return fillBuffer()? buffer.hasMoreValue() : false;
		}
		
		public boolean inc() {
			return buffer.inc();
		}
		
		public int nextValue() throws IOException {
			if(!hasMoreValue()) {
				throw new IndexOutOfBoundsException();
			}
			return buffer.nextValue();
		}
		
		private boolean fillBuffer() throws IOException {
			String nextLine = reader.readLine();
			if(nextLine == null) {
				return false;
			}
			buffer = new DataBuffer(nextLine);
			return true;
		}
	}
	
	private static class DataBuffer {
		private int[] buffer;
		private int pointer;
		
		public DataBuffer(String data) {
			StringTokenizer st = new StringTokenizer(data);
			buffer = new int[st.countTokens()];
			int index = 0;
			while(st.hasMoreTokens()) {
				buffer[index++] = Integer.parseInt(st.nextToken());
			}
		}
		
		public boolean hasMoreValue() {
			return pointer < buffer.length;
		}
		
		public boolean inc() {
			pointer++;
			return hasMoreValue();
		}
		
		public int nextValue() {
			return buffer[pointer];
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
		
		public void insert(Element newValue) throws IOException {
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
		
		public int pop() throws IOException {
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
		
		private int getMinIndex(int left, int right) throws IOException {
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
