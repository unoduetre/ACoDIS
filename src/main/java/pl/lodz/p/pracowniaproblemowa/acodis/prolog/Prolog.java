package pl.lodz.p.pracowniaproblemowa.acodis.prolog;

import alice.tuprolog.Theory;
import alice.tuprolog.Struct;
import alice.tuprolog.lib.JavaLibrary;


public class Prolog
{
  String theoryPath = null;
  alice.tuprolog.Prolog prolog = new alice.tuprolog.Prolog();
  
  public String getTheoryPath()
  {
    return theoryPath;
  }

  public void setTheoryPath(String theoryPath) throws Exception
  {
    this.theoryPath = theoryPath;
    prolog.setTheory(new Theory(Prolog.class.getResourceAsStream(theoryPath)));
  }

  public synchronized String accessLevel(PassedContext passedContext) throws Exception
  {
    JavaLibrary javaLibrary = (JavaLibrary)prolog.getLibrary("alice.tuprolog.lib.JavaLibrary");
    Struct passedContextTerm = new Struct("passedContext");

    javaLibrary.register(passedContextTerm, passedContext);

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

    javaLibrary.unregister(passedContextTerm);

    return highestLevel;
  }
}
