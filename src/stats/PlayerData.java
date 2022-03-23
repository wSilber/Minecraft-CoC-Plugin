package stats;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;

import clan.Clan;
import main.Main;

public class PlayerData implements ConfigurationSerializable{
	
	private Main main = Main.getPlugin(Main.class);	
	
	private UUID uuid;
	private String name;
	private int money;
	private int level;
	private Clan clan;
	private String rank;
	
//Constructors
	
	//creates a player object with a player
	public PlayerData(Player player) {
		uuid = player.getUniqueId();
		name = player.getName();
		initialize();
	}
	
	//creates a player object with a name
	public PlayerData(String name) {
		this.name = name;
		uuid = main.getServer().getPlayer(name).getUniqueId();
		initialize();
	}
	
	//creates a player object with all the information it can hold
	public PlayerData(String name, UUID uuid, int money, int level, Clan clan, String rank) {
		this.name = name;
		this.uuid = uuid;
		this.money = money;
		this.level = level;
		this.clan = clan;
		this.rank = rank;
	}
	
	//creates a player object given a HashMap of a playerData object
	public PlayerData(Map<String, Object> map) {
		this.name = (String) map.get("name");
		this.money = (int) Integer.parseInt((String) map.get("money"));
		this.level = (int) Integer.parseInt((String) map.get("level"));
		if(map.get("clan") != null) {
		this.clan = new Clan(main.getClanConfigManager().getClan((String) map.get("clan")));
		} else {
			this.clan = null;
		}
		this.rank = (String) map.get("rank");
		this.uuid = main.getServer().getPlayer(name).getUniqueId();
	}
	
	//returns the player name
	public Player getPlayer(String name) {
		return main.getServer().getPlayer(name);
	}
	
	//initializes the player object to default values
	public void initialize() {
		money = 0;
		level = 0;
		clan = null;
		rank = "member";
	}

	//returns the uuid
	public UUID getUuid() {
		return uuid;
	}

	//returns the name
	public String getName() {
		return name;
	}
	
	//returns the money
	public int getMoney() {
		return money;
	}

	//sets the money to value money
	public void setMoney(int money) {
		this.money = money;
	}

	//returns the level
	public int getLevel() {
		return level;
	}

	//sets the level
	public void setLevel(int level) {
		this.level = level;
	}

	//returns the clan 
	public Clan getClan() {
		return clan;
	}

	//sets the clan
	public void setClan(Clan clan) {
		this.clan = clan;
	}

	//returns the rank
	public String getRank() {
		return rank;
	}

	//sets the rank
	public void setRank(String rank) {
		this.rank = rank;
	}

	//Converts the playerData object into a HashMap for storing
	@Override
	public Map<String, Object> serialize() {
		HashMap<String, Object> map = new HashMap<String, Object>();

		map.put("name", name);
		map.put("uuid", uuid.toString());
		map.put("money", Integer.toString(money));
		map.put("level", Integer.toString(level));
		if(clan != null) { 
			map.put("clan", clan.getName());
		} else {
			map.put("clan", null);
		}
		map.put("rank", rank);
		
		return map;
	}
	
}
