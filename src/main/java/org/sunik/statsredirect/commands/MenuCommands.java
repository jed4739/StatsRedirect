package org.sunik.statsredirect.commands;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
            if (label.equalsIgnoreCase("status")) {
                CommandService.status(plugin, p);
                return true;
            }
            if (label.equalsIgnoreCase("stats")) {
                if (args.length > 1 && p.isOp()) {
                    if (args[0].equals("reset")) {
                        if (args.length == 2) {
                            Player targetPlayer = Bukkit.getPlayer(args[1]);
                            assert targetPlayer != null;
                            if (!targetPlayer.isOnline()) {
                                p.sendMessage(ChatColor.RED + "해당 유저는 접속하고 있지 않습니다.");
                                return true;
                            }
                            CommandService.reset(plugin, targetPlayer, p);
                            return true;
                        } else if (args.length == 3) {
                            Player targetPlayer = Bukkit.getPlayer(args[1]);
                            assert targetPlayer != null;
                            CommandService.modifyStats(plugin, targetPlayer, p, args[2]);
                        } else {
                            plugin.getLogger().info(p.getName() + "님이 스텟 명령어 실패!");
                            CommandService.infoMessage(p);
                        }
                    } else {
                        plugin.getLogger().info(p.getName() + "님이 스텟 명령어 실패!");
                        CommandService.infoMessage(p);
                    }
                    return true;
                }
                File playerFile = new File(plugin.getDataFolder() + "/userData", p.getName() + ".json");
                JsonObject playerData = JsonParseUtils.loadPlayerData(playerFile, new Gson());
                CommandService.openStatInventory(p, playerData, plugin); // 스탯 가상 인벤토리 열기
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
