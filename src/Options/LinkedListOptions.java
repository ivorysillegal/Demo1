package Options;

import java.awt.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.ListIterator;

public class LinkedListOptions {

    private final HashMap<String, LinkedList<String>> modifiedData;
    private final String[] option;

    //        PS 在执行操作之前 必须判断这个链表是否为空 如果是空 就创建一个链表 把值放到这个链表内就不会
//            不然就会报空指针异常
    public LinkedListOptions(String[] option, HashMap<String, LinkedList<String>> modifiedData) {
        this.option = option;
        this.modifiedData = modifiedData;
    }

    public boolean menu(){
        if (option[0].equals("lpush")) {
            if (option.length == 3) lpush(modifiedData, option[1], option[2]);
            else System.out.println("input error");
            return false;

        } else if (option[0].equals("rpush")) {
            if (option.length == 3) rpush(modifiedData, option[1], option[2]);
            else System.out.println("input error");
            return false;

        } else if (option[0].equals("range")) {
            if (option.length == 4)
                try {
                    range(modifiedData, option[1], option[2], option[3]);
                } catch (Exception e) {
                    System.out.println("input error");
                }
            else System.out.println("input error");
            return false;

        } else if (option[0].equals("rpop")) {
            if (option.length == 2) rpop(modifiedData, option[1]);
            else System.out.println("input error");
            return false;

        } else if (option[0].equals("lpop")) {
            if (option.length == 2) lpop(modifiedData, option[1]);
            else System.out.println("input error");
            return false;

        } else if (option[0].equals("len")) {
            if (option.length == 2) len(modifiedData, option[1]);
            else System.out.println("input error");
            return false;

        } else if (option[0].equals("idel")) {
            if (option.length == 2) idel(modifiedData, option[1]);
            else System.out.println("input error");
            return false;

        }else return true;
    }


    public static void lpush(HashMap<String, LinkedList<String>> modifiedData, String key, String input) {
        LinkedList<String> value = modifiedData.get(key);
        if (value == null) {
            value = new LinkedList<>();
        }
        value.add(0, input);
//        把修改后的数值放回去
        modifiedData.put(key, value);
        System.out.println("1");
    }

    public static void rpush(HashMap<String, LinkedList<String>> modifiedData, String key, String input) {
        LinkedList<String> value = modifiedData.get(key);
        if (value == null) {
            value = new LinkedList<>();
        }
        value.add(input);
        modifiedData.put(key, value);
//        把修改后的数值重新放回去
        System.out.println("1");
    }

    public static void range(HashMap<String, LinkedList<String>> hm, String key, String start, String end) {
        LinkedList<String> value = hm.get(key);
        if (value == null) {
            System.out.println("This LinkedList is null");
            return;
        }
        int s = Integer.parseInt(start);
        int e = Integer.parseInt(end);
        ListIterator<String> it = value.listIterator(s);
        while (it.hasNext() && it.nextIndex() <= e) {
            System.out.print(it.next() + ' ');
        }
        System.out.println();
    }

    public static void len(HashMap<String, LinkedList<String>> hm, String key) {
        LinkedList<String> value = hm.get(key);
        if (value == null) {
            System.out.println("This LinkedList is null");
        } else
            System.out.println("len is " + value.size());
    }

    public static void lpop(HashMap<String, LinkedList<String>> modifiedData, String key) {
        if (modifiedData.containsKey(key)) {
            LinkedList<String> value = modifiedData.get(key);
            if (value == null) {
                System.out.println("This LinkedList is null");
                return;
            }
            String del = value.pop();
            modifiedData.put(key, value);
            System.out.println(del);
        } else {
            System.out.println("0");
        }
    }

    public static void rpop(HashMap<String, LinkedList<String>> modifiedData, String key) {
        if (modifiedData.containsKey(key)) {
            LinkedList<String> value = modifiedData.get(key);
            if (value == null) {
                System.out.println("This LinkedList is null");
                return;
            }
            String del = value.removeLast();
            modifiedData.put(key, value);
            System.out.println(del);
        } else {
            System.out.println("0");
        }
    }

    public static void idel(HashMap<String, LinkedList<String>> modifiedData, String key) {
        LinkedList<String> value = modifiedData.get(key);
        if (value == null) {
            System.out.println("This LinkedList is already null!");
            return;
        }
        value.clear();
        modifiedData.put(key, value);
        System.out.println("1");
    }


}
