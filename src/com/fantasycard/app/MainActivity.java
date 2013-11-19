package com.fantasycard.app;

import java.util.Hashtable;

import com.fantasycard.app.R;
import com.fantasycard.cardinfo.CardInfo;
import com.fantasycard.ui.CardDragListener;
import com.fantasycard.ui.CardTouchListener;

import android.app.Activity;
import android.content.ClipData;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.DragShadowBuilder;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

	
	private MyCardSlotViewGroup mySlotView;
	private DeckManager mPlayerDeckManager;
	private DeckManager mEnemyDeckManager;
	
	public static CardInfo[] mMyHandSlot = new CardInfo[3];
	public static CardView[] mMyHandSlotCardView = new CardView[3];
	public int mSelectSlotId;
	
	public static CardInfo[] mMyBattleSlot = new CardInfo[3];
	public static CardView[] mMyBattleSlotView = new CardView[3];
	
	public static CardInfo[] mMyManaSlot = new CardInfo[6];
	public static ImageView[] mMyManaSlotView = new ImageView[6];
	
	public RelativeLayout[] mHandSlotFrame = new RelativeLayout[3];
	public RelativeLayout[] mBattleSlotFrame = new RelativeLayout[3];
	public RelativeLayout mManaFrame;
	public RelativeLayout effectArea;
	
	public ImageView cardEffectAnimiationView; 
	
	public static Hashtable<CardView, CardInfo> mCardViewList = new Hashtable<CardView, CardInfo>();
	
	public static CardInfo[] mEnemyHandSlot = new CardInfo[3];
	
	public static CardInfo[] mEnemyBattleSlot = new CardInfo[3];
	public static CardInfo[] mEnemyBattleSlotView = new CardInfo[3];
	
	public RelativeLayout[] mEnermyBattleSlotFrame = new RelativeLayout[3];
	
	public LinearLayout dropArea;
	
	private TextView mtxtTurnCnt;
	private TextView mtxtPlayerManaCnt;
	private TextView mtxtPlayerDeckCnt;
	
	private TextView mtxtEnemyManaCnt;
	private TextView mtxtEnemyDeckCnt;
	
	private int mTurn;
	private int mPlayerMana;
	
	private int mEnermyMana;
	
	private int mDeviceHeightDp;
	
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
		
		dropArea = (LinearLayout) findViewById(R.id.myBattleSlotArea); 
				
		mySlotView = (MyCardSlotViewGroup)findViewById(R.id.myslotview);
		
		mHandSlotFrame[0] = (RelativeLayout) findViewById(R.id.myCardSlot01);
		mHandSlotFrame[1] = (RelativeLayout) findViewById(R.id.myCardSlot02);
		mHandSlotFrame[2] = (RelativeLayout) findViewById(R.id.myCardSlot03);
		
		mBattleSlotFrame[0] = (RelativeLayout) findViewById(R.id.myBattleSlot01);
		mBattleSlotFrame[1] = (RelativeLayout) findViewById(R.id.myBattleSlot02);
		mBattleSlotFrame[2] = (RelativeLayout) findViewById(R.id.myBattleSlot03);
		
		mManaFrame = (RelativeLayout) findViewById(R.id.cardSlotMana);
		effectArea = (RelativeLayout) findViewById(R.id.effectArea);
		
		cardEffectAnimiationView = (ImageView) findViewById(R.id.cardEffectAnimation);
		
		mMyManaSlotView[0] = (ImageView) findViewById(R.id.myManaSlot01);
		mMyManaSlotView[1] = (ImageView) findViewById(R.id.myManaSlot02);
		mMyManaSlotView[2] = (ImageView) findViewById(R.id.myManaSlot03);
		mMyManaSlotView[3] = (ImageView) findViewById(R.id.myManaSlot04);
		mMyManaSlotView[4] = (ImageView) findViewById(R.id.myManaSlot05);
		mMyManaSlotView[5] = (ImageView) findViewById(R.id.myManaSlot06);
		
		mPlayerDeckManager = new DeckManager();
		mPlayerDeckManager.init();
		
		mEnemyDeckManager = new DeckManager();
		mEnemyDeckManager.init();
		
		firstDrawCard();
		firstDrawEnermyCard();
		
		///TurnCount初期化
		mtxtTurnCnt = new TextView(this);
		mtxtTurnCnt.setTextColor(Color.rgb(246, 196, 5));
        mySlotView.addView(mtxtTurnCnt);
        
		RelativeLayout.LayoutParams turnparams = (RelativeLayout.LayoutParams) mtxtTurnCnt.getLayoutParams();
		mtxtTurnCnt.setLayoutParams(UILayoutParams.wrapRect(turnparams, new Rect(260, 5, 10, 35)));

		///ManaCount初期化
		mtxtPlayerManaCnt = new TextView(this);
		mtxtPlayerManaCnt.setTextColor(Color.rgb(246, 196, 5));
		mySlotView.addView(mtxtPlayerManaCnt);
        
		RelativeLayout.LayoutParams manaparams = (RelativeLayout.LayoutParams) mtxtPlayerManaCnt.getLayoutParams();
		mtxtPlayerManaCnt.setLayoutParams(UILayoutParams.wrapRect(manaparams, new Rect(10, mDeviceHeightDp-25, 10, 35)));
		
		///LifeCount初期化
		mtxtPlayerDeckCnt = new TextView(this);
		mtxtPlayerDeckCnt.setTextColor(Color.rgb(246, 196, 5));
        mySlotView.addView(mtxtPlayerDeckCnt);
        
		RelativeLayout.LayoutParams lifeparams = (RelativeLayout.LayoutParams) mtxtPlayerDeckCnt.getLayoutParams();
		mtxtPlayerDeckCnt.setLayoutParams(UILayoutParams.wrapRect(lifeparams, new Rect(130, mDeviceHeightDp-25, 10, 35)));
		
		///EnermyManaCount初期化
		mtxtEnemyManaCnt = new TextView(this);
		mtxtEnemyManaCnt.setTextColor(Color.rgb(246, 196, 5));
		mySlotView.addView(mtxtEnemyManaCnt);
        
		RelativeLayout.LayoutParams enermy_manaparams = (RelativeLayout.LayoutParams) mtxtEnemyManaCnt.getLayoutParams();
		mtxtEnemyManaCnt.setLayoutParams(UILayoutParams.wrapRect(enermy_manaparams, new Rect(10, 5, 10, 35)));
		
		///EnermyLifeCount初期化
		mtxtEnemyDeckCnt = new TextView(this);
		mtxtEnemyDeckCnt.setTextColor(Color.rgb(246, 196, 5));
        mySlotView.addView(mtxtEnemyDeckCnt);
        
		RelativeLayout.LayoutParams enermy_lifeparams = (RelativeLayout.LayoutParams) mtxtEnemyDeckCnt.getLayoutParams();
		mtxtEnemyDeckCnt.setLayoutParams(UILayoutParams.wrapRect(enermy_lifeparams, new Rect(130, 5, 10, 35)));
		
		mCardViewList.put(mMyHandSlotCardView[0], mMyHandSlot[0]);
		mCardViewList.put(mMyHandSlotCardView[1], mMyHandSlot[1]);
		mCardViewList.put(mMyHandSlotCardView[2], mMyHandSlot[2]);
		
		mMyHandSlotCardView[0].setOnTouchListener(new CardTouchListener(this));
		mMyHandSlotCardView[1].setOnTouchListener(new CardTouchListener(this));
		mMyHandSlotCardView[2].setOnTouchListener(new CardTouchListener(this));
		
		dropArea.setOnDragListener(new CardDragListener(this, dropArea));
		
		mTurn = 0;
		mPlayerMana = 0;
		mEnermyMana = 0;
		
		updateState();
	}
	
	public void updateState() {
		mtxtTurnCnt.setText("Turn : " + mTurn);
		mtxtPlayerManaCnt.setText("Mana : " + mPlayerMana);
		mtxtPlayerDeckCnt.setText("Deck : " + mPlayerDeckManager.getDeckSize());
		
		mtxtEnemyManaCnt.setText("EnermyMana : " + mEnermyMana);
		mtxtEnemyDeckCnt.setText("EnemyDeck : " + mEnemyDeckManager.getDeckSize());
	}
	
	public CardInfo getCardInfoFromSelectHandSlot() {
		return mMyHandSlot[mSelectSlotId - AppValues.HAND_SLOT_1];
	}
	
	public CardInfo getCardInfoFromCardView(CardView view) {
		for(int i=0;i<3;i++) {
			if(mMyHandSlotCardView[i] == view) {
				return mMyHandSlot[i];
			}
		}
		return null;
	}
	
	public void selectCardSlotId(int cardslotid){
		mSelectSlotId = cardslotid;
	}
	
	private void firstDrawEnermyCard() {
		for(int i=0;i<3;i++){
			if(mEnemyHandSlot[i] == null){
				mEnemyHandSlot[i] = mEnemyDeckManager.getCardFromDeck();
			}
		}
	}
	
	private void firstDrawCard(){
		while(true){
			for(int i=0;i<3;i++){
				if(mMyHandSlot[i] == null){
					mMyHandSlot[i] = mPlayerDeckManager.getCardFromDeck();
					mMyHandSlotCardView[i] = drawCardImgMyHand(i, mMyHandSlot[i]);
					mMyHandSlotCardView[i].mCardSlotId = AppValues.HAND_SLOT_1 + i;
				}
			}
			
			boolean isManaCardExist = false;
			
			for(int i=0;i<3;i++){
				if(mMyHandSlot[i].card_category == AppValues.CARD_MANA){
					isManaCardExist = true;
					break;
				}
			}
			
			if(isManaCardExist) {
				break;
			} else {
				for(int i=0;i<3;i++) {
					mPlayerDeckManager.putCardToDeck(mMyHandSlot[i]);
				}
				mPlayerDeckManager.suffleDeck();
			}
		}
	}
	
	private CardView drawCardImgMyHand(int idx, CardInfo cardinfo){
		CardView cardImgView = getCardImageView(cardinfo);
		mySlotView.addImgViewToMyHandSlot(idx, cardImgView);

		return cardImgView;
	}
	
	public void moveHandToEmptyBattleSlot(int slotNum) {
		Log.d("Premo","moveHandToBattleSlot() ENTRY");
		Log.d("Premo","moveHandToBattleSlot() slotNum = " + slotNum);
		int emptyBattleSlotNum = -1;
		for(int i=0;i<3;i++) {
			if(mMyBattleSlot[i] == null) {
				emptyBattleSlotNum = i;
				break;
			}
		}
		if(emptyBattleSlotNum != -1) {
			if (mMyBattleSlot[slotNum] == null) {
				moveHandToBattleSlot(getCardSlotNumFromSlotId(mSelectSlotId), emptyBattleSlotNum);
			}
		}else {
			getCardViewFromSlotId(mSelectSlotId).setVisibility(View.VISIBLE);
		}
		Log.d("Premo","moveHandToBattleSlot() END");
	}
	
	public int getEmptyManaSlot(){
		for (int i = 0; i < mMyManaSlot.length; i++) {
			if (mMyManaSlot[i] == null) {
				return i;
			}
		}
		return -1;
	}
	public void moveHandToManaSlot(int fromHandSlotNum){
		effectArea.post(new Runnable(){
			@Override
			public void run() {
				effectArea.setVisibility(View.VISIBLE);
			}
	    });
		final int emptyManaSlot = getEmptyManaSlot();
		mMyManaSlot[emptyManaSlot] = mMyHandSlot[fromHandSlotNum];
		mHandSlotFrame[fromHandSlotNum].removeView(mMyHandSlotCardView[fromHandSlotNum]);
		mySlotView.addImgViewToMyManaSlot(mMyHandSlotCardView[fromHandSlotNum]);
		final View targetView = mMyHandSlotCardView[fromHandSlotNum];
	    targetView.post(new Runnable(){
			@Override
			public void run() {
				targetView.setVisibility(View.VISIBLE);
			}
	    });
	    final CardInfo selectedCardInfo = getCardInfoFromSelectHandSlot();
	    
	    setManaAnimation(selectedCardInfo.material);
	    AnimationDrawable effectAnimation = (AnimationDrawable) cardEffectAnimiationView.getBackground();
	    effectAnimation.start();
	    
	    final Animation fadeOutAnimation = AnimationUtils.loadAnimation(this, R.anim.card_fadeout);
	    targetView.startAnimation(fadeOutAnimation);
	   
	    mMyHandSlot[fromHandSlotNum] = null;
	    mMyHandSlotCardView[fromHandSlotNum] = null;
	    
	    int duration = 0;
		for (int i=0; i<effectAnimation.getNumberOfFrames(); i++){
		    duration = duration + effectAnimation.getDuration(i);
		    }
		Handler handler = new Handler();
		handler.postDelayed(new Runnable(){
		    public void run() {
		    	effectArea.setVisibility(View.GONE);
		    	mySlotView.removeView(targetView);
		    	targetView.setVisibility(View.GONE);
		    	cardEffectAnimiationView.setBackgroundColor(Color.TRANSPARENT);
		    	setMana(selectedCardInfo.material, emptyManaSlot);
		    }
		}, duration); 
	}
	
	public void setMana(int material,int emptyManaSlot){
		switch (material) {
		case 0:
			mMyManaSlotView[emptyManaSlot].setImageResource(R.drawable.mana_fire);
			break;
		case 1:
			mMyManaSlotView[emptyManaSlot].setImageResource(R.drawable.mana_water);
			break;
		case 2:
			mMyManaSlotView[emptyManaSlot].setImageResource(R.drawable.mana_forest);
			break;
		}
	}
	
	public void setManaAnimation(int material){
		switch (material) {
		case 0:
			cardEffectAnimiationView.setBackgroundResource(R.anim.fire);
			break;
		case 1:
			cardEffectAnimiationView.setBackgroundResource(R.anim.water);
			break;
		case 2:
			cardEffectAnimiationView.setBackgroundResource(R.anim.forest);
			break;
		}
	}
	
	public void moveHandToBattleSlot(int fromHandSlotNum, int targetBattleSlotNum){
		mMyBattleSlot[targetBattleSlotNum] = mMyHandSlot[fromHandSlotNum];
		mMyBattleSlotView[targetBattleSlotNum] = mMyHandSlotCardView[fromHandSlotNum];
	    mMyBattleSlotView[targetBattleSlotNum].mCardSlotId = AppValues.BATTLE_SLOT_1 + targetBattleSlotNum;
	    
		mHandSlotFrame[fromHandSlotNum].removeView(mMyHandSlotCardView[fromHandSlotNum]);
		Log.d("Premo", "moveHandToBattleSlot() addImgViewToMyBattleSlot targetBattleSlotNum = " + targetBattleSlotNum);
		mySlotView.addImgViewToMyBattleSlot(targetBattleSlotNum, mMyBattleSlotView[targetBattleSlotNum]);
	    final View droppedView = mMyBattleSlotView[targetBattleSlotNum];
	    droppedView.post(new Runnable(){
			@Override
			public void run() {
		        droppedView.setVisibility(View.VISIBLE);
			}
	    });	    
	    
	    mMyHandSlot[fromHandSlotNum] = null;
	    mMyHandSlotCardView[fromHandSlotNum] = null;
	}
	
	public void moveBattleToBattleSlot(int fromBattleSlotNum,int targetBattleSlotNum){
		final View targetView = mMyBattleSlotView[targetBattleSlotNum];
		final View fromView = mMyBattleSlotView[fromBattleSlotNum];
		
		if(mMyBattleSlot[targetBattleSlotNum] == null || mMyBattleSlotView[targetBattleSlotNum] == null) {
			fromView.post(new Runnable(){
				@Override
				public void run() {
					fromView.setVisibility(View.VISIBLE);
				}
		    });
		    return;
		}
		
		CardInfo tempinfo = mMyBattleSlot[targetBattleSlotNum];
		CardView tempview = mMyBattleSlotView[targetBattleSlotNum];
		mBattleSlotFrame[targetBattleSlotNum].removeView(mMyBattleSlotView[targetBattleSlotNum]);
		
		mMyBattleSlot[targetBattleSlotNum] = mMyBattleSlot[fromBattleSlotNum];
		mMyBattleSlotView[targetBattleSlotNum] = mMyBattleSlotView[fromBattleSlotNum];
		mBattleSlotFrame[fromBattleSlotNum].removeView(mMyBattleSlotView[fromBattleSlotNum]);
				
		Log.d("Premo", "moveBattleToBattleSlot() addImgViewToMyBattleSlot targetBattleSlotNum = " + targetBattleSlotNum);
		mySlotView.addImgViewToMyBattleSlot(targetBattleSlotNum, mMyBattleSlotView[targetBattleSlotNum]);
	    
	    targetView.post(new Runnable(){
			@Override
			public void run() {
				targetView.setVisibility(View.VISIBLE);
			}
	    });
	    
	    mMyBattleSlotView[targetBattleSlotNum].mCardSlotId = AppValues.BATTLE_SLOT_1 + targetBattleSlotNum;

	    mMyBattleSlot[fromBattleSlotNum] = tempinfo;
	    mMyBattleSlotView[fromBattleSlotNum] = tempview;
	    Log.d("Premo", "moveBattleToBattleSlot() addImgViewToMyBattleSlot fromBattleSlotNum = " + fromBattleSlotNum);
		mySlotView.addImgViewToMyBattleSlot(fromBattleSlotNum, mMyBattleSlotView[fromBattleSlotNum]);
	    
	    fromView.post(new Runnable(){
			@Override
			public void run() {
				fromView.setVisibility(View.VISIBLE);
			}
	    });
	    mMyBattleSlotView[fromBattleSlotNum].mCardSlotId = AppValues.BATTLE_SLOT_1 + fromBattleSlotNum;
	}
	
	public int getCardSlotNumFromSlotId(int slotid) {
		if(slotid >= AppValues.BATTLE_SLOT_1 && slotid <= AppValues.BATTLE_SLOT_3) {
			return slotid - AppValues.BATTLE_SLOT_1;
		} else if(slotid >= AppValues.HAND_SLOT_1 && slotid <= AppValues.HAND_SLOT_3) {
			return slotid - AppValues.HAND_SLOT_1;
		}
		return -1;
	}
	
	public CardView getCardViewFromSlotId(int slotid) {
		if(slotid >= AppValues.BATTLE_SLOT_1 && slotid <= AppValues.BATTLE_SLOT_3) {
			return mMyBattleSlotView[getCardSlotNumFromSlotId(slotid)];
		} else if(slotid >= AppValues.HAND_SLOT_1 && slotid <= AppValues.HAND_SLOT_3) {
			return mMyHandSlotCardView[getCardSlotNumFromSlotId(slotid)];
		}
		return null;
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
		int deviceheight = getWindowManager().getDefaultDisplay().getHeight();
		mDeviceHeightDp = (int)(deviceheight / getResources().getDisplayMetrics().density);
		Log.d("Premo","mDeviceHeightDp = " + mDeviceHeightDp);
		
        int px = getWindowManager().getDefaultDisplay().getWidth();
        float dp = px / getResources().getDisplayMetrics().density;
		if(dp > 720 ){
			UILayoutParams.SCALED_DENSITY = (float) ((720f / 320f) * metrics.scaledDensity);
		} else {
			UILayoutParams.SCALED_DENSITY = (float) (dp / 320) * metrics.scaledDensity;
		}
	}
}
