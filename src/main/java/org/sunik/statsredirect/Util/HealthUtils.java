package org.sunik.statsredirect.Util;

import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;

public class HealthUtils {
    public static void setMaxHealth(Player player, double maxHealth) {
        if (maxHealth > 0) {
            player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(maxHealth);
            player.setHealth(maxHealth);
        }
    }
    public static double getMaxHealth(Player player) {
        return player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();
    }

    public static void addMaxHealth(Player player, double amount) {
        double currentMaxHealth = getMaxHealth(player);
        setMaxHealth(player, currentMaxHealth + amount);
    }
}
