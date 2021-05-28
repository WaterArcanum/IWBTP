package states;

import entities.Player;
import main.AudioManager;
import main.GamePanel;
import objects.Map;

import java.awt.*;

import static main.Game.*;

public class PlayState extends GameState {
    private static GameStateManager gsm;
    private static Map map;
    private static Player player;
    private static final AudioManager bgm = new AudioManager("imgs/stage3.wav");
    private static final AudioManager deathSound = new AudioManager("imgs/death.wav");

    public PlayState(GameStateManager gsm) {
        super(gsm);
    }

    protected void init() {
        map = new Map("maps/map1.map");
        player = new Player(260, 90);
        bgm.start(true, false);
    }

    public static void restart() {
        //bgm.stop(true);
        deathSound.stop(false);
        GameStateManager.states.push(new PlayState(gsm));
    }

    public static void die() {
        bgm.stop(true);
        deathSound.start(false, true);
        GameStateManager.states.push(new DeathState(gsm, map, player));
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
        if(KEY_RESET.contains(k)) restart();
    }

    protected void keyReleased(int k) {
        player.keyReleased(k);
    }
}
