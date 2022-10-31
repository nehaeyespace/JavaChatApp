package registrationForm;
import javax.swing.*;
import java.awt.*;
import java.util.Calendar;
import java.util.Date;
import java.awt.event.*;
import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.*;

class Form extends JFrame implements ActionListener, java.awt.event.ActionListener {
 
        // Components of the Form
        private Container c;
        private JLabel title;
        private JLabel fname, lname, password;
        private JTextField tfname, tlname, tpassword;
        private JLabel eadd;
        private JTextField teadd;
        private JLabel gender;
        private JRadioButton male;
        private JRadioButton female;
        private ButtonGroup gengp;
        private JLabel dob;
        private Date today;
        private InfoDialog info;
        SimpleDateFormat df, df2, df3;
        JSpinner day, month, year;
        MyConnection db;
        JSpinner.DateEditor editor, editor2, editor3;

        private JLabel add;
        private JTextArea tadd;
        private JCheckBox term;
        private JButton submit;
        private JButton login;
        private JButton reset;
        private JTextArea tout;
        private JLabel res;
        private String PASSWORD_PATTERN;
        private Pattern pattern;

        // constructor, to initialize the components
        // with default values.
        public Form() {
            setTitle("Registration Form");
            setBounds(200, 90, 1000, 700);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setResizable(true);
     
            c = getContentPane();
            c.setLayout(null);
     
            title = new JLabel("Registration Form");
            title.setFont(new Font("Arial", Font.PLAIN, 30));
            title.setSize(300, 35);
            title.setLocation(300, 30);
            c.add(title);
     
            fname = new JLabel("Firstname");
            fname.setFont(new Font("Arial", Font.PLAIN, 20));
            fname.setSize(100, 20);
            fname.setLocation(100, 100);
            c.add(fname);
    
            lname = new JLabel("Lastname");
            lname.setFont(new Font("Arial", Font.PLAIN, 20));
            lname.setSize(100, 20);
            lname.setLocation(300, 100);
            c.add(lname);
     
            tfname = new JTextField();
            tfname.setFont(new Font("Arial", Font.PLAIN, 15));
            tfname.setSize(90, 25);
            tfname.setLocation(200, 98);
            c.add(tfname);
    
            tlname = new JTextField();
            tlname.setFont(new Font("Arial", Font.PLAIN, 15));
            tlname.setSize(90, 25);
            tlname.setLocation(400, 98);
            c.add(tlname);
     
            eadd = new JLabel("Email");
            eadd.setFont(new Font("Arial", Font.PLAIN, 20));
            eadd.setSize(100, 20);
            eadd.setLocation(100, 150);
            c.add(eadd);
     
            teadd = new JTextField();
            teadd.setFont(new Font("Arial", Font.PLAIN, 15));
            teadd.setSize(200, 25);
            teadd.setLocation(200, 148);
            c.add(teadd);
     
            gender = new JLabel("Gender");
            gender.setFont(new Font("Arial", Font.PLAIN, 20));
            gender.setSize(100, 20);
            gender.setLocation(100, 200);
            c.add(gender);
     
            male = new JRadioButton("Male");
            male.setFont(new Font("Arial", Font.PLAIN, 15));
            male.setSelected(true);
            male.setSize(75, 20);
            male.setLocation(200, 200);
            c.add(male);
     
            female = new JRadioButton("Female");
            female.setFont(new Font("Arial", Font.PLAIN, 15));
            female.setSelected(false);
            female.setSize(80, 20);
            female.setLocation(275, 200);
            c.add(female);
     
            gengp = new ButtonGroup();
            gengp.add(male);
            gengp.add(female);
     
            dob = new JLabel("Date Of Birth");
            dob.setFont(new Font("Arial", Font.PLAIN, 20));
            dob.setSize(150, 20);
            dob.setLocation(100, 250);
            c.add(dob);
            
            today = new Date();
            day = new JSpinner(new SpinnerDateModel(today, null, null, Calendar.DAY_OF_MONTH));
            editor = new JSpinner.DateEditor(day,"dd");
            day.setEditor(editor);
            day.setFont(new Font("Arial", Font.PLAIN, 15));
            day.setSize(50, 20);
            day.setLocation(250, 250);
            
            month = new JSpinner(new SpinnerDateModel(today, null, null, Calendar.MONTH));
            editor2 = new JSpinner.DateEditor(month, "MM");
            month.setEditor(editor2);
            month.setFont(new Font("Arial", Font.PLAIN, 15));
            month.setSize(50, 20);
            month.setLocation(300, 250);
            
            year = new JSpinner(new SpinnerDateModel(today, null, null, Calendar.YEAR));
            editor3 = new JSpinner.DateEditor(year, "YYYY");
            year.setEditor(editor3);
            year.setFont(new Font("Arial", Font.PLAIN, 15));
            year.setSize(60, 20);
            year.setLocation(350, 250);
            
            c.add(day);
            c.add(month);
            c.add(year);
    
            df = new SimpleDateFormat("dd");
            df2 = new SimpleDateFormat("MM");
            df3 = new SimpleDateFormat("YYYY");
            //String requiredDate = df.format(new Date()).toString();
            // System.out.println();
            // date.setText(date.getSelectedItem());
            // month.setText(month.getSelectedItem());
            // year.setText(year.getSelectedItem());
     
            add = new JLabel("Address");
            add.setFont(new Font("Arial", Font.PLAIN, 20));
            add.setSize(100, 20);
            add.setLocation(100, 300);
            c.add(add);
     
            tadd = new JTextArea();
            tadd.setFont(new Font("Arial", Font.PLAIN, 15));
            tadd.setSize(200, 75);
            tadd.setLocation(200, 298);
            tadd.setLineWrap(true);
            c.add(tadd);
    
            password = new JLabel("Create Password");
            password.setFont(new Font("Arial", Font.PLAIN, 20));
            password.setSize(200, 20);
            password.setLocation(100, 400);
            c.add(password);
     
            tpassword = new JTextField();
            tpassword.setFont(new Font("Arial", Font.PLAIN, 15));
            tpassword.setSize(200, 25);
            tpassword.setLocation(260, 398);
            c.add(tpassword);
            
            // digit + lowercase char + uppercase char + punctuation + symbol   
            PASSWORD_PATTERN =
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";
    
            pattern = Pattern.compile(PASSWORD_PATTERN);
            PASSWORD_PATTERN =
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";
    
            pattern = Pattern.compile(PASSWORD_PATTERN);
     
            term = new JCheckBox("Accept Terms And Conditions.");
            term.setFont(new Font("Arial", Font.PLAIN, 15));
            term.setSize(250, 20);
            term.setLocation(150, 450);
            c.add(term);
     
            submit = new JButton("SUBMIT");
            submit.setFont(new Font("Arial", Font.PLAIN, 15));
            submit.setSize(100, 20);
            submit.setLocation(150, 480);
            submit.addActionListener(this);
            c.add(submit);
     
            reset = new JButton("Reset");
            reset.setFont(new Font("Arial", Font.PLAIN, 15));
            reset.setSize(100, 20);
            reset.setLocation(270, 480);
            reset.addActionListener(this);
            c.add(reset);

            login = new JButton("Login");
            login.setFont(new Font("Arial", Font.PLAIN, 15));
            login.setSize(100, 20);
            login.setLocation(350, 480);
            login.addActionListener(this);
            c.add(login);
     
            tout = new JTextArea();
            tout.setFont(new Font("Arial", Font.PLAIN, 15));
            tout.setSize(300, 400);
            tout.setLocation(600, 100);
            tout.setLineWrap(true);
            tout.setEditable(false);
            c.add(tout);
     
            res = new JLabel("");
            res.setFont(new Font("Arial", Font.PLAIN, 20));
            res.setSize(500, 100);
            res.setLocation(100, 500);
            c.add(res);
     
            setVisible(true);
        }
     
