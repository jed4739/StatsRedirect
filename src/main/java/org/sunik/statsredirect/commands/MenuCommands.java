package org.sunik.statsredirect.commands;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.sunik.statsredirect.Util.HealthUtils;
import org.sunik.statsredirect.Util.JsonParseUtils;
import org.sunik.statsredirect.service.CommandService;

import java.io.File;

public class MenuCommands implements CommandExecutor {
    private final JavaPlugin plugin;
    public MenuCommands(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (label.equalsIgnoreCase("statslist")) {
                CommandService.infoMessage(p);
                return true;
            }
            if (label.equalsIgnoreCase("stats")) {
                File playerFile = new File(plugin.getDataFolder() + "/userData", p.getName() + ".json");
                JsonObject playerData = JsonParseUtils.loadPlayerData(playerFile, new Gson());
                CommandService.openStatInventory(p, playerData); // 스탯 가상 인벤토리 열기
                return true;
            }
            if (label.equalsIgnoreCase("heal") && p.isOp()) {
                HealthUtils.setMaxHealth(p, HealthUtils.getMaxHealth(p));
                for (PotionEffect effect : p.getActivePotionEffects()) {
                    PotionEffectType type = effect.getType();
                    p.removePotionEffect(type);
                }
                return true;
            } else if (label.equalsIgnoreCase("heal") && !p.isOp()) {
                p.sendMessage("관리자만 사용가능한 명령어 입니다.");
            }
        } else sender.sendMessage("플레이어만 사용할 수 있는 명령어입니다.");
        return false;
    }
}
