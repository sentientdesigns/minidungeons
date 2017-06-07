package util.statics;

import java.util.Random;

public class RandomNumberManager {
	static Random rand = new Random();
	
	/**
	 * Returns the next pseudorandom, uniformly distributed int value from this 
	 * random number generator's sequence. The general contract of nextInt is 
	 * that one int value is pseudorandomly generated and returned. 
	 * All 2^32 possible int values are produced with (approximately) 
	 * equal probability.
	 * @return random int among all possible int values
	 */
	public static int getRandomInt(){
		return rand.nextInt();
	}
	
	/**
	 * Returns a pseudorandom, uniformly distributed int value between min 
	 * (inclusive) and max (exclusive), drawn from this random 
	 * number generator's sequence.
	 * @param min
	 * @param max
	 * @return random int between min (inclusive) and max (exclusive)
	 */
	public static int getRandomInt(int min, int max){
		if(min==max){ return min; }
		return min + rand.nextInt(max-min);
	}
	
	/**
	 * Returns the next pseudorandom, uniformly distributed double value between 
	 * 0.0 and 1.0 from this random number generator's sequence. 
	 * @param min
	 * @param max
	 * @return random double between 0.0 (inclusive) and 1.0 (exclusive)
	 */
	public static double getRandomDouble(){
		return rand.nextDouble();
	}
	
	/**
	 * Returns a pseudorandom, uniformly distributed double value between min 
	 * (inclusive) and max (exclusive), drawn from this random 
	 * number generator's sequence.
	 * @param min
	 * @param max
	 * @return random double between min (inclusive) and max (exclusive)
	 */
	public static double getRandomDouble(double min, double max){
		if(min==max){ return min; }
		return min + rand.nextDouble()*(max-min);
	}
	
	/**
	 * Returns the next pseudorandom, uniformly distributed double value between 
	 * 0.0 and 1.0 from this random number generator's sequence. 
	 * @param min
	 * @param max
	 * @return random float between 0.0 (inclusive) and 1.0 (exclusive)
	 */
	public static float getRandomFloat(){
		return rand.nextFloat();
	}
	
	/**
	 * Returns a pseudorandom, uniformly distributed float value between min 
	 * (inclusive) and max (exclusive), drawn from this random 
	 * number generator's sequence.
	 * @param min
	 * @param max
	 * @return random float between min (inclusive) and max (exclusive)
	 */
	public static float getRandomFloat(float min, float max){
		if(min==max){ return min; }
		return min + rand.nextFloat()*(max-min);
	}
	
	/**
	 * Returns the next pseudorandom, uniformly distributed boolean value from 
	 * this random number generator's sequence. The general contract of 
	 * nextBoolean is that one boolean value is pseudorandomly generated 
	 * and returned. The values true and false are produced with (approximately)
	 * equal probability. 
	 * @return random float between min (inclusive) and max (exclusive)
	 */
	public static boolean getRandomBoolean(){
		return (rand.nextFloat()>=0.5);
	}

	public static double getRandomGaussian(){
		return rand.nextGaussian();
	}
}
