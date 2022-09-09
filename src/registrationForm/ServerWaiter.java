package registrationForm;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerWaiter implements Runnable {
  
  private SocketAdder server;
  private ServerSocket serverSocket;

  public ServerWaiter(SocketAdder server){
      this.server = server;
  }

  public void setServerSocket(ServerSocket serverSocket){
      this.serverSocket = serverSocket;
  }

  @Override
  public void run() {

      for(;!serverSocket.isClosed();){
          Socket socket = null;
        try {
          socket = serverSocket.accept();
          System.out.println("accept complete");
        } catch (IOException e) {
          e.printStackTrace();
        }
          if(socket != null){
              server.addSocket(socket);
          }
      }
  }
}
