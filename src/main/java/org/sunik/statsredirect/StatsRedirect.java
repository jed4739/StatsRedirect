package org.sunik.statsredirect;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;
import org.sunik.statsredirect.Util.InventoryStatsItems;
import org.sunik.statsredirect.Util.JsonParseUtils;
import org.sunik.statsredirect.commands.MenuCommands;
import org.sunik.statsredirect.handler.*;

import java.io.File;

public final class StatsRedirect extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("StatsRedirect이 활성화 되었습니다.");
        // 유저 정보 폴더 생성
        File userDataFolder = new File(getDataFolder(), "userData");
        if (!userDataFolder.exists()) {
            userDataFolder.mkdirs();
        }

        // 플러그인 설정 파일 생성
        File configFile = new File(getDataFolder(), "config" + ".yml");
        if (!configFile.exists()) {
            userDataFolder.mkdirs();
        }

        getServer().getScheduler().runTaskTimer(this, () -> {
            for (Player player : getServer().getOnlinePlayers()) {
                float xp = player.getExp();
                File playerFile = new File(getDataFolder() + "/userData", player.getName() + ".json");
                if (playerFile.exists()) {
                    Gson gson = new Gson();
                    JsonObject playerData = JsonParseUtils.loadPlayerData(playerFile, gson);
                    if (playerData.get("xp").getAsFloat() != xp) {
                        playerData.addProperty("xp", xp);
                        JsonParseUtils.modifyPlayerData(playerFile, gson, playerData);
                    }
                }
            }
        }, 0L, 20L * 10);

        // CommandExecutor 등록
        getCommand("stats").setExecutor(new MenuCommands(this));
        getCommand("statslist").setExecutor(new MenuCommands(this));
        getCommand("heal").setExecutor(new MenuCommands(this));

        // Listener 등록
        getServer().getPluginManager().registerEvents(new InventoryHandler(this, new Gson()), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinHandler(this, new Gson()), this);
        getServer().getPluginManager().registerEvents(new PlayerExpHandler(this, new Gson()), this);
        getServer().getPluginManager().registerEvents(new PlayerDeathHandler(this, new Gson()), this);
        getServer().getPluginManager().registerEvents(new PlayerDamageHandler(this, new Gson()), this);
    }
}
