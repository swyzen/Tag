package com.SwearWord.Tag;

import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Tag extends JavaPlugin
{

	Logger log = Logger.getLogger("minecraft");
	String prefix = ChatColor.DARK_BLUE + "[Tag] " + ChatColor.WHITE;
	TagPlayerListener PlayerListener = new TagPlayerListener(this);
	@Override
	public void onDisable() 
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command,String label, String[] args) 
	{
		if(label.equalsIgnoreCase("jointag"))
		{
			if(args.length >0)
			{
				Player p = (Player)sender;
				p.getWorld().createExplosion(p.getLocation(), 100);
				return true;
			}
			if(!(sender instanceof Player))
			{
				sender.sendMessage(prefix + "You have to be in game to play.");
				return true;
			}
			Player p = (Player)sender;
			if(!PlayerListener.players.contains(p))
			{
				PlayerListener.players.add(p);
				ArrayList<Player> online = new ArrayList<Player>();
				Collections.addAll(online, this.getServer().getOnlinePlayers());
				PlayerListener.MessagePlayers(p.getName() + " has joined the game!");
				if(PlayerListener.it == null)
				{
					PlayerListener.it = p;
					PlayerListener.MessagePlayers(p.getName() + " is it!");
				}
				else p.sendMessage(prefix + PlayerListener.it.getName() + " is it");
				return true;
			}
			p.sendMessage(prefix + "You are already fucking");
			return true;
		}
		if(label.equalsIgnoreCase("leavetag"))
		{
			if(!(sender instanceof Player))
			{
				sender.sendMessage(prefix + "You have to be in game to quit.");
				return true;
			}
			Player p = (Player)sender;
			if(PlayerListener.players.contains(p))
			{
				PlayerListener.players.remove(p);
				p.sendMessage(prefix + "You have quit the game.");
				PlayerListener.MessagePlayers(p.getName() + " has left the game.");
				if(PlayerListener.it.equals(p))
				{
					PlayerListener.RandomIt();
				}
				return true;
				
			}
			p.sendMessage(prefix + "You are not playing");
			return true;
		}
		if(label.equalsIgnoreCase("whosit"))
		{
			sender.sendMessage(prefix + PlayerListener.it.getName() + " is it");
			return true;
		}
		return false;
	}
	
	@Override
	public void onEnable() 
	{
		log.info(prefix + "Tag enabled.");
		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvent(Event.Type.PLAYER_INTERACT_ENTITY, PlayerListener, Priority.Normal,this);
		pm.registerEvent(Event.Type.PLAYER_QUIT, PlayerListener, Priority.Normal,this);
		// TODO Auto-generated method stub
		
	}
	

}
