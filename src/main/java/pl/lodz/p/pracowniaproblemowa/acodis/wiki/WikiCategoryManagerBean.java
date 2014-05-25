
package pl.lodz.p.pracowniaproblemowa.acodis.wiki;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.event.ActionEvent;

/**
 *
 * @author lukkot
 */
public class WikiCategoryManagerBean {
  private List<String> categoriesToDelete;
  private String newCategory;
  
  ////////////////////////////////////////////////////////////////////////////
  // KONSTRUKTOR
  public WikiCategoryManagerBean() {
    Logger.getLogger(WikiCategoryManagerBean.class.getName()).log(Level.WARNING, "* new WikiCategoryManagerBean()");
  }
  
  ////////////////////////////////////////////////////////////////////////////
  // PUBLICZNE
  public String deleteSelected() {
    Logger.getLogger(WikiCategoryManagerBean.class.getName()).log(Level.WARNING, "deleteSelected()");
    
    WikiUtils.deleteCategories(categoriesToDelete);
    return "wikiCategoryManager";
  }
  
  public void createCategory(ActionEvent event) {
    Logger.getLogger(WikiCategoryManagerBean.class.getName()).log(Level.WARNING, "createCategory(ActionEvent)");
    
      WikiUtils.createCategory(newCategory);
  }
  
  public String createCategory() {
      Logger.getLogger(WikiCategoryManagerBean.class.getName()).log(Level.WARNING, "createCategory()");
      
      WikiUtils.createCategory(newCategory);
      return "wikiCategoryManager";
  }
  
  ////////////////////////////////////////////////////////////////////////////
  // PSEUDO AKCESORY
  public List<String> getCategories() {
    Logger.getLogger(WikiCategoryManagerBean.class.getName()).log(Level.WARNING, "getCategories()");
    
    return WikiUtils.getCategories();
  }
  
  ////////////////////////////////////////////////////////////////////////////
  // AKCESORY

  public List<String> getCategoriesToDelete() {
    Logger.getLogger(WikiCategoryManagerBean.class.getName()).log(Level.WARNING, "getCategoriesToDelete()");
    
    return categoriesToDelete;
  }

  public void setCategoriesToDelete(List<String> categoriesToDelete) {
    Logger.getLogger(WikiCategoryManagerBean.class.getName()).log(Level.WARNING, "setCategoriesToDelete()");
    this.categoriesToDelete = categoriesToDelete;
  }

  public String getNewCategory() {
    Logger.getLogger(WikiCategoryManagerBean.class.getName()).log(Level.WARNING, "getNewCategory()");
    return newCategory;
  }

  public void setNewCategory(String newCategory) {
    Logger.getLogger(WikiCategoryManagerBean.class.getName()).log(Level.WARNING, "setNewCategory()");
    this.newCategory = newCategory;
  }
}
