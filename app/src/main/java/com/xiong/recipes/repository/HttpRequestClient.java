package com.xiong.recipes.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.xiong.lib_network.okhttp.CommonOkHttpClient;
import com.xiong.lib_network.okhttp.listener.DisposeDataHandle;
import com.xiong.lib_network.okhttp.listener.DisposeDataListener;
import com.xiong.lib_network.okhttp.request.CommonRequest;
import com.xiong.lib_network.okhttp.request.RequestParams;
import com.xiong.recipes.constant.Constant;
import com.xiong.recipes.entity.Food;
import com.xiong.recipes.entity.FoodList;
import com.xiong.recipes.entity.IndexFood;
import com.xiong.recipes.entity.ResultData;
import com.xiong.recipes.entity.UserData;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;

public class HttpRequestClient {
    private HttpRequestClient(){}
    public static class SingleHelper{
        private static HttpRequestClient httpRequestClient = new HttpRequestClient();
    }

    public static HttpRequestClient getInstance(){
        return SingleHelper.httpRequestClient;
    }

    public void queryFoodDetail(int food_id, final MutableLiveData<Food> food){
        try {
            RequestParams params = new RequestParams();
            params.put("food_id",food_id+"");
            CommonOkHttpClient.post(CommonRequest.createPostRequest(Constant.FOOD_DETAIL, params), new DisposeDataHandle(new DisposeDataListener() {
                @Override
                public void onSuccess(Object responseObj) {
                    Log.d("FoodDetailViewModel","获取首页数据成功");
                    Food data = (Food)responseObj;
                    food.setValue(data);
                }
                @Override
                public void onFailure(Object reasonObj) {
                    Log.e("FoodDetailViewModel","获取首页数据失败");
                }
            }, Food.class));
        }catch (Exception e){

        }

    }

    public void queryDayData(final MutableLiveData<List<Food>> foodList){
        CommonOkHttpClient.get(CommonRequest.createGetRequest(Constant.DAY_FOOD, null), new DisposeDataHandle(new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                Log.d("HomeViewModel","获取首页数据成功");
                IndexFood indexFood = (IndexFood)responseObj;
                //  List<Food> mornings = indexFood.getMorning();
                // Log.d("HomeViewModel",mornings.get(0).toString());
                List<Food> data = new ArrayList<>();
                data.add(new Food(0,"早餐"));
                data.addAll(indexFood.getMorning());
                data.add(new Food(0,"午餐"));
                data.addAll(indexFood.getNoon());
                data.add(new Food(0,"晚餐"));
                data.addAll(indexFood.getNight());
                foodList.setValue(data);
            }

            @Override
            public void onFailure(Object reasonObj) {
                Log.e("HomeViewModel","获取首页数据失败");
            }
        }, IndexFood.class));
    }

    public void searchFood(String foodName,String create_user,String type,int page,int limit,final MutableLiveData<List<Food>> foodList){
        try {
            RequestParams params = new RequestParams();
            if(foodName!=null){
                params.put("search",foodName);
            }
            if(create_user!=null){
                params.put("create_user",create_user);
            }
            if(type!=null){
                params.put("food_type",type);
            }
            params.put("page",page+"");
            params.put("limit",limit+"");
            CommonOkHttpClient.post(CommonRequest.createPostRequest(Constant.FOOD_SEARCH, params), new DisposeDataHandle(new DisposeDataListener() {
                @Override
                public void onSuccess(Object responseObj) {
                    Log.d("SearchViewModel","获取搜索数据成功");
                    FoodList data = (FoodList)responseObj;
                    if(data.getCode()==0){
                        foodList.setValue(data.getData());
                    }
                }

                @Override
                public void onFailure(Object reasonObj) {
                    Log.e("SearchViewModel","获取首页成功失败");
                }
            }, FoodList.class));
        }catch (Exception e){

        }
    }


    public void register(String userName,String password,final  CallBack callBack) {
        try {
            RequestParams params = new RequestParams();
            params.put("user_name", userName);
            params.put("password", password);
            CommonOkHttpClient.post(CommonRequest.createPostRequest(Constant.USER_REGISTER, params), new DisposeDataHandle(new DisposeDataListener() {
                @Override
                public void onSuccess(Object responseObj) {
                    callBack.onSuccess(responseObj);
                }

                @Override
                public void onFailure(Object reasonObj) {
                    callBack.onFailure(reasonObj);
                }
            }, UserData.class));
        } catch (Exception e) {

        }
    }

    public void login(String userName,String password,final  CallBack callBack) {
        try {
            RequestParams params = new RequestParams();
            params.put("user_name", userName);
            params.put("password", password);
            CommonOkHttpClient.post(CommonRequest.createPostRequest(Constant.USER_LOGIN, params), new DisposeDataHandle(new DisposeDataListener() {
                @Override
                public void onSuccess(Object responseObj) {
                    callBack.onSuccess(responseObj);
                }

                @Override
                public void onFailure(Object reasonObj) {
                    callBack.onFailure(reasonObj);
                }
            }, UserData.class));
        } catch (Exception e) {

        }
    }

    public void update(String userId,String nikeName,String userImage,String gender,String age,final  CallBack callBack) {
        try {
            RequestParams params = new RequestParams();
            params.put("user_id", userId);
            params.put("nike_name", nikeName);
            params.put("gender", gender);
            params.put("age", age);
            Request request = null;
            if(userImage!=null&&userImage!=""){
                params.put("user_image",new File(userImage));
                request = CommonRequest.createMultiPostRequest(Constant.USER_UPDATER, params);
            }else {
                request = CommonRequest.createPostRequest(Constant.USER_UPDATER, params);
            }
            CommonOkHttpClient.post(request, new DisposeDataHandle(new DisposeDataListener() {
                @Override
                public void onSuccess(Object responseObj) {
                    callBack.onSuccess(responseObj);
                }

                @Override
                public void onFailure(Object reasonObj) {
                    callBack.onFailure(reasonObj);
                }
            }, UserData.class));
        } catch (Exception e) {

        }
    }


    public void uploadImage(String userId,String userImage, final  CallBack callBack) {
        try {
            RequestParams params = new RequestParams();
            params.put("user_id", userId);
            params.put("user_image",new File(userImage));
            Request request = CommonRequest.createMultiPostRequest(Constant.IMAGE_UPDATE, params);
            CommonOkHttpClient.post(request, new DisposeDataHandle(new DisposeDataListener() {
                @Override
                public void onSuccess(Object responseObj) {
                    callBack.onSuccess(responseObj);
                }

                @Override
                public void onFailure(Object reasonObj) {
                    callBack.onFailure(reasonObj);
                }
            }, ResultData.class));
        } catch (Exception e) {

        }
    }

    public void foodAdd(String foodJson , final  CallBack callBack) {
        try {
            RequestParams params = new RequestParams();
            params.put("food", foodJson);
            Request request = CommonRequest.createPostRequest(Constant.FOOD_ADD, params);
            CommonOkHttpClient.post(request, new DisposeDataHandle(new DisposeDataListener() {
                @Override
                public void onSuccess(Object responseObj) {
                    callBack.onSuccess(responseObj);
                }

                @Override
                public void onFailure(Object reasonObj) {
                    callBack.onFailure(reasonObj);
                }
            }, ResultData.class));
        } catch (Exception e) {

        }
    }


    public interface CallBack{
        public void onSuccess(Object responseObj);
        public void onFailure(Object reasonObj);
    }

}
