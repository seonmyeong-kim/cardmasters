package com.fantasycard.ui;

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
	private int mCardSlotNum;
	
	public CardTouchListener(Context context, int slotnum) {
		mContext = context;
		mCardSlotNum = slotnum;
	}
	
	public boolean onTouch(View view, MotionEvent motionEvent) {
		Log.d("Premo","onTouch() motionEvent.getAction() = " + motionEvent.getAction());
		if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
			ClipData data = ClipData.newPlainText("", "");
			DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
			view.startDrag(data, shadowBuilder, view, 0);
			//view.setVisibility(View.INVISIBLE);
			Log.d("Premo","onTouch() selectCardSlot mCardSlotNum = " + mCardSlotNum);
			((MainActivity)mContext).selectView(view);
			int nowSlot = ((MainActivity)mContext).checkSlotCard();
			if (nowSlot == -1) {
				((MainActivity)mContext).selectCardSlot(mCardSlotNum);
				((MainActivity)mContext).handToTemp();
			}else {
				((MainActivity)mContext).selectCardSlot(nowSlot);
				((MainActivity)mContext).slotToTemp();
				
			}
			return true;
		} else {
			return false;
		}
	}
}
