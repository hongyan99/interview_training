package dp.pratice;

public class WordDistance {
	public static void main(String[] args) {
		System.out.println(levenshteinDistance("masilanidbny", "zwujtimkexcgvxrgkp"));
	}
	
	static int levenshteinDistance(String strWord1, String strWord2) {
		int[][] mem = new int[strWord1.length()][strWord2.length()];
		for(int i = 0; i < mem.length; i++) {
			for(int j = 0; j < mem[i].length; j++) {
				mem[i][j] = -1;
			}
		}
		return distance(strWord1, strWord2, 0, 0, mem);
    }
	
	private static int distance(String w1, String w2, int i, int j, int[][] mem) {
		if(i==w1.length() && j==w2.length()) {
			// if both w1 and w2 ended, the minimum steps needed is 0.
			return 0;
		}
		if(i==w1.length()) {
			// if w1 has extra chars, the minimum steps is to deleted the remaining in w1
			return w2.length()-j;
		}
		if(j==w2.length()) {
			// if w2 has extra chars, the minimum steps is to insert the remaining in w2 to the end of w1
			return w1.length()-i;
		}
		
		if(w1.charAt(i)==w2.charAt(j)) {
			// if the current char are the same, we can move forward one char on both w1 and w2 without any step
			return distance(w1, w2, i+1, j+1, mem);
		}
		
		if(mem[i][j]>-1) {
			return mem[i][j];
		}
		// deleted char at i from w1
		int delete = distance(w1, w2, i+1, j, mem);
		// insert w2.charAt(j) into w1 at current index
		int insert = distance(w1, w2, i, j+1, mem);
		// replace char at current index of w1 with that at current index of w2
		int replace = distance(w1, w2, i+1, j+1, mem);
		// all of the above takes one single step, we only want to minimum+1
		mem[i][j] = Math.min(replace, Math.min(delete, insert))+1;
		return mem[i][j];
	}
}
