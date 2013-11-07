package com.fantasycard.ui;

import com.fantasycard.app.MainActivity;

import android.content.Context;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.view.View.OnDragListener;

public class CardDragListener implements OnDragListener {

	public Context mContext;
	private int mCardSlotNum;
	boolean mIsEntered = false;
	
	public CardDragListener(Context context, int cardslot) {
		mContext = context;
		mCardSlotNum = cardslot;
	}
	
	@Override
	public boolean onDrag(View v, DragEvent event) {
		int action = event.getAction();
		Log.d("Premo", "onDrag() action = " + action + " mCardSlotNum = " + mCardSlotNum);
		switch (action) {
		case DragEvent.ACTION_DRAG_STARTED:
			// do nothing
			break;
		case DragEvent.ACTION_DRAG_ENTERED:
			mIsEntered = true;
			break;
		case DragEvent.ACTION_DRAG_EXITED:
			mIsEntered = false;
			break;
		case DragEvent.ACTION_DROP:
			break;
		case DragEvent.ACTION_DRAG_ENDED:
			if(mIsEntered) {
				Log.d("Premo", "onDrag() ACTION_DRAG_ENDED mCardSlotNum = " + mCardSlotNum);
				((MainActivity)mContext).dropCardToMyCardSlot();
			}
			break;
		default:
			break;
		}
		return true;
	}
}
