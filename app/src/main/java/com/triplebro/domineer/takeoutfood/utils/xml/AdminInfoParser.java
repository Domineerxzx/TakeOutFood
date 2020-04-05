package com.triplebro.domineer.takeoutfood.utils.xml;

import com.triplebro.domineer.takeoutfood.models.AdminInfo;

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
public class AdminInfoParser extends DefaultHandler{

    private List<AdminInfo> adminInfoList;
    private AdminInfo adminInfo;

    private StringBuilder password;
    private StringBuilder nickname;
    private StringBuilder user_head;

    private String nodeName;

    public List<AdminInfo> parseXML(InputStream in) throws
            ParserConfigurationException, SAXException, IOException {

        SAXParserFactory spf = SAXParserFactory.newInstance();
        SAXParser saxParser = spf.newSAXParser();
        saxParser.parse(in,this);
        return adminInfoList;
    }


    @Override
    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);

        if(localName.equals("AdminInfoList")){
            adminInfoList = new ArrayList<AdminInfo>();
            password = new StringBuilder();
            nickname = new StringBuilder();
            user_head = new StringBuilder();
        }else if(localName.equals("adminInfo")) {
            adminInfo = new AdminInfo();
            String value = attributes.getValue(0);
            adminInfo.setPhone_number(value);
        }
        nodeName=localName;
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws
            SAXException {
        super.endElement(uri, localName, qName);
        if(localName.equals("adminInfo")){
            adminInfoList.add(adminInfo);
        }else if(localName.equals("password")){
            adminInfo.setPassword(password.toString().trim());
            password.setLength(0);
        }else if(localName.equals("nickname")){
            adminInfo.setNickname(nickname.toString().trim());
            nickname.setLength(0);
        }else if(localName.equals("user_head")){
            adminInfo.setUser_head(user_head.toString().trim());
            user_head.setLength(0);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws
            SAXException {
        super.characters(ch, start, length);
        if(nodeName.equals("password")){
            password.append(ch,start,length);
        }else if(nodeName.equals("nickname")){
            nickname.append(ch,start,length);
        }else if(nodeName.equals("user_head")){
            user_head.append(ch,start,length);
        }
    }
}
