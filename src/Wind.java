/**
 * Represents a certain wind speed going a certain direction
 * @author christine
 *
 */
public class Wind 
{
	private int speed;
	private boolean direction; //true if east; false if west
	
	/**
	 * Creates a wind with a random speed between 5-20 mph and a direction (east or west)
	 */
	public Wind()
	{
		createRandomSpeed();
		createRandomDir();
	}
	
	/**
	 * Changes wind speed and direction randomly - can be 5 mph to 20 mph 
	 */
	public void changeSpeed()
	{
		int rand = (int)(Math.random() * 100 + 1); //random # out of 100
		
		if (rand > 98) //2% chance to change speeds
		{
			speed = (int)(Math.random() * 16 + 5);
			createRandomDir(direction);
		}
	}
	
	/**
	 * Creates wind speed randomly; can be 5 mph to 20 mph
	 */
	public void createRandomSpeed()
	{
		speed = (int)(Math.random() * 16 + 5);
	}
	
	/**
	 * Creates a random direction/boolean (50% chance to return true/east; 50% chance to return false/west)
	 */
	public void createRandomDir()
	{
		int rand = (int)(Math.random() * 2 + 1);
		if (rand == 1)
			direction = true;
		else
			direction = false;
	}
	
	/**
	 * 2% chance to change the direction of the wind
	 * @param prevDir current direction of the wind
	 */
	public void createRandomDir(boolean prevDir)
	{
		int rand = (int)(Math.random() * 100 + 1);
		if (rand < 98)
			direction = prevDir;
		else
			direction = !prevDir;
	}
	
	/**
	 * Returns the speed of the wind
	 * @return speed of the wind
	 */
	public int getSpeed()
	{
		return speed;
	}
	
	/**
	 * Returns the wind's direction
	 * @return true if wind is blowing east; false if west
	 */
	public boolean getDirection()
	{
		return direction;
	}
	
	/**
	 * Sets the direction; true if east - false if west
	 * @param dir the direction of the wind
	 */
	public void setDirection(boolean dir)
	{
		direction = dir;
	}

}
