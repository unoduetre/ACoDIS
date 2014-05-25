
package pl.lodz.p.pracowniaproblemowa.acodis.wiki;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
  ////////////////////////////////////////////////////////////////////////////
  // STAŁE PUBLICZNE
  // Ścieżka do stron wiki
  public static final String PAGES_PATH = ((ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext()).getRealPath("") + "/wikiPages";
  
  // Dostępne moduły wiki
  public static final String WIKIPAGE_READER = "WIKIPAGE_READER";
  public static final String WIKIPAGE_EDITOR = "WIKIPAGE_EDITOR";
  public static final String WIKIPAGE_CATEGORY_MANAGER = "WIKIPAGE_CATEGORY_MANAGER";
  
  ////////////////////////////////////////////////////////////////////////////
  // METODY PUBLICZNE
  public static String humanToUrl(String name) {
    return name.replace(' ', '_');
  }
  
  public static String urlToHuman(String name) {
    return name.replace('_', ' ');
  }
  
  public static List<String> getCategories() {
    List<String> categories = new ArrayList<>();
    
    File pagesPathFile = new File(WikiUtils.PAGES_PATH);
    File[] categoriesDir = pagesPathFile.listFiles( new DirectoryFileFilter() );
    
    if(categoriesDir != null) {
      for (File categoryDir : categoriesDir) {
        categories.add( urlToHuman( categoryDir.getName() ) );
      }
    }
    
    return categories;
  }
  
  public static List<String> getPages(String category) {
    List<String> pages = new ArrayList<>();
    
    File pagesPathFile = new File(WikiUtils.PAGES_PATH + "/" + category);
    File[] pagesDir = pagesPathFile.listFiles();
    
    if(pagesDir != null) {
      for (File pageDir : pagesDir) {
        pages.add( urlToHuman( pageDir.getName() ) );
      }
    }
    
    return pages;
  }
  
  public static TreeNode getMenuTree() {
    TreeNode root = new DefaultTreeNode(new WikiMenuItem(null, null), null);
    
    List<String> categories = WikiUtils.getCategories();
    
//    Logger.getLogger(WikiUtils.class.getName()).log(Level.SEVERE, "DRZEWO MENU");
    for(String category : categories) {
//      Logger.getLogger(WikiUtils.class.getName()).log(Level.SEVERE, ("  " + category));
      List<String> pages = WikiUtils.getPages( humanToUrl( category ) );
      
      TreeNode tnCategory = new DefaultTreeNode(new WikiMenuItem( urlToHuman( category ), null), root );
      
      for(String page : pages) {
//        Logger.getLogger(WikiUtils.class.getName()).log(Level.SEVERE, ("    " + page));
        DefaultTreeNode tnPage = new DefaultTreeNode(new WikiMenuItem( urlToHuman( category ), urlToHuman( page ) ), tnCategory);
      }
    }
    
    return root;
  }

  static String getPage(String category, String title) {
    String result = "";
    
    try {
      File file = new File( WikiUtils.PAGES_PATH + "/" + humanToUrl( category ) + "/" + humanToUrl( title ) );
      byte[] encoded = Files.readAllBytes( file.toPath() );
      result = new String(encoded, StandardCharsets.UTF_8);
    } catch (IOException ex) {
      Logger.getLogger(WikiUtils.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    return result;
  }
  
  public static void delete(File f) throws IOException {
    if (f.isDirectory()) {
      for (File c : f.listFiles())
        delete(c);
    }
    if (!f.delete())
      throw new FileNotFoundException("Failed to delete file: " + f);
  }
  
  public static void saveArticle(String category, String title, String text) {
    File outputFile = new File(WikiUtils.PAGES_PATH + "/" + humanToUrl( category ) + "/" + humanToUrl( title ));
    Logger.getLogger(WikiEditorBean.class.getName()).log(Level.WARNING, ("    *** Ścieżka do pliku: " + outputFile.getAbsolutePath()));
    
    if(outputFile.exists()) {
      outputFile.delete();
    }
    
    FileWriter fw = null;
    try {
      fw = new FileWriter( outputFile );
      
      fw.append(text);
      
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
  
  public static void deleteCategories(List<String> categoriesToDelete) {
    for(String category : categoriesToDelete) {
      try {
        File dir = new File(WikiUtils.PAGES_PATH + "/" + humanToUrl(category));
        Logger.getLogger(WikiCategoryManagerBean.class.getName()).log(Level.WARNING, ("Usuwam katalog: " + dir.getAbsolutePath()));
        WikiUtils.delete(dir);
      } catch(IOException ex) {
        Logger.getLogger(WikiCategoryManagerBean.class.getName()).log(Level.WARNING, ("Brak kategorii: " + humanToUrl(category)));
      }
    }
  }
  
  public static void createCategory(String category) {
      try {
        File dir = new File(WikiUtils.PAGES_PATH + "/" + humanToUrl(category));
        Logger.getLogger(WikiCategoryManagerBean.class.getName()).log(Level.WARNING, ("Tworzę katalog: " + dir.getAbsolutePath()));
        dir.mkdirs();
      } catch(Exception ex) {
        Logger.getLogger(WikiCategoryManagerBean.class.getName()).log(Level.WARNING, ("Nie udało się utworzyć kategorii: " + humanToUrl(category)), ex);
      }
  }
  
    public static void deleteArticle(String category, String title) {
      try {
        File outputFile = new File(WikiUtils.PAGES_PATH + "/" + humanToUrl( category ) + "/" + humanToUrl( title ));
        Logger.getLogger(WikiCategoryManagerBean.class.getName()).log(Level.WARNING, ("Usuwam artykuł: " + outputFile.getAbsolutePath()));
        WikiUtils.delete(outputFile);
      } catch(IOException ex) {
        Logger.getLogger(WikiCategoryManagerBean.class.getName()).log(Level.WARNING, ("Problem przy usuwaniu artykułu:"), ex);
      }
  }
}
