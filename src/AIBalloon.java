import java.util.Random;

/**
 * Represents an AI hot air balloon
 * @author christine
 *
 */
public class AIBalloon extends HotAirBalloon
{	
	private Random rand;
	
	/**
	 * Creates an AI Balloon randomly on the edges of the screen
	 */
	public AIBalloon()
	{
		super();
	}
	
	/**
	 * Moves the AI balloon so it has some sense of where to go on the screen
	 * (50% chance it will move)
	 * @param atm the Atmosphere that affects the AI Balloon's movement
	 * @param numOfWinds the numOfWinds in the Atmosphere
	 */
	public void act(Atmosphere atm, int numOfWinds)
	{
		super.act(atm, numOfWinds);
		
		int x = getX();
		int y = getY();
		int position = 0; //depends on array position of wind in atm that balloon is on
		int sectionHeight = 563/numOfWinds;
		for(int i = 1; position == 0 && i <= numOfWinds; i++)
		{
			if(x < sectionHeight * i)
				position = i-1;
		}

		//int rand = (int)(Math.random() * 100  + 1);
		int myRand = rand.nextInt(486) + 1;
		if(getY() < 15 && myRand % 2 == 0) //if closing in on top
			moveDown();
		if(getY() > 471 && myRand % 2 == 0) //if closing in on bottom
			moveUp();
		if(getX() < 263 && myRand % 2 == 0) //if closing in on left
		{
			int check = 0;
			for(int i = position-1; i >= 0; i--)
			{
				if(atm.getWind(i).getDirection() == true)
				{
					moveUp();
					check = 1;
				}
			}
			if(check == 0)
				moveDown();
		}
//		else if(getX() < 350 && rand <= 50)
		if(getX() > 1050 && myRand % 2 == 0) //if closing in on right
		{
			int check = 0;
			for(int i = position-1; i >= 0; i--)
			{
				if(atm.getWind(i).getDirection() == false)
				{
					moveUp();
					check = 1;
				}
			}
			if(check == 0)
				moveDown();
		}
//		else if(getX() > 1400 && rand <= 50)
//			moveUp();
	}
	
	/**
	 * Resets the AI balloon so it spawns randomly on the edges of the screen
	 */
	public void reset()
	{ 
		//int rand = (int)(Math.random() * 100 + 1);
		//int rand2 = (int)(Math.random() * 648 + 1);
		rand = new Random();
		int myRand = rand.nextInt(486) + 1;
		//System.out.println(myRand);
	    setY(myRand);
		if(myRand % 2 == 0)
			setX(38);
		else
			setX(1226);
	
	}
}
