package pl.lodz.p.pracowniaproblemowa.acodis.filebrowser;

import java.io.File;
import java.io.Serializable;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;


public class FileBrowserBean extends FileBrowserEntry implements Serializable {
  
  private static final long serialVersionUID = 1419920132613404300L;
  
  public FileBrowserBean() {
    super(new File(System.getProperty("user.home")));
    FacesContext context = FacesContext.getCurrentInstance();
    ExternalContext extContext = context.getExternalContext();
    Map<String, String> attributes = extContext.getRequestParameterMap();
    
    if(attributes.containsKey("resource")) {
      setResource((String) attributes.get("resource"));
    }
  }
  
}
