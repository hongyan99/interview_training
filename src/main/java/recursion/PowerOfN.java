package recursion;

public class PowerOfN {
	public static void main(String[] args) {
		System.out.println(calc(3,15));
	}
	
	private static long calc(int base, int power) {
		if(power == 0) {
			return 1;
		}
		long result = base;
		int powered = 1;
		while(powered < power/2) {
			powered *= 2;
			result = result*result;
		}
		result *= calc(base, power-powered);
		return result;
	}
}
