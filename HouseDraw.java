//7. Draw a House using Computer Graphics.

import java.awt.*;
import javax.swing.*;

public class HouseDraw extends JPanel {

    public void paint(Graphics g) {

        // House Body
        g.drawRect(150, 200, 200, 150);

        // Roof
        int xPoints[] = {150, 250, 350};
        int yPoints[] = {200, 120, 200};
        g.drawPolygon(xPoints, yPoints, 3);

        // Door
        g.drawRect(220, 270, 60, 80);

        // Window
        g.drawRect(170, 230, 50, 50);
        g.drawRect(290, 230, 50, 50);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("House");
        HouseDraw panel = new HouseDraw();
        frame.add(panel);
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}