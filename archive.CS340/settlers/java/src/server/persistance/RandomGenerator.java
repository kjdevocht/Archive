package server.persistance;

import java.util.Random;

public class RandomGenerator {
	private Random generator;
	private long seed;
	private long generations;
	
	public RandomGenerator()
	{
		this(new Random().nextLong());
	}
	
	public RandomGenerator(long seed)
	{
		this(seed, 0);
	}
	
	public RandomGenerator(long seed, long generations)
	{
		this.setGenerator(new Random(seed));
		this.setSeed(seed);
		this.setGenerations(0);
		
		for(int i =0; i < generations;i++)
		{
			this.nextInt();
		}
	}
	
	/**
	 * Returns the next pseudorandom, uniformly distributed int value from this random number generator's sequence. The general contract of nextInt is that one int value is pseudorandomly generated and returned. All 232 possible int values are produced with (approximately) equal probability. 
	 * @return the next pseudorandom, uniformly distributed int value from this random number generator's sequence.
	 */
	public int nextInt()
	{
		return this.nextInt(Integer.MAX_VALUE);
	}
	
	public int nextInt(int limit)
	{
		int returnVal = this.getGenerator().nextInt(limit);
		this.setGenerations(this.getGenerations() + 1);
		return returnVal;
	}

	/**
	 * @return the generator
	 */
	private Random getGenerator() {
		return generator;
	}

	/**
	 * @param generator the generator to set
	 */
	private void setGenerator(Random generator) {
		this.generator = generator;
	}

	/**
	 * @return the seed
	 */
	public long getSeed() {
		return seed;
	}

	/**
	 * @param seed the seed to set
	 */
	private void setSeed(long seed) {
		this.seed = seed;
	}

	/**
	 * @return the generations
	 */
	public long getGenerations() {
		return generations;
	}

	/**
	 * @param generations the generations to set
	 */
	private void setGenerations(long generations) {
		this.generations = generations;
	}
}
