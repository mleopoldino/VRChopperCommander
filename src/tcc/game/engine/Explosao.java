package tcc.game.engine;

import java.awt.Graphics;

public class Explosao extends GameObject {
	
	public static final int SOBE     = 1;
	public static final int DESCE    = 2;
	public static final int DIREITA  = 3;
	public static final int ESQUERDA = 4;
	public static final int CENTRO   = 0;

	// FIX: Use GameConfig for movement speeds (adjusted for 60 FPS)
	private static final Vector2D cima     = new Vector2D(0, GameConfig.MOVEMENT_SPEED);
	private static final Vector2D baixo    = new Vector2D(0, -GameConfig.MOVEMENT_SPEED);
	private static final Vector2D direita  = new Vector2D(-GameConfig.MOVEMENT_SPEED, 0);
	private static final Vector2D esquerda = new Vector2D(GameConfig.MOVEMENT_SPEED, 0);
	
	private boolean visible;
	private int direcao;
	
	//Construtor
	public Explosao(){
		// FIX: Call super() first, then initialize fields before using parent methods
		super();
		this.visible = false;
		this.direcao = CENTRO;
		// NOW safe to call methods inherited from GameObject
		getSpriteSheet().addImage("assets/images/explosao_sprite01.png");
		getSpriteSheet().addImage("assets/images/explosao_sprite02.png");
		getSpriteSheet().addImage("assets/images/explosao_sprite03.png");
		getSpriteSheet().addImage("assets/images/explosao_sprite04.png");
		getSpriteSheet().addImage("assets/images/explosao_sprite05.png");
		getSpriteSheet().addImage("assets/images/explosao_sprite06.png");
	}
	
	//Getters e Setters
	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	//Metodos
	public void explode(){
		visible = true;
	}
	
	public void update(){
		if (visible){
			
			switch(direcao){
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
			setScale(getScale() + GameConfig.EXPLOSION_SCALE_INCREMENT);

			super.update();
			if (getSpriteSheet().getImageNumber() == GameConfig.EXPLOSION_FINAL_FRAME){
				visible = false;
				respawn();
			}
		}
	}
	
	public void draw(Graphics g){
		// FIX: Defensive null check
		if (getSpriteSheet() == null || getSpriteSheet().getCurrentImage() == null) {
			GameLog.warn("Cannot draw Explosao: sprite or image is null");
			return;
		}
		int width  = (int) (getSpriteSheet().getCurrentImage().getWidth(null)*getScale());
		int height = (int) (getSpriteSheet().getCurrentImage().getHeight(null)*getScale());
		g.drawImage(getSpriteSheet().getCurrentImage(), getPosition().getX()-width/2, getPosition().getY()-height/2 ,
				           width, height, null );
	}
	
	public void respawn(){
		getSpriteSheet().setImageNumber(0);
	}
	
}
