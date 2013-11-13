package com.fantasycard.app;

import android.content.Context;
import android.widget.ImageView;

public class CardView extends ImageView{

	public int mCardSlotId = 0;
	
	public CardView(Context context) {
		super(context);
	}
	
	public void setBitmap(int resid){
		setBackgroundDrawable(getResources().getDrawable(resid));
	}
}
