package org.sunik.statsredirect.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.sunik.statsredirect.Util.JsonParseUtils;

import java.io.File;

/**
 * 해당 서비스 클래스는 플레이어의 레벨 로직에 대한 클래스 파일 입니다.
 */
public class PlayerCommandService {
    private final JavaPlugin plugin;

    public PlayerCommandService(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public boolean playerLevelCommand(Player p, String[] args) {
        if (args.length <= 1 || !p.isOp()) {
            plugin.getLogger().info(p.getName() + "님이 레벨 명령어 실패!");
            CommandService.infoMessage(p);
        }
        Player targetPlayer = Bukkit.getPlayer(args[1]);

        // null 체크
        if (targetPlayer == null) {
            p.sendMessage(ChatColor.RED + "해당 유저는 접속하고 있지 않습니다.");
            return true;
        }

        // 온라인 여부 확인
        if (!targetPlayer.isOnline()) {
            p.sendMessage(ChatColor.RED + "해당 유저는 접속하고 있지 않습니다.");
            return true;
        }


        if (args.length < 4 && args[0].equals("modify")) {
            String playerName = targetPlayer.getName();
            int changeValue = Integer.parseInt(args[2]);
            String sendMessage = (ChatColor.BOLD + targetPlayer.getName()) + "의 레벨 수정 완료";
            this.playerValueChange("level", p, playerName, changeValue, sendMessage);
        } else if (args.length < 3 && args[0].equals("reset")) {
            String playerName = targetPlayer.getName();
            int changeValue = 0;
            String sendMessage = (ChatColor.BOLD + targetPlayer.getName()) + "의 레벨 초기화 완료";
            this.playerValueChange("level", p, playerName, changeValue, sendMessage);
        } else {
            CommandService.infoMessage(p);
        }
        return true;
    }

    /**
     * 플레이어의 속성 값을 변경하고 메시지를 전송
     * @param property 바꿀 속성 이름
     * @param player 커맨드 입력 플레이어
     * @param targetName 지정할 플레이어
     * @param changeValue 변경한 레벨 값
     * @param sendMessage 플레이어에게 보낼 메시지
     */
    public void playerValueChange(String property, Player player, String targetName, int changeValue, String sendMessage) {
        File playerFile = new File(plugin.getDataFolder() + "/userData", targetName + ".json");
        JsonObject playerData = JsonParseUtils.loadPlayerData(playerFile, new Gson());
        playerData.addProperty(property, changeValue);
        player.sendMessage(sendMessage);
        JsonParseUtils.modifyPlayerData(playerFile, new Gson(), playerData);
    }


}
