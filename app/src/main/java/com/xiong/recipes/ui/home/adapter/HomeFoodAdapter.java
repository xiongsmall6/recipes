package com.xiong.recipes.ui.home.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xiong.recipes.R;
import com.xiong.recipes.entity.Food;
import com.xiong.recipes.imageload.app.ImageLoaderManager;

import java.util.List;

public class HomeFoodAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Food> foods;
    private ItemClickListener mItemClickListener ;
    public HomeFoodAdapter( List<Food> foods){
        super();
        this.foods = foods;
    }
    public void setOnItemClickListener(ItemClickListener itemClickListener){
        this.mItemClickListener = itemClickListener ;

    }
    public void setFoods(List<Food> foods) {
        this.foods = foods;
    }

    class TitleViewHolder extends RecyclerView.ViewHolder{
        public TextView timeTitle;
        public TitleViewHolder(@NonNull View itemView) {
            super(itemView);
            timeTitle = itemView.findViewById(R.id.title_text);
        }
    }

    class FoodViewHolder extends RecyclerView.ViewHolder{
        public View view;
        public ImageView foodImage;
        public TextView foodName;
        public TextView foodInfo;
        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            foodImage = itemView.findViewById(R.id.food_image);
            foodName = itemView.findViewById(R.id.food_name);
            foodInfo = itemView.findViewById(R.id.food_info);
            view = itemView;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return foods.get(position).getFoodType();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        if(viewType==0){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.title_item_layout,parent,false);
            viewHolder = new TitleViewHolder(view);
        }else{
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_item_layout,parent,false);
            viewHolder = new FoodViewHolder(view);
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        Food food = foods.get(position);
        if(food.getFoodType()==0){
            TitleViewHolder titleViewHolder = (TitleViewHolder)holder;
            titleViewHolder.timeTitle.setText(food.getTitleName());
        }else {
            FoodViewHolder foodViewHolder = (FoodViewHolder)holder;
            foodViewHolder.foodName.setText(food.getFoodName());
            foodViewHolder.foodInfo.setText(food.getFoodInfo());
            ImageLoaderManager.getInstance().displayImageForView(foodViewHolder.foodImage,food.getFoodImage());
            foodViewHolder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int index = holder.getAdapterPosition();
                    mItemClickListener.onItemClick(index);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        if(foods==null){
            return 0;
        }
        return foods.size();
    }

    public interface ItemClickListener{
        public void onItemClick(int position) ;
    }
}
