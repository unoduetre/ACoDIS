package pl.lodz.p.pracowniaproblemowa.acodis.protect;

import java.util.Map;
import java.util.HashMap;
import java.util.logging.Logger;
import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.faces.context.FacesContext;
import javax.faces.context.ExternalContext;
import javax.faces.event.PhaseId;

import pl.lodz.p.pracowniaproblemowa.acodis.prolog.Prolog;
import pl.lodz.p.pracowniaproblemowa.acodis.prolog.PassedContext;
import pl.lodz.p.pracowniaproblemowa.acodis.login.Login;

public class Protect implements Serializable
{
  private Logger logger = Logger.getLogger(Protect.class.getName());
  protected Map<String, Map<String, String>> componentTypesAccessLevels = new HashMap<String, Map<String, String>>();
  protected PhaseId previousPhase = null;
  protected Prolog prolog = null;
  protected Login login = null;

  public Login getLogin()
  {
    return login;
  }

  public void setLogin(Login login)
  {
    this.login = login;
  }

  public Prolog getProlog()
  {
    return prolog;
  }

  public void setProlog(Prolog prolog)
  {
    this.prolog = prolog;
  }

  private String findAccessLevel(String componentType, String componentId, String resourceType, String resourceId) throws Exception
  {
    String accessLevel = prolog.accessLevel(new PassedContext(FacesContext.getCurrentInstance(), componentType, componentId, resourceType, resourceId));

    String accessParameter = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(componentId);
    if(accessParameter != null)
    {
      accessLevel = accessParameter;
    }

    Map<String, String> componentAccessLevels = componentTypesAccessLevels.get(componentType);
    if(componentAccessLevels == null)
    {
      componentAccessLevels = new HashMap<String, String>();
      componentTypesAccessLevels.put(componentType, componentAccessLevels);
    }
    componentAccessLevels.put(componentId,accessLevel);

    logger.info("PROLOG ACCESS("+login.getUsername()+" -> " +componentType+" "+componentId+" "+resourceType+" "+resourceId+"): "+accessLevel);  
    return accessLevel;
  }

  public String getAccessLevel(String componentType, String componentId, String resourceType, String resourceId) throws Exception
  {
    if(componentId == null || componentId.equals("")) // gdy wczytywane przez UIDebug componentId jest pusty
    {
      return "no";
    }

    PhaseId currentPhase = FacesContext.getCurrentInstance().getCurrentPhaseId();
    if(previousPhase != null && currentPhase.equals(PhaseId.RENDER_RESPONSE) && !previousPhase.equals(PhaseId.RENDER_RESPONSE))
    {
      logger.info("Emptying access cache");
      componentTypesAccessLevels = new HashMap<String, Map<String, String>>();
    }
    previousPhase = currentPhase;
    
    Map<String, String> componentAccessLevels = componentTypesAccessLevels.get(componentType);
    if(componentAccessLevels == null)
    {
      return findAccessLevel(componentType, componentId, resourceType, resourceId);
    }
    String accessLevel = componentAccessLevels.get(componentId);
    if(accessLevel == null)
    {
      return findAccessLevel(componentType, componentId, resourceType, resourceId);
    }
    logger.info("CACHED ACCESS("+login.getUsername()+" -> "+componentType+" "+componentId+" "+resourceType+" "+resourceId+"): "+accessLevel);  
    return accessLevel;
  }
}

