package org.sunik.statsredirect;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.sunik.statsredirect.commands.MenuCommands;
import org.sunik.statsredirect.handler.InventoryHandler;

public final class StatsRedirect extends JavaPlugin {

    @Override
    public void onEnable() {
        // CommandExecutor 등록
        getCommand("스탯").setExecutor(new MenuCommands(this));

        // Listener 등록
        getServer().getPluginManager().registerEvents(new InventoryHandler(this), this);
    }

    // 스탯 가상 인벤토리 열기
    public void openStatInventory(Player player) {
        // 슬롯 사이즈
        int size = 27;

        Inventory inventory = Bukkit.createInventory(player, size, "스탯");

        // 체력 아이템 생성 및 설정
        ItemStack healthItem = new ItemStack(Material.GREEN_STAINED_GLASS_PANE);
        ItemMeta healthItemMeta = healthItem.getItemMeta();
        assert healthItemMeta != null;
        healthItemMeta.setDisplayName(ChatColor.GREEN + "체력");
        healthItem.setItemMeta(healthItemMeta);

        // 빈 유리 판 아이템 생성
        ItemStack emptyGlassPane = new ItemStack(Material.WHITE_STAINED_GLASS_PANE);
        ItemMeta emptyGlassPaneMeta = emptyGlassPane.getItemMeta();
        assert emptyGlassPaneMeta != null;
        emptyGlassPaneMeta.setDisplayName(" "); // 이름을 공백으로 설정
        emptyGlassPane.setItemMeta(emptyGlassPaneMeta);

        // 가상 인벤토리 구성
        for (int i = 0; i < size; i++) {
            if (i == 12) {
                // 2번째줄 4번째 칸에 체력 아이템 추가
                inventory.setItem(i, healthItem);
            } else {
                // 나머지 칸에 빈 유리 판 아이템 추가
                inventory.setItem(i, emptyGlassPane);
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
//            p.sendMessage(admin + ChatColor.YELLOW + "/");
        }

        // Default Command List
        p.sendMessage(ChatColor.GOLD + "/스텟 ");
    }
}
