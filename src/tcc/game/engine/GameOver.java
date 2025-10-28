package tcc.game.engine;

import java.awt.Graphics;

public class GameOver extends GameObject {
	
	private boolean visible;
	
	//Construtor
	public GameOver(){
		super();
		getSpriteSheet().addImage("assets/images/gameover_sprite01.png");
		getSpriteSheet().addImage("assets/images/gameover_sprite02.png");
		setScale(1f);
		visible = true;
		setPosition(new Point(378,245));
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
		int width = (int) (getSpriteSheet().getCurrentImage().getWidth(null) * getScale());
		int height = (int) (getSpriteSheet().getCurrentImage().getHeight(null) * getScale());
		g.drawImage(getSpriteSheet().getCurrentImage(), getPosition().getX() - width / 2,
				getPosition().getY() - height / 2, width, height, null);
		
	}
	
	public void update(){
		super.update();
			
	}
	
	
}
