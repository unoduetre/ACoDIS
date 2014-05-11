
package pl.lodz.p.pracowniaproblemowa.acodis.wiki.data;

/**
 *
 * @author lukkot
 */
public class WikiMenuItem {
  ////////////////////////////////////////////////////////////////////////////
  // ZMIENNE
  private String category;
  private String page;
  
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
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public String getPage() {
    return page;
  }

  public void setPage(String page) {
    this.page = page;
  }
}
