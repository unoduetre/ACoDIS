
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
public class CategoryManagerBean {
  private List<String> categoriesToDelete;
  private String newCategory;
  
  ////////////////////////////////////////////////////////////////////////////
  // PUBLICZNE
  public void deleteSelected(ActionEvent event) {
    for(String category : categoriesToDelete) {
      try {
        File dir = new File(WikiUtils.PAGES_PATH + "/" + category);
        Logger.getLogger(CategoryManagerBean.class.getName()).log(Level.WARNING, ("Usuwam katalog: " + dir.getAbsolutePath()));
        WikiUtils.delete(dir);
      } catch(Exception ex) {
        Logger.getLogger(CategoryManagerBean.class.getName()).log(Level.WARNING, ("Brak kategorii: " + category));
      }
    }
  }
  
  public void createCategory(ActionEvent event) {
      try {
        File dir = new File(WikiUtils.PAGES_PATH + "/" + getNewCategory());
        Logger.getLogger(CategoryManagerBean.class.getName()).log(Level.WARNING, ("Tworzę katalog: " + dir.getAbsolutePath()));
        dir.mkdirs();
      } catch(Exception ex) {
        Logger.getLogger(CategoryManagerBean.class.getName()).log(Level.WARNING, ("Nie udało się utworzyć kategorii: " + getNewCategory()), ex);
      }
  }
  
  ////////////////////////////////////////////////////////////////////////////
  // PSEUDO AKCESORY
  public List<String> getCategories() {
    return WikiUtils.getCategories();
  }
  
  ////////////////////////////////////////////////////////////////////////////
  // AKCESORY

  public List<String> getCategoriesToDelete() {
    return categoriesToDelete;
  }

  public void setCategoriesToDelete(List<String> categoriesToDelete) {
    this.categoriesToDelete = categoriesToDelete;
  }

  public String getNewCategory() {
    return newCategory;
  }

  public void setNewCategory(String newCategory) {
    this.newCategory = newCategory;
  }
}
