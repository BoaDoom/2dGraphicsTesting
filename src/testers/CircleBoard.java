package testers;


import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import org.w3c.dom.css.Rect;





class Surface extends JPanel implements ActionListener{
	// TABLE_WIDTH = 1015;
	//TABLE_HEIGHT = 835;

	private final static int TABLE_WIDTH = CircleBoard.TABLE_WIDTH;
	private final static int TABLE_HEIGHT = CircleBoard.TABLE_HEIGHT;

	final static double DOT_SIZE = 30;

	final static double ANIMATIONTIME = (int) (TABLE_HEIGHT*.36);
	final static double Y_TRAVEL_INCREMENTS = TABLE_HEIGHT/835;

	//circle of the table, not including the table rim
	final static double ALL_INSIDE_TABLE_XCORD = 0.044*TABLE_WIDTH; //gap left for the table rim
	final static double ALL_INSIDE_TABLE_YCORD = 0.034*TABLE_HEIGHT;
	final static double ALL_INSIDE_TABLE_XDIM = TABLE_WIDTH*.90; //compensation for leaving even spacing for rim
	final static double ALL_INSIDE_TABLE_YDIM = TABLE_HEIGHT*.85; //compensation
	final static double ALL_INSIDE_TABLE_XRADIUS = ALL_INSIDE_TABLE_XDIM/2;
	final static double ALL_INSIDE_TABLE_YRADIUS = ALL_INSIDE_TABLE_YDIM/2;

	final static double ROTATING_TABLETOP_XCORD = ALL_INSIDE_TABLE_XCORD-(DOT_SIZE/2); //moving the starting position underneith the rim
	final static double ROTATING_TABLETOP_YCORD = ALL_INSIDE_TABLE_YCORD-(DOT_SIZE/2);
	final static double ROTATING_TABLETOP_XDIM = ALL_INSIDE_TABLE_XDIM+DOT_SIZE; //expanding the tabletop so the rim overlaps ontop of it
	final static double ROTATING_TABLETOP_YDIM = ALL_INSIDE_TABLE_YDIM+DOT_SIZE;
	final static double ROTATING_TABLETOP_XCORD_CENTER = ROTATING_TABLETOP_XCORD-ROTATING_TABLETOP_XDIM/2;
	final static double ROTATING_TABLETOP_YCORD_CENTER = ROTATING_TABLETOP_YCORD-ROTATING_TABLETOP_YDIM/2;;


	//cords for the rim of the table, based on the inner circle
	final static double RIM_XDIM = ALL_INSIDE_TABLE_XDIM*1.09;
	final static double RIM_YDIM = ALL_INSIDE_TABLE_YDIM*1.1;

	//center pillar box, acts like the cylinder
	final double CENTER_CIRCLE_XDIM = TABLE_WIDTH/2.5;
	final double CENTER_CIRCLE_YDIM = TABLE_HEIGHT/3;
	final double CENTER_CIRCLE_XCORD = (TABLE_WIDTH/2)-(CENTER_CIRCLE_XDIM/2); //mid point on table minus the radius of the center circle
	final double CENTER_CIRCLE_YCORD = yPerspectiveCord(ALL_INSIDE_TABLE_YCORD + ALL_INSIDE_TABLE_YRADIUS - CENTER_CIRCLE_YDIM/2);//TABLE_HEIGHT/4;

	final  double CENTER_PILLAR_XCORD = CENTER_CIRCLE_XCORD;
	final  double CENTER_PILLAR_YCORD = CENTER_CIRCLE_YCORD+(CENTER_CIRCLE_YDIM/2);//TABLE_HEIGHT/2.5;	//cords for the inner cylinder
	final  double CENTER_PILLAR_XDIM = CENTER_CIRCLE_XDIM;
	final  double CENTER_PILLAR_YDIM = CENTER_CIRCLE_YDIM-(CENTER_CIRCLE_YDIM/2);

	final double MIDDLE_XCORD = CENTER_CIRCLE_XCORD+CENTER_CIRCLE_XDIM/2;//((ALL_INSIDE_TABLE_XCORD*.70)+((ALL_INSIDE_TABLE_XDIM+23)/2));//ALL_INSIDE_TABLE_XCORD+ALL_INSIDE_TABLE_XRADIUS;
	final double MIDDLE_YCORD = CENTER_CIRCLE_YCORD+CENTER_CIRCLE_YDIM/2;//((ALL_INSIDE_TABLE_YCORD)+((ALL_INSIDE_TABLE_YDIM+22)/2));//ALL_INSIDE_TABLE_YCORD+ALL_INSIDE_TABLE_YRADIUS;





	private boolean offOn = false;

	int outerCircleYmovement = 0;
	int yStretch = 0;
//	public int MiddleXCord_Movement = TABLE_WIDTH/2 + outerCircleXmovement;
	public int MiddleYCord_Movement = TABLE_HEIGHT/2 + outerCircleYmovement;

