package com.xiong.recipes.ui.detail.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xiong.recipes.R;
import com.xiong.recipes.entity.FoodMaterial;

import java.util.List;

public class FoodMaterialAdapter extends RecyclerView.Adapter<FoodMaterialAdapter.ViewHolder> {
    private List<FoodMaterial> data;
    public FoodMaterialAdapter(List<FoodMaterial> data){
        this.data = data;
    }

    public void setData(List<FoodMaterial> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.material_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(data!=null){
            FoodMaterial foodMaterial = data.get(position);
            holder.materialName.setText(foodMaterial.getMaterialName());
            holder.materialUnit.setText(foodMaterial.getMaterialUnit());
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
        private TextView materialName;
        private TextView materialUnit;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            materialName = itemView.findViewById(R.id.material_name);
            materialUnit = itemView.findViewById(R.id.material_unit);
        }
    }
}
