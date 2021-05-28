package objects;

import java.awt.*;

public class SavePoint extends Rectangle {
    public static int blockSize = Block.blockSize;

    private final int x;
    private final int y;

    private final int id;
    private final int width = blockSize;
    private final int height = blockSize;

    public SavePoint(int x, int y, int id) {
        this.x = x;
        this.y = y;
        this.id = id;
        setBounds(x, y, width, height);
    }

    public int getId() {
        return id;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void draw(Graphics g) {
        g.setColor(Color.YELLOW);
        g.fillRect(this.x, this.y, width, height);
    }
}
