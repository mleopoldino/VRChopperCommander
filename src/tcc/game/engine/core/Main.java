package tcc.game.engine.core;

import java.util.concurrent.CountDownLatch;

import javax.swing.SwingUtilities;

public class Main {
	public static void main(String args[]){
		CountDownLatch shutdownLatch = new CountDownLatch(1);

		SwingUtilities.invokeLater(() -> {
			Application app = new Application(shutdownLatch);
			app.start();
		});

		try {
			shutdownLatch.await();
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
		}

		System.exit(0);
	}

}
