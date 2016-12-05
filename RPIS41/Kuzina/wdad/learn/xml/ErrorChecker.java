/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RPIS41.Kuzina.wdad.learn.xml;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 *
 * @author Эльвира
 */
public class ErrorChecker implements ErrorHandler{
    
    private boolean isError = false;

    @Override
    public void warning(SAXParseException exception) throws SAXException {
        System.err.println("Warning at " + exception.getLineNumber() + " . " + exception.getColumnNumber()
                        + "  -  " + exception.getMessage());
        isError = true;
    }

    @Override
    public void error(SAXParseException exception) throws SAXException {
        System.err.println("Error at {" + exception.getLineNumber() + "."
                        + exception.getColumnNumber() + "  -  " + exception.getMessage());
        isError = true;
    }

    @Override
    public void fatalError(SAXParseException exception) throws SAXException {
        System.err.println("Fatal error at {" + exception.getLineNumber() + " . "
                        + exception.getColumnNumber() + "  -  " + exception.getMessage());
        isError = true;
    }
    
    public boolean isError(){
        return isError;
    }
}
