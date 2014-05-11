
package pl.lodz.p.pracowniaproblemowa.acodis.wiki;

import java.io.File;
import javax.faces.event.ActionEvent;

/**
 *
 * @author lukkot
 */
public class InstallTestDataBean {
  public void doIt(ActionEvent event) {
    // Utworzenie katalogu przechowującego dane
    File wikiPagesDir = new File(WikiUtils.PAGES_PATH);
    if(wikiPagesDir.exists() && !wikiPagesDir.isDirectory()) {
      wikiPagesDir.delete();
    }
    if(!wikiPagesDir.exists()) {
      wikiPagesDir.mkdirs();
    }
    
    
    // Utworzenie kategorii testowych
    File cat1 = new File(WikiUtils.PAGES_PATH + "/" + "cat1");
    File cat2 = new File(WikiUtils.PAGES_PATH + "/" + "cat2");
    File cat3 = new File(WikiUtils.PAGES_PATH + "/" + "cat3");
    
    if(!cat1.exists()) {
      cat1.mkdirs();
    }
    if(!cat2.exists()) {
      cat2.mkdirs();
    }
    if(!cat3.exists()) {
      cat3.mkdirs();
    }
  }
}
