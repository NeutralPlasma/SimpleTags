package eu.virtusdevelops.simpletags.guis;

import eu.virtusdevelops.simpletags.SimpleTags;
import eu.virtusdevelops.simpletags.data.TagData;
import eu.virtusdevelops.simpletags.handlers.PlayerHandler;
import eu.virtusdevelops.virtuscore.gui.Icon;
import eu.virtusdevelops.virtuscore.gui.InventoryCreator;
import eu.virtusdevelops.virtuscore.utils.HexUtil;
import eu.virtusdevelops.virtuscore.utils.TextUtils;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class TagsGui {
    private InventoryCreator inventory = new InventoryCreator(54, "Title here");
    private SimpleTags plugin;
    private List<Icon> icons = new ArrayList<>();
    private int currentPage = 1;
    private Player player;
    private PlayerHandler playerHandler;

    public TagsGui(Player player, PlayerHandler playerHandler, SimpleTags simpleTags){
        this.playerHandler = playerHandler;
        this.plugin = simpleTags;
        this.player = player;
        inventory.addCloseActions( (action, action2) -> {

        });
        inventory.setTitle(TextUtils.colorFormat(plugin.getConfig().getString("lang.gui.title")));

        load();
    }

    public void construct(){
        inventory.clean();
        for(int i = 0; i < 27; i++){
            int index = i + (currentPage-1) * 27;
            if(icons.size() > index) {
                inventory.setIcon(i+9, icons.get(index));
            }else {
                break;
            }
        }
        // Next page
        if(icons.size() > (currentPage) * 27) {
            ItemStack item = new ItemStack(Material.PAPER);
            ItemMeta meta = item.getItemMeta();
            List<String> lore;
            lore = plugin.getConfig().getStringList("lang.gui.nextlore");
            meta.setLore(TextUtils.colorFormatList(lore));
            String text = plugin.getConfig().getString("lang.gui.next");
            meta.setDisplayName(HexUtil.colorify(text));
            item.setItemMeta(meta);
            Icon icon = new Icon(item);
            icon.addClickAction((player1 -> {
                currentPage++;
                construct();
            }));
            inventory.setIcon(52, icon);
        }
        if(currentPage > 1){
            ItemStack item = new ItemStack(Material.PAPER);
            ItemMeta meta = item.getItemMeta();
            List<String> lore;
            lore = plugin.getConfig().getStringList("lang.gui.prevlore");
            meta.setLore(TextUtils.colorFormatList(lore));
            String text = plugin.getConfig().getString("lang.gui.prev");
            meta.setDisplayName(HexUtil.colorify(text));
            item.setItemMeta(meta);
            Icon icon = new Icon(item);
            icon.addClickAction((player1 -> {
                currentPage--;
                construct();
            }));
            inventory.setIcon(47, icon);
        }

        // CURRENTLY SELECTED
        TagData data = playerHandler.getActivePlayerTag(player);
        if(!data.getName().equals("NONE")) {

            ItemStack item = new ItemStack(Material.BOOK);
            ItemMeta meta = item.getItemMeta();
            List<String> lore;
            lore = plugin.getConfig().getStringList("lang.gui.currentlore");
            lore = TextUtils.formatList(lore, "{DESC}:" + data.getDescription());

            meta.setLore(TextUtils.colorFormatList(lore));
            String text = plugin.getConfig().getString("lang.gui.current").replace("{TAG}", data.getTag());
            meta.setDisplayName(HexUtil.colorify(text));
            item.setItemMeta(meta);
            Icon icon = new Icon(item);
            icon.addClickAction((player1 -> {
                playerHandler.removePlayer(player);
                player.sendMessage(TextUtils.colorFormat(plugin.getConfig().getString("lang.removedtag")));
                plugin.save();
                construct();
            }));
            inventory.setIcon(49, icon);
        }

        inventory.setBackground(new ItemStack(Material.PINK_STAINED_GLASS_PANE));

        player.openInventory(inventory.getInventory());
    }

    public void load(){
        inventory.clean();

        // THE NULL TAG



        for(TagData tag : playerHandler.getTags()){
            if(player.hasPermission(tag.getPermission())) {
                ItemStack item = new ItemStack(Material.NAME_TAG);
                ItemMeta meta = item.getItemMeta();
                List<String> lore;
                lore = plugin.getConfig().getStringList("lang.iconlore");
                lore = TextUtils.formatList(lore, "{DESC}:" + tag.getDescription());

                meta.setLore(TextUtils.colorFormatList(lore));
                String text = plugin.getConfig().getString("lang.iconname").replace("{TAG}", tag.getTag());
                meta.setDisplayName(HexUtil.colorify(text));
                item.setItemMeta(meta);
                Icon icon = new Icon(item);
                icon.addClickAction((player) -> {
                    playerHandler.addPlayer(player, tag);
                    player.sendMessage(TextUtils.colorFormat(plugin.getConfig().getString("lang.settag")));
                    player.closeInventory();
                    plugin.save();
                });
                icons.add(icon);
            }
        }
        construct();
    }
}
