
package pl.lodz.p.pracowniaproblemowa.acodis.wiki;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;

/**
 *
 * @author lukkot
 */
public class WikiDispatcherBean {
  private String destination = "";
  private String title = "";
  
  ////////////////////////////////////////////////////////////////////////////
  // KONSTRUKTORY
  public WikiDispatcherBean() {
    Logger.getLogger(WikiDispatcherBean.class.getName()).log(Level.WARNING, "Dispatcher START!");
    
    
    FacesContext fc = FacesContext.getCurrentInstance();
    Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
    String outsideDestination = params.get("destination");
    
    if(outsideDestination != null) {
      switch(outsideDestination) {
        case WikiUtils.WIKIPAGE_READER:
          destination = "reader.xhtml";
          title = params.getOrDefault("page", "");
          break;

        case WikiUtils.WIKIPAGE_EDITOR:
          destination = "editorWrapper.xhtml";
          title = "Edycja: " + params.getOrDefault("page", "Nowa strona");
          break;

        default:
          Logger.getLogger(WikiDispatcherBean.class.getName()).log(Level.WARNING, ("Brak poprawnej destynacji: " + outsideDestination));
          destination = "";
      }
    } else {
      destination = "";
    }
    
    Logger.getLogger(WikiDispatcherBean.class.getName()).log(Level.WARNING, "Dispatcher END!");
  }
  
  ////////////////////////////////////////////////////////////////////////////
  // AKCESORY

  public String getDestination() {
    return destination;
  }

  public void setDestination(String destination) {
    this.destination = destination;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }
}
