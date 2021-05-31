package states;

import main.GamePanel;
import main.Player;
import objects.Map;

import java.awt.*;

public class WinState extends GameState {
    private final Map map;
    private final Player player;
    private long start;
    private long finish;

    public WinState(GameStateManager gsm, Map map, Player player) {
        super(gsm);
        this.map = map;
        this.player = player;
    }

    protected void init() {
        start = System.currentTimeMillis();
    }

    protected void tick() {
        finish = System.currentTimeMillis();
    }

    protected void draw(Graphics g) {
        int width = GamePanel.WIDTH;
        int height = GamePanel.HEIGHT;
        map.draw(g);
        player.draw(g);
        int transition1 = 1187;
        int transition2 = 2684;
        int bgColor = 76;
        int bgOpacity = 150;
        int textYOffset = 50;
        int textXDefault = 250;
        int textYDefault = height / 4;
        long timeElapsed = finish - start;
        if(timeElapsed < transition1) {
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, width, height);
        }
        else if (timeElapsed < transition2) {
            float percent = ((timeElapsed - transition1) / (float)transition2) * 2;
            float colorPercent = 255 - ((255-bgColor) * percent);
            float opacityPercent = 255 - ((255-bgOpacity) * percent);
            if(colorPercent < bgColor) colorPercent = bgColor;
            if(opacityPercent < bgOpacity) opacityPercent = bgOpacity;
            System.out.println(colorPercent);
            g.setColor(new Color((int)colorPercent, (int)colorPercent, (int)colorPercent, (int)opacityPercent));
            g.fillRect(0, 0, width, height);
        }
        if(timeElapsed > transition2) {
            g.setColor(new Color(bgColor, bgColor, bgColor, bgOpacity));
            g.fillRect(0, 0, width, height);
            g.setFont(new Font("Exo", Font.BOLD, 18));
            g.setColor(Color.BLACK);
        }
        if(timeElapsed > 3383) {
            g.drawString("Stage " + (PlayState.getLevel()+1) + " clear!", textXDefault, textYDefault);
        }
        if(timeElapsed > 3858) {
            g.drawString(PlayState.getDeaths() + " deaths", textXDefault, textYDefault + textYOffset);
        }
        if(timeElapsed > 4332) {
            g.drawString(PlayState.getTime() + " seconds", textXDefault, textYDefault + textYOffset * 2);
        }
        if(timeElapsed > 4823) {
            g.drawString("Press any key to continue.", textXDefault, textYDefault + textYOffset * 3);
        }
        if(timeElapsed > 5157 && timeElapsed < 8000) {
            g.setColor(new Color(bgColor, bgColor, bgColor, bgOpacity-50));
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
