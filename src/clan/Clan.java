package clan;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

import config.PlayerDataConfigManager;
import main.Main;
import stats.PlayerData;

public class Clan implements ConfigurationSerializable {

	private Main main = Main.getPlugin(Main.class);
	
	private PlayerDataConfigManager data = main.getPlayerData();

	private String name;
	private ArrayList<String> members;
	private String leader;
	private ArrayList<String> coLeaders;
	private ArrayList<String> elders;
	private int money;

	//Clan constructor takes in the clan name
	public Clan(String name) {
		this.name = name;
		this.members = new ArrayList<String>();
		this.leader = "";
		this.money = 0;
		this.elders = new ArrayList<String>();
		this.coLeaders = new ArrayList<String>();
	}

	//Clan constructor takes in the clan name and leader
	public Clan(String name, String leader) {
		this.name = name;
		this.leader = leader;
		this.members = new ArrayList<String>();
		addMember(leader);
		this.money = 0;
		this.elders = new ArrayList<String>();
		this.coLeaders = new ArrayList<String>();
	}

	//Clan constructor takes in a hashmap of clan data
	public Clan(Map<String, Object> map) {
		this.name = (String) map.get("name");
		this.members = new ArrayList<String>(Arrays.asList(((String) map.get("members")).split(",")));
		this.leader = (String) map.get("leader");
		this.money = Integer.parseInt((String) map.get("money"));
		this.elders = new ArrayList<String>(Arrays.asList(((String) map.get("elders")).split(",")));
		this.coLeaders = new ArrayList<String>(Arrays.asList(((String) map.get("co-leaders")).split(",")));
	}

	//Clan constructor takes in a hashmap of clan data and sets the leader
	public Clan(Map<String, Object> map, String leader) {
		this.name = (String) map.get("name");
		this.members = new ArrayList<String>(Arrays.asList(((String) map.get("members")).split(",")));
		this.leader = leader;
		this.money = Integer.parseInt((String) map.get("money"));
		this.elders = new ArrayList<String>(Arrays.asList(((String) map.get("elders")).split(",")));
		this.coLeaders = new ArrayList<String>(Arrays.asList(((String) map.get("co-leaders")).split(",")));
	}

	//returns the clan name
	public String getName() {
		return name;
	}

	//returns the clan members
	public List<String> getMembers() {
		return members;
	}

	//returns the clan leader
	public String getLeader() {
		return leader;
	}

	//returns the clan's money
	public int getMoney() {
		return money;
	}
	
	//returns the clan's co-leaders
	public ArrayList<String> getCoLeaders() {
		return coLeaders;
	}
	
	//returns the clan's elders
	public ArrayList<String> getElders() {
		return elders;
	}
	
	//promotes a player to elder if villager, and to co-leader if elder
	public boolean promote(String name) {
		if(!elders.contains(name) && !coLeaders.contains(name)) {
			elders.add(name);
			return true;
		} else if(elders.contains(name)) {
			coLeaders.add(name);
			return true;
		} 
		return false;
	}
	
	//adds a co-leader to the clan
	public boolean addCoLeader(String name) {
		if(!coLeaders.contains(name)) {
			coLeaders.add(name);
			return true;
		} 
		return false;
	}
	
	//removes a co-leader from the clan
	public boolean removeCoLeader(String name) {
		if(coLeaders.contains(name)) {
			coLeaders.remove(name);
			return true;
		}
		return false;
	}
	
	//adds an elder to the clan
	public boolean addElder(String name) {
		if(!elders.contains(name)) {
			elders.add(name);
			return true;
		}
		return false;
	}
	
	//removes an elder from the clan
	public boolean removeElder(String name) {
		if(elders.contains(name)) {
			elders.remove(name);
			return true;
		}
		return false;
	}

	//sets the name of the clan
	public void setName(String name) {
		this.name = name;
	}

	//sets the leader of the clan
	public void setLeader(String leader) {
		this.leader = leader;
	}

	//sets the money of the clan
	public void setMoney(int money) {
		this.money = money;
	}

	//returns true if the clan contains the member
	public boolean containsMember(String name) {
		for (String player : members) {
			if (player.toLowerCase().equalsIgnoreCase(name.toLowerCase()))
				return true;
		}
		return false;
	}
	
	//returns the rank of the player as a number
	public int getRank(String name) {
		if(leader == name)
			return 3;
		if(coLeaders.contains(name)) 
			return 2; 
		else if(elders.contains(name))
			return 1;
		return 0;
	}

	//adds a member to the clan
	public boolean addMember(String name) {
		if (!containsMember(name)) {
			PlayerData playerData = new PlayerData(data.getPlayerData(main.getServer().getPlayer(name).getUniqueId().toString()));
			if(playerData.getClan() == null) {
				PlayerData temp = new PlayerData(playerData.getName(), playerData.getUuid(), playerData.getMoney(), playerData.getLevel(), this, playerData.getRank());
				main.getPlayerData().addPlayer(temp);
				members.add(name);
			} else {
				return false;
			}
			return true;
		}
		return false;
	}

	//removes a member from the clan
	public boolean removeMember(String name) {
		if (containsMember(name)) {
			members.remove(name);
			PlayerData playerData = new PlayerData(data.getPlayerData(main.getServer().getPlayer(name).getUniqueId().toString()));
			PlayerData temp = new PlayerData(playerData.getName(), playerData.getUuid(), playerData.getMoney(), playerData.getLevel(), null, playerData.getRank());
			main.getPlayerData().addPlayer(temp);
			if(coLeaders.contains(name))
				coLeaders.remove(name);
			if(elders.contains(name))
				elders.remove(name);
			if(leader == name)
				return false;
			return true;
		}
		return false;
	}

	//converts the clan into a hashmap for saving
	@Override
	public Map<String, Object> serialize() {
		HashMap<String, Object> map = new HashMap<String, Object>();

		map.put("name", name);
		map.put("members", join(members, ","));
		map.put("leader", leader);
		map.put("money", Integer.toString(money));
		map.put("elders", join(elders, ","));
		map.put("co-leaders", join(coLeaders, ","));

		return map;
	}

	//returns a string version of the collection with words separated with a comma
	private String join(Collection<String> collection, String marker) {
		StringBuilder stb = new StringBuilder();

		for (String str : collection) {
			stb.append(str).append(marker);
		}

		return stb.toString();
	}

}
