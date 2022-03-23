package listener;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import main.Main;

public class PlayerJoin implements Listener{
	
	private Main main = Main.getPlugin(Main.class);	
	
	//Event triggered when the player joins the server
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		Player player = e.getPlayer();
		if(main.getPlayerData().addPlayer(player)) {
			player.sendMessage(ChatColor.GREEN + "WELCOME FIRST TIMER!");
		} else  {
			player.sendMessage(ChatColor.GREEN + "Welcome back!");
		}
	}
}
