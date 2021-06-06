package objects;

import states.PlayState;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class SavePoint extends Rectangle {
    public static int blockSize = Block.blockSize;

    private final int x;
    private final int y;

    private final int id;

    private Image imgOff;
    private Image imgOn;

    public SavePoint(int x, int y, int id) {
        this.x = x;
        this.y = y;
        this.id = id;
        int width = blockSize;
        int height = blockSize;
        setBounds(x, y, width, height);
        try {
            imgOff = ImageIO.read(new File("resources/imgs/save.png"));
            imgOn = ImageIO.read(new File("resources/imgs/saved.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        Graphics2D g2d = (Graphics2D)g.create();
        float alpha = 1f;
        switch(PlayState.getLevel()) {
            case 0 -> alpha = 0.5f;
            case 1 -> alpha = 0.3f;
            case 2 -> alpha = 0.15f;
        }
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        if(id == PlayState.savePointId) g2d.drawImage(imgOn, x, y, null);
        else g2d.drawImage(imgOff, x, y, null);
    }
}
