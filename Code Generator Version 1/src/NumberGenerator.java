
public class NumberGenerator {
	static int stringToNum(String num){
		if(num.equalsIgnoreCase("one"))
			return 1;
		else
		if(num.equalsIgnoreCase("two"))
			return 2;
		else
		if(num.equalsIgnoreCase("three"))
			return 3;
		else
		if(num.equalsIgnoreCase("four"))
			return 4;
		else
		if(num.equalsIgnoreCase("five"))
			return 5;
		return 100;
	}

	static String numberToString(int num){
		return null;
	}
	
	static String numberToString(double num){
		return null;
	}
}