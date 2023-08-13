import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;

/**
 * Represents an array of winds
 * @author christine
 *
 */
public class Atmosphere
{
	
	private Wind[] winds;
	
	/**
	 * Creates an atmosphere of different wind speeds
	 * @param num the number of wind speeds in the atmosphere
	 */
	public Atmosphere(int num)
	{
		winds = new Wind[num];
				
		for(int i = 0; i < num; i++)
		{
			winds[i] = new Wind();
		}
		
		boolean check = winds[0].getDirection();
		
		for(int i = 1; i < num; i++)
		{
			if(winds[i].getDirection() != check)
			{
				break; 
			}
			if(i == (num - 1))
			{
				int rand = (int)(Math.random() * num);
				winds[rand].setDirection(!check);
			}
				
		}
	}
	
	/**
	 * Changes speed of all winds
	 */
	public void toggle()
	{
		for(int i = 0; i < winds.length; i++)
		{
			winds[i].changeSpeed();
		}
		
		boolean check = winds[0].getDirection();
		
		for(int i = 1; i < winds.length; i++)
		{
			if(winds[i].getDirection() != check)
			{
				break; 
			}
			if(i == (winds.length - 1))
			{
				int rand = (int)(Math.random() * (winds.length-1) + 1);
				winds[rand].setDirection(!check);
			}
				
		}
	}
	
	/**
	 * Draws the atmosphere
	 * @param marker the PApplet to draw with
	 * @param redEast red east arrow image
 	 * @param yellowEast yellow east arrow image
	 * @param greenEast green east arrow image
	 * @param west west arrow image
	 * @param east east arrow image
	 */
	public void draw(PApplet marker, PImage redEast, PImage yellowEast, PImage greenEast, PImage west, PImage east) 
	{
		marker.fill(0);
		for(int i = 0; i < winds.length; i++)
		{
			String s = getWind(i).getSpeed() + " mph";
			PFont myFont = marker.createFont("Georgia", 15);
			marker.textFont(myFont);
			if (!getWind(i).getDirection()) //west
			{
				marker.text(s, 1339 - 112 + 44, 38 + (i) * 112);
				marker.image(west, 1210, 45 + i*112, 141, 71);
			}
			else //east
			{
				marker.text(s, 15, 38 + (i) * 113);
				if(winds[i].getSpeed() > 15)
					marker.image(redEast, -15, 45 + i*112, 141, 71);
				else if(winds[i].getSpeed() > 9)
					marker.image(yellowEast, -15, 45 + i*112, 141, 71);
				else
					marker.image(greenEast, -15, 45 + i*112, 141, 71);
			}
		}
	}
	
	/**
	 * Returns the wind of the specified index 
	 * @param index The index of the wind to return
	 * @return Wind object
	 */
	public Wind getWind(int index)
	{
		return winds[index];
	}
	
	/**
	 * Resets the Atmosphere so that new winds are created
	 */
	public void reset() {
		for(int i = 0; i < winds.length; i++)
		{
			winds[i] = new Wind();
		}
	}
	
}
