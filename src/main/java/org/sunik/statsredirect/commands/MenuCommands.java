package org.sunik.statsredirect.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.sunik.statsredirect.StatsRedirect;

public class MenuCommands implements CommandExecutor {
    private final StatsRedirect plugin;
    public MenuCommands(StatsRedirect statsRedirect) {
        this.plugin = statsRedirect;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (label.equalsIgnoreCase("statslist")) {
                plugin.infoMessage(p);
                return true;
            }
            if (label.equalsIgnoreCase("스탯")) {
                plugin.openStatInventory(p); // 스탯 가상 인벤토리 열기
            }
        } else sender.sendMessage("플레이어만 사용할 수 있는 명령어입니다.");
        return false;
    }
}
