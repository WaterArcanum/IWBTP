package objects;

import main.GamePanel;
import states.PlayState;
import states.WinState;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class Map {
    private final String path;
    private final int width;
    private final int height;

    private final Block[][] blocks;
    private final Spike[][] spikes;
    private final SavePoint[][] savePoints;
    private final BackgroundRect[] deco0;
    private final BackgroundRect[] deco1;
    private Goal goal;

    public static ArrayList<Integer> spikesTouched = new ArrayList<>();

    private double tick;
    private boolean tickUp = true;
    private boolean bTickUp;

    public Map(String path) {
        Random rand = new Random();
        tick = rand.nextInt(75) + 25;

        this.path = path;
        width = 24;
        height = 24;
        blocks = new Block[height][width];
        spikes = new Spike[height][width];
        savePoints = new SavePoint[height][width];
        deco1 = new BackgroundRect[10];
        deco0 = new BackgroundRect[10];

        /*for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[0].length; j++) {
                blocks[i][j] = new Block(j * Block.blockSize * 5, 5 * i * Block.blockSize);
            }
        }*/

        loadMap();
    }

    public void draw(Graphics g) {
        switch(PlayState.getLevel()) {
            case 0 -> {
                g.setColor(new Color(0, 36, 90));
                g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
                for(BackgroundRect br : deco0) {
                    br.move();
                    br.draw(g);
                }
            }
            case 1 -> {
                if(tickUp) tick+=0.5;
                else tick-=0.5;
                if(tick >= 100) tickUp = false;
                if(tick <= 25) tickUp = true;
                float percent = WinState.percent(0, 100, (float)tick);
                float color = 50 * (percent/100);
                g.setColor(new Color(0, (int)color, (int)color));
//                g.setColor(new Color(0, 36, 90));
                g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
                for(BackgroundRect br : deco1) {
                    br.fade();
                    br.draw(g);
                }
            }
            case 2 -> {
                g.setColor(new Color(0,0,0));
                g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
            }
            case 3 -> {
                g.setColor(Color.BLACK);
                g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
                g.setColor(Color.WHITE);
                g.setFont(new Font("Exo", Font.BOLD, 15));
                g.drawString("Welcome to IWBTP, an I Wanna Be The Guy / Delicious Fruit",
                        50, 500);
                g.drawString("fangame, created for a school assignment.",
                        50, 530);
                g.drawString("Use arrow keys / WAD / AL / Space / Shift to move and jump around.",
                        180, 600);
                g.drawString("Avoid spikes and jump on platforms to get to the ending sphere.",
                        180, 630);
                g.drawString("You may press 'R' to reset and 'Esc' to exit the level.",
                        180, 660);
                g.drawString("Avoid these", 38, 380);
                g.drawString("Jump on these", 423, 405);
                g.drawString("Use your double jump", 523, 250);
                g.drawString("Save your progress here", 470, 100);
                g.drawString("Reach the goal", 38, 120);
            }
            case 4 -> {
                g.setColor(Color.BLACK);
                g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
                if(!WinState.won) {
                    g.setColor(Color.WHITE);
                    g.setFont(new Font("Exo", Font.BOLD, 28));
                    g.drawString("Haha, you thought you won.",
                            175, 500);
                    g.drawString("But there is no escape. You may not win this game.", 25, 560);
                }
            }
        }

        for (Spike[] spike : spikes) {
            for (int i = 0; i < spikes[0].length; i++) {
                if (spike[i] != null) {
                    spike[i].draw(g);
                }
            }
        }

        int tickStat = Block.tickStat;
        if (bTickUp) tickStat += 1;
        else tickStat -= 1;
        Block.tickStat = tickStat;
        if (tickStat >= 100) bTickUp = false;
        if (tickStat <= 65) bTickUp = true;
        for (Block[] block : blocks) {
            for (int i = 0; i < blocks[0].length; i++) {
                if (block[i] != null) {
                    block[i].draw(g);
                }
            }
        }

        for (SavePoint[] savePoint : savePoints) {
            for (int i = 0; i < savePoints[0].length; i++) {
                if (savePoint[i] != null) {
                    savePoint[i].draw(g);
                }
            }
        }
        goal.draw(g);

//        Spike[] spikes = {
//            new Spike(150, 150, 1),
//            new Spike(180, 180, 2),
//            new Spike(210, 210, 3),
//            new Spike(240, 240, 4),
//        };
//        for (int i = 0; i < 4; i++) {
//            spikes[i].draw(g);
//        }
    }

    public void loadMap() {
        File file = new File(path);
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            int savesIdBuffer = 1;
            int spikesIdBuffer = 1;
            for (int y = 0; y < height; y++) {
                String[] tokens = br.readLine().split("");

                for (int x = 0; x < width; x++) {
                    int token = Integer.parseInt(tokens[x]);
                    int xpos = x * Block.blockSize;
                    int ypos = y * Block.blockSize;
                    // Check every token in the map, assign object to position on map
                    switch (token) {
                        case 1 -> blocks[y][x] = new Block(xpos, ypos);
                        case 2 -> {
                                spikes[y][x] = new Spike(xpos, ypos, 1, spikesIdBuffer);
                                spikesIdBuffer++;
                        }
                        case 3 -> {
                            spikes[y][x] = new Spike(xpos, ypos, 2, spikesIdBuffer);
                            spikesIdBuffer++;
                        }
                        case 4 -> {
                            spikes[y][x] = new Spike(xpos, ypos, 3, spikesIdBuffer);
                            spikesIdBuffer++;
                        }
                        case 5 -> {
                            spikes[y][x] = new Spike(xpos, ypos, 4, spikesIdBuffer);
                            spikesIdBuffer++;
                        }
                        case 6 -> goal = new Goal(xpos, ypos);
                        case 7 -> {
                            savePoints[y][x] = new SavePoint(xpos, ypos, savesIdBuffer);
                            savesIdBuffer++;
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(PlayState.getLevel() == 0) {
            for (int i = 0; i < 10; i++) {
                deco0[i] = new BackgroundRect(true);
            }
        }
        if(PlayState.getLevel() == 1) {
            for (int i = 0; i < 10; i++) {
                deco1[i] = new BackgroundRect(false);
            }
        }
    }

    public Block[][] getBlocks() {
        return blocks;
    }

    public Spike[][] getSpikes() {
        return spikes;
    }

    public SavePoint[][] getSavePoints() {
        return savePoints;
    }

    public Goal getGoal() {
        return goal;
    }
}
