package com.triplebro.domineer.takeoutfood.utils.xml;

import com.triplebro.domineer.takeoutfood.models.TypeConcreteInfo;

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
public class TypeConcreteParser extends DefaultHandler{

    private List<TypeConcreteInfo> typeConcreteInfoList;
    private TypeConcreteInfo typeConcreteInfo;

    private StringBuilder type_generalize_id;
    private StringBuilder type_concrete_name;
    private StringBuilder type_concrete_image;

    private String nodeName;

    public List<TypeConcreteInfo> parseXML(InputStream in) throws
            ParserConfigurationException, SAXException, IOException {

        SAXParserFactory spf = SAXParserFactory.newInstance();
        SAXParser saxParser = spf.newSAXParser();
        saxParser.parse(in,this);
        return typeConcreteInfoList;
    }


    @Override
    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);

        if(localName.equals("TypeConcreteList")){
            typeConcreteInfoList = new ArrayList<TypeConcreteInfo>();
            type_generalize_id = new StringBuilder();
            type_concrete_name = new StringBuilder();
            type_concrete_image = new StringBuilder();
        }else if(localName.equals("typeConcrete")) {
            typeConcreteInfo = new TypeConcreteInfo();
            String value = attributes.getValue(0);
            typeConcreteInfo.setType_concrete_id(Integer.parseInt(value));
        }
        nodeName=localName;
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws
            SAXException {
        super.endElement(uri, localName, qName);
        if(localName.equals("typeConcrete")){
            typeConcreteInfoList.add(typeConcreteInfo);
        }else if(localName.equals("type_generalize_id")){
            typeConcreteInfo.setType_generalize_id(Integer.parseInt(type_generalize_id.toString().trim()));
            type_generalize_id.setLength(0);
        }else if(localName.equals("type_concrete_name")){
            typeConcreteInfo.setType_concrete_name(type_concrete_name.toString().trim());
            type_concrete_name.setLength(0);
        }else if(localName.equals("type_concrete_image")){
            typeConcreteInfo.setType_concrete_image(type_concrete_image.toString().trim());
            type_concrete_image.setLength(0);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws
            SAXException {
        super.characters(ch, start, length);
        if(nodeName.equals("type_generalize_id")){
            type_generalize_id.append(ch,start,length);
        }else if(nodeName.equals("type_concrete_name")){
            type_concrete_name.append(ch,start,length);
        }else if(nodeName.equals("type_concrete_image")){
            type_concrete_image.append(ch,start,length);
        }
    }
}
