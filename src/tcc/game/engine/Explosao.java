package tcc.game.engine;

import java.awt.Graphics;

public class Explosao extends GameObject{
	
	public static final int SOBE     = 1;
	public static final int DESCE    = 2;
	public static final int DIREITA  = 3;
	public static final int ESQUERDA = 4;
	public static final int CENTRO   = 0;
			
	private static final Vector2D cima     = new Vector2D(0  ,20);
	private static final Vector2D baixo    = new Vector2D(0  ,-20);
	private static final Vector2D direita  = new Vector2D(-20,0);
	private static final Vector2D esquerda = new Vector2D(20 ,0);
	
	private boolean visible;
	private int direcao;
	
	//Construtor
	public Explosao(){
		visible = false;
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
			
			setScale(getScale()+0.01f);
			
			super.update();
			if (getSpriteSheet().getImageNumber() == 5){
				visible = false;
				respawn();
			}
		}
	}
	
	public void draw(Graphics g){
		int width  = (int) (getSpriteSheet().getCurrentImage().getWidth(null)*getScale());
		int height = (int) (getSpriteSheet().getCurrentImage().getHeight(null)*getScale());
		g.drawImage(getSpriteSheet().getCurrentImage(), getPosition().getX()-width/2, getPosition().getY()-height/2 ,
				           width, height, null );
	}
	
	public void respawn(){
		getSpriteSheet().setImageNumber(0);
	}
	
}
