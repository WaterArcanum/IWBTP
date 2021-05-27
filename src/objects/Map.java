package objects;

import java.awt.*;
import java.io.*;

public class Map {
    private String path;
    private int width, height;

    private final Block[][] blocks;
    private final Spike[][] spikes;

    public Map(String prath) {
        path = prath;
        width = 24;
        height = 24;
        blocks = new Block[height][width];
        spikes = new Spike[height][width];

        /*for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[0].length; j++) {
                blocks[i][j] = new Block(j * Block.blockSize * 5, 5 * i * Block.blockSize);
            }
        }*/

        loadMap();
    }

    public void draw(Graphics g) {
        for (Block[] block : blocks) {
            for (int i = 0; i < blocks[0].length; i++) {
                if (block[i] != null) {
                    block[i].draw(g);
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
            for (int y = 0; y < height; y++) {
                String[] tokens = br.readLine().split("");

                for (int x = 0; x < width; x++) {
                    int token = Integer.parseInt(tokens[x]);
                    int xpos = x * Block.blockSize;
                    int ypos = y * Block.blockSize;
                    switch (token) {
                        case 1 -> blocks[y][x] = new Block(xpos, ypos);
                        case 2 -> spikes[y][x] = new Spike(xpos, ypos, 1);
                        case 3 -> spikes[y][x] = new Spike(xpos, ypos, 2);
                        case 4 -> spikes[y][x] = new Spike(xpos, ypos, 3);
                        case 5 -> spikes[y][x] = new Spike(xpos, ypos, 4);
                    }
//                    blocks[y][x] = new Block(xpos, ypos);
//                    System.out.println("Block placed at X:"+xpos+" | Y:"+ypos);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Block[][] getBlocks() {
        return blocks;
    }

    public Spike[][] getSpikes() {
        return spikes;
    }
}
