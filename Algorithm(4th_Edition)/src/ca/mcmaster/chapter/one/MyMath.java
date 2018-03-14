package ca.mcmaster.chapter.one;

public class MyMath {
	public static Boolean isPrime(int N) {
		if (N < 2)
			return false;
		for (int i = 2; i * i <= N; i++) {
			if (N % i == 0)
				return false;
		}
		return true;
	}

	public static Double sqrt(Double C) {
		Double result = C;
		Double error = 1e-15;
		while (Math.abs(result - C / result) > error * result)
			result = (result + C / result) / 2D;
		return result;
	}

	public static Double harmonicSeries(int N) {
		Double sum = new Double(0D);
		for (int i = 1; i <= N; i++) {
			sum += 1D / i;
		}
		return sum;
	}

	public static void main(String[] args) {
		// System.out.println(isPrime(13));
//		System.out.println(sqrt(2D));
		System.out.println(harmonicSeries(1000));
	}
}
