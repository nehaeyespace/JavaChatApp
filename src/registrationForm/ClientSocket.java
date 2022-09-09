package registrationForm;


import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;


public class ClientSocket implements Disconnector{
  private static final boolean def = false;

    private static Socket socket;
    private static ObjectOutputStream objectStream = null;
    private Scanner scanner;

    private static boolean isAlived;
    public ClientSocket(){
        scanner = new Scanner(System.in);
    }

    public void start(){
        connect();
        if(socket != null) {
            runReceiver();
            try {
                play();
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    private void runReceiver(){
        new Thread(new ClientReceiver(socket,this)).start();
    }

    public static void connect(){
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

    private void play() throws IOException{

        System.out.println("Please set a nickname.");
        System.out.print(">>");
        String nick = scanner.next();
        Protocol p = new Protocol(nick);

        System.out.println("Start chatting. To quit \"exit\" please enter exit!");
        for(;isAlived;){
            String message = scanner.nextLine();

            p.setMessage(message);

            objectStream.writeObject(p);
            objectStream.reset();
            objectStream.flush();

            if(message.equals("exit")){
                isAlived = false;
            }
        }
        System.out.println("Please enter the chat input has ended..");
        disconnect();
    }

    private void close(){
        try {
            objectStream.close();
            System.out.println("ObjectOutputStream close completed");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void disconnect() { close(); }

}
