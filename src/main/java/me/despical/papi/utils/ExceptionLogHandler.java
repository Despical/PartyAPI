package me.despical.papi.utils;

import me.despical.papi.PartyPlugin;
import org.bukkit.Bukkit;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

/**
 * @author Despical
 * <p>
 * Created at 16.01.2021
 */
public class ExceptionLogHandler extends Handler {

	private final PartyPlugin plugin;
	private final String[] blacklistedClasses = {"data.MysqlManager", "commonsbox.database.MysqlDatabase"};

	public ExceptionLogHandler(PartyPlugin plugin) {
		this.plugin = plugin;

		Bukkit.getLogger().addHandler(this);
	}

	@Override
	public void close() throws SecurityException {
	}

	@Override
	public void flush() {
	}

	@Override
	public void publish(LogRecord record) {
		Throwable throwable = record.getThrown();

		if (!(throwable instanceof Exception) || !throwable.getClass().getSimpleName().contains("Exception")) {
			return;
		}

		if (throwable.getStackTrace().length <= 0) {
			return;
		}

		if (throwable.getCause() != null && throwable.getCause().getStackTrace() != null) {
			if (!throwable.getCause().getStackTrace()[0].getClassName().contains("me.despical.papi")) {
				return;
			}
		}

		if (!throwable.getStackTrace()[0].getClassName().contains("me.despical.papi")) {
			return;
		}

		if (containsBlacklistedClass(throwable)) {
			return;
		}

		record.setThrown(null);

		Exception exception = throwable.getCause() != null ? (Exception) throwable.getCause() : (Exception) throwable;
		StringBuilder stacktrace = new StringBuilder(exception.getClass().getSimpleName());

		if (exception.getMessage() != null) {
			stacktrace.append(" (").append(exception.getMessage()).append(")");
		}

		stacktrace.append("\n");

		for (StackTraceElement str : exception.getStackTrace()) {
			stacktrace.append(str.toString()).append("\n");
		}

		plugin.getLogger().log(Level.WARNING, "[Reporter Service] <<-----------------------------[START]----------------------------->>");
		plugin.getLogger().log(Level.WARNING, stacktrace.toString());
		plugin.getLogger().log(Level.WARNING, "[Reporter Service] <<------------------------------[END]------------------------------>>");

		record.setMessage("[PartyPlugin] We have found a bug in the code. Contact us at our official Discord server (Invite link: https://discordapp.com/invite/Vhyy4HA) with the following error given above!");
	}

	private boolean containsBlacklistedClass(Throwable throwable) {
		for (StackTraceElement element : throwable.getStackTrace()) {
			for (String blacklist : blacklistedClasses) {
				if (element.getClassName().contains("me.despical.classicduels." + blacklist)) {
					return true;
				}
			}
		}

		return false;
	}
}