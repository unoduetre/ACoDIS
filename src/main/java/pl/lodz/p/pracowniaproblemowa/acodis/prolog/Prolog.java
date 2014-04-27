package pl.lodz.p.pracowniaproblemowa.acodis.prolog;

import java.util.Set;
import java.util.HashSet;

import alice.tuprolog.Theory;
import alice.tuprolog.SolveInfo;
import alice.tuprolog.Term;
import alice.tuprolog.Struct;
import alice.tuprolog.Var;


public class Prolog
{
  String theoryPath = null;
  alice.tuprolog.Prolog prolog = new alice.tuprolog.Prolog();
  
  public Prolog()
  {
  }

  public String getTheoryPath()
  {
    return theoryPath;
  }

  public void setTheoryPath(String theoryPath) throws Exception
  {
    this.theoryPath = theoryPath;
    prolog.setTheory(new Theory(Prolog.class.getResourceAsStream(theoryPath)));
  }

  public String accessLevel(PassedContext passedContext) throws Exception
  {
    String highestLevel = "no";
    Term passedContextTerm = passedContext.toTerm();

    if(prolog.solve(new Struct("canAccess",passedContextTerm,new Struct("readAccess"))).isSuccess())
    {
      highestLevel = "read";
    }
    if(prolog.solve(new Struct("canAccess",passedContextTerm,new Struct("writeAccess"))).isSuccess())
    {
      highestLevel = "write";
    }
    if(prolog.solve(new Struct("canAccess",passedContextTerm,new Struct("specialAccess"))).isSuccess())
    {
      highestLevel = "special";
    }

    if(highestLevel.equals("special") && prolog.solve(new Struct("cannotAccess",passedContextTerm,new Struct("specialAccess"))).isSuccess())
    {
      highestLevel = "write";
    }
    if(highestLevel.equals("write") && prolog.solve(new Struct("cannotAccess",passedContextTerm,new Struct("writeAccess"))).isSuccess())
    {
      highestLevel = "read";
    }
    if(highestLevel.equals("read") && prolog.solve(new Struct("cannotAccess",passedContextTerm,new Struct("readAccess"))).isSuccess())
    {
      highestLevel = "no";
    }

    return highestLevel;
  }
}
