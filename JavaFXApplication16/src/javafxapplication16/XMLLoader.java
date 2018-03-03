/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication16;

import java.io.File;
import java.util.ArrayList;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author Mtschannen
 */
class XMLLoader {
    static String output = "";
    static ArrayList<String> xmlStack = new ArrayList<>();
    
    public static String load(File xmlCourseFile) throws Exception {
        
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            
            DefaultHandler handler = new DefaultHandler() {
                
                @Override
                public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                    for(int i = 0; i < xmlStack.size(); i++){
                        output = output + "|     ";
                    }
                    output = output + "(start)" + qName + "\n";
                    xmlStack.add(qName);
                }
                
                @Override
                public void endElement(String uri, String localName, String qName) throws SAXException {
                    if(qName.equals(xmlStack.get(xmlStack.size()-1))){
                        xmlStack.remove(xmlStack.size()-1);
                    }
               
                    for(int i = 0; i < xmlStack.size(); i++){
                        output = output + "|     ";
                    }
                    output = output + "(end)" + qName + "\n";
                }
                
                @Override
                public void characters(char ch[], int start, int length) throws SAXException {
                        String content = new String(ch, start, length); 
                        content = content.replace("\n", "");
                        if(content != null && !content.equals("")){
                            for(int i = 0; i < xmlStack.size(); i++){
                                output = output + "|     ";
                            }
                            output = output + content + "\n";
                        }
                }
            };
            
            saxParser.parse(xmlCourseFile.getAbsoluteFile(), handler);
            
        } catch (Exception e) {
            throw e;
        }
        return output;
         
    }
}
