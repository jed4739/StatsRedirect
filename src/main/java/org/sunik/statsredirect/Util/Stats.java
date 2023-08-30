package org.sunik.statsredirect.Util;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Stats {
    public static void conEffect(Player p) {
        int healthToAdd = 1;
        p.sendMessage(ChatColor.GREEN + "체력 스탯 " + healthToAdd + "을 추가하였습니다.");

        // 추가할 체력
        double addedMaxHealth = 1.0;
        HealthUtils.addMaxHealth(p, addedMaxHealth);
    }
}
