package com.fantasycard.ui;

import android.content.Context;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.view.View.OnDragListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.RelativeLayout;

import com.fantasycard.app.AppValues;
import com.fantasycard.app.MainActivity;
import com.fantasycard.cardinfo.CardInfo;

public class GraveDragListener implements OnDragListener {

	public Context mContext;
	public MainActivity mActivity;
	
	boolean mIsEntered = false;
	private View dropView;
	
	public GraveDragListener(Context context, RelativeLayout dropArea) {
		mContext = context;
		mActivity = (MainActivity)context;
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
			break;
		case DragEvent.ACTION_DRAG_LOCATION:
			if (mIsEntered) {
				mActivity.mMyGraveSlotArea.clearAnimation();
				blinkSrot(mActivity.mMyGraveSlotArea);
			}
			break;
		case DragEvent.ACTION_DRAG_EXITED:
			mActivity.mMyGraveSlotArea.clearAnimation();
			mIsEntered = false;
			break;
		case DragEvent.ACTION_DROP:
			break;
		case DragEvent.ACTION_DRAG_ENDED:
			if(mIsEntered) {
				mActivity.mMyGraveSlotArea.clearAnimation();	
				int selectbattleslotnum = mActivity.getCardSlotNumFromSlotId(mActivity.mSelectSlotId);
				
				if(mActivity.mSelectSlotId >= AppValues.BATTLE_SLOT_1 &&
					mActivity.mSelectSlotId <= AppValues.BATTLE_SLOT_3) {
					CardInfo selectedCardInfo = mActivity.getCardInfoFromSelectBattleSlot();

					mActivity.moveBattleToGraveSlot(selectbattleslotnum);
				}
				else if(mActivity.mSelectSlotId >= AppValues.HAND_SLOT_1 &&
					    mActivity.mSelectSlotId <= AppValues.HAND_SLOT_3) {
					CardInfo selectedCardInfo = mActivity.getCardInfoFromSelectHandSlot();

					mActivity.moveHandToGraveSlot(selectbattleslotnum);
				}
			}else {
				(mActivity.getCardViewFromSlotId(mActivity.mSelectSlotId)).setVisibility(View.VISIBLE);
			}
			break;
		default:
			break;
		}
		return true;
	}
}