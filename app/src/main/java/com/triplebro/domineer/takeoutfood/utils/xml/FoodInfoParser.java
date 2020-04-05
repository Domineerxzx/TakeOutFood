package com.triplebro.domineer.takeoutfood.utils.xml;

import com.triplebro.domineer.takeoutfood.models.FoodInfo;

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
public class FoodInfoParser extends DefaultHandler{

    private List<FoodInfo> foodInfoList;
    private FoodInfo foodInfo;

    private StringBuilder food_name;
    private StringBuilder price;
    private StringBuilder food_image;
    private StringBuilder type_generalize_id;
    private StringBuilder type_concrete_id;
    private StringBuilder phone_number;

    private String nodeName;

    public List<FoodInfo> parseXML(InputStream in) throws
            ParserConfigurationException, SAXException, IOException {

        SAXParserFactory spf = SAXParserFactory.newInstance();
        SAXParser saxParser = spf.newSAXParser();
        saxParser.parse(in,this);
        return foodInfoList;
    }


    @Override
    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);

        if(localName.equals("FoodInfoList")){
            foodInfoList = new ArrayList<FoodInfo>();
            food_name = new StringBuilder();
            price = new StringBuilder();
            food_image = new StringBuilder();
            type_generalize_id = new StringBuilder();
            type_concrete_id = new StringBuilder();
            phone_number = new StringBuilder();
        }else if(localName.equals("foodInfo")) {
            foodInfo = new FoodInfo();
            String value = attributes.getValue(0);
            foodInfo.setFood_id(Integer.parseInt(value));
        }
        nodeName=localName;
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws
            SAXException {
        super.endElement(uri, localName, qName);
        if(localName.equals("foodInfo")){
            foodInfoList.add(foodInfo);
        }else if(localName.equals("food_name")){
            foodInfo.setFood_name(food_name.toString().trim());
            food_name.setLength(0);
        }else if(localName.equals("price")){
            foodInfo.setPrice(Integer.parseInt(price.toString().trim()));
            price.setLength(0);
        }else if(localName.equals("food_image")){
            foodInfo.setFood_image(food_image.toString().trim());
            food_image.setLength(0);
        }if(localName.equals("type_generalize_id")){
            foodInfo.setType_generalize_id(Integer.parseInt(type_generalize_id.toString().trim()));
            type_generalize_id.setLength(0);
        }else if(localName.equals("type_concrete_id")){
            foodInfo.setType_concrete_id(Integer.parseInt(type_concrete_id.toString().trim()));
            type_concrete_id.setLength(0);
        }else if(localName.equals("phone_number")){
            foodInfo.setPhone_number(phone_number.toString().trim());
            phone_number.setLength(0);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws
            SAXException {
        super.characters(ch, start, length);
        if(nodeName.equals("food_name")){
            food_name.append(ch,start,length);
        }else if(nodeName.equals("price")){
            price.append(ch,start,length);
        }else if(nodeName.equals("food_image")){
            food_image.append(ch,start,length);
        }else if(nodeName.equals("type_generalize_id")){
            type_generalize_id.append(ch,start,length);
        }else if(nodeName.equals("type_concrete_id")){
            type_concrete_id.append(ch,start,length);
        }else if(nodeName.equals("phone_number")){
            phone_number.append(ch,start,length);
        }
    }
}
