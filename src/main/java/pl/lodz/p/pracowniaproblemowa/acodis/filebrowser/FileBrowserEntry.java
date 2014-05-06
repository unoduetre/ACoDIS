package pl.lodz.p.pracowniaproblemowa.acodis.filebrowser;

import java.io.File;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class FileBrowserEntry implements Comparable<FileBrowserEntry> {

  File path;
  
  public FileBrowserEntry(File path) {
    this.path = path;
  }
  
  public String getName() {
    return path.getName();
  }

  @Override
  public int compareTo(FileBrowserEntry o) {
    if(path.isDirectory() != o.path.isDirectory()) {
      return path.isDirectory() ? -1 : 1;
    }
    return getName().toLowerCase().compareTo(o.getName().toLowerCase());
  }
  
  public static String encodePath(String path) {
    try {
      return URLEncoder.encode(path, "UTF-8");
    } catch (Throwable e) {
      return "";
    }
  }
  
  public static String decodePath(String code) {
    try {
      return URLDecoder.decode(code, "UTF-8");
    } catch (Throwable e) {
      return "";
    }
  }
  
  public Boolean isDir() {
    return path.isDirectory();
  }
  
  public List<FileBrowserEntry> listDir() {
    List<FileBrowserEntry> result = new LinkedList<FileBrowserEntry>();
    
    if(isDir()) {
      for(File f: path.listFiles()) {
        result.add(new FileBrowserEntry(f));
      }
    }
    
    Collections.sort(result);
    
    return result;
  }

  public void setFromCode(String code) {
    path = new File(decodePath(code));
  }

  public String getCode() {
    return encodePath(path.getAbsolutePath());
  }
  
  public Boolean hasParent() {
    return path.getParent() != null;
  }
  
  public String getParentResource() {
    return (new FileBrowserEntry(path.getParentFile())).getCode();
  }
  
  public void setResource(String code) {
    if(code != null) {
      setFromCode(code);
    }
  }
  
  public String getResource() {
    return getCode();
  }
  
}
