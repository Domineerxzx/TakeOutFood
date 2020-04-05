package com.triplebro.domineer.takeoutfood.controllers;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;

import com.triplebro.domineer.takeoutfood.models.AdminInfo;
import com.triplebro.domineer.takeoutfood.models.FoodImageInfo;
import com.triplebro.domineer.takeoutfood.models.FoodInfo;
import com.triplebro.domineer.takeoutfood.models.FoodSizeInfo;
import com.triplebro.domineer.takeoutfood.models.TypeConcreteInfo;
import com.triplebro.domineer.takeoutfood.models.TypeGeneralizeInfo;
import com.triplebro.domineer.takeoutfood.models.UserInfo;
import com.triplebro.domineer.takeoutfood.handlers.DataInsertHandler;
import com.triplebro.domineer.takeoutfood.properties.ProjectProperties;
import com.triplebro.domineer.takeoutfood.providers.DatabaseOP;
import com.triplebro.domineer.takeoutfood.utils.xml.AdminInfoParser;
import com.triplebro.domineer.takeoutfood.utils.xml.FoodImageInfoParser;
import com.triplebro.domineer.takeoutfood.utils.xml.FoodInfoParser;
import com.triplebro.domineer.takeoutfood.utils.xml.FoodSizeInfoParser;
import com.triplebro.domineer.takeoutfood.utils.xml.TypeConcreteParser;
import com.triplebro.domineer.takeoutfood.utils.xml.TypeGeneralizeParser;
import com.triplebro.domineer.takeoutfood.utils.xml.UserInfoParser;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

/**
 * @author Domineer
 * @data 2019/3/24,0:10
 * ----------为梦想启航---------
 * --Set Sell For Your Dream--
 */
public class SplashManager {

    private Context context;

    public SplashManager(Context context) {
        this.context = context;
    }

    public List<String> checkData(List<String> table_name) {
        List<String> nonentity_table_name = new ArrayList<>();
        DatabaseOP databaseOP = new DatabaseOP(context);
        for (String tableName : table_name) {
            boolean isExist = databaseOP.checkTableIsExist(tableName);
            if (!isExist) {
                nonentity_table_name.add(tableName);
            }
        }
        return nonentity_table_name;
    }

