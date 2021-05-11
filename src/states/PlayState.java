package states;

import entities.Player;
import objects.Block;
import objects.Map;

import java.awt.*;

import static main.Game.*;

public class PlayState extends GameState {
    private Player player;
    private Map map;
    //private Block[] blocks;

    public PlayState(GameStateManager gsm) {
        super(gsm);
    }

    protected void init() {
        //blocks = new Block[12];
        map = new Map("maps/map1.map");
        player = new Player(100, 50);
        /*for (int i = 0; i < 12; i++) {
            blocks[i] = new Block((1+i%2)*100, i*75);
        }*/
    }

    protected void tick() {
        player.tick(map.getBlocks());
    }

    protected void draw(Graphics g) {
        player.draw(g);
        map.draw(g);

        /*for (Block block : blocks) {
            block.draw(g);
        }*/
    }

    protected void keyPressed(int k) {
        player.keyPressed(k);
        if(KEY_RESET.contains(k)) gsm.states.push(new PlayState(gsm));
    }

    protected void keyReleased(int k) {
        player.keyReleased(k);
    }
}
