//3. Draw a Rectangle using Computer Graphics.

import java.awt.*;
import javax.swing.*;

public class RectangleDraw extends JPanel {

    public void paint(Graphics g) {

        //background color set kora
        g.setColor(Color.black);
        //backgound er size
        g.fillRect(150, 100, 200, 150);  // fill করে

        //border line er color
        g.setColor(Color.blue);
        //ki draw korbo.
        g.drawRect(100, 100, 200, 150);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Draw Rectangle");
        RectangleDraw panel = new RectangleDraw();
        frame.add(panel);
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}