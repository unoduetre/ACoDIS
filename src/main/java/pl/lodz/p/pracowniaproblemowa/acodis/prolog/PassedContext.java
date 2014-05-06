package pl.lodz.p.pracowniaproblemowa.acodis.prolog;

import javax.faces.context.FacesContext;

public class PassedContext
{
  FacesContext facesContext = null;
  String component = null;
  String category = null;
  String resource = null;

  public PassedContext(FacesContext facesContext, String component, String category, String resource)
  {
    this.facesContext = facesContext;
    this.component = component;
    this.category = category;
    this.resource = resource;
  }

  public String getComponent()
  {
    return component;
  }

  public String getCategory()
  {
    return category;
  }

  public String getResource()
  {
    return resource;
  }

  public FacesContext getFacesContext()
  {
    return facesContext;
  }
}

