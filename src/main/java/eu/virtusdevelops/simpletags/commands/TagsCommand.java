package eu.virtusdevelops.simpletags.commands;

import eu.virtusdevelops.simpletags.SimpleTags;
import eu.virtusdevelops.simpletags.guis.TagsGui;
import eu.virtusdevelops.simpletags.handlers.PlayerHandler;
import eu.virtusdevelops.virtuscore.utils.HexUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TagsCommand implements CommandExecutor {

    private PlayerHandler playerHandler;
    private SimpleTags simpleTags;

    public TagsCommand(PlayerHandler playerHandler, SimpleTags simpleTags){
        this.playerHandler = playerHandler;
        this.simpleTags = simpleTags;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if(args.length > 0){
            if(args[0].equalsIgnoreCase("reload") ){
                if(sender.hasPermission("simpletags.tagscommand.reload")) {
                    simpleTags.reload();
                    sender.sendMessage(HexUtil.colorify(simpleTags.getConfig().getString("lang.reload")));
                }else{
                    sender.sendMessage(HexUtil.colorify(simpleTags.getConfig().getString("lang.noperm")));
                }
            }
        }else{
            if(sender instanceof Player) {
                new TagsGui((Player) sender, playerHandler, simpleTags);
            }
        }
        return true;
    }
}
