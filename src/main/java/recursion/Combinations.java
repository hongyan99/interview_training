package recursion;

public class Combinations {
	public static void main(String[] args) {
		print(new int[] {1,2,3,4,5,6,7});
	}
	
	private static void print(int[] arr) {
		doPrint("", arr, 0);
	}

	private static void doPrint(String slate, int[] arr, int index) {
		if(index==arr.length) {
			System.out.println("{"+slate+"}");
			return;
		}
		doPrint(slate, arr, index+1);
		doPrint(slate+arr[index], arr, index+1);
	}
}
