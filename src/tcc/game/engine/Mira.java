package tcc.game.engine;

import java.awt.Graphics;

public class Mira extends GameObject {
	
	private boolean visible;
	
	//Construtor
	public Mira(){
		// FIX: Call super() first, then initialize fields before using parent methods
		super();
		this.visible = true;
		// NOW safe to call methods inherited from GameObject
		getSpriteSheet().addImage("assets/images/mira.png");
		setScale(GameConfig.MIRA_SCALE);
		setPosition(new Point(GameConfig.INITIAL_MIRA_X, GameConfig.INITIAL_MIRA_Y));
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
		// FIX: Defensive null check
		if (getSpriteSheet() == null || getSpriteSheet().getCurrentImage() == null) {
			GameLog.warn("Cannot draw Mira: sprite or image is null");
			return;
		}
		int width  = (int) (getSpriteSheet().getCurrentImage().getWidth(null)*getScale());
		int height = (int) (getSpriteSheet().getCurrentImage().getHeight(null)*getScale());
		g.drawImage(getSpriteSheet().getCurrentImage(), getPosition().getX()-width/2, getPosition().getY()-height/2 ,
				           width, height, null );
	}

}
