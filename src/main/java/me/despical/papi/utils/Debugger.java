package me.despical.papi.utils;

import me.despical.commonsbox.compat.VersionResolver;
import me.despical.commonsbox.string.StringMatcher;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Despical
 * <p>
 * Created at 16.01.2021
 */
public class Debugger {

	private static final HashSet<String> listenedPerformance = new HashSet<>();
	private static boolean enabled = false;
	private static boolean deep = false;
	private static final Logger logger = Logger.getLogger("Classic Duels");

	private Debugger() {
	}

	public static void setEnabled(boolean enabled) {
		Debugger.enabled = enabled;
	}

	public static void deepDebug(boolean deep) {
		Debugger.deep = deep;
	}

	public static void monitorPerformance(String task) {
		listenedPerformance.add(task);
	}

	public static void sendConsoleMessage(String message) {
		if (VersionResolver.isCurrentEqualOrHigher(VersionResolver.ServerVersion.v1_16_R1) && message.contains("#")) {
			message = StringMatcher.matchColorRegex(message);
		}

		Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', message));
	}

	/**
	 * Prints debug message with selected log level. Messages of level INFO or TASK
	 * won't be posted if debugger is enabled, warnings and errors will be.
	 *
	 * @param level level of debugged message
	 * @param msg message to debug
	 */
	public static void debug(Level level, String msg) {
		if (!enabled && (level != Level.WARNING || level != Level.SEVERE)) {
			return;
		}

		logger.log(level, "[PAPIDBG] " + msg);
	}

	/**
	 * Prints debug message with selected INFO log level.
	 *
	 * @param msg debugged message
	 * @param params to debug
	 */
	public static void debug(String msg, Object... params) {
		debug(Level.INFO, msg, params);
	}

	/**
	 * Prints debug message with selected log level. Messages of level INFO or TASK
	 * won't be posted if debugger is enabled, warnings and errors will be.
	 *
	 * @param msg debugged message
	 */
	public static void debug(String msg) {
		debug(Level.INFO, msg);
	}

	/**
	 * Prints debug message with selected log level and replaces parameters.
	 * Messages of level INFO or TASK won't be posted if debugger is enabled,
	 * warnings and errors will be.
	 *
	 * @param level level of debugged message
	 * @param msg debugged message
	 * @param params any params to debug
	 */
	public static void debug(Level level, String msg, Object... params) {
		if (!enabled && (level != Level.WARNING || level != Level.FINE)) {
			return;
		}

		logger.log(level, "[PAPIDBG] " + msg, params);
	}

	/**
	 * Prints performance debug message with selected log level and replaces
	 * parameters.
	 *
	 * @param monitorName name of monitor
	 * @param msg debugged message
	 * @param params any params to debug
	 */
	public static void performance(String monitorName, String msg, Object... params) {
		if (!deep || !listenedPerformance.contains(monitorName)) {
			return;
		}

		logger.log(Level.INFO, "[PAPIDBG] " + msg, params);
	}
}