	public double rotatingDotXcord = 0;
	public double rotatingDotYcord = 0;

	public double testDotXcord = ROTATING_TABLETOP_XCORD+(ROTATING_TABLETOP_XDIM/2)-DOT_SIZE/2;
	public double testDotYcord = ROTATING_TABLETOP_YCORD;
	public double testDotYMovement = 0;
//	Double scaleMovement = 0.0;

//	Ellipse2D.Double circleLayerGreen;
	Area allInsideOfRim;		//dark green filler
	Area greyOutside;		//grey area
	Area tableRim;			//light green rim
	Area tableRimRim;			//dark green rim on rim

	Area centerPillar;
	Area centerPillar2;

	Area rotatingTableTop;
	Area stationaryTopTable;
	Area innerStationaryCircleShade;
	Area innerStationaryCircleShadeHole;


	Area stationaryTopHole;
	Area innerRightSquare;
	Area pillarSubtractSquare;
	Area rightSideCircleShade;
	Area pillarSubtractSquareFiller;

	Area rotatingDot;
	Area testDot;

//	private Layer1Movement outerCircleAnimator;
  private Timer timer;
  private int count;

  private final int DELAY = 100;


	public Surface(){
  	initSurface();
	}

	private void initTimer() {
		timer = new Timer(DELAY, this);
		timer.start();
  }
  private void initSurface() {
	 	addMouseListener(new HitTestAdapter());
  }
  class HitTestAdapter extends MouseAdapter implements Runnable {
//    	private Thread outerCircleAnimator;
//    	private Thread Layer2Movement;
		@Override
		public void mousePressed(MouseEvent e) {
			int x = e.getX();
			int y = e.getY();
			if (stationaryTopTable.contains(x, y) && offOn == false) {
				/*outerCircleAnimator = new Thread(*/new Layer1Movement();
			offOn = true;
			}
	//    		if (stationaryTopTable.contains(x, y)) {
	//    			/*Layer2Movement =*/ new Thread(new Layer2Movement());
	//    		}
		}
	  public void run() {
	  }
	}
	// public class TranArea extends Area {
	// 	TranArea(){
	// 	}
	// }
		private void doDrawing(Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();
		Color darkGreen = new Color(30,110,30);
		Color lightGreen = new Color(59,154,59);
		Color tableGreen = new Color(39,134,39);
		Color blackGreen = new Color(20,80,20);
		Color lightTan = new Color(180,120,80);
		Color tan = new Color(140,100,60);
		Color darktan = new Color(120,80,40);


		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
		      RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
		      RenderingHints.VALUE_RENDER_QUALITY);



		g2d.setPaint(darkGreen);
		g2d.setPaint(new GradientPaint(0, 0, blackGreen, TABLE_WIDTH/2, 0, darkGreen));
		allInsideOfRim = new Area(new Ellipse2D.Double(ALL_INSIDE_TABLE_XCORD, ALL_INSIDE_TABLE_YCORD, ALL_INSIDE_TABLE_XDIM, ALL_INSIDE_TABLE_YDIM));
		g2d.fill(allInsideOfRim);

		greyOutside = new Area(new Rectangle2D.Double(0, 0, TABLE_WIDTH, TABLE_HEIGHT));
		greyOutside.subtract(allInsideOfRim);


		//        AffineTransform old = g2d.getTransform();
		//        g2d.shear(0,.1);

		//        g2d.setTransform(old);
		g2d.setPaint(new GradientPaint((int)(TABLE_WIDTH*.3), 0, lightGreen, (int)(TABLE_WIDTH*.8), 0, darkGreen));
		centerPillar = new Area(new Rectangle2D.Double(CENTER_PILLAR_XCORD, CENTER_PILLAR_YCORD, CENTER_PILLAR_XDIM, CENTER_PILLAR_YDIM+yStretch));
		centerPillar.subtract(greyOutside);
		g2d.fill(centerPillar);



		stationaryTopHole = new Area(new Ellipse2D.Double(CENTER_CIRCLE_XCORD , CENTER_CIRCLE_YCORD+outerCircleYmovement , CENTER_CIRCLE_XDIM, CENTER_CIRCLE_YDIM));
		pillarSubtractSquare = new Area(new Rectangle2D.Double(CENTER_PILLAR_XCORD, CENTER_PILLAR_YCORD, CENTER_PILLAR_XDIM, yStretch));

