/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RPIS41.Kuzina.wdad.data.managers;

import RPIS41.Kuzina.wdad.utils.PreferencesConstantManager;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import org.xml.sax.SAXException;

/**
 *
 * @author Elvira
 */
public class TestPreferencesManager {
    
    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {
        PreferencesManager mng = PreferencesManager.getInstance();
        System.out.println(mng.getProperty(PreferencesConstantManager.CREATE_REGISTRY));
        System.out.println(mng.getProperties());
    }
    
}
