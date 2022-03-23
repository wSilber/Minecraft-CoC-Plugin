package commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import clan.Clan;
import config.ClanConfigManager;
import config.PlayerDataConfigManager;
import main.Main;
import stats.PlayerData;

public class ClanCommands implements CommandExecutor {

	private Main main = Main.getPlugin(Main.class);

	private ClanConfigManager sm = main.getClanConfigManager();
	private PlayerDataConfigManager pc = main.getPlayerData();

	//Not Enough Arguments
	public void nea(CommandSender sender, String usage) {
		sender.sendMessage(ChatColor.RED + "ERROR: Not enough arguments!");
		sender.sendMessage(ChatColor.RED + "USAGE: /clan " + usage);
	}

	//Too Many Arguments
	public void tma(CommandSender sender, String usage) {
		sender.sendMessage(ChatColor.RED + "ERROR: Too many arguments!");
		sender.sendMessage(ChatColor.RED + "USAGE: /clan " + usage);
	}

	//Triggered when player types a command
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (label.equalsIgnoreCase("clan")) {
			if (args.length == 0) {
				if (sender instanceof Player) {
					Player player = (Player) sender;
					PlayerData pd = new PlayerData(
							PlayerDataConfigManager.getInstance().getPlayerData(player.getUniqueId().toString()));
					if (pd.getClan() != null) {
						Clan clan = new Clan(sm.getClan(pd.getClan().getName()));
						player.sendMessage(ChatColor.RED + "--------[" + ChatColor.GOLD + ChatColor.BOLD
								+ clan.getName() + ChatColor.RED + "]--------");
						player.sendMessage(ChatColor.RED + " - Leader: " + ChatColor.GOLD + clan.getLeader());
						player.sendMessage(ChatColor.RED + " - Members: " + ChatColor.GOLD + clan.getMembers());
						player.sendMessage(ChatColor.RED + " - Money: " + ChatColor.GOLD + clan.getMoney());
						player.sendMessage(ChatColor.RED + " - Elders: " + ChatColor.GOLD + clan.getElders());
						player.sendMessage(ChatColor.RED + " - Co-Leaders: " + ChatColor.GOLD + clan.getCoLeaders());
					} else {
						player.sendMessage(ChatColor.RED + "ERROR: You are not in a clan!");
					}
				}
			} else if (args.length == 1) {
				if (args[0].equalsIgnoreCase("create")) {
					nea(sender, "create (Clan)");
				} else if (args[0].equalsIgnoreCase("delete")) {
					if (sender instanceof Player) {
						Player player = (Player) sender;
						PlayerData pd = new PlayerData(pc.getPlayerData(player.getUniqueId().toString()));
						if (pd.getClan() != null) {
							Clan clan = new Clan(sm.getClan(pd.getClan().getName()));
							if (clan.getLeader().equalsIgnoreCase(player.getName())) {
								sm.removeClan(clan.getName(), main);
								player.sendMessage(ChatColor.GREEN + "SUCCESS: Clan " + ChatColor.GOLD + clan.getName()
										+ ChatColor.GREEN + " has been deleted");
							} else {
								player.sendMessage(
										ChatColor.RED + "ERROR: You must be the leader of the clan to do this!");
							}
						} else {
							player.sendMessage(ChatColor.RED + "ERROR: You are not in a clan!");
						}
					}
				} else if (args[0].equalsIgnoreCase("help")) {
					// Display Clan help
				} else if (args[0].equalsIgnoreCase("invite")) {
					nea(sender, "invite (Player)");
				} else if (args[0].equalsIgnoreCase("promote")) {
					nea(sender, "promote (Player)");
				} else if (args[0].equalsIgnoreCase("demote")) {
					nea(sender, "demote (Player)");
				} else if (args[0].equalsIgnoreCase("setLeader")) {
					nea(sender, "setLeader (Player)");
				} else if (args[0].equalsIgnoreCase("kick")) {
					nea(sender, "kick (player)");
				} else if (args[0].equalsIgnoreCase("list")) {
					sender.sendMessage(ChatColor.RED + "Listing all clans...");
					sender.sendMessage(ChatColor.GOLD + sm.getClans().toString());
				} else if (args[0].equalsIgnoreCase("leave")) {
					if (sender instanceof Player) {
						Player player = (Player) sender;
						PlayerData pd = new PlayerData(pc.getPlayerData(player.getUniqueId().toString()));
						if (pd.getClan() != null) {
							Clan clan = new Clan(sm.getClan(pd.getClan().getName()));
							if (clan.getLeader().equalsIgnoreCase(player.getName())) {
								sm.removeClan(clan.getName(), main);
								player.sendMessage(ChatColor.GREEN + "SUCCESS: Clan has been deleted!");
							} else {
								clan.removeMember(player.getName());
								sm.set(clan.getName(), clan.serialize());
								player.sendMessage(ChatColor.RED + "You have left the clan!");
							}
						} else {
							player.sendMessage(ChatColor.RED + "ERROR: You are not in a clan!");
						}
					} else {
						sender.sendMessage(ChatColor.RED + "ERROR: Must be a player to enter this command!");
					}
				} else {
					if (main.getClanConfigManager().containsClan(args[0])) {
						Clan clan = new Clan(main.getClanConfigManager().getClan(args[0]));
						sender.sendMessage(ChatColor.RED + "--------[" + ChatColor.GOLD + ChatColor.BOLD
								+ clan.getName() + ChatColor.RED + "]--------");
						sender.sendMessage(ChatColor.RED + " - Leader: " + ChatColor.GOLD + clan.getLeader());
						sender.sendMessage(ChatColor.RED + " - Members: " + ChatColor.GOLD + clan.getMembers());
						sender.sendMessage(ChatColor.RED + " - Money: " + ChatColor.GOLD + clan.getMoney());
						sender.sendMessage(ChatColor.RED + " - Elders: " + ChatColor.GOLD + clan.getElders());
						sender.sendMessage(ChatColor.RED + " - Co-Leaders: " + ChatColor.GOLD + clan.getCoLeaders());
					} else {
						sender.sendMessage(ChatColor.RED + "ERROR: " + args[0] + " does not exist!");
					}
				}
			} else if (args.length == 2) {
				if (args[0].equalsIgnoreCase("create")) {
					if (sender instanceof Player) {
						Player player = (Player) sender;
						PlayerData pd = new PlayerData(pc.getPlayerData(player.getUniqueId().toString()));
						if (pd.getClan() == null) {
							if (sm.addClan(args[1], player)) {
								player.sendMessage(ChatColor.GREEN + "SUCCESS: Clan " + ChatColor.GOLD + args[1]
										+ ChatColor.GREEN + " has been created!");
							} else {
								player.sendMessage(ChatColor.RED + "ERROR: Clan " + ChatColor.GOLD + args[1]
										+ ChatColor.RED + " already exists!");
							}
						} else {
							player.sendMessage(ChatColor.RED + "ERROR: You are already in a clan!");
						}
					} else {
						if (sm.addClan(args[1])) {
							sender.sendMessage(ChatColor.GREEN + "SUCCESS: clan " + ChatColor.GOLD + args[1]
									+ ChatColor.GREEN + " has been created!");
						} else {
							sender.sendMessage(ChatColor.RED + "ERROR: clan " + ChatColor.GOLD + args[1] + ChatColor.RED
									+ " already exists!");
						}
					}
				} else if (args[0].equalsIgnoreCase("delete")) {
					if (sender instanceof Player) {
						tma(sender, "delete");
					} else {
						if (sm.removeClan(args[1], main)) {
							sender.sendMessage(ChatColor.GREEN + "SUCCESS: Clan " + ChatColor.GOLD + args[1]
									+ ChatColor.GREEN + " has been deleted!");
						} else {
							sender.sendMessage(ChatColor.RED + "ERROR: Clan " + ChatColor.GOLD + args[1] + ChatColor.RED
									+ " does not exist!");
						}
					}
				} else if (args[0].equalsIgnoreCase("help")) {
					// display clan commands
				} else if (args[0].equalsIgnoreCase("invite")) {
					if (sender instanceof Player) {
						Player player = (Player) sender;
						PlayerData pd = new PlayerData(pc.getPlayerData(player.getUniqueId().toString()));
						if (pd.getClan() != null) {
							Clan clan = new Clan(sm.getClan(pd.getClan().getName()));
							if (main.getServer().getOnlinePlayers().contains(main.getServer().getPlayer(args[1]))) {
								if (!clan.getMembers().contains(args[1])) {
									clan.addMember(args[1]);
									sm.set(clan.getName(), clan.serialize());
									player.sendMessage(ChatColor.GREEN + "SUCCESS: " + args[1] + " has been added to "
											+ clan.getName());
									main.getServer().getPlayer(args[1]).sendMessage(
											ChatColor.GREEN + "You have been added into " + clan.getName());
								} else {
									player.sendMessage(ChatColor.RED + "ERROR: Player is already in the clan!");
								}
							} else {
								player.sendMessage(ChatColor.RED + "ERROR: Player " + args[1] + " does not exist!");
							}
						} else {
							player.sendMessage(ChatColor.RED + "ERROR: You are not in a clan!");
						}
					} else {
						sender.sendMessage(ChatColor.RED + "ERROR: Must be a player to enter this command");
						nea(sender, "addMember (Player) (Clan)");
					}
				} else if (args[0].equalsIgnoreCase("kick")) {
					if (sender instanceof Player) {
						Player player = (Player) sender;
						if (main.getServer().getOnlinePlayers().contains(main.getServer().getPlayer(args[1]))) {
							PlayerData pd = new PlayerData(pc.getPlayerData(player.getUniqueId().toString()));
							if (pd.getClan() != null) {
								Clan clan = new Clan(sm.getClan(pd.getClan().getName()));
								if (clan.getMembers().contains(args[1])) {
									if (clan.getRank(args[1]) > clan.getRank(player.getName())) {

										clan.removeMember(args[1]);
										sm.set(clan.getName(), clan.serialize());
										player.sendMessage(ChatColor.GREEN + "SUCCESS: " + args[1]
												+ " has been removed from the clan!");
										main.getServer().getPlayer(args[1]).sendMessage(
												ChatColor.RED + "You have been removed from " + clan.getName());

									} else {
										player.sendMessage(ChatColor.RED + "ERROR: You must be a higher rank!");
									}
								} else {
									player.sendMessage(ChatColor.RED + "ERROR: " + args[1] + " is not in your clan!");
								}
							} else {
								player.sendMessage(ChatColor.RED + "ERROR: You are not in a clan!");
							}
						} else {
							player.sendMessage(ChatColor.RED + "ERROR: Player " + args[1] + " does not exist!");
						}
					} else {
						sender.sendMessage(ChatColor.RED + "ERROR: Must be a player to enter this command");
						nea(sender, "removeMember (Player) (Clan)");
					}
				} else if (args[0].equalsIgnoreCase("promote")) {
					if (sender instanceof Player) {
						Player player = (Player) sender;
						PlayerData pd = new PlayerData(pc.getPlayerData(player.getUniqueId().toString()));
						if (main.getServer().getOnlinePlayers().contains(main.getServer().getPlayer(args[1]))) {
							if (pd.getClan() != null) {
								Clan clan = new Clan(sm.getClan(pd.getClan().getName()));
								if (clan.getLeader().equalsIgnoreCase(player.getName())) {
									if (clan.getMembers().contains(args[1])) {
										if (!clan.getElders().contains(args[1])
												&& !clan.getCoLeaders().contains(args[1])) {
											if (!(clan.getElders().size() >= 2)) {
												clan.addElder(args[1]);
												player.sendMessage(
														ChatColor.GREEN + "SUCCESS: " + args[1] + " is now an elder!");
												main.getServer().getPlayer(args[1]).sendMessage(
														ChatColor.GREEN + "Congradulations! You are now an elder!");
												sm.set(clan.getName(), clan.serialize());
											} else {
												player.sendMessage(ChatColor.RED
														+ "ERROR: You have reached the max amount of elders!");
											}
										} else if (clan.getElders().contains(args[1])) {
											clan.addCoLeader(args[1]);
											clan.removeElder(args[1]);
											player.sendMessage(
													ChatColor.GREEN + "SUCCESS: " + args[1] + " is now a Co-Leader!");
											main.getServer().getPlayer(args[1]).sendMessage(
													ChatColor.GREEN + "Congradulations! You are not a Co-Leader!");
											sm.set(clan.getName(), clan.serialize());
										} else {
											player.sendMessage(ChatColor.RED + "ERROR: You cannot promote past elder!");
										}
									} else {
										player.sendMessage(
												ChatColor.RED + "ERROR: " + args[1] + " is not in your clan!");
									}
								} else {
									player.sendMessage(
											ChatColor.RED + "ERROR: You must be the leader of the clan to do this!");
								}
							} else {
								player.sendMessage(ChatColor.RED + "ERROR: You are not in a clan!");
							}
						} else {
							player.sendMessage(ChatColor.RED + "ERROR: " + args[1] + " does not exist!");
						}
					} else {
						nea(sender, "promote (Player) (Clan)");
					}
				} else if (args[0].equalsIgnoreCase("demote")) {
					if (sender instanceof Player) {
						Player player = (Player) sender;
						PlayerData pd = new PlayerData(pc.getPlayerData(player.getUniqueId().toString()));
						if (main.getServer().getOnlinePlayers().contains(main.getServer().getPlayer(args[1]))) {
							if (pd.getClan() != null) {
								Clan clan = new Clan(sm.getClan(pd.getClan().getName()));
								if (clan.getMembers().contains(args[1])) {
									if (clan.getLeader().equalsIgnoreCase(player.getName())) {
										if (clan.getElders().contains(args[1])) {
											clan.removeElder(args[1]);
											player.sendMessage(
													ChatColor.GREEN + "SUCCESS: " + args[1] + " is now a villager!");
											main.getServer().getPlayer(args[1])
													.sendMessage(ChatColor.RED + "You have been demoted to villager!");
										} else if (clan.getCoLeaders().contains(args[1])) {
											clan.removeCoLeader(args[1]);
											clan.addElder(args[1]);
											player.sendMessage(
													ChatColor.GREEN + "SUCCESS: " + args[1] + " is now an elder!");
											main.getServer().getPlayer(args[1])
													.sendMessage(ChatColor.RED + "You have been demoted to elder!");
										} else {
											player.sendMessage(
													ChatColor.RED + "ERROR: " + args[1] + " is already a villager!");
										}
										sm.set(clan.getName(), clan.serialize());
									} else {
										player.sendMessage(
												ChatColor.RED + "ERROR: Must be the leader of the clan to do this!");
									}
								} else {
									player.sendMessage(ChatColor.RED + "ERROR: " + args[1] + " is not in the clan!");
								}
							} else {
								player.sendMessage(ChatColor.RED + "ERROR: You must be in a clan to do this!");
							}
						} else {
							player.sendMessage(ChatColor.RED + "ERROR: " + args[1] + " does not exist!");
						}
					} else {
						sender.sendMessage(ChatColor.RED + "ERROR: Must be a player to enter this command!");
					}
				} else if (args[0].equalsIgnoreCase("setLeader")) {
					if (sender instanceof Player) {
						Player player = (Player) sender;
						if (main.getServer().getOnlinePlayers().contains(main.getServer().getPlayer(args[1]))) {
							PlayerData data = new PlayerData(pc.getPlayerData(player.getUniqueId().toString()));
							if (data.getClan() != null) {
								Clan clan = new Clan(sm.getClan(data.getClan().getName()));
								if (clan.containsMember(args[1])) {
									if (clan.getLeader().equalsIgnoreCase(player.getName())) {
										clan.setLeader(args[1]);
										if (clan.getCoLeaders().contains(args[1]))
											clan.getCoLeaders().remove(args[1]);
										if (clan.getElders().contains(args[1]))
											;
										clan.getElders().remove(args[1]);
										sm.set(clan.getName(), clan.serialize());
										player.sendMessage(ChatColor.GREEN + "SUCCESS: " + args[1]
												+ " is now the leader of " + clan.getName() + "!");
										main.getServer().getPlayer(args[1]).sendMessage(
												ChatColor.GREEN + "You are now the leader of " + clan.getName() + "!");
									} else {
										player.sendMessage(
												ChatColor.RED + "ERROR: You must be the clan leader to do this!");
									}
								} else {
									player.sendMessage(ChatColor.RED + "ERROR: " + args[1] + " is not in your clan!");
								}
							} else {
								player.sendMessage(ChatColor.RED + "ERROR: You are not in a clan!");
							}
						} else {
							player.sendMessage(ChatColor.RED + "ERROR: " + args[1] + " does not exist!");
						}
					} else {
						sender.sendMessage(ChatColor.RED + "ERROR: Must be a player to enter this command!");
					}
				} else {
					// not a command
					// show clan usage
				}
			} else if (args.length == 3) {
				if (args[0].equalsIgnoreCase("create")) {
					tma(sender, "create (Clan)");
				} else if (args[0].equalsIgnoreCase("delete")) {
					tma(sender, "delete (Clan)");
				} else if (args[0].equalsIgnoreCase("help")) {
					// display clan commands
				} else if (args[0].equalsIgnoreCase("addMember")) {
					if (sm.containsClan(args[2])) {
						if (main.getServer().getOnlinePlayers().contains(main.getServer().getPlayer(args[1]))) {
							Clan clan = new Clan(sm.getClan(args[2]));
							if (clan.addMember(args[1])) {
								sender.sendMessage(
										ChatColor.GREEN + "SUCCESS: " + args[1] + " has joined " + args[2] + "!");
								main.getServer().getPlayer(args[1])
										.sendMessage(ChatColor.GREEN + "You have joined " + args[2] + "!");
								sm.set(args[2], clan.serialize());
							} else {
								sender.sendMessage(ChatColor.RED + "ERROR: " + args[1] + " is already in that clan!");
							}
							clan.serialize();
						} else {
							sender.sendMessage(
									ChatColor.RED + "ERROR: Player " + args[1] + " does not exist or is not online!");
						}
					} else {
						sender.sendMessage(ChatColor.RED + "ERROR: Clan " + args[2] + " does not exist!");
					}
				} else if (args[0].equalsIgnoreCase("removeMember")) {
					if (sm.containsClan(args[2])) {
						Clan clan = new Clan(sm.getClan(args[2]));
						if (clan.getMembers().contains(args[1])) {
							if (main.getServer().getOnlinePlayers().contains(main.getServer().getPlayer(args[1]))) {
								if (clan.removeMember(args[1])) {
									sender.sendMessage(ChatColor.GREEN + "SUCCESS: " + args[1]
											+ " has been removed from " + args[2] + "!");
									main.getServer().getPlayer(args[1])
											.sendMessage(ChatColor.RED + "You have been kicked from " + args[2] + "!");
									sm.set(args[2], clan.serialize());
								} else {
									sender.sendMessage(ChatColor.RED + "ERROR: " + args[1] + " is not in the clan!");
								}
							} else {
								sender.sendMessage(ChatColor.RED + "ERROR: Player " + args[1] + " does not exist!");
							}
						} else {
							sender.sendMessage(ChatColor.RED + "ERROR: Player " + args[1] + " is not in the clan!");
						}
					} else {
						sender.sendMessage(ChatColor.RED + "ERROR: Clan " + args[2] + " is not a clan!");
					}
				} else if (args[0].equalsIgnoreCase("promote")) {
					tma(sender, "promote (Player)");
				} else if (args[0].equalsIgnoreCase("demote")) {
					tma(sender, "demote (Player)");
				} else if (args[0].equalsIgnoreCase("setLeader")) {
					tma(sender, "setLeader (Player)");
				} else {
					// return clan stats of the given clan
				}
			} else {

			}
		}
		return true;
	}

}
