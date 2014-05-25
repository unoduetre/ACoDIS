
package pl.lodz.p.pracowniaproblemowa.acodis.wiki;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;

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
    Logger.getLogger(WikiEditorBean.class.getName()).log(Level.WARNING, "* new WikiEditorBean()");
    
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
      title = "Nowa strona";
      category = null;
      page = null;
      isNewPage = true;
    }
  }  
    
  ////////////////////////////////////////////////////////////////////////////
  // PUBLICZNE

  public String saveFile() {
    Logger.getLogger(WikiEditorBean.class.getName()).log(Level.WARNING, "saveFile()");
    
    if(isNewPage) {
      isNewPage = false;
      inputCategory = category;
      inputTitle = title;
    }
    
    WikiUtils.saveArticle(inputCategory, inputTitle, editorValue);
    
    return "wiki";
  }
  
  public String saveFileAndFinish() throws IOException {
    Logger.getLogger(WikiEditorBean.class.getName()).log(Level.WARNING, "saveFileAndFinish()");
    
    saveFile();
    
//    String path = "wikiReader.xhtml?category=" + WikiUtils.humanToUrl(category) + "&page=" + WikiUtils.humanToUrl(page);
    
//    Logger.getLogger(WikiReaderBean.class.getName()).log(Level.WARNING, ("Przekierowuje: " + path));
//    
//    ExternalContext context = FacesContext.getCurrentInstance().getExternalContext(); 
//    context.redirect(path);
    return "wikiReader";
  }
  
  public String deleteFile() {
    Logger.getLogger(WikiEditorBean.class.getName()).log(Level.WARNING, "deleteFile()");
    
    WikiUtils.deleteArticle( inputCategory, inputTitle );
    
    return "wikiMenu";
  }
  
  ////////////////////////////////////////////////////////////////////////////
  // PSEUDO AKCESORY
  public List<String> getCategories() {
    Logger.getLogger(WikiEditorBean.class.getName()).log(Level.WARNING, "getCategories()");
    
    return WikiUtils.getCategories();
  }
  
  public String getRightPage() {
    Logger.getLogger(WikiEditorBean.class.getName()).log(Level.WARNING, "getRightPage()");
    
    if(page != null) {
      return WikiUtils.humanToUrl(page);
    } else {
      return "none";
    }
  }

  public String getRightCategory() {
    Logger.getLogger(WikiEditorBean.class.getName()).log(Level.WARNING, "getRightCategory()");
    
    if(category != null) {
      return WikiUtils.humanToUrl(category);
    } else {
      return "none";
    }
  }

  public String getOutputPage() {
    Logger.getLogger(WikiEditorBean.class.getName()).log(Level.WARNING, "getOutputPage()");
    
    return WikiUtils.humanToUrl( (inputTitle == null) ? page : inputTitle );
  }
  
  public String getOutputCategory() {
    Logger.getLogger(WikiEditorBean.class.getName()).log(Level.WARNING, "getOutputCategory()");
    
    return WikiUtils.humanToUrl( (inputTitle == null) ? category : inputCategory );
  }

  ////////////////////////////////////////////////////////////////////////////
  // AKCESORY
  public String getEditorValue() {
    Logger.getLogger(WikiEditorBean.class.getName()).log(Level.WARNING, "getEditorValue()");
    
      return editorValue;
  }

  public void setEditorValue(String editorValue) {
    Logger.getLogger(WikiEditorBean.class.getName()).log(Level.WARNING, "setEditorValue()");
    
    this.editorValue = editorValue;
  } 

  public String getTitle() {
    Logger.getLogger(WikiEditorBean.class.getName()).log(Level.WARNING, "getTitle()");
    
    return title;
  }

  public void setTitle(String title) {
    Logger.getLogger(WikiEditorBean.class.getName()).log(Level.WARNING, "setTitle()");
    
    this.title = title;
  }

  public String getCategory() {
    Logger.getLogger(WikiEditorBean.class.getName()).log(Level.WARNING, "getCategory()");
    
    return category;
  }

  public void setCategory(String category) {
    Logger.getLogger(WikiEditorBean.class.getName()).log(Level.WARNING, "setCategory()");
    
    this.category = category;
  }

  public boolean getIsNewPage() {
    Logger.getLogger(WikiEditorBean.class.getName()).log(Level.WARNING, "getIsNewPage()");
    
    return isNewPage;
  }

  public void setIsNewPage(boolean isNewPage) {
    Logger.getLogger(WikiEditorBean.class.getName()).log(Level.WARNING, "setIsNewPage()");
    
    this.isNewPage = isNewPage;
  }

  public String getInputCategory() {
    Logger.getLogger(WikiEditorBean.class.getName()).log(Level.WARNING, "getInputCategory()");
    
    return inputCategory;
  }

  public void setInputCategory(String inputCategory) {
    Logger.getLogger(WikiEditorBean.class.getName()).log(Level.WARNING, "setInputCategory()");
    
    this.inputCategory = inputCategory;
  }

  public String getInputTitle() {
    Logger.getLogger(WikiEditorBean.class.getName()).log(Level.WARNING, "getInputTitle()");
    
    return inputTitle;
  }

  public void setInputTitle(String inputTitle) {
    Logger.getLogger(WikiEditorBean.class.getName()).log(Level.WARNING, "setInputTitle()");
    
    this.inputTitle = inputTitle;
  }
}
