//5. Draw a Bresenham Line using Computer Graphics.

import java.awt.*;
import javax.swing.*;

public class BresenhamLine extends JPanel {

    public void paint(Graphics g) {

        int x1 = 50, y1 = 50;
        int x2 = 300, y2 = 200;

        int dx = x2 - x1;
        int dy = y2 - y1;

        int p = 2 * dy - dx;
        int x = x1;
        int y = y1;

        while (x <= x2) {
            g.drawString(".", x, y);
            x++;

            if (p < 0) {
                p = p + 2 * dy;
            } else {
                y++;
                p = p + 2 * dy - 2 * dx;
            }
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Bresenham Line");
        BresenhamLine panel = new BresenhamLine();
        frame.add(panel);
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}