package pl.lodz.p.pracowniaproblemowa.acodis.security;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.logging.Logger;
import java.net.URLDecoder;

import pl.lodz.p.pracowniaproblemowa.acodis.prolog.Prolog;

public class Security
{
  private Logger logger = Logger.getLogger(Security.class.getName());
  private String code = null;
  private Prolog prolog = null;

  public Prolog getProlog()
  {
    return prolog;
  }

  public void setProlog(Prolog prolog) throws Exception
  {
    this.prolog = prolog;
    BufferedReader reader = null;
    try
    {
      reader = new BufferedReader(new FileReader(URLDecoder.decode(Security.class.getResource(prolog.getAccessPath()).getPath(),"UTF-8")));
      StringBuffer buffer = new StringBuffer();
      String line = null;
      while((line=reader.readLine()) != null)
      {
        buffer.append(line);
        buffer.append('\n');
      }
      code = buffer.toString();
    }
    finally
    {
      if(reader != null)
      {
        reader.close();
      }
    }
  }

  public String getCode()
  {
    return code;
  }

  public void setCode(String code)
  {
    this.code = code;
  }

  public void save() throws Exception
  {
    PrintWriter writer = null;
    try
    {
      writer = new PrintWriter(URLDecoder.decode(Security.class.getResource(prolog.getAccessPath()).getPath(),"UTF-8"));
      writer.print(code);
      writer.close();
      writer = null;
      prolog.reread();
    }
    finally
    {
      if(writer != null)
      {
        writer.close();
      }
    }
  }
}

