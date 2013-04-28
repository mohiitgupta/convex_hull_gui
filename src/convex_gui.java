import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class convex_gui {

	public static void main(String[] args) {

		
		JFrame frame = new buildGui();

		frame.setSize(400, 400);

		frame.setVisible(true);

	}

}

class buildGui extends JFrame {

	Point2D.Double[] inputpoints, f;
	int  status=0;
	int n , cnt=0, i;
	int pts[][] = new int[100][2];
	static JLabel lblx;
	  ArrayList<Point2D.Double> temppoints = new ArrayList<Point2D.Double>(); 
	static JLabel lbly;
int a, b;
	public buildGui() {

		// set properties for the frame

		setTitle("Mouse Co-ordinates");

		setResizable(false);

		setLocationRelativeTo(null);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();

		panel.setLayout(new BorderLayout());

		add(panel);

		lblx = new JLabel("X Co-ordinate:   ");

		panel.add(lblx, BorderLayout.NORTH);

		lbly = new JLabel("Y Co-ordinate:   ");

		panel.add(lbly, BorderLayout.SOUTH);

		this.addMouseListener(new FindLocation(lblx, lbly));

		// add panels to the main panel

		add(panel, BorderLayout.CENTER);
		conhull ch = new conhull();
		
	} // end gui method

	class conhull{
		conhull()
		{
		String inputs =	JOptionPane.showInputDialog(null, "No. of points to be executed");
			n = Integer.parseInt(inputs);
			
			
		}
		
	}
	class FindLocation implements MouseListener {

		public FindLocation(JLabel lbx, JLabel lby) {

			lblx = lbx;

			lbly = lby;

		}

		public void mouseClicked(MouseEvent e) {

			
			
			if(cnt<n)
			{
		
			String textx = lblx.getText();
			int x = e.getX();
			int y = e.getY();
			temppoints.add(new Point2D.Double(x,y)); 	
			a = x; b = y;	
			System.out.println("x = " + x);
			System.out.println("y = " + y);
			repaint();	
			cnt++;
		
			}
			else{
				status = 1;
				inputpoints =  temppoints.toArray(new Point2D.Double[1]);
				f =bruteForceConvexHull(inputpoints);
				//System.out.println("Brute Force Executed ");
				for(int i = 0; i < f.length; i++) {
				  if (f[i] == null) break;
				  System.out.println(f[i].x + ", " + f[i].y);
				  repaint();
				}
				
				

			}
			
			
				
		}

		public void mousePressed(MouseEvent e) {

		}

		public void mouseReleased(MouseEvent e) {

		}

		public void mouseEntered(MouseEvent e) {

		}

		public void mouseExited(MouseEvent e) {

		}

	}
	public void paint(Graphics g)
	{
		//g.drawLine(a, b, a, b);
		if(status ==0)
		{
		g.drawOval(a-1, b-1, 2, 2);
		g.fillOval(a-1, b-1, 2, 2);
		
		lblx.setText("X=" + a);

		lbly.setText("Y=" + b);
		}
		
		else
		{
			int i = 0;
			
			try{
			for(i=1; i<f.length; i++)
			{
			
					if(f[i]==null)
					{
						break;
					}
					g.drawLine((int)f[i-1].x, (int)f[i-1].y, (int)f[i].x, (int)f[i].y);	
				
				
				
			}
			
			g.drawLine((int)f[i-1].x, (int)f[i-1].y, (int)f[0].x, (int)f[0].y);
		}
			catch(Exception e)
			{
				System.out.println("");
			}
			}
			
	}
	
	
	public class component extends JComponent{
		
	}
	
	   public static Point2D.Double [] bruteForceConvexHull(Point2D.Double [] inputpointset) {
		     Point2D.Double[] result = new Point2D.Double[inputpointset.length];
			 
			 
			 Point2D.Double startpoint = inputpointset[0];
			 int i;
			 for (i=1; i < inputpointset.length; i++) {
			   if (startpoint.x > inputpointset[i].x) 
			      startpoint = inputpointset[i];
			 }
			  result[0] = startpoint;

		    
		     int currentvertexindex = 0;
			 Point2D.Double currentvertex = result[currentvertexindex];
			 Point2D.Double pointtocheck = null;
			 boolean done;
			 if (inputpointset.length==1) done = true;
			 else done = false;
			 while (!done) {
			   
			   for (i=0; i < inputpointset.length; i++) { 
				  pointtocheck = inputpointset[i];
				  
				  if (!pointtocheck.equals(currentvertex)) {
				      
				      if (allPointsOnRight(currentvertex, pointtocheck, inputpointset)) {
						  
						  break;
					  }
				   }
			    }
				
				if (pointtocheck.equals(result[0])) done = true;
				else {
			
					currentvertexindex++;
					result[currentvertexindex] = pointtocheck;
					currentvertex = pointtocheck;
				}
			 }
				 
		     return result;
		   }

		  
		   public static boolean allPointsOnRight(Point2D.Double p1, Point2D.Double p2, Point2D.Double[] A) {
			  
			  for (int i=0; i < A.length; i++) {
			     if (determinantformula(p1, p2, A[i]) > 0)  
				    return false;
			  } 	  
			  return true;
		   } 
		   
		   public static double determinantformula(Point2D.Double p1,Point2D.Double p2, Point2D.Double p3) {
		      return (p1.x * p2.y + p3.x * p1.y + p2.x * p3.y 
					- p3.x * p2.y - p2.x * p1.y - p1.x * p3.y);
		   } 
		
} // end GUI class


