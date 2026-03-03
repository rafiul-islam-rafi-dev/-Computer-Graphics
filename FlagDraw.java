//6. Draw a Flag using Computer Graphics.

import java.awt.*;
import javax.swing.*;

public class FlagDraw extends JPanel {

    public void paint(Graphics g) {

        g.setColor(Color.GREEN);
        g.fillRect(100, 100, 300, 200);

        g.setColor(Color.RED);
        g.fillOval(200, 150, 100, 100);

        g.setColor(Color.BLACK);
        g.fillRect(80, 80, 20, 400);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Flag");
        FlagDraw panel = new FlagDraw();
        frame.add(panel);
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}