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
		getSpriteSheet().addImage("assets/images/helicoptero_sprite01.png");
		getSpriteSheet().addImage("assets/images/helicoptero_sprite02.png");

		setScale(0.01f);
		// FIX: Start invisible on title screen, will be made visible when game starts
		setVisible(false);
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
		int posX, posY;

		posX = +200 + Math.abs((int) ((Math.random() * 1000) % 800 ));
		posY = Math.abs((int) ((Math.random() * 1000) % 200)) + 100;
		if (!visible || (visible && getScale() >= 1.0f)) {
			setScale(0.01f);
			System.out.println("New Helicoptero "+posX+","+posY);
			setPosition(new Point(posX, posY));
			setVisible(true);
			this.erro++;
			//System.out.println(this.erro);
		}

	}
	
	
}
