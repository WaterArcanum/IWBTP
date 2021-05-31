package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;

public class Game {
    public static ArrayList<Integer> KEY_DOWN =
            new ArrayList<>(Arrays.asList(KeyEvent.VK_DOWN, KeyEvent.VK_S, KeyEvent.VK_NUMPAD2, KeyEvent.VK_NUMPAD5));
    public static ArrayList<Integer> KEY_UP = new ArrayList<>(Arrays.asList(KeyEvent.VK_UP, KeyEvent.VK_W));
    public static ArrayList<Integer> KEY_LEFT = new ArrayList<>(Arrays.asList(KeyEvent.VK_LEFT, KeyEvent.VK_A));
    public static ArrayList<Integer> KEY_RIGHT = new ArrayList<>(Arrays.asList(KeyEvent.VK_RIGHT, KeyEvent.VK_D, KeyEvent.VK_L));
    public static ArrayList<Integer> KEY_ENTER = new ArrayList<>(Arrays.asList(KeyEvent.VK_ENTER, KeyEvent.VK_SPACE));
    public static ArrayList<Integer> KEY_JUMP =
            new ArrayList<>(Arrays.asList(KeyEvent.VK_SHIFT, KeyEvent.VK_SPACE, KeyEvent.VK_UP, KeyEvent.VK_W));
    public static ArrayList<Integer> KEY_RESET = new ArrayList<>(Arrays.asList(KeyEvent.VK_R, KeyEvent.VK_BACK_SLASH));
    public static ArrayList<Integer> KEY_EXIT = new ArrayList<>(Arrays.asList(KeyEvent.VK_ESCAPE, KeyEvent.VK_BACK_SPACE));

    public static void main(String[] args) {
        JFrame frame = new JFrame("IWBTProject");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLayout(new BorderLayout());
        frame.add(new GamePanel(), BorderLayout.CENTER);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}