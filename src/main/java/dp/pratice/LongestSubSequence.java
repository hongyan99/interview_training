package dp.pratice;

public class LongestSubSequence {
	public static void main(String[] args) {
		System.out.println(lcs("ABCDE", "AECBD"));
	}
	
	public static String lcs(String a, String b) {
		StringBuilder[][] dp = new StringBuilder[a.length()+1][b.length()+1];
		for(int i = 0; i < a.length()+1; i++) {
			dp[i][b.length()] = new StringBuilder();
		}
		for(int j = 0; j < b.length()+1; j++) {
			dp[a.length()][j] = new StringBuilder();
		}
		lcs(a, b, 0, 0, dp);
		return dp[0][0].length()==0 ? "-1" : dp[0][0].toString();
    }

	private static void lcs(String a, String b, int i, int j, StringBuilder[][] dp) {
		if(dp[i][j]!=null) {
			return;
		}
		
		if(a.charAt(i)==b.charAt(j)) {
			lcs(a, b, i+1, j+1, dp);
			dp[i][j] = new StringBuilder().append(a.charAt(i)).append(dp[i+1][j+1]);
		} else {
			lcs(a, b, i+1, j, dp);
			lcs(a, b, i, j+1, dp);
			if(dp[i+1][j].length() >= dp[i][j+1].length()) {
				dp[i][j] = dp[i+1][j];
			} else {
				dp[i][j] = dp[i][j+1];
			}
		}
	}
}
