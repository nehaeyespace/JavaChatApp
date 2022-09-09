package registrationForm;

import java.util.Date;

import javax.swing.JFrame;

public class InfoDialog extends JFrame{
    public String fname, lname, password, email, gender, address;
    public Date dateOfBirth;

    public void setFirstName(String fname){
        this.fname = fname;
    }
    public void setLastName(String lname){
        this.lname = lname;
    }
    
    public void setPassword(String password){
        this.password = password;
    }
    
    public void setEmail(String email){
        this.email = email;
    }
    
    public void setgender(String gender){
        this.gender = gender;
    }
    
    public void setAddress(String address){
        this.address = address;
    }

    public void setDateOfBirth(Date dateOfbirth){
        this.dateOfBirth = dateOfbirth;
    }

    public String getFirstName(){
        return fname;
    }

    public String getLastName(){
        return lname;
    }
    
    public String getEmail(){
        return email;
    }
    
    public String getGender(){
        return gender;
    }

    public String getAddress() {
        return address;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }
    
    public String getPassword(){
        return password;
    }

}