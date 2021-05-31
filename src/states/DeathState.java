package states;

import main.Player;
import main.GamePanel;
import objects.Map;
import static main.Game.*;

import java.awt.*;

public class DeathState extends GameState {
    private final Map map;
    private final Player player;
    private long start;
    private long finish;

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
        map.draw(g);
        player.draw(g);
        long timeElapsed = finish - start;
        if(timeElapsed > 500) {
            g.setColor(new Color(76, 76, 76, 150));
            g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
            g.setFont(new Font("Exo", Font.BOLD, 18));
            g.setColor(Color.BLACK);
            g.drawString("Very temporary game over screen this is not final please stop asking about it",
                    GamePanel.WIDTH *0, GamePanel.HEIGHT / 2);
        }
    }

    protected void keyPressed(int k) {
        if(KEY_RESET.contains(k)) PlayState.restart();
        if(KEY_EXIT.contains(k)) PlayState.exit();
    }

    protected void keyReleased(int k) {

    }
}
