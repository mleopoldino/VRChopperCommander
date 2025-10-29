package tcc.game.engine;

public class Tiro extends GameObject {
	
	private boolean visible;
	
	//Construtor
	public Tiro(){
		// FIX: Call super() first, then initialize fields before using parent methods
		super();
		this.visible = false;
		// NOW safe to call methods inherited from GameObject
		getSpriteSheet().addImage("assets/images/tiro_sprite01.png");
		getSpriteSheet().addImage("assets/images/tiro_sprite02.png");
		setPosition(new Point(GameConfig.TIRO_OFFSET_X, GameConfig.TIRO_OFFSET_Y));
	}

	//Getters e Setters
	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

}
