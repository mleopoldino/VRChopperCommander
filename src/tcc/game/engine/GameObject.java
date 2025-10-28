package tcc.game.engine;

import java.awt.Graphics;

public  class GameObject {
	
	//Definicao de Atributos
	private Point position;
	private int width;
	private int heigth;
	private float scale=1;
	private SpriteSheet spriteSheet;
	
	//Construtor
	public GameObject(){
		spriteSheet = new SpriteSheet();
		position    = new Point();
	}
	
	//Getters e Setters
	public Point getPosition() {
		return position;
	}
	public void setPosition(Point position) {
		this.position = position;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeigth() {
		return heigth;
	}
	public void setHeigth(int heigth) {
		this.heigth = heigth;
	}

	public SpriteSheet getSpriteSheet() {
		return spriteSheet;
	}

	public void setSpriteSheet(SpriteSheet spriteSheet) {
		this.spriteSheet = spriteSheet;
	}
	
	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}
	
	//Metodos
	public void update(){
		spriteSheet.update();
	}
	public void draw(Graphics g){
		g.drawImage(spriteSheet.getCurrentImage(), position.getX(), 
				position.getY(), (int)(spriteSheet.getCurrentImage().getWidth(null)*scale), (int)(spriteSheet.getCurrentImage().getHeight(null)*scale), null);
	}
	
}
