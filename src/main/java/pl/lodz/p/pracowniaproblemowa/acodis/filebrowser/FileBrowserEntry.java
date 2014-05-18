package pl.lodz.p.pracowniaproblemowa.acodis.filebrowser;

import java.io.File;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class FileBrowserEntry implements Comparable<FileBrowserEntry> {

  File file;
  Path path;
  BasicFileAttributes attrs;
  
  public FileBrowserEntry(File file) {
    this.file = file;
    path = Paths.get(file.getAbsolutePath());
    try {
      attrs = Files.readAttributes(path, BasicFileAttributes.class);
    } catch (Throwable e) {
      attrs = null;
    }
  }
  
  public String getName() {
    return file.getName();
  }
  
  public String getPath() {
    return file.getPath();
  }
  
  public String getSize() {
    if(attrs == null) return "Unknown";
    long sz = attrs.size();
    
    if(sz < 1000) {
      return sz + " B ";
    } else if(sz < 1024000) {
      return ((sz + 512) >> 10) + " kB";
    } else if(sz < 1048576000) {
      return ((sz + 524288) >> 20) + " MB";
    } else {
      return ((sz + 536870912) >> 30) + " GB";
    }
  }
  
  private String formatFileTime(FileTime ft) {
    long epochMillis = ft.to(TimeUnit.MILLISECONDS);
    Date date = new Date(epochMillis);
    return date.toString();
  }
  
  public List<Property> getFileProperties() {
    List<Property> ret = new LinkedList<Property>();
    
    ret.add(new Property("Name", getName()));
    ret.add(new Property("Mime type", getMimeType()));
    
    if(attrs != null) {
      ret.add(new Property("Size", Long.toString(attrs.size()) + " B"));
      ret.add(new Property("Access time", formatFileTime(attrs.lastAccessTime())));
      ret.add(new Property("Modification time", formatFileTime(attrs.lastModifiedTime())));
      ret.add(new Property("Creation time", formatFileTime(attrs.creationTime())));
    }
    
    return ret;
  }
  
  public String getMimeType() {
    try {
      return Files.probeContentType(path);
    } catch (Throwable e) {
      return "Unknown";
    }
  }
  
  @Override
  public int compareTo(FileBrowserEntry o) {
    if(file.isDirectory() != o.file.isDirectory()) {
      return file.isDirectory() ? -1 : 1;
    }
    return getName().toLowerCase().compareTo(o.getName().toLowerCase());
  }
  
  public static String encodePath(String pathStr) {
    try {
      return URLEncoder.encode(pathStr, "UTF-8");
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
    return file.isDirectory();
  }
  
  public Boolean hasContents() {
    return isDir() && file.list().length != 0;
  }
  
  public List<FileBrowserEntry> listDir() {
    List<FileBrowserEntry> result = new LinkedList<FileBrowserEntry>();
    
    if(isDir()) {
      for(File f: file.listFiles()) {
        result.add(new FileBrowserEntry(f));
      }
    }
    
    Collections.sort(result);
    
    return result;
  }

  public void setFromCode(String code) {
    file = new File(decodePath(code));
    path = Paths.get(file.getAbsolutePath());
    try {
      attrs = Files.readAttributes(path, BasicFileAttributes.class);
    } catch (Throwable e) {
      attrs = null;
    }
  }

  public String getCode() {
    return encodePath(file.getAbsolutePath());
  }
  
  public Boolean hasParent() {
    return file.getParent() != null;
  }
  
  public String getParentResource() {
    return (new FileBrowserEntry(file.getParentFile())).getCode();
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
