package main;

import objects.*;
import states.PlayState;

import javax.imageio.ImageIO;

import static main.Game.*;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Player {

    // Bounds
    private double x;
    private double y;
    private final int width = 15;
    private final int height = 18;

    // Speeds
    private final double moveSpeed = 3.5;

    private final double jumpSpeed = 3.5;
    private final double maxJumpDelta = 2.5;
    private double jumpDelta = 0;
    private double currentJumpSpeed = jumpSpeed;

    private final double maxFallSpeed = 3.5;
    private double currentFallSpeed = 0.1;

    // Movement
    private boolean left = false;
    private boolean right = false;
    private boolean jumping = false;
    private boolean falling = true;
    private boolean dead = false;
    private boolean fallingFromBlock = false;
    private boolean bottomCollision = false;
    private boolean topCollision = false;
    private boolean leftCollision = false;
    private boolean rightCollision = false;

    // Key-based movement
    private boolean stopLeft = true;
    private boolean stopRight = true;
    private boolean stopJump = true;
    private boolean hasSecondJump = true;

    // Audio
    private final AudioManager jump1 = new AudioManager("resources/audio/jump1.wav", -10);
    private final AudioManager jump2 = new AudioManager("resources/audio/jump2.wav", -10);

    // Draw variables
    private int facing = 1;
    private int leftTick = 0;
    private int rightTick = 0;
    private int jumpTick = 0;
    private int fallTick = 0;

    public Player(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void tick(Block[][] b, Spike[][] s, SavePoint[][] sp, Goal g) {
        double cRight = x + width;
        double cLeft = x;
        double cTop = y;
        double cBottom = y + height;

        left = !stopLeft;
        right = !stopRight;

        // Check block collision
        for (Block[] blocks : b) {
            for (int j = 0; j < b.length; j++) {
                if (blocks[j] != null) {
                    Block block = blocks[j];
                    // Bottom collision
                    if (Collision.playerBlock(cRight-5, cBottom+1, block) ||
                            Collision.playerBlock(cLeft+5, cBottom+1, block)) {
                        y = block.getY() - height;
                        hasSecondJump = true;
                        falling = false;
                        bottomCollision = true;
                        fallingFromBlock = false;
                    }
                    // Right collision
                    if (Collision.playerBlock(cRight+3, cBottom-1, block) ||
                            Collision.playerBlock(cRight+3, cTop+3, block)) {
                        right = false;
                    }
                    // Left collision
                    if (Collision.playerBlock(cLeft-3, cBottom-1, block) ||
                            Collision.playerBlock(cLeft-3, cTop+3, block)) {
                        left = false;
                    }
                    // Top collision
                    if (Collision.playerBlock(cRight-5, cTop, block) ||
                            Collision.playerBlock(cLeft+5, cTop, block)) {
                        jumping = false;
                        falling = true;
                    }
                    // Enable falling physics
                    if (!bottomCollision && !jumping) {
                        falling = true;
                        fallingFromBlock = true;
                    }
                }
            }
        }
        // Check spike collision
        for (Spike[] spikes : s) {
            for (int j = 0; j < s.length; j++) {
                if(spikes[j] != null) {
                    if(Collision.playerSpike(x, y, width, height, spikes[j])) {
                        dead = true;
                        PlayState.die();
                    }
                }
            }
        }
        // Check savepoint collision
        for (SavePoint[] savePoints : sp) {
            for (int j = 0; j < s.length; j++) {
                if(savePoints[j] != null) {
                    if(Collision.playerSave(x, y, width, height, savePoints[j])) {
                        PlayState.save(savePoints[j]);
                    }
                }
            }
        }
        // Check block collision
        if(Collision.playerGoal(x, y, g)) {
            PlayState.win();
        }

        bottomCollision = false;
        topCollision = false;
        leftCollision = false;
        rightCollision = false;

        x -= left ? moveSpeed : 0;
        x += right ? moveSpeed : 0;

        if(!stopJump && jumpDelta < maxJumpDelta) {
            jumpDelta += 0.1;
        }

        if(jumping) {
            y -= currentJumpSpeed;

            if(jumpDelta >= maxJumpDelta || stopJump) {
                currentJumpSpeed += (currentJumpSpeed > .2) ? 0.05 : 0.005;
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
                // Falling speed calculation magic
                if(fallingFromBlock) currentFallSpeed += .15;
                else currentFallSpeed +=
                        (currentFallSpeed > .25) ? .15 :
                        (currentFallSpeed > .2) ? 0.05 : 0.005;
            }
        }
        else {
            currentFallSpeed = .1;
        }
    }

    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        // Facing = { 0 : LEFT || 1 : RIGHT }
        int drawX = (int)x - ((facing-1) * width);
        int drawY = (int)y;
        int drawWidth = width * ((facing * 2) + -1);
        String pathname = "resources/imgs/";
        if(!dead) {
            // Ticks for sprite changes
            if (left) {
                facing = 0;
                if (!jumping && !falling) {
                    leftTick += 1;
                    if (leftTick == 21) leftTick = 0;
                    if (leftTick <= 10) pathname += "walk1.png";
                    else pathname += "walk2.png";
                }
                rightTick = 0;
            } else if (right) {
                facing = 1;
                if (!jumping && !falling) {
                    rightTick += 1;
                    if (rightTick == 21) rightTick = 0;
                    if (rightTick <= 10) pathname += "walk1.png";
                    else pathname += "walk2.png";
                }
                leftTick = 0;
            }
            if (jumping) {
                jumpTick += 1;
                if (jumpTick == 9) jumpTick = 0;
                if (jumpTick <= 4) pathname += "jump1.png";
                else pathname += "jump2.png";
            } else if (falling) {
                jumpTick = 0;
                fallTick += 1;
                if (fallTick == 11) fallTick = 0;
                if (fallTick <= 5) pathname += "fall1.png";
                else pathname += "fall2.png";
            } else fallTick = 0;
            if (pathname.charAt(pathname.length() - 1) == '/') {
                pathname += "idle.png";
            }
        }
        else {
            pathname += "dead.png";
        }
        try {
            Image img = ImageIO.read(new File(pathname));
            g.drawImage(img, drawX, drawY, drawWidth, height, null);
            // Tutorial arrows
            if(PlayState.getLevel() == 3 && PlayState.getTime() < 15000) {
                img = ImageIO.read(new File("resources/imgs/arrows.png"));
                g.drawImage(img, (int)x-55, (int)y-60, null);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void keyPressed(int k) {
        if(KEY_LEFT.contains(k)) {
            stopLeft = false;
        }
        if(KEY_RIGHT.contains(k)) {
            stopRight = false;
        }
        if(KEY_JUMP.contains(k) && !jumping && hasSecondJump && stopJump) {
            if(falling) {
                jumpDelta = 0.1;
                hasSecondJump = false;
                jump2.start(false, true);
            }
            else jump1.start(false, true);
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
