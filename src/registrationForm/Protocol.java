package registrationForm;

import java.io.Serializable;

public class Protocol implements Serializable{
  public static final long serialVersionUID  = 1;

  public Protocol(String nickName){
      this.nickName = nickName;
  }

  public void setMessage(String message){
      this.message = message;
  }

  public String nickName;
  public String message;

}