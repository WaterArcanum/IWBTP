package entities;

import objects.Block;
import objects.Collision;

import javax.imageio.ImageIO;

import static main.Game.*;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Player {

    // Bounds
    private double x;
    private double y;
    private final int width = 20;
    private final int height = 30;

    // Speeds
    private final int moveSpeed = 5;

    private final double jumpSpeed = 5;
    private final double maxJumpDelta = 1.5;
    private double jumpDelta = 0;
    private double currentJumpSpeed = jumpSpeed;

    private final double maxFallSpeed = 5;
    private double currentFallSpeed = 0.1;

    // Movement
    private boolean left = false;
    private boolean right = false;
    private boolean jumping = false;
    private boolean falling = false;
    private boolean blockFall = false;
    private boolean topCollision = false;

    // Key-based movement
    private boolean stopLeft = true;
    private boolean stopRight = true;
    private boolean stopJump = true;
    private boolean secondJump = false;

    public Player(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void tick(Block[][] b) {
        int iX = (int)x;
        int iY = (int)y;

        int cRight = iX + width;
        int cLeft = iX;
        int cTop = iY;
        int cBottom = iY + height;

        left = !stopLeft;
        right = !stopRight;

        for (int i = 0; i < b.length; i++) {
            for (int j = 0; j < b.length; j++) {
                if (Collision.playerBlock(cRight, cTop + 2, cRight, cBottom - 1, b[i][j])) {
                    //System.out.println("RIGHT");
                    right = false;
                }
                if (Collision.playerBlock(cLeft - 1, cTop + 2, cLeft - 1, cBottom - 1, b[i][j])) {
                    //System.out.println("LEFT");
                    left = false;
                }
                if (Collision.playerBlock(cRight - 1, cTop, cLeft + 1, cTop, b[i][j])) {
                    //System.out.println("TOP");
                    y = b[i][j].getY() + height;
                    jumping = false;
                    falling = true;
                }
                if (Collision.playerBlock(cRight - 1, cBottom + 1, cLeft + 2, cBottom + 1, b[i][j])) {
                    //System.out.println("BOTTOM");
                    y = b[i][j].getY() - height;
                    secondJump = false;
                    falling = false;
                    topCollision = true;
                    blockFall = false;
                } else {
                    if (!topCollision && !jumping) {
                        falling = true;
                        blockFall = true;
                    }
                }
            }
        }

        topCollision = false;

        x -= left ? moveSpeed : 0;
        x += right ? moveSpeed : 0;

        if(!stopJump && jumpDelta < maxJumpDelta) {
            jumpDelta += 0.1;
            //System.out.println("jd: " + jumpDelta);
        }

        if(jumping) {
            y -= currentJumpSpeed;

            //System.out.println("cjs: " + currentJumpSpeed);
            //currentJumpSpeed -= 0.1;

            if(jumpDelta >= maxJumpDelta || stopJump) {
                currentJumpSpeed = jumpSpeed;
                jumping = false;
                falling = true;
            }
        }
        else {
            jumpDelta = 0;
        }

        if(falling) {
            y += currentFallSpeed;

            if(currentFallSpeed < maxFallSpeed) {
                // This is good enough :D
                if(blockFall) currentFallSpeed += .15;
                else currentFallSpeed += (currentFallSpeed > .25) ? .15 : (currentFallSpeed > .2) ? 0.05 : .05;
            }
        }
        else {
            currentFallSpeed = .1;
        }
    }

    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        try {
            Image img = ImageIO.read(new File("imgs/img.png"));
            g.drawImage(img, (int)x, (int)y, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //g.fillRect((int)x, (int)y, width, height);
    }

    public void keyPressed(int k) {
        if(KEY_LEFT.contains(k)) {
            stopLeft = false;
        }
        if(KEY_RIGHT.contains(k)) {
            stopRight = false;
        }
        if(KEY_JUMP.contains(k) && !jumping && !secondJump) {
            if(falling) {
                jumpDelta = 0.1;
                secondJump = true;
            }
            jumping = true;
            falling = false;
            stopJump = false;
        }
    }

    public void keyReleased(int k) {
        if(KEY_LEFT.contains(k)) {
            stopLeft = true;
        }
        if(KEY_RIGHT.contains(k)) {
            stopRight = true;
        }
        if(KEY_JUMP.contains(k)) {
            stopJump = true;
        }
    }
}
