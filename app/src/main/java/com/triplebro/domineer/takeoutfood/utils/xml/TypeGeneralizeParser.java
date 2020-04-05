package com.triplebro.domineer.takeoutfood.utils.xml;

import com.triplebro.domineer.takeoutfood.models.TypeGeneralizeInfo;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * @author Domineer
 * @data 2019/3/24,0:45
 * ----------为梦想启航---------
 * --Set Sell For Your Dream--
 */
public class TypeGeneralizeParser extends DefaultHandler{

    private List<TypeGeneralizeInfo> typeGeneralizeInfoList;
    private TypeGeneralizeInfo typeGeneralizeInfo;

    private StringBuilder type_generalize_name;

    private String nodeName;

    public List<TypeGeneralizeInfo> parseXML(InputStream in) throws
            ParserConfigurationException, SAXException, IOException {

        SAXParserFactory spf = SAXParserFactory.newInstance();
        SAXParser saxParser = spf.newSAXParser();
        saxParser.parse(in,this);
        return typeGeneralizeInfoList;
    }


    @Override
    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);

        if(localName.equals("TypeGeneralizeList")){
            typeGeneralizeInfoList = new ArrayList<TypeGeneralizeInfo>();
            type_generalize_name = new StringBuilder();
        }else if(localName.equals("typeGeneralize")) {
            typeGeneralizeInfo = new TypeGeneralizeInfo();
            String value = attributes.getValue(0);
            typeGeneralizeInfo.setType_generalize_id(Integer.parseInt(value));
        }
        nodeName=localName;
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws
            SAXException {
        super.endElement(uri, localName, qName);
        if(localName.equals("typeGeneralize")){
            typeGeneralizeInfoList.add(typeGeneralizeInfo);
        }else if(localName.equals("type_generalize_name")){
            typeGeneralizeInfo.setType_generalize_name(type_generalize_name.toString().trim());
            type_generalize_name.setLength(0);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws
            SAXException {
        super.characters(ch, start, length);
        if(nodeName.equals("type_generalize_name")){
            type_generalize_name.append(ch,start,length);
        }
    }
}
