package com.fantasycard.app;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
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
	
	public void addImgViewToMyCardSlot(int cardslot, CardView cardview) {
		addView(cardview);

		RelativeLayout.LayoutParams cardimgparams = (RelativeLayout.LayoutParams) cardview.getLayoutParams();
		cardview.setLayoutParams(UILayoutParams.changeRect(cardimgparams, new Rect(14 + (cardslot*90), 230, 70, 90)));
	}
	
	public void addImgViewToMyHand(int cardslot, CardView cardview) {
		addView(cardview);

		RelativeLayout.LayoutParams cardimgparams = (RelativeLayout.LayoutParams) cardview.getLayoutParams();
		cardview.setLayoutParams(UILayoutParams.changeRect(cardimgparams, new Rect(12 + (cardslot*90), 430, 70, 90)));
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
