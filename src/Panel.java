//import processing.sound.*;

import java.awt.MediaTracker;
import java.io.File;
import java.util.ArrayList;

import javax.print.DocFlavor.URL;
import javax.print.attribute.standard.Media;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.UnsupportedAudioFileException;

import java.io.File;


import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;

/**
 * Represents the panel to be drawn on
 * @author christine & nicholas
 *
 */
public class Panel extends PApplet 
{
	//images
	private PImage background, westArrowImg, eastArrowImg, balloon, coin, balAIImg, bigEast, medEast, smallEast;
	//game stuff
	private Atmosphere atm;
	private final int numOfWinds = 5;
	private HotAirBalloon b;
	private CoinCollection c;
	private ArrayList<AIBalloon> balAI;
	private ControlPanel cp;
	//sounds
	private Clip ding;
	

	/**
	 * Creates a panel with all the required aspects of the game
	 */
	public Panel()
	{
		b = new HotAirBalloon();
		atm = new Atmosphere(numOfWinds);
		c = new CoinCollection(20, 1339, 740);
		cp = new ControlPanel();
		balAI = new ArrayList<AIBalloon>();
		for(int i = 0; i < 4 ; i++)
		{
			balAI.add(new AIBalloon());
		}	
		try
	    {
			//File f = new File("res/coinSoundClipped2.wav"); 
			//System.out.println(f.exists());

			//AudioInputStream ais = AudioSystem.getAudioInputStream(f);
			//AudioInputStream audio = AudioSystem.getAudioInputStream(
				     //Main.class.getResourceAsStream("coinSoundClipped.mp3"));
			//java.net.URL url = this.getClass().getResource("res/coinSoundClipped2.wav");
			AudioInputStream audio = 
					AudioSystem.getAudioInputStream(this.getClass().getResource("/coinSoundClipped2.wav"));
			ding = AudioSystem.getClip();
			ding.open(audio);
	    }
	    catch (UnsupportedAudioFileException ex)
	    {
	    	System.out.println("Unsupported File.");
	    }
		catch (Exception ex)
	    {
	    	System.out.println("Could not find and open file.");
	    }
	}
	
//	public void runMe() {
//		super.setSize(1339, 740);
//		super.sketchPath();
//		super.initSurface();
//		super.surface.startThread();
//	}

	/**
	 * The setup for the panel
	 */
	public void setup()
	{
//		size(1339, 740);//size of background photo
		background = loadImage("smallerBackground.jpg"); 
		balloon = loadImage("SmallBalloon.png");		
		westArrowImg = loadImage("westarrow.png");
		eastArrowImg = loadImage("eastarrow.png");
		balAIImg = loadImage("AIBalloon.png");
		coin = loadImage("PlusSign.png");
//		ding = new SoundFile(this, "coinSound.mp3");
		frameRate(30);
	}
	
