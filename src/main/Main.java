package main;

import java.util.logging.Logger;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import commands.ClanCommands;
import commands.PlayerDataCommands;
import config.ClanConfigManager;
import config.PlayerDataConfigManager;
import listener.PlayerJoin;

public class Main extends JavaPlugin implements Listener {
	
	ClanConfigManager sm = ClanConfigManager.getInstance();
	PlayerDataConfigManager pdcm = PlayerDataConfigManager.getInstance();

	//Event triggered when the server starts up (one time action)
	public void onEnable() {
		
		sm.setup(this);
		pdcm.setup(this);
		
		registerCommands();
		registerListeners();

		Logger.getLogger("Minecraft").info("Plugin is being enabled!");
	}
	

	//Event triggered when the server shuts down (one time action)
	public void onDisable() {
		Logger.getLogger("Minecraft").info("Plugin is being disabled!");
		sm.save();
		pdcm.save();
	}
	
	//registers all of the commands
	public void registerCommands() {
		getServer().getPluginCommand("clan").setExecutor(new ClanCommands());
		getServer().getPluginCommand("stats").setExecutor(new PlayerDataCommands());
	}
	
	//registers all of the listeners
	public void registerListeners() {
		getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
	}
	
	//returns the clanconfigmanager object
	public ClanConfigManager getClanConfigManager() {
		return sm;
	}
	
	//returns the playerdataconfigmanager object
	public PlayerDataConfigManager getPlayerData() {
		return pdcm;
	}
}
