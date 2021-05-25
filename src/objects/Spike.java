package objects;

import java.awt.*;

public class Spike extends Polygon {
    public static int blockSize = Block.blockSize;
    private static final int thinness = blockSize/15;

    private final int width = blockSize;
    private final int height = blockSize;

    public Spike(int x, int y, int rotation) {
        switch (rotation) {
            // Facing up
            case 1 -> {
                addPoint(x - blockSize + thinness, y); // Left point
                addPoint(x - thinness, y); // Right point
                addPoint(x - blockSize / 2, y - blockSize); // Middle point
            }
            // Facing down
            case 2 -> {
                addPoint(x - blockSize + thinness, y - blockSize);
                addPoint(x - thinness, y - blockSize);
                addPoint(x - blockSize / 2, y);
            }
            // Facing left
            case 3 -> {
                addPoint(y, x - blockSize + thinness);
                addPoint(y, x - thinness);
                addPoint(y - blockSize, x - blockSize / 2);
            }
            // Facing right
            case 4 -> {
                addPoint(y - blockSize, x - blockSize + thinness);
                addPoint(y - blockSize, x - thinness);
                addPoint(y, x - blockSize / 2);
            }
        }
    }

    public void draw(Graphics g) {
        g.setColor(Color.DARK_GRAY);
        Graphics2D g2d = (Graphics2D)g.create();
        g2d.setColor(Color.GREEN);
        g2d.fill(this);
    }
}
