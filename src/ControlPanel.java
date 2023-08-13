import java.util.Timer;

import processing.core.PApplet;

/*
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

import processing.awt.PSurfaceAWT;

public class ControlPanel extends JPanel implements ActionListener
{
	
	private Panel panel;
	private JButton pauseButton, instructionsButton, resetButton;
	
	public ControlPanel(Panel drawing)
	{
		panel = drawing;
		
		pauseButton = new JButton("Pause");
		pauseButton.addActionListener(this);
		add(pauseButton);
		instructionsButton = new JButton("How to Play");
		instructionsButton.addActionListener(this);
		add(instructionsButton);
		resetButton = new JButton("Reset");
		resetButton.addActionListener(this);
		add(resetButton);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == pauseButton)
			panel.togglePaused();
		else if (arg0.getSource() == instructionsButton)
			panel.toggleInstructions();
		else if (arg0.getSource() == resetButton)
			panel.reset();
		
		PSurfaceAWT surf = (PSurfaceAWT) panel.getSurface();
		PSurfaceAWT.SmoothCanvas processingCanvas = (PSurfaceAWT.SmoothCanvas) surf.getNative();
		processingCanvas.requestFocus();
	}

}
*/
/**
 * Represents the basic background functions and controls for the game
 * @author nicholas
 *
 */
public class ControlPanel {
	
	private int gameState, buttonsState, endState;
	public static final int START=1, PLAY=2, PAUSE=3, END=4;
	public static final int BUTTONS_NORMAL=1, BUTTONS_INSTRUCTIONS=2, BUTTONS_STATS=3, BUTTONS_QUIT=4;
	public static final int CRASH=1, FLEWOFF=2, SAFE = 3;
	private int time; //based on 30 frames per second
//	private int bestTime; //:)?
	private int gamesPlayed;

	
	
	/**
	 * Creates the control panel
	 */
	public ControlPanel() {
		gameState = START;
		buttonsState = BUTTONS_NORMAL; 
		time = 1800;
		gamesPlayed = 0;
//		bestTime = 0;
		endState = SAFE;
	}
	
	/**
	 * Draws the control panel
	 * @param drawer the PApplet to draw with
	 * @param c the current CoinCollection (that stores the revenue)
	 */
	public void draw(PApplet drawer, CoinCollection c) {
		int revenue = c.getRevenue();
		drawer.pushStyle();
		
		drawer.translate(-40, 0);
		drawScore(drawer, revenue);
		drawer.translate(-40, 0);
		drawTime(drawer);
		drawer.translate(80, 0);
	
		
		if (gameState == START) {
			drawTitle(drawer);
		} else if (gameState == PAUSE) {
			drawPause(drawer);
		} else if (gameState == END) {
			drawGameOver(drawer, revenue);
		} else if (gameState == PLAY) {
			time--;
			if (time <= 0)
				endGame(c, SAFE);
		}
		
		
		if (buttonsState == BUTTONS_NORMAL) {
			drawButtons(drawer);
		} else if (buttonsState == BUTTONS_INSTRUCTIONS) {
			drawInstructions(drawer);
		} else if (buttonsState == BUTTONS_STATS) {
			drawStats(drawer);
		} else if (buttonsState == BUTTONS_QUIT) {
			drawQuit(drawer);
		}
		
		drawer.popStyle();
	}
	
	/**
	 * Draws the buttons 
	 * @param drawer the PApplet to draw with
	 */
	private void drawButtons(PApplet drawer) {
		drawer.fill(255);
		drawer.rect(180, 665, 300, 40, 5);
		drawer.rect(530, 605, 300, 40, 5);
		if (gameState == START)
			drawer.fill(155);
		drawer.rect(530, 665, 300, 40, 5);
		if (gameState == END)
			drawer.fill(155);
		drawer.rect(180, 605, 300, 40, 5);
		
		drawer.textAlign(PApplet.CENTER, PApplet.CENTER);
		drawer.fill(0);
		drawer.textFont(drawer.createFont("rockwell", 20));
		drawer.text("how to play", 680, 625);
		drawer.text("stats", 330, 685);
		if (gameState == START)
			drawer.fill(100);
		drawer.text("quit", 680, 685);
		if (gameState == END)
			drawer.fill(100);
		drawer.text("pause", 330, 625);
	}
	
