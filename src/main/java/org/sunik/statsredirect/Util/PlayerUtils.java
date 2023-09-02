package org.sunik.statsredirect.Util;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PlayerUtils {
    public static boolean isPlayer(Player player) {
        UUID uuid = player.getUniqueId();
        // Check if the UUID is from a player
        // You can use a more comprehensive way to check if the UUID belongs to a player
        return Bukkit.getPlayer(uuid) != null;
    }
}
