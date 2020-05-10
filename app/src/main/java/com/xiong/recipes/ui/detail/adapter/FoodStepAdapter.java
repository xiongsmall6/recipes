package com.xiong.recipes.ui.detail.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xiong.recipes.R;
import com.xiong.recipes.entity.FoodStep;
import com.xiong.recipes.imageload.app.ImageLoaderManager;

import java.util.List;

public class FoodStepAdapter extends RecyclerView.Adapter<FoodStepAdapter.ViewHolder> {
    private List<FoodStep> data;
    public FoodStepAdapter(List<FoodStep> data){
        super();
        this.data = data;
    }

    public void setData(List<FoodStep> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.step_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(data!=null){
            FoodStep foodStep = data.get(position);
            ImageLoaderManager.getInstance().displayImageForView(holder.foodImage,foodStep.getStepImage());
            holder.stepName.setText("第"+(foodStep.getStepNum())+"步");
            holder.stepInfo.setText(foodStep.getStepInfo());
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
        private TextView stepName;
        private TextView stepInfo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            foodImage = itemView.findViewById(R.id.food_image);
            stepName = itemView.findViewById(R.id.step_name);
            stepInfo = itemView.findViewById(R.id.step_info);

        }
    }
}
