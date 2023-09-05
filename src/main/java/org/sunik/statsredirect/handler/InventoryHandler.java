package org.sunik.statsredirect.handler;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
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
import org.sunik.statsredirect.Util.PlayerUtils;

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

        if (str) {
            event.setCurrentItem(null);
            addStr(player);
            player.performCommand("stats");
        } else if (con) {
            event.setCurrentItem(null);
            addCon(player);
            player.performCommand("stats");
        } else if (dex) {
            event.setCurrentItem(null);
            addDex(player);
            player.performCommand("stats");
        } else if (luck) {
            event.setCurrentItem(null);
            addLuck(player);
            player.performCommand("stats");
        } else if (itemMeta.getDisplayName().equalsIgnoreCase(" ") && clickedItem.getType() == Material.WHITE_STAINED_GLASS_PANE) {
            event.setCurrentItem(null);
            player.performCommand("stats");
        }
    }

    private void addStr(Player p) {
        // 추가할 체력
        double addedMaxHealth = 2.0;
        HealthUtils.addMaxHealth(p, addedMaxHealth);

        File playerFile = new File(plugin.getDataFolder() + "/userData", p.getName() + ".json");
        if (!playerFile.exists()) {
            return;
        }
        JsonObject playerData = JsonParseUtils.loadPlayerData(playerFile, gson);
        int oldStr = playerData.get("str").getAsInt();
        playerData.addProperty("str", ++oldStr);
        JsonParseUtils.modifyPlayerData(playerFile, gson, playerData);

        PlayerUtils.modifyPlayerAttribute(p, Attribute.GENERIC_ATTACK_DAMAGE, 1.0 + (1.0 * playerData.get("str").getAsInt()));
        PlayerUtils.modifyPlayerAttribute(p, Attribute.GENERIC_ARMOR, 0.25 * playerData.get("str").getAsInt());

        p.sendMessage(ChatColor.GREEN + "힘 스탯 " + 1 + "을 추가하였습니다.");
        plugin.getLogger().info(p.getName() + "님이 힘 스텟을 올렸습니다.");
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

        PlayerUtils.modifyPlayerAttribute(p, Attribute.GENERIC_KNOCKBACK_RESISTANCE, 0.25 * playerData.get("con").getAsInt());

        p.sendMessage(ChatColor.GREEN + "체력 스탯 " + 1 + "을 추가하였습니다.");
        plugin.getLogger().info(p.getName() + "님이 체력 스텟을 올렸습니다.");
    }

    private void addDex(Player p) {
        File playerFile = new File(plugin.getDataFolder() + "/userData", p.getName() + ".json");
        if (!playerFile.exists()) {
            return;
        }
        JsonObject playerData = JsonParseUtils.loadPlayerData(playerFile, gson);
        int oldDex = playerData.get("dex").getAsInt();
        playerData.addProperty("dex", ++oldDex);
        JsonParseUtils.modifyPlayerData(playerFile, gson, playerData);

        PlayerUtils.modifyPlayerAttribute(p, Attribute.GENERIC_ATTACK_SPEED, 4.0 + (0.03 * playerData.get("dex").getAsInt()));
        PlayerUtils.modifyPlayerAttribute(p, Attribute.GENERIC_MOVEMENT_SPEED, 0.10000000149011612 + (0.00003 * playerData.get("dex").getAsInt()));

        p.sendMessage(ChatColor.GREEN + "민첩 스탯 " + 1 + "을 추가하였습니다.");
        plugin.getLogger().info(p.getName() + "님이 민첩 스텟을 올렸습니다.");
    }

    private void addLuck(Player p) {
        File playerFile = new File(plugin.getDataFolder() + "/userData", p.getName() + ".json");
        if (!playerFile.exists()) {
            return;
        }
        JsonObject playerData = JsonParseUtils.loadPlayerData(playerFile, gson);
        int oldLuck = playerData.get("luck").getAsInt();
        playerData.addProperty("luck", ++oldLuck);
        JsonParseUtils.modifyPlayerData(playerFile, gson, playerData);

        p.sendMessage(ChatColor.GREEN + "행운 스탯 " + 1 + "을 추가하였습니다.");
        plugin.getLogger().info(p.getName() + "님이 행운 스텟을 올렸습니다.");
    }
}
