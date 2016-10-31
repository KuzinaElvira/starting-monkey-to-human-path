/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RPIS41.Kuzina.wdad.learn.xml;
//
//import org.w3c.dom.Document;
//import org.w3c.dom.NamedNodeMap;
//import org.w3c.dom.NodeList;

/**
 *
 * @author Эльвира
 */
public class User {
    private String name;
    private String mail;
    private String rights;
    public static final String NO_RIGHTS = "";
    public static final String RIGHTS_R = "R";
    public static final String RIGHTS_RW = "RW";
    
    public User(String name, String mail, String rights){
        this.name = name;
        this.mail = mail;
        if(rights.equals(""))
            rights = RIGHTS_R;
        else this.rights = rights;
    }    
    
    public void setName(String name){
        this.name = name;
    }
    
    public void setMail(String mail){
        this.mail = mail;
    }
        
    public void setRights(String rights){
        this.rights = rights;
    }
    
    public String getName(){
        return name;
    }
    
    public String getMail(){
        return mail;
    }
    
    public String getRights(){
        return rights;
    }
    
//    public static User findOwner(Document document, String title){
//        NodeList notes = document.getDocumentElement().getChildNodes();
//        if(notes.getLength() == 0)
//            return null;
//        String ownerName = "";
//        String ownerMail = "";
//        for(int i = 0; i < notes.getLength(); i++){
//            NodeList notesChilds = notes.item(i).getChildNodes();
//            if(notesChilds.item(i).getNodeName().equals("title") && notesChilds.item(i).getNodeValue().equals(title)){
//                NamedNodeMap ownerAttr;
//                if(notesChilds.item(i).getNodeName().equals("owner")){
//                    ownerAttr = notesChilds.item(i).getAttributes();
//                    for(int j = 0; j < ownerAttr.getLength(); j++){
//                        if(ownerAttr.item(j).getNodeName().equals("name"))
//                            ownerName = ownerAttr.item(j).getNodeValue();
//                        else ownerMail = ownerAttr.item(j).getNodeValue();
//                    }
//                }
//            }
//        }
//        return new User(ownerName, ownerMail, NO_RIGHTS);
//    }
//    
//    public static User findUser(Document document){
//        NodeList notes = document.getDocumentElement().getChildNodes();
//        if(notes.getLength() == 0)
//            return null;
//        String userName = "";
//        String userMail = "";
//        String userRights = RIGHTS_R;
//        for(int i = 0; i < notes.getLength(); i++){
//            NodeList notesChilds = notes.item(i).getChildNodes();
//            if(notesChilds.item(i).getNodeName().equals("privileges")){
//                NodeList priveleges = notesChilds.item(i).getChildNodes();
//                for(int j = 0; j < priveleges.getLength(); j++){
//                    NodeList privChilds = priveleges.item(j).getChildNodes();
//                    for(int k = 0; k < priveleges.getLength(); k++){
//                        if(privChilds.item(k).getNodeName().equals("user")){
//                            NamedNodeMap userAttr = privChilds.item(k).getAttributes();
//                            for(int x = 0; x < userAttr.getLength(); x++){
//                                if(userAttr.item(x).getNodeName().equals("name"))
//                                    userName = userAttr.item(x).getNodeValue();
//                                if(userAttr.item(x).getNodeName().equals("mail"))
//                                    userMail = userAttr.item(x).getNodeValue();
//                                if(userAttr.item(x).getNodeName().equals("rights"))
//                                    userRights = userAttr.item(x).getNodeValue();
//                            }
//                        }
//                    }
//                }
//            }
//        }
//        return new User(userName, userMail, userRights);
//    }
}
