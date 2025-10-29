package tcc.game.engine.core;

import java.awt.Graphics;
import javax.swing.Timer;
import javax.swing.JPanel;

import tcc.game.engine.Cenario;
import tcc.game.engine.Cockpit;
import tcc.game.engine.Explosao;
import tcc.game.engine.GameConfig;
import tcc.game.engine.GameObject;
import tcc.game.engine.GameOver;
import tcc.game.engine.Inimigo;
import tcc.game.engine.Mira;
import tcc.game.engine.Point;
import tcc.game.engine.Tiro;
import tcc.game.engine.Sound;
import tcc.game.engine.SoundManager;
import tcc.game.engine.GameLog;

public class GameCore extends JPanel {

	private static final long serialVersionUID = 1L;

	// All game object fields marked as transient since they are not serializable
	// and should not be persisted across sessions
	private transient Cenario cenario;
	private transient Cockpit cockpit;
	private transient GameObject tela;
	private transient Inimigo inimigo;
	private transient Tiro tiro;
	private transient Mira mira;
	private transient Explosao explosao;
	private transient GameOver gameOver;
	private transient Sound backgroundMusic;
	private transient Sound helicopterSound;
	private transient Sound explosionSound;
	private transient Sound machineGunSound;

	private int countDestroy = 0;
	private int pontos = 0;
	private int countError = 0;
	private int count = 0;

	// Game loop timer - 60 FPS (16ms per frame)
	private transient Timer gameTimer;
	private boolean explosionJustStarted = false; // FIX: Track explosion state to count only once

	// FIX: SoundManager executes sound operations on background thread (off-EDT)
	private transient SoundManager soundManager;
	
	//Construtor
	public GameCore() {
		// FIX: Initialize ALL fields BEFORE calling any parent class methods
		// to avoid 'this' escape during construction

		// Initialize sound manager first
		soundManager = new SoundManager();

		cenario = new Cenario();
		cenario.setPosition(new Point(GameConfig.INITIAL_CENARIO_X, GameConfig.INITIAL_CENARIO_Y));

		// FIX: Cockpit is a fixed UI overlay at screen position (0, 0)
		cockpit = new Cockpit();

		inimigo = new Inimigo();
		inimigo.setPosition(new Point(GameConfig.INITIAL_INIMIGO_X, GameConfig.INITIAL_INIMIGO_Y));

		mira = new Mira();
		explosao = new Explosao();

		tela = new GameObject();
		tela.getSpriteSheet().addImage("assets/images/capa_sprite.png");

		tiro = new Tiro();

		gameOver = new GameOver();

		// Initialize sounds
		backgroundMusic = new Sound();
		backgroundMusic.load("assets/sounds/BackgroundTheme.wav");

		helicopterSound = new Sound();
		helicopterSound.load("assets/sounds/HelicopterSoundEffect.wav");

		explosionSound = new Sound();
		explosionSound.load("assets/sounds/ExplosionSoundEffect.wav");

		machineGunSound = new Sound();
		machineGunSound.load("assets/sounds/MachineGunSoundEffect.wav");

		// NOW safe to call parent class methods after all fields initialized
		setDoubleBuffered(true);

		// FIX: Initialize game loop timer using GameConfig
		gameTimer = new Timer(GameConfig.FRAME_DELAY_MS, e -> {
			updateGameLogic();
			repaint();
		});
		gameTimer.start();

		// FIX: Start background music after a short delay to ensure component is ready
		// Use a separate timer to start music after initialization
		Timer musicStartTimer = new Timer(100, e -> {
			if (getCount() == 0 && !backgroundMusic.isPlaying()) {
				// FIX: Use SoundManager to execute sound operations off-EDT
				soundManager.loopAsync(backgroundMusic);
				GameLog.debug("Background music started on title screen");
			}
			((Timer)e.getSource()).stop(); // Stop this timer after first execution
		});
		musicStartTimer.setRepeats(false);
		musicStartTimer.start();
	}
	
