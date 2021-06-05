package states;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

import static main.Game.*;
import static main.Game.KEY_ENTER;

public class OptionsState extends GameState {
    private final String[] options = {"Extremely easy", "Very easy", "Easy"};
    private int current = 0;

    public OptionsState(GameStateManager gsm) {
        super(gsm);
    }

    protected void init() {

    }

    protected void tick() {

    }

    protected void draw(Graphics g) {
        int width = GamePanel.WIDTH;
        int height = GamePanel.HEIGHT;

        g.setColor(new Color(9, 5, 95));
        g.fillRect(0, 0, width, height);

        g.setFont(new Font("Exo", Font.BOLD, 72));
        g.setColor(Color.WHITE);
        g.drawString("Choose a difficulty", 5, 72);

        for (int i = 0; i < options.length; i++) {
            if(i == current) g.setColor(new Color(150, 150, 255));
            else g.setColor(new Color(255, 255, 255));
            g.drawString(options[i], width / 5, height / options.length + 20 + i * 150);
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
            GameStateManager.difficulty = current;
            GameStateManager.states.push(new MenuState(gsm));
        }
    }

    protected void keyReleased(int k) {

    }
}
