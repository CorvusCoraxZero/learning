package com.zero.operator;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AnalyzeTaskHistory {
    private enum Level {
        URGENT(0, "urgent"),
        HIGH(1, "high"),
        MID(2, "mid"),
        LOW(3, "low"),
        MESSAGE(4, "message"),
        WEAKNESS(5, "weakness");

        int value;
        String name;
        private static HashMap<Integer, Level> map = new HashMap<>();

        Level(int value, String name) {
            this.value = value;
            this.name = name;
        }

        public static Level getLevelByValue(int value) {
            if (map.size() == 0) {
                for (Level level : Level.values()) {
                    map.put(level.value, level);
                }
            }
            return map.get(value);
        }
    }

    private enum MapType {
        TASK, IP, PORT
    }

    public static String analyzeTaskData(String originData) {
        Gson gson = new Gson();
        Map<String, Object> map = gson.fromJson(originData, HashMap.class);
        Map<String, Object> dataMap = (LinkedTreeMap<String, Object>) map.get("data");
        List<Map<String, String>> vuls = (List<Map<String, String>>) dataMap.get("vuls");

        Long taskId = Math.round((Double) map.get("task_id"));
        Long taskHistoryId = Math.round((Double) map.get("task_history_id"));

        Map<String, Object> taskHistory = initMap(MapType.TASK);
        taskHistory.put("task_id", taskId);
        taskHistory.put("id", taskHistoryId);
        Map<String, Map<String, Object>> taskHistoryAboutIp = new HashMap<>();
        Map<String, Map<String, Object>> taskHistoryAboutPort = new HashMap<>();

        for (Map<String, String> vul : vuls) {
            String assetUrl = vul.get("assetUrl");
            Pattern pattern = Pattern.compile("^(?:https?:\\/\\/)?([\\w-]+(?:\\.[\\w-]+)+):(\\d{1,5})(?:\\/.*|\\/?)$");
            Matcher matcher = pattern.matcher(assetUrl);
            String ip = assetUrl;
            int port = 0;
            if (matcher.find()) {
                ip = matcher.group(1);
                port = Integer.parseInt(matcher.group(2));
            }

            System.out.println(ip);
            System.out.println(port);

            int levelValue = Integer.parseInt(vul.get("level"));
            Level level = Level.getLevelByValue(levelValue);

            // task
            taskHistory.put(level.name, (Integer) taskHistory.get(level.name) + 1);
            taskHistory.put("number", (Integer) taskHistory.get("number") + 1);

            // ip
            String finalIp = ip;
            Map<String, Object> ipMap = taskHistoryAboutIp.computeIfAbsent(ip, key -> {
                Map<String, Object> initMap = initMap(MapType.IP);
                initMap.put("ip_addr", finalIp);
                initMap.put("task_history_id", taskHistoryId);
                taskHistory.put("ip_count", (Integer) taskHistory.get("ip_count") + 1);
                return initMap;
            });
            ipMap.put(level.name, (Integer) ipMap.get(level.name) + 1);
            ipMap.put("total", (Integer) ipMap.get("total") + 1);

            //port
            int finalPort = port;
            Map<String, Object> portMap = taskHistoryAboutPort.computeIfAbsent(ip + port, key -> {
                Map<String, Object> initMap = initMap(MapType.PORT);
                initMap.put("ip_addr", finalIp);
                initMap.put("port", finalPort);
                initMap.put("task_history_id", taskHistoryId);
                ipMap.put("port", (Integer) ipMap.get("port") + 1);
                return initMap;
            });
            portMap.put(level.name, (Integer) portMap.get(level.name) + 1);
            portMap.put("total", (Integer) portMap.get("total") + 1);
        }

        Map<String, Object> resultMap = new LinkedTreeMap<>();
        resultMap.put("task_history", taskHistory);
        resultMap.put("task_history_ip", taskHistoryAboutIp.values());
        resultMap.put("task_history_port", taskHistoryAboutPort.values());
        return gson.toJson(resultMap);
    }

    public static Map<String, Object> initMap(MapType type) {
        HashMap<String, Object> map = new HashMap<>();
        switch (type) {
            case TASK:
                map.put("task_id", null);
                map.put("number", 0);
                map.put("active_count", 0);
                map.put("weakness", 0);
                map.put("urgent", 0);
                map.put("high", 0);
                map.put("mid", 0);
                map.put("low", 0);
                map.put("message", 0);
                map.put("current_status", 6);
                map.put("start time", null);
                map.put("end_time", new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
                map.put("ip_count", 0);
                return map;
            case IP:
                map.put("ip_addr", "");
                map.put("asset_type", 1);
                map.put("port", 0);
                map.put("task_history_id", 0);
                map.put("tag", "{}");
                map.put("weakness", 0);
                map.put("urgent", 0);
                map.put("high", 0);
                map.put("mid", 0);
                map.put("low", 0);
                map.put("message", 0);
                map.put("total", 0);
                return map;
            case PORT:
                map.put("ip_addr", "");
                map.put("port_num", 0);
                map.put("port", 0);
                map.put("protocol", "TCP");
                map.put("task_history_id", 0);
                map.put("tag", "{}");
                map.put("weakness", 0);
                map.put("urgent", 0);
                map.put("high", 0);
                map.put("mid", 0);
                map.put("low", 0);
                map.put("message", 0);
                map.put("total", 0);
                return map;
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }
    }

    public static void main(String[] args) {
        System.out.println(analyzeTaskData("{\n" +
                "    \"data\": {\n" +
                "        \"vuls\": [\n" +
                "            {\n" +
                "                \"result\": \"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\",\n" +
                "                \"level\": \"0\",\n" +
                "                \"createTime\": 1667506574000,\n" +
                "                \"assetId\": \"382\",\n" +
                "                \"module\": 0,\n" +
                "                \"policyNo\": \"EmailAddress\",\n" +
                "                \"id\": \"7311\",\n" +
                "                \"proof\": \"sjsrey@gmail.com \",\n" +
                "                \"taskId\": 100,\n" +
                "                \"url\": \"http://192.168.23.177/gtd-php/donate.php\",\n" +
                "                \"assetUrl\": \"http://192.168.23.177:8891/123.index\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"result\": \"eJy9VGtv4jgU/Y7Ef7ibD6uZ0WDHCeENK4bQx5SXBrrVarWqTGISD8HOOIaW/vp1SDulo5bVfqkQwj4+9r3nnss9Hy4A8w2NWIbhYrGYYYJIuXQhM90C0nQQqTWQ4yJSr5dL1xlTlX7EhDkbyweeJBR7yIYPN1yE8i6DyQJqiLThZnpTq7ZhoXhoyLiO7DbMR4OB0wY0MREHo2/gIBt5dt2pH2Eu8pBrsOYLzD7Gqsge/FwN26B2QAiyP0LC1wzOWbCW5VI/CFhqkvyEPz1tKkMRyJCLqAXRA08/h2yVUM3KpYGUa85aMLuYzYfz+aXfTZXi9fVqu9Q/AuEl36PlMvoeyJoi7a8HynRiWHaTkOZZv+6a5BrEqfke6X+pu7ZXa7oeqfntcqn4PFUVHNuG6VW55Ju4LVjE289guzCRO3PiOECcFnFbtgPn40W5NGdqx1QL+ikNYoYdZKyowofr5Vbo7UfYyPB2I4U0B1Xk5skbJ1zkVMj2wDCgDXdcxzDfxjLjojKjOoghVfJ+fxvrTYLzwpLDQ+lex1IYwPgMs2LjoBryDqdZljyFn6ZMzOcjbKMmaqxhFm8zLsXtjGYZExFTuJqb1SgeZSq/Z6MqzPLlzkPEznvrT6r2RtdLV3IfhDbN8otPz/jIRNBxC6oN9xlc7FNTSs3uNc41tYOYqozp7vXirNLIi9/5zZ8OFn/Nhqa5xyOYXX8ZXQ7AqmB84w4w9hd+cWBKB2dc0ATj4cTqlTr5c70SdGJGQ/MLHc11wnqXImT3IFdPf5oOLnDDxI/UzlKG+/wF8grbgKWOpktzpaOV+ca9Dt9EkKmga2EeSJHhZULFGkV8ZQFNdNf6+3Iw/cfqmVBxcYFCrNiqa/0x6E7a065v9SZ0wzqYvs4ZG07f6o1opnNn+Iqz8E3yvCDP+cPbD/oFx2dZoHiqTQs8U/FB1UEZBDLJUiq6lmeyj9URo1RQQtjRhEeGoWVqvVIJGrwohH/5rShEmF9+zglbvRlVpiHA54oFWqr9Y0YF8XexzNL20xYeYyoexdrqAVTgVeb/SPRgr/PCs/H5q6leCbm87YchSkVk9Y53xxn/kqJNKmO6rzg2MVPEzIg6wBvMKnKu3luMzv+KpgmOJR1jJ4SRWqWfqkIY8VreKWHeOwrbUBFyKnQh6Xh3Skyj8nUrcjEeOOSUGIIa7yhG3tEsLZT8XJ6Q4ThHntRaVfdNGVC3/1PG6SmAi1FoFoeh2cHF2M0n97/qypyb\",\n" +
                "                \"level\": \"1\",\n" +
                "                \"createTime\": 1667507610000,\n" +
                "                \"assetId\": \"382\",\n" +
                "                \"module\": 0,\n" +
                "                \"policyNo\": \"DirectoryList\",\n" +
                "                \"id\": \"7590\",\n" +
                "                \"proof\": \"http://192.168.23.177/images/\",\n" +
                "                \"taskId\": 100,\n" +
                "                \"url\": \"http://192.168.23.177/images/\",\n" +
                "                \"assetUrl\": \"http://192.168.23.177:8891/\"\n" +
                "            }\n" +
                "        ],\n" +
                "        \"count\": 2\n" +
                "    },\n" +
                "    \"task_id\": 1,\n" +
                "    \"task_history_id\": 456\n" +
                "}"));
    }

}
