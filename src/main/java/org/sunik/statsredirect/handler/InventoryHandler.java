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

        // 클릭된 인벤토리가 없으면 무시
        if (clickedInventory == null) {
            return;
        }

        // 클릭된 인벤토리가 플레이어의 열린 상단 인벤토리와 다를 경우 무시
        if (!clickedInventory.equals(player.getOpenInventory().getTopInventory())) {
            return;
        }

        ItemStack clickedItem = event.getCurrentItem(); // 클릭된 아이템
        // 클릭된 아이템이 없거나 초록색 유리 판이 아닌 경우 무시
        if (clickedItem == null || clickedItem.getType() != Material.GREEN_STAINED_GLASS_PANE) {
            return;
        }

        ItemMeta itemMeta = clickedItem.getItemMeta(); // 클릭된 아이템의 메타 데이터
        // 클릭된 아이템의 메타 데이터가 없거나 이름이 "체력"이 아닌 경우 무시
        if (itemMeta == null || !itemMeta.getDisplayName().equals(ChatColor.GREEN + "체력")) {
            return;
        }

        // 아이템 원래 자리로 돌려놓기
        if (slot >= 0 && slot < player.getOpenInventory().getTopInventory().getSize()) {
            player.getOpenInventory().getTopInventory().setItem(slot, clickedItem);
        }

        // 손의 아이템 제거
        event.setCurrentItem(null);
        addHealth(player);

        player.performCommand("stats");
    }

    private void addHealth(Player p) {
        int healthToAdd = 1;
        p.sendMessage(ChatColor.GREEN + "체력 스탯 " + healthToAdd + "을 추가하였습니다.");
        plugin.getLogger().info((ChatColor.RED + p.getName()) + "님이 체력 스텟을 올렸습니다.");

        // 추가할 체력
        double addedMaxHealth = 1.0;
        HealthUtils.addMaxHealth(p, addedMaxHealth);
    }
}
