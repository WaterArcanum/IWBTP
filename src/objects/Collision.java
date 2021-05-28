package objects;

import java.awt.*;

public class Collision {
    public static boolean playerBlock(int x1, int y1, int x2, int y2, Block b) {
        Point p1 = new Point(x1, y1);
        Point p2 = new Point(x2, y2);
        return b.contains(p1) || b.contains(p2);
    }

    public static boolean playerBlock(int x, int y, Block b) {
        Point p = new Point(x, y);
        return b.contains(p);
    }

    public static boolean playerSpike(double x, double y, double w, double h, Spike s) {
        return s.intersects(x, y, w, h);
    }

    public static boolean playerSave(double x, double y, double w, double h, SavePoint sp) {
        return sp.intersects(x, y, w, h);
    }

    public static boolean playerGoal(int x, int y, Goal g) {
        Point p = new Point(x, y);
        return g.contains(p);
    }
}
