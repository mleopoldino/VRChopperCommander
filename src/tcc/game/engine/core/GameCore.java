package tcc.game.engine.core;

import java.awt.Component;
import java.awt.Graphics;
import javax.swing.Timer;

import tcc.game.engine.Cenario;
import tcc.game.engine.Explosao;
import tcc.game.engine.GameConfig;
import tcc.game.engine.GameObject;
import tcc.game.engine.GameOver;
import tcc.game.engine.Inimigo;
import tcc.game.engine.Mira;
import tcc.game.engine.Point;
import tcc.game.engine.Tiro;
import tcc.game.engine.Sound;

public class GameCore extends Component {

	private Cenario cenario;
	private GameObject cockpit;
	private GameObject tela;
	private Inimigo inimigo;
	private Tiro tiro;
	private Mira mira;
	private Explosao explosao;
	private GameOver gameOver;
	private Sound backgroundMusic;
	private Sound helicopterSound;
	private Sound explosionSound;
	private Sound machineGunSound;

	private int countDestroy = 0;
	private int pontos = 0;
	private int countError = 0;
	private int count = 0;

	// Game loop timer - 60 FPS (16ms per frame)
	private Timer gameTimer;
	private boolean explosionJustStarted = false; // FIX: Track explosion state to count only once
	
	//Construtor
	public GameCore() {
		
		cenario = new Cenario();
		cenario.setPosition(new Point(-378, -100));

		inimigo = new Inimigo();
		inimigo.setPosition(new Point(378, 245));

		mira = new Mira();
		explosao = new Explosao();
		
		cockpit = new GameObject();
		cockpit.getSpriteSheet().addImage("assets/images/painel_sprite.png");
		
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
				backgroundMusic.loop();
				System.out.println("Background music started on title screen");
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
		this.count = count;

		// FIX: When starting game (pressing ENTER), reset enemy to initial state
		if (count == 1) {
			inimigo.setScale(0.01f);
			inimigo.setPosition(new Point(
				200 + Math.abs((int) ((Math.random() * 1000) % 800)),
				Math.abs((int) ((Math.random() * 1000) % 200)) + 100
			));
			inimigo.setVisible(true);
			System.out.println("Game started - Enemy initialized at: " + inimigo.getPosition().getX() + "," + inimigo.getPosition().getY());
		}
	}
	
	//Metodos

	// FIX: Separate game logic from rendering
	private void updateGameLogic() {
		this.update();

		// FIX: Handle explosion counting correctly (only once per explosion)
		if (explosao.isVisible()){
			if (!explosionJustStarted) {
				// Explosion just started - count it once
				this.countDestroy += 4; // Add 4 to match original scoring logic
				somaPontos();
				explosionJustStarted = true;
			}
		} else {
			// Reset flag when explosion finishes
			explosionJustStarted = false;
		}

		// Handle sound transitions between title screen and gameplay
		if(getCount()==0){
			// Title screen: play background music, stop helicopter sound
			if (!backgroundMusic.isPlaying()) {
				backgroundMusic.loop();
			}
			if (helicopterSound.isPlaying()) {
				helicopterSound.stop();
			}
		} else {
			// Gameplay: stop background music, play helicopter sound
			if (backgroundMusic.isPlaying()) {
				backgroundMusic.stop();
			}
			if (!helicopterSound.isPlaying()) {
				helicopterSound.loop();
			}

			// Check game over condition
			if(validaGameOver()){
				gameOver.update();
				inimigo.setVisible(false);
				// Stop all gameplay sounds on game over
				if (helicopterSound.isPlaying()) {
					helicopterSound.stop();
				}
				if (machineGunSound.isPlaying()) {
					machineGunSound.stop();
				}
			}
		}
	}

	@Override
	public void paint(Graphics g) {
		// FIX: paint() now only handles rendering, no game logic or Thread.sleep

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
			g.drawString(String.valueOf(this.pontos*10), 160, 475);

			if(this.countError<=3){
				g.drawString(String.valueOf(this.countError), 580, 475);
			} else {
				g.drawString("3", 580, 475); // FIX: Keep position consistent
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
				explosionSound.play();
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
		// Stop all sounds
		if (backgroundMusic != null && backgroundMusic.isPlaying()) {
			backgroundMusic.stop();
		}
		if (helicopterSound != null && helicopterSound.isPlaying()) {
			helicopterSound.stop();
		}
		if (machineGunSound != null && machineGunSound.isPlaying()) {
			machineGunSound.stop();
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
				machineGunSound.loop();
			}
		} else {
			// Stop machine gun sound when firing stops
			machineGunSound.stop();
		}
	}
	
	public void somaPontos(){
		this.pontos = this.countDestroy/4;
	}
	
	public boolean validaGameOver(){
		boolean status = true;
		this.countError = inimigo.getErro()-this.pontos;
		if(this.countError < 3){
			status = false;
			return status;
		} else {
			status = true;
			return status;
		}
	}
	
}
