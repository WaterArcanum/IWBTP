package main;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class AudioManager {
    private Clip clip;

    public AudioManager(String filename) {
        init(filename, -20);
    }

    public AudioManager(String filename, int gain) {
        init(filename, gain);
    }

    public void init(String filename, int gain) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(new File(filename));
            clip = AudioSystem.getClip();
            clip.open(ais);
            FloatControl fc = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            fc.setValue(gain);
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
