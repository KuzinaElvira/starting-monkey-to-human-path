/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RPIS41.Kuzina.wdad.data.managers;

import RPIS41.Kuzina.wdad.learn.xml.XMLReader;
import java.io.File;
import java.io.IOException;
import java.util.Properties;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Эльвира
 */
public class PreferencesManager {

    private static PreferencesManager instance;
    private Document document;
    private XPath xPath;
    
    private final String SERVER = "server";
    private final String CLIENT = "client";
    private final String CLASS_PROVIDER = "classprovider";
    private final String REGISTRY = "registry";
    private final String BINDED_OBJECT = "bindedobject";
    private final String CREATE_REGISTRY = "createregistry";
    private final String REGISTRY_ADDRESS = "registryaddress";
    private final String REGISTRY_PORT = "registryport";
    private final String POLICY_PATH = "policypath";
    private final String USE_CODE_BASE_ONLY = "usecodebaseonly";
    private final String NAME_ATTRIBUTE = "name";
    private final String CLASS_ATTRIBUTE = "class";
    
    private static final String APPCONFIG_PATH = ".\\src\\RPIS41\\Kuzina\\wdad\\resources\\configuration\\appconfig.xml";

    private PreferencesManager(Document document) {
        this.xPath = XPathFactory.newInstance().newXPath();
        this.document = document;
    }

    public static synchronized PreferencesManager getInstance() throws ParserConfigurationException, SAXException, IOException {
        if (instance == null) {
            instance = new PreferencesManager(XMLReader.xmlReader(new File(APPCONFIG_PATH)));
        }
        return instance;
    }
    
    /*---------------------------3 ЛАБА---------------------------*/
    
    private Node getNode(String key) throws XPathExpressionException{
        key = key.replace('.', '/');
        XPathExpression expr = xPath.compile(key);
        return (Node) expr.evaluate(document,XPathConstants.NODE);
    }
    
    public void setProperty(String key, String value) throws XPathExpressionException {
        getNode(key).setTextContent(value);
    }

    public String getProperty(String key) throws XPathExpressionException {        
        return getNode(key).getTextContent();
    }

    public void setProperties(Properties prop) throws XPathExpressionException {
        for(Object key : prop.keySet()){
            String skey = (String) key;
            setProperty(skey, prop.getProperty(skey));
        }
    }
    
    private String getNodePath(Node node) {
        Node parent = node.getParentNode();
        if (parent == null || parent.getNodeName().equals("#document")) {
            return node.getNodeName();
        }
        return getNodePath(parent) + '.' + node.getNodeName();
    }
    
    public Properties getProperties() throws XPathExpressionException {
        Properties props = new Properties();
        String key;
        NodeList nodeList = (NodeList) xPath.compile("//*[not(*)]").evaluate(document, XPathConstants.NODESET);
        for (int i = 0; i < nodeList.getLength(); i++) {
            key = getNodePath(nodeList.item(i));
            props.put(key, getProperty(key));
        }
        return props;
    }    

    public void addBindedObject(String name, String className) throws XPathExpressionException {
        Node server = getNode("appconfig.rmi.server");
        Element bindedObject = document.createElement("bindedobject");
        bindedObject.setAttribute("name", name);
        bindedObject.setAttribute("class", className);
        server.appendChild(bindedObject);
    }

    public void removeBindedObject(String name) throws XPathExpressionException {
        Node server = getNode("appconfig.rmi.server");
        NodeList bindedObjects = server.getChildNodes();
        for(int i = 0; i < bindedObjects.getLength(); i++){
            Node bindedObject = bindedObjects.item(i);
            if(!bindedObject.getNodeName().equals("bindedobject")) continue;
            if(bindedObject.getAttributes().getNamedItem("name").getNodeValue().equals(name)){
                server.removeChild(bindedObject);
            }
        }
    }

    /*---------------------------3 ЛАБА---------------------------*/
    
    private Node findNodeByName(String name, Node parentNode) {
        Element parent = (Element) parentNode;
        return parent.getElementsByTagName(name).item(0);
    }

