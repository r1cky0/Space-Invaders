package logic.environment.manager.file;


import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;

public class ReadXmlFile {
    public static String readXmlFile(int i, String tag) {

        try {

            File fXmlFile = new File("res/paths.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName(tag);

            Node nNode1 = nList.item(i);
            Element element = (Element) nNode1;
            return element.getElementsByTagName("ImgUrl").item(0).getTextContent();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

}
