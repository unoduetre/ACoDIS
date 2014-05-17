
package pl.lodz.p.pracowniaproblemowa.acodis.wiki;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
  private String editorValue = "";

  public WikiEditorBean() {
    // Utworzenie miejsca zapisu stron
    File pagesPathFile = new File(WikiUtils.PAGES_PATH);
    if(!pagesPathFile.exists()) {
      pagesPathFile.mkdirs();
    }
  }  
    
  ////////////////////////////////////////////////////////////////////////////
  // PUBLICZNE
  public void saveFile(ActionEvent event) {
    File outputFile = new File(WikiUtils.PAGES_PATH + "/" + category + "/" + title);
    Logger.getLogger(WikiEditorBean.class.getName()).log(Level.WARNING, ("    *** Ścieżka do pliku: " + outputFile.getAbsolutePath()));
    
    if(outputFile.exists()) {
      outputFile.delete();
    }
    
    FileWriter fw = null;
    try {
      fw = new FileWriter( outputFile );
      
      fw.append(editorValue);
      
    } catch (IOException ex) {
      Logger.getLogger(WikiEditorBean.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
      try {
        if(fw != null) { fw.close(); }
      } catch (IOException ex) {
        Logger.getLogger(WikiEditorBean.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
  }
  
  ////////////////////////////////////////////////////////////////////////////
  // PSEUDO AKCESORY
  public List<String> getCategories() {
    return WikiUtils.getCategories();
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
}
