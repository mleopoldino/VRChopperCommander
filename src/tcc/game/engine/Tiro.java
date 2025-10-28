package tcc.game.engine;

public class Tiro extends GameObject{
	
	private boolean visible;
	
	//Construtor
	public Tiro(){
		getSpriteSheet().addImage("assets/images/tiro_sprite01.png");
		getSpriteSheet().addImage("assets/images/tiro_sprite02.png");
		setVisible(false);
		setPosition(new Point(-10, 95));	//Alinhamento do Tiro com a Mira
	}

	//Getters e Setters
	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

}
