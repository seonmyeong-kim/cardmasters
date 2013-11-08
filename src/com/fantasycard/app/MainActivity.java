package com.fantasycard.app;

import com.fantasycard.app.R;
import com.fantasycard.cardinfo.CardInfo;
import com.fantasycard.ui.CardDragListener;
import com.fantasycard.ui.CardTouchListener;

import android.app.Activity;
import android.content.ClipData;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.DragShadowBuilder;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

	
	private MyCardSlotViewGroup mySlotView;
	private DeckManager mDeckManager;
	
	public static CardInfo[] mMyHands = new CardInfo[3];
	public static CardView[] mMyHandsCardView = new CardView[3];
	public int mSelectCard;
	
	public static CardInfo[] mMyCardSlots = new CardInfo[3];
	public static CardView[] mMyCardSlotsView = new CardView[3];
	
	public RelativeLayout[] mHandCardFrame = new RelativeLayout[3];
	public RelativeLayout[] mSlotCardFrame = new RelativeLayout[3];
	
	private TextView mtxtTurnCnt;
	private TextView mtxtManaCnt;
	private TextView mtxtLifeCnt;
	
	private int mTurn;
	private int mPlayerMana;
	private int mPlayerLife;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();
		
	}
	
	@Override
	public void onAttachedToWindow() {
		super.onAttachedToWindow();
		appOnAttachedToWindow();
	}
	
	public void init() {
		setDensity();
		appOnAttachedToWindow();
		
		mySlotView = (MyCardSlotViewGroup)findViewById(R.id.myslotview);
		
		mHandCardFrame[0] = (RelativeLayout) findViewById(R.id.mycardslot01);
		mHandCardFrame[1] = (RelativeLayout) findViewById(R.id.mycardslot02);
		mHandCardFrame[2] = (RelativeLayout) findViewById(R.id.mycardslot03);
		
		mSlotCardFrame[0] = (RelativeLayout) findViewById(R.id.cardslot01);
		mSlotCardFrame[1] = (RelativeLayout) findViewById(R.id.cardslot02);
		mSlotCardFrame[2] = (RelativeLayout) findViewById(R.id.cardslot03);
		
		mTurn = 0;
		mPlayerMana = 0;
		mPlayerLife = 10;
		
		///TurnCount初期化
		mtxtTurnCnt = new TextView(this);
		mtxtTurnCnt.setTextColor(Color.rgb(246, 196, 5));
		mtxtTurnCnt.setText("Turn : " + mTurn);
        mySlotView.addView(mtxtTurnCnt);
        
		RelativeLayout.LayoutParams turnparams = (RelativeLayout.LayoutParams) mtxtTurnCnt.getLayoutParams();
		mtxtTurnCnt.setLayoutParams(UILayoutParams.changeMargin(turnparams, new Rect(10, 5, 10, 35)));

		///ManaCount初期化
		mtxtManaCnt = new TextView(this);
		mtxtManaCnt.setTextColor(Color.rgb(246, 196, 5));
		mtxtManaCnt.setText("Mana : " + mPlayerMana);
		mySlotView.addView(mtxtManaCnt);
        
		RelativeLayout.LayoutParams manaparams = (RelativeLayout.LayoutParams) mtxtManaCnt.getLayoutParams();
		mtxtManaCnt.setLayoutParams(UILayoutParams.changeMargin(manaparams, new Rect(100, 5, 10, 35)));
		
		///LifeCount初期化
		mtxtLifeCnt = new TextView(this);
		mtxtLifeCnt.setTextColor(Color.rgb(246, 196, 5));
		mtxtLifeCnt.setText("Life : " + mPlayerLife);
        mySlotView.addView(mtxtLifeCnt);
        
		RelativeLayout.LayoutParams lifeparams = (RelativeLayout.LayoutParams) mtxtLifeCnt.getLayoutParams();
		mtxtLifeCnt.setLayoutParams(UILayoutParams.changeMargin(lifeparams, new Rect(210, 5, 10, 35)));
        
		mDeckManager = new DeckManager();
		mDeckManager.init();
		
		firstDrawCard();
		
		mMyHandsCardView[0].setOnTouchListener(new CardTouchListener(this, 1));
		mMyHandsCardView[1].setOnTouchListener(new CardTouchListener(this, 2));
		mMyHandsCardView[2].setOnTouchListener(new CardTouchListener(this, 3));
	    findViewById(R.id.cardslot01).setOnDragListener(new CardDragListener(this, 1));
	    findViewById(R.id.cardslot02).setOnDragListener(new CardDragListener(this, 2));
	    findViewById(R.id.cardslot03).setOnDragListener(new CardDragListener(this, 3));
	}
	
	public CardInfo getCardInfoFromSelectCardSlot() {
		return mMyHands[mSelectCard];
	}
	
	public CardInfo getCardInfoFromCardView(CardView view) {
		for(int i=0;i<3;i++) {
			if(mMyHandsCardView[i] == view) {
				return mMyHands[i];
			}
		}
		return null;
	}
	
	public void selectCardSlot(int cardslot){
		mSelectCard = cardslot;
	}
	
	private void firstDrawCard(){
		while(true){
			for(int i=0;i<3;i++){
				if(mMyHands[i] == null){
					mMyHands[i] = mDeckManager.getCardFromDeck();
					mMyHandsCardView[i] = drawCardImgMyHand(i, mMyHands[i]);
				}
			}
			
			boolean isManaCardExist = false;
			
			for(int i=0;i<3;i++){
				if(mMyHands[i].card_category == AppValues.CARD_MANA){
					isManaCardExist = true;
					break;
				}
			}
			
			if(isManaCardExist) {
				break;
			} else {
				for(int i=0;i<3;i++) {
					mDeckManager.putCardToDeck(mMyHands[i]);
				}
				mDeckManager.suffleDeck();
			}
		}
	}
	
	private CardView drawCardImgMyHand(int idx, CardInfo cardinfo){
		CardView cardImgView = getCardImageView(cardinfo);
		mySlotView.addImgViewToMyHand(idx, cardImgView);

		return cardImgView;
	}
	
	public void dropCardToMyCardSlot() {
		Log.d("Premo","dropCardToMyCardSlot() ENTRY");
		for(int i=0;i<3;i++) {
			if(mMyCardSlots[i] == null) {
				mMyCardSlots[i] = mMyHands[mSelectCard-1];
				mMyCardSlotsView[i] = mMyHandsCardView[mSelectCard-1];
				//mySlotView.removeView(mMyCardSlotsView[i]);
				for (int j = 0; j < 3; j++) {
					mHandCardFrame[j].removeView(mMyCardSlotsView[i]);
				}
				
				Log.d("Premo","dropCardToMyCardSlot() i = " + i);
				mySlotView.addImgViewToMyCardSlot(i, mMyCardSlotsView[i]);
			    final View droppedView = mMyCardSlotsView[i];
			    droppedView.post(new Runnable(){
					@Override
					public void run() {
				        droppedView.setVisibility(View.VISIBLE);
					}
			    });
				break;
			}
		}
	}
	
	private CardView getCardImageView(CardInfo cardinfo) {
		CardView cardimg = new CardView(this);
		switch(cardinfo.card_id){
			case 1:
				cardimg.setBitmap(R.drawable.element1);
				break;
			case 2:
				cardimg.setBitmap(R.drawable.element2);
				break;
			case 3:
				cardimg.setBitmap(R.drawable.element3);
				break;
			case 4:
				cardimg.setBitmap(R.drawable.card_1_1);
				break;
			case 5:
				cardimg.setBitmap(R.drawable.card_1_2);
				break;
			case 6:
				cardimg.setBitmap(R.drawable.card_1_3);
				break;
			case 7:
				cardimg.setBitmap(R.drawable.card_2_1);
				break;
			case 8:
				cardimg.setBitmap(R.drawable.card_2_2);
				break;
			case 9:
				cardimg.setBitmap(R.drawable.card_2_3);
				break;
			case 10:
				cardimg.setBitmap(R.drawable.card_3_1);
				break;
		}
		
		return cardimg;
	}

	/**
	 * viewport 設定処理
	 */
    private void setDensity() {
    	DisplayMetrics dm = getResources().getDisplayMetrics();
    	float wdp = dm.widthPixels / dm.density;
    	if(wdp > 720) wdp = 720;
    	UILayoutParams.DENSITY_DPI = Math.floor(320 * 160 / wdp);
    	if(UILayoutParams.DENSITY_DPI > 160) UILayoutParams.DENSITY_DPI = 160;
	}
    
	/**
	 * 画面表示時に端末のdp設定処理
	 */
	private void appOnAttachedToWindow() {
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int px = getWindowManager().getDefaultDisplay().getWidth();
        float dp = px / getResources().getDisplayMetrics().density;
		if(dp > 720 ){
			UILayoutParams.SCALED_DENSITY = (float) ((720f / 320f) * metrics.scaledDensity);
		} else {
			UILayoutParams.SCALED_DENSITY = (float) (dp / 320) * metrics.scaledDensity;
		}
	}
}
