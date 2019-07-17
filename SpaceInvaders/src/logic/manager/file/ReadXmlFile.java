package logic.manager.file;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;

import java.io.File;

/**
 * Classe che si occupa della lettura dei path delle risorse contenute nel file xml.
 */
public class ReadXmlFile {

    /**
     * Lettura path.
     * @param tag chiave che identifica i path nel file xml
     * @return restituisce il path della risorsa
     */
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
