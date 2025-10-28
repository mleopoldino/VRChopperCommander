package tcc.game.engine;

/**
 * Game configuration constants
 * Centralizes all game speed and timing parameters
 */
public class GameConfig {

	// Frame rate settings
	public static final int TARGET_FPS = 60;
	public static final int FRAME_DELAY_MS = 1000 / TARGET_FPS; // ~16ms for 60 FPS

	// Original game ran at ~12.5 FPS with sleep(80)
	// Speed multiplier to maintain original game speed at 60 FPS
	// 12.5 FPS / 60 FPS = 0.208 (approximately 1/5 speed)
	public static final float SPEED_MULTIPLIER = 0.21f;

	// Movement speeds (adjusted for 60 FPS)
	// Original: 20 pixels per frame at 12.5 FPS = 250 px/sec
	// Target: 250 px/sec at 60 FPS = ~4.2 px/frame
	public static final int MOVEMENT_SPEED = (int)(20 * SPEED_MULTIPLIER); // 4 pixels per frame

	// Enemy scaling speed (adjusted for 60 FPS)
	// Original: 0.01f per frame at 12.5 FPS = 0.125/sec
	// Target: 0.125/sec at 60 FPS = 0.0021/frame
	public static final float ENEMY_SCALE_INCREMENT = 0.01f * SPEED_MULTIPLIER; // ~0.002f per frame

	// Explosion scaling speed (adjusted for 60 FPS)
	public static final float EXPLOSION_SCALE_INCREMENT = 0.01f * SPEED_MULTIPLIER; // ~0.002f per frame

	// Sprite animation speed
	// At 60 FPS, we want sprites to update every few frames to match original speed
	// Original: sprite changed every frame at 12.5 FPS
	// Target: sprite changes every ~5 frames at 60 FPS (12 changes per second)
	public static final int SPRITE_UPDATE_FRAMES = 5; // Update sprite every 5 frames

	// Private constructor to prevent instantiation
	private GameConfig() {
		throw new AssertionError("GameConfig is a utility class and should not be instantiated");
	}
}
