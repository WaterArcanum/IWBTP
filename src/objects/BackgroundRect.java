package objects;

import main.GamePanel;
import states.DeathState;

import java.awt.*;
import java.util.Random;

public class BackgroundRect extends Rectangle {
    private int x;
    private int y;
    private Color color;
    private int width;
    private int height;
    private int direction;
    private int speed;
    private int rotation;
    private int alpha;
    private boolean small;

    public BackgroundRect(int x, int y, Color color, int width, int height, int direction) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.width = width;
        this.height = height;
        this.direction = direction;

        setBounds(x, y, width, height);
    }

    public BackgroundRect(boolean small) {
        this.small = small;
        if(small) generateSmall();
        else generateBig();
    }

    public void generateSmall() {
        Random rand = new Random();
        int x;
        int y;
        int width;
        int height;
        int dir;
        int bound = 500;
        int speed = rand.nextInt(5);
        int even = rand.nextInt(2);
        if(even % 2 == 0) {
            x = rand.nextInt(2) == 1 ?
                    rand.nextInt(bound) * -1 :
                    rand.nextInt(bound) + 720;
            y = rand.nextInt(12) * 60 - 75;
            width = 90;
            height = 30;
            dir = x < 0 ? 0 : 1;
        }
        else {
            x = rand.nextInt(12) * 60 - 75;
            y = rand.nextInt(2) == 1 ?
                    rand.nextInt(bound) * -1 :
                    rand.nextInt(bound) + 720;
            width = 30;
            height = 90;
            dir = y < 0 ? 2 : 3;
        }
        this.x = x;
        this.y = y;
        this.color = new Color(0, 255, 255, 75);
        this.width = width;
        this.height = height;
        this.direction = dir;
        this.speed = speed;
        this.rotation = 0;
    }

    public void generateBig() {
        Random rand = new Random();
        int x;
        int y;
        int width;
        int height;
        int rotation;
        int alpha;
        x = 0;
        y = 0;
        width = 720 * 2;
        height = 25;
        rotation = rand.nextInt(90);
        alpha = rand.nextInt(150) + 55;
        this.x = x;
        this.y = y;
        int base = rand.nextInt(50) + 100;
        this.color = switch (rand.nextInt(6)) {
            case 0 -> new Color(base, base-50, base-100);
            case 1 -> new Color(base, base-100, base-50);
            case 2 -> new Color(base-50, base, base-100);
            case 3 -> new Color(base-100, base, base-50);
            case 4 -> new Color(base-50, base-100, base);
            case 5 -> new Color(base-100, base-50, base);
            default -> new Color(base, base, base);
        };
        this.width = width;
        this.height = height;
        this.rotation = rotation;
        this.alpha = alpha;
    }

    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D)g.create();
        if(!small) color = new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);
        g2d.setColor(color);
        g2d.rotate(Math.toRadians(rotation));
        if(rotation == 0)
            g2d.drawRect(x, y, width, height);
        else
            g2d.fillRect(x, y, width, height);
    }

    public void fade() {
        if(alpha <= 0) {
            if(!DeathState.dead) generateBig();
        }
        else alpha-=1;
    }

    public void move() {
        int step = speed + 7;
        switch(direction) {
            case 0 -> x+=step;
            case 1 -> x-=step;
            case 2 -> y+=step;
            case 3 -> y-=step;
        }
        if((x > GamePanel.WIDTH*1.2 || x < -GamePanel.WIDTH*1.2 ||
                y > GamePanel.HEIGHT*1.2 || y < -GamePanel.HEIGHT*1.2) && !DeathState.dead) {
            generateSmall();
        }
    }
}
