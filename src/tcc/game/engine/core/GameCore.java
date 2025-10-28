package tcc.game.engine.core;

import java.awt.Component;
import java.awt.Graphics;

import tcc.game.engine.Cenario;
import tcc.game.engine.Explosao;
import tcc.game.engine.GameObject;
import tcc.game.engine.GameOver;
import tcc.game.engine.Inimigo;
import tcc.game.engine.Mira;
import tcc.game.engine.Point;
import tcc.game.engine.Tiro;
//import tcc.game.engine.Sound;

public class GameCore extends Component {

	private Cenario cenario;
	private GameObject cockpit;
	private GameObject tela;
	private Inimigo inimigo;
	private Tiro tiro;
	private Mira mira;
	private Explosao explosao;
	private GameOver gameOver;
	//private Sound sound;
	
	private int countDestroy = 0;
	private int pontos = 0;
	private int countError = 0;
	private int count = 0;
	
	//Construtor
	public GameCore() {
		
		cenario = new Cenario();
		cenario.setPosition(new Point(-378, -100));

		inimigo = new Inimigo();
		inimigo.setPosition(new Point(378, 245));

		mira = new Mira();
		explosao = new Explosao();
		
		cockpit = new GameObject();
		cockpit.getSpriteSheet().addImage("assets/images/painel_sprite.png");
		
		tela = new GameObject();
		tela.getSpriteSheet().addImage("assets/images/capa_sprite.png");

		tiro = new Tiro();
			
		gameOver = new GameOver();
		
		//sound = new Sound();
		//sound.start();
				
	}
	
	//Getters e Setters
	public int getCountDestroy() {
		return countDestroy;
	}

	public void setCountDestroy(int countDestroy) {
		this.countDestroy = countDestroy;
	}
	
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
	//Metodos
	public synchronized void paint(Graphics g) {
						
		this.update();
		
		cenario.draw(g);
		inimigo.draw(g);
		mira.draw(g);
		
			
		if (tiro.isVisible()){
			tiro.draw(g);
		}
			
		if (explosao.isVisible()){
			explosao.draw(g);
			this.countDestroy++;
			somaPontos();
		}
		
		if(getCount()==0){				
			
			tela.draw(g);
			
		} else {
			cockpit.draw(g);
			
			//Placar
			g.drawString(String.valueOf(this.pontos*10), 160, 475);
			
			if(this.countError<=3){
				g.drawString(String.valueOf(this.countError), 580, 475);
			} else {
				g.drawString("3", 680, 50);
			}
			
			if(validaGameOver()){
				gameOver.draw(g);
				gameOver.update();
				inimigo.setVisible(false);
								
			}
			
		}
				
		
		
		try {
			Thread.sleep(80);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		repaint();
	}

	public boolean verificaColisao() {
		int inimigoWidth, inimigoHeight, inimigoX, inimigoY;
		inimigoX = inimigo.getPosition().getX();
		inimigoY = inimigo.getPosition().getY();
		inimigoWidth = (int) (inimigo.getSpriteSheet().getCurrentImage().getWidth(null) * inimigo.getScale());
		inimigoHeight = (int) (inimigo.getSpriteSheet().getCurrentImage().getHeight(null) * inimigo.getScale());

		int miraX, miraY;
		miraX = mira.getPosition().getX()
				+ (int) (mira.getSpriteSheet().getCurrentImage().getWidth(null) * mira.getScale()) / 2;
		miraY = mira.getPosition().getY()
				+ (int) (mira.getSpriteSheet().getCurrentImage().getHeight(null) * mira.getScale()) / 2;

		if (miraX >= inimigoX && miraX <= inimigoX + inimigoWidth && miraY >= inimigoY
				&& miraY <= inimigoY + inimigoHeight) {
			return true;
			
		} 
			
		return false;
	}

	public void update() {
				
		inimigo.update();
		cenario.update();
		cockpit.update();
		tiro.update();
		tela.update();
		mira.update();
		
		if (tiro.isVisible() && verificaColisao()) {
			explosao.setPosition(inimigo.getPosition());
			explosao.setScale(inimigo.getScale());
			inimigo.setVisible(false);
			explosao.explode();
			gameOver.update();
		} 
		
		explosao.update();
				
	}

	public void changeCenario(int direcao) {
		cenario.setDirecao(direcao);
		inimigo.setDirecao(direcao);
	}

	public void atira(boolean status) {
		tiro.setVisible(status);
	}
	
	public void somaPontos(){
		this.pontos = this.countDestroy/4;
	}
	
	public boolean validaGameOver(){
		boolean status = true;
		this.countError = inimigo.getErro()-this.pontos;
		if(this.countError < 3){
			status = false;
			return status;
		} else {
			status = true;
			return status;
		}
	}
	
}
