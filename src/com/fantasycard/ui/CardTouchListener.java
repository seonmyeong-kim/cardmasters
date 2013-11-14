package com.fantasycard.ui;

import com.fantasycard.app.CardView;
import com.fantasycard.app.MainActivity;

import android.content.ClipData;
import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnTouchListener;

public final class CardTouchListener implements OnTouchListener {

	public Context mContext;
	
	public CardTouchListener(Context context) {
		mContext = context;
	}
	
	public boolean onTouch(View view, MotionEvent motionEvent) {
		Log.d("Premo","onTouch() motionEvent.getAction() = " + motionEvent.getAction());
		if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
			ClipData data = ClipData.newPlainText("", "");
			DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
			view.startDrag(data, shadowBuilder, view, 0);
			view.setVisibility(View.INVISIBLE);
			
			Log.d("Premo","onTouch() selectCardSlotId mCardSlotId = " + ((CardView)view).mCardSlotId);
			((MainActivity)mContext).selectCardSlotId(((CardView)view).mCardSlotId);

			return true;
		} else {
			return false;
		}
	}
}
