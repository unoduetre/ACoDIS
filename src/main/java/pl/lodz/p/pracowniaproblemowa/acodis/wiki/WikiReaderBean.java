
package pl.lodz.p.pracowniaproblemowa.acodis.wiki;

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
  
  public WikiReaderBean() {
    FacesContext fc = FacesContext.getCurrentInstance();
    Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
    category = WikiUtils.urlToHuman( params.get("category") );
    page = WikiUtils.urlToHuman( params.get("page") );
    
    title = page;
    body = WikiUtils.getPage(category, page);
  }
  
  ////////////////////////////////////////////////////////////////////////////
  // Publiczne
  public void getPage(ActionEvent event) {
        Logger.getLogger(WikiReaderBean.class.getName()).log(Level.WARNING, ("page: " + page));
  }
  
  public String getCategoryForUrl() {
    return WikiUtils.humanToUrl( category );
  }
  
  public String getPageForUrl() {
    return WikiUtils.humanToUrl( page );
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

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }
  
}
