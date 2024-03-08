package org.sunik.statsredirect.Util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;

public class InventoryStatsItems {
    public ItemStack createConItem(int con) {
        // 체력 아이템 생성 및 설정
        ItemStack healthItem = new ItemStack(Material.GREEN_STAINED_GLASS_PANE);
        ItemMeta healthItemMeta = healthItem.getItemMeta();
        assert healthItemMeta != null;
        healthItemMeta.setDisplayName(ChatColor.GREEN + (ChatColor.BOLD + "체력"));
        ArrayList<String> lore = new ArrayList<>();
        lore.add((ChatColor.RED + "+") + (ChatColor.YELLOW + "체력:") + " " + (ChatColor.WHITE + "5"));
        lore.add((ChatColor.RED + "+") + (ChatColor.YELLOW + "체력 흡수:") + " " + (ChatColor.WHITE + "0.1% (공격력 0.1%)"));
        lore.add((ChatColor.RED + "+") + (ChatColor.YELLOW + "넉백 저항:") + " " + (ChatColor.WHITE + "0.0001"));
        lore.add((ChatColor.LIGHT_PURPLE + "현재 스텟: ") + (ChatColor.YELLOW + String.valueOf(con)));
        healthItemMeta.setLore(lore);
        healthItem.setItemMeta(healthItemMeta);
        return healthItem;
    }

    public ItemStack createStrengthItem(int str) {
        // 체력 아이템 생성 및 설정
        ItemStack strengthItem = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        ItemMeta strengthItemMeta = strengthItem.getItemMeta();
        assert strengthItemMeta != null;
        strengthItemMeta.setDisplayName(ChatColor.RED + (ChatColor.BOLD + "힘"));
        ArrayList<String> lore = new ArrayList<>();
        lore.add((ChatColor.RED + "+") + (ChatColor.YELLOW + "체력:") + " " + (ChatColor.WHITE + "2"));
        lore.add((ChatColor.RED + "+") + (ChatColor.YELLOW + "근접 공격력:") + " " + (ChatColor.WHITE + "0.5"));
        lore.add((ChatColor.RED + "+") + (ChatColor.YELLOW + "방어력:") + " " + (ChatColor.WHITE + "0.3"));
        lore.add((ChatColor.LIGHT_PURPLE + "현재 스텟: ") + (ChatColor.YELLOW + String.valueOf(str)));
        strengthItemMeta.setLore(lore);
        strengthItem.setItemMeta(strengthItemMeta);
        return strengthItem;
    }

    public ItemStack createDexItem(int dex) {
        // 민첩 아이템 생성 및 설정
        ItemStack dexItem = new ItemStack(Material.LIGHT_BLUE_STAINED_GLASS_PANE);
        ItemMeta dexItemMeta = dexItem.getItemMeta();
        assert dexItemMeta != null;
        dexItemMeta.setDisplayName(ChatColor.AQUA + (ChatColor.BOLD + "민첩"));
        ArrayList<String> lore = new ArrayList<>();
        lore.add((ChatColor.RED + "+") + (ChatColor.YELLOW + "이동속도:") + " " + (ChatColor.WHITE + "0.3%"));
        lore.add((ChatColor.RED + "+") + (ChatColor.YELLOW + "근접 치명타 데미지:") + " " + (ChatColor.WHITE + "5%"));
        lore.add((ChatColor.RED + "+") + (ChatColor.YELLOW + "원거리 치명타 데미지:") + " " + (ChatColor.WHITE + "15%"));
        lore.add((ChatColor.RED + "+") + (ChatColor.YELLOW + "공격속도:") + " " + (ChatColor.WHITE + "0.1"));
        lore.add((ChatColor.RED + "+") + (ChatColor.YELLOW + "체력:") + " " + (ChatColor.WHITE + "1"));
        lore.add((ChatColor.LIGHT_PURPLE + "현재 스텟: ") + (ChatColor.YELLOW + String.valueOf(dex)));
        dexItemMeta.setLore(lore);
        dexItem.setItemMeta(dexItemMeta);
        return dexItem;
    }

