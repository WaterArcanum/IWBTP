package objects;

import states.PlayState;
import states.WinState;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Spike extends Polygon {
    public static int blockSize = Block.blockSize;
    private static final int thinness = 1;

    private Image img;
    private int tick;
    private boolean tickUp = true;
    private final int x;
    private final int y;

    public Spike(int x, int y, int rotation) {
        this.x = x;
        this.y = y;
        Random rand = new Random();
        tick = rand.nextInt(75) + 25;

        x += blockSize;
        y += blockSize;
        int x0 = x - blockSize;
        int x1 = x - (blockSize / 2);
        int x2 = x;
        int y0 = y;
        int y1 = y - (blockSize / 2);
        int y2 = y - blockSize;
        // The spike sprite was way too ugly.
        String pathname = "resources/imgs/spike";
        switch (rotation) {
            // Facing up
            case 1 -> {
                addPoint(x0+thinness, y0); // Left point
                addPoint(x2-thinness, y0); // Right point
                addPoint(x1, y2+thinness*3); // Middle point
                pathname += "up";
            }
            // Facing down
            case 2 -> {
                addPoint(x0+thinness, y2); // Left point
                addPoint(x2-thinness, y2); // Right point
                addPoint(x1, y0-thinness*3); // Middle point
                pathname += "down";
            }
            // Facing left
            case 3 -> {
                addPoint(x0+thinness*3, y1); // Middle point
                addPoint(x2, y2+thinness); // Upper point
                addPoint(x2, y0-thinness); // Lower point
                pathname += "left";
            }
            // Facing right
            case 4 -> {
                addPoint(x2-thinness*3, y1); // Middle point
                addPoint(x0, y2+thinness); // Upper point
                addPoint(x0, y0-thinness); // Lower point
                pathname += "right";
            }
        }

        try {
            img = ImageIO.read(new File(pathname+".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D)g.create();
        switch(PlayState.getLevel()) {
            // Ticks for sprite changes
            case 0 -> {
                if(tickUp) tick++;
                else tick--;
                if(tick >= 100) tickUp = false;
                if(tick <= 50) tickUp = true;
                float percent = WinState.percent(0, 100, tick);
                float color = 190 * (percent/100);
                g2d.setColor(new Color((int)color, 0, (int)color));
                g2d.draw(this);
            }
            case 1 -> {
//                if(tickUp) tick++;
//                else tick--;
//                if(tick >= 100) tickUp = false;
//                if(tick <= 50) tickUp = true;
//
//                float percent = WinState.percent(0, 100, tick);
//                float color = 153 * (percent/100);
//                g2d.setColor(new Color((int)color, (int)color, (int)color, (int)color));
//                g2d.draw(this);
                int tickStat = Block.tickStat;
                float percent = WinState.percent(0, 100, tickStat);
                int color = (int) (190 * (percent/100));
                g2d.setColor(new Color(0, Math.max(color-50, 0), color));
                g2d.draw(this);
            }
            case 2 -> {
//                g2d.setColor(new Color(255, 0, 0, 100));
//                g2d.draw(this);
//                g2d.setColor(new Color(168, 0, 0, 50));
//                g2d.fill(this);

                Random rand = new Random();
                if(tickUp) {
                    if(rand.nextInt(5) > 1) tick+=2;
                }
                else if(rand.nextInt(5) > 1) tick-=2;
                if(tick >= 100) tickUp = false;
                if(tick <= 25) tickUp = true;
                float percent = WinState.percent(0, 100, tick);
                float color = 168 * (percent/100);
                g2d.setColor(new Color((int)color, 0, 0, (int)color));
//                g2d.setColor(new Color(168, 0, 0, 50));
                g2d.draw(this);
                g2d.fill(this);
            }
            case 3 -> {
                g2d.setColor(Color.WHITE);
                g2d.fill(this);
            }
        }
    }
}
