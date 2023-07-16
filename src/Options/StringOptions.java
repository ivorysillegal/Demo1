package Options;

import java.awt.*;
import java.util.HashMap;

public class StringOptions {

    private final HashMap<String, String> modifiedData;
    private final String[] option;

    public StringOptions(String[] option, HashMap<String, String> modifiedData) {
        this.option = option;
        this.modifiedData = modifiedData;
    }
    public boolean menu() {
        if (option[0].equals("get")) {
            if (option.length == 2) get(modifiedData, option[1]);
            else System.out.println("input error");
            return false;

        } else if (option[0].equals("del")) {
            if (option.length == 2) del(modifiedData, option[1]);
            else System.out.println("input error");
            return false;

        } else if (option[0].equals("set")) {
            if (option.length == 3) set(modifiedData, option[1], option[2]);
            else System.out.println("input error");
            return false;

        } else return true;
    }


    public static void set(HashMap<String, String> modifiedData, String setKey, String setValue) {
        modifiedData.put(setKey, setValue);
        System.out.println("1");
    }

    public static void get(HashMap<String, String> modifiedData, String inputKey) {
        if (modifiedData.containsKey(inputKey)) {
            String valueOfInputKey = modifiedData.get(inputKey);
            System.out.println(valueOfInputKey);
        } else {
            System.out.println("null");
        }
    }

    public static void del(HashMap<String, String> modifiedData, String delKey) {
        if (modifiedData.containsKey(delKey)) {
            modifiedData.remove(delKey);
            System.out.println("1");
        } else {
            System.out.println("null");
        }
    }


}
