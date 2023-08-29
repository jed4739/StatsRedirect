package org.sunik.statsredirect;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.sunik.statsredirect.Util.InventoryStatsItems;
import org.sunik.statsredirect.commands.MenuCommands;
import org.sunik.statsredirect.handler.InventoryHandler;

import java.io.File;
import java.util.ArrayList;

public final class StatsRedirect extends JavaPlugin {

    @Override
    public void onEnable() {
        // 유저 정보 폴더 생성
//        File userDataFolder = new File(getDataFolder(), "userData");
//        if (!userDataFolder.exists()) {
//            userDataFolder.mkdirs();
//        }

        // CommandExecutor 등록
        getCommand("stats").setExecutor(new MenuCommands(this));
        getCommand("statslist").setExecutor(new MenuCommands(this));
        getCommand("heal").setExecutor(new MenuCommands(this));

        // Listener 등록
        getServer().getPluginManager().registerEvents(new InventoryHandler(this), this);
    }

    // 스탯 가상 인벤토리 열기
    public void openStatInventory(Player player) {
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

    public void infoMessage(Player p) {
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
