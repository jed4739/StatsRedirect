package org.sunik.statsredirect.handler;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.sunik.statsredirect.StatsRedirect;
import org.sunik.statsredirect.Util.HealthUtils;
import org.sunik.statsredirect.Util.JsonParseUtils;

import java.io.File;

public class InventoryHandler implements Listener {
    private final StatsRedirect plugin;

    private final Gson gson;

    public InventoryHandler(StatsRedirect statsRedirect, Gson gson) {
        this.plugin = statsRedirect;
        this.gson = gson;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Inventory clickedInventory = event.getClickedInventory();
        if (clickedInventory == null || !clickedInventory.equals(player.getOpenInventory().getTopInventory())) {
            return;
        }

        ItemStack clickedItem = event.getCurrentItem();

        if (clickedItem == null) {
            return; // 클릭된 아이템이 없는 경우 무시
        }

        ItemMeta itemMeta = clickedItem.getItemMeta();

        if (itemMeta == null) {
            return; // 아이템 메타 데이터가 없는 경우 무시
        }

        boolean str = itemMeta.getDisplayName().equals(ChatColor.RED + (ChatColor.BOLD + "힘")) && clickedItem.getType() == Material.RED_STAINED_GLASS_PANE;
        boolean con = itemMeta.getDisplayName().equals(ChatColor.GREEN + (ChatColor.BOLD + "체력")) && clickedItem.getType() == Material.GREEN_STAINED_GLASS_PANE;
        boolean dex = itemMeta.getDisplayName().equals(ChatColor.AQUA + (ChatColor.BOLD + "민첩")) && clickedItem.getType() == Material.LIGHT_BLUE_STAINED_GLASS_PANE;
        boolean luck = itemMeta.getDisplayName().equals(ChatColor.GOLD + (ChatColor.BOLD + "행운")) && clickedItem.getType() == Material.YELLOW_STAINED_GLASS_PANE;
        plugin.getLogger().info(str + "," + con + "," + dex + "," + luck);

        if (str) {
            event.setCurrentItem(null);
            addStr();
            player.performCommand("stats");
        } else if (con) {
            event.setCurrentItem(null);
            addCon(player);
            player.performCommand("stats");
        } else if (dex) {
            event.setCurrentItem(null);
            addDex();
            player.performCommand("stats");
        } else if (luck) {
            event.setCurrentItem(null);
            addLuck();
            player.performCommand("stats");
        } else if (itemMeta.getDisplayName().equalsIgnoreCase(" ") && clickedItem.getType() == Material.WHITE_STAINED_GLASS_PANE) {
            event.setCurrentItem(null);
            player.performCommand("stats");
        }
    }

    private void addStr() {

    }

    private void addCon(Player p) {
        // 추가할 체력
        double addedMaxHealth = 10.0;
        HealthUtils.addMaxHealth(p, addedMaxHealth);

        File playerFile = new File(plugin.getDataFolder() + "/userData", p.getName() + ".json");
        if (!playerFile.exists()) {
            return;
        }
        JsonObject playerData = JsonParseUtils.loadPlayerData(playerFile, gson);
        int oldCon = playerData.get("con").getAsInt();
        playerData.addProperty("con", ++oldCon);
        JsonParseUtils.modifyPlayerData(playerFile, gson, playerData);

        int healthToAdd = 1;
        p.sendMessage(ChatColor.GREEN + "체력 스탯 " + healthToAdd + "을 추가하였습니다.");
        plugin.getLogger().info(p.getName() + "님이 체력 스텟을 올렸습니다.");
    }

    private void addDex() {

    }

    private void addLuck() {

    }
}
