package logic.environment.manager.file;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;

public class ReadXmlFile {

    public String read(String tag) {

        try {
            File file = new File("res/paths.xml");
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(file);
            return document.getElementsByTagName(tag).item(0).getTextContent();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
