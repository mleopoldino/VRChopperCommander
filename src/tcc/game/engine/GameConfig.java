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
    public static final float MOVEMENT_SPEED = 20f * SPEED_MULTIPLIER; // ~4.2 pixels per frame

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

	// ==== LAYOUT AND POSITIONING CONSTANTS ====

	// Initial positions for game objects
	public static final int INITIAL_CENARIO_X = -378;
	public static final int INITIAL_CENARIO_Y = -100;
	public static final int INITIAL_INIMIGO_X = 378;
	public static final int INITIAL_INIMIGO_Y = 245;
	public static final int INITIAL_MIRA_X = 378;
	public static final int INITIAL_MIRA_Y = 245;
	public static final int INITIAL_GAMEOVER_X = 378;
	public static final int INITIAL_GAMEOVER_Y = 245;
	// Note: Cockpit is always fixed at (0, 0) - it's a UI overlay, not a moving object

	// Tiro (shot) alignment offset relative to Mira (crosshair)
	public static final int TIRO_OFFSET_X = -10;
	public static final int TIRO_OFFSET_Y = 95;

	// Cenario (background) boundary limits
	public static final float CENARIO_TOP_BOUNDARY = -1f;
	public static final float CENARIO_BOTTOM_BOUNDARY = -460f;
	public static final float CENARIO_LEFT_BOUNDARY = -755f;
	public static final float CENARIO_RIGHT_BOUNDARY = -20f;

	// UI display positions
	public static final int SCORE_DISPLAY_X = 160;
	public static final int SCORE_DISPLAY_Y = 475;
	public static final int ERROR_DISPLAY_X = 580;
	public static final int ERROR_DISPLAY_Y = 475;

	// ==== GAME LOGIC CONSTANTS ====

	// Enemy spawn area boundaries
	public static final int ENEMY_SPAWN_X_MIN = 200;
	public static final int ENEMY_SPAWN_X_RANGE = 800;  // 200 + 800 = max X of 1000
	public static final int ENEMY_SPAWN_Y_MIN = 100;
	public static final int ENEMY_SPAWN_Y_RANGE = 200;  // 100 + 200 = max Y of 300

	// Scaling thresholds
	public static final float ENEMY_MAX_SCALE = 1.0f;     // Enemy respawns when reaching this scale
	public static final float ENEMY_MIN_SCALE = 0.01f;    // Enemy starts at this scale
	public static final float EXPLOSAO_INITIAL_SCALE = 1f;

	// Animation frame counts
	public static final int EXPLOSION_FINAL_FRAME = 5;    // Explosion animation has 6 frames (0-5)

	// Scoring constants
	public static final int POINTS_PER_EXPLOSION = 4;     // countDestroy increments by 4 per explosion
	public static final int SCORE_MULTIPLIER = 10;        // Final score = (countDestroy/4) * 10

	// Game over threshold
	public static final int MAX_ERRORS_ALLOWED = 3;       // Game over when countError >= 3

	// Mira (crosshair) scale
	public static final float MIRA_SCALE = 0.1f;

	// Cenario (background) scale
	public static final int CENARIO_SCALE = 2;

	// Float comparison epsilon for boundary checks
	public static final float EPSILON = 0.01f;

	// Window dimensions
	public static final int WINDOW_WIDTH = 756;
	public static final int WINDOW_HEIGHT = 530;

	// Cockpit dimensions (from painel_sprite.png)
	public static final int COCKPIT_WIDTH = 756;
	public static final int COCKPIT_HEIGHT = 491;

	// Private constructor to prevent instantiation
	private GameConfig() {
		throw new AssertionError("GameConfig is a utility class and should not be instantiated");
	}
}
