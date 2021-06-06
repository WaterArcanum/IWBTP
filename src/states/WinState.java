package states;

import main.GamePanel;
import main.SaveManager;
import objects.Map;

import java.awt.*;

import java.lang.Math;

public class WinState extends GameState {
    private final Map map;
    private long start;
    private long finish;
    public static boolean won;

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
        won = true;
        int width = GamePanel.WIDTH;
        int height = GamePanel.HEIGHT;
        map.draw(g);
        int transition1 = 1187;
        int transition2 = 2684;
        int bgColor = 0;
        int bgOpacity = 100;
        int textYOffset = 72;
        long timeElapsed = finish - start;
        // Timed text on screen
        Font f = new Font("Exo", Font.BOLD, 26);
        Rectangle r = new Rectangle(180, 180, 360, 360);
        boolean notLast = PlayState.getLevel() != 4;
        SaveManager save = new SaveManager(SaveManager.getIndex());
        String text;
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
            g.setFont(f);
            g.setColor(Color.WHITE);
        }
        if(timeElapsed > 3363) {
//            g.drawString("Stage " + (PlayState.getLevel()+1) + " clear!", textXDefault, textYDefault);
            r.setBounds((int)r.getX(), (int)r.getY()+textYOffset, (int)r.getWidth(), (int)r.getHeight());
            text = notLast ? ("Stage " + (PlayState.getLevel()+1) + " clear!") : "Sorry, I lied.";
            OptionsState.centerTextX(g, text, r, f);
        }
        if(timeElapsed > 3838) {
            int deaths = notLast ? PlayState.getDeaths() : save.getDeaths();
//            g.drawString((deaths > 1 ? deaths : "No") + " death" + (deaths != 1 ? "s" : ""), textXDefault, textYDefault + textYOffset);
            r.setBounds((int)r.getX(), (int)r.getY()+textYOffset, (int)r.getWidth(), (int)r.getHeight());
            text = ((deaths > 1 ? deaths : "No") + " death" + (deaths != 1 ? "s" : ""));
            OptionsState.centerTextX(g, text, r, f);
        }
        if(timeElapsed > 4312) {
            double time = notLast ? (Math.round(PlayState.getTime() * 100.0) / 100.0) / 1000 :
                    (Math.round(save.getTime() * 100.0) / 100.0) / 1000;
//            g.drawString(time + " seconds", textXDefault, textYDefault + textYOffset * 2);
            r.setBounds((int)r.getX(), (int)r.getY()+textYOffset , (int)r.getWidth(), (int)r.getHeight());
            text = time + " seconds";
            OptionsState.centerTextX(g, text, r, f);
        }
        if(timeElapsed > 4803) {
//            g.drawString("Press any key to continue.", textXDefault, textYDefault + textYOffset * 3);
            r.setBounds((int)r.getX(), (int)r.getY()+textYOffset, (int)r.getWidth(), (int)r.getHeight());
            text = notLast ? "Press any key to continue." : "Congratulations, you won!";
            OptionsState.centerTextX(g, text, r, f);
        }
        if(timeElapsed > 5157) {
            if(timeElapsed < 8000) {
                colorLerp(g, 5157, 8000, timeElapsed, bgColor, bgOpacity - 50, bgColor, bgOpacity);
                g.fillRect(0, 0, width, height);
            }
            g.setColor(new Color(0, 0, 0, bgOpacity-25));
            g.fillRect(180, 180, 360, 360);
        }
        if(timeElapsed > 8000) {
            g.setColor(new Color(bgColor, bgColor, bgColor, bgOpacity-50));
            g.fillRect(0, 0, width, height);
        }
        g.setColor(Color.WHITE);
        g.drawRect(180, 180, 360, 360);
    }

    protected void keyPressed(int k) {
        if(PlayState.getLevel() != 4) {
            won = false;
            long timeElapsed = finish - start;
            if (timeElapsed > 4823) PlayState.progress();
        }
    }

    protected void keyReleased(int k) {

    }
}
