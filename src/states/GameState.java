package states;

import java.awt.*;

public abstract class GameState {
    protected GameStateManager gsm;

    public GameState(GameStateManager gsm) {
        this.gsm = gsm;
        init();
    }

    protected abstract void init();
    protected abstract void tick();
    protected abstract void draw(Graphics g);
    protected abstract void keyPressed(int k);
    protected abstract void keyReleased(int k);
}
