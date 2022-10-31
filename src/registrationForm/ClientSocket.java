package registrationForm;


import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

import com.mysql.cj.interceptors.QueryInterceptor;


public class ClientSocket implements Disconnector{
  private static final boolean def = false;

    private static Socket socket;
    private static ObjectOutputStream objectStream = null;
    private Scanner scanner;
    Protocol p;

    private static boolean isAlived;
    public ClientSocket(){
        scanner = new Scanner(DashboardFrame.userText);
    }

    public void start(){
        final String ipAddress;
        final int port;
        if(def){
            ipAddress = "localhost";
            port = 5000;
        }else{           
            ipAddress = DashboardFrame.getIPAddress();
            System.out.println("IP Address: "+ipAddress);
            port = DashboardFrame.getPort();
            System.out.println("Port: "+port);
        }

        System.out.println(String.format("%s:%d Connecting to server...", ipAddress, port));

        try {
            socket = new Socket(ipAddress, port);
            System.out.println("Server is connected..");
            isAlived = true;
            objectStream = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setNickName(){
        if(socket != null) {
            runReceiver();
            System.out.println("Please set a nickname.");
            System.out.print(">>");
            String nick = DashboardFrame.getNickname();
            p = new Protocol(nick);
            System.out.println("Start chatting. To quit \"exit\" please enter exit!");
        }
    }

    private void runReceiver(){
        new Thread(new ClientReceiver(socket,this)).start();
    }

    public static void setMes(Protocol p, String text){
        if(!text.equals("quit")){
            System.out.println("SetMes() text: "+text);
            p.setMessage(text);
            try {
                objectStream.writeObject(p);
                objectStream.reset();
                objectStream.flush();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        else{
            System.out.println("Quit entered. The chat input has ended..");
        }                
    }

    static void close(){
        try {
            objectStream.close();
            System.out.println("ObjectOutputStream close completed");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void disconnect() {
        // TODO Auto-generated method stub
        
    }

    public void setIsAliveFalse() {
        isAlived = false;
    }

    public boolean checkIsAlive(boolean isAlive){
        if(isAlive) {
            return true;
        } else {
            return false;
        }
    }

    public void sendText(String text) {
        setMes(p, text);
    }

}
