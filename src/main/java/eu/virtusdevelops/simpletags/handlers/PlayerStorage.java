package eu.virtusdevelops.simpletags.handlers;

import eu.virtusdevelops.virtuscore.managers.FileManager;
import org.bukkit.entity.Player;

public class PlayerStorage {
    // TODO: Add implementation for player storage and their last selected tag.
    private FileManager fileManager;

    public PlayerStorage(FileManager fileManager){
        this.fileManager = fileManager;

    }

    public String getLastSelectedTag(Player player){
        return fileManager.getConfiguration("storage").getString("data." + player.getUniqueId() + ".last");
    }

    public void updateSelected(Player player, String tag){
        fileManager.getConfiguration("storage").set("data." + player.getUniqueId() + ".last", tag);
    }

}
