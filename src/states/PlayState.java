package states;

import entities.Player;
import main.AudioManager;
import objects.Map;
import objects.SavePoint;

import java.awt.*;
import java.util.Arrays;

import static main.Game.*;

public class PlayState extends GameState {
    private static GameStateManager gsm;
    private static Map map;
    private static Player player;
    private static final AudioManager[] bgm = {
            new AudioManager("resources/audio/stage1.wav"),
            new AudioManager("resources/audio/stage2.wav"),
            new AudioManager("resources/audio/stage3.wav")
    };
    private static final AudioManager deathSound = new AudioManager("resources/audio/death.wav");
    private static int level;
    private static int savePointId;

    public PlayState(GameStateManager gsm, int level) {
        super(gsm);
    }

    protected void init() {
        String path = "resources/maps/map" + (level + 1) + ".map";
        map = new Map(path);
        double x = 30;
        double y = 30;
        for (SavePoint[] savePoints : map.getSavePoints()) {
            for (SavePoint point : savePoints) {
                if(point != null) {
                    if (point.getId() == savePointId) {
                        x = point.getX();
                        y = point.getY();
                    }
                }
            }
        }
        player = new Player(x, y);
        bgm[level].start(true, false);
    }

    public static void progress() {
        bgm[level].stop(false);
        level += 1;
        PlayState.restart();
    }

    public static void restart() {
        //bgm.stop(true);
        deathSound.stop(false);
        GameStateManager.states.push(new PlayState(gsm, level));
    }

    public static void die() {
        bgm[level].stop(true);
        deathSound.start(false, true);
        GameStateManager.states.push(new DeathState(gsm, map, player));
    }

    public static void save(SavePoint sp) {
        savePointId = sp.getId();
    }

    public void exit() {
        bgm[level].stop(false);
        GameStateManager.states.push(new MenuState(gsm));
    }

    protected void tick() {
        player.tick(map.getBlocks(), map.getSpikes(), map.getSavePoints(), map.getGoal());
    }

    protected void draw(Graphics g) {
        map.draw(g);
        player.draw(g);
    }

    protected void keyPressed(int k) {
        player.keyPressed(k);
        if(KEY_RESET.contains(k)) restart();
        if(KEY_EXIT.contains(k)) exit();
    }

    protected void keyReleased(int k) {
        player.keyReleased(k);
    }
}
