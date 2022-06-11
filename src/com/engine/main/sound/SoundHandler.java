package com.engine.main.sound;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

/**
 * Code from: https://www.delftstack.com/howto/java/play-sound-in-java/
 * Shots from: https://freesound.org/people/LittleRobotSoundFactory/packs/16681/
 * Music from: https://www.fesliyanstudios.com/royalty-free-music/downloads-c/8-bit-music/6
 */

public class SoundHandler {

    //define storage for start position
    Long nowFrame;
    Clip clip;

    String path;

    // get the clip status
    String thestatus;

    AudioInputStream audioStream;

    // initialize both the clip and streams
    public SoundHandler(String path) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        // the input stream object
        audioStream = AudioSystem.getAudioInputStream(new File(path).getAbsoluteFile());

        this.path = path;

        // the reference to the clip
        clip = AudioSystem.getClip();

        clip.open(audioStream);

        //clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void play()
    {

        //start the clip
        clip.start();

        thestatus = "play";
    }

    // reset the audio stream
    private void resetAudioStream(boolean back) throws UnsupportedAudioFileException, IOException,
            LineUnavailableException
    {
        audioStream = AudioSystem.getAudioInputStream(
                new File(path).getAbsoluteFile());
        clip.open(audioStream);

        if(back){
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }

    }

    // restart audio
    public void restart(boolean back) throws IOException, LineUnavailableException,
            UnsupportedAudioFileException
    {
        clip.stop();
        clip.close();
        resetAudioStream(back);
        nowFrame = 0L;
        clip.setMicrosecondPosition(0);
        this.play();
    }

}