	/**
	 * Draws the current time left
	 * @param drawer the PApplet to draw with
	 */
	private void drawTime(PApplet drawer)
	{
		drawer.textAlign(PApplet.CENTER);
		drawer.fill(255);
		drawer.textFont(drawer.createFont("rockwell", 40));
		drawer.text("time:", 1200, 640);
		drawer.textFont(drawer.createFont("georgia", 60));
		
		if (time < 300)
			drawer.fill(82, 171, 239);
		drawer.text(timeInMinutes(time), 1200, 690);
		
//		if (time == 1800) {
//			drawer.text("1:00", 1200, 690);
//		} else {
//			int sec = time/30;
//			if (sec >= 10) {
//				drawer.text("0:" + sec, 1200, 690);
//			} else {
//				drawer.fill(255,0,0);
//				drawer.text("0:0" + sec, 1200, 690);
//			}
//		}
	}
	
	/**
	 * Draws the instructions 
	 * @param drawer the PApplet to draw with
	 */
	private void drawInstructions(PApplet drawer) {
		drawer.fill(255);
		drawer.rect(180, 605, 650, 100, 10);
		drawer.textFont(drawer.createFont("rockwell", 14));
		drawer.fill(0);
		drawer.textAlign(PApplet.CENTER, PApplet.TOP);
		String instructions = "You are a hot air balloon tour guide, trying to show your tourists the best possible views!";
		drawer.text(instructions, 505, 615);
		drawer.textFont(drawer.createFont("georgia", 14));
		drawer.textAlign(PApplet.LEFT, PApplet.TOP);
		instructions = "~use the up and down arrow keys to move\n~the winds will move you left and right"
				+ "\n~collect plus signs to increase your revenue";
		drawer.text(instructions, 190, 635);
		drawer.strokeWeight(1.5f);
		drawer.line(485, 635, 485, 695);
		instructions = "~the tour ends once time runs out"
				+ "\n~don\'t crash into other balloons or fly off the screen!";
		drawer.text(instructions, 500, 635);
		drawer.text("(click to return)", 725, 681);
	}
	
	/**
	 * Draws the stats of the player 
	 * @param drawer the PApplet to draw with
	 */
	private void drawStats(PApplet drawer) {
		drawer.fill(255);
		drawer.rect(180, 605, 650, 100, 10);
		drawer.textFont(drawer.createFont("georgia", 15));
		drawer.fill(0);
		drawer.textAlign(PApplet.LEFT, PApplet.TOP);
		String stats = "highest revenue: $" + CoinCollection.getHighScore() + "\ngames played: " + gamesPlayed;
//				+ "\nlongest time survived: " + timeInMinutes(bestTime);
		drawer.text(stats, 190, 612);
		drawer.text("(click to return)", 720, 681);
	}
	
	/**
	 * Draws the title screen
	 * @param drawer the PApplet to draw with
	 */
	private void drawTitle(PApplet drawer) {
		drawer.textAlign(PApplet.CENTER);
		drawer.textFont(drawer.createFont("rockwell", 80));
		drawer.fill(0);
		drawer.text("African Safari!", 670, 100);
		drawer.textFont(drawer.createFont("georgia", 20));
		drawer.text("click anywhere to start", 670, 130);
	}
	