	//Getters e Setters
	public int getCountDestroy() {
		return countDestroy;
	}

	public void setCountDestroy(int countDestroy) {
		this.countDestroy = countDestroy;
	}
	
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		// FIX: Validate input - count must be 0 (title screen) or 1 (gameplay)
		if (count != 0 && count != 1) {
			throw new IllegalArgumentException("count must be 0 (title screen) or 1 (gameplay), got: " + count);
		}
		this.count = count;

		if (count == 1) {
			resetGameSession();
		}
	}

	private void resetGameSession() {
		countDestroy = 0;
		pontos = 0;
		countError = 0;
		explosionJustStarted = false;

		cenario.setDirecao(Cenario.CENTRO);
		tiro.setVisible(false);

		if (machineGunSound != null && machineGunSound.isPlaying()) {
			soundManager.stopAsync(machineGunSound);
		}

		explosao.setVisible(false);
		explosao.setScale(1f);
		explosao.respawn();

		inimigo.setDirecao(Cenario.CENTRO);
		inimigo.resetForNewGame(generateEnemySpawnPoint());
	}

	private Point generateEnemySpawnPoint() {
		// FIX: Use clear random generation instead of convoluted modulo
		int x = GameConfig.ENEMY_SPAWN_X_MIN + (int)(Math.random() * GameConfig.ENEMY_SPAWN_X_RANGE);
		int y = GameConfig.ENEMY_SPAWN_Y_MIN + (int)(Math.random() * GameConfig.ENEMY_SPAWN_Y_RANGE);
		return new Point(x, y);
	}
	
	//Metodos

	// FIX: Separate game logic from rendering
	private void updateGameLogic() {
		this.update();

		// FIX: Handle explosion counting correctly (only once per explosion)
		if (explosao.isVisible()){
			if (!explosionJustStarted) {
				// Explosion just started - count it once
				this.countDestroy += GameConfig.POINTS_PER_EXPLOSION;
				somaPontos();
				explosionJustStarted = true;
			}
		} else {
			// Reset flag when explosion finishes
			explosionJustStarted = false;
		}

		// Handle sound transitions between title screen and gameplay
		// FIX: All sound operations now execute off-EDT via SoundManager
		if(getCount()==0){
			// Title screen: play background music, stop helicopter sound
			if (!backgroundMusic.isPlaying()) {
				soundManager.loopAsync(backgroundMusic);
			}
			if (helicopterSound.isPlaying()) {
				soundManager.stopAsync(helicopterSound);
			}
		} else {
			// Gameplay: stop background music, play helicopter sound
			if (backgroundMusic.isPlaying()) {
				soundManager.stopAsync(backgroundMusic);
			}
			if (!helicopterSound.isPlaying()) {
				soundManager.loopAsync(helicopterSound);
			}

			// Check game over condition
			if(validaGameOver()){
				gameOver.update();
				inimigo.setVisible(false);
				// Stop all gameplay sounds on game over
				if (helicopterSound.isPlaying()) {
					soundManager.stopAsync(helicopterSound);
				}
				if (machineGunSound.isPlaying()) {
					soundManager.stopAsync(machineGunSound);
				}
			}
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		// FIX: paintComponent() now only handles rendering, no game logic or Thread.sleep

		if(getCount()==0){
			// Title screen - only draw title image
			tela.draw(g);
		} else {
			// Gameplay - draw all game objects
			cenario.draw(g);
			inimigo.draw(g);
			mira.draw(g);

			if (tiro.isVisible()){
				tiro.draw(g);
			}

			if (explosao.isVisible()){
				explosao.draw(g);
			}

			cockpit.draw(g);

			//Placar
			g.drawString(String.valueOf(this.pontos * GameConfig.SCORE_MULTIPLIER),
				GameConfig.SCORE_DISPLAY_X, GameConfig.SCORE_DISPLAY_Y);

			if(this.countError <= GameConfig.MAX_ERRORS_ALLOWED){
				g.drawString(String.valueOf(this.countError),
					GameConfig.ERROR_DISPLAY_X, GameConfig.ERROR_DISPLAY_Y);
			} else {
				g.drawString(String.valueOf(GameConfig.MAX_ERRORS_ALLOWED),
					GameConfig.ERROR_DISPLAY_X, GameConfig.ERROR_DISPLAY_Y);
			}

			if(validaGameOver()){
				gameOver.draw(g);
			}
		}
	}

	public boolean verificaColisao() {
		int inimigoWidth, inimigoHeight, inimigoX, inimigoY;
		inimigoX = inimigo.getPosition().getX();
		inimigoY = inimigo.getPosition().getY();
		inimigoWidth = (int) (inimigo.getSpriteSheet().getCurrentImage().getWidth(null) * inimigo.getScale());
		inimigoHeight = (int) (inimigo.getSpriteSheet().getCurrentImage().getHeight(null) * inimigo.getScale());

		int miraX, miraY;
		miraX = mira.getPosition().getX()
				+ (int) (mira.getSpriteSheet().getCurrentImage().getWidth(null) * mira.getScale()) / 2;
		miraY = mira.getPosition().getY()
				+ (int) (mira.getSpriteSheet().getCurrentImage().getHeight(null) * mira.getScale()) / 2;

		if (miraX >= inimigoX && miraX <= inimigoX + inimigoWidth && miraY >= inimigoY
				&& miraY <= inimigoY + inimigoHeight) {
			return true;
			
		} 
			
		return false;
	}

	public void update() {
		// FIX: Only update game objects during gameplay (not on title screen)
		if (getCount() > 0) {
			// Gameplay updates
			inimigo.update();
			cenario.update();
			cockpit.update();
			tiro.update();
			mira.update();

			// FIX: Collision detection now works independently
			// Only trigger explosion when shooting AND collision detected AND enemy is visible
			if (tiro.isVisible() && verificaColisao() && inimigo.isVisible()) {
				explosao.setPosition(inimigo.getPosition());
				explosao.setScale(inimigo.getScale());
				inimigo.setVisible(false);
				explosao.explode();
				// FIX: Execute sound playback off-EDT
				soundManager.playAsync(explosionSound);
			}

			explosao.update();
		} else {
			// Title screen updates (only title animation)
			tela.update();
		}
	}

	// Stop game timer and sounds
	public void stopGame() {
		if (gameTimer != null && gameTimer.isRunning()) {
			gameTimer.stop();
		}
		// Release audio resources (now using SoundManager)
		shutdownSound(backgroundMusic);
		shutdownSound(helicopterSound);
		shutdownSound(explosionSound);
		shutdownSound(machineGunSound);
		// Shutdown sound manager thread pool
		if (soundManager != null) {
			soundManager.shutdown();
		}
	}

	public void changeCenario(int direcao) {
		cenario.setDirecao(direcao);
		inimigo.setDirecao(direcao);
	}

	public void atira(boolean status) {
		tiro.setVisible(status);
		if (status) {
			// Start looping machine gun sound when firing starts
			if (!machineGunSound.isPlaying()) {
				soundManager.loopAsync(machineGunSound);
			}
		} else {
			// Stop machine gun sound when firing stops
			soundManager.stopAsync(machineGunSound);
		}
	}
	
	public void somaPontos(){
		this.pontos = this.countDestroy / GameConfig.POINTS_PER_EXPLOSION;
	}
	
	public boolean validaGameOver(){
		// FIX: Simplified logic using GameConfig constant
		this.countError = inimigo.getErro() - this.pontos;
		return this.countError >= GameConfig.MAX_ERRORS_ALLOWED;
	}

	private void shutdownSound(Sound sound) {
		if (sound == null) {
			return;
		}
		// FIX: Use SoundManager for thread-safe shutdown
		soundManager.stopAsync(sound);
		soundManager.releaseAsync(sound);
	}
	
}
