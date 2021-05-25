package objects;

import java.awt.*;

public class Block extends Rectangle {
    public static int blockSize = 30;

    private int x;
    private int y;
    private final int width = blockSize;
    private final int height = blockSize;

    public Block(int x, int y) {
        this.x = x;
        this.y = y;
        setBounds(x, y, width, height);
    }

    public void draw(Graphics g) {
        g.setColor(Color.DARK_GRAY);
        g.fillRect(this.x, this.y, width, height);
    }
}
