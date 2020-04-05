package com.triplebro.domineer.takeoutfood.utils.xml;

import com.triplebro.domineer.takeoutfood.models.UserInfo;

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
public class UserInfoParser extends DefaultHandler{

    private List<UserInfo> userInfoList;
    private UserInfo userInfo;

    private StringBuilder password;
    private StringBuilder nickname;
    private StringBuilder user_head;

    private String nodeName;

    public List<UserInfo> parseXML(InputStream in) throws
            ParserConfigurationException, SAXException, IOException {

        SAXParserFactory spf = SAXParserFactory.newInstance();
        SAXParser saxParser = spf.newSAXParser();
        saxParser.parse(in,this);
        return userInfoList;
    }


    @Override
    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);

        if(localName.equals("UserInfoList")){
            userInfoList = new ArrayList<UserInfo>();
            password = new StringBuilder();
            nickname = new StringBuilder();
            user_head = new StringBuilder();
        }else if(localName.equals("userInfo")) {
            userInfo = new UserInfo();
            String value = attributes.getValue(0);
            userInfo.setPhone_number(value);
        }
        nodeName=localName;
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws
            SAXException {
        super.endElement(uri, localName, qName);
        if(localName.equals("userInfo")){
            userInfoList.add(userInfo);
        }else if(localName.equals("password")){
            userInfo.setPassword(password.toString().trim());
            password.setLength(0);
        }else if(localName.equals("nickname")){
            userInfo.setNickname(nickname.toString().trim());
            nickname.setLength(0);
        }else if(localName.equals("user_head")){
            userInfo.setUser_head(user_head.toString().trim());
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
