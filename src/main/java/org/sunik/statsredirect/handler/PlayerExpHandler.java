package org.sunik.statsredirect.handler;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerLevelChangeEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.sunik.statsredirect.Util.JsonParseUtils;

import java.io.File;

public class PlayerExpHandler implements Listener {
    private final JavaPlugin plugin;

    private final Gson gson;

    public PlayerExpHandler(JavaPlugin plugin, Gson gson) {
        this.plugin = plugin;
        this.gson = gson;
    }
    @EventHandler
    public void onPlayerExpChange(PlayerExpChangeEvent event) {
        Player player = event.getPlayer();
        int level = player.getLevel();
        float xp = player.getExp();
        savePlayerExp(player, level, xp);
    }

    @EventHandler
    public void onPlayerLevelChange(PlayerLevelChangeEvent event) {
        Player player = event.getPlayer();
        int level = event.getNewLevel();
        float xp = player.getExp();
        savePlayerExp(player, level, xp);
    }

    /*
     * 유저 데이터를 읽어와서 level과 xp 프로퍼티만 수정 후 저장.
     */
    private void savePlayerExp(Player player, int level, float xp) {
        File playerFile = new File(plugin.getDataFolder() + "/userData", player.getName() + ".json");
        if (playerFile.exists()) {
            JsonObject playerData = JsonParseUtils.loadPlayerData(playerFile, gson);
            // 데이터 값이 더 낮아지지 않도록 수정
            int oldLevel = playerData.get("level").getAsInt();
            float oldXp = playerData.get("xp").getAsFloat();
            if (level < oldLevel) {
                return;
            } else if (level == oldLevel && xp < oldXp) {
                return;
            }
            playerData.addProperty("level", level);
            playerData.addProperty("xp", xp);
            JsonParseUtils.modifyPlayerData(playerFile, gson, playerData);
        }
    }
}