    //ДЛЯ ПОИСКА ДЕТЕЙ ПО ИМЕНИ
    private Node getRmiChild(String name) {
        Node appconf = document.getDocumentElement();
        Node rmi = appconf.getFirstChild();
        return findNodeByName(name, rmi);
    }

    private Node getServerChild(String name) {
        Node server = getRmiChild(SERVER);
        return findNodeByName(name, server);
    }

    private Node getClientChild(String name) {
        Node client = getRmiChild(CLIENT);
        return findNodeByName(name, client);
    }

    private Node getRegistryChild(String name) {
        Node registry = getServerChild(REGISTRY);
        return findNodeByName(name, registry);
    }
    //ДЛЯ ПОИСКА ДЕТЕЙ ПО ИМЕНИ     

    @Deprecated
    public boolean getCreateregistry() {
        Node createRegistry = getRegistryChild(CREATE_REGISTRY);
        String val = createRegistry.getNodeValue();
        switch (val) {
            case "yes":
                return true;
            case "no":
                return false;
            default:
                throw new IllegalArgumentException("Ошибка наполнения тега.");
        }
    }

    @Deprecated
    public void setCreateregistry(boolean value) {
        Node registry = getServerChild(REGISTRY);
        Node createRegistry = findNodeByName(CREATE_REGISTRY, registry);
        if (createRegistry == null) {//если тега нет, создать его
            Element createRegistryNode = document.createElement(CREATE_REGISTRY);
            if (value) {
                createRegistryNode.getFirstChild().setNodeValue("yes");
            } else {
                createRegistryNode.getFirstChild().setNodeValue("no");
            }
            registry.appendChild(createRegistryNode);
        } else {//если он есть, установить внутри значение ему
            if (value) {
                createRegistry.getFirstChild().setNodeValue("yes");
            } else {
                createRegistry.getFirstChild().setNodeValue("no");
            }
        }
    }

    @Deprecated
    public String getRegistryAddress() {
        return getRegistryChild(REGISTRY_ADDRESS).getNodeValue();
    }

    @Deprecated
    public void setRegistryAddress(String value) {
            Node registry = getServerChild(REGISTRY);
            Node registryAddress = findNodeByName(REGISTRY_ADDRESS, registry);
            if (registryAddress == null) {//если тега нет, создать его
                Element registryAddressNode = document.createElement(REGISTRY_ADDRESS);
                registryAddressNode.getFirstChild().setNodeValue(value);
                registry.appendChild(registryAddressNode);
            } else {//если он есть, установить внутри значение ему
                registryAddress.getFirstChild().setNodeValue(value);
            }        
    }

    @Deprecated
    public String getRegistryport() {
        return getRegistryChild(REGISTRY_PORT).getNodeValue();
    }

    @Deprecated
    public void setRegistryport(String value) {
        Node registry = getServerChild(REGISTRY);
        Node registryPort = findNodeByName(REGISTRY_PORT, registry);
        if (registryPort == null) {//если тега нет, создать его
            Element registryPortNode = document.createElement(REGISTRY_PORT);
            registryPortNode.getFirstChild().setNodeValue(value);
            registry.appendChild(registryPortNode);
        } else {//если он есть, установить внутри значение ему
            registryPort.getFirstChild().setNodeValue(value);
        }
    }

    private Node getBindedObject(String nameAttr) {
        Node server = getRmiChild(SERVER);
        NodeList serverChildren = server.getChildNodes();
        for (int i = 0; i < serverChildren.getLength(); i++) {
            Node serverChild = serverChildren.item(i);
            if (serverChild.getNodeName().equals(BINDED_OBJECT)) {
                NamedNodeMap bindedObjectAttr = serverChild.getAttributes();
                if (bindedObjectAttr.getNamedItem(NAME_ATTRIBUTE).getFirstChild().getNodeValue().equals(nameAttr)) {
                    return serverChildren.item(i);
                }
            }
        }
        return null;
    }

