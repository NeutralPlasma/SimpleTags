package eu.virtusdevelops.simpletags.listeners;

import eu.virtusdevelops.simpletags.handlers.PlayerHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;


public class PlayerListener implements Listener {
    private PlayerHandler playerHandler;

    public PlayerListener(PlayerHandler playerHandler){
        this.playerHandler = playerHandler;
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void playerJoin(PlayerJoinEvent event){
        playerHandler.addPlayer(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void playerQuit(PlayerQuitEvent event){
        playerHandler.removePlayer(event.getPlayer());
    }
}
