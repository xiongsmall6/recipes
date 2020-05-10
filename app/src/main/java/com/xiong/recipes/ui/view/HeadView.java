package com.xiong.recipes.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xiong.recipes.R;

public class HeadView extends RelativeLayout {
    private ImageView headerLeftImg;
    private TextView headLeftText;
    private TextView headRightText;
    private TextView headerCenterText;
    private ImageView headerRightImg;
    private final int DEFAULT_COLOR =0xffffff;

    public HeadView(Context context) {
        this(context,null);
    }

    public HeadView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public HeadView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.HeadView);
        int leftColor = ta.getColor(R.styleable.HeadView_left_text_color,DEFAULT_COLOR);
        String leftText = ta.getString(R.styleable.HeadView_left_text);
        boolean leftVisible = ta.getBoolean(R.styleable.HeadView_left_text_visible,false);
        int centerColor = ta.getColor(R.styleable.HeadView_center_text_color,DEFAULT_COLOR);
        String centerText = ta.getString(R.styleable.HeadView_center_text);
        boolean centerVisible = ta.getBoolean(R.styleable.HeadView_center_text_visible,true);
        boolean rightVisible = ta.getBoolean(R.styleable.HeadView_right_img_visible,false);
        boolean headRightTextVisible = ta.getBoolean(R.styleable.HeadView_right_text_visible,false);
        if(!TextUtils.isEmpty(leftText)){
            headLeftText.setText(leftText);
        }
        if(!TextUtils.isEmpty(centerText)){
            headerCenterText.setText(centerText);
        }
        headLeftText.setTextColor(leftColor);
        headerCenterText.setTextColor(centerColor);
        headLeftText.setVisibility(leftVisible? View.VISIBLE: View.GONE);
        headerCenterText.setVisibility(centerVisible? View.VISIBLE: View.GONE);
        headerRightImg.setVisibility(rightVisible? View.VISIBLE: View.GONE);
        headRightText.setVisibility(headRightTextVisible? View.VISIBLE: View.GONE);
        ta.recycle();
    }

    private void initView(Context context){
        View view = LayoutInflater.from(context).inflate(R.layout.head_layout,this,true);
        headerLeftImg = view.findViewById(R.id.header_left_img);
        headLeftText = view.findViewById(R.id.head_left_text);
        headRightText = view.findViewById(R.id.head_right_text);
        headerCenterText = view.findViewById(R.id.header_center_text);
        headerRightImg = view.findViewById(R.id.header_right_img);
    }

    public void setHeadCenterText(String text){
       if(text!=null){
           headerCenterText.setText(text);
       }
    }

    public void setLeftOnClickListener(OnClickListener onClickListener){
        headerLeftImg.setOnClickListener(onClickListener);
    }
    public void setRightOnClickListener(OnClickListener onClickListener){
        headerRightImg.setOnClickListener(onClickListener);
    }
    public void setRightTextOnClickListener(OnClickListener onClickListener){
        headRightText.setOnClickListener(onClickListener);
    }
}
