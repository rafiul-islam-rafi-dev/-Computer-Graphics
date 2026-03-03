//2. Draw a Circle using Computer Graphics.

import java.awt.*;
import javax.swing.*;

public class CircleDraw extends JPanel {

    public void paint(Graphics g) {
        g.drawOval(100, 100, 150, 150);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Draw Circle");
        CircleDraw panel = new CircleDraw();
        frame.add(panel);
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}