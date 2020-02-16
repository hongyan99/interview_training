package other;

public class Base10ToXConversion {
	private static final char[] SYMBOLS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();
	
	public static void main(String[] args) {
		System.out.println(convert(9));
		System.out.println(convert(60));
		System.out.println(convert(63));
		System.out.println(convert(126));
		System.out.println(convert(485680L));
	}
	
	public static String convert(long input) {
		StringBuilder sb = new StringBuilder();
		sb.append(SYMBOLS[(int)(input%62)]);
		long p = input/62;
		while(p!=0) {
			sb.append(SYMBOLS[(int)(p%62)]);
			p /= 62;
		}
		
		return sb.toString();
		
	}
}
