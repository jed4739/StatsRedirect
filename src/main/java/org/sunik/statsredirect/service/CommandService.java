package org.sunik.statsredirect.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;
import org.sunik.statsredirect.Util.InventoryStatsItems;
import org.sunik.statsredirect.Util.JsonParseUtils;

import java.io.File;

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
            p.sendMessage(admin + ChatColor.YELLOW + "/stats reset [PlayerName] [str,con,dex,luck](없으면 all)");
        }

        // Default Command List
        p.sendMessage(ChatColor.GOLD + "/stats");
        p.sendMessage(ChatColor.GOLD + "/statslist");
    }
    public static void status(JavaPlugin plugin, Player p) {
        // Header
        p.sendMessage(ChatColor.BOLD + "" + ChatColor.WHITE + "==== " + ChatColor.RED +
                p.getName()
                + ChatColor.WHITE + " ====");

        File playerFile = new File(plugin.getDataFolder() + "/userData", p.getName() + ".json");
        JsonObject playerData = JsonParseUtils.loadPlayerData(playerFile, new Gson());
        p.sendMessage(ChatColor.YELLOW + (ChatColor.BOLD + "Lv") + ChatColor.YELLOW + ": " + ChatColor.WHITE + playerData.get("str").getAsInt());
        p.sendMessage(ChatColor.RED + (ChatColor.BOLD + "힘") + ChatColor.YELLOW + ": " + ChatColor.WHITE + playerData.get("str").getAsInt());
        p.sendMessage(ChatColor.GREEN + (ChatColor.BOLD + "체력") + ChatColor.YELLOW + ": " + ChatColor.WHITE + playerData.get("con").getAsInt());
        p.sendMessage(ChatColor.AQUA + (ChatColor.BOLD + "민첩") + ChatColor.YELLOW + ": " + ChatColor.WHITE + playerData.get("dex").getAsInt());
        p.sendMessage(ChatColor.GOLD + (ChatColor.BOLD + "행운") + ChatColor.YELLOW + ": " + ChatColor.WHITE + playerData.get("luck").getAsInt());

        p.sendMessage(ChatColor.GOLD + "/stats");
        p.sendMessage(ChatColor.GOLD + "/statslist");
    }

    public static void reset(JavaPlugin plugin, Player targetP, Player p) {
        File playerFile = new File(plugin.getDataFolder() + "/userData", targetP.getName() + ".json");
        JsonObject playerData = JsonParseUtils.loadPlayerData(playerFile, new Gson());
        playerData.addProperty("str", 0);
        playerData.addProperty("con", 0);
        playerData.addProperty("dex", 0);
        playerData.addProperty("luck", 0);
        p.sendMessage((ChatColor.BOLD + targetP.getName()) + "의 스텟 초기화 완료");
        JsonParseUtils.modifyPlayerData(playerFile, new Gson(), playerData);
    }

    public static void modifyStats(JavaPlugin plugin, Player targetP, Player p, String args) {
        File playerFile = new File(plugin.getDataFolder() + "/userData", targetP.getName() + ".json");
        JsonObject playerData = JsonParseUtils.loadPlayerData(playerFile, new Gson());
        switch (args) {
            case "str":
                playerData.addProperty("str", 0);
                break;
            case "con":
                playerData.addProperty("con", 0);
                break;
            case "dex":
                playerData.addProperty("dex", 0);
                break;
            case "luck":
                playerData.addProperty("luck", 0);
                break;
            default: CommandService.infoMessage(targetP); return;
        }
        p.sendMessage((ChatColor.BOLD + targetP.getName()) + "의 " + (ChatColor.BOLD + args) + " 스텟 초기화 완료.");
        JsonParseUtils.modifyPlayerData(playerFile, new Gson(), playerData);
    }
}
