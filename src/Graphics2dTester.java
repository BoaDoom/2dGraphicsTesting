//import java.awt.EventQueue;
//import java.awt.Graphics;
//import java.awt.Graphics2D;
//import javax.swing.JFrame;
//import javax.swing.JPanel;
//
//import testers.Surface;
//
//class Surface extends JPanel {
//
//    private void doDrawing(Graphics g) {
//
//        Graphics2D g2d = (Graphics2D) g;
//        g2d.drawString("Java 2D", 10, 50);
//    }
//
//    @Override
//    public void paintComponent(Graphics g) {
//
//        super.paintComponent(g);
//        doDrawing(g);
//    }
//}
//
//public class Graphics2dTester extends JFrame {
//
//    public Graphics2dTester() {
//
//        initUI();
//    }
//
//    private void initUI() {
//
//        add(new Surface());
//
//        setTitle("Simple Java 2D example");
//        setSize(300, 200);
//        setLocationRelativeTo(null);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//    }
//
//    public static void main(String[] args) {
//
//        EventQueue.invokeLater(new Runnable() {
//
//            @Override
//            public void run() {
//                Graphics2dTester ex = new Graphics2dTester();
//                ex.setVisible(true);
//            }
//        });
//    }
//}