	/**
	 * Draws the game over screen
	 * @param drawer the PApplet to draw with
	 * @param revenue the current revenue of the player
	 */
	private void drawGameOver(PApplet drawer, int revenue) {
		if (endState == SAFE) {
			drawer.fill(82, 171, 239);
			drawer.rect(450, 210, 436, 150, 10);
			drawer.fill(0);
			drawer.textAlign(PApplet.CENTER, PApplet.CENTER);
			drawer.textFont(drawer.createFont("rockwell", 40));
			drawer.text("TOUR COMPLETED", 668, 250);
			drawer.textFont(drawer.createFont("rockwell", 15));
			drawer.text("YOU DID IT!", 668, 290);
			drawer.textFont(drawer.createFont("georgia", 20));
			drawer.text("final revenue: $" + revenue, 668, 318);
			drawer.textSize(10);
			drawer.text("(click anywhere to restart)", 823, 350);
		} 
		else {
			drawer.fill(0);
			drawer.rect(518, 210, 300, 150, 10);
			drawer.fill(255, 255, 255);
			drawer.textAlign(PApplet.CENTER, PApplet.CENTER);
			drawer.textFont(drawer.createFont("rockwell", 40));
			drawer.text("GAME OVER", 668, 250);
			drawer.textFont(drawer.createFont("rockwell", 15));
			if(endState == CRASH)
				drawer.text("YOU CRASHED!", 668, 290);
			else if(endState == FLEWOFF)
				drawer.text("YOU FLEW OFF!", 668, 290);
			drawer.textFont(drawer.createFont("georgia", 20));
			drawer.text("no revenue earned", 668, 318);
			drawer.textSize(10);
			drawer.text("(click anywhere to restart)", 755, 350);
		}
	}
	
	/**
	 * Draws the pause screen 
	 * @param drawer the PApplet to draw with
	 */
	private void drawPause(PApplet drawer) {
		drawer.textAlign(PApplet.CENTER);
		drawer.textFont(drawer.createFont("rockwell", 40));
		drawer.fill(0);
		drawer.text("paused", 668, 265);
		drawer.textFont(drawer.createFont("georgia", 20));
		drawer.text("(click anywhere to continue)", 668, 310);
	}
	
	/**
	 * Draws the score 
	 * @param drawer the PApplet to draw with
	 * @param revenue the current revenue of the player
	 */
	private void drawScore(PApplet drawer, int revenue) {
		drawer.textAlign(PApplet.CENTER);
		drawer.fill(255);
		drawer.textFont(drawer.createFont("rockwell", 40));
		drawer.text("revenue:", 990, 640);
		drawer.textFont(drawer.createFont("georgia", 60));
		drawer.text("$" + revenue, 990, 690);
		
	}
	
	/**
	 * Draws the quit button 
	 * @param drawer the PApplet to draw with
	 */
	private void drawQuit(PApplet drawer) {
		drawer.fill(255);
		drawer.rect(180, 605, 650, 100, 10);
		drawer.rect(180, 665, 300, 40, 5);
		drawer.rect(530, 665, 300, 40, 5);
		drawer.fill(0);
		drawer.textFont(drawer.createFont("georgia", 20));
		drawer.textAlign(PApplet.CENTER);
		drawer.text("Are you sure you want to quit and go back to the home screen?", 505, 640);
		drawer.textAlign(PApplet.CENTER, PApplet.CENTER);
		drawer.textFont(drawer.createFont("rockwell", 20));
		drawer.text("quit", 330, 685);
		drawer.text("return", 680, 685);
		
	}
	
