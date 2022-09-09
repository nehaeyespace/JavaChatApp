package registrationForm;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;

public class ServerSockett implements Broadcast, SocketAdder{
    private static final int Max_Client_Number = 20;

    private ServerSocket serverSocket;

    private ServerWaiter waiter;
    private HashSet<ServerReceiver> receivers;
    

    public ServerSockett(){
        waiter = new ServerWaiter(this);
        receivers = new HashSet<>();
    }

    public void start(int port){
        createServerSocket(port);
        new Thread(waiter).start();
    }

    public void start(){
        start(5000);
    }

    private void createServerSocket(int port){
        try {
            serverSocket = new ServerSocket(port);
            waiter.setServerSocket(serverSocket);
            System.out.printf("Start server! (port number: %d)\n", port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addSocket(Socket socket) {
        ServerReceiver receiver = new ServerReceiver(this, socket);
        receivers.add(receiver);
        new Thread(receiver).start();
        displayCurrentUserCount();
        
    }

    public void displayCurrentUserCount(){
        System.out.println(String.format("Number of currently connected users: %d", receivers.size()));
    }

    @Override
    public void broadcast(String message) {
        for(ServerReceiver receiver : receivers){
            if(receiver != null){
                receiver.println(message);
            }
        }
    }

    @Override
    public void notiLeftUser(ServerReceiver receiver) {
        receivers.remove(receiver);
        displayCurrentUserCount();
    }

    @Override
    public int getUserCount() {
        return receivers.size();
    }


}