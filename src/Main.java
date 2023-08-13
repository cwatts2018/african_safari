//import java.awt.BorderLayout;
//import java.awt.Component;
import java.awt.Dimension;
//import java.awt.event.ComponentAdapter;
//import java.awt.event.ComponentEvent;

import javax.swing.JFrame;
//import javax.swing.JPanel;

//import nicholas.illusions.DrawingSurface;
import processing.awt.PSurfaceAWT;
import processing.core.PApplet;

/**
 * Runs the program
 * @author nicholas
 *
 */
public class Main 
{
/*	private JFrame window;
//	private ControlPanel controls;
	private Panel drawing;  // These are PApplets - you use these to do regular processing stuff
	private PSurfaceAWT surf;  // These are the "portals" through which the PApplets draw on the canvas
	//private processingCanvas;  // These are swing components (think of it as the canvas that the PApplet draws on to)

	
	public Main() {
		window = new JFrame();
		
		drawing = new Panel();
		drawing.runMe();
		
		surf = (PSurfaceAWT) drawing.getSurface();
		PSurfaceAWT.SmoothCanvas processingCanvas = (PSurfaceAWT.SmoothCanvas)surf.getNative();
		
		window.setMinimumSize(new Dimension(100,100));
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(true);
		
		JPanel mainPanel = new JPanel();
	    
		mainPanel.addComponentListener(new ComponentAdapter() {

			@Override
			public void componentResized(ComponentEvent arg0) {
				Component x = (Component)arg0.getSource();
				fixProcessingPanelSizes(x);
			}

	    });

//		controls = new ControlPanel(drawing);
	    
		mainPanel.add(processingCanvas);
		
		window.setLayout(new BorderLayout());
		
	    window.add(mainPanel,BorderLayout.CENTER);
//	    window.add(controls, BorderLayout.SOUTH);
	    
	    
	    window.setVisible(true);
	    
	    window.setBounds(20, 20, 1339, 740);
	    
	    processingCanvas.requestFocus();
	}
	
	public void fixProcessingPanelSizes(Component match) {
		surf.setSize(match.getWidth(),match.getHeight());
	}
	

	public static void main(String[] args)
	{
		Main m = new Main();
	}
*/  

	
//	public static void main(String args[])
//	{
//		Panel drawing = new Panel();
//		PApplet.runSketch(new String[]{""}, drawing);
//		PSurfaceAWT surf = (PSurfaceAWT) drawing.getSurface();
//		PSurfaceAWT.SmoothCanvas canvas = (PSurfaceAWT.SmoothCanvas) surf.getNative();
//		JFrame window = (JFrame)canvas.getFrame();
//
//		window.setBounds(20, 20, 1786, 967); //needed as same size for background img
//		window.setMinimumSize(new Dimension(100,100));
//		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		window.setResizable(true);
//
//		window.setVisible(true);
//		canvas.requestFocus();
//	}
	
	/**
	 * Runs the program
	 * @param args
	 */
	public static void main(String args[]) {
		Panel drawing = new Panel();
		PApplet.runSketch(new String[]{""}, drawing);
		PSurfaceAWT surf = (PSurfaceAWT) drawing.getSurface();
		PSurfaceAWT.SmoothCanvas canvas = (PSurfaceAWT.SmoothCanvas) surf.getNative();
		JFrame window = (JFrame)canvas.getFrame();

		window.setBounds(20, 20, 1339, 760);
		window.setMinimumSize(new Dimension(100,100));
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(true);

		window.setVisible(true);
		
		window.setTitle("African Safari");
		
	}
	
}
