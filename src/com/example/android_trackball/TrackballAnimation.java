package com.example.android_trackball;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;


/**
 * 处理快拍、选照片、随笔 菜单的动画工具类
 * @author Jack
 */
public class TrackballAnimation {
	
	/**
	 * 包括新弹出的3个按钮，点击后的旋转动画
	 * @param fromDegrees
	 * @param toDegrees
	 * @param durationMillis
	 * @return
	 */
	public static Animation getRotateAnimation(float fromDegrees, float toDegrees, long durationMillis) {
		
		RotateAnimation rotate = new RotateAnimation(fromDegrees, toDegrees, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		rotate.setDuration(durationMillis);
		rotate.setInterpolator(new DecelerateInterpolator());   //减速
		rotate.setFillAfter(true);   //fillBefore是指动画结束时画面停留在第一帧，fillAfter是指动画结束是画面停留在最后一帧。
		return rotate;
	}

	/**
	 * 设置透明度动画
	 * @param fromAlpha
	 * @param toAlpha
	 * @param durationMillis
	 * @return
	 */
	public static Animation getAlphaAnimation(float fromAlpha, float toAlpha, long durationMillis) {
		
		AlphaAnimation alpha = new AlphaAnimation(fromAlpha, toAlpha);
		alpha.setDuration(durationMillis);
		alpha.setFillAfter(true);
		return alpha;
	}

	/**
	 * 设置缩放动画
	 * @param durationMillis
	 * @return
	 */
	public static Animation getScaleAnimation(long durationMillis) {
		
		ScaleAnimation scale = new ScaleAnimation(1.0f, 1.5f, 1.0f, 1.5f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		scale.setDuration(durationMillis);
		return scale;
	}

	/**
	 * 设置位移动画
	 * @param fromXDelta
	 * @param toXDelta
	 * @param fromYDelta
	 * @param toYDelta
	 * @param durationMillis
	 * @return
	 */
	public static Animation getTranslateAnimation(float fromXDelta, float toXDelta, float fromYDelta, float toYDelta, long durationMillis) {
		
		TranslateAnimation translate = new TranslateAnimation(fromXDelta, toXDelta, fromYDelta, toYDelta);
		translate.setDuration(durationMillis);
		translate.setInterpolator(new OvershootInterpolator(3));
		translate.setFillAfter(true);
		return translate;
	}

	/**
	 * 开启动画
	 * @param relativeLayout
	 * @param durationMillis
	 */
	public static void startOpenAnimation(RelativeLayout relativeLayout, long durationMillis, Context context) {
		
		relativeLayout.setVisibility(View.VISIBLE);
		for (int i = 0; i < relativeLayout.getChildCount(); i++) {
			
			ImageView imageView = (ImageView) relativeLayout.getChildAt(i);
			imageView.setVisibility(View.VISIBLE);
			MarginLayoutParams params = (MarginLayoutParams) imageView.getLayoutParams();
			AnimationSet set = new AnimationSet(true);
			set.addAnimation(getRotateAnimation(-360*6, 0, durationMillis));
			set.addAnimation(getAlphaAnimation(0.0f, 1.0f, durationMillis));
			/**相对初始位置的偏移量*/
			float fromXDelta = 0; 
			
			switch (i) {
			
				case 0:   //左侧按钮往左移动
					
					fromXDelta = context.getResources().getDimension(R.dimen.take_photo_btn_margin);
					break;
				case 1:
					
					fromXDelta = 0;
					break;
				case 2:   //右侧按钮往右移动
					
					fromXDelta = -context.getResources().getDimension(R.dimen.take_photo_btn_margin);;
					break;
			}
			set.addAnimation(getTranslateAnimation(fromXDelta, 0f, params.bottomMargin, 0f, durationMillis));
			set.setFillAfter(true);
			set.setDuration(durationMillis);
			set.setStartOffset((i * durationMillis/5));
			set.setInterpolator(new OvershootInterpolator(2f)); //设置此动画的加速曲线。2倍冲插补。
			imageView.startAnimation(set);
		}
	}

	/**
	 * 关闭动画
	 * @param relativeLayout
	 * @param durationMillis
	 */
	public static void startCloseAnimation(final RelativeLayout relativeLayout, long durationMillis, Context context) {
		
		for (int i = 0; i < relativeLayout.getChildCount(); i++) {
			final ImageView imageView = (ImageView) relativeLayout.getChildAt(i);
			MarginLayoutParams params = (MarginLayoutParams) imageView.getLayoutParams();
			AnimationSet set = new AnimationSet(true);
			set.addAnimation(getRotateAnimation(0, -360*6, durationMillis));
			set.addAnimation(getAlphaAnimation(1.0f, 0.5f, durationMillis));
			/**相对初始位置的偏移量*/
			float xTrans = 0;
			switch (i) {
			case 0:  //左侧按钮往右移动
				xTrans = context.getResources().getDimension(R.dimen.take_photo_btn_margin);;
				break;
			case 1:
				xTrans = 0;
				break;
			case 2:  //右侧按钮往左移动
				xTrans = -context.getResources().getDimension(R.dimen.take_photo_btn_margin);;
				break;
			}
			set.addAnimation(getTranslateAnimation(0f, xTrans, 0f, params.bottomMargin, durationMillis));
			set.setFillAfter(true);
			set.setDuration(durationMillis);
			set.setInterpolator(new AnticipateInterpolator(2f));
			if(0 == i)
			set.setAnimationListener(new Animation.AnimationListener() {
				public void onAnimationStart(Animation arg0) {
				}
				public void onAnimationRepeat(Animation arg0) {
				}
				public void onAnimationEnd(Animation arg0) {
					relativeLayout.setVisibility(View.GONE);
				}
			});
			imageView.startAnimation(set);
		}
	}

	/**
	 * 单击中间按钮，加载三个按钮的动画
	 * @param durationMillis
	 * @return
	 */
	public static Animation clickAnimation(long durationMillis) {
		AnimationSet set = new AnimationSet(true);
		set.addAnimation(getAlphaAnimation(1.0f, 0.3f, durationMillis));
		set.addAnimation(getScaleAnimation(durationMillis));
		set.setDuration(durationMillis);
		return set;
	}
}