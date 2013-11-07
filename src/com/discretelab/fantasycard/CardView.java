package com.discretelab.fantasycard;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Context;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.View.OnDragListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

@SuppressLint("NewApi")
public class CardView extends ImageView implements OnTouchListener, OnDragListener{

	public CardView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public void setBitmap(int resid){
		setBackgroundDrawable(getResources().getDrawable(resid));
		this.setOnTouchListener(this);
		this.setOnDragListener(this);
	}
	
	public boolean onTouch(View view, MotionEvent motionEvent) {
	    if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
		    ClipData data = ClipData.newPlainText("", "");
		    DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
		    view.startDrag(data, shadowBuilder, view, 0);
		    view.setVisibility(View.INVISIBLE);
		    return true;
		} else {
		    return false;
		}
    }

	@Override
	public boolean onDrag(View v, DragEvent event) {
	    switch (event.getAction()) {
	    case DragEvent.ACTION_DRAG_STARTED:
	    // Do nothing
	      break;
	    case DragEvent.ACTION_DRAG_ENTERED:
	      break;
	    case DragEvent.ACTION_DRAG_EXITED:        
	      break;
	    case DragEvent.ACTION_DROP:
	      // Dropped, reassign View to ViewGroup
	      View view = (View) event.getLocalState();
	      ViewGroup owner = (ViewGroup) view.getParent();
	      owner.removeView(view);
	      RelativeLayout container = (RelativeLayout) v;
	      container.addView(view);
	      view.setVisibility(View.VISIBLE);
	      break;
	    case DragEvent.ACTION_DRAG_ENDED:
	      default:
	      break;
	    }
	    return true;
	}
}
