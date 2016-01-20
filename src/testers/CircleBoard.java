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
import java.awt.geom.Ellipse2D;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;





class Surface extends JPanel implements ActionListener{
	private final static int TABLE_WIDTH = CircleBoard.TABLE_WIDTH;
	private final static int TABLE_HEIGHT = CircleBoard.TABLE_HEIGHT;
	
//	Ellipse2D.Double circleLayerGreen;
	Ellipse2D.Double ccLayer1;
	Ellipse2D.Double ccLayer2;
	
//	private Layer1Movement rectAnimator;
//    private Timer timer;
//    private int count;
//    private final int INITIAL_DELAY = 200;
//    private final int DELAY = 80;
    
    int circleYcord1 = (TABLE_HEIGHT/4);
    int circleYcord2 = 30;
    
    public int circleValAdd = 0;
    
    public Surface() {
        initSurface();
    }
    private void initSurface() {
	   	addMouseListener(new HitTestAdapter());
        
    }
    class HitTestAdapter extends MouseAdapter implements Runnable {
    	private Thread rectAnimator;
    	private Thread Layer2Movement;
    	@Override
    	public void mousePressed(MouseEvent e) {
    		int x = e.getX();
    		int y = e.getY();
    		if (ccLayer1.contains(x, y)) {
    			rectAnimator = new Thread(new Layer1Movement());
    		}
    		if (ccLayer2.contains(x, y)) {
    			Layer2Movement = new Thread(new Layer2Movement());
    		}
    	}
    	public void run() {
    	}
    }
    
    private void doDrawing(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();

        g2d.setPaint(new Color(39,134,39));
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setPaint(new Color(39,134,39));
        ccLayer1 = new Ellipse2D.Double(15, 15, 970, 770);
        g2d.fill(ccLayer1);
        g2d.setPaint(new Color(20,80,20));
        ccLayer2 = new Ellipse2D.Double(((TABLE_WIDTH/2)-((TABLE_WIDTH/2.5)/2)), circleYcord1 , TABLE_WIDTH/2.5, TABLE_HEIGHT/3);
        g2d.fill(ccLayer2);
//        g2d.setPaint(new Color(30,110,30));
//        centerMovingTop = new Ellipse2D.Double(((TABLE_WIDTH/2)-((TABLE_WIDTH/2.5)/2)), (TABLE_HEIGHT/4), TABLE_WIDTH/2.5, TABLE_HEIGHT/3);
//        g2d.fill(centerMovingTop);
//        rectAnimator = new Layer1Movement();
        g2d.translate(0,circleValAdd);


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
            while (count < 60) {
                repaint();
                count++;
                if (count <= 10){
                	circleYcord1 += 5;
                }
                else if (count > 20){
                }
                else{
                	circleYcord1 -= 5;
                }

                try {
                    Thread.sleep(50);
                } catch (InterruptedException ex) {
                     Logger.getLogger(Surface.class.getName()).log(Level.SEVERE, 
                             null, ex);
                }
            }
        }
    }
    class Layer2Movement implements Runnable {
        private Thread runner;
        private int count = 0;
        public Layer2Movement() {
        	initThread();
        }
        private void initThread() {
            runner = new Thread(this);
            runner.start();
        }
        @Override
        public void run() {
            while (count < 60) {
                repaint();
                count++;
                if (count <= 10){
                	circleYcord2 += 5;
                }
                else if (count > 20){
                }
                else{
                	circleYcord2 -= 5;
                }
                try {
                    Thread.sleep(50);
                } catch (InterruptedException ex) {
                     Logger.getLogger(Surface.class.getName()).log(Level.SEVERE, 
                             null, ex);
                }
            }
        }
    }
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
