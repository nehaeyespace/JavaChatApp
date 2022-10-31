package registrationForm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
 
public class LoginFrame extends JFrame implements ActionListener {
    
    Container container;
    JLabel userLabel = new JLabel("USERNAME");
    JLabel passwordLabel = new JLabel("PASSWORD");
    JTextField userTextField = new JTextField();
    JPasswordField passwordField = new JPasswordField();
    JButton loginButton = new JButton("LOGIN");
   
    JButton resetButton = new JButton("RESET");
    JCheckBox showPassword = new JCheckBox("Show Password");
 
 
    LoginFrame()
    {
       //Calling methods inside constructor.
        setLayoutManager();
        setLocationAndSize();
        // addComponentsToContainer();
 
    }
   public LoginFrame(Container contentPane) {
       //Calling methods inside constructor.
       container = contentPane;
    //    setLayoutManager();
       setLocationAndSize();
    //    addComponentsToContainer();
    }

  public void setLayoutManager()
   {
    //    container.setLayout(null);
   }
   public void setLocationAndSize()
   {
        //Setting location and Size of each components using setBounds() method.
        userLabel.setBounds(50,150,100,30);
        passwordLabel.setBounds(50,220,100,30);
        userTextField.setBounds(150,150,150,30);
        passwordField.setBounds(150,220,150,30);
        showPassword.setBounds(150,250,150,30);
        loginButton.setBounds(50,300,100,30);
        loginButton.addActionListener(this);
        resetButton.setBounds(200,300,100,30);
        resetButton.addActionListener(this);
        System.out.println("Setting bounds");
        container.add(userLabel);
        container.add(passwordLabel);
        container.add(userTextField);
        container.add(passwordField);
        container.add(showPassword);
        container.add(loginButton);
        container.add(resetButton);
        System.out.println("Adding comp");
        container.setVisible(true);
 
   }

   public void setLocationAndSizeDashboard()
   {
        //Setting location and Size of each components using setBounds() method.
        userLabel.setBounds(50,150,100,30);
        userTextField.setBounds(150,150,150,30);
       
        loginButton.setBounds(50,300,100,30);
        loginButton.addActionListener(this);
        resetButton.setBounds(200,300,100,30);
        resetButton.addActionListener(this);
        System.out.println("Setting bounds");
        container.add(userLabel);
        container.add(userTextField);

        container.add(loginButton);
        container.add(resetButton);
        System.out.println("Adding comp");
        container.setVisible(true);
        this.repaint();
        this.revalidate();
 
   }

 
    @Override
    public void actionPerformed(ActionEvent e) {
        //Coding Part of LOGIN button
        if (e.getSource() == loginButton) {
            String userText;
            char[] ch;
            userText = userTextField.getText();
            ch = passwordField.getPassword();
            String pwdText = new String(ch);
            System.out.println("username:"+userText);
            System.out.println("password:"+pwdText);
            MyConnection conn = new MyConnection();
            boolean result = conn.readfromconnection(userText, pwdText);
            if(result == true){
                System.out.println("FOUND");
                JOptionPane.showMessageDialog(
                    container, 
                    "The user is registered. Moving to the Chat Dashboard"
                );
                //some kind of establishing the client socket and server socket 
                //leading to dashboard
                container.removeAll();
                repaint();
                revalidate();
    
                setTitle("Dashboard");
                // Container cc =  new JPanel();
                DashboardFrame df = new DashboardFrame(this.container);
                // cc.add(lf);
                // c.add(cc);
                // c.set
                df.setVisible(true);
                container.repaint();
                container.revalidate();
                // ClientSocket client = new ClientSocket();
                // client.start();
                
            }else{
                //not in the database
                //either entered incorrect details
                //retry
                //else have not registered
                //else forgot the details
                System.out.println("DID NOT FIND");
                JOptionPane.showMessageDialog(
                    container, 
                    "The user is not registered",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );

            }
        }
                

        if (e.getSource() == resetButton) {
            userTextField.setText("");
            passwordField.setText("");
        }

        if (e.getSource() == showPassword) {
            if (showPassword.isSelected()) {
                passwordField.setEchoChar((char) 0);
            } else {
                passwordField.setEchoChar('*');
            }
        }
    }

 
    public static void main(String[] a){
        LoginFrame frame = new LoginFrame();
        frame.setVisible(true);
        frame.setBounds(10,10,370,600);
        frame.setResizable(false);
 
    }
}
