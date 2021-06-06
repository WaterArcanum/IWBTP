package states;

import main.Player;
import main.GamePanel;
import objects.Map;

import javax.imageio.ImageIO;

import static main.Game.*;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class DeathState extends GameState {
    private final Map map;
    private final Player player;
    private long start;
    private long finish;
    public static boolean dead;

    public DeathState(GameStateManager gsm, Map map, Player player) {
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
        dead = true;
        map.draw(g);
        player.draw(g);
        long timeElapsed = finish - start;
        int height = GamePanel.HEIGHT;
        if(timeElapsed > 500) {
            g.setColor(new Color(0, 0, 0, 150));
            g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
            try {
                String pathname = "resources/imgs/gameover.png";
                Image img = ImageIO.read(new File(pathname));
                g.drawImage(img, 10, height / 2 - 137 / 2, null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    protected void keyPressed(int k) {
        dead = false;
        if(KEY_RESET.contains(k)) PlayState.restart();
        if(KEY_EXIT.contains(k)) PlayState.exit();
    }

    protected void keyReleased(int k) {

    }
}
