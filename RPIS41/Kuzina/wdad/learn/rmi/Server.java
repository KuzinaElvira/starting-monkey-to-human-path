/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RPIS41.Kuzina.wdad.learn.rmi;

import RPIS41.Kuzina.wdad.data.managers.PreferencesManager;
import RPIS41.Kuzina.wdad.learn.xml.XMLReader;
import RPIS41.Kuzina.wdad.learn.xml.XmlTask;
import RPIS41.Kuzina.wdad.utils.PreferencesConstantManager;
import java.io.File;
import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 *
 * @author Эльвира
 */
public class Server {
     
    private static final String XML_DATA_MANAGER = "XmlDataManager";

    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, XPathExpressionException, RemoteException, AlreadyBoundException, InterruptedException {
        
        File file = new File(".\\src\\RPIS41\\Kuzina\\wdad\\learn\\xml\\test1.xml");
        Document document = XMLReader.xmlReader(file);
        PreferencesManager manager = PreferencesManager.getInstance();
        
        int port = Integer.parseInt(manager.getProperty(PreferencesConstantManager.REGISTRY_PORT));
        Registry registry = LocateRegistry.createRegistry(port);
        
        XmlTask notes = new XmlTask(document);
        XmlDataManager xmlDataManager = new XmlDataManagerImpl(notes);
        Remote stub = UnicastRemoteObject.exportObject(xmlDataManager, 0);
 
        System.out.print("Binding service...");
        registry.bind(XML_DATA_MANAGER, stub);
        manager.addBindedObject(XML_DATA_MANAGER, XmlDataManager.class.getCanonicalName());
        System.out.println(" OK");   
    }
    
}
