package tcc.game.engine;

public class Cenario extends GameObject {
	
	public static final int SOBE     = 1;
	public static final int DESCE    = 2;
	public static final int DIREITA  = 3;
	public static final int ESQUERDA = 4;
	public static final int CENTRO   = 0;

	// FIX: Use GameConfig for movement speeds (adjusted for 60 FPS)
	private static final Vector2D cima     = new Vector2D(0, GameConfig.MOVEMENT_SPEED);
	private static final Vector2D baixo    = new Vector2D(0, -GameConfig.MOVEMENT_SPEED);
	private static final Vector2D direita  = new Vector2D(-GameConfig.MOVEMENT_SPEED, 0);
	private static final Vector2D esquerda = new Vector2D(GameConfig.MOVEMENT_SPEED, 0);
	
	private int direcao;
	
	//Construtor
	public Cenario(){
		// FIX: Call super() first (required for Java constructors)
		super();
		// FIX: Initialize direcao field before using parent methods
		this.direcao = CENTRO;
		// NOW safe to call methods inherited from GameObject
		getSpriteSheet().addImage("assets/images/cenario_sprite01.png");
		getSpriteSheet().addImage("assets/images/cenario_sprite02.png");
		getSpriteSheet().addImage("assets/images/cenario_sprite03.png");
		getSpriteSheet().addImage("assets/images/cenario_sprite04.png");
		getSpriteSheet().addImage("assets/images/cenario_sprite05.png");
		getSpriteSheet().addImage("assets/images/cenario_sprite06.png");
		setScale(GameConfig.CENARIO_SCALE);
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
			if(getPosition().getYFloat() <= GameConfig.CENARIO_TOP_BOUNDARY){
				getPosition().somaVector2D(cima);
			}
			break;
		case DESCE:
			if(getPosition().getYFloat() >= GameConfig.CENARIO_BOTTOM_BOUNDARY){
				getPosition().somaVector2D(baixo);
			}
			break;
		case DIREITA:
			if(getPosition().getXFloat() >= GameConfig.CENARIO_LEFT_BOUNDARY){
				getPosition().somaVector2D(direita);
			}
			break;
		case ESQUERDA:
			if(getPosition().getXFloat() <= GameConfig.CENARIO_RIGHT_BOUNDARY){
				getPosition().somaVector2D(esquerda);
			}
			break;
		}
		super.update();
	}

}
