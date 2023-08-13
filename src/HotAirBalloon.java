import processing.core.PApplet;
import processing.core.PImage;

/**
 * Represents the hot air balloon for the player to control
 * @author nicholas
 *
 */
public class HotAirBalloon {
	
	private int xPos, yPos;
	private double xVel, yVel;

	private static final double speed = 0.5, maxVel = 3, percent = 2;
	
	/**
	 * Creates a hot air balloon on the floor in the middle of the screen
	 */
	public HotAirBalloon() {
		reset();
	}
	
	/**
	 * Changes the balloon's velocity upwards
	 */
	public void moveUp() {
		yVel-=speed; 
	}
	
	/**
	 * Changes the balloon's velocity downwards
	 */
	public void moveDown() {
		yVel+=speed;
	}
	
	/**
	 * Draws the hot air balloon
	 * @param marker the PApplet to draw with
	 * @param balloon the balloon image
	 */
	public void draw(PApplet marker, PImage balloon) {
		marker.image(balloon, xPos, yPos, 79, 95);
	}
	
	/**
	 * Makes the hot air balloon move based on it's velocities and based on the winds, 
	 * as well as makes sure the balloon is not moving too fast and is not below the ground
	 * @param atm the Atmosphere to affect the hot air balloon
	 * @param numOfWinds the number of winds in the Atmosphere
	 */
	public void act(Atmosphere atm, int numOfWinds) {	
		double maxVelX = 0;
		//System.out.println("X: " + xPos);
		
		//movement
		xPos+=xVel;
		yPos+=yVel;
		
		//winds
		int yCenter = yPos+47;
		int sectionHeight = 563/numOfWinds;
		int sectionNum = 0;
		for(int i = 1; i < numOfWinds; i++) 
			if (yCenter > sectionHeight*i)
				sectionNum++;
		Wind w = atm.getWind(sectionNum);
		int windSpeed = w.getSpeed();
		maxVelX = windSpeed*maxVel/20*percent;
		if (w.getDirection())
			xVel += maxVelX/(maxVel/speed);
		else
			xVel -= maxVelX/(maxVel/speed);
		
		//terminal velocities
		if (xVel > maxVelX)
			xVel = maxVelX;
		else if (xVel < -maxVelX)
			xVel = -maxVelX;
		if (yVel > maxVel)
			yVel = maxVel;
		else if (yVel < -maxVel)
			yVel = -maxVel;
		
		//the ground
		if (yPos > 486) {
			yPos = 486;
			yVel = 0;
		}
		
	}
	
	/**
	 * Checks the boundaries of the hot air balloon
	 * @return false if the hot air balloon is not within the boundaries of the screen
	 */
	public boolean checkBoundaries() 
	{
		if (xPos < 34 || xPos > 1226 || yPos < -95)
		{
			return false;	
		}
		return true;
	}
	
	/**
	 * Checks if the hot air balloon intersects with the AI balloon
	 * @param a the AIBalloon to check the intersection with
	 * @return true if the balloons intersect
	 */
	public boolean checkIntersection(AIBalloon a) 
	{
		if(a.getX() <= xPos + 60 && a.getX() + 60 > xPos && a.getY() <= yPos + 50 && a.getY() + 60 > yPos)
			return true;
		return false;
	}
	
	/**
	 * Returns the x-coordinate of the hot air balloon
	 * @return x-coordinate of the hot air balloon
	 */
	public int getX()
	{
		return xPos;
	}
	
	/**
	 * Returns the y-coordinate of the hot air balloon
	 * @return y-coordinate of the hot air balloon
	 */
	public int getY()
	{
		return yPos;
	}
	
	/**
	 * Sets the x-coordinate of the hot air balloon
	 * @param x x-coordinate to set the hot air balloon to
	 */
	public void setX(int x)
	{
		xPos = x;
	}
	
	/**
	 * Sets the y-coordinate of the hot air balloon
	 * @param y y-coordinate to set the hot air balloon to
	 */
	public void setY(int y)
	{
		yPos = y;
	}
	
	/**
	 * Resets the hot air balloon and its coordinates
	 */
	public void reset() {
		xPos = 630;
		yPos = 486;
		xVel = 0;
		yVel = 0;
	}
}
