import javax.swing.*; //Swing er class import (ex: JFrame, JPanel)
import java.awt.*; //graphics er sob class import kora hoyeche(ex: Graphics, color, e.t.c.)

// JPanel inherit korlam karon "panel" er opor drawing korbo.
public class TriangleScaling extends JPanel {

    // custom origin point (0,0) = (320,240)
    int originX = 320;
    int originY = 240;

    // Original triangle points. Triangle er 3ta vertex define kora hoyeche. Ex: A(0,50), B(-50,-50), C(50,-50)
    int[] x = {0, -50, 50};
    int[] y = {50, -50, -50};

    // custom-origin er X-coordinate er sathe original-triangle er X-coordinate value add kore dibo.
    int screenX(int x) {
        // origin point er sathe x value add kora hoyeche
        return originX + x;
    }

    // custom-origin er Y-coordinate er sathe original-triangle er Y-coordinate value add kore dibo.
    int screenY(int y) {
        // Java te Y-axis ulta hoy tai minus kora hoyeche
        return originY - y;
    }

    // Triangle draw korar jonno method (original-Triangle and new-Triangle)
    public void drawTriangle(Graphics g, int[] x, int[] y) {
        // 3ta line draw korar jonno loop use kora hoyeche
        for (int i = 0; i < 3; i++) {
            // Next point calculate korar jonno
            int j = (i + 1) % 3;
            // dui point er moddhe line draw kora hocche
            g.drawLine(screenX(x[i]), screenY(y[i]), screenX(x[j]), screenY(y[j]));
        }
    }

    // New-triangle er X-coordinate scale korar value store korar jonno array.
    int[] xs = new int[3];
    // New-triangle er Y-coordinate scale korar value store korar jonno array.
    int[] ys = new int[3];

    // Scaling values.
    double sx = 2.0; // X-axis e koto boro hobe (1 er beshi = boro, 1 er kom = choto)
    double sy = 2.0; // Y-axis e koto boro hobe (1 er beshi = boro, 1 er komi = choto)

    // Constructor
    // Object create hole automatic call hoy
    TriangleScaling() {
        // New Scaling apply korar jonno loop
        for (int i = 0; i < 3; i++) {
            // x-coordinate ke sx diye multiply kora hocche
            xs[i] = (int)(x[i] * sx); // Formula:  x' = x * sx;   newScaled (x') = originalValue (x)   *   scalingValue (sx)
            // y-coordinate ke sy diye multiply kora hocche
            ys[i] = (int)(y[i] * sy); // Formula:  y' = y * sy;   newScaled (y') = originalValue (y)   *   scalingValue (sy)
        }
    }

    /*
     * ei method automatic call hoy jokhon window open hoy
     * "Graphics g" holo drawing tool
     * "g" use kore shape draw kori.
     */
    public void paintComponent(Graphics g) {
        // JPanel clear kore abar draw korar jonno super method call
        super.paintComponent(g);

        // Draw axes (2ta line er color)
        g.setColor(Color.PINK);
        // Horizontal(left-to-right) line draw kore X-axis banano hoyeche
        g.drawLine(0, originY, 640, originY); // X-axis
        // Vertical(up-and-down) line draw kore Y-axis banano hoyeche
        g.drawLine(originX, 0, originX, 480); // Y-axis

        // Original triangle (Green)
        g.setColor(Color.GREEN);
        // Original triangle draw kora hocche.
        drawTriangle(g, x, y);

        // New Scaled triangle (Red)
        g.setColor(Color.RED);
        // New Scaled triangle draw kora hocche.
        drawTriangle(g, xs, ys);
    }

    public static void main(String[] args) {
        // ekti Window create korlam. title "Triangle Scaling" dilam.
        JFrame frame = new JFrame("Triangle Scaling");

        // obj create korlam
        TriangleScaling panel = new TriangleScaling();

        // Obj er background color set korlam
        panel.setBackground(Color.BLACK);

        // obj, frame er majhe add na korle output kichui show korbe na.
        frame.add(panel);
        // frame er Window size
        frame.setSize(640, 480);
        // Close button caple program jeno full off hoy.
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Window visible kora hoyeche noyto window dekhabe na
        frame.setVisible(true);
    }
}
