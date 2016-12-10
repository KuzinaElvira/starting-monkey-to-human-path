/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RPIS41.Kuzina.wdad.learn.xml;

import java.util.Objects;

/**
 *
 * @author Эльвира
 */
public class User {
    private String name;
    private String mail;
    static final User ALL = new User("ALL", "");
    
    public User(String name, String mail){
        this.name = name;
        this.mail = mail;
    }    
    
    public void setName(String name){
        this.name = name;
    }
    
    public void setMail(String mail){
        this.mail = mail;
    }
    
    public String getName(){
        return name;
    }
    
    public String getMail(){
        return mail;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + Objects.hashCode(this.name);
        hash = 47 * hash + Objects.hashCode(this.mail);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final User other = (User) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.mail, other.mail)) {
            return false;
        }
        return true;
    }
    
}
