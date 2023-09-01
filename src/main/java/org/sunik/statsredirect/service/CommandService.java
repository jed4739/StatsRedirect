package org.sunik.statsredirect.service;

import com.google.gson.Gson;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;
import org.sunik.statsredirect.Util.InventoryStatsItems;

import java.io.File;

public class CommandService {
    public static void statsReset(Player p, String[] args, JavaPlugin plugin) {
        if (args[1] == null) {
            p.sendMessage(ChatColor.RED + "초기화 대상을 지정해주세요.");
            p.sendMessage(ChatColor.RED + "/stats reset <player> <statsType>(all, str, con, dex, luck, level, point)");
        } else if (args[2] == null) {
            p.sendMessage(ChatColor.RED + "초기화 스텟을 지정해주세요.");
            p.sendMessage(ChatColor.RED + "/stats reset <player> <statsType>(all, str, con, dex, luck, level, point)");
        }
        Gson gson = new Gson();
        File playerFile = new File(plugin.getDataFolder() + "/userData", args[1] + ".json");
//        playerFile.exz
        // 추가기능
    }

    // 스탯 가상 인벤토리 열기
    public static void openStatInventory(Player player) {
        // 슬롯 사이즈
        int size = 27;

        Inventory inventory = Bukkit.createInventory(player, size, "스탯");
        InventoryStatsItems items = new InventoryStatsItems();

        // 가상 인벤토리 구성
        for (int i = 0; i < size; i++) {
            switch (i) {
                case 10:
                    // 2번째줄 2번째 칸에 체력 아이템 추가
                    inventory.setItem(i, items.createStrengthItem());
                    break;
                case 12:
                    // 2번째줄 4번째 칸에 체력 아이템 추가
                    inventory.setItem(i, items.createHealthItem());
                    break;
                case 14:
                    // 2번째줄 6번째 칸에 체력 아이템 추가
                    inventory.setItem(i, items.createDexItem());
                    break;
                case 16:
                    // 2번째줄 8번째 칸에 체력 아이템 추가
                    inventory.setItem(i, items.createLuckItem());
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
