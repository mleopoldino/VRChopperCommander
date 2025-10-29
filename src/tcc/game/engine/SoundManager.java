package tcc.game.engine;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * FIX: SoundManager executes sound operations on a background thread
 * to avoid blocking the Swing EDT (Event Dispatch Thread).
 *
 * Java Sound API is not EDT-safe, so all operations should be done off-EDT.
 */
public class SoundManager {

    // Single thread executor for sequential sound operations
    private final ExecutorService soundExecutor;

    public SoundManager() {
        // Create a single-threaded executor for sound operations
        soundExecutor = Executors.newSingleThreadExecutor(r -> {
            Thread t = new Thread(r, "SoundManager-Thread");
            t.setDaemon(true); // Daemon thread won't prevent JVM shutdown
            return t;
        });
    }

    /**
     * Play a sound on background thread (one-shot playback)
     */
    public void playAsync(Sound sound) {
        if (sound == null) {
            return;
        }
        soundExecutor.submit(() -> {
            try {
                sound.play();
            } catch (Exception e) {
                GameLog.error("Error playing sound", e);
            }
        });
    }

    /**
     * Loop a sound continuously on background thread
     */
    public void loopAsync(Sound sound) {
        if (sound == null) {
            return;
        }
        soundExecutor.submit(() -> {
            try {
                sound.loop();
            } catch (Exception e) {
                GameLog.error("Error looping sound", e);
            }
        });
    }

    /**
     * Stop a sound on background thread
     */
    public void stopAsync(Sound sound) {
        if (sound == null) {
            return;
        }
        soundExecutor.submit(() -> {
            try {
                sound.stop();
            } catch (Exception e) {
                GameLog.error("Error stopping sound", e);
            }
        });
    }

    /**
     * Release sound resources on background thread
     */
    public void releaseAsync(Sound sound) {
        if (sound == null) {
            return;
        }
        soundExecutor.submit(() -> {
            try {
                sound.release();
            } catch (Exception e) {
                GameLog.error("Error releasing sound", e);
            }
        });
    }

    /**
     * Shutdown the sound manager and wait for pending operations
     */
    public void shutdown() {
        soundExecutor.shutdown();
        try {
            // Wait up to 2 seconds for pending operations to complete
            if (!soundExecutor.awaitTermination(2, java.util.concurrent.TimeUnit.SECONDS)) {
                soundExecutor.shutdownNow();
            }
        } catch (InterruptedException e) {
            soundExecutor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
