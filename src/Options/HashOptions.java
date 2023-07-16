package Options;

import java.awt.*;
import java.util.HashMap;

public class HashOptions {
    private final HashMap<String, HashMap<String, String>> modifiedData;
    private final String[] option;

    public HashOptions(String[] option, HashMap<String, HashMap<String, String>> modifiedData) {
        this.option = option;
        this.modifiedData = modifiedData;
    }

    public boolean menu(){
        if (option[0].equals("hset")) {
            if (option.length == 4) hset(modifiedData, option[1], option[2], option[3]);
            else System.out.println("input error");
            return false;

        } else if (option[0].equals("hget")) {
            if (option.length == 3) hget(modifiedData, option[1], option[2]);
            else System.out.println("input error");
            return false;

        } else if (option[0].equals("hdel")) {
            if (option.length == 2) hdel(modifiedData, option[1]);
            else if (option.length == 3) hdel(modifiedData, option[1], option[2]);
            else System.out.println("input error");
            return false;

        }else return true;
    }

    public static void hset(HashMap<String, HashMap<String, String>> modifiedData, String key, String field, String value) {
            HashMap<String, String> data = modifiedData.get(key);
//            如果是第一次写入 或者之前清空过 data是null不能直接写入
            if (data == null) data = new HashMap<>();
            data.put(field, value);
//                把修改完后的数值重新写入文件 以便下一次写入文件
                modifiedData.put(key, data);
                System.out.println("1");
    }

    public static void hget(HashMap<String, HashMap<String, String>> modifiedData, String key, String field) {
        if (modifiedData.containsKey(key)) {
            HashMap<String, String> data = modifiedData.get(key);
            if (data == null) {
                System.out.println("null");
                return;
            }
            if (data.containsKey(field)) {
                String s = data.get(field);
                System.out.println(s);
            } else {
                System.out.println("null");
            }
        } else {
            System.out.println("null");
        }
    }

    public static void hdel(HashMap<String, HashMap<String, String>> modifiedData, String key, String field) {
        if (modifiedData.containsKey(key)) {
            HashMap<String, String> data = modifiedData.get(key);
            if (data == null) {
                System.out.println("null");
                return;
            }
            if (data.containsKey(field)) {
                data.remove(field);
                modifiedData.put(key, data);
                System.out.println("1");
//                把修改完后的数值重新写入文件 以便下一次写入文件
            } else {
                System.out.println("null");
            }
        } else {
            System.out.println("null");
        }
    }

    public static void hdel(HashMap<String, HashMap<String, String>> modifiedData, String key) {
        if (modifiedData.containsKey(key)) {
            HashMap<String, String> data = modifiedData.get(key);
            if (data == null) {
                System.out.println("null");
                return;
            }
            data.clear();
            System.out.println("1");
        } else {
            System.out.println("null");
        }
    }
}
