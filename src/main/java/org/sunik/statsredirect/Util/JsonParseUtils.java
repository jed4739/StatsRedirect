package org.sunik.statsredirect.Util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class JsonParseUtils {
    /**
     * JsonObject 에 플레이어 정보를 담고 반환.
     * @param file - 해당 파일 위치
     * @param gson - Gson 객체
     * @return JsonObject - 유저 정보를 담고 있는 객체
     */
    public static JsonObject loadPlayerData(File file, Gson gson) {
        JsonObject data = new JsonObject();
        try (FileReader reader = new FileReader(file)) {
            data = gson.fromJson(reader, JsonObject.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * File 에 지정한 (Player Name).json 의 정보를 수정.
     * @param userFile - 해당 파일 위치
     * @param gson - Gson 객체
     * @param jsonObject - 수정할 프로퍼티
     */
    public static void modifyPlayerData(File userFile, Gson gson, JsonObject jsonObject) {
        try (FileWriter writer = new FileWriter(userFile)) {
            writer.write(gson.toJson(jsonObject));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * File 에 지정한 경로와 파일 형식에 맞게 파일 생성.
     * @param file - 해당 파일 위치
     * @param gson - Gson 객체
     * @param data - 추가할 프로퍼티
     */
    public static void savePlayerData(File file, Gson gson, JsonObject data) {
        try (FileWriter writer = new FileWriter(file)) {
            gson.toJson(data, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
