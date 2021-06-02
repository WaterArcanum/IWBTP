package objects;

import states.PlayState;
import states.WinState;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public class Block extends Rectangle {
    public static int blockSize = 30;

    private final int x;
    private final int y;
    private final int width = blockSize;
    private final int height = blockSize;

    private int tickRand;
    public static int tickStat = 100;
    private boolean tickUp = true;

    private Image img;

    public Block(int x, int y, int even) {
        this.x = x;
        this.y = y;
        Random rand = new Random();
        tickRand = rand.nextInt(75) + 25;
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
                g.setColor(new Color(255, 81, 0, 20));
                g.fillRect(x, y, width, height);
            }
            case 1 -> {
                Graphics2D g2d = (Graphics2D)g.create();
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                        (float)Math.max(tickStat, 0)/255 * 0 + 0.25f));
                g2d.drawImage(img, this.x, this.y, null);

                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
                float percent = WinState.percent(0, 100, tickStat);
                int color = (int) (190 * (percent/100));
                g2d.setColor(new Color(0, color, color));
                g2d.drawRect(this.x, this.y, width, height);
            }
            case 2 -> {
                Random rand = new Random();
//                g.setColor(new Color(64, 64, 64));
//                g.drawRect(this.x, this.y, width, height);
                if(tickUp) {
                    if(rand.nextInt(5) > 1) tickRand +=2;
                }
                else if(rand.nextInt(5) > 1) tickRand -=2;
                if(tickRand >= 100) tickUp = false;
                if(tickRand <= 25) tickUp = true;
                float percent = WinState.percent(0, 100, tickRand);
                float color = 168 * (percent/100);
                g.setColor(new Color((int)color, 0, 0, (int)color));
                g.drawRect(this.x, this.y, width, height);
            }
            case 3 -> {
                g.setColor(Color.WHITE);
                g.fillRect(this.x, this.y, width, height);
            }
        }
    }
}