		g2d.setPaint(blackGreen);
		tableRimRim = new Area (new Ellipse2D.Double(ALL_INSIDE_TABLE_XCORD, ALL_INSIDE_TABLE_YCORD, ALL_INSIDE_TABLE_XDIM, ALL_INSIDE_TABLE_YDIM));
		tableRimRim.subtract(new Area (new Ellipse2D.Double(ALL_INSIDE_TABLE_XCORD, ALL_INSIDE_TABLE_YCORD+5, ALL_INSIDE_TABLE_XDIM, ALL_INSIDE_TABLE_YDIM)));
		g2d.fill(tableRimRim);

		rotatingDot = new Area(new Ellipse2D.Double(rotatingDotXcord, rotatingDotYcord+outerCircleYmovement, DOT_SIZE, DOT_SIZE));
		g2d.setPaint(tableGreen);
		rotatingTableTop = new Area (new Ellipse2D.Double(ROTATING_TABLETOP_XCORD, (ROTATING_TABLETOP_YCORD + outerCircleYmovement), ROTATING_TABLETOP_XDIM,ROTATING_TABLETOP_YDIM));
		rotatingTableTop.subtract(greyOutside);
		rotatingTableTop.subtract(stationaryTopHole);
		rotatingTableTop.subtract(pillarSubtractSquare);
		rotatingTableTop.subtract(rotatingDot);
		g2d.fill(rotatingTableTop);

		stationaryTopTable = new Area(new Ellipse2D.Double(CENTER_CIRCLE_XCORD, CENTER_CIRCLE_YCORD , CENTER_CIRCLE_XDIM, CENTER_CIRCLE_YDIM));
		g2d.fill(stationaryTopTable);

		g2d.setPaint(blackGreen);		//shaded rim around the stationary center of the table
		innerStationaryCircleShadeHole = new Area(new Ellipse2D.Double(CENTER_CIRCLE_XCORD +2, CENTER_CIRCLE_YCORD+1 , CENTER_CIRCLE_XDIM-6, CENTER_CIRCLE_YDIM-6));
		innerStationaryCircleShade = new Area(new Ellipse2D.Double(CENTER_CIRCLE_XCORD, CENTER_CIRCLE_YCORD , CENTER_CIRCLE_XDIM, CENTER_CIRCLE_YDIM));
		innerStationaryCircleShade.subtract(innerStationaryCircleShadeHole);
		g2d.fill(innerStationaryCircleShade);


		g2d.setPaint(tan);
		tableRim = new Area(new Ellipse2D.Double(0, 0, RIM_XDIM, RIM_YDIM));
		tableRim.subtract(allInsideOfRim);
		g2d.fill(tableRim);

		g2d.setPaint(blackGreen);
		// testDot = new Area(new Ellipse2D.Double(testDotXcord, yPerspectiveCord(testDotYcord + testDotYMovement), DOT_SIZE, yPerspectiveDim((testDotYcord + testDotYMovement), DOT_SIZE)));
		// g2d.fill(testDot);
		// // g2d.setPaint(blackGreen);
		// // g2d.fill(rotatingDot);

		// Area testRec = new Area(new Rectangle2D.Double(300,300,50, 75));
		Path2D.Double path = new Path2D.Double(new Rectangle2D.Double(MIDDLE_XCORD-25,yPerspectiveCord((MIDDLE_YCORD-37)+300),50, yPerspectiveDim(yPerspectiveCord((MIDDLE_YCORD-37)+300),75)));
		// path.append(testRec, false);
		AffineTransform testATrans = new AffineTransform();
		testATrans.rotate(Math.toRadians(testDotYMovement/2), MIDDLE_XCORD, MIDDLE_YCORD);
		path.transform(testATrans);
		g2d.fill(path);
		g2d.draw(path);

