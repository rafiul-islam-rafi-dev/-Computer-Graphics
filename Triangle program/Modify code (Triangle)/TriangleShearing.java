import javax.swing.*; //Swing er class import (ex: JFrame, JPanel)
import java.awt.*; //graphics er sob class import kora hoyeche(ex: Graphics, color, e.t.c.)

// JPanel inherit korlam karon "panel" er opor drawing korbo.
public class TriangleShearing extends JPanel {

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

    // X-direction shear er jonno array (X-axis bরাবার tana hobe)
    int[] xShX = new int[3]; // X-shear: x change hoy
    int[] yShX = new int[3]; // X-shear: y same thake

    // Y-direction shear er jonno array (Y-axis bরাবার tana hobe)
    int[] xShY = new int[3]; // Y-shear: x same thake
    int[] yShY = new int[3]; // Y-shear: y change hoy

    // Shearing values
    double shx = 0.5; // X-direction shear factor (koto tana hobe X-axis bরাবার)
    double shy = 0.5; // Y-direction shear factor (koto tana hobe Y-axis bরাবার)

    // Constructor
    // Object create hole automatic call hoy
    TriangleShearing() {
        for (int i = 0; i < 3; i++) {

            // ---- X-direction Shear ----
            // X-shear korle x er sathe (shx * y) add hoy, y same thake
            xShX[i] = (int)(x[i] + shx * y[i]); // Formula:  x' = x + shx*y;   x er sathe shear value add hoy
            yShX[i] = y[i];                       // Formula:  y' = y;            y same thake

            // ---- Y-direction Shear ----
            // Y-shear korle x same thake, y er sathe (shy * x) add hoy
            xShY[i] = x[i];                       // Formula:  x' = x;            x same thake
            yShY[i] = (int)(y[i] + shy * x[i]);   // Formula:  y' = y + shy*x;   y er sathe shear value add hoy
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

        // X-direction Sheared triangle (Red)
        g.setColor(Color.RED);
        // X-direction shear triangle draw kora hocche.
        drawTriangle(g, xShX, yShX);

        // Y-direction Sheared triangle (Yellow)
        g.setColor(Color.YELLOW);
        // Y-direction shear triangle draw kora hocche.
        drawTriangle(g, xShY, yShY);
    }

    public static void main(String[] args) {
        // ekti Window create korlam. title "Triangle Shearing" dilam.
        JFrame frame = new JFrame("Triangle Shearing");

        // obj create korlam
        TriangleShearing panel = new TriangleShearing();

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
