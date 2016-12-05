/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RPIS41.Kuzina.wdad.learn.xml;

import java.util.Date;
import java.util.HashMap;

/**
 *
 * @author Elvira
 */
public class Note {

    private User owner;
    private String title;
    private StringBuilder text;
    private Date cdate;
    private HashMap<User, Integer> privelegesMap;

    public Note(User owner, String title, StringBuilder text, Date cdate) {
        this.owner = owner;
        this.title = title;
        this.text = text;
        this.cdate = cdate;
    }   

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public StringBuilder getText() {
        return text;
    }

    public void setText(StringBuilder text) {
        this.text = text;
    }

    public Date getCdate() {
        return cdate;
    }

    public void setCdate(Date cdate) {
        this.cdate = cdate;
    }

    public void addPriveleges(User user, int rights){
        privelegesMap.put(user, rights);
    }

    public void deletePriveleges(User user){
        privelegesMap.remove(user);
    }
}
