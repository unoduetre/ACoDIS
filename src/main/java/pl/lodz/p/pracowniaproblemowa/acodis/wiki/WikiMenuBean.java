
package pl.lodz.p.pracowniaproblemowa.acodis.wiki;

import org.primefaces.model.TreeNode;

/**
 *
 * @author lukkot
 */
public class WikiMenuBean {
  public TreeNode getMenuElements() {
    return WikiUtils.getMenuTree();
  }
}
