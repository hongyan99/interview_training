package recursion;

public class BinaryString {
	public static void main(String[] args) {
		long time1 = printDivideConquor(10);
		long time2 = printReduceConquor(10);
		System.out.println("Time1="+time1+ ", Time2="+time2);
	}
	
	private static long printDivideConquor(int size) {
		long start = System.currentTimeMillis();
		printDivide("", size);
		return System.currentTimeMillis()-start;
	}
	
	private static void printDivide(String slate, int n) {
		if(n==0) {
			System.out.println(slate);
		} else {
			printDivide(slate+"0", n-1);
			printDivide(slate+"1", n-1);
		}
	}
	
	private static long printReduceConquor(int size) {
		long start = System.currentTimeMillis();
		String[] result = printReduce(size);
		for(int i = 0; i < result.length; i++) {
			System.out.println(result[i]);
		}
		return System.currentTimeMillis()-start;
	}
	
	private static String[] printReduce(int n) {
		if(n==0) {
			return new String[] {""};
		}
		String[] result = printReduce(n-1);
		String[] returns = new String[result.length*2];
		for(int i = 0; i < result.length; i++) {
			returns[2*i] = result[i]+"0";
			returns[2*i+1] = result[i]+"1";
		}
		
		return returns;
		
	}
}
