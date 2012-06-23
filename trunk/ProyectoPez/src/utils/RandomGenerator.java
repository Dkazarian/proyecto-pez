package utils;

public class RandomGenerator {

	
	public static int randomInt(int min, int max){
		int delta = max - min;
		int random = (int)(Math.random()*delta);
		return min + random;
	}
}
