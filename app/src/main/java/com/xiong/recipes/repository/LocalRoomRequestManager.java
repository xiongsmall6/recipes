package com.xiong.recipes.repository;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.xiong.recipes.database.FoodDao;
import com.xiong.recipes.database.FoodDataBase;
import com.xiong.recipes.entity.Food;

import java.util.List;

public class LocalRoomRequestManager{
    private static LocalRoomRequestManager INSTANCE;
    private FoodDao foodDao;
    private LiveData<List<Food>> foodLive;

    private LocalRoomRequestManager(Context context){
        FoodDataBase foodDataBase = FoodDataBase.getFoodDataBase(context.getApplicationContext());
        foodDao = foodDataBase.getFoodDao();
        foodLive = foodDao.getAllFood();
    }

    public static LocalRoomRequestManager getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (LocalRoomRequestManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new LocalRoomRequestManager(context);
                }
            }
        }
        return INSTANCE;
    }
    public LiveData<List<Food>> getFoodLive() {
        return foodLive;
    }


    public void insertFood(Food... foods) {
        new InsertAsyncTask(foodDao).execute(foods);
    }

    public void updateFood(Food... foods) {
        new UpdateAsyncTask(foodDao).execute(foods);
    }

    public void deleteFood(Food... foods) {
        new DeleteAsyncTask(foodDao).execute(foods);
    }

    public void deleteAllFood() {
        new DeleteAllAsyncTask(foodDao).execute();
    }

    public void clearAndInsertFood(Food... foods) {
        new CleanInsertAsyncTask(foodDao).execute(foods);
    }

    static class CleanInsertAsyncTask extends AsyncTask<Food,Void,Void>{
        private FoodDao foodDao;
        CleanInsertAsyncTask(FoodDao foodDao){
            this.foodDao = foodDao;
        }

        @Override
        protected Void doInBackground(Food... foods) {
            foodDao.deleteAllFood();
            foodDao.insertFood(foods);
            return null;
        }
    }

    static class InsertAsyncTask extends AsyncTask<Food,Void,Void>{
        private FoodDao foodDao;
        InsertAsyncTask(FoodDao foodDao){
            this.foodDao = foodDao;
        }

        @Override
        protected Void doInBackground(Food... foods) {
            foodDao.insertFood(foods);
            return null;
        }
    }
    static class UpdateAsyncTask extends AsyncTask<Food,Void,Void>{
        private FoodDao foodDao;
        UpdateAsyncTask(FoodDao foodDao){
            this.foodDao = foodDao;
        }

        @Override
        protected Void doInBackground(Food... foods) {
            foodDao.updateFood(foods);
            return null;
        }
    }

    static class DeleteAsyncTask extends AsyncTask<Food,Void,Void>{
        private FoodDao foodDao;
        DeleteAsyncTask(FoodDao foodDao){
            this.foodDao = foodDao;
        }

        @Override
        protected Void doInBackground(Food... foods) {
            foodDao.deleteFood(foods);
            return null;
        }
    }

    static class DeleteAllAsyncTask extends AsyncTask<Void,Void,Void>{
        private FoodDao foodDao;
        DeleteAllAsyncTask(FoodDao foodDao){
            this.foodDao = foodDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            foodDao.deleteAllFood();
            return null;
        }
    }
}
