package pl.lodz.p.pracowniaproblemowa.acodis.prolog;

import javax.faces.context.FacesContext;

public class PassedContext
{
  FacesContext facesContext = null;
  String componentType = null;
  String componentId = null;
  String resourceType = null;
  String resourceId = null;

  public PassedContext(FacesContext facesContext, String componentType, String componentId, String resourceType, String resourceId)
  {
    this.facesContext = facesContext;
    this.componentType = componentType;
    this.componentId = componentId;
    this.resourceType = resourceType;
    this.resourceId = resourceId;
  }

  public String getComponentType()
  {
    return componentType;
  }

  public String getComponentId()
  {
    return componentId;
  }

  public String getResourceType()
  {
    return resourceType;
  }

  public String getResourceId()
  {
    return resourceId;
  }

  public FacesContext getFacesContext()
  {
    return facesContext;
  }
}

