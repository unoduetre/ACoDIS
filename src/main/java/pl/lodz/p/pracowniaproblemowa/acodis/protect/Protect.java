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

public class Protect implements Serializable
{
  private Logger logger = Logger.getLogger(Protect.class.getName());
  private Map<String, Map<String, String>> componentsTypesAccessLevels = new HashMap<String, Map<String, String>>();
  private PhaseId previousPhase = null;
  private Prolog prolog = null;

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

    Map<String, String> componentsAccessLevels = componentsTypesAccessLevels.get(componentType);
    if(componentsAccessLevels == null)
    {
      componentsAccessLevels = new HashMap<String, String>();
      componentsTypesAccessLevels.put(componentType, componentsAccessLevels);
    }
    componentsAccessLevels.put(componentId,accessLevel);

    logger.info("ACCESS("+componentType+" "+componentId+" "+resourceType+" "+resourceId+"): "+accessLevel);  
    return accessLevel;
  }

  public String getAccessLevel(String componentType, String componentId, String resourceType, String resourceId) throws Exception
  {
    if(componentId == null) // gdy wczytywane przez UIDebug componentId jest pusty
    {
      return "no";
    }
    PhaseId currentPhase = FacesContext.getCurrentInstance().getCurrentPhaseId();
    if(currentPhase == PhaseId.RENDER_RESPONSE && previousPhase != PhaseId.RENDER_RESPONSE)
    {
      previousPhase = currentPhase;
      return findAccessLevel(componentType, componentId, resourceType, resourceId);
    }
    previousPhase = currentPhase;
    
    Map<String, String> componentsAccessLevels = componentsTypesAccessLevels.get(componentType);
    if(componentsAccessLevels == null)
    {
      return findAccessLevel(componentType, componentId, resourceType, resourceId);
    }
    String accessLevel = componentsAccessLevels.get(componentId);
    if(accessLevel == null)
    {
      return findAccessLevel(componentType, componentId, resourceType, resourceId);
    }
    logger.info("ACCESS("+componentType+" "+componentId+" "+resourceType+" "+resourceId+"): "+accessLevel);  
    return accessLevel;
  }
}

