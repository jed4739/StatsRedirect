package org.sunik.statsredirect.Util;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;

public class HealthUtils {
    /**
     * 해당 플레이어의 최대체력 수치를 설정
     * @param player - 해당 플레이어 객체
     * @param maxHealth - 변경될 최대체력 수치
     */
    public static void setMaxHealth(Player player, double maxHealth) {
        if (maxHealth > 0) {
            player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(maxHealth);
            player.setHealth(maxHealth);
        }
    }

    /**
     * 해당 플레이어의 최대체력을 double 로 반환.
     * @param player - 해당 플레이어 객체
     * @return GENERIC_MAX_HEALTH - 해당 플레이어의 최대 체력
     */
    public static double getMaxHealth(Player player) {
        return player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();
    }

    /**
     * 현재 유저의 최대 체력 수치를 추가할 수치만큼 늘림.
     * @param player - 해당 플레이어 객체
     * @param amount - 추가할 체력 수치
     */
    public static void addMaxHealth(Player player, double amount) {
        double currentMaxHealth = getMaxHealth(player);
        setMaxHealth(player, currentMaxHealth + amount);
    }
}
