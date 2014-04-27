package pl.lodz.p.pracowniaproblemowa.acodis.protect;

import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.faces.context.FacesContext;
import javax.faces.context.ExternalContext;


public class Protect
{
  private Map<String,String> accessMap = new HashMap<String, String>();

  private FacesContext facesContext = FacesContext.getCurrentInstance();
  private ExternalContext externalContext = facesContext.getExternalContext();
  private HttpServletRequest request = (HttpServletRequest)externalContext.getRequest();

  public Protect()
  {
    accessMap.putAll(externalContext.getRequestParameterMap());
  }

  public void determineAccess(String component)
  {
    if(accessMap.get(component) == null)
    {
      accessMap.put(component, "no");
    }
  }

  public Boolean hasSpecialAccess(String component)
  {
    determineAccess(component);
    return accessMap.get(component).equals("special");
  }

  public Boolean hasWriteAccess(String component)
  {
    determineAccess(component);
    return accessMap.get(component).equals("write");
  }

  public Boolean hasReadAccess(String component)
  {
    determineAccess(component);
    return accessMap.get(component).equals("read");
  }

  public Collection<String> split(String list)
  {
    return Arrays.asList(list.split(" "));
  }
}

