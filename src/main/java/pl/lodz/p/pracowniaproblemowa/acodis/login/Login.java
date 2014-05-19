package pl.lodz.p.pracowniaproblemowa.acodis.login;

import java.io.Serializable;

public class Login implements Serializable
{
  private String username = null;
  private String password = null;

  public String getUsername()
  {
    return username;
  }

  public void setUsername(String username)
  {
    this.username = username;
  }

  public String getPassword()
  {
    return "";
  }

  public String getRealPassword()
  {
    return password;
  }

  public void setPassword(String password)
  {
    this.password = password;
  }

  public String logout()
  {
    System.err.println("############################# logout ################################");
    username = null;
    password = null;
    return "index";
  }
}

