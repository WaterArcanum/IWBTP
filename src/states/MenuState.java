package states;

import main.AudioManager;
import main.GamePanel;

import javax.imageio.ImageIO;

import static main.Game.*;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class MenuState extends GameState {
    private final String[] options = {"Start", "Tutorial", "Exit"};
    private int current = 0;
    public static long start;
    private boolean played = false;
    private AudioManager bgm;
    public static boolean tutorial;

    public MenuState(GameStateManager gsm) {
        super(gsm);
    }

    protected void init() {
        tutorial = false;
        start = System.currentTimeMillis();
    }
    protected void tick() {
        long finish = System.currentTimeMillis();
        long timeElapsed = finish - start;
        if(timeElapsed > 50 && !played) {
            bgm = new AudioManager("resources/audio/menu.wav");
            bgm.start(true, true);
            played = true;
        }
    }

    protected void draw(Graphics g) {
        int width = GamePanel.WIDTH;
        int height = GamePanel.HEIGHT;

        long finish = System.currentTimeMillis();
        long timeElapsed = finish - start;
        float textPercent = WinState.percent(0, 5000, timeElapsed)/100;
        float bgPercent = WinState.percent(0, 10000, timeElapsed)/100;

        g.setColor(new Color((int)(bgPercent * 9), (int)(bgPercent * 5), (int)(bgPercent * 95)));
        g.fillRect(0, 0, width, height);

        for (int i = 0; i < options.length; i++) {
            if(i == current) g.setColor(new Color(150, 150, 255, (int)(textPercent * 254)));
            else g.setColor(new Color(255, 255, 255, (int)(textPercent * 255)));

            g.setFont(new Font("Exo", Font.BOLD, 72));
            g.drawString(options[i], width / 3, height / options.length + 20 + i * 150);
        }

        try {
            Image img = ImageIO.read(new File("resources/imgs/title.png"));
            g.drawImage(img, -600, 0,null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void keyPressed(int k) {
        if(KEY_DOWN.contains(k)) {
            current++;
            if(current >= options.length) current = 0;
        }
        else if(KEY_UP.contains(k)) {
            current--;
            if(current < 0) current = options.length-1;
        }

        if(KEY_EXIT.contains(k)) {
            System.exit(0);
        }

        if(KEY_ENTER.contains(k)) {
            bgm.stop(false);
            switch (current) {
                case 0 -> {
                    start = System.currentTimeMillis();
                    PlayState.savePointId = 0;
                    GameStateManager.states.push(new PlayState(gsm));
                }
                case 1 -> {
                    tutorial = true;
                    GameStateManager.states.push(new PlayState(gsm));
                }
                case 2 -> System.exit(0);
            }
        }
    }

    protected void keyReleased(int k) {

    }
}
