package Testers2;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;

class Surface extends JPanel {
    private Rectangle2D rect;
    private Ellipse2D ellipse;
    private float alpha_rectangle;
    private float alpha_ellipse;
    
    public Surface() {
        initSurface();
    }
    private void initSurface() {
	   	addMouseListener(new HitTestAdapter());

        alpha_rectangle = 20;
        alpha_ellipse = 30;        
    }
    private void doDrawing(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setPaint(new Color(50, 50, 50));
        rect = new Rectangle2D.Float(20, alpha_rectangle, 80, 50);
        ellipse = new Ellipse2D.Float(120, alpha_ellipse, 60, 60);
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        rh.put(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHints(rh);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
        		1));
        g2d.fill(rect);


        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                1));
        
        g2d.fill(ellipse);
        g2d.dispose();
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }
    class RectRunnable implements Runnable {
        private Thread runner;
        private int count = 0;
        public RectRunnable() {
        	initThread();
        }
        private void initThread() {
            runner = new Thread(this);
            runner.start();
        }
        @Override
        public void run() {
            while (alpha_rectangle >= 0) {
                repaint();
                count++;
                if (count <= 10){
                	alpha_rectangle += 5;
                }
                else if (count > 20){
                }
                else{
                    alpha_rectangle -= 5;
                }

                try {
                    Thread.sleep(20);
                } catch (InterruptedException ex) {
                     Logger.getLogger(Surface.class.getName()).log(Level.SEVERE, 
                             null, ex);
                }
            }
        }
    }
    class EllipseAnimator implements Runnable {
        private Thread runner;
        private int count = 0;
        public EllipseAnimator() {
        	initThread();
        }
        private void initThread() {
            runner = new Thread(this);
            runner.start();
        }
        @Override
        public void run() {
            while (alpha_ellipse >= 0) {
                repaint();
                count++;
                if (count <= 10){
                	alpha_ellipse += 5;
                }
                else if (count > 20){
                }
                else{
                	alpha_ellipse -= 5;
                }
                try {
                    Thread.sleep(20);
                } catch (InterruptedException ex) {
                     Logger.getLogger(Surface.class.getName()).log(Level.SEVERE, 
                             null, ex);
                }
            }
        }
    }
    
    class HitTestAdapter extends MouseAdapter
            implements Runnable {
        private Thread rectAnimator;
        private Thread ellipseAnimator;
        @Override
        public void mousePressed(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();
            if (rect.contains(x, y)) {
                rectAnimator = new Thread(new RectRunnable());
            }
            if (ellipse.contains(x, y)) {
                ellipseAnimator = new Thread(new EllipseAnimator());
//                ellipseAnimator.start();
            }
        }
//        @Override
        public void run() {
//            while (alpha_ellipse >= 0) {
//                repaint();
//                alpha_ellipse += 1;
//                if (alpha_ellipse < 0) {
//                    alpha_ellipse = 0;
//                }
//                try {
//                    Thread.sleep(50);
//                } catch (InterruptedException ex) {  
//                    Logger.getLogger(Surface.class.getName()).log(Level.SEVERE, 
//                        null, ex);
//                }
//            }
        }
    }
}

public class tester1 extends JFrame {
    public tester1() {
        add(new Surface());
        setTitle("Hit testing");
        setSize(250, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);           
    }

    public static void main(String[] args) {
        
        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                tester1 ex = new tester1();
                ex.setVisible(true);
            }
        });     
    }
}

