package states;

import main.GamePanel;
import static main.Game.*;

import java.awt.*;

public class MenuState extends GameState {
    private final String[] options = {"Start", "Credits", "Exit"};
    private int current = 0;

    public MenuState(GameStateManager gsm) {
        super(gsm);
    }

    protected void init() {}
    protected void tick() {}

    protected void draw(Graphics g) {
        int width = GamePanel.WIDTH;
        int height = GamePanel.HEIGHT;
        g.setColor(new Color(50, 100, 200));
        g.fillRect(0, 0, width, height);

        for (int i = 0; i < options.length; i++) {
            if(i == current) g.setColor(Color.GREEN);
            else g.setColor(Color.WHITE);

            g.setFont(new Font("Exo", Font.BOLD, 72));
            g.drawString(options[i], GamePanel.WIDTH / 2 - 50, 100 + i * 150);
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
            switch (current) {
                case 0:
                    GameStateManager.states.push(new PlayState(gsm));
                    break;
                case 1:
                    break;
                case 2:
                    System.exit(0);
                    break;
            }
        }
    }

    protected void keyReleased(int k) {

    }
}
