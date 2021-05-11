package objects;

import java.awt.*;
import java.io.*;

public class Map {
    private String path;
    private int width, height;

    private final Block[][] blocks;

    public Map(String prath) {
        path = prath;
        width = 10;
        height = 10;
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
    }

    public void loadMap() {
        File file = new File(path);
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    blocks[y][x] = new Block(x * Block.blockSize, y * Block.blockSize);
                }
            }
            String[] tokens = br.readLine().split("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Block[][] getBlocks() {
        return blocks;
    }
}
