//1. Draw a Line using Computer Graphics.

//graphics er sob class import kora hoyeche(ex: Graphics, color, e.t.c.)
import java.awt.*;
//Swing er class import (ex: JFrame, JPanel)
import javax.swing.*;


// JPanel inherit korlam karon panel er opor drawing korbo.
public class LineDraw extends JPanel {

    /*
    * ei method automatic call hoy jokhon window open hoy
    * "Graphics g" holo drawing tool
    * "g" use kore shape draw kori. 
    * format: drawLine(x1, y1, x2, y2)
    * akhane Starting point = (50, 50) and Ending point = (200, 200)
    * মানে screen এর 50,50 position থেকে 200,200 পর্যন্ত একটি লাইন।
    */
    public void paint(Graphics g) {
        g.drawLine(50, 50, 200, 200);
    }

    public static void main(String[] args) {
        // ekti Window create korlam and ei window use korar jonno obj create korlam. title "Draw Line" dilam.
        JFrame frame = new JFrame("Draw Line");
        LineDraw panel = new LineDraw();
        // obj, frame er majhe add na korle output kichui show korbe na.
        frame.add(panel);
        // frame er Window size 
        frame.setSize(400, 400);
        // Close button caple program jeno full off hoy.
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Window visible kora hoyeche noyto window dekhane na
        frame.setVisible(true);
    }
}

// note: Java এর coordinate system math graph থেকে আলাদা