package org.sunik.statsredirect.handler;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.sunik.statsredirect.Util.JsonParseUtils;
import org.sunik.statsredirect.Util.PlayerUtils;

import java.io.File;


public class PlayerJoinHandler implements Listener {
    private final JavaPlugin plugin;
    private final Gson gson;

    public PlayerJoinHandler(JavaPlugin plugin, Gson gson) {
        this.plugin = plugin;
        this.gson = gson;
    }

    @EventHandler
    public void onPlayJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String playerNm = player.getName();

        // 플레이어 데이터 JSON 파일 생성 또는 로드
        File playerFile = new File(plugin.getDataFolder() + "/userData", playerNm + ".json");
        if (!playerFile.exists()) {
            // JSON 파일이 없을 경우 새로 생성
            JsonObject defaultData = new JsonObject();
            defaultData.addProperty("str", 0);
            defaultData.addProperty("con", 0);
            defaultData.addProperty("dex", 0);
            defaultData.addProperty("luck", 0);
            defaultData.addProperty("xp", 0);
            defaultData.addProperty("level", 0);
            defaultData.addProperty("point", 0);
            JsonParseUtils.savePlayerData(playerFile, gson, defaultData);
        } else {
            // JSON 파일이 이미 있을 경우 데이터 로드
            JsonObject playerData = JsonParseUtils.loadPlayerData(playerFile, gson);
            // 데이터 활용하기
            int strength = playerData.get("str").getAsInt();
            int constitution = playerData.get("con").getAsInt();
            int dexterity = playerData.get("dex").getAsInt();
            int luck = playerData.get("luck").getAsInt();
            int level = playerData.get("level").getAsInt();
            float xp = playerData.get("xp").getAsFloat();
            int point = playerData.get("point").getAsInt();
            
            // 스텟 적용
            player.setExp(xp);
            player.setLevel(level);
            // con
            PlayerUtils.modifyPlayerAttribute(player, Attribute.GENERIC_KNOCKBACK_RESISTANCE, 0.25 * constitution);
        }
    }
}
