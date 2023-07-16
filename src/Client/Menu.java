package Client;

import Options.HashOptions;
import Options.Instructor;
import Options.LinkedListOptions;
import Options.StringOptions;
import Server.Server;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;


public class Menu {

    public Menu() {
    }

    public static boolean menu(HashMap<String, String> modifiedData, HashMap<String, LinkedList<String>> modifiedData1, HashMap<String, HashMap<String, String>> modifiedData2) {

        System.out.print(Client.IP + ":" + Server.PORT2 + " ~ " + Server.PORT + ">");
        Scanner sc = new Scanner(System.in);
        String options = sc.nextLine();

//        退出指令 返回true 保存文件
        if (options.equals("exit"))
            return true;

        String[] option = options.split(" ");

//        心跳指令
        if (options.equals("ping")) {
            System.out.println("pong");
            return false;
        }

        if (option[0].equals("help")) {
            if (option.length == 1) {
                Instructor.help();
            } else if (option.length == 2) {
                Instructor.help(option[1]);
            } else {
                System.out.println("input error");
            }
            return false;
        }

        if (option.length > 4) {
            System.out.println("input error");
            return false;
        }

//        根据输入的不同指令 分别进行三种不同类型的操作
        boolean stringNotExecute = true;
        StringOptions stringOptions = new StringOptions(option, modifiedData);
        stringNotExecute = stringOptions.menu();

        boolean linkedListNotExecute = true;
        LinkedListOptions linkedListOptions = new LinkedListOptions(option, modifiedData1);
        linkedListNotExecute = linkedListOptions.menu();

        boolean HashNotExecute = true;
        HashOptions hashOptions = new HashOptions(option, modifiedData2);
        HashNotExecute = hashOptions.menu();

        if (stringNotExecute && linkedListNotExecute && HashNotExecute){
            System.out.println("input error");
            return false;
        }
//        创造每一种类型的类
//        在每一个类型中都有一个选择方法的类，如果没有执行此种类型的方法，则会返回false，表示此次循环没有执行操作，
//        如果三种类型都没有选择到方法，也就是说输入错误了
//        执行完操作之后默认进行下一次操作 不退出不保存 直接停止程序运行会导致本地文件未能更新值
        return false;
    }
}
