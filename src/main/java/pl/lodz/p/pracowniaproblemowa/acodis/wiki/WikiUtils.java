
package pl.lodz.p.pracowniaproblemowa.acodis.wiki;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import pl.lodz.p.pracowniaproblemowa.acodis.utils.DirectoryFileFilter;
import pl.lodz.p.pracowniaproblemowa.acodis.wiki.data.WikiMenuItem;

/**
 *
 * @author lukkot
 */
public class WikiUtils {
  public static final String PAGES_PATH = ((ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext()).getRealPath("") + "/wikiPages";
  
  public static List<String> getCategories() {
    List<String> categories = new ArrayList<>();
    
    File pagesPathFile = new File(WikiUtils.PAGES_PATH);
    File[] categoriesDir = pagesPathFile.listFiles( new DirectoryFileFilter() );
    
    if(categoriesDir != null) {
      for (File categoryDir : categoriesDir) {
        categories.add(categoryDir.getName());
      }
    }
    
    return categories;
  }
  
  public static List<String> getPages(String category) {
    List<String> pages = new ArrayList<>();
    
    File pagesPathFile = new File(WikiUtils.PAGES_PATH + "\\" + category);
    File[] pagesDir = pagesPathFile.listFiles();
    
    if(pagesDir != null) {
      for (File pageDir : pagesDir) {
        pages.add(pageDir.getName());
      }
    }
    
    return pages;
  }
  
  public static TreeNode getMenuTree() {
    TreeNode root = new DefaultTreeNode(new WikiMenuItem(null, null), null);
    
    List<String> categories = WikiUtils.getCategories();
    
    Logger.getLogger(WikiUtils.class.getName()).log(Level.SEVERE, "DRZEWO MENU");
    for(String category : categories) {
      Logger.getLogger(WikiUtils.class.getName()).log(Level.SEVERE, "  " + category);
      List<String> pages = WikiUtils.getPages(category);
      
      TreeNode tnCategory = new DefaultTreeNode(new WikiMenuItem(category, null), root);
      
      for(String page : pages) {
        Logger.getLogger(WikiUtils.class.getName()).log(Level.SEVERE, "    " + page);
        DefaultTreeNode tnPage = new DefaultTreeNode(new WikiMenuItem(category, page), tnCategory);
      }
    }
    
    return root;
  }

  static String getPage(String category, String page) {
    String result = "";
    
    try {
      File file = new File( WikiUtils.PAGES_PATH + "/" + category + "/" + page );
      byte[] encoded = Files.readAllBytes( file.toPath() );
      result = new String(encoded, StandardCharsets.UTF_8);
    } catch (IOException ex) {
      Logger.getLogger(WikiUtils.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    return result;
  }
}
