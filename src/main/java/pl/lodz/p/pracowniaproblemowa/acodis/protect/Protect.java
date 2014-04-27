package pl.lodz.p.pracowniaproblemowa.acodis.protect;

import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.faces.context.FacesContext;
import javax.faces.context.ExternalContext;

import pl.lodz.p.pracowniaproblemowa.acodis.prolog.Prolog;
import pl.lodz.p.pracowniaproblemowa.acodis.prolog.PassedContext;

public class Protect
{

  private Prolog prolog = null;

  private Map<String,Map<String,Map<String, String>>> componentsAccess = new HashMap<String, Map<String,Map<String, String>>>();

  private FacesContext facesContext = FacesContext.getCurrentInstance();
  private ExternalContext externalContext = facesContext.getExternalContext();
  private HttpServletRequest request = (HttpServletRequest)externalContext.getRequest();

  public Prolog getProlog()
  {
    return prolog;
  }

  public void setProlog(Prolog prolog)
  {
    this.prolog = prolog;
  }

  private void determineAccess(String component, String category, String resource) throws Exception
  {
    assureExistence(component, category, resource);
    componentsAccess.get(component).get(category).put(resource,prolog.accessLevel(new PassedContext(facesContext, component, category, resource)));

    Map<String, String> parameters = externalContext.getRequestParameterMap();
    for(String parameterName : parameters.keySet())
    {
      if(parameterName.matches("\\A.+\\..+\\..+\\z"))
      {
        String[] three = parameterName.split("\\.");
        assureExistence(three[0], three[1], three[2]);
        componentsAccess.get(three[0]).get(three[1]).put(three[2],parameters.get(parameterName));
      }
    }
  }

  private void assureExistence(String component, String category, String resource)
  {
    Map<String, Map<String, String>> categoriesAccess = componentsAccess.get(component);
    if(categoriesAccess == null)
    {
      categoriesAccess = new HashMap<String, Map<String,String>>();
      componentsAccess.put(component, categoriesAccess);
    }

    Map<String, String> resourcesAccess = categoriesAccess.get(category);
    if(resourcesAccess == null)
    {
      resourcesAccess = new HashMap<String, String>();
      categoriesAccess.put(category, resourcesAccess);
    }

    String access = resourcesAccess.get(resource);
    if(access == null)
    {
      access = "no";
      resourcesAccess.put(resource, "access");
    }
  }

  public Boolean hasSpecialAccess(String component, String category, String resource) throws Exception
  {
    determineAccess(component, category, resource);
    return componentsAccess.get(component).get(category).get(resource).equals("special");
  }

  public Boolean hasWriteAccess(String component, String category, String resource) throws Exception
  {
    determineAccess(component, category, resource);
    return componentsAccess.get(component).get(category).get(resource).equals("write");
  }

  public Boolean hasReadAccess(String component, String category, String resource) throws Exception
  {
    determineAccess(component, category, resource);
    return componentsAccess.get(component).get(category).get(resource).equals("read");
  }
}

