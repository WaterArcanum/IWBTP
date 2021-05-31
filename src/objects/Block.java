package objects;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Block extends Rectangle {
    public static int blockSize = 30;

    private final int x;
    private final int y;
    private final int width = blockSize;
    private final int height = blockSize;

    private Image img;

    public Block(int x, int y, int even) {
        this.x = x;
        this.y = y;
        setBounds(x, y, width, height);
        try {
            String pathname = "resources/imgs/" + (even%2==0 ? "stolen.png" : "stolen.png");
            img = ImageIO.read(new File(pathname));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics g) {
        g.setColor(Color.DARK_GRAY);
        g.fillRect(this.x, this.y, width, height);
        g.drawImage(img, this.x, this.y, null);
    }
}
