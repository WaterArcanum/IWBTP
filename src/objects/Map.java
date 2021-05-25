package objects;

import java.awt.*;
import java.io.*;

public class Map {
    private String path;
    private int width, height;

    private final Block[][] blocks;

    public Map(String prath) {
        path = prath;
        width = 24;
        height = 24;
        blocks = new Block[height][width];

        /*for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[0].length; j++) {
                blocks[i][j] = new Block(j * Block.blockSize * 5, 5 * i * Block.blockSize);
            }
        }*/

        loadMap();
    }

    public void draw(Graphics g) {
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[0].length; j++) {
                blocks[i][j].draw(g);
            }
        }
        Spike s = new Spike(150, 150);
        s.draw(g);
    }

    public void loadMap() {
        File file = new File(path);
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            for (int y = 0; y < height; y++) {
                String[] tokens = br.readLine().split("");

                for (int x = 0; x < width; x++) {
                    int token = Integer.parseInt(tokens[x]);
                    int xpos = token * Block.blockSize * x;
                    int ypos = y * Block.blockSize;
                    blocks[y][x] = new Block(xpos, ypos);
                    System.out.println("Block placed at X:"+xpos+" | Y:"+ypos);
                }
            }
            System.out.println();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Block[][] getBlocks() {
        return blocks;
    }
}