    public ItemStack createLuckItem(int luck) {
        // 체력 아이템 생성 및 설정
        ItemStack luckItem = new ItemStack(Material.YELLOW_STAINED_GLASS_PANE);
        ItemMeta luckItemMeta = luckItem.getItemMeta();
        assert luckItemMeta != null;
        luckItemMeta.setDisplayName(ChatColor.GOLD + (ChatColor.BOLD + "행운"));
        ArrayList<String> lore = new ArrayList<>();
        lore.add((ChatColor.RED + "+") + (ChatColor.YELLOW + "근접 치명타 확률:") + " " + (ChatColor.WHITE + "0.3%"));
        lore.add((ChatColor.RED + "+") + (ChatColor.YELLOW + "원거리 치명타 확률:") + " " + (ChatColor.WHITE + "1%"));
        lore.add((ChatColor.RED + "+") + (ChatColor.YELLOW + "방어력:") + " " + (ChatColor.WHITE + "0.5"));
        lore.add((ChatColor.LIGHT_PURPLE + "현재 스텟: ") + (ChatColor.YELLOW + String.valueOf(luck)));
        luckItemMeta.setLore(lore);
        luckItem.setItemMeta(luckItemMeta);
        return luckItem;
    }

    public ItemStack createWisItem(int wis) {
        // 체력 아이템 생성 및 설정
        ItemStack wisItem = new ItemStack(Material.PURPLE_STAINED_GLASS_PANE);
        ItemMeta wisItemMeta = wisItem.getItemMeta();
        assert wisItemMeta != null;
        wisItemMeta.setDisplayName(ChatColor.DARK_PURPLE + (ChatColor.BOLD + "지혜"));
        ArrayList<String> lore = new ArrayList<>();
        lore.add((ChatColor.RED + "+") + (ChatColor.YELLOW + "근접 공격력:") + " " + (ChatColor.WHITE + "0.1"));
        lore.add((ChatColor.RED + "+") + (ChatColor.YELLOW + "원거리 치명타 데미지:") + " " + (ChatColor.WHITE + "5%"));
        lore.add((ChatColor.RED + "+") + (ChatColor.YELLOW + "체력:") + " " + (ChatColor.WHITE + "1"));
        lore.add((ChatColor.RED + "+") + (ChatColor.YELLOW + "방어력:") + " " + (ChatColor.WHITE + "0.01"));
        lore.add((ChatColor.RED + "+") + (ChatColor.YELLOW + "치유량:") + " " + (ChatColor.WHITE + "1% (공격력 1%)"));
        lore.add((ChatColor.LIGHT_PURPLE + "현재 스텟: ") + (ChatColor.YELLOW + String.valueOf(wis)));
        wisItemMeta.setLore(lore);
        wisItem.setItemMeta(wisItemMeta);
        return wisItem;
    }

    public ItemStack createStatsInfo(JavaPlugin plugin, Player player) {
        ItemStack emeraldItem = new ItemStack(Material.EMERALD);
        ItemMeta emeraldItemMeta = emeraldItem.getItemMeta();
        assert emeraldItemMeta != null;

        File playerFile = new File(plugin.getDataFolder() + "/userData", player.getName() + ".json");
        JsonObject playerData = JsonParseUtils.loadPlayerData(playerFile, new Gson());

        emeraldItemMeta.setDisplayName(ChatColor.DARK_PURPLE + (ChatColor.BOLD + "포인트"));
        ArrayList<String> lore = new ArrayList<>();
        lore.add((ChatColor.RED + "+") + (ChatColor.YELLOW + "남은 포인트:") + " " + (ChatColor.WHITE + String.valueOf(playerData.get("point").getAsInt())));
        emeraldItemMeta.setLore(lore);
        emeraldItem.setItemMeta(emeraldItemMeta);
        return emeraldItem;
    }

    public ItemStack createEmptyGlassItem() {
        // 빈 유리 판 아이템 생성
        ItemStack emptyGlassPane = new ItemStack(Material.WHITE_STAINED_GLASS_PANE);
        ItemMeta emptyGlassPaneMeta = emptyGlassPane.getItemMeta();
        assert emptyGlassPaneMeta != null;
        emptyGlassPaneMeta.setDisplayName(" "); // 이름을 공백으로 설정
        emptyGlassPane.setItemMeta(emptyGlassPaneMeta);
        return emptyGlassPane;
    }
}
