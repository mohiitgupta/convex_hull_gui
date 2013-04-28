import java.util.*;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
class ConvexHullGraham
{
    public static void main(String args[])
    {
        EventQueue.invokeLater(new Runnable()
        {
            public void run()
                {
                    final Gframe frame=new Gframe();
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.addWindowListener(new WindowListener(){
            			
            			@Override
            			public void windowOpened(WindowEvent e) {
            				// TODO Auto-generated method stub
            								
            			}
            			
            			@Override
            			public void windowIconified(WindowEvent e) {
            				// TODO Auto-generated method stub
            				
            			}
            			
            			@Override
            			public void windowDeiconified(WindowEvent e) {
            				// TODO Auto-generated method stub
            				
            			}
            			
            			@Override
            			public void windowDeactivated(WindowEvent e) {
            				// TODO Auto-generated method stub
            				
            			}
            			
            			@Override
            			public void windowClosing(WindowEvent e)
            			{
            				int confirm = JOptionPane.showOptionDialog(frame,
            		                "Are You Sure to Close this Application?",
            		                "Exit Confirmation", JOptionPane.YES_NO_OPTION,
            		                JOptionPane.QUESTION_MESSAGE, null, null, null);
            				if(confirm==JOptionPane.YES_OPTION)
            					System.exit(0);
            			}
            			
            			@Override
            			public void windowClosed(WindowEvent e) {
            				// TODO Auto-generated method stub
            				
            				
            			}
            			
            			@Override
            			public void windowActivated(WindowEvent e) {
            				// TODO Auto-generated method stub
            				
            			}
            		});
                   
                    frame.setVisible(true);
                }});
        
    }
}
class Gframe extends JFrame
{
    GComponent comp;
    static int pNum=0;
    Gframe()
    {
    setTitle("Convex Hull");
        setBounds(300,0,500,570);
        
          JButton b=new JButton("Draw Convex Hull");
        b.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if(pNum>1)
               comp.grahamsScan();
            }
        });
        JButton r=new JButton("Exit");
        r.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
            	//JOptionPane.showMessageDialog(Gframe,"bbie");
            	Gframe.this .dispose();
            	System.exit(0);
               
            }
        });
       
        JPanel p=new JPanel();
        p.add(b);
        p.add(r);
        p.setBackground(Color.black);
        add(p,BorderLayout.NORTH);
                comp=new GComponent();
                comp.setBackground(Color.GREEN);
        add(comp,BorderLayout.CENTER);
        
    }
    class GComponent extends JComponent
{
    
        int num;
        
	int w =400;
	int h=400;
       // int pNum=1000;
	//Random rnd = new Random();
        int xPoints2[];
        int yPoints2[];
	int xPoints[] = new int[1000];
	int yPoints[] = new int[1000];
        
       /* class clicks extends MouseAdapter
        {
            public void mouseClicked(MouseEvent event)
            {
            	Graphics g;
                xPoints[pNum]=event.getX();
                yPoints[pNum]=event.getY();
               // System.out.println(event.getX()+"   "+event.getY());
                repaint();
                pNum++;
            }
        }*/
    GComponent()
    {
  
        
       this.addMouseListener(new MouseAdapter()
       	{
    	   //@override
    	   public void mouseClicked(MouseEvent event){
    		   Graphics g;
    		   xPoints[pNum]=event.getX();
    		   yPoints[pNum]=event.getY();
    		   repaint();
    		   pNum++;
    		   
    	   }
    	   
       });
       if(pNum>2)
       {
       grahamsScan();
        repaint();}
        
       
    }
    
    public double angle(int o, int a)
    {
	return Math.atan((double)(yPoints[a] - yPoints[o]) / (double)(xPoints[a] - xPoints[o]));
    }
    
    public long distance(int a, int b)
    {
	return ((xPoints[b] - xPoints[a])*(xPoints[b] - xPoints[a]) + (yPoints[b] - yPoints[a])*(yPoints[b] - yPoints[a]));
    }

