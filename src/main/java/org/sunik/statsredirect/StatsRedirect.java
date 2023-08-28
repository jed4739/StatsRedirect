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

import java.util.ArrayList;

public final class StatsRedirect extends JavaPlugin {

    @Override
    public void onEnable() {
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
        ItemStack healthItem = createHealthItem();
        ItemStack emptyGlassPane = createEmptyGlassItem();

        // 가상 인벤토리 구성
        for (int i = 0; i < size; i++) {
            switch (i) {
                case 10:
                    break;
                case 12:
                    // 2번째줄 4번째 칸에 체력 아이템 추가
                    inventory.setItem(i, healthItem);
                    break;
                case 14:
                    break;
                case 16:
                    break;
                default:
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

    private ItemStack createHealthItem() {
        // 체력 아이템 생성 및 설정
        ItemStack healthItem = new ItemStack(Material.GREEN_STAINED_GLASS_PANE);
        ItemMeta healthItemMeta = healthItem.getItemMeta();
        assert healthItemMeta != null;
        healthItemMeta.setDisplayName(ChatColor.GREEN + "체력");
        ArrayList<String> lore = new ArrayList<>();
        lore.add((ChatColor.RED + "+") + (ChatColor.YELLOW + "체력:") + " " + (ChatColor.WHITE + "10"));
        lore.add((ChatColor.RED + "+") + (ChatColor.YELLOW + "체력 리젠:") + " " + (ChatColor.WHITE + "0.25"));
        lore.add((ChatColor.RED + "+") + (ChatColor.YELLOW + "체력 흡수:") + " " + (ChatColor.WHITE + "0.1"));
        lore.add((ChatColor.RED + "+") + (ChatColor.YELLOW + "넉백 저항:") + " " + (ChatColor.WHITE + "0.5"));
        lore.add(ChatColor.LIGHT_PURPLE + "현재 스텟: ");
        healthItemMeta.setLore(lore);
        healthItem.setItemMeta(healthItemMeta);
        return healthItem;
    }

    private ItemStack createEmptyGlassItem() {
        // 빈 유리 판 아이템 생성
        ItemStack emptyGlassPane = new ItemStack(Material.WHITE_STAINED_GLASS_PANE);
        ItemMeta emptyGlassPaneMeta = emptyGlassPane.getItemMeta();
        assert emptyGlassPaneMeta != null;
        emptyGlassPaneMeta.setDisplayName(" "); // 이름을 공백으로 설정
        emptyGlassPane.setItemMeta(emptyGlassPaneMeta);
        return emptyGlassPane;
    }
}
