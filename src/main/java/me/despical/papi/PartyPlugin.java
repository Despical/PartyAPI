package me.despical.papi;

import me.despical.papi.utils.Debugger;
import me.despical.papi.utils.ExceptionLogHandler;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author Despical
 * <p>
 * Created at 16.01.2021
 */
public class PartyPlugin extends JavaPlugin {

	private ExceptionLogHandler exceptionLogHandler;

	@Override
	public void onEnable() {
		exceptionLogHandler = new ExceptionLogHandler(this);
		saveDefaultConfig();

		Debugger.setEnabled(getDescription().getVersion().contains("debug") || getConfig().getBoolean("Debug-Messages"));
		Debugger.debug("Initialization started.");

		if (getConfig().getBoolean("Developer-Mode")) {
			Debugger.deepDebug(true);
			Debugger.debug("Deep debug enabled.");
			getConfig().getStringList("Listenable-Performances").forEach(Debugger::monitorPerformance);
		}

		long start = System.currentTimeMillis();

		Debugger.debug("Initialization finished took {0} ms", System.currentTimeMillis() - start);
	}

	@Override
	public void onDisable() {
		Bukkit.getLogger().removeHandler(exceptionLogHandler);
	}
}