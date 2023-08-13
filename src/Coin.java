/**
 * Represents a coin that could increase revenue
 * @author christine
 *
 */
public class Coin 
{
	private int x;
	private int y;
	
	/**
	 * Creates a coin at a random location on the screen
	 */
	public Coin()
	{
		changeCoordinates();
	}
	
	/**
	 * Creates a coin at a random coordinate within the boundaries of a certain rectangle
	 * @param x upper left x-coordinate of the rectangle
	 * @param y upper left y-coordinate of the rectangle
	 * @param width width of the rectangle the coin should be in
	 * @param height height of the rectangle
	 */
	public Coin(int x, int y, int width, int height)
	{
		changeCoordinates(x, y, width, height);
	}
	
	/**
	 * Changes the coordinates of the coin randomly to be within the boundaries of a certain rectangle
	 * @param x upper left x-coordinate of the rectangle
	 * @param y upper left y-coordinate of the rectangle
	 * @param width width of the rectangle the coin should be in
	 * @param height height of the rectangle
	 */
	public void changeCoordinates(int x, int y, int width, int height)	
	{
		this.x = (int)(Math.random() * (width) + x);  
		this.y = (int)(Math.random() * (height) + y); 
	}
	
	/**
	 * Changes the coordinates randomly
	 */
	public void changeCoordinates()	
	{
		x = (int)(Math.random() * (1114) + 120); 
		y = (int)(Math.random() * (486) + 19);   
	}
	
	/**
	 * Returns the x-coordinate of the coin
	 * @return x-coordinate of the coin
	 */
	public int getX()
	{
		return x;
	}
	
	/**
	 * Returns the y-coordinate of the coin
	 * @return y-coordinate of the coin
	 */
	public int getY()
	{
		return y;
	}
}
