package objects;

import java.awt.*;

public class Spike extends Polygon {
    public static int blockSize = Block.blockSize;
    private static final int thinness = 0;

    private final int width = blockSize;
    private final int height = blockSize;

    public Spike(int x, int y, int rotation) {
        x += blockSize;
        y += blockSize;
        int x0 = x - blockSize;
        int x1 = x - (blockSize / 2);
        int x2 = x;
        int y0 = y;
        int y1 = y - (blockSize / 2);
        int y2 = y - blockSize;
        switch (rotation) {
            // Facing up
            case 1 -> {
                addPoint(x0+thinness, y0); // Left point
                addPoint(x2-thinness, y0); // Right point
                addPoint(x1, y2); // Middle point
            }
            // Facing down
            case 2 -> {
                addPoint(x0+thinness, y2);
                addPoint(x2-thinness, y2);
                addPoint(x1, y0);
            }
            // Facing left
            case 3 -> {
                addPoint(x0+thinness, y1);
                addPoint(x2-thinness, y2);
                addPoint(x2, y0);
            }
            // Facing right
            case 4 -> {
                addPoint(x0+thinness, y2);
                addPoint(x0-thinness, y0);
                addPoint(x2, y1);
            }
        }
//        switch (rotation) {
//            // Facing up
//            case 1 -> {
//                addPoint(x - blockSize + thinness, y); // Left point
//                addPoint(x - thinness, y); // Right point
//                addPoint(x - blockSize / 2, y - blockSize); // Middle point
//            }
//            // Facing down
//            case 2 -> {
//                addPoint(x - blockSize + thinness, y - blockSize);
//                addPoint(x - thinness, y - blockSize);
//                addPoint(x - blockSize / 2, y);
//            }
//            // Facing left
//            case 3 -> {
//                addPoint(y, x - blockSize + thinness);
//                addPoint(y, x - thinness);
//                addPoint(y - blockSize, x - blockSize / 2);
//            }
//            // Facing right
//            case 4 -> {
//                addPoint(y - blockSize, x - blockSize + thinness);
//                addPoint(y - blockSize, x - thinness);
//                addPoint(y, x - blockSize / 2);
//            }
//        }
    }

    public void draw(Graphics g) {
        g.setColor(Color.DARK_GRAY);
        Graphics2D g2d = (Graphics2D)g.create();
        g2d.setColor(Color.GREEN);
        g2d.fill(this);
    }
}
