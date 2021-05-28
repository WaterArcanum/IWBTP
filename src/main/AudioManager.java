package main;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class AudioManager {
    private Clip clip;

    public AudioManager(String filename) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(new File(filename));
            clip = AudioSystem.getClip();
            clip.open(ais);
            FloatControl fc = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            fc.setValue(-20);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        clip.start();
    }

    public void start(boolean loop, boolean startAnew) {
        if(startAnew) clip.setMicrosecondPosition(0);
        clip.start();
        if(loop) clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop(boolean pause) {
        clip.stop();
        if(!pause) clip.setMicrosecondPosition(0);
    }
}
