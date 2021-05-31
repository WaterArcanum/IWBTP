package states;

import main.Player;
import main.AudioManager;
import objects.Map;
import objects.SavePoint;

import java.awt.*;

import static main.Game.*;

public class PlayState extends GameState {
    private static GameStateManager gsm;
    private static Map map;
    private static Player player;
    private static final AudioManager[] bgm = {
            new AudioManager("resources/audio/stage1.wav"),
            new AudioManager("resources/audio/stage2.wav"),
            new AudioManager("resources/audio/stage3.wav"),
            new AudioManager("resources/audio/tutorial.wav"),
            new AudioManager("resources/audio/goodbye.wav")
    };
    private static final AudioManager deathSound = new AudioManager("resources/audio/death.wav", -10);
    private static final AudioManager winSound = new AudioManager("resources/audio/win.wav", -10);
    private static int level;
    private static int deaths;
    private static long start;
    private static long finish;
    public static int savePointId;

    public PlayState(GameStateManager gsm) {
        super(gsm);
    }

    protected void init() {
        start = MenuState.start;
        if(MenuState.tutorial) level = 3;
        else if(level == 3) level = 0;
        String path = "resources/maps/map" + (level + 1) + ".map";
        map = new Map(path);
        double x = level < 3 ? 30 : 360;
        double y = x;
        // Set x/y position if there is a savepoint activated
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

    // Move to next level
    public static void progress() {
        start = System.currentTimeMillis();
        savePointId = 0;
        bgm[level].stop(false);
        level += 1;
        deaths = 0;
        if(level == 3) level = 4;
        PlayState.restart();
    }

    // Initiate win screen
    public static void win() {
        savePointId = 0;
        bgm[level].stop(true);
        winSound.start(false, true);
        if(level == 3) GameStateManager.states.push(new MenuState(gsm));
        else GameStateManager.states.push(new WinState(gsm, map));
    }

    // Restart the level
    public static void restart() {
        deathSound.stop(false);
        winSound.stop(false);
        GameStateManager.states.push(new PlayState(gsm));
    }

    // Initiate death screen
    public static void die() {
        deaths++;
        bgm[level].stop(true);
        deathSound.start(false, true);
        GameStateManager.states.push(new DeathState(gsm, map, player));
    }

    public static void save(SavePoint sp) {
        savePointId = sp.getId();
    }

    // Exit to menu
    public static void exit() {
        bgm[level].stop(false);
        deathSound.stop(false);
        winSound.stop(false);
        GameStateManager.states.push(new MenuState(gsm));
    }

    public static long getTime() {
        return finish - start;
    }

    public static int getDeaths() {
        return deaths;
    }

    public static int getLevel() {
        return level;
    }

    protected void tick() {
        finish = System.currentTimeMillis();
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
