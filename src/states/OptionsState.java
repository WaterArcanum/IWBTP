package states;

import main.GamePanel;
import main.SaveManager;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;

import static main.Game.*;
import static main.Game.KEY_ENTER;

public class OptionsState extends GameState {
    private final String[] diffOptions = {"Extremely easy", "Very easy", "Easy"};
    private int current = 0;
    private int chosenSave;
    private SaveManager[] saves;
    private Image[] imgs;
    private boolean diffSelect = false;
    private final Color SELECTED_COLOR = new Color(150, 150, 255);

    public OptionsState(GameStateManager gsm) {
        super(gsm);
    }

    protected void init() {
        saves = new SaveManager[3];
        imgs = new Image[4];
        for (int i = 0; i < saves.length; i++) {
            saves[i] = new SaveManager(i);
            try {
                imgs[i] = ImageIO.read(new File("resources/imgs/preview" + (i+1) + ".png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            imgs[3] = ImageIO.read(new File("resources/imgs/unknown.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void tick() {
    }

    protected void draw(Graphics g) {
        int width = GamePanel.WIDTH;
        int height = GamePanel.HEIGHT;

        g.setColor(new Color(9, 5, 95));
        g.fillRect(0, 0, width, height);

        if(!diffSelect) {
            g.setColor(Color.WHITE);
            Font f = new Font("Exo", Font.BOLD, 72);
            Rectangle r = new Rectangle(0, 144, 720, 720);
            centerTextX(g, "Pick a save file", r, f);
            f = new Font("Exo", Font.BOLD, 24);
            for (int i = 0; i < 3; i++) {
                int x = 25 + i * 230;
                int y = 250;
                int wh = 210;

                SaveManager save = saves[i];
                int stage = save.getStage();
                int deaths = save.getDeaths();
                int diff = save.getDiff();

                if (i == current) g.setColor(SELECTED_COLOR);
                else g.setColor(Color.WHITE);
                if (stage != -1 && stage <= 2) g.drawImage(imgs[stage], x, y, wh, wh, null);
                else g.drawImage(imgs[3], x, y, wh, wh, null);
                g.drawRect(x, y, wh, wh);

                int offset = 35;
                int textY = y + wh + offset;
                String[] texts;
                if (stage != -1) {
                    texts = new String[]{
                            "Save " + (i+1),
                            "Stage: " + (stage < 3 ? (stage+1) : "???"),
                            "Deaths: " + deaths,
                            diffOptions[diff]
                    };
                } else {
                    texts = new String[]{"Save " + (i+1)};
                }
                for (int j = 0; j < texts.length; j++) {
                    String text = texts[j];
                    r = new Rectangle(x, textY + offset * j, wh, wh);
                    centerTextX(g, text, r, f);
                }
            }
        }
        else {
            Font f = new Font("Exo", Font.BOLD, 72);
            Rectangle r = new Rectangle(0, 150, width, height);
            g.setColor(Color.WHITE);
            centerTextX(g, "Choose a difficulty", r, f);

            for (int i = 0; i < diffOptions.length; i++) {
                if (i == current) g.setColor(new Color(150, 150, 255));
                else g.setColor(new Color(255, 255, 255));
                g.drawString(diffOptions[i], 85, height / diffOptions.length + 50 + i * 150);
            }
        }
    }

    public static void centerTextX(Graphics g, String text, Rectangle rect, Font font) {
        FontMetrics metrics = g.getFontMetrics(font);
        int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
        int y = rect.y;
        g.setFont(font);
        g.drawString(text, x, y);
    }

    protected void keyPressed(int k) {
        if(KEY_DOWN.contains(k) || KEY_RIGHT.contains(k)) {
            current++;
            if(current >= diffOptions.length) current = 0;
        }
        else if(KEY_UP.contains(k) || KEY_LEFT.contains(k)) {
            current--;
            if(current < 0) current = diffOptions.length-1;
        }

        if(KEY_EXIT.contains(k)) {
            if(!diffSelect) System.exit(0);
            else {
                current = 0;
                diffSelect = false;
            }
        }

        if(KEY_ENTER.contains(k)) {
            if(!diffSelect) {
                SaveManager.setIndex(current);
                if(saves[current].getStage() != -1) GameStateManager.states.push(new MenuState(gsm));
                else {
                    chosenSave = current;
                    current = 0;
                    diffSelect = true;
                }
            }
            else {
                SaveManager save = saves[chosenSave];
                save.setDiff(current);
                save.setDeaths(0);
                save.setC_deaths(0);
                save.setStage(0);
                save.setTime(0);
                save.setC_stage(0);
                save.save();
                GameStateManager.states.push(new MenuState(gsm));
            }
        }
    }

    protected void keyReleased(int k) {

    }
}
