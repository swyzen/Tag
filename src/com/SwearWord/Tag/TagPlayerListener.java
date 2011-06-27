package com.SwearWord.Tag;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerQuitEvent;

public class TagPlayerListener extends PlayerListener
{
	private Tag t;
	public Player tb=null;
	public Player it=null;
	private Random r = new Random();
	public ArrayList<Player> players = new ArrayList<Player>();
	public TagPlayerListener(Tag plugin)
	{
		t = plugin;
	}
	
	
	public void onPlayerQuit(PlayerQuitEvent event) 
	{
		if(players.contains(event.getPlayer())) players.remove(event.getPlayer());
		if(event.getPlayer().equals(it))
		{
			RandomIt();
		}
		
	}
	
	public void RandomIt()
	{
		tb = null;
		if(players.size() > 0)
		{
			it = players.get(r.nextInt(players.size()));
			MessagePlayers(it.getName() + " is it. Run away!");
			return;
		}
		it = null;
	}
	
	public void onPlayerInteractEntity(PlayerInteractEntityEvent event) 
	{
		Player p = event.getPlayer();
		if(it != p)
		{
			return;
		}
		if(!(event.getRightClicked() instanceof Player))
		{
			return;
		}
		Player target = (Player)event.getRightClicked();
		if(!players.contains(target))
		{
			p.sendMessage(t.prefix + target.getName() + " is not playing.");
			return;
		}
		if(tb != null && tb.equals(target))
		{
			p.sendMessage(t.prefix + "No tag backs!");
			return;
		}
		tb = it;
		it = target;
		//it.damage(2);
		MessagePlayers(it.getName() + " is it.");
	}
	
	public void MessagePlayers(String m)
	{
		for(int i=0;i<players.size();i++)
		{
			players.get(i).sendMessage(t.prefix + m);
		}
	}

}
