package com.triplebro.domineer.takeoutfood.utils.xml;

import com.triplebro.domineer.takeoutfood.models.FoodSizeInfo;

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
public class FoodSizeInfoParser extends DefaultHandler{

    private List<FoodSizeInfo> foodSizeInfoList;
    private FoodSizeInfo foodSizeInfo;

    private StringBuilder food_id;
    private StringBuilder size_name;
    private StringBuilder size_count;
    private StringBuilder phone_number;

    private String nodeName;

    public List<FoodSizeInfo> parseXML(InputStream in) throws
            ParserConfigurationException, SAXException, IOException {

        SAXParserFactory spf = SAXParserFactory.newInstance();
        SAXParser saxParser = spf.newSAXParser();
        saxParser.parse(in,this);
        return foodSizeInfoList;
    }


    @Override
    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);

        if(localName.equals("FoodSizeInfoList")){
            foodSizeInfoList = new ArrayList<FoodSizeInfo>();
            food_id = new StringBuilder();
            size_name = new StringBuilder();
            size_count = new StringBuilder();
            phone_number = new StringBuilder();
        }else if(localName.equals("foodSizeInfo")) {
            foodSizeInfo = new FoodSizeInfo();
            String value = attributes.getValue(0);
            foodSizeInfo.set_id(Integer.parseInt(value));
        }
        nodeName=localName;
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws
            SAXException {
        super.endElement(uri, localName, qName);
        if(localName.equals("foodSizeInfo")){
            foodSizeInfoList.add(foodSizeInfo);
        }else if(localName.equals("food_id")){
            foodSizeInfo.setFood_id(Integer.parseInt(food_id.toString().trim()));
            food_id.setLength(0);
        }else if(localName.equals("size_name")){
            foodSizeInfo.setSize_name(size_name.toString().trim());
            size_name.setLength(0);
        }else if(localName.equals("size_count")){
            foodSizeInfo.setSize_count(Integer.parseInt(size_count.toString().trim()));
            size_count.setLength(0);
        }else if(localName.equals("phone_number")){
            foodSizeInfo.setPhone_number(phone_number.toString().trim());
            phone_number.setLength(0);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws
            SAXException {
        super.characters(ch, start, length);
        if(nodeName.equals("food_id")){
            food_id.append(ch,start,length);
        }else if(nodeName.equals("size_name")){
            size_name.append(ch,start,length);
        }else if(nodeName.equals("size_count")){
            size_count.append(ch,start,length);
        } else if(nodeName.equals("phone_number")){
            phone_number.append(ch,start,length);
        }
    }
}
