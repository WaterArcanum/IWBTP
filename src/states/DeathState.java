package states;

import entities.Player;
import objects.Map;
import static main.Game.*;

import java.awt.*;

public class DeathState extends GameState {
    private final Map map;
    private final Player player;

    public DeathState(GameStateManager gsm, Map map, Player player) {
        super(gsm);
        this.map = map;
        this.player = player;
    }

    protected void init() {
        System.out.println("you died lol");
    }

    protected void tick() {

    }

    protected void draw(Graphics g) {
        map.draw(g);
        player.draw(g);
    }

    protected void keyPressed(int k) {
        if(KEY_RESET.contains(k)) PlayState.restart();
    }

    protected void keyReleased(int k) {

    }
}
