/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RPIS41.Kuzina.wdad.learn.rmi;

import RPIS41.Kuzina.wdad.learn.xml.Note;
import RPIS41.Kuzina.wdad.learn.xml.User;
import RPIS41.Kuzina.wdad.learn.xml.XmlTask;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 *
 * @author Elvira
 */
public class XmlDataManagerImpl implements XmlDataManager{
    
    private XmlTask task;

    public XmlDataManagerImpl(XmlTask task) {
        this.task = task;
    }    
    
    @Override
    public String getNote(User owner, String title) {
        try {
            return task.getNoteText(owner, title);
        } catch (ParserConfigurationException | SAXException | IOException ex) {
        }
        return null;
    }

    @Override
    public void updateNote(User owner, String title, StringBuilder newText) {
        try {
            task.updateNote(owner, title, newText.toString());
        } catch (ParserConfigurationException | SAXException | IOException ex) {
        }
    }

    @Override
    public void setPrivileges(String noteTitle, User user, int newRights) {
        try {
            task.setPrivileges(user, noteTitle, newRights);
        } catch (ParserConfigurationException | SAXException | IOException ex) {
        }
    }

    @Override
    public List<Note> getNotes(User owner) {
        try {
            return task.getNotesByOwner(owner);
        } catch (ParseException ex) {    
            throw new RuntimeException(ex);
        }
    }
}
