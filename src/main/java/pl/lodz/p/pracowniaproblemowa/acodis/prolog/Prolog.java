package pl.lodz.p.pracowniaproblemowa.acodis.prolog;

import java.io.FileInputStream;
import java.util.logging.Logger;
import java.net.URLDecoder;

import alice.tuprolog.Theory;
import alice.tuprolog.Struct;
import alice.tuprolog.lib.JavaLibrary;

import pl.lodz.p.pracowniaproblemowa.acodis.datetime.DateAndTime;

public class Prolog
{
  private String libPath = null;
  private String accessPath = null;
  private Boolean theoryLoaded = false;
  private alice.tuprolog.Prolog prolog = new alice.tuprolog.Prolog();
  private Logger logger = Logger.getLogger(Prolog.class.getName());
  private JavaLibrary javaLibrary = (JavaLibrary)prolog.getLibrary("alice.tuprolog.lib.JavaLibrary");

  public Prolog() throws Exception
  {
    javaLibrary.register(new Struct("logger"),logger);
  }
  
  public String getLibPath()
  {
    return libPath;
  }

  public synchronized void setLibPath(String libPath)
  {
    this.libPath = libPath;
  }

  public String getAccessPath()
  {
    return accessPath;
  }

  public synchronized void setAccessPath(String accessPath)
  {
    this.accessPath = accessPath;
  }

  public synchronized void reread() throws Exception
  {
    theoryLoaded = false;
  }

  public synchronized String accessLevel(PassedContext passedContext) throws Exception
  {
    Struct passedContextTerm = new Struct("passedContext");
    javaLibrary.register(passedContextTerm, passedContext);

    Struct dateAndTimeTerm = new Struct("dateAndTime");
    javaLibrary.register(dateAndTimeTerm, new DateAndTime());

    if(!theoryLoaded)
    {
      logger.info("Loading access databases from "+libPath+" and "+accessPath);
      FileInputStream libStream = null;
      FileInputStream accessStream = null;
      try
      {
        libStream = new FileInputStream(URLDecoder.decode(Prolog.class.getResource(libPath).getPath(),"UTF-8"));
        accessStream = new FileInputStream(URLDecoder.decode(Prolog.class.getResource(accessPath).getPath(),"UTF-8"));
        prolog.setTheory(new Theory(libStream));
        prolog.addTheory(new Theory(accessStream));
      }
      finally
      {
        if(libStream != null)
        {
          libStream.close();
        }
        if(accessStream != null)
        {
          accessStream.close();
        }
      }
      logger.info(String.valueOf(prolog.getTheory()));
      theoryLoaded = true;
    }

    String highestLevel = "no";

    if(prolog.solve(new Struct("canAccess",new Struct("readAccess"))).isSuccess())
    {
      highestLevel = "read";
    }
    if(prolog.solve(new Struct("canAccess",new Struct("writeAccess"))).isSuccess())
    {
      highestLevel = "write";
    }
    if(prolog.solve(new Struct("canAccess",new Struct("specialAccess"))).isSuccess())
    {
      highestLevel = "special";
    }

    if(highestLevel.equals("special") && prolog.solve(new Struct("cannotAccess",new Struct("specialAccess"))).isSuccess())
    {
      highestLevel = "write";
    }
    if(highestLevel.equals("write") && prolog.solve(new Struct("cannotAccess",new Struct("writeAccess"))).isSuccess())
    {
      highestLevel = "read";
    }
    if(highestLevel.equals("read") && prolog.solve(new Struct("cannotAccess",new Struct("readAccess"))).isSuccess())
    {
      highestLevel = "no";
    }

    javaLibrary.unregister(dateAndTimeTerm);
    javaLibrary.unregister(passedContextTerm);

    return highestLevel;
  }
}
