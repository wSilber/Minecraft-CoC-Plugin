package config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import clan.Clan;
import stats.PlayerData;

public class ClanConfigManager extends AbstractConfig{

	private static ClanConfigManager instance = new ClanConfigManager();

	public static ClanConfigManager getInstance() {
		return instance;
	}

	//creates the directory, file, and loads the config into the server
	public void setup(Plugin p) {
		fileName = "Clans.yml";
		super.setup(p);
	}

	//returns all the clans as a list
	public List<String> getClans() {
		List<String> clans = new ArrayList<String>();
		for (String key : config.getKeys(false)) {
			clans.add(key);
		}

		return clans;
	}

	//adds a clan to the config
	public boolean addClan(String name, Player player) {
		if (!containsClan(name)) {
			set(name, new Clan(name, player.getName()).serialize());
			return true;
		}
		return false;
	}

	//adds a clan to the config
	public boolean addClan(String name) {
		if (!containsClan(name)) {
			set(name, new Clan(name).serialize());
			return true;
		}
		return false;
	}

	//removes a clan from the config
	public boolean removeClan(String name, Plugin main) {
		if (containsClan(name)) {
			Clan clan = new Clan((HashMap<String, Object>) getClan(name));
			for(String player : clan.getMembers()) {
				PlayerData pd = new PlayerData(PlayerDataConfigManager.getInstance().getPlayerData(main.getServer().getPlayer(player).getUniqueId().toString()));
				pd.setClan(null);
				PlayerDataConfigManager.getInstance().set(pd.getUuid().toString(), pd.serialize());
			}
			set(name, null);
			return true;
		}
		return false;
	}

	//returns a HashMap version of the path specified
	public Map<String, Object> getClan(String path) {
		return  ClanConfigManager.getInstance().config.getConfigurationSection(path).getValues(false);
	}

	//returns true if the config contains clan name
	public boolean containsClan(String clan) {
		List<String> clans = new ArrayList<String>();
		clans = getClans();
		if (clans.contains(clan))
			return true;
		return false;
	}
}
