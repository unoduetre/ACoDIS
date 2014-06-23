package pl.lodz.p.pracowniaproblemowa.acodis.filebrowser;

import java.io.File;
import java.io.Serializable;
import java.net.URLDecoder;
import java.util.Map;
import java.util.logging.Logger;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import pl.lodz.p.pracowniaproblemowa.acodis.login.Login;
import pl.lodz.p.pracowniaproblemowa.acodis.filebrowser.FileBrowserEntry;


public class FileBrowserBean extends FileBrowserEntry implements Serializable {
  
  private static final long serialVersionUID = 1419920132613404300L;
  
  private Login login;
  private Logger logger = Logger.getLogger(FileBrowserBean.class.getName());
  
  public FileBrowserBean() {
    super(new File(System.getProperty("user.home")));
  }
  
  private void init() {
    FacesContext context = FacesContext.getCurrentInstance();
    ExternalContext extContext = context.getExternalContext();
    Map<String, String> attributes = extContext.getRequestParameterMap();
    
    if(attributes.containsKey("resource")) {
      setResource((String) attributes.get("resource"));
      
      if(attributes.containsKey("action")) {
        String action = (String) attributes.get("action");
        
        if(action.equals("rmdir") || action.equals("rm")) {
          if(attributes.containsKey("referrer")) {
            File f = new File(decodePath((String) attributes.get("referrer")));
            f.delete();
          }
        }
        
        if(action.equals("new")) {
          
          if(attributes.containsKey("referrer") && attributes.containsKey("type") && attributes.containsKey("name")) {
            String referrer = (String) attributes.get("referrer");
            String type = (String) attributes.get("type");
            String name = (String) attributes.get("name");
            File p = new File(decodePath(referrer));
            File n = new File(p, name);
            if(type.equals("file")) {
              try {
                n.createNewFile();
              } catch (Throwable e) { }
            } else if(type.equals("dir")) {
              n.mkdirs();
            }
            setResource(encodePath(n.getAbsolutePath()));
          }
        }
      }
    } else {
      try {
        String filesPath = new File(URLDecoder.decode(FileBrowserBean.class.getResource("/files/.holder").getPath(), "UTF-8")).getParent();
        String userDir = (new File(new File(filesPath), login.getUsername())).getAbsolutePath();
        if(! (new File(userDir)).exists()) {
          (new File(userDir)).mkdirs();
        }
        setResource(encodePath(userDir));
      } catch (Throwable e) {
        logger.info(e.toString());
      }
    }
  }
  
  public boolean entryNameHasPrefix(String prefix) {
    return getName().toLowerCase().startsWith(prefix.toLowerCase());
  }
  
  public boolean someParentDirNameHasPrefix(String prefix) {
    File f = file;
    String p = prefix.toLowerCase();
    while(f != null) {
      if(f.getName().toLowerCase().startsWith(p)) {
        return true;
      }
      f = f.getParentFile();
    }
    return false;
  }
  
  public boolean isReallyFresh() {
    long creation = attrs.creationTime().toMillis();
    long now = System.currentTimeMillis();
    return now - creation <= 60000;
  }
  
  public boolean doesFileExist(String path) {
    return (new File(path)).exists();
  }
  
  public Login getLogin() {
    return login;
  }
  
  public void setLogin(Login login) {
    this.login = login;
    
    init();
  }
  
}