    public void uploadData(List<String> nonentity_table_name, DataInsertHandler dataInsertHandler) {

        AssetManager assets = context.getAssets();
        DatabaseOP databaseOP = new DatabaseOP(context);
        try {
            for (String nonentityTableName : nonentity_table_name) {
                InputStream inputStream = null;
                String assetsFileName;
                if (nonentityTableName.equals("adminInfo")) {
                    assetsFileName = "AdminInfoList.xml";
                    AdminInfoParser xmlParser = new AdminInfoParser();
                    inputStream = assets.open(assetsFileName);
                    List<AdminInfo> list = xmlParser.parseXML(inputStream);
                    for (AdminInfo adminInfo : list) {
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("phone_number", adminInfo.getPhone_number());
                        contentValues.put("password", adminInfo.getPassword());
                        contentValues.put("nickname", adminInfo.getNickname());
                        contentValues.put("user_head", adminInfo.getUser_head());
                        databaseOP.insertAdminInfo(contentValues);
                    }
                } else if (nonentityTableName.equals("userInfo")) {
                    assetsFileName = "UserInfoList.xml";
                    UserInfoParser xmlParser = new UserInfoParser();
                    inputStream = assets.open(assetsFileName);
                    List<UserInfo> list = xmlParser.parseXML(inputStream);
                    for (UserInfo userInfo : list) {
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("phone_number", userInfo.getPhone_number());
                        contentValues.put("password", userInfo.getPassword());
                        contentValues.put("nickname", userInfo.getNickname());
                        contentValues.put("user_head", userInfo.getUser_head());
                        databaseOP.insertUserInfo(contentValues);
                    }
                } else if (nonentityTableName.equals("typeGeneralize")) {
                    assetsFileName = "TypeGeneralizeList.xml";
                    TypeGeneralizeParser xmlParser = new TypeGeneralizeParser();
                    inputStream = assets.open(assetsFileName);
                    List<TypeGeneralizeInfo> list = xmlParser.parseXML(inputStream);
                    for (TypeGeneralizeInfo typeGeneralizeInfo : list) {
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("type_generalize_id", typeGeneralizeInfo.getType_generalize_id());
                        contentValues.put("type_generalize_name", typeGeneralizeInfo.getType_generalize_name());
                        databaseOP.insertTypeGeneralize(contentValues);
                    }
                } else if(nonentityTableName.equals("typeConcrete")){
                    assetsFileName = "TypeConcreteList.xml";
                    TypeConcreteParser xmlParser = new TypeConcreteParser();
                    inputStream = assets.open(assetsFileName);
                    List<TypeConcreteInfo> list = xmlParser.parseXML(inputStream);
                    for (TypeConcreteInfo typeConcreteInfo : list) {
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("type_concrete_id", typeConcreteInfo.getType_concrete_id());
                        contentValues.put("type_generalize_id", typeConcreteInfo.getType_generalize_id());
                        contentValues.put("type_concrete_name", typeConcreteInfo.getType_concrete_name());
                        contentValues.put("type_concrete_image", typeConcreteInfo.getType_concrete_image());
                        databaseOP.insertTypeConcrete(contentValues);
                    }
                }else if(nonentityTableName.equals("foodInfo")){
                    assetsFileName = "foodInfo.xml";
                    FoodInfoParser xmlParser = new FoodInfoParser();
                    inputStream = assets.open(assetsFileName);
                    List<FoodInfo> list = xmlParser.parseXML(inputStream);
                    for (FoodInfo foodInfo : list) {
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("food_id", foodInfo.getFood_id());
                        contentValues.put("price", foodInfo.getPrice());
                        contentValues.put("food_name", foodInfo.getFood_name());
                        contentValues.put("food_image", foodInfo.getFood_image());
                        contentValues.put("type_concrete_id", foodInfo.getType_concrete_id());
                        contentValues.put("phone_number", foodInfo.getPhone_number());
                        databaseOP.insertFoodInfo(contentValues);
                    }
                }else if(nonentityTableName.equals("foodImageInfo")){
                    assetsFileName = "foodImageInfo.xml";
                    FoodImageInfoParser xmlParser = new FoodImageInfoParser();
                    inputStream = assets.open(assetsFileName);
                    List<FoodImageInfo> list = xmlParser.parseXML(inputStream);
                    for (FoodImageInfo foodImageInfo : list) {
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("_id", foodImageInfo.get_id());
                        contentValues.put("food_id", foodImageInfo.getFood_id());
                        contentValues.put("food_image", foodImageInfo.getFood_image());
                        contentValues.put("phone_number", foodImageInfo.getPhone_number());
                        databaseOP.insertFoodImageInfo(contentValues);
                    }
                }else if(nonentityTableName.equals("foodSizeInfo")){
                    assetsFileName = "foodSizeInfo.xml";
                    FoodSizeInfoParser xmlParser = new FoodSizeInfoParser();
                    inputStream = assets.open(assetsFileName);
                    List<FoodSizeInfo> list = xmlParser.parseXML(inputStream);
                    for (FoodSizeInfo foodSizeInfo : list) {
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("_id", foodSizeInfo.get_id());
                        contentValues.put("food_id", foodSizeInfo.getFood_id());
                        contentValues.put("size_name", foodSizeInfo.getSize_name());
                        contentValues.put("size_count", foodSizeInfo.getSize_count());
                        contentValues.put("phone_number", foodSizeInfo.getPhone_number());
                        databaseOP.insertFoodSizeInfo(contentValues);
                    }
                }
            }
            dataInsertHandler.sendEmptyMessage(ProjectProperties.DATA_INSERT_SUCCESS);
        } catch (IOException e) {
            e.printStackTrace();
            dataInsertHandler.sendEmptyMessage(ProjectProperties.DATA_INSERT_FAILED);
        } catch (SAXException e) {
            e.printStackTrace();
            dataInsertHandler.sendEmptyMessage(ProjectProperties.DATA_INSERT_FAILED);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            dataInsertHandler.sendEmptyMessage(ProjectProperties.DATA_INSERT_FAILED);
        }
    }
}
