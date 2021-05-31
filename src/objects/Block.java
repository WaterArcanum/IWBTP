package objects;

import main.GamePanel;
import states.PlayState;

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
        switch(PlayState.getLevel()) {
            case 0 -> {
                g.setColor(new Color(255, 81, 0));
                g.drawRect(x, y, width, height);
            }
            case 1 -> {
                g.setColor(Color.DARK_GRAY);
                g.fillRect(this.x, this.y, width, height);
                g.drawImage(img, this.x, this.y, null);
            }
            case 2 -> {
                g.setColor(new Color(255, 96, 96, 100));
                g.drawRect(this.x, this.y, width, height);
                g.setColor(new Color(144, 88, 88, 50));
                g.fillRect(this.x, this.y, width, height);
            }
            case 3 -> {
                g.setColor(Color.WHITE);
                g.fillRect(this.x, this.y, width, height);
            }
        }
    }
}
