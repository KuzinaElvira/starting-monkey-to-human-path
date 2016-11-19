/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RPIS41.Kuzina.wdad.learn.xml;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.xml.sax.SAXException;

/**
 *
 * @author Эльвира
 */
public class XMLWriter {

    public static void xmlWriter(File file, Document document) throws ParserConfigurationException, SAXException, IOException, TransformerConfigurationException, TransformerException {
        DOMSource dom_source = new DOMSource(document);
        StreamResult out_stream = new StreamResult(file);
        TransformerFactory tFactory = TransformerFactory.newInstance();
        Transformer transformer = tFactory.newTransformer();
        DocumentType docType = document.getDoctype();
        if (docType != null) {
            String systemID = docType.getSystemId();
            String res = systemID;
            transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, res);
        }
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");    
        transformer.transform(dom_source, out_stream);
    }
    
}
