package objects;

import java.awt.geom.Point2D;

public class Collision {

    public static boolean playerBlock(double x, double y, Block b) {
        Point2D.Double p = new Point2D.Double(x, y);
        return b.contains(p);
    }

    public static boolean playerSpike(double x, double y, double w, double h, Spike s) {
        return s.intersects(x, y, w, h);
    }

    public static boolean playerSave(double x, double y, double w, double h, SavePoint sp) {
        return sp.intersects(x, y, w, h);
    }

    public static boolean playerGoal(double x, double y, Goal g) {
        Point2D.Double p = new Point2D.Double(x, y);
        return g.contains(p);
    }
}
