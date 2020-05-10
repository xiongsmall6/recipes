package com.xiong.recipes.ui.kitchen.adapter;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xiong.recipes.R;
import com.xiong.recipes.entity.FoodMaterial;

import java.util.List;

public class MaterialAdapter extends RecyclerView.Adapter<MaterialAdapter.ViewHolder> {
    private List<FoodMaterial> data;
    private TextChange textChange;
    public MaterialAdapter(List<FoodMaterial> data,TextChange textChange){
        this.data = data;
        this.textChange = textChange;
    }

    public void setData(List<FoodMaterial> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_material_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(data!=null){
            FoodMaterial foodMaterial = data.get(position);
            if(foodMaterial.getMaterialName()!=null){
                holder.materialName.setText(foodMaterial.getMaterialName());
                holder.materialUnit.setText(foodMaterial.getMaterialUnit());
            }
            holder.materialName.addTextChangedListener(new TextWatcher() {
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
                        textChange.nameText(index,s.toString());
                    }else{
                        textChange.nameText(index,null);
                    }
                }
            });
            holder.materialUnit.addTextChangedListener(new TextWatcher() {
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
                        textChange.unitText(index,s.toString());
                    }else{
                        textChange.unitText(index,null);
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
        private TextView materialName;
        private TextView materialUnit;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            materialName = itemView.findViewById(R.id.material_name);
            materialUnit = itemView.findViewById(R.id.material_unit);
        }
    }
    public interface TextChange{
        void nameText(int position,String text);
        void unitText(int position,String text);
    }
}
