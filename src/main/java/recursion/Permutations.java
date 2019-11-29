package recursion;

public class Permutations {
	public static void main(String[] args) {
		print("abcde");
	}
	
	private static void print(String str) {
		doPrint("", str);
	}
	
	private static void doPrint(String slate, String str) {
		if(str.length()==0) {
			System.out.println(slate);
			return;
		}
		for(int i=0; i<str.length(); i++) {
			char[] chars = new char[str.length()-1];
			int k = 0;
			for(int j = 0; j < str.length(); j++) {
				if(j!=i) {
					chars[k++] = str.charAt(j);
				}
			}
			doPrint(slate+str.charAt(i), new String(chars));
		}
	}
}
