package testers;


import java.awt.Color;
import java.awt.EventQueue;
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
	
	int outerCircleYmovement = 0;
	int xStretch = 0;
//	Double scaleMovement = 0.0;
	
//	Ellipse2D.Double circleLayerGreen;
	Area outerCircleTable;
	Area outerCircleBackground;
	Area tableRim;
	Area innerStationaryCircleTop;
	Area innerStationaryCircleShade;
	Area innerStationaryCircleShadeHole;
	
	
	Area innerStationaryCircleTopHole;
	Area innerRightSideSquare;
	Area innerLeftSideSquare;
	Area rightSideCircleShade;
	Area innerLeftSideSquareFiller;
	
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
    		if (innerStationaryCircleTop.contains(x, y)) {
    			/*outerCircleAnimator =*/ new Thread(new Layer1Movement());
    		}
//    		if (innerStationaryCircleTop.contains(x, y)) {
//    			/*Layer2Movement =*/ new Thread(new Layer2Movement());
//    		}
    	}
    	public void run() {
    	}
    }
    
    private void doDrawing(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        
        g2d.setPaint(new Color(30,110,30));
        outerCircleBackground = new Area(new Ellipse2D.Double(OUTERCIRCLEXCORD, OUTERCIRCLEYCORD, OUTERCIRCLEXDIM, OUTERCIRCLEYDIM));
        g2d.fill(outerCircleBackground);
        
        Area insideTableRim = new Area(new Ellipse2D.Double(OUTERCIRCLEXCORD, OUTERCIRCLEYCORD, OUTERCIRCLEXDIM, OUTERCIRCLEYDIM));
        Area everythingOutsideTableRim = new Area(new Rectangle2D.Double(0, 0, TABLE_WIDTH, TABLE_HEIGHT));
        everythingOutsideTableRim.subtract(insideTableRim);
        

        
        g2d.setPaint(new Color(59,154,59));
        tableRim = new Area(new Ellipse2D.Double(0, 0, OUTERCIRCLEXDIM+85, OUTERCIRCLEYDIM+85));
        tableRim.subtract(insideTableRim);
        g2d.fill(tableRim);
        
        AffineTransform old = g2d.getTransform();
        g2d.shear(0,.1);
        g2d.setPaint(new Color(50,150,50));
        rightSideCircleShade = new Area(new Ellipse2D.Double((TABLE_WIDTH/3.8), TABLE_HEIGHT/2 , TABLE_WIDTH/2.5, TABLE_HEIGHT/6));
        g2d.fill(rightSideCircleShade);
        g2d.setTransform(old);
        innerLeftSideSquareFiller = new Area(new Rectangle2D.Double((TABLE_WIDTH/2-TABLE_WIDTH/5), TABLE_HEIGHT/2.5+4, TABLE_WIDTH/5, 300));
        g2d.setPaint(new Color(50,150,50));
        g2d.fill(innerLeftSideSquareFiller);

//        g2d.setPaint(new Color(30,110,30));
//        centerMovingTop = new Ellipse2D.Double(((TABLE_WIDTH/2)-((TABLE_WIDTH/2.5)/2)), (TABLE_HEIGHT/4), TABLE_WIDTH/2.5, TABLE_HEIGHT/3);
//        g2d.fill(centerMovingTop);
//        outerCircleAnimator = new Layer1Movement();
        
        innerStationaryCircleTopHole = new Area(new Ellipse2D.Double(((TABLE_WIDTH/2)-((TABLE_WIDTH/2.5)/2)) , circleYcord1+outerCircleYmovement , TABLE_WIDTH/2.5, TABLE_HEIGHT/3));
        innerRightSideSquare = new Area(new Rectangle2D.Double((TABLE_WIDTH/2), TABLE_HEIGHT/2.5+4, TABLE_WIDTH/5, xStretch));
//        g2d.setPaint(new Color(60,180,60));
//        g2d.fill(innerRightSideSquare);
        
        innerLeftSideSquare = new Area(new Rectangle2D.Double((TABLE_WIDTH/2-TABLE_WIDTH/5), TABLE_HEIGHT/2.5+4, TABLE_WIDTH/5, xStretch));
        g2d.setPaint(new Color(50,150,50));
        g2d.fill(innerLeftSideSquare);
        
        g2d.setPaint(new Color(39,134,39));
        outerCircleTable = new Area (new Ellipse2D.Double(OUTERCIRCLEXCORD, (OUTERCIRCLEYCORD + outerCircleYmovement), OUTERCIRCLEXDIM, OUTERCIRCLEYDIM));
        outerCircleTable.subtract(everythingOutsideTableRim);
        outerCircleTable.subtract(innerStationaryCircleTopHole);
        outerCircleTable.subtract(innerRightSideSquare);
        outerCircleTable.subtract(innerLeftSideSquare);
        g2d.fill(outerCircleTable);
        
        g2d.setPaint(new Color(39,134,39));
        innerStationaryCircleTop = new Area(new Ellipse2D.Double(((TABLE_WIDTH/2)-((TABLE_WIDTH/2.5)/2)), circleYcord1 , TABLE_WIDTH/2.5, TABLE_HEIGHT/3));
        g2d.fill(innerStationaryCircleTop);
        
        g2d.setPaint(new Color(20,80,20));
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
            while (count < 300) {
                repaint();
                count++;
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
            }
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