    @Deprecated
    public String getBindedObjectsClassAttr(String nameAttr) {
        Node bindedObject = getBindedObject(nameAttr);
        NamedNodeMap bindedObjectAttr = bindedObject.getAttributes();
        return bindedObjectAttr.getNamedItem(CLASS_ATTRIBUTE).getFirstChild().getNodeValue();
    }

    @Deprecated
    public void setBindedObjectsClassAttr(String nameAttr, String valAttr) {
        Node server = getRmiChild(SERVER);
        Node bindedObject = getBindedObject(nameAttr);//нашли нужный тег биндед
        if (bindedObject == null) {//если тега нет, создать его
            Element bindedObjectNode = document.createElement(BINDED_OBJECT);
            bindedObjectNode.setAttribute(NAME_ATTRIBUTE, nameAttr);
            bindedObjectNode.setAttribute(CLASS_ATTRIBUTE, valAttr);
            server.appendChild(bindedObjectNode);
        } else {//если он есть, установить внутри значение атрибуту
            NamedNodeMap bindedObjectAttr = bindedObject.getAttributes();
            bindedObjectAttr.getNamedItem(NAME_ATTRIBUTE).getFirstChild().setNodeValue(nameAttr);
            bindedObjectAttr.getNamedItem(CLASS_ATTRIBUTE).getFirstChild().setNodeValue(valAttr);
        }
    }

    @Deprecated
    public String getPolicypath() {
        return getClientChild(POLICY_PATH).getNodeValue();
    }

    @Deprecated
    public void setPolicypath(String value) {
        Node client = getRmiChild(CLIENT);
        Node policyPath = findNodeByName(POLICY_PATH, client);
        if (policyPath == null) {//если тега нет, создать его
            Element policyPathNode = document.createElement(POLICY_PATH);
            policyPathNode.getFirstChild().setNodeValue(value);
            client.appendChild(policyPathNode);
        } else {//если он есть, установить внутри значение ему
            policyPath.getFirstChild().setNodeValue(value);
        }
    }

    @Deprecated
    public boolean getUseCodeBaseOnly() {
        Node useCodeBaseOnly = getClientChild(USE_CODE_BASE_ONLY);
        String val = useCodeBaseOnly.getNodeValue();
        switch (val) {
            case "yes":
                return true;
            case "no":
                return false;
            default:
                throw new IllegalArgumentException("Ошибка наполнения тега.");
        }
    }

    @Deprecated
    public void setUseCodeBaseOnly(boolean value) {
        Node client = getRmiChild(CLIENT);
        Node useCodeBaseOnly = findNodeByName(USE_CODE_BASE_ONLY, client);
        if (useCodeBaseOnly == null) {//если тега нет, создать его
            Element useCodeBaseOnlyNode = document.createElement(USE_CODE_BASE_ONLY);
            if (value) {
                useCodeBaseOnlyNode.getFirstChild().setNodeValue("yes");
            } else {
                useCodeBaseOnlyNode.getFirstChild().setNodeValue("no");
            }
            client.appendChild(useCodeBaseOnlyNode);
        } else {//если он есть, установить внутри значение ему
            if (value) {
                useCodeBaseOnly.getFirstChild().setNodeValue("yes");
            } else {
                useCodeBaseOnly.getFirstChild().setNodeValue("no");
            }
        }
    }

    @Deprecated
    public String getClassprovider() {
        return getRmiChild(CLASS_PROVIDER).getNodeValue();
    }

    @Deprecated
    public void setClassprovider(String value) {
        Node appconf = document.getDocumentElement();
        Node rmi = appconf.getFirstChild();
        Node classProvider = findNodeByName(CLASS_PROVIDER, rmi);
        if (classProvider == null) {//если тега нет, создать его
            Element classProviderNode = document.createElement(CLASS_PROVIDER);
            classProviderNode.getFirstChild().setNodeValue(value);
            rmi.appendChild(classProviderNode);
        } else {//если он есть, установить внутри значение ему
            classProvider.getFirstChild().setNodeValue(value);
        }
    }
}