	/**
	 * Draws on the panel
	 */
	public void draw()
	{
		int gameState = cp.getState();
		
		
		//STUFF THAT ALWAYS HAPPENS
		//draw background
		image(background, 0, 0);
		
		//draw balloon
		b.draw(this, balloon);
		
		//draw AI balloons
		for(int i = 0; i < balAI.size(); i++) {
			balAI.get(i).draw(this, balAIImg);
		}
		
		
		
		if (gameState == ControlPanel.START) {
			//move AI balloons NOT SURE IF THESE BALLOONS LOOK TOO WIERD
			for(int i = 0; i < balAI.size(); i++) {
				balAI.get(i).act(atm, numOfWinds);
			}
			
			//toggle winds
			atm.toggle();
		}
		
		
		
		else if (gameState == ControlPanel.PLAY) {
			//move balloon
			b.act(atm, numOfWinds);
			
			//move AI balloons
			for(int i = 0; i < balAI.size(); i++) {
				balAI.get(i).act(atm, numOfWinds);
			}
			
			//draw and collect coins
			c.draw(this, coin);
			c.checkIntersection(b, width, height, ding);
			
			//blue rectangles: width of 112
			fill(82, 171, 239);
			rect(0, 0, 112, 740);
			rect(1339 - 112, 0, 112, 740);
			//wind speeds: 25 pixels down and 150 pixels between		
			fill(0);
			for(int i = 0; i < numOfWinds; i++)
			{
				String s = atm.getWind(i).getSpeed() + " mph";
				PFont myFont = createFont("Georgia", 15);
				textFont(myFont);
				if (!atm.getWind(i).getDirection()) //west
				{
					text(s, 1339 - 112 + 44, 38 + (i) * 112);
					image(westArrowImg, 1210, 45 + i*112, 141, 71);
				}
				else //east
				{
					text(s, 15, 38 + (i) * 113);
					image(eastArrowImg, -15, 45 + i*112, 141, 71);
				}
			}
			//atm.draw(this, bigEast, medEast, smallEast, westArrowImg, eastArrowImg);
			
			//toggle winds
			atm.toggle();
			
			//check boundaries
			if(!b.checkBoundaries())
			{
				cp.endGame(c, ControlPanel.FLEWOFF);
				
//				cp.changeState(ControlPanel.END);
//				cp.setEndState(ControlPanel.FLEWOFF);
//				c.resetRevenue();
			}
			
			//check intersections with AI balloons
			for(int i = 0; i < balAI.size(); i++)
			{
				if(b.checkIntersection(balAI.get(i)))
				{
					cp.endGame(c, ControlPanel.CRASH);
					
//					cp.changeState(ControlPanel.END);
//					cp.setEndState(ControlPanel.CRASH);
//					c.resetRevenue();
				}
			}
		}
		
		
		
		else if (gameState == ControlPanel.PAUSE) {
			//draw coins
			c.draw(this, coin);
			
			//blue rectangles: width of 112
			fill(82, 171, 239);
			rect(0, 0, 112, 740);
			rect(1339 - 112, 0, 112, 740);
			//wind speeds: 25 pixels down and 150 pixels between		
			fill(0);
			for(int i = 0; i < numOfWinds; i++)
			{
				String s = atm.getWind(i).getSpeed() + " mph";
				PFont myFont = createFont("Georgia", 15);
				textFont(myFont);
				if (!atm.getWind(i).getDirection()) //west
				{
					text(s, 1339 - 112 + 44, 38 + (i) * 112);
					image(westArrowImg, 1210, 45 + i*112, 141, 71);
				}
				else //east
				{
					text(s, 15, 38 + (i) * 113);
					image(eastArrowImg, -15, 45 + i*112, 141, 71);
				}
			}
		}
		
		
		
		else if (gameState == ControlPanel.END) {
			//draw coins
			c.draw(this, coin);
			
			//blue rectangles: width of 112
			fill(82, 171, 239);
			rect(0, 0, 112, 740);
			rect(1339 - 112, 0, 112, 740);
			//wind speeds: 25 pixels down and 150 pixels between		
			fill(0);
			for(int i = 0; i < numOfWinds; i++)
			{
				String s = atm.getWind(i).getSpeed() + " mph";
				PFont myFont = createFont("Georgia", 15);
				textFont(myFont);
				if (!atm.getWind(i).getDirection()) //west
				{
					text(s, 1339 - 112 + 44, 38 + (i) * 112);
					image(westArrowImg, 1210, 45 + i*112, 141, 71);
				}
				else //east
				{
					text(s, 15, 38 + (i) * 113);
					image(eastArrowImg, -15, 45 + i*112, 141, 71);
				}
			}
		}
		
		
		
		//draw all control panel stuff! (it always happens, but it has to be drawn after everything else is drawn)
		cp.draw(this, c);
		
//		if(state == ControlPanel.END)
//		{
//			fill(0);
//			rect(518, 210, 300, 150);
//			fill(255, 255, 255);
//			pushStyle();
//			textAlign(CENTER, CENTER);
//			textSize(38);
//			text("GAME OVER", 668, 285);
//			popStyle();
//			controlPanel.changeState(ControlPanel.PAUSE);
//		}
		
	}
	
	/**
	 * When the key is pressed, moves the hot air balloon image
	 */
	public void keyPressed()
	{
		if (key == CODED && cp.getState() == ControlPanel.PLAY) 
		{
		    if (keyCode == UP) 
		     	b.moveUp();
		    else if (keyCode == DOWN) 
		    	b.moveDown();
		}
	}
	
	/**
	 * When the key is released, the hot air balloon acts
	 */
	public void keyReleased() 
	{ 
		if(cp.getState() == ControlPanel.PLAY)
			b.act(atm, numOfWinds);
	}
	
	/**
	 * Checks is the mouse is pressed
	 */
	public void mousePressed() {
		cp.clicked(mouseX, mouseY, this);
	}
	
//	public void togglePaused() {
//		paused = !paused;
//	}
//	
//	public void toggleInstructions() {
//		instructions = !instructions;
//	}
	
	/**
	 * Resets the panel for a new game
	 */
	public void reset() {
		b.reset();
		for(int i = 0; i < balAI.size(); i++)
		{
			balAI.get(i).reset();
		}
		atm.reset();
		c.reset(1339, 740);
	}
}
