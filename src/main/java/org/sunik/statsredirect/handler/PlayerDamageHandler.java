package org.sunik.statsredirect.handler;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.bukkit.ChatColor;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.sunik.statsredirect.Util.HealthUtils;
import org.sunik.statsredirect.Util.JsonParseUtils;

import java.io.File;
import java.util.Random;

public class PlayerDamageHandler implements Listener {
    private final JavaPlugin plugin;
    private final Gson gson;

    public PlayerDamageHandler(JavaPlugin plugin, Gson gson) {
        this.plugin = plugin;
        this.gson = gson;
    }

    @EventHandler
    public void onEntityDamageByEntityHp(EntityDamageByEntityEvent event) {
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
            double maxHealth = HealthUtils.getMaxHealth(player);
            if (resultHealth > maxHealth) {
                resultHealth = maxHealth;
            }

            player.setHealth(resultHealth);
        }
    }

    @EventHandler
    public void onEntityDamageByEntityCritical(EntityDamageByEntityEvent event) {
        // 이벤트에 관련된 엔티티를 가져옵니다.
        Entity damager = event.getDamager(); // 데미지를 주는 엔티티
        Entity victim = event.getEntity();    // 데미지를 받는 엔티티

        // 활로 데미지를 주는 것을 판단 후,
        // 데미지를 주는 엔티티와 받는 엔티티가 모두 플레이어인지 확인합니다.
        if (damager instanceof Arrow) {
            Arrow arrow = (Arrow) damager;
            if (arrow.getShooter() instanceof Player) {
                Player shooter = (Player) arrow.getShooter();
                File playerFile = new File(plugin.getDataFolder() + "/userData", shooter.getName() + ".json");
                JsonObject playerData = JsonParseUtils.loadPlayerData(playerFile, new Gson());
                // 확률 계산이 0~1 이기에 0.001 = 0.1% 이다.
                double criticalPercentage = 0.01 * playerData.get("luck").getAsInt();
                // 이벤트로부터 기본 데미지 값을 가져옵니다.
                double baseDamage = event.getDamage();
                // 기본데미지 입력
                double resultCriticalDamage = baseDamage;
                // 크리티컬 데미지를 적용할지 여부를 확인합니다.
                if (shouldDealCriticalDamage(criticalPercentage)) {
                    double criticalDamage = (((baseDamage * 0.15) * (playerData.get("dex").getAsInt())) +
                                            ((baseDamage * 0.5) * playerData.get("wis").getAsInt()));
                    // 크리티컬 데미지를 계산합니다.
                    resultCriticalDamage = baseDamage + criticalDamage;
                    // 메시지
                    shooter.sendMessage(ChatColor.YELLOW + "크리티컬!");
                    // 이벤트의 데미지 값을 크리티컬 데미지 값으로 설정합니다.
                    resultCriticalDamage = headShot(resultCriticalDamage, shooter);
                    event.setDamage(resultCriticalDamage);
                    return;
                }
                resultCriticalDamage = headShot(resultCriticalDamage, shooter);
                event.setDamage(resultCriticalDamage);
                // shooter가 활을 가진 플레이어인지 확인하고 처리할 작업을 수행
            }
        } else if (damager instanceof Player) {
            // 엔티티를 각각 플레이어 객체로 캐스팅합니다.
            Player attacker = (Player) damager;
            assert victim instanceof Player;

            File playerFile = new File(plugin.getDataFolder() + "/userData", attacker.getName() + ".json");
            JsonObject playerData = JsonParseUtils.loadPlayerData(playerFile, new Gson());
            // 확률 계산이 0~1 이기에 0.001 = 0.1% 이다.
            double criticalPercentage = 0.003 * playerData.get("luck").getAsInt();
            // 크리티컬 데미지를 적용할지 여부를 확인합니다.
            if (shouldDealCriticalDamage(criticalPercentage)) {
                // 이벤트로부터 기본 데미지 값을 가져옵니다.
                double baseDamage = event.getDamage();
                double criticalDamage = (baseDamage * 0.05) * playerData.get("dex").getAsInt();
                // 크리티컬 데미지를 계산합니다.
                double resultCriticalDamage = baseDamage + criticalDamage;
                // 메시지
                attacker.sendMessage(ChatColor.YELLOW + "크리티컬!");
                // 이벤트의 데미지 값을 크리티컬 데미지 값으로 설정합니다.
                event.setDamage(resultCriticalDamage);
            }
        }
    }

    /*
     * 헤드샷 계산을 분리하여 동일 코드에서 불러오기만 하면 되게 작성했습니다.
     * @param damage - 기본 데미지
     * @param shooter - 피해를 준 플레이어
     * @return resultDamage
     */
    private Double headShot(double damage, Player shooter) {
        if (shouldDealCriticalDamage(0.2)) {
            plugin.getLogger().info("헤드샷!");
            shooter.sendMessage(ChatColor.RED + "헤드샷!");
            return damage * 2;
        } else {
            return damage;
        }
    }

    // 크리티컬 데미지를 적용해야 할지 여부를 결정하는 메서드입니다.
    private boolean shouldDealCriticalDamage(double chance) {
        // 0부터 99 사이의 난수를 생성합니다.
        // 생성된 난수가 확률보다 작으면 true를 반환합니다.
        return Math.random() < chance;
    }

}
