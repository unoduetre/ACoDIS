package pl.lodz.p.pracowniaproblemowa.acodis.protect;

import java.util.Map;
import java.util.HashMap;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.faces.context.FacesContext;
import javax.faces.context.ExternalContext;
import javax.faces.event.PhaseId;

import pl.lodz.p.pracowniaproblemowa.acodis.prolog.Prolog;
import pl.lodz.p.pracowniaproblemowa.acodis.prolog.PassedContext;

public class Protect
{
  private Logger logger = Logger.getLogger(Protect.class.getName());
  private Map<String, Map<String, Map<String, String>>> componentsAccessLevels = new HashMap<String, Map<String, Map<String, String>>>();
  private Prolog prolog = null;

  public Protect()
  {
    System.err.println("PROTECT");
  }

  public Prolog getProlog()
  {
    return prolog;
  }

  public void setProlog(Prolog prolog)
  {
    this.prolog = prolog;
  }

  private String findAccessLevel(String component, String category, String resource) throws Exception
  {
    String accessLevel = prolog.accessLevel(new PassedContext(FacesContext.getCurrentInstance(), component, category, resource));

    String accessParameter = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(component+"."+category+"."+resource);
    if(accessParameter != null)
    {
      accessLevel = accessParameter;
    }

    Map<String, Map<String, String>> categoriesAccessLevels = componentsAccessLevels.get(component);
    if(categoriesAccessLevels == null)
    {
      categoriesAccessLevels = new HashMap<String, Map<String, String>>();
      componentsAccessLevels.put(component, categoriesAccessLevels);
    }
    Map<String, String> resourcesAccessLevels = categoriesAccessLevels.get(category);
    if(resourcesAccessLevels == null)
    {
      resourcesAccessLevels = new HashMap<String, String>();
      categoriesAccessLevels.put(category, resourcesAccessLevels);
    }
    resourcesAccessLevels.put(resource,accessLevel);

    logger.info("UUUACCESS LEVEL: "+accessLevel);
    return accessLevel;
  }

  public String getAccessLevel(String component, String category, String resource) throws Exception
  {

    Map<String, Map<String, String>> categoriesAccessLevels = componentsAccessLevels.get(component);
    if(categoriesAccessLevels == null)
    {
      return findAccessLevel(component, category, resource);
    }
    Map<String, String> resourcesAccessLevels = categoriesAccessLevels.get(category);
    if(resourcesAccessLevels == null)
    {
      return findAccessLevel(component, category, resource);
    }
    String accessLevel = resourcesAccessLevels.get(resource);
    if(accessLevel == null)
    {
      return findAccessLevel(component, category, resource);
    }
    logger.info("ACCESS LEVEL: "+accessLevel);

    System.err.println(FacesContext.getCurrentInstance().getCurrentPhaseId().getName());
    if(FacesContext.getCurrentInstance().getCurrentPhaseId() == PhaseId.RENDER_RESPONSE)
    {
      return findAccessLevel(component, category, resource);
    }
    else
    {
      return accessLevel;
    }
  }
}

