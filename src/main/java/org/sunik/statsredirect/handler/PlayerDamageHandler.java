package org.sunik.statsredirect.handler;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.sunik.statsredirect.Util.HealthUtils;
import org.sunik.statsredirect.Util.JsonParseUtils;

import java.io.File;

public class PlayerDamageHandler implements Listener {
    private final JavaPlugin plugin;
    private final Gson gson;

    public PlayerDamageHandler(JavaPlugin plugin, Gson gson) {
        this.plugin = plugin;
        this.gson = gson;
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        // 데미지를 가한 자가 플레이어일 경우
        if (event.getDamager() instanceof Player) {
            Player player = (Player) event.getDamager();
            double damage = event.getDamage();

            // 플레이어 데이터 JSON 파일 생성 또는 로드
            File playerFile = new File(plugin.getDataFolder() + "/userData", player.getName() + ".json");
            JsonObject playerData = JsonParseUtils.loadPlayerData(playerFile, gson);
            // 현재 체력
            double currentHealth = player.getHealth();
            double resultHealth = currentHealth + (damage * (0.01 * playerData.get("con").getAsInt()));
            plugin.getLogger().info(damage * (0.01 * playerData.get("con").getAsInt()) + " 회복수치");
            double maxHealth = HealthUtils.getMaxHealth(player);
            if (resultHealth > maxHealth) {
                resultHealth = maxHealth;
            }

            player.setHealth(resultHealth);
        }
    }
}
