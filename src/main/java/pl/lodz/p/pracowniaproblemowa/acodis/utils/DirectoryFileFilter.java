
package pl.lodz.p.pracowniaproblemowa.acodis.utils;

import java.io.File;
import java.io.FileFilter;

/**
 *
 * @author lukkot
 */
public class DirectoryFileFilter implements FileFilter {

  @Override
  public boolean accept(File pathname) {
    return pathname.isDirectory();
  }
  
}
