package tcc.game.engine;

public class Point {
	
	//Definicao de Atributos
	private int x;
	private int y;
	
	//Construtores
	public Point(){
		super();
	}
		
	public Point(int x, int y){
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
			
	//Metodos
	public void setPoint(Point p){
		this.x = p.getX();
		this.y = p.getY();
	}
	
	public void somaVector2D(Vector2D vector){
		this.x += vector.getX();
		this.y += vector.getY();
	}
	
}
