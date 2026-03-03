//4. Draw a DDA Line using Computer Graphics.

import java.awt.*;
import javax.swing.*;

public class DDALine extends JPanel {

    public void paint(Graphics g) {
        int x1 = 50, y1 = 50;
        int x2 = 300, y2 = 200;

        int dx = x2 - x1;
        int dy = y2 - y1;

        int steps = Math.max(Math.abs(dx), Math.abs(dy));

        float xInc = dx / (float) steps;
        float yInc = dy / (float) steps;

        float x = x1;
        float y = y1;

        for (int i = 0; i <= steps; i++) {
            g.drawString(".", Math.round(x), Math.round(y));
            x += xInc;
            y += yInc;
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("DDA Line");
        DDALine panel = new DDALine();
        frame.add(panel);
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}