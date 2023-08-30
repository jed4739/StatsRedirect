package org.sunik.statsredirect.handler;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.sunik.statsredirect.StatsRedirect;
import org.sunik.statsredirect.Util.Stats;

public class InventoryHandler implements Listener {
    private final StatsRedirect plugin;

    public InventoryHandler(StatsRedirect statsRedirect) {
        this.plugin = statsRedirect;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Inventory clickedInventory = event.getClickedInventory();
        if (clickedInventory == null || !clickedInventory.equals(player.getOpenInventory().getTopInventory())) {
            return;
        }

        ItemStack clickedItem = event.getCurrentItem();
        plugin.getLogger().info("3");

        if (clickedItem == null) {
            return; // 클릭된 아이템이 없는 경우 무시
        }

        ItemMeta itemMeta = clickedItem.getItemMeta();

        if (itemMeta == null) {
            return; // 아이템 메타 데이터가 없는 경우 무시
        }

        boolean str = itemMeta.getDisplayName().equals(ChatColor.RED + (ChatColor.BOLD + "힘")) && clickedItem.getType() == Material.RED_STAINED_GLASS_PANE;
        boolean con = itemMeta.getDisplayName().equals(ChatColor.GREEN + (ChatColor.BOLD + "체력")) && clickedItem.getType() == Material.GREEN_STAINED_GLASS_PANE;
        boolean dex = itemMeta.getDisplayName().equals(ChatColor.AQUA + (ChatColor.BOLD + "민첩")) && clickedItem.getType() == Material.LIGHT_BLUE_STAINED_GLASS_PANE;
        boolean luck = itemMeta.getDisplayName().equals(ChatColor.GOLD + (ChatColor.BOLD + "행운")) && clickedItem.getType() == Material.YELLOW_STAINED_GLASS_PANE;
        plugin.getLogger().info(str + "," + con + "," + dex + "," + luck);
        if (str && con && dex && luck) {
            return;
        }

        if (str) {
            event.setCurrentItem(null);
            addStr();
            player.performCommand("stats");
        } else if (con) {
            event.setCurrentItem(null);
            addCon(player);
            player.performCommand("stats");
        } else if (dex) {
            event.setCurrentItem(null);
            addDex();
            player.performCommand("stats");
        } else if (luck) {
            event.setCurrentItem(null);
            addLuck();
            player.performCommand("stats");
        }
    }

    private void addStr() {

    }

    private void addCon(Player p) {
        Stats.conEffect(p);
        plugin.getLogger().info(p.getName() + "님이 체력 스텟을 올렸습니다.");
    }

    private void addDex() {

    }

    private void addLuck() {

    }
}
