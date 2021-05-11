package objects;

import java.awt.*;

public class Block extends Rectangle {
    public static int blockSize = 30;

    private double x;
    private double y;
    private final int width = blockSize;
    private final int height = blockSize;

    public Block(double x, double y) {
        this.x = x;
        this.y = y;
        setBounds((int)x, (int)y, width, height);
    }

    public void draw(Graphics g) {
        g.setColor(Color.DARK_GRAY);
        g.fillRect((int)x, (int)y, width, height);
    }
}
