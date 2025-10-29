package tcc.game.engine;

import java.awt.Graphics;

public class Inimigo extends GameObject {
	public static final int SOBE = 1;
	public static final int DESCE = 2;
	public static final int DIREITA = 3;
	public static final int ESQUERDA = 4;
	public static final int CENTRO = 0;

	// FIX: Use GameConfig for movement speeds (adjusted for 60 FPS)
	private static final Vector2D cima = new Vector2D(0, GameConfig.MOVEMENT_SPEED);
	private static final Vector2D baixo = new Vector2D(0, -GameConfig.MOVEMENT_SPEED);
	private static final Vector2D direita = new Vector2D(-GameConfig.MOVEMENT_SPEED, 0);
	private static final Vector2D esquerda = new Vector2D(GameConfig.MOVEMENT_SPEED, 0);

	private int direcao;
	private boolean visible;
	private int erro = 0;
	
	
	//Construtor
	public Inimigo() {
		// FIX: Call super() first, then initialize fields before using parent methods
		super();
		this.direcao = CENTRO;
		this.visible = false;
		this.erro = 0;
		// NOW safe to call methods inherited from GameObject
		getSpriteSheet().addImage("assets/images/helicoptero_sprite01.png");
		getSpriteSheet().addImage("assets/images/helicoptero_sprite02.png");
		setScale(GameConfig.ENEMY_MIN_SCALE);
	}

	//Getters e Setters
	public int getDirecao() {
		return direcao;
	}
	
	public void setDirecao(int direcao) {
		this.direcao = direcao;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	public int getErro() {
		return erro;
	}

	public void setErro(int erro) {
		this.erro = erro;
	}
	
	//Metodos
	public void draw(Graphics g) {
		// FIX: Defensive null check
		if (getSpriteSheet() == null || getSpriteSheet().getCurrentImage() == null) {
			GameLog.warn("Cannot draw Inimigo: sprite or image is null");
			return;
		}
		int width = (int) (getSpriteSheet().getCurrentImage().getWidth(null) * getScale());
		int height = (int) (getSpriteSheet().getCurrentImage().getHeight(null) * getScale());
		g.drawImage(getSpriteSheet().getCurrentImage(), getPosition().getX() - width / 2,
				getPosition().getY() - height / 2, width, height, null);
	}

	public void update() {
		switch (direcao) {
		case SOBE:
			getPosition().somaVector2D(cima);
			break;
		case DESCE:
			getPosition().somaVector2D(baixo);
			break;
		case DIREITA:
			getPosition().somaVector2D(direita);
			break;
		case ESQUERDA:
			getPosition().somaVector2D(esquerda);
			break;
		}

		// FIX: Use GameConfig for scaling speed (adjusted for 60 FPS)
		setScale(getScale() + GameConfig.ENEMY_SCALE_INCREMENT);

		respawn();

		super.update();

	}
	
	public void destroy() {
		visible = false;
	}
			
	public void respawn() {
		// FIX: Use clear random generation with GameConfig constants
		int posX = GameConfig.ENEMY_SPAWN_X_MIN + (int)(Math.random() * GameConfig.ENEMY_SPAWN_X_RANGE);
		int posY = GameConfig.ENEMY_SPAWN_Y_MIN + (int)(Math.random() * GameConfig.ENEMY_SPAWN_Y_RANGE);

		if (!visible || (visible && getScale() >= GameConfig.ENEMY_MAX_SCALE)) {
			setScale(GameConfig.ENEMY_MIN_SCALE);
			GameLog.debug("New helicopter respawned at " + posX + "," + posY);
			setPosition(new Point(posX, posY));
			setVisible(true);
			this.erro++;
		}
	}
	
	public void resetForNewGame(Point spawnPoint) {
		setErro(0);
		setScale(GameConfig.ENEMY_MIN_SCALE);
		setPosition(spawnPoint);
		setVisible(true);
	}
	
	
}
