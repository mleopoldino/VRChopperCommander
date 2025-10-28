package tcc.game.engine;

public class Cenario extends GameObject {
	
	public static final int SOBE     = 1;
	public static final int DESCE    = 2;
	public static final int DIREITA  = 3;
	public static final int ESQUERDA = 4;
	public static final int CENTRO   = 0;
	
	private static final Vector2D cima     = new Vector2D(0  ,20);
	private static final Vector2D baixo    = new Vector2D(0  ,-20);
	private static final Vector2D direita  = new Vector2D(-20,0);
	private static final Vector2D esquerda = new Vector2D(20 ,0);
	
	private int direcao;
	
	//Construtor
	public Cenario(){
		super();
		getSpriteSheet().addImage("assets/images/cenario_sprite01.png");
		getSpriteSheet().addImage("assets/images/cenario_sprite02.png");
		getSpriteSheet().addImage("assets/images/cenario_sprite03.png");
		getSpriteSheet().addImage("assets/images/cenario_sprite04.png");
		getSpriteSheet().addImage("assets/images/cenario_sprite05.png");
		getSpriteSheet().addImage("assets/images/cenario_sprite06.png");
		setScale(2);
		
	}
	
	//Getters e Setters 
	public int getDirecao() {
		return direcao;
	}

	public void setDirecao(int direcao) {
		this.direcao = direcao;
	}
	
	//Metodos
	public void update(){
		switch(direcao){
		case SOBE:
			if(getPosition().getY()<=-1){
				getPosition().somaVector2D(cima);
			}
			break;
		case DESCE:
			if(getPosition().getY()>=-460){
				getPosition().somaVector2D(baixo);
			}
			break;
		case DIREITA:
			if(getPosition().getX()>=-755){
				getPosition().somaVector2D(direita);
			}
			break;
		case ESQUERDA:
			if(getPosition().getX()<=-20){
				getPosition().somaVector2D(esquerda);
			}
			break;
		}
		super.update();
	}

}
