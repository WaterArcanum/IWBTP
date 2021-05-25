package objects;

import java.awt.*;

public class Spike extends Polygon {
    public static int blockSize = Block.blockSize;
    private static int thinness = blockSize/15;

    private final int width = blockSize;
    private final int height = blockSize;

    public Spike(int x, int y) {
        addPoint(x-blockSize+thinness, y);
        addPoint(x-thinness, y);
        addPoint(x-blockSize/2, y-blockSize);
    }

    public void draw(Graphics g) {
        g.setColor(Color.DARK_GRAY);
        Graphics2D g2d = (Graphics2D)g.create();
        g2d.setColor(Color.GREEN);
        g2d.fill(this);
    }
}
