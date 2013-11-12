package com.fantasycard.ui;

import com.fantasycard.app.MainActivity;
import com.fantasycard.app.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.view.View.OnDragListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.LinearLayout;

public class CardDragListener implements OnDragListener {

	public Context mContext;
	private int mCardSlotNum;
	boolean mIsEntered = false;
	private View dropView;
	private int slotNum = -1;
	public CardDragListener(Context context, int cardslot) {
		mContext = context;
		mCardSlotNum = cardslot;
	}
	
	public CardDragListener(Context context, LinearLayout dropArea) {
		mContext = context;
		dropView = dropArea;
	}
	
	private void blinkSrot(View v){
		Animation anim = new AlphaAnimation(0.0f, 1.0f);
		anim.setDuration(500);
		anim.setStartOffset(20);
		anim.setRepeatMode(Animation.REVERSE);
		anim.setRepeatCount(Animation.INFINITE);
		v.startAnimation(anim);
		
	}
	
	private int nowSlot(float x){
		float unitWidth = dropView.getWidth()/3;
		int slotNum=(int)(x / unitWidth);
		return slotNum;
	}
	
	@Override
	public boolean onDrag(View v, DragEvent event) {
		int action = event.getAction();
		switch (action) {
		case DragEvent.ACTION_DRAG_STARTED:
			Log.d("", "start");
			break;
		case DragEvent.ACTION_DRAG_ENTERED:
			Log.d("", "enter:"+event.getX()+" - "+event.getY());
			mIsEntered = true;
			slotNum = nowSlot(event.getX());
			break;
		case DragEvent.ACTION_DRAG_LOCATION:
			int newSlotNum = nowSlot(event.getX());
			if (slotNum != newSlotNum) {
				if (slotNum != -1) {
					((MainActivity)mContext).mSlotCardFrame[slotNum].clearAnimation();
				}
				slotNum = newSlotNum;
				blinkSrot(((MainActivity)mContext).mSlotCardFrame[slotNum]);
			}
			break;
		case DragEvent.ACTION_DRAG_EXITED:
			((MainActivity)mContext).mSlotCardFrame[slotNum].clearAnimation();
			slotNum = -1;
			mIsEntered = false;
			break;
		case DragEvent.ACTION_DROP:
			break;
		case DragEvent.ACTION_DRAG_ENDED:
			if(mIsEntered) {
				if (slotNum != -1) {
					((MainActivity)mContext).mSlotCardFrame[slotNum].clearAnimation();	
				}
				((MainActivity)mContext).dropCardToMyCardSlot(slotNum);
			}else {
				((MainActivity)mContext).tempReturn();
			}
			slotNum = -1;
			break;
		default:
			break;
		}
		return true;
	}
}
