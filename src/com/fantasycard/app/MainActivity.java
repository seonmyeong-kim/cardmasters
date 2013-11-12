package com.fantasycard.app;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fantasycard.cardinfo.CardInfo;
import com.fantasycard.ui.CardDragListener;
import com.fantasycard.ui.CardTouchListener;

public class MainActivity extends Activity {

	
	private MyCardSlotViewGroup mySlotView;
	private DeckManager mDeckManager;
	
	public CardInfo[] mMyHands = new CardInfo[3];
	public CardView[] mMyHandsCardView = new CardView[3];
	public int mSelectCard;
	public View mSelectView;
	
	public CardInfo[] mMyCardSlots = new CardInfo[3];
	public CardView[] mMyCardSlotsView = new CardView[3];
	
	public RelativeLayout[] mHandCardFrame = new RelativeLayout[3];
	public RelativeLayout[] mSlotCardFrame = new RelativeLayout[3];
	
	public RelativeLayout mTempSlot;
	public CardInfo mTempCardInfo;
	
	public LinearLayout dropArea;
	
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
		
		dropArea = (LinearLayout) findViewById(R.id.drop_area);
		
		mTempSlot = (RelativeLayout) findViewById(R.id.temp_slot);
		
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
		
		mMyHandsCardView[0].setOnTouchListener(new CardTouchListener(this, 0));
		mMyHandsCardView[1].setOnTouchListener(new CardTouchListener(this, 1));
		mMyHandsCardView[2].setOnTouchListener(new CardTouchListener(this, 2));
		
		dropArea.setOnDragListener(new CardDragListener(this, dropArea));
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
	public void selectView(View v){
		mSelectView = v;
	}
	
	public int checkSlotCard(){
		for (int i = 0; i < mMyCardSlotsView.length; i++) {
			Log.d("", "discrete:"+mMyCardSlotsView[i]+" - "+mSelectView);
			if (mMyCardSlotsView[i] == mSelectView) {
				return i;
			}
		}
		return -1;
	}

	public void tempReturn(){
		if (checkSlotCard() == -1) {
			tempToHand();
		}else {
			tempToSlot();
		}
	}
	
	public void handToTemp(){
		Log.d("", "handToTemp");
		for (int i = 0; i < mHandCardFrame.length; i++) {
			mHandCardFrame[i].removeView(mMyHandsCardView[mSelectCard]);
		}
		mTempSlot.addView(mMyHandsCardView[mSelectCard]);
		mTempCardInfo = mMyHands[mSelectCard];
		mMyHands[mSelectCard] = null;
	}
	
	public void tempToHand(){
		Log.d("", "tempToHand");
		mTempSlot.removeView(mMyHandsCardView[mSelectCard]);
		mHandCardFrame[mSelectCard].addView(mMyHandsCardView[mSelectCard]);
		mMyHands[mSelectCard] = mTempCardInfo;
		mTempCardInfo = null;
	}
	
	public void slotToTemp(){
		Log.d("", "slotToTemp");
		for (int i = 0; i < mSlotCardFrame.length; i++) {
			mSlotCardFrame[i].removeView(mMyCardSlotsView[mSelectCard]);
		}
		mTempSlot.addView(mMyCardSlotsView[mSelectCard]);
		mTempCardInfo = mMyCardSlots[mSelectCard];
		mMyCardSlots[mSelectCard] = null;
	}
	
	public void tempToSlot(){
		Log.d("", "tempToSlot");
		mTempSlot.removeView(mMyCardSlotsView[mSelectCard]);
		mSlotCardFrame[mSelectCard].addView(mMyCardSlotsView[mSelectCard]);
		mMyCardSlots[mSelectCard] = mTempCardInfo;
		mTempCardInfo = null;
	}
	
	public void tempToSlot(int targetSlot){
		Log.d("", "tempToSlot:"+targetSlot+" "+mSelectCard);
		mTempSlot.removeView(mMyCardSlotsView[mSelectCard]);
		mSlotCardFrame[targetSlot].addView(mMyCardSlotsView[mSelectCard]);
		mMyCardSlots[targetSlot] = mTempCardInfo;
		mTempCardInfo = null;
	}
	
	public void moveHandToSlot(int targetSlotNum){
		mMyCardSlots[targetSlotNum] = mTempCardInfo;
		//mMyHands[fromCardNum] = null;
		mMyCardSlotsView[targetSlotNum] = mMyHandsCardView[mSelectCard];
		mMyHandsCardView[mSelectCard] = null;
		mTempSlot.removeView(mMyCardSlotsView[targetSlotNum]);
		
		mySlotView.addImgViewToMyCardSlot(targetSlotNum, mMyCardSlotsView[targetSlotNum]);
	    final View droppedView = mMyCardSlotsView[targetSlotNum];
	    droppedView.post(new Runnable(){
			@Override
			public void run() {
		        droppedView.setVisibility(View.VISIBLE);
			}
	    });
	}
	
	public void shiftSlot(int fromSlotNum){
		if (mMyCardSlotsView[fromSlotNum+1] != null) {
			shiftSlot(fromSlotNum+1);
		}
		mMyCardSlots[fromSlotNum+1] = mMyCardSlots[fromSlotNum];
		mMyCardSlotsView[fromSlotNum+1] = mMyCardSlotsView[fromSlotNum];
		mSlotCardFrame[fromSlotNum].removeView(mMyCardSlotsView[fromSlotNum]);
		mySlotView.addImgViewToMyCardSlot(fromSlotNum+1, mMyCardSlotsView[fromSlotNum+1]);
	    final View droppedView = mMyCardSlotsView[fromSlotNum+1];
	    droppedView.post(new Runnable(){
			@Override
			public void run() {
		        droppedView.setVisibility(View.VISIBLE);
			}
	    });
	}
	
	public int getEmptySlot(){
		int emptySlot = -1;
		for(int i=0;i<3;i++) {
			if(mMyCardSlots[i] == null) {
				emptySlot = i;
				break;
			}
		}
		return emptySlot;
	}
	
	public void moveSlotToSlot(int fromSlotNum){
		int emptySlot = getEmptySlot();
		mMyCardSlots[emptySlot] = mMyCardSlots[fromSlotNum];
		mMyCardSlotsView[emptySlot] = mMyCardSlotsView[fromSlotNum];
		mSlotCardFrame[fromSlotNum].removeView(mMyCardSlotsView[fromSlotNum]);
		mySlotView.addImgViewToMyCardSlot(emptySlot, mMyCardSlotsView[emptySlot]);
	    final View droppedView = mMyCardSlotsView[emptySlot];
	    droppedView.post(new Runnable(){
			@Override
			public void run() {
		        droppedView.setVisibility(View.VISIBLE);
			}
	    });
	}
	
	public void dropCardToMyCardSlot(int slotNum) {
		Log.d("Premo","dropCardToMyCardSlot() ENTRY");
		int emptySlot = getEmptySlot();
		Log.d("", "dropCardToMyCardSlot()"+emptySlot);
		if (checkSlotCard() == -1) {
			if(emptySlot != -1) {
				if (mMyCardSlots[slotNum] == null) {
					moveHandToSlot(emptySlot);
				}else {
					shiftSlot(slotNum);
					moveHandToSlot(slotNum);
				}
			}
		}else {
			moveSlotToSlot(slotNum);
			tempToSlot(slotNum);
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
