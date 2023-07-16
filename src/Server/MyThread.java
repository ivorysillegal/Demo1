package Server;

import Log.MyLogger;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.LinkedList;

public class MyThread implements Runnable {
    private final HashMap<String, HashMap<String, String>> data2;
    private final Socket socket;
    private final Socket socket1;
    private final Socket socket2;
    private final HashMap<String, String> data;
    private final HashMap<String, LinkedList<String>> data1;



    public MyThread(Socket socket, HashMap<String,String> data, Socket socket1, HashMap<String,LinkedList<String>> data1, Socket socket2, HashMap<String,HashMap<String,String>> data2) {
    this.socket = socket;
    this.socket1 = socket1;
    this.socket2 = socket2;
    this.data = data;
    this.data1 = data1;
    this.data2 = data2;

    }

    @Override
    public void run() {

        File file;
        try {
            file = new File(Server.STRING_FILE);
            File file1 = new File(Server.LINKED_LIST_FILE);
            File file2 = new File(Server.HASH_FILE);


//            把从本地文件读到的数据全部发送给客户端
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectOutputStream oos1 = new ObjectOutputStream(socket1.getOutputStream());
            ObjectOutputStream oos2 = new ObjectOutputStream(socket2.getOutputStream());
            oos.writeObject(data);
            oos1.writeObject(data1);
            oos2.writeObject(data2);
            oos.flush();
            oos1.flush();
            oos2.flush();

//            把从客户端经过修改之后的数据全部接收
            ObjectInputStream ois = null;
            ObjectInputStream ois1 = null;
            ObjectInputStream ois2 = null;
            try {
                ois = new ObjectInputStream(socket.getInputStream());
                ois1 = new ObjectInputStream(socket1.getInputStream());
                ois2 = new ObjectInputStream(socket2.getInputStream());

                HashMap<String, String> receivedData = (HashMap<String, String>) ois.readObject();
                HashMap<String, LinkedList<String>> receivedData1 = (HashMap<String, LinkedList<String>>) ois1.readObject();
                HashMap<String, HashMap<String, String>> receivedData2 = (HashMap<String, HashMap<String, String>>) ois2.readObject();


//                判断接收的数据是不是空的，如果不是空的话就直接输出回本地文件当中
                if (!receivedData.isEmpty()) {
                    if (file.exists()) {
                        file.delete();
                    }
                    ObjectOutputStream fileOos = new ObjectOutputStream(Files.newOutputStream(file.toPath()));
                    data.putAll(receivedData);
                    fileOos.writeObject(data);
                    fileOos.flush();
                    fileOos.close();
                }

                if (!receivedData1.isEmpty()) {
                    if (file1.exists()) {
                        file1.delete();
                    }
                    ObjectOutputStream fileOos = new ObjectOutputStream(Files.newOutputStream(file1.toPath()));
                    data1.putAll(receivedData1);
                    fileOos.writeObject(data1);
                    fileOos.flush();
                    fileOos.close();
                }

                if (!receivedData2.isEmpty()) {
                    if (file2.exists()) {
                        file2.delete();
                    }
                    ObjectOutputStream fileOos = new ObjectOutputStream(Files.newOutputStream(file2.toPath()));
                    data2.putAll(receivedData2);
                    fileOos.writeObject(data2);
                    fileOos.flush();
                    fileOos.close();
                }



//            数据写入完毕，关掉所有流
            oos.close();
            oos1.close();
            oos2.close();
            ois.close();
            ois1.close();
            ois2.close();
            socket.close();
            socket1.close();
            socket2.close();

        } catch (IOException | ClassNotFoundException e) {
            return;
        }
        // 退出程序时保存数据到本地文件
        ObjectOutputStream fileOos = new ObjectOutputStream(Files.newOutputStream(file.toPath()));
        fileOos.writeObject(data);
        fileOos.flush();
        fileOos.close();
        System.out.println("数据已保存到本地文件 " + Server.STRING_FILE);

        ObjectOutputStream fileOos1 = new ObjectOutputStream(Files.newOutputStream(file1.toPath()));
        fileOos1.writeObject(data1);
        fileOos1.flush();
        fileOos1.close();
        System.out.println("数据已保存到本地文件 " + Server.LINKED_LIST_FILE);

        ObjectOutputStream fileOos2 = new ObjectOutputStream(Files.newOutputStream(file2.toPath()));
        fileOos2.writeObject(data2);
        fileOos2.flush();
        fileOos2.close();
        System.out.println("数据已保存到本地文件 " + Server.HASH_FILE);

    } catch (IOException e) {
            MyLogger myLogger = new MyLogger();
            myLogger.logToFile(String.valueOf(e), Server.SERVER_LOG_FILE);
        }
    }
}
