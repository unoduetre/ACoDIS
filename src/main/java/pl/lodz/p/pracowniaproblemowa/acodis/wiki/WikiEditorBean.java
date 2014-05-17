
package pl.lodz.p.pracowniaproblemowa.acodis.wiki;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

/**
 *
 * @author lukkot
 */
public class WikiEditorBean {
  ////////////////////////////////////////////////////////////////////////////
  // ZMIENNE
  private String title = "";
  private String category = "";
  private String page = "";
  private String editorValue = "";
  
  private String inputCategory = "";
  private String inputTitle = "";
  
  private boolean isNewPage = true;

  public WikiEditorBean() {
    // Utworzenie miejsca zapisu stron
    File pagesPathFile = new File(WikiUtils.PAGES_PATH);
    if(!pagesPathFile.exists()) {
      pagesPathFile.mkdirs();
    }
    
    FacesContext fc = FacesContext.getCurrentInstance();
    Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
    
    category = params.get("category");
    page = params.get("page");
    
    if(category != null && page != null) {
      inputCategory = WikiUtils.urlToHuman( category );
      inputTitle = WikiUtils.urlToHuman( page );
      
      title = inputTitle;
      category = inputCategory;
      editorValue = WikiUtils.getPage(category, page);
      isNewPage = false;
    } else {
      category = null;
      page = null;
    }
  }  
    
  ////////////////////////////////////////////////////////////////////////////
  // PUBLICZNE
  public void saveFile(ActionEvent event) {
    if(isNewPage) {
      isNewPage = false;
      inputCategory = category;
      inputTitle = title;
    }
    
    WikiUtils.saveArticle(inputCategory, inputTitle, editorValue);
  }
  
  public void saveFileAndFinish(ActionEvent event) throws IOException {
    saveFile(event);
    
    String path = "wiki.xhtml?destination=WIKIPAGES_READER&category=" + WikiUtils.humanToUrl(category) + "&page=" + WikiUtils.humanToUrl(page);
    
    Logger.getLogger(WikiReaderBean.class.getName()).log(Level.WARNING, "Przekierowuje: " + path);
    
    ExternalContext context = FacesContext.getCurrentInstance().getExternalContext(); 
    context.redirect(path);
  }
  
  ////////////////////////////////////////////////////////////////////////////
  // PSEUDO AKCESORY
  public List<String> getCategories() {
    return WikiUtils.getCategories();
  }
  
  public String getRightPage() {
    if(page != null) {
      return WikiUtils.humanToUrl(page);
    } else {
      return "null";
    }
  }

  public String getRightCategory() {
    if(category != null) {
      return WikiUtils.humanToUrl(category);
    } else {
      return "null";
    }
  }
  
  public String getOutputPage() {
    return WikiUtils.humanToUrl( (inputTitle == null) ? page : inputTitle );
  }
  
  public String getOutputCategory() {
    return WikiUtils.humanToUrl( (inputTitle == null) ? category : inputCategory );
  }

  ////////////////////////////////////////////////////////////////////////////
  // AKCESORY
  public String getEditorValue() {
      return editorValue;
  }

  public void setEditorValue(String editorValue) {
    this.editorValue = editorValue;
  } 

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public boolean getIsNewPage() {
    return isNewPage;
  }

  public void setIsNewPage(boolean isNewPage) {
    this.isNewPage = isNewPage;
  }

  public String getInputCategory() {
    return inputCategory;
  }

  public void setInputCategory(String inputCategory) {
    this.inputCategory = inputCategory;
  }

  public String getInputTitle() {
    return inputTitle;
  }

  public void setInputTitle(String inputTitle) {
    this.inputTitle = inputTitle;
  }
}
