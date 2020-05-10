package com.xiong.recipes.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.xiong.recipes.entity.Food;

@Database(entities = {Food.class},version = 1,exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class FoodDataBase extends RoomDatabase {
    private static FoodDataBase foodDataBase;

    public static synchronized FoodDataBase getFoodDataBase(Context context){
        if (foodDataBase==null){
            foodDataBase = Room.databaseBuilder(context.getApplicationContext(),FoodDataBase.class,"food_database").build();
        }
        return foodDataBase;
    }
    public abstract FoodDao getFoodDao();
}
