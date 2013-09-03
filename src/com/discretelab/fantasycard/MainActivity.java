package com.discretelab.fantasycard;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;

public class MainActivity extends Activity {

	private MyCardSlotViewGroup mySlotView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();
		
	}
	
	public void init() {
		setDensity();
		appOnAttachedToWindow();
		
		mySlotView = (MyCardSlotViewGroup)findViewById(R.id.myslotview);
	}
    
	@Override
	public void onAttachedToWindow() {
		super.onAttachedToWindow();
		appOnAttachedToWindow();
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
		}else{
			UILayoutParams.SCALED_DENSITY = (float) (dp / 320) * metrics.scaledDensity;
		}
	}
}
