package registrationForm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;


public class ClientReceiver implements Runnable{
  
  private Socket socket;

    private static BufferedReader br;

    private Disconnector dis;

    public ClientReceiver(Socket socket, Disconnector dis){
        this.socket = socket;
        this.dis = dis;
    }

    @Override
    public void run() {
        createStream();
        if(DashboardFrame.getText().equals("quit")){
            System.out.println("Chat output has ended.");
            System.out.println("The connection to the server has been safely terminated..");
            dis.disconnect();
        }else {
            System.out.print(">>");
            System.out.println("Clientreceiver () : "+DashboardFrame.getText());
            DashboardFrame.setMessage("");
        }
    }

    private void createStream(){
        try {
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void close(){
        try {
            br.close();
            System.out.println("BufferedReader close complete");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
