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
        if(id == PlayState.savePointId) g.drawImage(imgOn, x, y, null);
        else g.drawImage(imgOff, x, y, null);
    }
}
