package com.discretelab.fantasycard;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MyCardSlotViewGroup  extends RelativeLayout{

	private Context mContext;
	
	public MyCardSlotViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        
        mContext = context;
    }

	public void addImgView(int resId, Rect rect){
		ImageView iv = new ImageView(mContext);
		iv.setId(this.getChildCount() + 1);
		iv.setImageResource(resId);
		
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams
                (ViewGroup.LayoutParams.WRAP_CONTENT,
                  ViewGroup.LayoutParams.WRAP_CONTENT);
		
		params = UILayoutParams.changeRect(params, rect);
		
		this.addView(iv, params);
	}
	
	public void addTextView(TextView tv, Rect rect){
		tv.setId(this.getChildCount() + 1);
		tv.setTextColor(Color.rgb(246, 196, 5));
		
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams
                (ViewGroup.LayoutParams.WRAP_CONTENT,
                  ViewGroup.LayoutParams.WRAP_CONTENT);
		
		params = UILayoutParams.wrapRect(params, rect);
		
		this.addView(tv, params);
	}
}
