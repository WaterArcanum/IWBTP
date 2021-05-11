package states;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;

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
