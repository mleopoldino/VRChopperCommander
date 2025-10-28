package tcc.game.engine.core;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import tcc.game.engine.Cenario;

public class Application implements KeyListener {
	
	private JFrame   tela;
	private GameCore core;
	private boolean  running;
	
	//Construtor
	public Application(){
		tela = new JFrame("VR Chopper Commander");
		Image icon = Toolkit.getDefaultToolkit().getImage("assets/icon/GameIcon.png");		
		
		core = new GameCore();
		
		running = true;
		tela.add(core);
		tela.setIconImage(icon);
		tela.setSize(756, 530);
		tela.addKeyListener(this);
		
		tela.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		tela.setVisible(true);
	}
	
	//Metodos
	public void run(){
		while (running){
		
		}
		System.exit(0);
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		switch(arg0.getKeyCode()){
		case KeyEvent.VK_UP:
			core.changeCenario(Cenario.SOBE);
			break;
		case KeyEvent.VK_DOWN:
			core.changeCenario(Cenario.DESCE);
			break;
		case KeyEvent.VK_LEFT:
			core.changeCenario(Cenario.ESQUERDA);
			break;
		case KeyEvent.VK_RIGHT:
			core.changeCenario(Cenario.DIREITA);
			break;
		case KeyEvent.VK_SPACE:
			core.atira(true);
			break;
		case KeyEvent.VK_ENTER:
			core.setCount(1);
			break;
		case KeyEvent.VK_ESCAPE:
			// FIX: ESC key now works correctly in keyPressed
			core.stopGame();
			running = false;
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		switch(arg0.getKeyCode()){
		case KeyEvent.VK_UP:
		case KeyEvent.VK_DOWN:
		case KeyEvent.VK_LEFT:
		case KeyEvent.VK_RIGHT:
			core.changeCenario(Cenario.CENTRO);
			break;
		case KeyEvent.VK_SPACE:
			core.atira(false);
			break;
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// Not used - key handling moved to keyPressed/keyReleased
	}

}
