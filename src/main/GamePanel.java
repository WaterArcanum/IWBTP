package main;

import states.GameStateManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GamePanel extends JPanel implements Runnable, KeyListener {
    public static final int WIDTH = 720;
    public static final int HEIGHT = 720;

    private boolean isRunning = false;

    private GameStateManager gsm;

    public GamePanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));

        addKeyListener(this);
        setFocusable(true);

        start();
    }

    public void start() {
        isRunning = true;
        Thread thread = new Thread(this);
        thread.start();
    }

    public synchronized void run() {
        long start, elapsed, wait;

        gsm = new GameStateManager();

        // Ensure the game always runs at constant speed
        while(isRunning) {
            start = System.nanoTime();

            tick();
            repaint();

            elapsed = System.nanoTime() - start;
            int FPS = 60;
            long targetTime = 1000 / FPS;
            wait = targetTime - elapsed / 1000000;

            sleep((int)wait);

        }
    }

    public void sleep(int w) {
        try {
            if(w < 0) w = 0;
            Thread.sleep(w);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void tick() {
        gsm.tick();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
//        Graphics2D g2d = (Graphics2D) g;
//        g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
//        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
        gsm.draw(g);
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
        gsm.keyPressed(e.getKeyCode());
    }

    public void keyReleased(KeyEvent e) {
        gsm.keyReleased(e.getKeyCode());
    }
}
