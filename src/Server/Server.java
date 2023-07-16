package Server;

import Log.MyLogger;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Server {

    public static final String STRING_FILE = "src\\KeyAndValue\\ForString.property";
    public static final String LINKED_LIST_FILE = "src\\KeyAndValue\\ForLinkedList.property";
    public static final String HASH_FILE = "src\\KeyAndValue\\ForHash.property";
    public static final int PORT = 8888;
    public static final int PORT1 = 8887;
    public static final int PORT2 = 8886;
    public static final String SERVER_LOG_FILE = "src\\Log\\ServerLog.log";
    public static void main(String[] args) throws IOException {

        MyLogger myLogger = new MyLogger();
        myLogger.logToFile("Starting Server", SERVER_LOG_FILE);

        ThreadPoolExecutor pool = new ThreadPoolExecutor(
                3,  // 核心线程数量
                16, // 线程池总大小
                60, // 空闲时间
                TimeUnit.SECONDS,   // 空闲时间（单位）
                new ArrayBlockingQueue<>(2),    // 队列
                Executors.defaultThreadFactory(),   // 线程工厂 让线程池如何创建对象（创建对象的方式）
                new ThreadPoolExecutor.AbortPolicy()    // 阻塞队列
        );


        ServerSocket serverSocket = null;
        ServerSocket serverSocket1 = null;
        ServerSocket serverSocket2 = null;
        try {
            serverSocket = new ServerSocket(PORT);
            serverSocket1 = new ServerSocket(PORT1);
            serverSocket2 = new ServerSocket(PORT2);
            System.out.println("服务器启动，等待客户端连接...");
            while (true) {
                Socket socket = serverSocket.accept();
                Socket socket1 = serverSocket1.accept();
                Socket socket2 = serverSocket2.accept();
                System.out.println("客户端连接成功！");
                myLogger.logToFile("Server Connect",SERVER_LOG_FILE);

    //            先创建三个空的Map，之后如果从本地文件读取的数据是空的，就直接用这个new的Map
    //            如果文件不是空的,就直接把从本地文件读取的对象赋值给这个Map就行
                Map<String, String> data = new HashMap<>();
                Map<String, LinkedList<String>> data1 = new HashMap<>();
                Map<String, HashMap<String, String>> data2 = new HashMap<>();
                File file = new File(STRING_FILE);
                File file1 = new File(LINKED_LIST_FILE);
                File file2 = new File(HASH_FILE);

    //            判断三种存储不同类型数据的文件是否为空
//                如果为空 就往文件中先写入一个字节防止报空指针
                if (file.exists() && file.isFile() && file.length() > 0) {
                    ObjectInputStream fileOis = new ObjectInputStream(Files.newInputStream(file.toPath()));
                        data = (Map<String, String>) fileOis.readObject();
                    fileOis.close();
                }else {
                    FileOutputStream f = new FileOutputStream(STRING_FILE);
                    f.write(0);
                    f.close();
                }

                if (file1.exists() && file1.isFile() && file1.length() > 0) {
                    ObjectInputStream fileOis1 = new ObjectInputStream(Files.newInputStream(file1.toPath()));
                        data1 = (Map<String, LinkedList<String>>) fileOis1.readObject();
                    fileOis1.close();
                }else {
                    FileOutputStream f = new FileOutputStream(STRING_FILE);
                    f.write(0);
                    f.close();
                }

                if (file2.exists() && file2.isFile() && file2.length() > 0) {
                    ObjectInputStream fileOis2 = new ObjectInputStream(Files.newInputStream(file2.toPath()));
                        data2 = (Map<String, HashMap<String, String>>) fileOis2.readObject();
                    fileOis2.close();
                }else {
                    FileOutputStream f = new FileOutputStream(STRING_FILE);
                    f.write(0);
                    f.close();
                }

                pool.submit(new MyThread(socket, (HashMap<String, String>) data,socket1, (HashMap<String, LinkedList<String>>) data1,socket2, (HashMap<String, HashMap<String, String>>) data2));

                Scanner sc = new Scanner(System.in);
                String option = sc.nextLine();
                if (option.equals("exit")){
                    break;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            myLogger.logToFile("Server Failed",SERVER_LOG_FILE);
        }

        assert serverSocket != null;
        serverSocket.close();
        assert serverSocket1 != null;
        serverSocket1.close();
        assert serverSocket2 != null;
        serverSocket2.close();
        myLogger.logToFile("Server stopped.", SERVER_LOG_FILE);
    }
}



