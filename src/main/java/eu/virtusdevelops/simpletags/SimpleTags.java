package eu.virtusdevelops.simpletags;

import eu.virtusdevelops.simpletags.commands.TagsCommand;
import eu.virtusdevelops.simpletags.handlers.PlaceholderHandler;
import eu.virtusdevelops.simpletags.handlers.PlayerHandler;
import eu.virtusdevelops.simpletags.handlers.PlayerStorage;
import eu.virtusdevelops.simpletags.listeners.PlayerListener;
import eu.virtusdevelops.virtuscore.VirtusCore;
import eu.virtusdevelops.virtuscore.managers.FileManager;
import eu.virtusdevelops.virtuscore.utils.FileLocation;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.LinkedHashSet;

public class SimpleTags extends JavaPlugin {
    private FileManager fileManager;
    private PlayerStorage playerStorage;
    private PlayerHandler playerHandler;
    private PlaceholderHandler placeholderHandler;


    @Override
    public void onEnable() {
        ConsoleCommandSender sender = VirtusCore.console();
        saveDefaultConfig();

        this.fileManager = new FileManager(this, new LinkedHashSet<>(Arrays.asList(
                FileLocation.of("storage.yml", true),
                FileLocation.of("tags.yml", true)
        )));
        fileManager.loadFiles();

        playerStorage = new PlayerStorage(fileManager);
        playerHandler = new PlayerHandler(fileManager, playerStorage);
        placeholderHandler = new PlaceholderHandler(this, playerHandler);
        placeholderHandler.register();

        // Register events
        PluginManager pm = VirtusCore.plugins();
        pm.registerEvents(new PlayerListener(playerHandler), this);

        // register Commands
        getCommand("tags").setExecutor(new TagsCommand(playerHandler, this));
    }

    @Override
    public void onDisable() {
        // TODO: Disable everything.
    }

    public void save(){
        fileManager.saveFile("storage.yml");
    }

    public void reload(){
        reloadConfig();
        fileManager.clear();
        fileManager.loadFiles();

        playerHandler.reload();

    }
}
