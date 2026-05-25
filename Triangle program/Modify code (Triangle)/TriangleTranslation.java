import javax.swing.*; //Swing er class import (ex: JFrame, JPanel)
import java.awt.*; //graphics er sob class import kora hoyeche(ex: Graphics, color, e.t.c.)

// JPanel inherit korlam karon "panel" er opor drawing korbo.
public class TriangleTranslation extends JPanel {

    // custom origin point (0,0)=(320,240)
    int originX = 320;
    int originY = 240;

    // Original triangle points. Triangle er 3ta vertex define kora hoyeche. Ex: A(0,50), B(-50, -50), C(50, -50)
    int[] x = {0, -50, 50};
    int[] y = {50, -50, -50};

    // custom-origin er X-coordinate er sathe original-triangle er X-coordinate value add kore dibo. ( originalTriangleX = customOriginX   +   originalTriangleValueX )
    int screenX (int x) {
        // origin point er sathe x value add kora hoyeche
        return originX + x;
    }
    // custom-origin er Y-coordinate er sathe original-triangle er Y-coordinate value add kore dibo. ( originalTriangleY = customOriginY   +   originalTriangleValueY )
    int screenY(int y) {
        // Java te Y-axis ulta hoy tai minus kora hoyeche
        return originY - y; 
    }

    // Triangle draw korar jonno method (original-Triangle and new-Triangle)
    public void drawTriangle(Graphics g, int[] x, int[] y) {
        // 3 ta line draw korar jonno loop use kora hoyeche
        for (int i = 0; i < 3; i++) {
            // Next point calculate korar jonno
            int j = (i + 1) % 3;
            // dui point er moddhe line draw kora hocche
            g.drawLine(screenX(x[i]), screenY(y[i]), screenX(x[j]), screenY(y[j]));
        }
    }


    // New-triangle er X-coordinate translate korar value store korar jonno array.
    int[] xt = new int[3];
    // New-triangle er Y-coordinate translate korar value store korar jonno array.
    int[] yt = new int[3];

    // Translation values.
    int tx = 100; // (right-, left+ ) side
    int ty = 100;  // (top+, down-) side

    // New Translation apply korar. 
    // class er majhe direct for-loop lekha jabe na, tai constructor/method er majhe likhte hobe.
    // Constructor
    // Object create hole automatic call hoy
    TriangleTranslation() {
        // New Translation apply korar jonno loop
        for (int i = 0; i < 3; i++) {
            // x-coordinate er sathe tx add kora hocche
            xt[i] = x[i] + tx; //  Formula:  x' = x + tx;   newTranslation (x') = originalValue (x)    +    translationValue (tx);
            // y-coordinate er sathe ty add kora hocche
            yt[i] = y[i] + ty; //  Formula:  y' = y + ty;   newTranslation (y') = originalValue (y)    +    translationValue (ty);
        }

    }

    
    /*
    * ei method automatic call hoy jokhon window open hoy
    * "Graphics g" holo drawing tool
    * "g" use kore shape draw kori. 
    * format: drawLine(x1, y1, x2, y2) karon ekti line draw korte 4ta point lage.
    * akhane Starting point = (x1, y1) and Ending point = (x2, y2)
    */
    public void paintComponent(Graphics g) {
        // JPanel clear kore abar draw korar jonno super method call
        super.paintComponent(g);

        // Draw axes (2ta line er color)
        g.setColor(Color.PINK);
        // Horizontal(left-to-right) line draw kore X-axis banano hoyeche
        g.drawLine(0, originY, 640, originY); // X-axis
        // vertical(up-and-down) line draw kore Y-axis banano hoyeche
        g.drawLine(originX, 0, originX, 480); // Y-axis


        // Original triangle (Green)
        g.setColor(Color.GREEN);
        // Original triangle draw kora hocche. "drawTriangle" method a value pass kore dilam Triangle draw korar jonno.
        drawTriangle(g, x, y);


        // New Translated triangle Color (Red)
        g.setColor(Color.RED);
        // New Translated triangle draw kora hocche. "drawTriangle" method a value pass kore dilam Triangle draw korar jonno.
        drawTriangle(g, xt, yt);
    }

    public static void main(String[] args) {
        // ekti Window create korlam and ei window use korar jonno obj create korlam. title "Triangle Translation" dilam.
        JFrame frame = new JFrame("Triangle Translation");

        // obj create korlam
        TriangleTranslation panel = new TriangleTranslation();

        // Obj er background color set korlam
        panel.setBackground(Color.BLACK);

        // obj, frame er majhe add na korle output kichui show korbe na.
        frame.add(panel);
        // frame er Window size 
        frame.setSize(640, 480);
        // Close button caple program jeno full off hoy.
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Window visible kora hoyeche noyto window dekhane na
        frame.setVisible(true);
    }
}