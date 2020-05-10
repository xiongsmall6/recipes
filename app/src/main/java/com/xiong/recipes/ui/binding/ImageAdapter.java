package com.xiong.recipes.ui.binding;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.xiong.recipes.R;
import com.xiong.recipes.imageload.app.ImageLoaderManager;

// DataBinding 高级用法
public class ImageAdapter {

    // placeHolder 占位图
    @BindingAdapter(value = {"imageUrl", "placeHolder"}, requireAll = false)
    public static void loadUrl(ImageView view, String url, Drawable placeHolder) {
        if (url != null) {
            ImageLoaderManager.getInstance().displayImageForCircle(view,url);
        } else {
            Glide.with(view.getContext()).load(R.drawable.man_default).placeholder(placeHolder).into(view);
        }
    }

    @BindingAdapter(value = {"foodImageUrl", "placeHolder"}, requireAll = false)
    public static void loadFoodUrl(ImageView view, String url, Drawable placeHolder) {
        if (url != null) {
            ImageLoaderManager.getInstance().displayImage(view,url);
        } else {
            Glide.with(view.getContext()).load(R.drawable.add_image).placeholder(placeHolder).into(view);
        }
    }

}
