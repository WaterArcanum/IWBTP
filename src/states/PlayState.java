package states;

import entities.Player;
import objects.Map;

import java.awt.*;

import static main.Game.*;

public class PlayState extends GameState {
    private Player player;
    private Map map;

    public PlayState(GameStateManager gsm) {
        super(gsm);
    }

    protected void init() {
        map = new Map("maps/map1.map");
        player = new Player(150, 150);
    }

    protected void tick() {
        player.tick(map.getBlocks(), map.getSpikes());
    }

    protected void draw(Graphics g) {
        player.draw(g);
        map.draw(g);
    }

    protected void keyPressed(int k) {
        player.keyPressed(k);
        if(KEY_RESET.contains(k)) gsm.states.push(new PlayState(gsm));
    }

    protected void keyReleased(int k) {
        player.keyReleased(k);
    }
}
