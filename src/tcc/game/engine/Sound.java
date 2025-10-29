package tcc.game.engine;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;

import tcc.game.engine.GameLog;

public class Sound {

    // FIX: Use lock object for thread-safe access to clip
    private final Object lock = new Object();
    private Clip clip;
    private String filePath;

    public Sound() {
        // Constructor
    }

    public void load(String filePath) {
        synchronized (lock) {
            try {
                if (filePath == null || filePath.isEmpty()) {
                    GameLog.warn("Invalid sound path provided.");
                    return;
                }

                if (clip != null) {
                    if (this.filePath != null && this.filePath.equals(filePath) && clip.isOpen()) {
                        // Already loaded
                        return;
                    }
                    closeClip();
                }

            this.filePath = filePath;
            File soundFile = new File(filePath);
            GameLog.debug("Attempting to load sound file: " + soundFile.getAbsolutePath());
            if (!soundFile.exists()) {
                GameLog.warn("Sound file does not exist at " + soundFile.getAbsolutePath());
                return;
            }

            // Check if file is MP3 or WAV
            if (filePath.toLowerCase().endsWith(".mp3")) {
                GameLog.warn("MP3 file detected. Java Sound API does not support MP3 natively; prefer WAV for playback.");
                // We'll try to load it anyway, but it likely won't work
            }

            try {
                try (AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile)) {
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

                    try (AudioInputStream decodedInputStream = AudioSystem.getAudioInputStream(decodedFormat, audioInputStream)) {
                        clip = AudioSystem.getClip();
                        clip.open(decodedInputStream);
                        GameLog.debug("Successfully loaded sound file: " + filePath);
                    }
                }
            } catch (UnsupportedAudioFileException e) {
                GameLog.warn("Unsupported audio format for " + filePath, e);
                throw e;
            }
            } catch (Exception e) {
                GameLog.error("Error loading sound file " + filePath, e);
            }
        }
    }

    // FIX: Synchronize all public methods that access clip field
    public void play() {
        synchronized (lock) {
            if (clip != null && clip.isOpen()) {
                clip.stop(); // Stop if already playing
                clip.setFramePosition(0); // Rewind to the beginning
                clip.start(); // Start playing
                GameLog.debug("Playing sound: " + filePath);
            } else if (clip != null && !clip.isOpen()) {
                GameLog.warn("Clip is not open for playback: " + filePath);
            } else {
                GameLog.warn("Clip is null for playback: " + filePath);
            }
        }
    }

    public void loop() {
        synchronized (lock) {
            if (clip != null && clip.isOpen()) {
                if (!clip.isRunning()) {
                    clip.setFramePosition(0); // Rewind to the beginning
                    clip.loop(Clip.LOOP_CONTINUOUSLY); // Loop continuously
                    GameLog.debug("Looping sound: " + filePath);
                }
            } else if (clip != null && !clip.isOpen()) {
                GameLog.warn("Clip is not open for looping: " + filePath);
            } else {
                GameLog.warn("Clip is null for looping: " + filePath);
            }
        }
    }

    public void stop() {
        synchronized (lock) {
            if (clip != null && clip.isRunning()) {
                clip.stop();
                GameLog.debug("Stopped sound: " + filePath);
            }
        }
    }

    public boolean isPlaying() {
        synchronized (lock) {
            return clip != null && clip.isRunning();
        }
    }

    public void release() {
        synchronized (lock) {
            closeClip();
            GameLog.debug("Released sound resources for: " + filePath);
        }
    }

    private void closeClip() {
        if (clip != null) {
            try {
                clip.stop();
            } catch (Exception ignored) {
            }
            clip.close();
            clip = null;
        }
    }
}