    public int ccw(int p1, int p2, int p3)
    {
	return (xPoints[p2] - xPoints[p1])*(yPoints[p3] - yPoints[p1]) - (yPoints[p2] - yPoints[p1])*(xPoints[p3] - xPoints[p1]);
    }

    public void swap(int[] stack, int a, int b)
    {
	int tmp = stack[a];
	stack[a] = stack[b];
	stack[b] = tmp;
    }

    class pData implements Comparable<pData>
    {
	int index;
	double angle;
	long distance;

	pData(int i, double a, long d)
	{
	    index = i;
	    angle = a;
	    distance = d;
	}
	// for sorting
	public int compareTo(pData p)
	{
	    if ( this.angle < p.angle )
		return -1;
	    else if ( this.angle > p.angle )
		return 1;
	    else {
		if ( this.distance < p.distance )
		    return -1;
		else if ( this.distance > p.distance )
		    return 1;
	    }
	    return 0;
	}
    }
    public void grahamsScan()
    {
	// random points
	int x, y;
        System.out.println("Input Points");
	for ( int i = 0; i < pNum; i++ ) {
            
	    
            System.out.println(xPoints[i]+" "+yPoints[i]);
		}

	// convex hull routine

	// (0) find the lowest point
	int min = 0;
	for ( int i = 1; i < pNum; i++ ) {
	    if ( yPoints[i] == yPoints[min] ) {
		if ( xPoints[i] < xPoints[min] )
		    min = i;
	    }
	    else if ( yPoints[i] < yPoints[min] )
		min = i;
	}
	//System.out.println("min: " + min);

	ArrayList<pData> al = new ArrayList<pData>();
	double ang;
	long dist;
	// (1) calculate angle and distance from base
	for ( int i = 0; i < pNum; i++ ) {
	    if ( i ==  min )
		continue;
	    ang = angle(min, i);
	    if ( ang < 0 )
		ang += Math.PI;
	    dist = distance(min, i);
	    al.add(new pData(i, ang, dist));
	}
	// (2) sort by angle and distance
	Collections.sort(al);

	//for (Iterator<pData> i=al.iterator(); i.hasNext(); ) {
	//pData data = i.next();
	//System.out.println(data.index + ":" + data.angle + "," + data.distance);
	//}

	// (3) create stack
	int stack[] = new int[pNum+1];
	int j = 2;
	for ( int i = 0; i < pNum; i++ ) {
	    if ( i == min )
		continue;
	    pData data = al.get(j-2);
	    stack[j++] = data.index;
	}
	stack[0] = stack[pNum];
	stack[1] = min;

	// (4)
	int tmp;
	int M = 2;
	for ( int i = 3; i <= pNum; i++ ) {
	    while ( ccw(stack[M-1], stack[M], stack[i]) <= 0 )
		M--;
	    M++;
	    swap(stack, i, M);
	}

	// assign border points
	num = M;
        System.out.println("num="+num);
	xPoints2 = new int[num];
	yPoints2 = new int[num];
        System.out.println("Output");
	for ( int i = 0; i < num; i++ ) {
	    xPoints2[i] = xPoints[stack[i+1]];
	    yPoints2[i] = yPoints[stack[i+1]];
            System.out.println(xPoints2[i]+" "+yPoints2[i]);
	}
        
        repaint();
        
    }
     public void paintComponent(Graphics g)
    {
	// background
    	 g.setColor(Color.GRAY);
    	 g.fillRect(0,0,1300,800);
    		
        if(pNum>2)
        {
	
       
	
	// set of points
	g.setColor(Color.blue);
	//for ( int i = 0; i < pNum; i++ ) {
	   
//	}
	
	// border edge
	g.setColor(Color.black);
	g.drawPolygon(xPoints2, yPoints2, num);
	g.setColor(Color.blue);
	for ( int i = 0; i < pNum; i++ ) {
	    g.fillOval(xPoints[i]-2,yPoints[i]-2, 4,4);
		}

	// border points
	g.setColor(Color.red);
	for ( int i = 0; i < num; i++ ) {
		  g.drawOval(xPoints2[i]-5,yPoints2[i]-5, 10,10);
		}
	}
        
 	   
 	
    }
   }
}