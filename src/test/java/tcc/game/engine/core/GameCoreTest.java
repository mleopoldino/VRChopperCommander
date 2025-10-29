package tcc.game.engine.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import javax.swing.SwingUtilities;
import javax.swing.Timer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import tcc.game.engine.GameConfig;
import tcc.game.engine.Inimigo;
import tcc.game.engine.Mira;
import tcc.game.engine.Point;
import tcc.game.engine.Tiro;

class GameCoreTest {

	private GameCore core;

	@BeforeAll
	static void setHeadlessMode() {
		System.setProperty("java.awt.headless", "true");
	}

	@BeforeEach
	void createCore() throws Exception {
		core = createGameCoreOnEdt();
	}

	@AfterEach
	void tearDown() throws Exception {
		if (core != null) {
			SwingUtilities.invokeAndWait(() -> core.stopGame());
		}
	}

	@Test
	void timerUsesFrameDelayFromConfig() throws Exception {
		Timer gameTimer = callOnEdt(() -> (Timer) getField(GameCore.class, "gameTimer").get(core));
		assertEquals(GameConfig.FRAME_DELAY_MS, gameTimer.getDelay(), "Timer delay should match GameConfig");
	}

	@Test
	void explosionCountsScoreOnce() throws Exception {
		runOnEdt(() -> core.setCount(1));

		Inimigo inimigo = callOnEdt(() -> (Inimigo) getField(GameCore.class, "inimigo").get(core));
		Mira mira = callOnEdt(() -> (Mira) getField(GameCore.class, "mira").get(core));
		Tiro tiro = callOnEdt(() -> (Tiro) getField(GameCore.class, "tiro").get(core));

		Point target = new Point(350, 220);
		runOnEdt(() -> {
			inimigo.setVisible(true);
			inimigo.setScale(0.5f);
			inimigo.setPosition(target);

			mira.getPosition().setPoint(target);
			mira.setVisible(true);

			tiro.setVisible(true);
			tiro.setPosition(target);

			core.update();
		});

		Method updateGameLogic = GameCore.class.getDeclaredMethod("updateGameLogic");
		updateGameLogic.setAccessible(true);

		callOnEdt(() -> {
			updateGameLogic.invoke(core);
			return null;
		});
		assertEquals(4, core.getCountDestroy(), "Explosion should add 4 to destruction counter");

		callOnEdt(() -> {
			updateGameLogic.invoke(core);
			return null;
		});
		assertEquals(4, core.getCountDestroy(), "Explosion should only be counted once");

		int pontos = callOnEdt(() -> (int) getField(GameCore.class, "pontos").get(core));
		assertEquals(1, pontos, "Score should reflect a single enemy destroyed");
	}

	@Test
	void collisionDetectionMatchesOverlappingTargets() throws Exception {
		runOnEdt(() -> core.setCount(1));

		Inimigo inimigo = callOnEdt(() -> (Inimigo) getField(GameCore.class, "inimigo").get(core));
		Mira mira = callOnEdt(() -> (Mira) getField(GameCore.class, "mira").get(core));

		Point target = new Point(320, 200);
		runOnEdt(() -> {
			inimigo.setVisible(true);
			inimigo.setScale(0.5f);
			inimigo.setPosition(target);
			mira.getPosition().setPoint(target);
		});

		assertTrue(callOnEdt(() -> core.verificaColisao()), "Overlapping mira and enemy should collide");

		runOnEdt(() -> mira.getPosition().setPoint(new Point(10, 10)));
		assertFalse(callOnEdt(() -> core.verificaColisao()), "Separated mira and enemy should not collide");
	}

	@Test
	void stopGameHaltsTimer() throws Exception {
		Timer gameTimer = callOnEdt(() -> (Timer) getField(GameCore.class, "gameTimer").get(core));
		assertTrue(gameTimer.isRunning(), "Timer should start running on construction");

		runOnEdt(() -> core.stopGame());
		assertFalse(gameTimer.isRunning(), "Timer should stop after stopGame()");
	}

	private static GameCore createGameCoreOnEdt() throws Exception {
		CountDownLatch latch = new CountDownLatch(1);
		AtomicReference<GameCore> ref = new AtomicReference<>();

		SwingUtilities.invokeLater(() -> {
			ref.set(new GameCore());
			latch.countDown();
		});

		if (!latch.await(5, TimeUnit.SECONDS)) {
			throw new IllegalStateException("Timeout creating GameCore on EDT");
		}

		return ref.get();
	}

	private static Field getField(Class<?> type, String name) throws Exception {
		Field field = type.getDeclaredField(name);
		field.setAccessible(true);
		return field;
	}

	private static void runOnEdt(Runnable action) throws Exception {
		SwingUtilities.invokeAndWait(action);
	}

	private static <T> T callOnEdt(java.util.concurrent.Callable<T> callable) throws Exception {
		AtomicReference<T> ref = new AtomicReference<>();
		AtomicReference<Exception> error = new AtomicReference<>();
		SwingUtilities.invokeAndWait(() -> {
			try {
				ref.set(callable.call());
			} catch (Exception ex) {
				error.set(ex);
			}
		});
		if (error.get() != null) {
			throw error.get();
		}
		return ref.get();
	}
}
