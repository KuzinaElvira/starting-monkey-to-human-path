/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RPIS41.Kuzina.wdad.learn.rmi;

import RPIS41.Kuzina.wdad.data.managers.PreferencesManager;
import RPIS41.Kuzina.wdad.learn.xml.User;
import RPIS41.Kuzina.wdad.utils.PreferencesConstantManager;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import org.xml.sax.SAXException;

/**
 *
 * @author Эльвира
 */
public class Client {
    private static final String XML_DATA_MANAGER = "XmlDataManager";

    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, XPathExpressionException, RemoteException, NotBoundException {
        
        PreferencesManager manager = PreferencesManager.getInstance();
        int port = Integer.parseInt(manager.getProperty(PreferencesConstantManager.REGISTRY_PORT));
        Registry registry = LocateRegistry.getRegistry("localhost", port);
        XmlDataManager service = (XmlDataManager) registry.lookup(XML_DATA_MANAGER);
        String text = service.getNote(new User("Olga", ""), "NOTE3");
        System.out.println(text);
        service.updateNote(new User("Olga", ""), "NOTE3", new StringBuilder("CHANGEDDDDD"));
        text = service.getNote(new User("Olga", ""), "NOTE3");        
        System.out.println(text);
        
    }
    
}
