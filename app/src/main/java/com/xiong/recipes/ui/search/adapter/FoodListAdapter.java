package com.xiong.recipes.ui.search.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xiong.recipes.R;
import com.xiong.recipes.entity.Food;
import com.xiong.recipes.entity.FoodList;
import com.xiong.recipes.entity.FoodStep;
import com.xiong.recipes.imageload.app.ImageLoaderManager;
import com.xiong.recipes.ui.home.adapter.HomeFoodAdapter;

import java.util.List;

public class FoodListAdapter extends RecyclerView.Adapter<FoodListAdapter.ViewHolder> {
    private List<Food> data;
    private ItemClickListener mItemClickListener ;
    public FoodListAdapter(List<Food> data){
        super();
        this.data = data;
    }

    public void setData(List<Food> data) {
        this.data = data;
    }

    public void setOnItemClickListener(ItemClickListener itemClickListener){
        this.mItemClickListener = itemClickListener ;

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_item_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        if(data!=null){
            Food food = data.get(position);
            ImageLoaderManager.getInstance().displayImageForView(holder.foodImage,food.getFoodImage());
            holder.foodName.setText(food.getFoodName());
            holder.foodInfo.setText(food.getFoodInfo());
            holder.view.setOnClickListener(new View.OnClickListener() {
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
        if(data==null){
            return 0;
        }
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView foodImage;
        private TextView foodName;
        private TextView foodInfo;
        private View view;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            foodImage = itemView.findViewById(R.id.food_image);
            foodName = itemView.findViewById(R.id.food_name);
            foodInfo = itemView.findViewById(R.id.food_info);
            view = itemView;

        }
    }
    public interface ItemClickListener{
        public void onItemClick(int position) ;
    }
}
