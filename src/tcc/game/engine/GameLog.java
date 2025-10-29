package tcc.game.engine;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Centralized logging utility with optional verbose output.
 * Enable verbose logs by passing -Dtcc.game.verbose=true to the JVM.
 */
public final class GameLog {

	private static final Logger LOGGER = Logger.getLogger("tcc.game");
	private static final boolean VERBOSE = Boolean.parseBoolean(System.getProperty("tcc.game.verbose", "false"));

	private GameLog() {
		// Utility class
	}

	public static void debug(String message) {
		if (VERBOSE) {
			LOGGER.log(Level.INFO, message);
		}
	}

	public static void info(String message) {
		LOGGER.log(Level.INFO, message);
	}

	public static void warn(String message) {
		LOGGER.log(Level.WARNING, message);
	}

	public static void warn(String message, Throwable throwable) {
		LOGGER.log(Level.WARNING, message, throwable);
	}

	public static void error(String message, Throwable throwable) {
		LOGGER.log(Level.SEVERE, message, throwable);
	}
}
