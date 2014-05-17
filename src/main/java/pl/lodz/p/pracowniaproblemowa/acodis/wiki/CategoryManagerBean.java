
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
    WikiUtils.deleteCategories(categoriesToDelete);
  }
  
  public void createCategory(ActionEvent event) {
      WikiUtils.createCategory(newCategory);
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
