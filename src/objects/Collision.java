package objects;

import java.awt.Point;

public class Collision {
    public static boolean playerBlock(int x1, int y1, int x2, int y2, Block b) {
        Point p1 = new Point(x1, y1);
        Point p2 = new Point(x2, y2);
        return b.contains(p1) || b.contains(p2);
    }
}
