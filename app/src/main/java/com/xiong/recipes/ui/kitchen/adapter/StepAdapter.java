package com.xiong.recipes.ui.kitchen.adapter;

import android.text.Editable;
import android.text.TextWatcher;
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

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.ViewHolder> {
    private List<FoodStep> data;
    private TextChange textChange;
    public StepAdapter(List<FoodStep> data,TextChange textChange){
        super();
        this.data = data;
        this.textChange = textChange;
    }

    public void setData(List<FoodStep> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_step_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(data!=null){
            FoodStep foodStep = data.get(position);
            if(foodStep.getStepImage()!=null){
                ImageLoaderManager.getInstance().displayImageForView(holder.foodImage,foodStep.getStepImage());

            }
            if(foodStep.getStepInfo()!=null){
                holder.stepInfo.setText(foodStep.getStepInfo());
            }
            if(foodStep.getStepNum()!=null){
                holder.stepName.setText(foodStep.getStepNum());
            }else{
                holder.stepName.setText("第"+(position+1)+"步");
            }
            holder.foodImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int index = holder.getAdapterPosition();
                    textChange.addImage(index,v);
                }
            });
            holder.stepInfo.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }
                @Override
                public void afterTextChanged(Editable s) {
                    int index = holder.getAdapterPosition();
                    if(s!=null){
                        textChange.stepText(index,s.toString());
                    }else{
                        textChange.stepText(index,null);
                    }
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
        private TextView stepName;
        private TextView stepInfo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            foodImage = itemView.findViewById(R.id.food_image);
            stepName = itemView.findViewById(R.id.step_name);
            stepInfo = itemView.findViewById(R.id.step_info);
        }
    }

    public interface TextChange{
        void addImage(int position,View v);
        void stepText(int position,String text);
    }
}
