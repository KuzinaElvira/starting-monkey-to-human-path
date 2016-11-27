/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RPIS41.Kuzina.wdad.learn.xml;

import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
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
public class XmlTask {

    private final int RIGHT_NO = 0;
    private final int RIGHT_R = 1;
    private final int RIGHT_RW = 3;
    private final String RIGHT_READ = "R";
    private final String RIGHT_READ_WRITE = "RW";
    private Document document;

    public XmlTask(Document document) {
        this.document = document;
    }

    private Node findNodeByName(String name, Node parentNode) {
        NodeList childs = parentNode.getChildNodes();
        for (int i = 0; i < childs.getLength(); i++) {
            if (childs.item(i).getNodeType() == Node.TEXT_NODE) {
                continue;
            }
            if (childs.item(i).getNodeName().equals(name)) {
                return childs.item(i);
            }
        }
        return null;
    }

    private Node getTextNode(User owner, String title) {
        NodeList notes = document.getDocumentElement().getChildNodes();//получили список note-ов
        if (notes.getLength() == 1) {
            return null;
        }
        for (int i = 0; i < notes.getLength(); i++) {
            Node note = notes.item(i);
            if (note.getNodeType() == Node.TEXT_NODE) {
                continue;//убираем #text между note-ами
            }
            if (!findNodeByName("title", note).getFirstChild().getNodeValue().equals(title)) {
                continue;
            }
            Node xmlOwner = findNodeByName("owner", note);
            NamedNodeMap ownerAttr = xmlOwner.getAttributes();
            if (ownerAttr.getNamedItem("name").getFirstChild().getNodeValue().equals(owner.getName())
                    && ownerAttr.getNamedItem("mail").getFirstChild().getNodeValue().equals(owner.getMail())) {
                return findNodeByName("text", note);
            }
        }
        return null;
    }
    
    private boolean textNodeIsNull(Node textNode){
        return textNode == null || textNode.getFirstChild() == null;
    }

    public String getNoteText(User owner, String title) throws ParserConfigurationException, SAXException, IOException {
        Node textNode = getTextNode(owner, title);
        if (textNodeIsNull(textNode)) {
            return null;
        } else {
            return textNode.getFirstChild().getNodeValue();
        }
    }

    public void updateNote(User owner, String title, String newText) throws ParserConfigurationException, SAXException, IOException {
        Node textNode = getTextNode(owner, title);
        if (textNodeIsNull(textNode)) {
            return;
        }
        textNode.getFirstChild().setTextContent(newText);
    }

    private void setNewRights(NodeList usersAll, Node privileges, int userNum, NamedNodeMap userAttr, int newRights) {
        switch (newRights) {
            case RIGHT_R:
                userAttr.getNamedItem("rights").getFirstChild().setNodeValue(RIGHT_READ);
                break;
            case RIGHT_RW:
                userAttr.getNamedItem("rights").getFirstChild().setNodeValue(RIGHT_READ_WRITE);
                break;
            case RIGHT_NO: {
                privileges.removeChild(usersAll.item(userNum));
            }
            break;
        }
    }
            
    public void setPrivileges(User user, String title, int newRights) throws ParserConfigurationException, SAXException, IOException {
        NodeList notes = document.getDocumentElement().getChildNodes();//получили список note-ов
        if (notes.getLength() == 1) {
            return;
        }
        for (int i = 0; i < notes.getLength(); i++) {
            Node note = notes.item(i);
            if (note.getNodeType() == Node.TEXT_NODE) {
                continue;//убираем #text между note-ами
            }
            if (!findNodeByName("title", note).getFirstChild().getNodeValue().equals(title)) {
                continue;
            }
            Node privileges = findNodeByName("privileges", note);
            NodeList usersAll = privileges.getChildNodes();//получает детей из privileges (либо кучу user-ов, либо ALL)
            for (int j = 0; j < usersAll.getLength(); j++) {
                if (usersAll.item(j).getNodeType() == Node.TEXT_NODE) {
                    continue;//убираем #text между user-ами
                }
                if (!usersAll.item(j).getNodeName().equals("ALL")) {
                    Node currentUser = usersAll.item(j);
                    NamedNodeMap userAttr = currentUser.getAttributes();//получает список атрибутов у текущего юзера
                    if(userAttr.getNamedItem("name") == null){
                        if(user.getName() == null){
                            setNewRights(usersAll, privileges, j, userAttr, newRights);
                            return;
                        }
                        break;
                    }                        
                    if(user.getName() == null || user.getName().equals(""))
                        break;
                    if ((user.getName().equals(userAttr.getNamedItem("name").getFirstChild().getNodeValue()))
                            && (user.getMail().equals(userAttr.getNamedItem("mail").getFirstChild().getNodeValue()))) {
                            setNewRights(usersAll, privileges, j, userAttr, newRights);
                            return;
                    }
                } else {
                    if(usersAll.getLength() != 3)
                        privileges.removeChild(usersAll.item(j));
                    return;
                }
            }
            Element userNode = document.createElement("user");
            userNode.setAttribute("name", user.getName());
            userNode.setAttribute("mail", user.getMail());
            switch (newRights) {
                case RIGHT_R:
                    userNode.setAttribute("rights", RIGHT_READ);
                    break;
                case RIGHT_RW:
                    userNode.setAttribute("rights", RIGHT_READ_WRITE);
                    break;
                case RIGHT_NO: {
                    return;
                }
            }
            privileges.appendChild(userNode);
        }
    }
}
