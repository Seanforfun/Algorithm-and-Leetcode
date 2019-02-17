package ca.mcmaster.offer;

public class Question5_2 {
	public static String doubleToString(double d){
		if(d <= 0 || d >= 1) return "ERROR";
		StringBuilder binary = new StringBuilder();
		int counter = 0;
		binary.append('.');
		while(d != 0){
			counter++;
			if(counter > 32)	return "ERROR";
			int append = d * 2 >= 1 ? 1:0;
			binary.append(append);
			d = d * 2 - append;
		}
		return binary.toString();
	}
	public static void main(String[] args) {
		System.out.println(doubleToString(0.525d));
		System.out.println(Float.toHexString(0.25f));
	}
}
