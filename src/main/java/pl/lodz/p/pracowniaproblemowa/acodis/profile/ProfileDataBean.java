package pl.lodz.p.pracowniaproblemowa.acodis.profile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;
import pl.lodz.p.pracowniaproblemowa.acodis.login.Login;

public class ProfileDataBean {

    private List<Profile> profiles = null;
    private Profile actualProfile;
    private boolean editable;
    private Login login;
    private final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public ProfileDataBean() {
        if (profiles == null) {
            this.profiles = load();
        }
        this.actualProfile = profiles.get(0);
        this.editable = false;
    }

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        if (login != null) {
            this.login = login;
        }
    }

    public List<Profile> getProfiles() {
        return profiles;
    }

    public Profile getActualProfile() {
        return actualProfile;
    }

    public void setActualProfile(Profile actualProfile) {
        this.actualProfile = actualProfile;
    }

    private List<Profile> load() {
        this.profiles = new ArrayList<>();
        NodeList nodeList = getProfilesNodeList();

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element elem = (Element) node;
                String name = elem.getElementsByTagName("name").item(0).getTextContent();
                String surname = elem.getElementsByTagName("surname").item(0).getTextContent();
                String duty = elem.getElementsByTagName("duty").item(0).getTextContent();
                Date birthday = null;
                try {
                    birthday = sdf.parse(elem.getElementsByTagName("birthday").item(0).getTextContent());
                } catch (ParseException ex) {
                    Logger.getLogger(ProfileDataBean.class.getName()).log(Level.SEVERE, null, ex);
                }
                int id = Integer.parseInt(elem.getAttribute("id"));
                int accessLevel = Integer.parseInt(elem.getElementsByTagName("access").item(0).getTextContent());
                profiles.add(new Profile(name, surname, duty, birthday, id, accessLevel));
            }
        }

        return profiles;
    }

    public void save() {
        String xmlPath = FacesContext.getCurrentInstance().getExternalContext().
                getRealPath("/WEB-INF/classes/pl/lodz/p/pracowniaproblemowa/acodis/resources/profiles.xml");
        Document xmlDoc;
        Element e = null;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            xmlDoc = db.newDocument();
            Element root = xmlDoc.createElement("profiles");

            for (Profile p : profiles) {
                e = xmlDoc.createElement("profile");
                e.setAttribute("id", Integer.toString(p.getId()));
                Element name = xmlDoc.createElement("name");
                name.appendChild(xmlDoc.createTextNode(p.getName()));
                e.appendChild(name);
                Element surname = xmlDoc.createElement("surname");
                surname.appendChild(xmlDoc.createTextNode(p.getSurname()));
                e.appendChild(surname);
                Element duty = xmlDoc.createElement("duty");
                duty.appendChild(xmlDoc.createTextNode(p.getDuty()));
                e.appendChild(duty);
                Element birthday = xmlDoc.createElement("birthday");
                birthday.appendChild(xmlDoc.createTextNode(p.getBirthdayString()));
                e.appendChild(birthday);
                Element access = xmlDoc.createElement("access");
                access.appendChild(xmlDoc.createTextNode(Integer.toString(p.getAccessLevel())));
                e.appendChild(access);

                root.appendChild(e);
            }
            xmlDoc.appendChild(root);

            Transformer tr = TransformerFactory.newInstance().newTransformer();
            tr.setOutputProperty(OutputKeys.INDENT, "yes");
            tr.setOutputProperty(OutputKeys.METHOD, "xml");
            tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            tr.transform(new DOMSource(xmlDoc), new StreamResult(new FileOutputStream(xmlPath)));

        } catch (TransformerException | FileNotFoundException | ParserConfigurationException ex) {
            Logger.getLogger(ProfileDataBean.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private static NodeList getProfilesNodeList() {
        try {
            String xmlPath = FacesContext.getCurrentInstance().getExternalContext().
                    getRealPath("/WEB-INF/classes/pl/lodz/p/pracowniaproblemowa/acodis/resources/profiles.xml");
            if (xmlPath == null) {
                xmlPath = Thread.currentThread().getContextClassLoader().
                        getResource("/pl/lodz/p/pracowniaproblemowa/acodis/resources/profiles.xml").getFile();
            }
            File xmlData = new File(xmlPath);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document xmlDoc = db.parse(xmlData);
            NodeList nodeList = xmlDoc.getElementsByTagName("profile");

            return nodeList;

        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(ProfileDataBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public void profileChangeListener(ValueChangeEvent e) {
        actualProfile = (Profile) e.getNewValue();
        this.editable = false;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public void changeEditable() {
        if (editable) {
            save();
        }
        this.editable = !editable;
    }

    public void cancelEdit() {
        FacesContext context = FacesContext.getCurrentInstance();
        String viewId = context.getViewRoot().getViewId();
        ViewHandler handler = context.getApplication().getViewHandler();
        UIViewRoot root = handler.createView(context, viewId);
        root.setViewId(viewId);
        context.setViewRoot(root);
        this.editable = false;
    }

}
