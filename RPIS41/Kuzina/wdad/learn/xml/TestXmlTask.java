/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RPIS41.Kuzina.wdad.learn.xml;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 *
 * @author Эльвира
 */
public class TestXmlTask {
    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, TransformerException {
        String path = "C:\\test3.xml";
        Document document = XMLReader.xmlReader(new File(path));
        XmlTask testObj = new XmlTask(document);
        User owner = new User("Olga","","");
        System.out.println(testObj.getNoteText(owner, "NOTE3"));
        testObj.updateNote(owner, "NOTE3", "Olgathebest_____!_");
        System.out.println(testObj.getNoteText(owner, "NOTE3"));
    }
}
