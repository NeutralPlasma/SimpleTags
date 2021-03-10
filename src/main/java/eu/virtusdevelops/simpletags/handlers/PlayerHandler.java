package eu.virtusdevelops.simpletags.handlers;

import eu.virtusdevelops.simpletags.data.TagData;
import eu.virtusdevelops.virtuscore.VirtusCore;
import eu.virtusdevelops.virtuscore.managers.FileManager;
import eu.virtusdevelops.virtuscore.utils.TextUtils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.*;

public class PlayerHandler {
    private Map<UUID, TagData> activeTags = new HashMap<>();
    private List<TagData> tags = new ArrayList<>();
    private PlayerStorage playerStorage;
    private FileManager fileManager;
    private final TagData nullTag = new TagData("NONE", "", "", "");

    public PlayerHandler(FileManager fileManager, PlayerStorage playerStorage){
        this.fileManager = fileManager;
        this.playerStorage = playerStorage;

        reload();
    }

    public List<TagData> getTags(){
        return this.tags;
    }


    public void reload(){
        activeTags.clear();
        tags.clear();

        // Load all tags.
        ConfigurationSection section = fileManager.getConfiguration("tags").getConfigurationSection("tags");
        if(section != null) {
            for (String data : section.getKeys(false)) {
                TagData tag = new TagData(
                        data,
                        section.getString(data + ".description"),
                        section.getString(data + ".tag"),
                        "simpletags.tag." + section.getString(data + ".permission")
                );
                tags.add(tag);
            }
        }
        // Load all players

        for(Player player: Bukkit.getOnlinePlayers()){
            TagData tag = getTag(playerStorage.getLastSelectedTag(player));
            if(tag != null && player.hasPermission(tag.getPermission())){
                activeTags.put(player.getUniqueId(), tag);
                //fileManager.getConfiguration("storage").set("data." + player.getUniqueId() + ".last", tag.getName());
            }
        }

    }

    public TagData getTag(String name){
        for(TagData data : tags){
            if(data.getName().equals(name)){
                return data;
            }
        }
        return null;
    }

    public TagData getActivePlayerTag(Player player){
        return activeTags.get(player.getUniqueId()) == null ? nullTag : activeTags.get(player.getUniqueId());
    }

    public void addPlayer(Player player, TagData data){
        if(player.hasPermission(data.getPermission())){
            activeTags.put(player.getUniqueId(), data);
            fileManager.getConfiguration("storage").set("data." + player.getUniqueId() + ".last", data.getName());
        }
    }

    public void addPlayer(Player player){
        TagData tag = getTag(playerStorage.getLastSelectedTag(player));
        if(tag != null && player.hasPermission(tag.getPermission())){
            activeTags.put(player.getUniqueId(), tag);
            fileManager.getConfiguration("storage").set("data." + player.getUniqueId() + ".last", tag.getName());
        }
    }

    public void removePlayer(Player player){
        activeTags.remove(player.getUniqueId());
    }
}
