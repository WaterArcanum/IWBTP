package objects;

import states.PlayState;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Goal extends Rectangle {
    public static int blockSize = Block.blockSize;

    private final int x;
    private int y;
    private final int origY;
    private Image img;
    private int tick;

    public Goal(int x, int y) {
        this.x = x;
        this.y = y - 5;
        origY = y;
        int width = blockSize;
        int height = blockSize;
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
        Graphics2D g2d = (Graphics2D)g.create();
        float alpha = 1f;
        switch(PlayState.getLevel()) {
            case 0 -> alpha = 0.9f;
            case 1 -> alpha = 0.7f;
            case 2 -> alpha = 0.5f;
        }
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g2d.drawImage(img, this.x, this.y, null);
    }
}
