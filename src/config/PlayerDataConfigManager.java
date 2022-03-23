package config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import stats.PlayerData;

public class PlayerDataConfigManager extends AbstractConfig{

	private static PlayerDataConfigManager instance = new PlayerDataConfigManager();

	public static PlayerDataConfigManager getInstance() {
		return instance;
	}	
	
	//creates the directory, file, and loads the config into the server
	public void setup(Plugin p) {
		fileName = "PlayerData.yml";
		super.setup(p);
	}

	//Returns a HashMap version of a playerData object specified at path
	public Map<String, Object> getPlayerData(String path) {
		if(containsPlayer(path))
			return PlayerDataConfigManager.getInstance().config.getConfigurationSection(path).getValues(false);
		return null;
	}

	//returns all of the playerstats as a list
	public List<String> getAllStats() {
		List<String> allStats = new ArrayList<String>();

		for (String key : config.getKeys(false)) {
			allStats.add(key);
		}

		return allStats;
	}

	//returns true if the config contains player
	public boolean containsPlayer(Player player) {
		if (getAllStats().contains(player.getUniqueId().toString()))
			return true;
		return false;
	}

	//returns true if the config contains the player name
	public boolean containsPlayer(String name) {
		if (getAllStats().contains(name))
			return true;
		return false;
	}

	//adds a player to the config
	public boolean addPlayer(Player player) {
		if (!containsPlayer(player)) {
			PlayerData pd = new PlayerData(player);
			config.set(player.getUniqueId().toString(), pd.serialize());
			save();
			return true;
		}
		return false;
	}

	//adds a player to the config
	public boolean addPlayer(String name) {
		if (!containsPlayer(name)) {
			PlayerData pd = new PlayerData(name);
			config.set(pd.getUuid().toString(), pd.serialize());
			save();
			return true;
		}
		return false;
	}

	//adds a player to the config
	public boolean addPlayer(PlayerData pd) {
		set(pd.getUuid().toString(), pd.serialize());
		save();
		return true;
	}	

	//returns a new playerData object with the given player
	public PlayerData getStats(Player player) {
		if(containsPlayer(player)) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map = (HashMap<String, Object>) getPlayerData(player.getUniqueId().toString());
			PlayerData pd = new PlayerData(map);
			return pd;
		}
		return null;
	}
	
	//removes a player from the config
	public void removePlayer(Player player) {
		if(containsPlayer(player)) {
			config.set(player.getUniqueId().toString(), null);
			save();
		}
	}
	
	//clears the config
	public void clear() {
		for(String key : config.getKeys(false)) {
			config.set(key, null);
		}
		save();
	}
}
