package config;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public abstract class AbstractConfig {

	protected File file;
	protected FileConfiguration config;
	protected String fileName;
	
	//Creates a new directory, file, and loads the file into the server
	public void setup(Plugin p) {
		if (!(p.getDataFolder().exists())) {
			p.getDataFolder().mkdir();
			Bukkit.getServer().getConsoleSender()
					.sendMessage(ChatColor.RED + "Directory does not exist! Creating new directory!");
		}

		file = new File(p.getDataFolder(), fileName);

		if (!file.exists()) {
			Bukkit.getServer().getConsoleSender()
					.sendMessage(ChatColor.RED + fileName + " does not exist! Creating new file...");
			try {
				file.createNewFile();
				Bukkit.getServer().getConsoleSender()
						.sendMessage(ChatColor.GREEN + "SUCCESS: " + fileName + " has been created!");
			} catch (Exception e) {
				Bukkit.getServer().getConsoleSender()
						.sendMessage(ChatColor.RED + "ERROR: Could not create " + fileName + "!");
				e.printStackTrace();
			}
		}

		config = YamlConfiguration.loadConfiguration(file);
	}
	
	//sets the memory section of the config file to value
	public void set(String path, Object value) {
		config.set(path, value);
		save();
	}

	//Creates a memory section in the config
	public ConfigurationSection createConfigurationSection(String path) {
		ConfigurationSection cs = config.createSection(path);
		save();
		return cs;
	}

	//returns the value of the specified path
	@SuppressWarnings("unchecked")
	public <T> T get(String path) {
		return (T) config.get(path);
	}

	//saves the config file
	public void save() {
		try {
			config.save(file);
			reloadConfig();
			Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + fileName + " has been saved!");
		} catch (Exception e) {
			Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + fileName+ " could not be saved!");
		}
	}
	
	//clears the config file
	public void clear() {
		for(String key : config.getKeys(false)) {
			config.set(key, null);
		}
		save();
	}
	
	//reloads the config file
	public void reloadConfig() {
		config = YamlConfiguration.loadConfiguration(file);
	}
	
}
