package registrationForm;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DashboardFrame extends JFrame implements ActionListener {
    
  Container con;
  JLabel userLabel = new JLabel("USERNAME");
  static JLabel textFieldLabel = new JLabel();
  static JTextField textField = new JTextField();
  static JLabel textFieldLabel2 = new JLabel();
  static JTextField textField2 = new JTextField();
  static JLabel textFieldLabel3 = new JLabel();
  static JTextField textField3= new JTextField();
  static JButton enterButton = new JButton("Enter");

  JButton setNicknameButton = new JButton("Set Nickname");
  JTextField userTextField = new JTextField();
  JTextField chatBox = new JTextField();
  JButton sendButton = new JButton("Send");
  String receivedText;
  String textFieldEntyeredByUser;
  static String ipAddress = null;
  static int port = 0;
 
  // JButton resetButton = new JButton("RESET");


  DashboardFrame()
  {
     //Calling methods inside constructor.
      setLayoutManager();
      setLocationAndSizeDashboard();
      // addComponentsToContainer();

  }
 public DashboardFrame(Container contentPane) {
     //Calling methods inside constructor.
     con = contentPane;
     setLayoutManager();
     setLocationAndSizeDashboard();
  //    addComponentsToContainer();
  }

public void setLayoutManager()
 {
     con.setLayout(null);
 }

 public void setLocationAndSizeDashboard()
 {
      //Setting location and Size of each components using setBounds() method.

      textField.setBounds(400,30,200,30);
      textFieldLabel.setBounds(20,30,350,30);
      textFieldLabel.setText("Enter the server address to connect to");

      textField2.setBounds(400,70,200,30);
      textFieldLabel2.setBounds(20,70,350,30);
      textFieldLabel2.setText("Enter the port number of the server to connect to");

      enterButton.setBounds(600,70,100,30);
      enterButton.addActionListener(this);

      textField3.setBounds(400,120,200,30);
      textFieldLabel3.setBounds(20,120,350,30);
      textFieldLabel3.setText("Enter the nickname of the user");

      setNicknameButton.setBounds(600,120,150,30);
      setNicknameButton.addActionListener(this);

      userLabel.setBounds(50,180,100,30);
      userTextField.setBounds(180,150,500,100);
      
      chatBox.setBounds(150,330,500,300);
      
      sendButton.setBounds(50,200,100,30);
      sendButton.addActionListener(this);

      System.out.println("Setting bounds for username and textfield");
      con.add(textField);
      con.add(textFieldLabel);
      con.add(textField2);
      con.add(textFieldLabel2);
      con.add(textField3);
      con.add(textFieldLabel3);
      con.add(setNicknameButton);
      con.add(enterButton);
      con.add(userLabel);
      con.add(userTextField);
      con.add(chatBox);
      con.add(sendButton);
      // con.add(resetButton);
      System.out.println("Adding components");
      con.setVisible(true);
      // this.repaint();
      // this.revalidate();

 }

 public static String getIPAddress(){
  return ipAddress;
}

public static int getPort(){
  return port;
}


  @Override
  public void actionPerformed(ActionEvent e) {
      //Coding Part of LOGIN button
      if (e.getSource() == sendButton) {
      }
          
          
              
          // }else{
              //not in the database
              //either entered incorrect details
              //retry
              //else have not registered
              //else forgot the details
              // System.out.println("DID NOT FIND");
          
      if (e.getSource() == enterButton) {
        ipAddress = textField.getText();
        port = Integer.parseInt(textField2.getText());
        ClientSocket.connect();
        
        System.out.println("Enter button pressed for ipaddress and port");
        try {
          wait(10);
        } catch (InterruptedException e1) {
          // TODO Auto-generated catch block
          e1.printStackTrace();
        }
        textField.setText("");
        textField2.setText("");
        
      }

      if (e.getSource() == setNicknameButton) {
        if(ipAddress!=null && port!= 0){
          userLabel.setText(textField3.getText());
        }
        System.out.println("Set Nickname button pressed for ipaddress and port");
        String userText;
          char[] ch;
          userText = userTextField.getText();
          // receivedText.
          userText = userText + receivedText;
          chatBox.setText(userText);


          System.out.println("username:"+userText);

          // MyConnection conn = new MyConnection();
          // setLocationAndSizeDashboard();
          ClientSocket client = new ClientSocket();
          client.start();
      }
    }

  public static void main(String[] args){
      DashboardFrame frame = new DashboardFrame();
      frame.setVisible(true);
      frame.setBounds(10,10,370,600);
      frame.setResizable(false);
      frame.setLocationAndSizeDashboard();
      frame.repaint();
      frame.revalidate();

  }
}
