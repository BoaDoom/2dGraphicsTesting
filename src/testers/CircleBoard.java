package testers;


import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;





class Surface extends JPanel implements ActionListener{
	private final static int TABLE_WIDTH = CircleBoard.TABLE_WIDTH;
	private final static int TABLE_HEIGHT = CircleBoard.TABLE_HEIGHT;

	final static int OUTERCIRCLEYCORD = 45;
	final static int OUTERCIRCLEXCORD = 45;
	final static int OUTERCIRCLEXDIM = 910;
	final static int OUTERCIRCLEYDIM = 710;

	private boolean offOn = false;

	int outerCircleYmovement = 0;
	int xStretch = 0;
//	Double scaleMovement = 0.0;

//	Ellipse2D.Double circleLayerGreen;
	Area tableBackground;		//dark green filler
	Area outsideTable;		//grey area
	Area tableRim;			//light green rim
	Area tableRimRim;			//dark green rim on rim

	Area centerPillar;
	Area centerPillar2;

	Area movingTableTop;
	Area stationaryTopTable;
	Area innerStationaryCircleShade;
	Area innerStationaryCircleShadeHole;


	Area stationaryTopHole;
	Area innerRightSquare;
	Area innerLeftSquare;
	Area rightSideCircleShade;
	Area innerLeftSquareFiller;

//	private Layer1Movement outerCircleAnimator;
//    private Timer timer;
//    private int count;
//    private final int INITIAL_DELAY = 200;
//    private final int DELAY = 80;

    int circleYcord1 = (TABLE_HEIGHT/4);
    int circleYcord2 = 30;

    int tableEdgeYcord = 30;

    public int circleValAdd = 0;

    public Surface() {
        initSurface();
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

    private void doDrawing(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        Color darkGreen = new Color(30,110,30);
        Color lightGreen = new Color(59,154,59);
        Color tableGreen = new Color(39,134,39);
				Color blackGreen = new Color(20,80,20);


        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);







        g2d.setPaint(darkGreen);
				g2d.setPaint(new GradientPaint(0, 0, blackGreen, TABLE_WIDTH/2, 0, darkGreen));
        tableBackground = new Area(new Ellipse2D.Double(OUTERCIRCLEXCORD, OUTERCIRCLEYCORD, OUTERCIRCLEXDIM, OUTERCIRCLEYDIM));
				g2d.fill(tableBackground);

				outsideTable = new Area(new Rectangle2D.Double(0, 0, TABLE_WIDTH, TABLE_HEIGHT));
				outsideTable.subtract(tableBackground);

        g2d.setPaint(lightGreen);
        tableRim = new Area(new Ellipse2D.Double(0, 0, OUTERCIRCLEXDIM+85, OUTERCIRCLEYDIM+85));
        tableRim.subtract(tableBackground);
        g2d.fill(tableRim);

//        AffineTransform old = g2d.getTransform();
//        g2d.shear(0,.1);

//        g2d.setTransform(old);
				g2d.setPaint(new GradientPaint((TABLE_WIDTH/2-TABLE_WIDTH/5), 0, lightGreen, (TABLE_WIDTH/2)+(TABLE_WIDTH/5), 0, darkGreen));
				centerPillar = new Area(new Rectangle2D.Double((TABLE_WIDTH/2-TABLE_WIDTH/5), TABLE_HEIGHT/2.5+4, TABLE_WIDTH/2.5, 300));
				g2d.fill(centerPillar);



				stationaryTopHole = new Area(new Ellipse2D.Double(((TABLE_WIDTH/2)-((TABLE_WIDTH/2.5)/2)) , circleYcord1+outerCircleYmovement , TABLE_WIDTH/2.5, TABLE_HEIGHT/3));
				innerRightSquare = new Area(new Rectangle2D.Double((TABLE_WIDTH/2), TABLE_HEIGHT/2.5+4, TABLE_WIDTH/5, xStretch));
				innerLeftSquare = new Area(new Rectangle2D.Double((TABLE_WIDTH/2-TABLE_WIDTH/5), TABLE_HEIGHT/2.5+4, TABLE_WIDTH/5, xStretch));

				g2d.setPaint(blackGreen);
				tableRimRim = new Area (new Ellipse2D.Double(OUTERCIRCLEXCORD, OUTERCIRCLEYCORD, OUTERCIRCLEXDIM, OUTERCIRCLEYDIM));
				tableRimRim.subtract(new Area (new Ellipse2D.Double(OUTERCIRCLEXCORD, OUTERCIRCLEYCORD+5, OUTERCIRCLEXDIM, OUTERCIRCLEYDIM)));
				g2d.fill(tableRimRim);

				g2d.setPaint(tableGreen);
        movingTableTop = new Area (new Ellipse2D.Double(OUTERCIRCLEXCORD, (OUTERCIRCLEYCORD + outerCircleYmovement), OUTERCIRCLEXDIM, OUTERCIRCLEYDIM));
        movingTableTop.subtract(outsideTable);
        movingTableTop.subtract(stationaryTopHole);
        movingTableTop.subtract(innerRightSquare);
        movingTableTop.subtract(innerLeftSquare);
        g2d.fill(movingTableTop);

        stationaryTopTable = new Area(new Ellipse2D.Double(((TABLE_WIDTH/2)-((TABLE_WIDTH/2.5)/2)), circleYcord1 , TABLE_WIDTH/2.5, TABLE_HEIGHT/3));
        g2d.fill(stationaryTopTable);

        g2d.setPaint(blackGreen);		//shaded rim around the stationary center of the table
        innerStationaryCircleShadeHole = new Area(new Ellipse2D.Double(((TABLE_WIDTH/2)-((TABLE_WIDTH/2.5)/2))+3, circleYcord1+1 , (TABLE_WIDTH/2.5)-6, (TABLE_HEIGHT/3)-6));
        innerStationaryCircleShade = new Area(new Ellipse2D.Double(((TABLE_WIDTH/2)-((TABLE_WIDTH/2.5)/2)), circleYcord1 , TABLE_WIDTH/2.5, TABLE_HEIGHT/3));
        innerStationaryCircleShade.subtract(innerStationaryCircleShadeHole);
        g2d.fill(innerStationaryCircleShade);



        g2d.dispose();
   }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        doDrawing(g);
    }
    @Override
    public void actionPerformed(ActionEvent e) {

        repaint();
//        count++;
    }

    class Layer1Movement implements Runnable {
        private Thread runner;
        private int count = 0;
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
								// if ((outerCircleYmovement >100 || xStretch > 100)){
								// 	outerCircleYmovement -= 1;
								// 	xStretch -= 1;
								// }
								// else if (outerCircleYmovement <0 || xStretch < 0){
								// 	outerCircleYmovement = 0;
								// 	xStretch = 0;
								// }
								// else{
	                if (count <= 100){
	                	outerCircleYmovement += 1;
	                	xStretch += 1;
	                }
	                else if (count > 100 && count <= 200){
	                }
	                else if (count > 200 && count <= 300){
	                	outerCircleYmovement -= 1;
	                	xStretch -= 1;
	                }
	                try {
	                    Thread.sleep(10);
	                } catch (InterruptedException ex) {
	                     Logger.getLogger(Surface.class.getName()).log(Level.SEVERE,
	                             null, ex);
	                }
								// }
            }
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
	final static int TABLE_WIDTH = 1015;
	final static int TABLE_HEIGHT = 835;



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
