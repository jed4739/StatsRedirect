package org.sunik.statsredirect.handler;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.sunik.statsredirect.Util.HealthUtils;
import org.sunik.statsredirect.Util.JsonParseUtils;

import java.io.File;

public class PlayerDeathHandler implements Listener {
    private final Gson gson;
    private final JavaPlugin plugin;

    public PlayerDeathHandler(JavaPlugin plugin, Gson gson) {
        this.plugin = plugin;
        this.gson = gson;
    }

    @EventHandler
    public void onPlayerResurrection(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        String playerNm = player.getName();
        File playerFile = new File(plugin.getDataFolder() + "/userData", playerNm + ".json");
        if (playerFile.exists()) {
            JsonObject playerData = JsonParseUtils.loadPlayerData(playerFile, gson);
            int strength = playerData.get("str").getAsInt();
            int constitution = playerData.get("con").getAsInt();
            int dexterity = playerData.get("dex").getAsInt();
            int luck = playerData.get("luck").getAsInt();
            int level = playerData.get("level").getAsInt();
            float xp = playerData.get("xp").getAsFloat();
            int point = playerData.get("point").getAsInt();

            double resultHp = 100.0 + (10.0 * constitution);
            HealthUtils.setMaxHealth(player, resultHp);

            player.setExp(xp);
            player.setLevel(level);
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        plugin.getLogger().info(player.getName() + "님이 사망하였습니다.");
        // 금액 설정
        int amount = 1000;
        // 플레이어 위치에 아이템 생성 및 떨어뜨리기
        ItemStack droppedItem = new ItemStack(Material.PAPER, 1);
        ItemMeta itemMeta = droppedItem.getItemMeta();
        assert itemMeta != null;
        itemMeta.setDisplayName(ChatColor.GREEN + String.valueOf(amount) + ChatColor.YELLOW + "$");
        droppedItem.setItemMeta(itemMeta);
        player.getWorld().dropItemNaturally(player.getLocation(), droppedItem);

        File playerFile = new File(plugin.getDataFolder() + "/userData", player.getName() + ".json");
        if (playerFile.exists()) {
            JsonObject playerData = JsonParseUtils.loadPlayerData(playerFile, gson);
            float oldXp = playerData.get("xp").getAsFloat();
            if (oldXp != 0) {
                float xp = oldXp * 0.7f;
                playerData.addProperty("xp", xp);
                JsonParseUtils.modifyPlayerData(playerFile, gson, playerData);
            }
        }
    }
}
