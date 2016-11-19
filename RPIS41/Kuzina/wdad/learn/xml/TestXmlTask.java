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
        String pathForReading = "C:\\Users\\Elvira\\Documents\\NetBeansProjects\\starting-monkey-to-human-path\\src\\RPIS41\\Kuzina\\wdad\\learn\\xml\\test3.xml";
        String pathForWriting = "C:\\Users\\Elvira\\Documents\\NetBeansProjects\\starting-monkey-to-human-path\\src\\RPIS41\\Kuzina\\wdad\\learn\\xml\\test2CHANGED.xml";
        File fileForReading = new File(pathForReading);
        File fileForWriting = new File(pathForWriting);
        Document document = XMLReader.xmlReader(fileForReading);
        XmlTask testObj = new XmlTask(document);
        
        User owner = new User("Olga","","");
        testObj.updateNote(owner, "NOTE3", "Olgathebest____CHANGED");   
        XMLWriter.xmlWriter(fileForWriting, document);
        
        User firstUser = new User("Olga", "ulogola.com", "R");
        testObj.setPrivileges(firstUser, "NOTE4", 3);
        testObj.setPrivileges(firstUser, "NOTE3", 3);        
        User secondUser = new User("Olga", "olo@mail.ru", "RW");
        testObj.setPrivileges(secondUser, "NOTE2", 1);
        XMLWriter.xmlWriter(fileForWriting, document);
    }
}
