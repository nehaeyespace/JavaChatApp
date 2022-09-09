package registrationForm;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

public class ServerReceiver implements Runnable{
    private Broadcast server;
    private Socket socket;

    private PrintWriter printWriter;
    private ObjectInputStream objectInputStream;

    private String nickName = null;


    public ServerReceiver(Broadcast serverSockett, Socket socket) {
        this.server = serverSockett;
        this.socket = socket;
        try {
            printWriter = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void println(String message) {
        printWriter.println(message);
    }

    @Override
    public void run() {
        String message;
        Object obj;
        boolean isConnected = true;
        try {
            objectInputStream = new ObjectInputStream(socket.getInputStream());

            server.broadcast(String.format("One new guest has entered. (Total %d connected)", server.getUserCount()));

            for (; isConnected && (obj = objectInputStream.readObject()) != null; ) {
                Protocol p = (Protocol) obj;
                message = p.message;
                if (message.equals("exit")) {
                    isConnected = false;
                    nickName = p.nickName;
                    socket.close();
                    break;
                } else if(!message.isEmpty()){
                    nickName = p.nickName;
                    message = String.format("[%s]: %s has left.", nickName, message);
                    System.out.println(message);
                    server.broadcast(message);
                }
              }
              close();
        }  catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }finally {
            println("disconnect");
            message = String.format("[%s] has left. (Total %d connected)", nickName, server.getUserCount());
            server.broadcast(message);
            System.out.println(message);
            server.notiLeftUser(this);
        }
    }

    private void close() throws IOException{
        printWriter.close();
        System.out.println("PrintWriter close completion");
        objectInputStream.close();
        System.out.println("ObjectInputStream close completion");
    }
}
