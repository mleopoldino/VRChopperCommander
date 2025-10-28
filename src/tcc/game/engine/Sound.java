package tcc.game.engine;

import javax.sound.sampled.*;
import java.io.*;

public class Sound {

    private Clip clip;
    private String filePath;
    private Thread mp3Thread;
    private volatile boolean shouldLoop = false;
    private volatile boolean isPlaying = false;

    public Sound() {
        // Constructor
    }

    public void load(String filePath) {
        try {
            this.filePath = filePath;
            File soundFile = new File(filePath);
            System.out.println("Attempting to load sound file: " + soundFile.getAbsolutePath());
            if (!soundFile.exists()) {
                System.err.println("Error: Sound file does not exist at " + soundFile.getAbsolutePath());
                return;
            }

            // Check if file is MP3 or WAV
            if (filePath.toLowerCase().endsWith(".mp3")) {
                System.out.println("MP3 file detected. Note: Java Sound API doesn't support MP3 natively.");
                System.out.println("Please convert to WAV format for proper playback.");
                // We'll try to load it anyway, but it likely won't work
            }

            try {
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
                AudioFormat baseFormat = audioInputStream.getFormat();

                // Convert to PCM if needed
                AudioFormat decodedFormat = new AudioFormat(
                    AudioFormat.Encoding.PCM_SIGNED,
                    baseFormat.getSampleRate(),
                    16,
                    baseFormat.getChannels(),
                    baseFormat.getChannels() * 2,
                    baseFormat.getSampleRate(),
                    false
                );

                AudioInputStream decodedInputStream = AudioSystem.getAudioInputStream(decodedFormat, audioInputStream);

                clip = AudioSystem.getClip();
                clip.open(decodedInputStream);
                System.out.println("Successfully loaded sound file: " + filePath);
            } catch (UnsupportedAudioFileException e) {
                System.err.println("Error: Unsupported audio format for " + filePath);
                System.err.println("Please convert MP3 files to WAV format.");
                throw e;
            }
        } catch (Exception e) {
            System.err.println("Error loading sound file " + filePath + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void play() {
        if (clip != null && clip.isOpen()) {
            clip.stop(); // Stop if already playing
            clip.setFramePosition(0); // Rewind to the beginning
            clip.start(); // Start playing
            isPlaying = true;
            System.out.println("Playing sound: " + filePath);
        } else if (clip != null && !clip.isOpen()) {
            System.err.println("Error: Clip is not open for playback: " + filePath);
        } else {
            System.err.println("Error: Clip is null for playback: " + filePath);
        }
    }

    public void loop() {
        if (clip != null && clip.isOpen()) {
            if (!clip.isRunning()) {
                clip.setFramePosition(0); // Rewind to the beginning
                clip.loop(Clip.LOOP_CONTINUOUSLY); // Loop continuously
                isPlaying = true;
                System.out.println("Looping sound: " + filePath);
            }
        } else if (clip != null && !clip.isOpen()) {
            System.err.println("Error: Clip is not open for looping: " + filePath);
        } else {
            System.err.println("Error: Clip is null for looping: " + filePath);
        }
    }

    public void stop() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
            isPlaying = false;
            System.out.println("Stopped sound: " + filePath);
        }
    }

    public boolean isPlaying() {
        return clip != null && clip.isRunning();
    }
}
