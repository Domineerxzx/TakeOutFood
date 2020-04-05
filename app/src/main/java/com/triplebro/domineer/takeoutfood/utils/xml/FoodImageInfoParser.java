package com.triplebro.domineer.takeoutfood.utils.xml;

import com.triplebro.domineer.takeoutfood.models.FoodImageInfo;

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
public class FoodImageInfoParser extends DefaultHandler{

    private List<FoodImageInfo> foodImageInfoList;
    private FoodImageInfo foodImageInfo;

    private StringBuilder food_id;
    private StringBuilder food_image;
    private StringBuilder phone_number;

    private String nodeName;

    public List<FoodImageInfo> parseXML(InputStream in) throws
            ParserConfigurationException, SAXException, IOException {

        SAXParserFactory spf = SAXParserFactory.newInstance();
        SAXParser saxParser = spf.newSAXParser();
        saxParser.parse(in,this);
        return foodImageInfoList;
    }


    @Override
    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);

        if(localName.equals("FoodImageInfoList")){
            foodImageInfoList = new ArrayList<FoodImageInfo>();
            food_id = new StringBuilder();
            food_image = new StringBuilder();
            phone_number = new StringBuilder();
        }else if(localName.equals("foodImageInfo")) {
            foodImageInfo = new FoodImageInfo();
            String value = attributes.getValue(0);
            foodImageInfo.set_id(Integer.parseInt(value));
        }
        nodeName=localName;
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws
            SAXException {
        super.endElement(uri, localName, qName);
        if(localName.equals("foodImageInfo")){
            foodImageInfoList.add(foodImageInfo);
        }else if(localName.equals("food_id")){
            foodImageInfo.setFood_id(Integer.parseInt(food_id.toString().trim()));
            food_id.setLength(0);
        }else if(localName.equals("food_image")){
            foodImageInfo.setFood_image(food_image.toString().trim());
            food_image.setLength(0);
        }else if(localName.equals("phone_number")){
            foodImageInfo.setPhone_number(phone_number.toString().trim());
            phone_number.setLength(0);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws
            SAXException {
        super.characters(ch, start, length);
        if(nodeName.equals("food_id")){
            food_id.append(ch,start,length);
        }else if(nodeName.equals("food_image")){
            food_image.append(ch,start,length);
        }else if(nodeName.equals("phone_number")){
            phone_number.append(ch,start,length);
        }
    }
}
