package eu.virtusdevelops.simpletags.handlers;

import eu.virtusdevelops.simpletags.SimpleTags;
import eu.virtusdevelops.virtuscore.utils.HexUtil;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;

public class PlaceholderHandler extends PlaceholderExpansion{

    private SimpleTags plugin;
    private PlayerHandler playerHandler;

    public PlaceholderHandler(SimpleTags plugin, PlayerHandler playerHandler){
        this.playerHandler = playerHandler;
        this.plugin = plugin;
    }

    /**
     * This method should always return true unless we
     * have a dependency we need to make sure is on the server
     * for our placeholders to work!
     *
     * @return always true since we do not have any dependencies.
     */
    @Override
    public boolean canRegister(){
        return true;
    }

    @Override
    public String getAuthor(){
        return "NeutralPlasma";
    }


    @Override
    public String getIdentifier(){
        return "simpletags";
    }


    @Override
    public String getVersion(){
        return "1.0.0";
    }


    @Override
    public String onPlaceholderRequest(Player player, String identifier){

        if(player == null){
            return "";
        }

        // %simpletags_tag%
        if(identifier.equals("tag")){
            return playerHandler.getActivePlayerTag(player).getTag();
        }

        // %simpletags_tag%
        if(identifier.equals("tag_colored")){
            return HexUtil.colorify(playerHandler.getActivePlayerTag(player).getTag());
        }

        // %simpletags_tagdescription%
        if(identifier.equals("tagdescription")){
            return playerHandler.getActivePlayerTag(player).getDescription();
        }

        // %simpletags_tagdescription%
        if(identifier.equals("tagname")){
            return playerHandler.getActivePlayerTag(player).getName();
        }


        // We return null if an invalid placeholder (f.e. %someplugin_placeholder3%)
        // was provided
        return null;
    }

}
