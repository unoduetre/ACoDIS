
package pl.lodz.p.pracowniaproblemowa.acodis.wiki;

import java.io.File;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

/**
 *
 * @author lukkot
 */
public class WikiReaderBean {
  ////////////////////////////////////////////////////////////////////////////
  // ZMIENNE
  private String category = "";
  private String page = "";
  private String title = "Title";
  private String body = "Body";
  
  ////////////////////////////////////////////////////////////////////////////
  // Publiczne
  public void getPage(ActionEvent event) {
        Logger.getLogger(WikiReaderBean.class.getName()).log(Level.WARNING, "page: " + page);
  }
  
  ////////////////////////////////////////////////////////////////////////////
  // AKCESORY

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getBody() {
    FacesContext fc = FacesContext.getCurrentInstance();
    Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
    category = params.get("category"); 
    page = params.get("page");
    
    body = WikiUtils.getPage(category, page);
    
    return body;
  }
  
  public void setBody(String body) {
    this.body = body;
  }

  public String getPage() {
    return page;
  }

  public void setPage(String page) {
    this.page = page;
  }
  
}
