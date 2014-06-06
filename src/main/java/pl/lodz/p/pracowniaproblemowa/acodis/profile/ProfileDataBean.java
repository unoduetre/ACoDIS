package pl.lodz.p.pracowniaproblemowa.acodis.profile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ValueChangeEvent;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

@ManagedBean(name = "profileDataBean")
@SessionScoped
public class ProfileDataBean {

    private List<Profile> profiles;
    private Profile actualProfile;

    public ProfileDataBean() {
        this.profiles = load();
        this.actualProfile = profiles.get(0);
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

    private static List<Profile> load() {
        List<Profile> profiles = new ArrayList<>();
        try {
            String xmlPath = Thread.currentThread().getContextClassLoader().
                    getResource("/pl/lodz/p/pracowniaproblemowa/acodis/resources/profiles.xml").getFile();
            File xmlData = new File(xmlPath);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document xmlDoc = db.parse(xmlData);
            NodeList nodeList = xmlDoc.getElementsByTagName("profile");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element elem = (Element) node;
                    String name = elem.getElementsByTagName("name").item(0).getTextContent();
                    String surname = elem.getElementsByTagName("surname").item(0).getTextContent();
                    String duty = elem.getElementsByTagName("duty").item(0).getTextContent();;
                    int age = Integer.parseInt(elem.getElementsByTagName("age").item(0).getTextContent());
                    int id = Integer.parseInt(elem.getAttribute("id"));
                    int accessLevel = Integer.parseInt(elem.getElementsByTagName("access").item(0).getTextContent());
                    profiles.add(new Profile(name, surname, duty, age, id, accessLevel));
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(ProfileDataBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        return profiles;
    }

    public void profileChangeListener(ValueChangeEvent e) {
        actualProfile = (Profile) e.getNewValue();
    }

}
