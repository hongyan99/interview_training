package dp.pratice;

import java.util.BitSet;

/**
 * You're given three strings a, b and i. Write a function that checks whether i is an interleaving of a and b.
 * 
 * String i is said to be interleaving string a and b, if:
 * len(i) = len(a) + len(b).
 * * only contains characters present in a or b.
 * * contains all characters of a. From a, any character a[index] should be added exactly once in i.
 * * contains all characters of b. From b, any character b[index] should be added exactly once in i.
 * 
 * Order of all characters in individual strings (a and b) is preserved.
 * @author Hongyan
 *
 */
public class StringInterleave {
	public static void main(String[] args) {
		System.out.println(doStringsInterleave("123", "456", "123456"));
		System.out.println(doStringsInterleave("AAB", "AAC", "AAAACB"));
		System.out.println(doStringsInterleave("ABC", "DEF", "ABCABC"));
	}
	
    public static boolean doStringsInterleave(String a, String b, String i) {
    	if(i.length()!=a.length()+b.length()) {
    		return false;
    	}
    	BitSet[][] failed = new BitSet[a.length()+1][b.length()+1];
		return isInterleave(a.toCharArray(), 0, b.toCharArray(), 0, i.toCharArray(), 0, failed);
    }
    
    private static boolean isInterleave(char[] a, int x, char[] b, int y, char[] i, int z, BitSet[][] failed) {
    	if(x==a.length && y==b.length && z==i.length) {
    		return true;
    	}
    	if(failed[x][y]!=null && failed[x][y].get(z)) {
    		return false;
    	}
    	if(x<a.length && z<i.length && a[x]==i[z]) {
    		boolean returns = isInterleave(a, x+1, b, y, i, z+1, failed);
    		if(returns==true) return true;
    		get(failed, x+1, y, i.length).set(z+1);
    	}
    	
    	if(y<b.length && z<i.length && b[y]==i[z]) {
    		boolean returns = isInterleave(a, x, b, y+1, i, z+1, failed);
    		if(returns==true) return true;
    		get(failed, x, y+1, i.length).set(z+1);
    	}
    	return false;
    }
    
    private static BitSet get(BitSet[][] flags, int x, int y, int size) {
    	BitSet returns = flags[x][y];
    	if(returns ==null) {
    		return flags[x][y]=new BitSet(size);
    	}
    	return returns;
    }

}
