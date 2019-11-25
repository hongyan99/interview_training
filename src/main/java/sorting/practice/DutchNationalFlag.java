package sorting.practice;

import java.util.Arrays;

public class DutchNationalFlag {
	public static void main(String[] args) {
		char[] input = new char[] {'G', 'B', 'G', 'G', 'R', 'B', 'R', 'G'};
		order(input);
		System.out.println(Arrays.toString(input));
	}
	
	private static void order(char[] balls) {
		int index = -1;
		for(int i = 0; i<balls.length; i++) {
			if(balls[i]=='R') {
				if(index!=-1) {
					char temp = balls[i];
					balls[i] = balls[index];
					balls[index] = temp;
					index++;
					if(balls[index]=='R') {
						index=-1;
					}
				}
			} else {
				if(index==-1) {
					index = i;
				}
			}
		}
		index=-1;
		for(int i = balls.length-1; i>=0&&balls[i]!='R'; i--) {
			if(balls[i]=='B') {
				if(index!=-1) {
					char temp = balls[i];
					balls[i] = balls[index];
					balls[index] = temp;
					index--;
					if(balls[index]=='B') {
						index=-1;
					}
				}
			} else {
				if(index==-1) {
					index = i;
				}
			}
		}
	}
}
