package com.xiong.recipes.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.xiong.recipes.entity.Food;

import java.util.List;

@Dao
public interface FoodDao  {
    @Insert
    void insertFood(Food... foods);
    @Update
    void updateFood(Food... foods);
    @Delete
    void deleteFood(Food... foods);
    @Query("delete from food")
    void deleteAllFood();
    @Query("select * from food order by lid")
    LiveData<List<Food>> getAllFood();

}
