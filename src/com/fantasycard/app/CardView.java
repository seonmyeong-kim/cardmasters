package com.fantasycard.app;

import android.content.Context;
import android.widget.ImageView;

public class CardView extends ImageView{

	public CardView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public void setBitmap(int resid){
		setBackgroundDrawable(getResources().getDrawable(resid));
	}
}
