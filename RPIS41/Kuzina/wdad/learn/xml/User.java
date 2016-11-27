/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RPIS41.Kuzina.wdad.learn.xml;

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
}
