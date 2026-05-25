import javax.swing.*; //Swing er class import (ex: JFrame, JPanel)
import java.awt.*; //graphics er sob class import kora hoyeche(ex: Graphics, color, e.t.c.)

// JPanel inherit korlam karon "panel" er opor drawing korbo.
public class TriangleReflection extends JPanel {

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

    // X-axis reflection er jonno array (Y-axis e mirror)
    int[] xRefX = new int[3]; // X-axis reflection: x same thake
    int[] yRefX = new int[3]; // X-axis reflection: y er sign change hoy

    // Y-axis reflection er jonno array (X-axis e mirror)
    int[] xRefY = new int[3]; // Y-axis reflection: x er sign change hoy
    int[] yRefY = new int[3]; // Y-axis reflection: y same thake

    // Constructor
    // Object create hole automatic call hoy
    TriangleReflection() {
        for (int i = 0; i < 3; i++) {

            // ---- X-axis er sathe reflection ----
            // X-axis e reflect korle x same thake, y er sign change hoy (negative hoy)
            xRefX[i] =  x[i];       // Formula:  x' = x;    x same thake
            yRefX[i] = -y[i];       // Formula:  y' = -y;   y er value negative hoy

            // ---- Y-axis er sathe reflection ----
            // Y-axis e reflect korle x er sign change hoy (negative hoy), y same thake
            xRefY[i] = -x[i];       // Formula:  x' = -x;   x er value negative hoy
            yRefY[i] =  y[i];       // Formula:  y' = y;    y same thake
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

        // X-axis Reflected triangle (Red)
        g.setColor(Color.RED);
        // X-axis er sathe reflected triangle draw kora hocche.
        drawTriangle(g, xRefX, yRefX);

        // Y-axis Reflected triangle (Yellow)
        g.setColor(Color.YELLOW);
        // Y-axis er sathe reflected triangle draw kora hocche.
        drawTriangle(g, xRefY, yRefY);
    }

    public static void main(String[] args) {
        // ekti Window create korlam. title "Triangle Reflection" dilam.
        JFrame frame = new JFrame("Triangle Reflection");

        // obj create korlam
        TriangleReflection panel = new TriangleReflection();

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
