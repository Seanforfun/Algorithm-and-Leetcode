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
		if(C < 0) return Double.NaN;
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
	
	private static void call(Test t){
		Test tnew = new Test();
		t.setName("abc");
		System.out.println(t);
		tnew.setName("111");
		t = tnew;
		System.out.println(t);
	}

	public static void main(String[] args) {
		// System.out.println(isPrime(13));
//		System.out.println(sqrt(2D));
//		System.out.println(harmonicSeries(1000));
		
		//Java pass value test
		Test t = new Test();
		System.out.println(t);
		call(t);
		System.out.println(t);
	}
}

class Test{
	String name;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
