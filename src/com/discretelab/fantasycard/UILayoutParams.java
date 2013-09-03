package com.discretelab.fantasycard;

import android.graphics.Rect;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout.LayoutParams;

public class UILayoutParams {

	public static float SCALED_DENSITY;
	public static double DENSITY_DPI;

	public static LayoutParams changeRect(LayoutParams layoutParams, Rect rect) {
		layoutParams.width = (int) (rect.right * SCALED_DENSITY);
		layoutParams.height = (int) (rect.bottom * SCALED_DENSITY);
		layoutParams.leftMargin = (int) (rect.left * SCALED_DENSITY);
		layoutParams.topMargin = (int) (rect.top * SCALED_DENSITY);
		return layoutParams;
	}

	public static LayoutParams wrapRect(LayoutParams layoutParams, Rect rect) {
		layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
		layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
		layoutParams.leftMargin = (int) (rect.left * SCALED_DENSITY);
		layoutParams.topMargin = (int) (rect.top * SCALED_DENSITY);
		return layoutParams;
	}

	public static LayoutParams changeMargin(LayoutParams layoutParams, Rect rect) {
		layoutParams.leftMargin = (int) (rect.left * SCALED_DENSITY);
		layoutParams.topMargin = (int) (rect.top * SCALED_DENSITY);
		layoutParams.rightMargin = (int) (rect.right * SCALED_DENSITY);
		layoutParams.bottomMargin = (int) (rect.bottom * SCALED_DENSITY);
		return layoutParams;
	}

	public static LayoutParams changeAnchor(LayoutParams layoutParams,
			String anchor) {
		if (anchor.length() > 0 && anchor.contains(",")) {
			String[] anchorAry = anchor.split(",");

			float addX = layoutParams.leftMargin;
			float addY = layoutParams.topMargin;

			String anchorType = anchorAry[2];
			// X Operator
			if (anchorType.equals("center")) {
				addX -= (layoutParams.width / 2);
			} else if (anchorType.contains("right")) {
				addX -= layoutParams.width;
			}

			// Y Operator
			if (anchorType.equals("center") || anchorType.contains("middle")) {
				addY -= (layoutParams.height / 2);
			} else if (anchorType.contains("bottom")) {
				addY -= layoutParams.height;
			}

			layoutParams.leftMargin = (int) ((Integer.valueOf(anchorAry[0]) * SCALED_DENSITY) + addX);
			layoutParams.topMargin = (int) ((Integer.valueOf(anchorAry[1]) * SCALED_DENSITY) + addY);
		}
		return layoutParams;
	}
}
