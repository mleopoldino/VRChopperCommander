package tcc.game.engine;

public class Point {
	
	//Definicao de Atributos
    private float x;
    private float y;
	
	//Construtores
    public Point(){
        this(0f, 0f);
    }
        
    public Point(int x, int y){
        this((float)x, (float)y);
    }

    public Point(float x, float y){
        this.x = x;
        this.y = y;
    }
	
	//Getters e Setters
    public int getX() {
        return Math.round(x);
    }
    public float getXFloat() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public void setX(float x) {
        this.x = x;
    }
    public int getY() {
        return Math.round(y);
    }
    public float getYFloat() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }
    public void setY(float y) {
        this.y = y;
    }
			
	//Metodos
    public void setPoint(Point p){
        this.x = p.getXFloat();
        this.y = p.getYFloat();
    }
	
    public void somaVector2D(Vector2D vector){
        this.x += vector.getX();
        this.y += vector.getY();
    }

}
