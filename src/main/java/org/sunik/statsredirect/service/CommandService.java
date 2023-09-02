package org.sunik.statsredirect.service;

import com.google.gson.JsonObject;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.sunik.statsredirect.Util.InventoryStatsItems;

public class CommandService {
    // 스탯 가상 인벤토리 열기
    public static void openStatInventory(Player player, JsonObject playerData) {
        // 슬롯 사이즈
        int size = 27;
        Inventory inventory = Bukkit.createInventory(player, size, "스탯");
        InventoryStatsItems items = new InventoryStatsItems();
        // 가상 인벤토리 구성
        for (int i = 0; i < size; i++) {
            switch (i) {
                case 10:
                    // 2번째줄 2번째 칸에 체력 아이템 추가
                    inventory.setItem(i, items.createStrengthItem(playerData.get("str").getAsInt()));
                    break;
                case 12:
                    // 2번째줄 4번째 칸에 체력 아이템 추가
                    inventory.setItem(i, items.createConItem(playerData.get("con").getAsInt()));
                    break;
                case 14:
                    // 2번째줄 6번째 칸에 체력 아이템 추가
                    inventory.setItem(i, items.createDexItem(playerData.get("dex").getAsInt()));
                    break;
                case 16:
                    // 2번째줄 8번째 칸에 체력 아이템 추가
                    inventory.setItem(i, items.createLuckItem(playerData.get("luck").getAsInt()));
                    break;
                default:
                    inventory.setItem(i, items.createEmptyGlassItem());
            }
        }
        // 가상 인벤토리 열기
        player.openInventory(inventory);
    }


    public static void infoMessage(Player p) {
        // Header
        p.sendMessage(ChatColor.BOLD + "" + ChatColor.DARK_AQUA + "============ " + ChatColor.GOLD +
                "StatsRedirect Command"
                + ChatColor.DARK_AQUA + " ============");

        // OP Command List
        if (p.isOp()) {
            // 관리자용 접두사 인스턴스
            String admin = ChatColor.WHITE + "[" + ChatColor.RED + "관리자 용도" + ChatColor.WHITE + "]";
            p.sendMessage(admin + ChatColor.YELLOW + "/heal");
        }

        // Default Command List
        p.sendMessage(ChatColor.GOLD + "/stats");
        p.sendMessage(ChatColor.GOLD + "/statslist");
    }
}
