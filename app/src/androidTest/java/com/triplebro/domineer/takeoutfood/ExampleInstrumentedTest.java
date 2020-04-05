package com.triplebro.domineer.takeoutfood;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.triplebro.domineer.takeoutfood.models.FoodInfo;
import com.triplebro.domineer.takeoutfood.providers.DatabaseOP;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.triplebro.domineer.takeoutfood", appContext.getPackageName());
    }

    @Test
    public void databaseTest(){
        DatabaseOP databaseOP = new DatabaseOP(InstrumentationRegistry.getTargetContext());
        List<FoodInfo> foodInfoList = databaseOP.getFoodInfoList();
        if(foodInfoList.size()>0){
            assertNotNull(foodInfoList);
        }else{
            assertNull(foodInfoList);
        }
    }
}
