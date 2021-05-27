package objects;

import java.awt.*;

public class Spike extends Polygon {
    public static int blockSize = Block.blockSize;
    private static final int thinness = blockSize/25;

    private final int width = blockSize;
    private final int height = blockSize;

    public Spike(int x, int y, int rotation) {
        x += blockSize;
        y += blockSize;
        int x0 = x ;
        int x1 = x - (blockSize / 2);
        int x2 = x - blockSize;
        int y0 = y;
        int y1 = y - (blockSize / 2);
        int y2 = y - blockSize;
        switch (rotation) {
            // Facing up
            case 1 -> {
                addPoint(x0, y0); // Left point
                addPoint(x2, y0); // Right point
                addPoint(x1, y2); // Middle point
            }
            // Facing down
            case 2 -> {
                addPoint(x0, y2);
                addPoint(x2, y2);
                addPoint(x1, y0);
            }
            // Facing left
            case 3 -> {
                addPoint(x0, y2);
                addPoint(x0, y0);
                addPoint(x2, y1);
            }
            // Facing right
            case 4 -> {
                addPoint(x0, y1);
                addPoint(x2, y2);
                addPoint(x2, y0);
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
