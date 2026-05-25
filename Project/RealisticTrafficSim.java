import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class RealisticTrafficSim extends JPanel implements ActionListener {

    static final int PED_TOP_Y = 210;
    static final int PED_BOTTOM_Y = 380;
    static final int PED_LEFT_X = 300;
    static final int PED_RIGHT_X = 500;

    int phase = 0; // 0=NS Green, 1=NS Yellow, 2=EW Green, 3=EW Yellow
    int timer = 0;

    int nsCarY = 0, snCarY = 550;
    int ewCarX = 0, weCarX = 750;
    int pedNS_Y = PED_TOP_Y, pedSN_Y = PED_BOTTOM_Y, pedEW_X = PED_LEFT_X, pedWE_X = PED_RIGHT_X;
    int worldTick = 0;

    Timer t = new Timer(40, this);

    public RealisticTrafficSim() {
        t.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        drawSkyAndGround(g2);
        drawSidewalks(g2);
        drawRoads(g2);
        drawRoadMarkings(g2);

        // --- Scenery ---
        drawHouse(g2, 40, 140);      // Top-Left House (on ground)
        drawTrees(g2, 520, 165);     // Top-Right Trees (on ground)
        drawBank(g2, 40, 430);       // Bottom-Left Bank
        drawStore(g2, 560, 440);     // Bottom-Right Store

        // --- Signals & Labels ---
        boolean nsGreen = (phase == 0), nsYellow = (phase == 1), nsRed = (phase >= 2);
        boolean ewGreen = (phase == 2), ewYellow = (phase == 3), ewRed = (phase <= 1);

        drawSignalPost(g2, 290, 140, nsGreen, nsYellow, nsRed, "NORTH");
        drawSignalPost(g2, 490, 380, nsGreen, nsYellow, nsRed, "SOUTH");
        drawSignalPost(g2, 290, 380, ewGreen, ewYellow, ewRed, "WEST");
        drawSignalPost(g2, 490, 140, ewGreen, ewYellow, ewRed, "EAST");

        updateLogic();
        drawCars(g2);
        drawPedestrians(g2);
        drawBirds(g2);
    }

    // --- Environmental Objects ---
    void drawSkyAndGround(Graphics2D g) {
        GradientPaint sky = new GradientPaint(0, 0, new Color(120, 185, 255), 0, 220, new Color(210, 235, 255));
        g.setPaint(sky);
        g.fillRect(0, 0, 800, 220);
        g.setColor(new Color(48, 150, 70));
        g.fillRect(0, 220, 800, 380);
    }

    void drawBirds(Graphics2D g) {
        drawBird(g, (worldTick * 2) % 860 - 30, 30 + (int) (6 * Math.sin(worldTick / 10.0)), 1.0f);
        drawBird(g, 820 - (worldTick * 3 % 900), 55 + (int) (5 * Math.sin((worldTick + 20) / 11.0)), 0.85f);
        drawBird(g, (worldTick * 1) % 820 - 20, 20 + (int) (4 * Math.sin((worldTick + 40) / 9.0)), 0.7f);
    }

    void drawBird(Graphics2D g, int x, int y, float s) {
        Stroke old = g.getStroke();
        g.setStroke(new BasicStroke(2f * s, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g.setColor(new Color(40, 40, 40));
        int w = (int) (12 * s);
        int h = (int) (6 * s);
        g.drawArc(x, y, w, h, 0, 180);
        g.drawArc(x + w, y, w, h, 0, 180);
        g.setStroke(old);
    }

    void drawSidewalks(Graphics2D g) {
        g.setColor(new Color(190, 190, 190));
        g.fillRect(0, 230, 800, 20);
        g.fillRect(0, 350, 800, 20);
        g.fillRect(330, 0, 20, 600);
        g.fillRect(450, 0, 20, 600);
        g.setColor(new Color(160, 160, 160));
        g.drawRect(0, 230, 800, 20);
        g.drawRect(0, 350, 800, 20);
        g.drawRect(330, 0, 20, 600);
        g.drawRect(450, 0, 20, 600);
    }

    void drawRoads(Graphics2D g) {
        g.setColor(new Color(58, 58, 58));
        g.fillRect(0, 250, 800, 100); // Horizontal
        g.fillRect(350, 0, 100, 600); // Vertical
        g.setColor(new Color(45, 45, 45));
        g.fillRect(0, 290, 800, 6);
        g.fillRect(350, 0, 6, 600);
    }

    void drawHouse(Graphics2D g, int x, int y) {
        g.setColor(new Color(0, 0, 0, 45));
        g.fillOval(x + 8, y + 74, 74, 10);
        g.setColor(new Color(163, 110, 70));
        g.fillRect(x, y, 90, 80);
        g.setColor(new Color(120, 70, 40));
        g.drawRect(x, y, 90, 80);
        g.setColor(new Color(190, 60, 50));
        g.fillPolygon(new int[]{x - 10, x + 45, x + 100}, new int[]{y, y - 35, y}, 3);
        g.setColor(new Color(230, 245, 255));
        g.fillRect(x + 12, y + 20, 22, 20);
        g.fillRect(x + 56, y + 20, 22, 20);
        g.setColor(new Color(120, 80, 50));
        g.fillRect(x + 38, y + 40, 16, 40);
        g.setColor(new Color(90, 60, 40));
        g.fillOval(x + 50, y + 60, 4, 4);
    }

    void drawTrees(Graphics2D g, int x, int y) {
        drawTree(g, x, y, 1.0f);
        drawTree(g, x + 70, y + 10, 0.9f);
        drawTree(g, x + 130, y - 5, 1.1f);
    }

    void drawTree(Graphics2D g, int x, int y, float s) {
        int w = (int) (50 * s);
        int h = (int) (55 * s);
        g.setColor(new Color(0, 0, 0, 40));
        g.fillOval(x + 6, y + h - 6, w - 12, 8);
        g.setColor(new Color(110, 74, 40));
        g.fillRoundRect(x + w / 2 - 6, y + h - 10, 12, 28, 6, 6);
        g.setColor(new Color(22, 120, 40));
        g.fillOval(x, y, w, h);
        g.setColor(new Color(30, 140, 50));
        g.fillOval(x + 10, y - 8, w - 5, h - 5);
        g.setColor(new Color(15, 90, 30));
        g.drawOval(x, y, w, h);
    }

    void drawBank(Graphics2D g, int x, int y) {
        g.setColor(new Color(0, 0, 0, 45));
        g.fillOval(x + 12, y + 82, 130, 10);
        g.setColor(new Color(235, 235, 230));
        g.fillRect(x, y, 160, 90);
        g.setColor(new Color(180, 180, 175));
        g.drawRect(x, y, 160, 90);
        g.setColor(new Color(210, 210, 205));
        g.fillRect(x, y - 18, 160, 18);
        g.setColor(new Color(90, 90, 90));
        g.setFont(new Font("Arial", Font.BOLD, 14));
        g.drawString("BANK", x + 55, y - 5);
        for (int i = 0; i < 4; i++) {
            int cx = x + 20 + i * 30;
            g.setColor(new Color(220, 220, 215));
            g.fillRect(cx, y + 25, 12, 45);
            g.setColor(new Color(170, 170, 165));
            g.drawRect(cx, y + 25, 12, 45);
        }
        g.setColor(new Color(100, 70, 50));
        g.fillRect(x + 66, y + 48, 28, 42);
        g.setColor(new Color(180, 215, 245));
        g.fillRect(x + 10, y + 10, 28, 18);
        g.fillRect(x + 122, y + 10, 28, 18);
    }

    void drawStore(Graphics2D g, int x, int y) {
        g.setColor(new Color(0, 0, 0, 45));
        g.fillOval(x + 12, y + 78, 120, 10);
        g.setColor(new Color(220, 200, 170));
        g.fillRect(x, y, 140, 85);
        g.setColor(new Color(130, 95, 70));
        g.drawRect(x, y, 140, 85);
        g.setColor(new Color(255, 230, 100));
        g.fillRect(x + 10, y - 14, 120, 20);
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 12));
        g.drawString("SHOP", x + 55, y);
        g.setColor(new Color(200, 40, 40));
        for (int i = 0; i < 6; i++) {
            g.fillRect(x + 10 + i * 20, y + 8, 10, 12);
        }
        g.setColor(new Color(80, 80, 80));
        g.fillRect(x + 55, y + 40, 30, 45);
        g.setColor(new Color(180, 220, 245));
        g.fillRect(x + 10, y + 35, 35, 25);
        g.fillRect(x + 95, y + 35, 35, 25);
    }

    void drawRoadMarkings(Graphics2D g) {
        g.setColor(new Color(235, 235, 235));
        for (int x = 0; x < 800; x += 40) {
            g.fillRect(x + 5, 298, 22, 4);
        }
        for (int y = 0; y < 600; y += 40) {
            g.fillRect(398, y + 6, 4, 22);
        }
        g.setColor(Color.WHITE);
        g.fillRect(350, 220, 100, 5); // Stop Lines
        g.fillRect(350, 375, 100, 5);
        g.fillRect(325, 250, 5, 100);
        g.fillRect(475, 250, 5, 100);

        g.setColor(new Color(245, 245, 245));
        for (int i = 0; i < 5; i++) {
            g.fillRect(356, 236 + i * 6, 88, 4); // North crosswalk
            g.fillRect(356, 360 + i * 6, 88, 4); // South crosswalk
            g.fillRect(336 + i * 6, 256, 4, 88); // West crosswalk
            g.fillRect(460 + i * 6, 256, 4, 88); // East crosswalk
        }

        g.setColor(new Color(70, 70, 70));
        for (int x = 0; x < 800; x += 30) {
            g.fillRect(x + 8, 258, 4, 4);
            g.fillRect(x + 18, 330, 4, 4);
        }
    }

    void drawSignalPost(Graphics2D g, int x, int y, boolean gOn, boolean yOn, boolean rOn, String label) {
        // Direction Label
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 13));
        g.drawString(label, x - 5, y - 8);

        // Signal Housing
        g.setColor(Color.BLACK);
        g.fillRoundRect(x, y, 25, 65, 10, 10);
        g.fillRect(x + 10, y + 65, 5, 25);
        g.setColor(new Color(80, 80, 80));
        g.fillOval(x + 6, y + 88, 13, 5);
        
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
        boolean nsWalk = (phase == 0 || phase == 1);
        boolean ewWalk = (phase == 2 || phase == 3);

        if (nsWalk || (pedNS_Y > PED_TOP_Y && pedNS_Y < PED_BOTTOM_Y)) {
            pedNS_Y += pedSpeed;
            pedSN_Y -= pedSpeed;
        }
        if (pedNS_Y > PED_BOTTOM_Y) pedNS_Y = PED_TOP_Y;
        if (pedSN_Y < PED_TOP_Y) pedSN_Y = PED_BOTTOM_Y;

        if (ewWalk || (pedEW_X > PED_LEFT_X && pedEW_X < PED_RIGHT_X)) {
            pedEW_X += pedSpeed;
            pedWE_X -= pedSpeed;
        }
        if (pedEW_X > PED_RIGHT_X) pedEW_X = PED_LEFT_X;
        if (pedWE_X < PED_LEFT_X) pedWE_X = PED_RIGHT_X;
    }

    void drawCars(Graphics2D g) {
        boolean personOnNS = (pedEW_X > 330 && pedEW_X < 470) || (pedWE_X > 330 && pedWE_X < 470);
        boolean personOnEW = (pedNS_Y > 230 && pedNS_Y < 370) || (pedSN_Y > 230 && pedSN_Y < 370);

        // --- NS Direction Logic ---
        int speedNS = (phase == 0) ? 6 : (phase == 1 ? 3 : 0);
        int speedSN = (phase == 0) ? 6 : (phase == 1 ? 3 : 0);
        if (phase >= 2) { // Red: stop before line, clear after it
            if (nsCarY > 170) speedNS = 6;
            if (snCarY < 380) speedSN = 6;
        }
        if (personOnNS && nsCarY > 100 && nsCarY <= 170) speedNS = 0;
        if (personOnNS && snCarY < 500 && snCarY >= 380) speedSN = 0;
        if (phase >= 2 && nsCarY <= 170) speedNS = 0;
        if (phase >= 2 && snCarY >= 380) speedSN = 0;

        nsCarY += speedNS; snCarY -= speedSN;

        // --- EW Direction Logic ---
        int speedEW = (phase == 2) ? 6 : (phase == 3 ? 3 : 0);
        int speedWE = (phase == 2) ? 6 : (phase == 3 ? 3 : 0);
        if (phase <= 1) { // Red: stop before line, clear after it
            if (ewCarX > 275) speedEW = 6;
            if (weCarX < 475) speedWE = 6;
        }
        if (personOnEW && ewCarX > 150 && ewCarX <= 275) speedEW = 0;
        if (personOnEW && weCarX < 650 && weCarX >= 475) speedWE = 0;
        if (phase <= 1 && ewCarX <= 275) speedEW = 0;
        if (phase <= 1 && weCarX >= 475) speedWE = 0;

        ewCarX += speedEW; weCarX -= speedWE;

        // Drawing Cars
        drawCarVertical(g, 380, nsCarY, new Color(50, 110, 220), true);
        drawCarVertical(g, 420, snCarY, new Color(200, 60, 60), false);
        drawCarHorizontal(g, ewCarX, 270, new Color(220, 140, 50), true);
        drawCarHorizontal(g, weCarX, 310, new Color(70, 150, 130), false);

        // Loop resets
        if (nsCarY > 650) nsCarY = -50; if (snCarY < -50) snCarY = 650;
        if (ewCarX > 850) ewCarX = -50; if (weCarX < -50) weCarX = 850;
    }

    void drawPedestrians(Graphics2D g) {
        drawPerson(g, 332, pedNS_Y, new Color(60, 120, 200), new Color(30, 30, 30), false); // North -> South
        drawPerson(g, 462, pedSN_Y, new Color(200, 80, 80), new Color(60, 40, 40), true);  // South -> North
        drawPerson(g, pedEW_X, 225, new Color(90, 150, 90), new Color(50, 50, 70), true);  // West -> East
        drawPerson(g, pedWE_X, 360, new Color(200, 140, 60), new Color(30, 30, 30), false); // East -> West
    }

    void drawCarVertical(Graphics2D g, int x, int y, Color body, boolean down) {
        int w = 30, h = 56;
        g.setColor(new Color(0, 0, 0, 70));
        g.fillOval(x + 3, y + 12, w - 6, h - 6);
        GradientPaint gp = new GradientPaint(x, y, body.brighter(), x, y + h, body.darker());
        g.setPaint(gp);
        g.fillRoundRect(x, y, w, h, 10, 10);
        g.setColor(body.darker().darker());
        g.drawRoundRect(x, y, w, h, 10, 10);
        g.setColor(new Color(255, 255, 255, 70));
        g.fillRoundRect(x + 4, y + 6, w - 8, 8, 6, 6);
        g.setColor(new Color(185, 220, 245));
        g.fillRoundRect(x + 6, y + 10, w - 12, 14, 6, 6);
        g.setColor(new Color(160, 200, 230));
        g.fillRoundRect(x + 6, y + h - 22, w - 12, 10, 6, 6);
        g.setColor(body.darker());
        g.fillRect(x + 5, y + h / 2 - 2, w - 10, 4);
        g.setColor(new Color(25, 25, 25));
        g.fillOval(x - 2, y + 8, 8, 12);
        g.fillOval(x - 2, y + 36, 8, 12);
        g.fillOval(x + w - 6, y + 8, 8, 12);
        g.fillOval(x + w - 6, y + 36, 8, 12);
        g.setColor(new Color(160, 160, 160));
        g.fillOval(x, y + 11, 4, 6);
        g.fillOval(x, y + 39, 4, 6);
        g.fillOval(x + w - 2, y + 11, 4, 6);
        g.fillOval(x + w - 2, y + 39, 4, 6);
        g.setColor(new Color(255, 240, 180));
        if (down) {
            g.fillRect(x + 6, y + h - 4, 6, 3);
            g.fillRect(x + w - 12, y + h - 4, 6, 3);
            g.setColor(new Color(220, 60, 60));
            g.fillRect(x + 6, y + 1, 5, 3);
            g.fillRect(x + w - 11, y + 1, 5, 3);
        } else {
            g.fillRect(x + 6, y + 1, 6, 3);
            g.fillRect(x + w - 12, y + 1, 6, 3);
            g.setColor(new Color(220, 60, 60));
            g.fillRect(x + 6, y + h - 4, 5, 3);
            g.fillRect(x + w - 11, y + h - 4, 5, 3);
        }
    }

    void drawCarHorizontal(Graphics2D g, int x, int y, Color body, boolean right) {
        int w = 56, h = 30;
        g.setColor(new Color(0, 0, 0, 70));
        g.fillOval(x + 6, y + 12, w - 12, h - 6);
        GradientPaint gp = new GradientPaint(x, y, body.brighter(), x + w, y, body.darker());
        g.setPaint(gp);
        g.fillRoundRect(x, y, w, h, 10, 10);
        g.setColor(body.darker().darker());
        g.drawRoundRect(x, y, w, h, 10, 10);
        g.setColor(new Color(255, 255, 255, 70));
        g.fillRoundRect(x + 8, y + 4, 16, h - 8, 6, 6);
        g.setColor(new Color(180, 220, 245));
        g.fillRoundRect(x + 14, y + 6, 20, 10, 6, 6);
        g.setColor(new Color(160, 200, 230));
        g.fillRoundRect(x + 34, y + 6, 14, 10, 6, 6);
        g.setColor(body.darker());
        g.fillRect(x + w / 2 - 2, y + 4, 4, h - 8);
        g.setColor(new Color(25, 25, 25));
        g.fillOval(x + 8, y - 2, 12, 8);
        g.fillOval(x + 8, y + h - 6, 12, 8);
        g.fillOval(x + w - 20, y - 2, 12, 8);
        g.fillOval(x + w - 20, y + h - 6, 12, 8);
        g.setColor(new Color(160, 160, 160));
        g.fillOval(x + 11, y + 1, 6, 4);
        g.fillOval(x + 11, y + h - 5, 6, 4);
        g.fillOval(x + w - 17, y + 1, 6, 4);
        g.fillOval(x + w - 17, y + h - 5, 6, 4);
        g.setColor(new Color(255, 240, 180));
        if (right) {
            g.fillRect(x + w - 3, y + 6, 3, 6);
            g.fillRect(x + w - 3, y + 16, 3, 6);
            g.setColor(new Color(220, 60, 60));
            g.fillRect(x, y + 6, 3, 6);
            g.fillRect(x, y + 16, 3, 6);
        } else {
            g.fillRect(x, y + 6, 3, 6);
            g.fillRect(x, y + 16, 3, 6);
            g.setColor(new Color(220, 60, 60));
            g.fillRect(x + w - 3, y + 6, 3, 6);
            g.fillRect(x + w - 3, y + 16, 3, 6);
        }
    }

    void drawPerson(Graphics2D g, int x, int y, Color shirt, Color pants, boolean female) {
        g.setColor(new Color(255, 224, 189));
        g.fillOval(x + 4, y, 8, 8);
        if (female) {
            g.setColor(new Color(90, 60, 40));
            g.fillArc(x + 2, y - 1, 12, 10, 0, 180);
        }
        g.setColor(shirt);
        g.fillRoundRect(x + 2, y + 8, 12, 12, 4, 4);
        if (female) {
            g.setColor(pants);
            g.fillPolygon(new int[]{x + 2, x + 14, x + 8}, new int[]{y + 20, y + 20, y + 30}, 3);
            g.setColor(pants.darker());
            g.drawLine(x + 6, y + 30, x + 4, y + 38);
            g.drawLine(x + 10, y + 30, x + 12, y + 38);
        } else {
            g.setColor(pants);
            g.fillRect(x + 3, y + 20, 4, 9);
            g.fillRect(x + 9, y + 20, 4, 9);
        }
        g.setColor(new Color(80, 60, 40));
        g.drawLine(x + 2, y + 12, x - 2, y + 16);
        g.drawLine(x + 14, y + 12, x + 18, y + 16);
    }

    public void actionPerformed(ActionEvent e) {
        timer += 40;
        worldTick++;
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
