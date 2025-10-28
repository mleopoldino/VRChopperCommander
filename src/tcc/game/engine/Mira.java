package tcc.game.engine;

import java.awt.Graphics;

public class Mira extends GameObject{
	
	private boolean visible;
	
	//Construtor
	public Mira(){
		getSpriteSheet().addImage("assets/images/mira.png");
		setScale(0.1f);
		setPosition(new Point(378,245));
		setVisible(true);
	}
	
	//Getters e Setters
	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	//Metodos
	public void draw(Graphics g){
		int width  = (int) (getSpriteSheet().getCurrentImage().getWidth(null)*getScale());
		int height = (int) (getSpriteSheet().getCurrentImage().getHeight(null)*getScale());
		g.drawImage(getSpriteSheet().getCurrentImage(), getPosition().getX()-width/2, getPosition().getY()-height/2 ,
				           width, height, null );
	}

}
