/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RPIS41.Kuzina.wdad.learn.xml;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

    private NodeList findNodeByName(String name, Node parentNode) {
        Element parent = (Element) parentNode;
        return parent.getElementsByTagName(name);
    }
    
    private NodeList getAllNotes(){
        return findNodeByName("note", document.getDocumentElement());
    }
    
    private User createUser(Node xmlUser){
        NamedNodeMap userAttr = xmlUser.getAttributes();
        Node nameNode = userAttr.getNamedItem("name");
        String name = (nameNode == null)? "" : nameNode.getFirstChild().getNodeValue();
        String mail = userAttr.getNamedItem("mail").getFirstChild().getNodeValue();
        return new User(name, mail);
    }
    
    private Node createUserNodeWithRights(User user, int newRights){
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
                return null;
            }
        }
        return userNode;
    }

    private Node getNoteByOwnerAndTitle(User owner, String title) {
        NodeList notes = getAllNotes();
        for (int i = 0; i < notes.getLength(); i++) {
            Node note = notes.item(i);
            if (((Element)findNodeByName("title", note).item(0)).getTextContent().equals(title)) {
                Node xmlOwner = findNodeByName("owner", note).item(0);                
                User notesOwner = createUser(xmlOwner);
                if(owner.equals(notesOwner)){
                    return note;
                }
            }
        }
        throw new TagNotFoundException();
    }
    
    private boolean textNodeContentIsNull(Node textNode){
        return textNode.getFirstChild() == null;
    }
    
    private Node getTextNode(Node note){
        return findNodeByName("text", note).item(0);
    }

    public String getNoteText(User owner, String title) throws ParserConfigurationException, SAXException, IOException {
        Element textNode = (Element) getTextNode(getNoteByOwnerAndTitle(owner, title));
        if (textNodeContentIsNull(textNode)) {
            return "";
        } else {
            return textNode.getTextContent();
        }
    }

    public void updateNote(User owner, String title, String newText) throws ParserConfigurationException, SAXException, IOException {
        Element textNode = (Element) getTextNode(getNoteByOwnerAndTitle(owner, title));
        if (textNodeContentIsNull(textNode)) {
            return;
        }
        textNode.setTextContent(newText);
    }

    private void setNewRights(Node user, int newRights) {
        Node privileges = user.getParentNode();
        NamedNodeMap userAttr = user.getAttributes();
        switch (newRights) {
            case RIGHT_R:
                userAttr.getNamedItem("rights").getFirstChild().setNodeValue(RIGHT_READ);
                break;
            case RIGHT_RW:
                userAttr.getNamedItem("rights").getFirstChild().setNodeValue(RIGHT_READ_WRITE);
                break;
            case RIGHT_NO: {
                privileges.removeChild(user);
            }
            break;
        }
    }
            
    public void setPrivileges(User user, String title, int newRights) throws ParserConfigurationException, SAXException, IOException {
        NodeList notes = getAllNotes();
        for (int i = 0; i < notes.getLength(); i++) {
            Node note = notes.item(i);
            if (((Element)findNodeByName("title", note).item(0)).getTextContent().equals(title)) {
                Node privileges = findNodeByName("privileges", note).item(0);
                NodeList users = findNodeByName("user", privileges);
                for (int j = 0; j < users.getLength(); j++) {
                    Node currentUser = users.item(j);
                    User notesUser = createUser(currentUser);
                    if (user.equals(notesUser)) {
                        setNewRights(currentUser, newRights);
                        return;
                    }
                }
                Node userNode = createUserNodeWithRights(user, newRights);
                if (userNode != null) {
                    privileges.appendChild(userNode);
                }
                return;
            }

    private Note createNote(Node xmlNote) throws ParseException {
        Node xmlOwner = findNodeByName("owner", xmlNote).item(0);
        User ownerNote = createUser(xmlOwner);
        String title = findNodeByName("title", xmlNote).item(0).getFirstChild().getNodeValue();
        String textString = findNodeByName("text", xmlNote).item(0).getFirstChild().getNodeValue();
        StringBuilder text = new StringBuilder(textString);
        SimpleDateFormat formatter = new SimpleDateFormat("DD-MM-YYYY");
        String date = findNodeByName("cdate", xmlNote).item(0).getFirstChild().getNodeValue();
        Date cdate = formatter.parse(date);
        return new Note(ownerNote, title, text, cdate);
    }

    public List<Note> getNotesByOwner(User owner) throws ParseException {
        List<Note> notesByOwner = new ArrayList<>();
        NodeList notes = getAllNotes();
        for (int i = 0; i < notes.getLength(); i++) {
            Node note = notes.item(i);
            Node xmlOwner = findNodeByName("owner", note).item(0);
            User notesOwner = createUser(xmlOwner);
            if (owner.equals(notesOwner)) {
                notesByOwner.add(createNote(note));
            }
        }
        return notesByOwner;

    }
    
}
