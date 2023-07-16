package Client;

import Log.MyLogger;
import Server.Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedList;

public class Client {
    public static final String IP = "127.0.0.1";
    public static final String CLIENT_LOG_FILE = "src\\Log\\ClientLog.log";
    public static void main(String[] args) {
        MyLogger myLogger = new MyLogger();
        myLogger.logToFile("Starting Client.", CLIENT_LOG_FILE);
        try {
//            将三个类型的数据分别都先接收到客户端当中
            Socket socket = new Socket(IP, Server.PORT);
            Socket socket1 = new Socket(IP, Server.PORT1);
            Socket socket2 = new Socket(IP, Server.PORT2);
            System.out.println("连接服务器成功！");

            myLogger.logToFile("Client Connected", CLIENT_LOG_FILE);

            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            ObjectInputStream ois1 = new ObjectInputStream(socket1.getInputStream());
            ObjectInputStream ois2 = new ObjectInputStream(socket2.getInputStream());

            HashMap<String, String> data = (HashMap<String, String>) ois.readObject();
            HashMap<String, LinkedList<String>> data1 = (HashMap<String, LinkedList<String>>) ois1.readObject();
            HashMap<String, HashMap<String, String>> data2 = (HashMap<String, HashMap<String, String>>) ois2.readObject();

            // 客户端对数据进行修改操作


            HashMap<String, String> modifiedData = new HashMap<>(data);
            HashMap<String, LinkedList<String>> modifiedData1 = new HashMap<>(data1);
            HashMap<String, HashMap<String, String>> modifiedData2 = new HashMap<>(data2);

            while (true) {
                myLogger.logToFile("Choosing options", CLIENT_LOG_FILE);

//                进入while循环,选择想要的操作,修改文件之后写回给服务器端

//                创造一个数据类型为布尔的方法,
//                通过返回值和if语句判断要不要继续循环下去(选择exit方法可以退出循环结束进程)
//                如果不是选择exit方法,括号内的方法会继续执行,执行到exit为止

                if (Menu.menu(modifiedData, modifiedData1, modifiedData2)) {
//                    进入到括号内的语句,就表示选择了exit方法
//                    进入余下语句,就把三种类型的数据从客户端写回服务器,注意及时关流
                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                    oos.writeObject(modifiedData);
                    oos.flush();
                    oos.close();

                    ObjectOutputStream oos1 = new ObjectOutputStream(socket1.getOutputStream());
                    oos1.writeObject(modifiedData1);
                    oos1.flush();
                    oos1.close();

                    ObjectOutputStream oos2 = new ObjectOutputStream(socket2.getOutputStream());
                    oos2.writeObject(modifiedData2);
                    oos2.flush();
                    oos2.close();

                    break;
                }

            }

//            程序主要步骤运行完毕,关流结束
            ois.close();
            ois1.close();
            ois2.close();
            socket.close();
            socket1.close();
            socket2.close();

        } catch (IOException | ClassNotFoundException e) {
            myLogger.logToFile("Client Failed because of" + e, CLIENT_LOG_FILE);
        }
    }

}
