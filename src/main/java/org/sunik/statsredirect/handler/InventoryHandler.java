package org.sunik.statsredirect.handler;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.sunik.statsredirect.StatsRedirect;
import org.sunik.statsredirect.Util.HealthUtils;

public class InventoryHandler implements Listener {
    private final StatsRedirect plugin;

    public InventoryHandler(StatsRedirect statsRedirect) {
        this.plugin = statsRedirect;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        // 클릭된 인벤토리
        Inventory clickedInventory = event.getClickedInventory();
        int slot = event.getSlot(); // 클릭한 슬롯 번호

        // 클릭된 인벤토리가 없거나 인벤토리가 플레이어의 열린 상단 인벤토리와 다를 경우 무시
        if (clickedInventory == null || !clickedInventory.equals(player.getOpenInventory().getTopInventory())) {
            return;
        }

        // 클릭된 아이템
        ItemStack clickedItem = event.getCurrentItem();
        // 클릭된 아이템의 메타 데이터
        ItemMeta itemMeta = clickedItem.getItemMeta();
        // 클릭한 아이템 이름이 (힘, 체력, 민첩, 행운) 이고 아이템 종류가 (빨강, 초록, 하늘, 노랑)색 유리판 이 아닐 경우 무시
        boolean str = itemMeta.getDisplayName().equals(ChatColor.RED + (ChatColor.BOLD + "힘")) && clickedItem.getType() != Material.RED_STAINED_GLASS_PANE;
        boolean con = itemMeta.getDisplayName().equals(ChatColor.GREEN + (ChatColor.BOLD + "체력")) && clickedItem.getType() != Material.GREEN_STAINED_GLASS_PANE;
        boolean dex = itemMeta.getDisplayName().equals(ChatColor.AQUA + (ChatColor.BOLD + "민첩")) && clickedItem.getType() != Material.LIGHT_BLUE_STAINED_GLASS_PANE;
        boolean luck = itemMeta.getDisplayName().equals(ChatColor.GOLD + (ChatColor.BOLD + "행운")) && clickedItem.getType() != Material.YELLOW_STAINED_GLASS_PANE;
        if (str && con && dex && luck) {
            return;
        }

        // 아이템 원래 자리로 돌려놓기
        if (slot >= 0 && slot < player.getOpenInventory().getTopInventory().getSize()) {
            player.getOpenInventory().getTopInventory().setItem(slot, clickedItem);
        }

        // 손의 아이템 제거
        event.setCurrentItem(null);
        if (str) {
            addStr();
            player.performCommand("stats");
        } else if (con) {
            addCon(player);
            player.performCommand("stats");
        } else if (dex) {
            addDex();
            player.performCommand("stats");
        } else if (luck) {
            addLuck();
            player.performCommand("stats");
        }
    }

    private void addStr() {

    }

    private void addCon(Player p) {
        int healthToAdd = 1;
        p.sendMessage(ChatColor.GREEN + "체력 스탯 " + healthToAdd + "을 추가하였습니다.");
        plugin.getLogger().info(p.getName() + "님이 체력 스텟을 올렸습니다.");

        // 추가할 체력
        double addedMaxHealth = 1.0;
        HealthUtils.addMaxHealth(p, addedMaxHealth);
    }

    private void addDex() {

    }

    private void addLuck() {

    }
}
