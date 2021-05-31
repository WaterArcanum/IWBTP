package states;

import main.GamePanel;
import objects.Map;

import java.awt.*;

import java.lang.Math;

public class WinState extends GameState {
    private final Map map;
    private long start;
    private long finish;

    public WinState(GameStateManager gsm, Map map) {
        super(gsm);
        this.map = map;
    }

    protected void init() {
        start = System.currentTimeMillis();
    }

    protected void tick() {
        finish = System.currentTimeMillis();
    }

    public static float percent(float min, float max, float val) {
        float result = ((val-min)/(max-min))*100;
        return result <= 100 ? result : 100;
    }

    // Change colour from $colorStart to $color based on $val percentage between $min & $max
    private static void colorLerp(Graphics g, int min, int max, long val, int color, int opacity,
                                  int colorStart, int opacityStart) {
        float percent = percent(min, max, val) / 100;
        float colorPercent = colorStart - ((colorStart-color) * percent);
        float opacityPercent = opacityStart - ((opacityStart-opacity) * percent);
        g.setColor(new Color((int)colorPercent, (int)colorPercent, (int)colorPercent, (int)opacityPercent));
    }

    protected void draw(Graphics g) {
        int width = GamePanel.WIDTH;
        int height = GamePanel.HEIGHT;
        map.draw(g);
        int transition1 = 1187;
        int transition2 = 2684;
        int bgColor = 76;
        int bgOpacity = 150;
        int textYOffset = 72;
        int textXDefault = 100;
        int textYDefault = height / 3;
        long timeElapsed = finish - start;
        // Timed text on screen
        if(timeElapsed < transition1) {
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, width, height);
        }
        else if (timeElapsed < transition2) {
            colorLerp(g, transition1, transition2, timeElapsed, bgColor, bgOpacity, 255, 255);
            g.fillRect(0, 0, width, height);
        }
        if(timeElapsed > transition2) {
            g.setColor(new Color(bgColor, bgColor, bgColor, bgOpacity));
            g.fillRect(0, 0, width, height);
            g.setFont(new Font("Exo", Font.BOLD, 36));
            g.setColor(Color.WHITE);
        }
        if(timeElapsed > 3363) {
            g.drawString("Stage " + (PlayState.getLevel()+1) + " clear!", textXDefault, textYDefault);
        }
        if(timeElapsed > 3838) {
            int deaths = PlayState.getDeaths();
            g.drawString((deaths > 1 ? deaths : "No") + " death" + (deaths != 1 ? "s" : ""), textXDefault, textYDefault + textYOffset);
        }
        if(timeElapsed > 4312) {
            double time = (Math.round(PlayState.getTime() * 100.0) / 100.0) / 1000;
            g.drawString(time + " seconds", textXDefault, textYDefault + textYOffset * 2);
        }
        if(timeElapsed > 4803) {
            g.drawString("Press any key to continue.", textXDefault, textYDefault + textYOffset * 3);
        }
        if(timeElapsed > 5157 && timeElapsed < 8000) {
            colorLerp(g, 5157, 8000, timeElapsed, bgColor-50, bgOpacity-50, bgColor, bgOpacity);
            g.fillRect(0, 0, width, height);
        }
        if(timeElapsed > 8000) {
            g.setColor(new Color(bgColor-50, bgColor-50, bgColor-50, bgOpacity-50));
            g.fillRect(0, 0, width, height);
        }
    }

    protected void keyPressed(int k) {
        long timeElapsed = finish - start;
        if(timeElapsed > 4823) PlayState.progress();
    }

    protected void keyReleased(int k) {

    }
}
