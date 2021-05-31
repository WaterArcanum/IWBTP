package objects;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Goal extends Rectangle {
    public static int blockSize = Block.blockSize;

    private final int x;
    private int y;
    private final int origY;
    private final int width = blockSize;
    private final int height = blockSize;
    private Image img;
    private int tick;

    public Goal(int x, int y) {
        this.x = x;
        this.y = y - 5;
        origY = y;
        setBounds(x, y, width, height);

        try {
            img = ImageIO.read(new File("resources/imgs/sphere.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics g) {
        tick += 1;
        if(tick == 101) tick = 0;
        if(tick <= 50) {
            if(tick % 10 == 0 && (y - origY) <= 1) {
                y++;
            }
        }
        else {
            if(tick % 10 == 0 && (origY - y) <= 5)  {
                y--;
            }
        }
        g.setColor(Color.MAGENTA);
        g.drawImage(img, this.x, this.y, null);
    }
}
