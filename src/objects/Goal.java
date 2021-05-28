package objects;

import java.awt.*;

public class Goal extends Rectangle {
    public static int blockSize = Block.blockSize;

    private final int x;
    private final int y;
    private final int width = blockSize;
    private final int height = blockSize;

    public Goal(int x, int y) {
        this.x = x;
        this.y = y;
        setBounds(x, y, width, height);
    }

    public void draw(Graphics g) {
        g.setColor(Color.MAGENTA);
        g.fillRect(this.x, this.y, width, height);
    }
}
