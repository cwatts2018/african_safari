import java.util.ArrayList;

import javax.sound.sampled.Clip;

import processing.core.PApplet;
import processing.core.PImage;
import processing.sound.SoundFile;

/**
 * Represents a collection of coins
 * @author christine
 *
 */
public class CoinCollection
{
	private ArrayList<Coin> coins = new ArrayList<Coin>();
	private int revenue;
	private static int highScore = 0;
	private int numOfCoins;
	
	/**
	 * Creates a collection of coins all with completely random coordinates
	 * @param num the number of coins in the collection
	 */
	public CoinCollection(int num)
	{
		numOfCoins = num;
		for(int i = 0; i < num; i++)
		{
			coins.add(new Coin());
		}
		revenue = 0;
	}
	
	/**
	 * Creates a collection of coins spread out around the screen
	 * @param num the number of coins in the collection
	 * @param width the width of the screen
	 * @param height the height of the screen
	 */
	public CoinCollection(int num, int width, int height)
	{
		numOfCoins = num;
		for(int i = 0; i < num/2; i++)
		{
//			System.out.println(i);
			coins.add(new Coin((i) * (width-300)/(num/2) + 113, 0, (width-300)/(num/2), 243));
		}
		for(int i = num/2 + 1; i <= num; i++)
		{
//			System.out.println("2nd loop: " + i);
			coins.add(new Coin((i-num/2-1) * (width-300)/(num/2) + 113, 243, (width-300)/(num/2), 243));
		}
//		for(int i = 0; i < num; i++)
//		{
//			System.out.println(coins.get(i).toString());
//		}
		revenue = 0;
	}
	
	/**
	 * Draws the collection of coins on the screen
	 * @param marker the PApplet to draw with
	 * @param coin the image of a coin
	 */
	public void draw(PApplet marker, PImage coin)
	{
		for(int i = 0; i < coins.size(); i++)
		{
			marker.image(coin, coins.get(i).getX(), coins.get(i).getY());
		}
	}
	
	/**
	 * Checks if a coin in the collection intersects with the hot air balloon
	 * @param balloon the balloon to check if a coin intersects with
	 * @param width the width of the screen
	 * @param height the height of the screen
	 */
	public void checkIntersection(HotAirBalloon balloon, int width, int height, Clip collectSound)
	{
		int bX = balloon.getX();
		int bY = balloon.getY();
		
		for(int i = 0; i < coins.size(); i++)
		{
			Coin current = coins.get(i);
			int cX = current.getX();
			int cY = current.getY();
			//115; 140   left, right, top, bottom
			if(cX+15 >= bX && cX <= bX+68 && cY+15 >= bY && cY+30 <= bY+105) //if balloon intersects with a coin
			{ 
				if(i <= numOfCoins/2)
					current.changeCoordinates();
					//current.changeCoordinates((i) * (width-400)/(numOfCoins/2) + 150, 0, (width-400)/(numOfCoins/2), 324);
				else
					current.changeCoordinates();
					//current.changeCoordinates((i-numOfCoins/2-1) * (width-400)/(numOfCoins/2) + 150, 324, (width-400)/(numOfCoins/2), 324);
				collectSound.setFramePosition(0);
				collectSound.start();
				
				revenue += 5;
				
			}
		}
	}
	
	/**
	 * Resets the coins so that all of the coins' coordinates are changed within the screen
	 * @param width the width of the screen
	 * @param height the height of the screen
	 */
	public void reset(int width, int height) {
		for(int i = 0; i < coins.size(); i++)
		{
			coins.get(i).changeCoordinates();
		}
		revenue = 0;
	}
	
	/**
	 * Returns the total revenue collected from the coins
	 * @return total revenue
	 */
	public int getRevenue() {
		return revenue;
	}
	
	/**
	 * Resets the total revenue collected to zero
	 */
	public void resetRevenue() {
		revenue = 0;
	}
	/**
	 * Returns the highest score obtained
	 * @return the highest score
	 */
	public static int getHighScore() {
		return highScore;
	}
	
	/**
	 * Checks and records if the current score is a high score
	 */
	public void checkForNewHighScore() {
		if (revenue > highScore)
			highScore = revenue;
	}
}
