import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class RealisticTrafficSim extends JPanel implements ActionListener {

    int phase = 0; // 0=NS Green, 1=NS Yellow, 2=EW Green, 3=EW Yellow
    int timer = 0;

    int nsCarY = 0, snCarY = 550;
    int ewCarX = 0, weCarX = 750;
    int pedNS_Y = 180, pedEW_X = 280;

    Timer t = new Timer(40, this);

    public RealisticTrafficSim() {
        t.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(new Color(34, 139, 34)); // Grass

        // --- Scenery ---
        drawHouse(g, 50, 50);      // Top-Left House
        drawTrees(g, 650, 60);     // Top-Right Trees
        drawATM(g,   50, 450);      // Bottom-Left ATM
        drawStore(g, 600, 450);    // Bottom-Right Store

        // --- Roads ---
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, 250, 800, 100); // Horizontal
        g.fillRect(350, 0, 100, 600); // Vertical

        drawRoadMarkings(g);

        // --- Signals & Labels ---
        boolean nsGreen = (phase == 0), nsYellow = (phase == 1), nsRed = (phase >= 2);
        boolean ewGreen = (phase == 2), ewYellow = (phase == 3), ewRed = (phase <= 1);

        drawSignalPost(g, 290, 140, nsGreen, nsYellow, nsRed, "NORTH");
        drawSignalPost(g, 490, 380, nsGreen, nsYellow, nsRed, "SOUTH");
        drawSignalPost(g, 290, 380, ewGreen, ewYellow, ewRed, "WEST");
        drawSignalPost(g, 490, 140, ewGreen, ewYellow, ewRed, "EAST");

        updateLogic();
        drawCars(g);
        drawPedestrians(g);
    }

    // --- Environmental Objects ---
    void drawHouse(Graphics g, int x, int y) {
        g.setColor(new Color(139, 69, 19)); // Brown
        g.fillRect(x, y, 80, 80);
        g.setColor(Color.RED); // Roof
        g.fillPolygon(new int[]{x-10, x+40, x+90}, new int[]{y, y-40, y}, 3);
        g.setColor(Color.CYAN); // Window
        g.fillRect(x+15, y+20, 20, 20);
        g.fillRect(x+45, y+20, 20, 20);
    }

    void drawTrees(Graphics g, int x, int y) {
        g.setColor(new Color(101, 67, 33)); // Trunk
        g.fillRect(x+15, y+40, 15, 30);
        g.setColor(new Color(0, 100, 0)); // Leaves
        g.fillOval(x, y, 45, 50);
        g.fillOval(x+20, y+10, 45, 50);
    }

    void drawATM(Graphics g, int x, int y) {
        g.setColor(Color.BLUE);
        g.fillRect(x, y, 50, 80);
        g.setColor(Color.WHITE);
        g.fillRect(x+10, y+10, 30, 20); // Screen
        g.setFont(new Font("Arial", Font.BOLD, 12));
        g.drawString("ATM", x+12, y+50);
    }

    void drawStore(Graphics g, int x, int y) {
        g.setColor(new Color(210, 180, 140)); // Tan
        g.fillRect(x, y, 120, 80);
        g.setColor(Color.BLACK);
        g.drawRect(x, y, 120, 80);
        g.setColor(Color.YELLOW);
        g.fillRect(x+10, y-10, 100, 20); // Signboard
        g.setColor(Color.BLACK);
        g.drawString("SUPER SHOP", x+20, y+5);
    }

    void drawRoadMarkings(Graphics g) {
        g.setColor(Color.WHITE);
        for (int i = 360; i <= 440; i += 15) {
            g.fillRect(i, 230, 8, 20); 
            g.fillRect(i, 350, 8, 20); 
        }
        for (int i = 260; i <= 340; i += 15) {
            g.fillRect(330, i, 20, 8); 
            g.fillRect(450, i, 20, 8); 
        }
        g.fillRect(350, 220, 100, 5); // Stop Lines
        g.fillRect(350, 375, 100, 5); 
        g.fillRect(325, 250, 5, 100); 
        g.fillRect(475, 250, 5, 100); 
    }

    void drawSignalPost(Graphics g, int x, int y, boolean gOn, boolean yOn, boolean rOn, String label) {
        // Direction Label
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 13));
        g.drawString(label, x - 5, y - 8);

        // Signal Housing
        g.setColor(Color.BLACK);
        g.fillRoundRect(x, y, 25, 65, 10, 10);
        
        // Red, Yellow, Green
        g.setColor(rOn ? Color.RED : new Color(60, 0, 0));
        g.fillOval(x + 5, y + 5, 15, 15);
        g.setColor(yOn ? Color.YELLOW : new Color(60, 60, 0));
        g.fillOval(x + 5, y + 25, 15, 15);
        g.setColor(gOn ? Color.GREEN : new Color(0, 60, 0));
        g.fillOval(x + 5, y + 45, 15, 15);
    }

    void updateLogic() {
        int pedSpeed = (timer > 5000) ? 4 : 2;
        if (phase == 0 || phase == 1) pedNS_Y += pedSpeed;
        if (pedNS_Y > 450) pedNS_Y = 150;
        if (phase == 2 || phase == 3) pedEW_X += pedSpeed;
        if (pedEW_X > 550) pedEW_X = 250;
    }

    void drawCars(Graphics g) {
        boolean personOnNS = (pedEW_X > 330 && pedEW_X < 470);
        boolean personOnEW = (pedNS_Y > 230 && pedNS_Y < 370);

        // --- NS Direction Logic ---
        int speedNS = (phase == 0) ? 6 : (phase == 1 ? 3 : 0);
        if (phase >= 2) { // Clearing Intersection
            if (nsCarY > 170 && nsCarY < 400) speedNS = 6;
            if (snCarY < 380 && snCarY > 200) speedNS = 6;
        }
        if (personOnNS && ((nsCarY < 230 && nsCarY > 100) || (snCarY > 350 && snCarY < 500))) speedNS = 0;
        if (phase >= 2 && nsCarY <= 170) speedNS = 0;
        if (phase >= 2 && snCarY >= 380) speedNS = 0;

        nsCarY += speedNS; snCarY -= speedNS;

        // --- EW Direction Logic ---
        int speedEW = (phase == 2) ? 6 : (phase == 3 ? 3 : 0);
        if (phase <= 1) { // Clearing Intersection
            if (ewCarX > 275 && ewCarX < 500) speedEW = 6;
            if (weCarX < 475 && weCarX > 300) speedEW = 6;
        }
        if (personOnEW && ((ewCarX < 330 && ewCarX > 150) || (weCarX > 450 && weCarX < 650))) speedEW = 0;
        if (phase <= 1 && ewCarX <= 275) speedEW = 0;
        if (phase <= 1 && weCarX >= 475) speedEW = 0;

        ewCarX += speedEW; weCarX -= speedEW;

        // Drawing Cars
        g.setColor(Color.BLUE); g.fillRect(380, nsCarY, 30, 50); g.fillRect(420, snCarY, 30, 50); 
        g.setColor(Color.RED); g.fillRect(ewCarX, 270, 50, 30); g.fillRect(weCarX, 310, 50, 30); 

        // Loop resets
        if (nsCarY > 650) nsCarY = -50; if (snCarY < -50) snCarY = 650;
        if (ewCarX > 850) ewCarX = -50; if (weCarX < -50) weCarX = 850;
    }

    void drawPedestrians(Graphics g) {
        g.setColor(Color.MAGENTA);
        g.fillOval(335, pedNS_Y, 15, 15);
        g.fillOval(pedEW_X, 235, 15, 15);
    }

    public void actionPerformed(ActionEvent e) {
        timer += 40;
        if (phase == 0 && timer >= 7000) { phase = 1; timer = 0; }
        else if (phase == 1 && timer >= 3000) { phase = 2; timer = 0; }
        else if (phase == 2 && timer >= 7000) { phase = 3; timer = 0; }
        else if (phase == 3 && timer >= 3000) { phase = 0; timer = 0; }
        repaint();
    }

    public static void main(String[] args) {
        JFrame f = new JFrame("Realistic Traffic Simulation v2");
        f.add(new RealisticTrafficSim());
        f.setSize(800, 600);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }
}