        public void actionPerformed(ActionEvent e) {
            Connection con;
            info = new InfoDialog();
            if (e.getSource() == submit) {
                if (term.isSelected()) {
                    if(!tfname.getText().equals("") && tfname.getText().length() > 3 && !tlname.getText().equals("") && tlname.getText().length() > 3){
                        if( !teadd.getText().equals("") && teadd.getText().contains("@") && teadd.getText().length() > 10){
                            if(!tadd.getText().equals("") && teadd.getText().length() > 10){
                                Matcher matcher = pattern.matcher(tpassword.getText());
                                if(!tpassword.getText().equals("") && tpassword.getText().length() > 8 && matcher.matches()){
                                       
                                    String data1;
                                    String data
                                        = "First Name : "
                                        + tfname.getText() + "\n"
                                        + "Last Name : "
                                        + tlname.getText() + "\n"
                                        + "Email Address : "
                                        + teadd.getText() + "\n";
                                    if (male.isSelected())
                                        data1 = "Gender : Male"
                                                + "\n";
                                    else
                                        data1 = "Gender : Female"
                                                + "\n";
                                    String data2
                                        = "DOB : "
                                        + df.format(day.getValue())
                                        + "/" + df2.format(month.getValue())
                                        + "/" + df3.format(year.getValue())
                                        + "\n";
                                    String dateofbirth = df.format(day.getValue())
                                    + "/" + df2.format(month.getValue())
                                    + "/" + df3.format(year.getValue());
                                    SimpleDateFormat dateOfBirth = new SimpleDateFormat("dd/MM/YYYY");
                                    Date dateOfBirthParsed;
                                    try {
                                        dateOfBirthParsed = dateOfBirth.parse(dateofbirth);
                                        
                                        info.setDateOfBirth(dateOfBirthParsed);
                                    } catch (ParseException e1) {
                                        e1.printStackTrace();
                                    }
     
                                    String data3 = "Address : " + tadd.getText();
                                    String data4 = "Password: " + tpassword.getText();
                                    tout.setText(data + data1 + data2 + data3 + data4);
                                    tout.setEditable(false);
                                    
                    info.setFirstName(tfname.getText());
                    info.setLastName(tlname.getText());
                    info.setEmail(teadd.getText());
                    info.setPassword(tpassword.getText());

                    if (male.isSelected())
                    info.setgender("Male");
                    else
                    info.setgender("Female");
                    info.setAddress(tadd.getText());



                    db = new MyConnection(info);
                    db.createConnection();
                    res.setText("Registration Successful!");
                                    } else {
                                        res.setText("<html>Please enter the password greater than 8 characters <br>including - 1 digit, 1 lowercase character, 1 uppercase character and 1 symbol</html>");
                                    }
                                } else {
                                    res.setText("Please enter the Address");
                                }
                            } else {
                                res.setText("Please enter the Email address correctly");
                            }
                        } else {
                            res.setText("Please enter the Name");
                        }
                    } else {
                        tout.setText("");
                        res.setText("Please accept the terms & conditions.");
                    }
            } else if (e.getSource() == reset) {
                String def = "";
                tfname.setText(def);
                tlname.setText(def);
                tadd.setText(def);
                teadd.setText(def);
                res.setText(def);
                tout.setText(def);
                term.setSelected(false);
            }
        else if (e.getSource() == login) {
            c.removeAll();
            repaint();
            revalidate();
            setTitle("Login Form");
            LoginFrame lf = new LoginFrame(this.getContentPane());
            this.setVisible(true);
            repaint();
            revalidate();
        }
        }
    }
     
    public class App {
        public static void main(String[] args) throws Exception {
          Form f = new Form();
        }
}