		g2d.dispose();
	}
	public void circleMovement(double radius){
		rotatingDotXcord = (ROTATING_TABLETOP_XCORD+(ROTATING_TABLETOP_XDIM/2)-(DOT_SIZE/2))+((ROTATING_TABLETOP_XDIM/2))*Math.cos(radius); //(center of circle) + (radius of circle)*cos(radians rotated)
		// rotatingDotXcord = ((ALL_INSIDE_TABLE_XCORD*.70)+(ALL_INSIDE_TABLE_XDIM+23)/2)+((ALL_INSIDE_TABLE_XDIM+23)/2)*Math.cos(radius); //(center of circle) + (radius of circle)*cos(radians rotated)
		rotatingDotYcord = (ROTATING_TABLETOP_YCORD+(ROTATING_TABLETOP_YDIM/2)-(DOT_SIZE/2))+((ROTATING_TABLETOP_YDIM/2))*Math.sin(radius);
		// rotatingDotYcord = MIDDLEYCORD+((ALL_INSIDE_TABLE_YDIM+22)/2)*Math.sin(radius);
	}
	// public double yPerspectiveCord(double cordLocation, double cordToChange){
	// 	//Full size is center, smallest is at ROTATING_TABLETOP_YCORD, largest is at ROTATING_TABLETOP_YCORD + ROTATING_TABLETOP_YDIM. Smallest is 75%, largest is 125%.
	// 	//ROTATING_TABLETOP_YCORD is 50%
	// 	//ROTATING_TABLETOP_YCORD_CENTER is 75%
	// 	//ROTATING_TABLETOP_YDIM is 100%
	// 	cordToChange = ((((cordLocation-ROTATING_TABLETOP_YCORD)/ROTATING_TABLETOP_YDIM)*.5)+.5)*cordToChange;
	// 	return cordToChange;
	// }
	public double yPerspectiveCord(double cordToChange){
		//Full size is center, smallest is at ROTATING_TABLETOP_YCORD, largest is at ROTATING_TABLETOP_YCORD + ROTATING_TABLETOP_YDIM. Smallest is 75%, largest is 125%.
		//ROTATING_TABLETOP_YCORD is 50%
		//ROTATING_TABLETOP_YCORD_CENTER is 75%
		//ROTATING_TABLETOP_YDIM is 100%
		cordToChange = ((((cordToChange-ROTATING_TABLETOP_YCORD)/ROTATING_TABLETOP_YDIM)*.5)+.5)*cordToChange; //(NonAngledCord - ROTATING_TABLETOP_YCORD)
		return cordToChange;
	}
	public double yPerspectiveDim(double cordLocation, double dimToChange){
		dimToChange = ((((cordLocation-ROTATING_TABLETOP_YCORD)/ROTATING_TABLETOP_YDIM)*.5)+.5)*dimToChange;
		return dimToChange;
	}


    @Override
    public void paintComponent(Graphics g) {
      super.paintComponent(g);
      doDrawing(g);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
      repaint();
    }

    class Layer1Movement implements Runnable {
      private Thread runner;
      private int count = 0;
      private double subcounter = 3.15;
      public Layer1Movement() {
      	initThread();
      }
      private void initThread() {
        runner = new Thread(this);
        runner.start();
      }
      @Override
      public void run() {
          while (count < 300 ) {
              repaint();
              count++;

							// if ((outerCircleYmovement >100 || yStretch > 100)){
							// 	outerCircleYmovement -= 1;
							// 	yStretch -= 1;
							// }
							// else if (outerCircleYmovement <0 || yStretch < 0){
							// 	outerCircleYmovement = 0;
							// 	yStretch = 0;
							// }
							// else{
							// circleMovementX(.0003333);
                if (count <= ANIMATIONTIME*.333){
                	outerCircleYmovement += TABLE_HEIGHT/835;
                	yStretch += 1;
									testDotYMovement += ROTATING_TABLETOP_YDIM/100;
                }
                else if (count > ANIMATIONTIME*.333 && count <= ANIMATIONTIME*.666){
									subcounter += 0.01575; //0.021 is full rotation
									circleMovement(subcounter);
                }
                else if (count > ANIMATIONTIME*.666 && count <= ANIMATIONTIME*.999){
                	outerCircleYmovement -= 1;
                	yStretch -= 1;
									testDotYMovement -= ROTATING_TABLETOP_YDIM/101;
                }
                try {
                    Thread.sleep(10);
                } catch (InterruptedException ex) {
                     Logger.getLogger(Surface.class.getName()).log(Level.SEVERE,
                             null, ex);
                }
							// }
          }
					yStretch = 0;
					outerCircleYmovement =0;
					repaint();
					offOn = false;
      }
    }
//    class Layer2Movement implements Runnable {
//        private Thread runner;
//        private int count = 0;
//        public Layer2Movement() {
//        	initThread();
//        }
//        private void initThread() {
//            runner = new Thread(this);
//            runner.start();
//        }
//        @Override
//        public void run() {
//            while (count < 60) {
//                repaint();
//                count++;
//                if (count <= 10){
//                	circleYcord2 += 1;
//                }
//                else if (count > 20){
//                }
//                else{
//                	circleYcord2 -= 1;
//                }
//                try {
//                    Thread.sleep(50);
//                } catch (InterruptedException ex) {
//                     Logger.getLogger(Surface.class.getName()).log(Level.SEVERE,
//                             null, ex);
//                }
//            }
//        }
//    }
}



public class CircleBoard extends JFrame {
	final static int TABLE_WIDTH = 1015;//1015;
	final static int TABLE_HEIGHT = 835;//835;



	Surface surface;

	Ellipse2D.Double circleLayerOne;
    public CircleBoard() {

        initUI();
    }

    private void initUI() {
        surface = new Surface();
        surface.setBackground(new Color(150, 150, 150));
        add(surface);

        setTitle("Basic shapes");
        setSize(TABLE_WIDTH, TABLE_HEIGHT);
        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {

                CircleBoard ex = new CircleBoard();
                ex.setVisible(true);
            }
        });
    }
}
