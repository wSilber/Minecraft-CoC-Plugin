package commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import config.PlayerDataConfigManager;
import main.Main;
import stats.PlayerData;

public class PlayerDataCommands implements CommandExecutor {

	private Main main = Main.getPlugin(Main.class);

	private PlayerDataConfigManager playerData = main.getPlayerData();

	//Not Enough Arguments
	public void nea(CommandSender sender, String usage) {
		sender.sendMessage(ChatColor.RED + "ERROR: Not enough arguments!");
		sender.sendMessage(ChatColor.RED + "USAGE: /stats " + usage);
	}

	//Too Many Arguments
	public void tma(CommandSender sender, String usage) {
		sender.sendMessage(ChatColor.RED + "ERROR: Too many arguments!");
		sender.sendMessage(ChatColor.RED + "USAGE: /stats " + usage);
	}

	//Triggered when a player types a command
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (label.equalsIgnoreCase("stats")) {
			if (args.length == 0) {
				if (sender instanceof Player) {
					Player player = (Player) sender;
					if (playerData.containsPlayer(player)) {
						PlayerData data = new PlayerData(playerData.getPlayerData(player.getUniqueId().toString()));
						player.sendMessage(ChatColor.RED + "--------[" + ChatColor.GOLD + ChatColor.BOLD
								+ player.getName() + ChatColor.RED + "]--------");
						player.sendMessage(ChatColor.RED + " - UUID: " + ChatColor.GOLD + data.getUuid().toString());
						player.sendMessage(ChatColor.RED + " - Rank: " + ChatColor.GOLD + data.getRank());
						player.sendMessage(ChatColor.RED + " - Money: " + ChatColor.GOLD + data.getMoney());
						player.sendMessage(ChatColor.RED + " - Level: " + ChatColor.GOLD + data.getLevel());
						if (data.getClan() == null) {
							player.sendMessage(ChatColor.RED + " - Clan: " + ChatColor.GOLD + "None");
						} else {
							player.sendMessage(ChatColor.RED + " - Clan: " + ChatColor.GOLD + data.getClan().getName());
						}
					} else {
						player.sendMessage(ChatColor.RED + "ERROR: " + player.getName() + " does not exist!");
					}
				} else {
					nea(sender, "(Player)");
				}
			} else if (args.length == 1) {
				if (sender instanceof Player) {
					Player player = (Player) sender;
					if (args[0].equalsIgnoreCase("uuid")) {
						PlayerData data = new PlayerData(playerData.getPlayerData(player.getUniqueId().toString()));
						player.sendMessage(ChatColor.RED + "--------[" + ChatColor.GOLD + ChatColor.BOLD
								+ player.getName() + ChatColor.RED + "]--------");
						player.sendMessage(ChatColor.RED + " - UUID: " + ChatColor.GOLD + data.getUuid().toString());
					} else if (args[0].equalsIgnoreCase("rank")) {
						PlayerData data = new PlayerData(playerData.getPlayerData(player.getUniqueId().toString()));
						player.sendMessage(ChatColor.RED + "--------[" + ChatColor.GOLD + ChatColor.BOLD
								+ player.getName() + ChatColor.RED + "]--------");
						player.sendMessage(ChatColor.RED + " - Rank: " + ChatColor.GOLD + data.getRank());
					} else if (args[0].equalsIgnoreCase("money")) {
						PlayerData data = new PlayerData(playerData.getPlayerData(player.getUniqueId().toString()));
						player.sendMessage(ChatColor.RED + "--------[" + ChatColor.GOLD + ChatColor.BOLD
								+ player.getName() + ChatColor.RED + "]--------");
						player.sendMessage(ChatColor.RED + " - Money: " + ChatColor.GOLD + data.getMoney());
					} else if (args[0].equalsIgnoreCase("level")) {
						PlayerData data = new PlayerData(playerData.getPlayerData(player.getUniqueId().toString()));
						player.sendMessage(ChatColor.RED + "--------[" + ChatColor.GOLD + ChatColor.BOLD
								+ player.getName() + ChatColor.RED + "]--------");
						player.sendMessage(ChatColor.RED + " - Level: " + ChatColor.GOLD + data.getLevel());
					} else if (args[0].equalsIgnoreCase("clan")) {
						PlayerData data = new PlayerData(playerData.getPlayerData(player.getUniqueId().toString()));
						player.sendMessage(ChatColor.RED + "--------[" + ChatColor.GOLD + ChatColor.BOLD
								+ player.getName() + ChatColor.RED + "]--------");
						if (data.getClan() == null) {
							player.sendMessage(ChatColor.RED + " - Clan: " + ChatColor.GOLD + "None");
						} else {
							player.sendMessage(ChatColor.RED + " - Clan: " + ChatColor.GOLD + data.getClan().getName());
						}
					} else {
						if (main.getServer().getOnlinePlayers().contains(main.getServer().getPlayer(args[0]))) {
							PlayerData data = new PlayerData(playerData
									.getPlayerData(main.getServer().getPlayer(args[0]).getUniqueId().toString()));
							player.sendMessage(ChatColor.RED + "--------[" + ChatColor.GOLD + ChatColor.BOLD
									+ data.getName() + ChatColor.RED + "]--------");
							player.sendMessage(
									ChatColor.RED + " - UUID: " + ChatColor.GOLD + data.getUuid().toString());
							player.sendMessage(ChatColor.RED + " - Rank: " + ChatColor.GOLD + data.getRank());
							player.sendMessage(ChatColor.RED + " - Money: " + ChatColor.GOLD + data.getMoney());
							player.sendMessage(ChatColor.RED + " - Level: " + ChatColor.GOLD + data.getLevel());
							if (data.getClan() != null) {
								player.sendMessage(
										ChatColor.RED + " - Clan: " + ChatColor.GOLD + data.getClan().getName());
							} else {
								player.sendMessage(ChatColor.RED + " - Clan: " + ChatColor.GOLD + "none");
							}
						} else {
							player.sendMessage(ChatColor.RED + "ERROR: " + args[0] + " does not exist!");
						}
					}
				} else {
					if (main.getServer().getOnlinePlayers().contains(main.getServer().getPlayer(args[0]))) {
						PlayerData data = new PlayerData(
								playerData.getPlayerData(main.getServer().getPlayer(args[0]).getUniqueId().toString()));
						sender.sendMessage(ChatColor.RED + "--------[" + ChatColor.GOLD + ChatColor.BOLD
								+ data.getName() + ChatColor.RED + "]--------");
						sender.sendMessage(ChatColor.RED + " - UUID: " + ChatColor.GOLD + data.getUuid().toString());
						sender.sendMessage(ChatColor.RED + " - Rank: " + ChatColor.GOLD + data.getRank());
						sender.sendMessage(ChatColor.RED + " - Money: " + ChatColor.GOLD + data.getMoney());
						sender.sendMessage(ChatColor.RED + " - Level: " + ChatColor.GOLD + data.getLevel());
						if (data.getClan() != null) {
							sender.sendMessage(ChatColor.RED + " - Clan: " + ChatColor.GOLD + data.getClan().getName());
						} else {
							sender.sendMessage(ChatColor.RED + " - Clan: " + ChatColor.GOLD + "null");
						}
					} else {
						sender.sendMessage(ChatColor.RED + "ERROR: " + args[0] + " does not exist!");
					}
				}

			} else if (args.length == 2) {

				if (main.getServer().getOnlinePlayers().contains(main.getServer().getPlayer(args[1]))) {
					PlayerData data = new PlayerData(
							playerData.getPlayerData(main.getServer().getPlayer(args[1]).getUniqueId().toString()));
					if (args[0].equalsIgnoreCase("uuid")) {
						sender.sendMessage(ChatColor.RED + "--------[" + ChatColor.GOLD + ChatColor.BOLD
								+ data.getName() + ChatColor.RED + "]--------");
						sender.sendMessage(ChatColor.RED + " - UUID: " + ChatColor.GOLD + data.getUuid().toString());
					} else if (args[0].equalsIgnoreCase("rank")) {
						sender.sendMessage(ChatColor.RED + "--------[" + ChatColor.GOLD + ChatColor.BOLD
								+ data.getName() + ChatColor.RED + "]--------");
						sender.sendMessage(ChatColor.RED + " - Rank: " + ChatColor.GOLD + data.getRank());
					} else if (args[0].equalsIgnoreCase("money")) {
						sender.sendMessage(ChatColor.RED + "--------[" + ChatColor.GOLD + ChatColor.BOLD
								+ data.getName() + ChatColor.RED + "]--------");
						sender.sendMessage(ChatColor.RED + " - Money: " + ChatColor.GOLD + data.getMoney());
					} else if (args[0].equalsIgnoreCase("level")) {
						sender.sendMessage(ChatColor.RED + "--------[" + ChatColor.GOLD + ChatColor.BOLD
								+ data.getName() + ChatColor.RED + "]--------");
						sender.sendMessage(ChatColor.RED + " - Level: " + ChatColor.GOLD + data.getLevel());
					} else if (args[0].equalsIgnoreCase("clan")) {
						sender.sendMessage(ChatColor.RED + "--------[" + ChatColor.GOLD + ChatColor.BOLD
								+ data.getName() + ChatColor.RED + "]--------");
						if (data.getClan() == null) {
							sender.sendMessage(ChatColor.RED + " - Clan: " + ChatColor.GOLD + "None");
						} else {
							sender.sendMessage(ChatColor.RED + " - Clan: " + ChatColor.GOLD + data.getClan().getName());
						}
					}
				} else {
					sender.sendMessage(ChatColor.RED + "ERROR: " + args[1] + " does not exist!");
				}

			}
		}
		return false;

	}
}
