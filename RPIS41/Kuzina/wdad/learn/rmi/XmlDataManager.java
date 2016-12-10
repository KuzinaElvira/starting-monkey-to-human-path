package RPIS41.Kuzina.wdad.learn.rmi;


import RPIS41.Kuzina.wdad.learn.xml.Note;
import RPIS41.Kuzina.wdad.learn.xml.User;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Elvira
 */
public interface XmlDataManager extends Remote{
 
    public String getNote (User owner, String title) throws RemoteException;
    public void updateNote (User owner, String title, StringBuilder newText) throws RemoteException;
    public void setPrivileges (String noteTitle, User user, int newRights) throws RemoteException;
    public List<Note> getNotes(User owner) throws RemoteException;
    
}
