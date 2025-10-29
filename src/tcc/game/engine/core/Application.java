package tcc.game.engine.core;

import java.awt.Image;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

import tcc.game.engine.Cenario;
import tcc.game.engine.GameConfig;
import tcc.game.engine.GameLog;

public class Application {
	
	private final JFrame tela;
	private final GameCore core;
	private final CountDownLatch shutdownLatch;
	private final KeyEventDispatcher keyDispatcher;
	
	//Construtor
	public Application(CountDownLatch shutdownLatch){
		this.shutdownLatch = shutdownLatch;

		tela = new JFrame("VR Chopper Commander");

		// FIX: Use ImageIO.read() instead of deprecated Toolkit.getImage()
		Image icon = loadApplicationIcon("assets/icon/GameIcon.png");

		core = new GameCore();
		core.setFocusable(true);

		tela.add(core);
		if (icon != null) {
			tela.setIconImage(icon);
		}
		tela.setSize(GameConfig.WINDOW_WIDTH, GameConfig.WINDOW_HEIGHT);
		tela.setFocusable(true);
		tela.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		tela.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				core.stopGame();
			}

			@Override
			public void windowClosed(WindowEvent e) {
				KeyboardFocusManager.getCurrentKeyboardFocusManager().removeKeyEventDispatcher(keyDispatcher);
				shutdownLatch.countDown();
			}
		});

		keyDispatcher = event -> {
			if (event.getID() == KeyEvent.KEY_PRESSED) {
				handleKeyPressed(event);
			} else if (event.getID() == KeyEvent.KEY_RELEASED) {
				handleKeyReleased(event);
			}
			return false;
		};
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(keyDispatcher);
	}
	
	public void start() {
		tela.setLocationRelativeTo(null);
		tela.setVisible(true);
		tela.requestFocus();
		tela.requestFocusInWindow();
		core.requestFocusInWindow();
		javax.swing.SwingUtilities.invokeLater(() -> {
			tela.requestFocusInWindow();
			core.requestFocusInWindow();
		});
	}

	private void handleKeyPressed(KeyEvent arg0) {
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
			core.stopGame();
			tela.dispose();
			break;
		}
	}

	private void handleKeyReleased(KeyEvent arg0) {
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

	/**
	 * FIX: Load application icon using ImageIO instead of deprecated Toolkit.getImage()
	 * This ensures the image is fully loaded before use and provides proper error handling
	 */
	private Image loadApplicationIcon(String iconPath) {
		try {
			File iconFile = new File(iconPath);
			if (!iconFile.exists()) {
				GameLog.warn("Application icon not found: " + iconPath);
				return null;
			}

			BufferedImage icon = ImageIO.read(iconFile);
			if (icon == null) {
				GameLog.warn("Failed to load application icon (ImageIO returned null): " + iconPath);
				return null;
			}

			GameLog.debug("Successfully loaded application icon: " + iconPath);
			return icon;

		} catch (IOException e) {
			GameLog.warn("Error loading application icon: " + iconPath, e);
			return null; // Non-critical - app can run without icon
		}
	}

}