	/**
	 * Does specified actions of the buttons if the mouse clicked on one
	 * @param mouseX x-coordinate of the mouse
	 * @param mouseY y-coordinate of the mouse
	 * @param panel the Panel object the mouse clicked on 
	 */
	public void clicked(int mouseX, int mouseY, Panel panel) {
		int buttonClicked = getButtonClicked(mouseX, mouseY);
		if (buttonClicked == 1) {
			if (buttonsState == BUTTONS_NORMAL) {
				if (gameState == PLAY) {
					gameState = PAUSE;
				} else if (gameState == PAUSE) {
					gameState = PLAY;
//				} else if (gameState == END) {
//					resetAtStart(panel, true);
//					gameState = PLAY;
				}
			} else {
				buttonsState = BUTTONS_NORMAL;
			}
		} 
		
		else if (buttonClicked == 2) {
			if (buttonsState == BUTTONS_NORMAL) {
				buttonsState = BUTTONS_INSTRUCTIONS;
			} else {
				buttonsState = BUTTONS_NORMAL;
			}
		} 
		
		else if (buttonClicked == 3) {
			if (buttonsState == BUTTONS_NORMAL) {
				buttonsState = BUTTONS_STATS;
			} else if (buttonsState == BUTTONS_QUIT) {
				gameState = START;
				buttonsState = BUTTONS_NORMAL;
				resetAtStart(panel, false);
			} else {
				buttonsState = BUTTONS_NORMAL;
			}
		} 
		
		else if (buttonClicked == 4) {
			if (buttonsState == BUTTONS_NORMAL) {
				if (gameState != START) {
					buttonsState = BUTTONS_QUIT;
				}
			} else {
				buttonsState = BUTTONS_NORMAL;
			}
		} 
		
		else if (buttonClicked == 5) {
			if (buttonsState != BUTTONS_NORMAL) {
				buttonsState = BUTTONS_NORMAL;
			} else {
				if (gameState == START || gameState == PAUSE || gameState == END) {
					if (gameState != PAUSE)
						resetAtStart(panel, true);
					gameState = PLAY;
				} 
				else {
					gameState = PAUSE;
				}
			}
		} 
		
		else {
			if (gameState == START || gameState == PAUSE || gameState == END) {
				if (gameState != PAUSE)
					resetAtStart(panel, true);
				gameState = PLAY;
			}
			else {
				gameState = PAUSE;
			}
		}
	}
	
	/**
	 * Determines which button is clicked
	 * @param mouseX the x-coordinate of the mouse
	 * @param mouseY the y-coordinate of the mouse
	 * @return the number of the corresponding button clicked
	 */
	private int getButtonClicked(int mouseX, int mouseY) { //key: 1 = pause, 2 = instructions, 3 = stats, 4 = quit, 5 = area of all 4 buttons, 6 = anywhere else
		if (mouseX>180 && mouseX<480 && mouseY>605 && mouseY<645) 
			return 1;
		else if (mouseX>530 && mouseX<830 && mouseY>605 && mouseY<645) 
			return 2;
		else if (mouseX>180 && mouseX<480 && mouseY>665 && mouseY<705) 
			return 3;
		else if (mouseX>530 && mouseX<830 && mouseY>665 && mouseY<705) 
			return 4;
		else if (mouseX>180 && mouseX<830 && mouseY>605 && mouseY<705) 
			return 5;
		else 
			return 6;
	}
	
	/**
	 * Changes the state of the game
	 * @param state the number of the corresponding state of the game
	 */
	public void changeState(int state) {
		gameState = state;
	}
	
	/**
	 * Gets the current state of the game
	 * @return the number of the corresponding state of the game
	 */
	public int getState() {
		return gameState;
	}
	
	/**
	 * Resets the ControlPanel for a new game
	 * @param panel the Panel to be reset
	 * @param isNewGame true if this reset is for the start of a new game (false means this is a reset back to the title screen)
	 */
	private void resetAtStart(Panel panel, boolean isNewGame) {
		if (isNewGame)
			gamesPlayed++;
		time = 1800;
		panel.reset();
	}
	
	/**
	 * 
	 * @param c
	 * @param endState
	 */
	public void endGame(CoinCollection c, int endState) {
		gameState = END;
		this.endState = endState;
		if (endState == SAFE)
			c.checkForNewHighScore();
//		if (1800-time > bestTime)
//			bestTime = 1800-time;
	}
	
	/**
	 * Converts a given time into basic clock format
	 * @param time the time to convert, based on 30 frames per second
	 * @return the converted time as a String; minutes and seconds separated by a colon
	 */
	private String timeInMinutes(int time) {
		int seconds = time/30;
		int minutes = seconds/60;
		seconds -= minutes*60;
		
		if (seconds >= 10) 
			return minutes + ":" + seconds;
		else 
			return minutes + ":0" + seconds;
	}
	
}

