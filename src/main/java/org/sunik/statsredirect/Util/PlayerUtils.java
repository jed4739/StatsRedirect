package org.sunik.statsredirect.Util;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PlayerUtils {
    public static boolean isPlayer(Player player) {
        UUID uuid = player.getUniqueId();
        // Check if the UUID is from a player
        // You can use a more comprehensive way to check if the UUID belongs to a player
        return Bukkit.getPlayer(uuid) != null;
    }

    public static double findPlayerAttribute(Player player, Attribute attribute) {
        return player.getAttribute(attribute).getBaseValue();
    }

    /**
     * 플레이어 속성값 수정
     * @param player - 해당 유저 객체
     * @param attribute - 변경할 attribute
     * @param value - 변경할 값
     */
    public static void modifyPlayerAttribute(Player player, Attribute attribute, double value) {
        player.getAttribute(attribute).setBaseValue(value);
        // 지정한 플레이어 정보 저장 (위치, 체력 등...)
        player.saveData();
    }
}
