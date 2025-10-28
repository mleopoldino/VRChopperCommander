package tcc.game.engine;

public class Vector2D {
		
	//Definicao dos Atributos
	private int x;
	private int y;
	
	//Contrutores
	public Vector2D(){
		super();
	}
		
	public Vector2D(int x, int y){
		super();
		this.x = x;
		this.y = y;
	}
	
	//Getters e Setters
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
			
}
