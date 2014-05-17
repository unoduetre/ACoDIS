
package pl.lodz.p.pracowniaproblemowa.acodis.wiki.data;

import pl.lodz.p.pracowniaproblemowa.acodis.wiki.WikiUtils;

/**
 *
 * @author lukkot
 */
public class WikiMenuItem {
  ////////////////////////////////////////////////////////////////////////////
  // ZMIENNE
  private final String category;
  private final String page;
  
  ////////////////////////////////////////////////////////////////////////////
  // KONSTRUKTORY
  public WikiMenuItem(String category, String page) {
    this.category = category;
    this.page = page;
  }
  
  ////////////////////////////////////////////////////////////////////////////
  // PSEUDO AKCESORY
  public String getName() {
    return (page != null) ? page : category;
  }
  
  ////////////////////////////////////////////////////////////////////////////
  // AKCESORY
  public String getCategory() {
    return WikiUtils.humanToUrl( category );
  }

  public String getPage() {
    if(page == null) return null;
    
    return WikiUtils.humanToUrl( page );
  }
}
