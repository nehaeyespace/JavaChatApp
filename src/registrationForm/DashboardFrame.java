package registrationForm;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class DashboardFrame extends JFrame implements ActionListener {
    
  Container con;
  static JLabel userLabel = new JLabel("USERNAME");
  static JLabel textFieldLabel = new JLabel();
  static JTextField textField = new JTextField();
  static JLabel textFieldLabel2 = new JLabel();
  static JTextField textField2 = new JTextField();
  static JLabel textFieldLabel3 = new JLabel();
  static JTextField textField3= new JTextField();
  static JButton enterButton = new JButton("Enter");
  static JButton exitButton = new JButton("Exit");
  JButton setNicknameButton = new JButton("Set Nickname");
  static JTextField userTextField = new JTextField();
  JTextArea chatBox = new JTextArea();
  JButton sendButton = new JButton("Send");
  static String receivedText = "";
  static String userText = "";
  String textFieldEnteredByUser;
  static String ipAddress = null;
  static int port = 0;
  ClientSocket cs ;
  static boolean exitButtonPressed = false;
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
      userTextField.setEnabled(false);
      
      chatBox.setBounds(180,330,500,300);
      
      sendButton.setBounds(50,200,100,30);
      sendButton.addActionListener(this);

      exitButton.setBounds(720,200,100,30);
      exitButton.addActionListener(this);

      sendButton.setEnabled(false);
      exitButton.setEnabled(false);
      setNicknameButton.setEnabled(false);

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
      con.add(exitButton);
      // con.add(resetButton);
      System.out.println("Adding components");
      con.setVisible(true);

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
        String message = userTextField.getText();
        System.out.println(message);
        userText =  userText + "\n" + textField3.getText() + ":"  + userTextField.getText();
        cs.sendText(message);
        userTextField.setText("");
        chatBox.setText(userText);
        chatBox.setEditable(false);
      }

          
      if (e.getSource() == enterButton) {
        ipAddress = textField.getText();
        port = Integer.parseInt(textField2.getText());
        cs =new ClientSocket();
        cs.start();    
        System.out.println("Enter button pressed for ipaddress and port");
        JOptionPane.showMessageDialog(
                    con, 
                    "Server is connected"
        );
        enterButton.setEnabled(false);
        textField.setEditable(false);
        textField2.setEditable(false);
        sendButton.setEnabled(false);
        exitButton.setEnabled(false);
        setNicknameButton.setEnabled(true);
      }

      if (e.getSource() == setNicknameButton) {
        if(ipAddress!=null && port!= 0){
          userLabel.setText(textField3.getText());
        }
        cs.setNickName();
        System.out.println("Set Nickname button pressed for ipaddress and port");
        userTextField.setEnabled(true);
        textField3.setEditable(false);
        chatBox.setEditable(false);
        setNicknameButton.setEnabled(false);
        enterButton.setEnabled(false);
        sendButton.setEnabled(true);
        exitButton.setEnabled(true);        
        JOptionPane.showMessageDialog(
                    con, 
                    "Nickname is set to: "+textField3.getText()
        );
      }
      if (e.getSource() == exitButton) {
        exitButtonPressed = true;
        cs.setIsAliveFalse();
        ClientSocket.close();
        ClientReceiver.close();
        enterButton.setEnabled(true);
        sendButton.setEnabled(false);
        exitButton.setEnabled(false);
        setNicknameButton.setEnabled(false);
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
  public static String setMessage(String message) {
    receivedText = message;
    return receivedText;
  }
  public static String getText() {
    String text = "";
    // while(exitButtonPressed!=true){
    //   text = text + ;
    // } 
    return userTextField.getText();
  }

  public static void setText(String text) {
    userTextField.setText(text);
  }
  public static String getNickname() {
    return userLabel.getText();
  }
  public static boolean exitButtonisPressed() {
    return exitButtonPressed;
  }
}
//Exception handling, user interface , network request connection is down, or down