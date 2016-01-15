package testers;


import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;


class Surface extends JPanel implements ActionListener{
	private final static int TABLE_WIDTH = CircleBoard.TABLE_WIDTH;
	private final static int TABLE_HEIGHT = CircleBoard.TABLE_HEIGHT;
	
	Ellipse2D.Double circleLayerGreen;
	Ellipse2D.Double centerCircleDarkGreen;
	Ellipse2D.Double centerMovingTop;
	
    private Timer timer;
    private int count;
    private final int INITIAL_DELAY = 200;
    private final int DELAY = 80;
    
    public int circleHeight = (TABLE_HEIGHT/4);
    
    public Surface() {
        initTimer();
    }
    private void initTimer() {
        
        timer = new Timer(DELAY, this);
        timer.setInitialDelay(INITIAL_DELAY);
        timer.start();        
    }
	
    private void doDrawing(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();

        g2d.setPaint(new Color(39,134,39));
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setPaint(new Color(39,134,39));
        circleLayerGreen = new Ellipse2D.Double(15, 15, 970, 770);
        g2d.fill(circleLayerGreen);
        g2d.setPaint(new Color(20,80,20));
        centerCircleDarkGreen = new Ellipse2D.Double(((TABLE_WIDTH/2)-((TABLE_WIDTH/2.5)/2)), circleHeight, TABLE_WIDTH/2.5, TABLE_HEIGHT/3);
        g2d.fill(centerCircleDarkGreen);
        g2d.setPaint(new Color(30,110,30));
        centerMovingTop = new Ellipse2D.Double(((TABLE_WIDTH/2)-((TABLE_WIDTH/2.5)/2)), (TABLE_HEIGHT/4), TABLE_WIDTH/2.5, TABLE_HEIGHT/3);
        g2d.fill(centerMovingTop);
        
        g2d.translate(0,circleHeight);
        circleHeight++;
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
        count++;
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
