package com.example.android_trackball;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * 显示界面
 * @author Jack  
 * @version 创建时间：2014-1-7  下午7:06:58
 */
public class MainActivity extends Activity implements TakePhotoMenuVeiw.OnClickPhtoMenuListener, View.OnClickListener {

	// 按快拍弹出菜单
	private TakePhotoMenuVeiw mTakePhotoMenuView;
	// 显示蒙版点击后自己和快拍菜单消失
    private View mPageMask;
	// 点击后弹出菜单的按钮
	private ImageView mIvPublish;
	// 点击后弹出菜单的按钮(x号)
	private ImageView mIvPublish_close;
	// 菜单按钮旋转动画
	private Animation rotateIn, rotateOut;
	
	private boolean isMenuShow = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initTakePhotoMenu();
	}


	/**
	 * 初始化底部快拍按钮菜单
	 */
	private void initTakePhotoMenu() {
		mTakePhotoMenuView = (TakePhotoMenuVeiw) findViewById(R.id.takePhotoMenuView);
		mTakePhotoMenuView.setOnClickPhtoMenuListener(this);
		mIvPublish = (ImageView) findViewById(R.id.iv_publish);
		mIvPublish_close = (ImageView) findViewById(R.id.iv_publish_close);
		mIvPublish.setOnClickListener(this);
		rotateIn = AnimationUtils.loadAnimation(this, R.anim.rotate_in);
		rotateOut = AnimationUtils.loadAnimation(this, R.anim.rotate_out);
		mPageMask = findViewById(R.id.pageMask);
		mPageMask.setOnClickListener(this);
		mIvPublish_close.setVisibility(View.GONE);
		rotateIn.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				mIvPublish_close.setVisibility(View.GONE);
				new View(MainActivity.this).post(new Runnable() {
					public void run() {
						if (!isMenuShow) {
							mIvPublish.setImageResource(R.drawable.publish_close);
						} else {
							mIvPublish.setImageResource(R.drawable.publish_open);
						}
					}
				});
			}
		});

		rotateOut.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				mIvPublish_close.setVisibility(View.GONE);
				new View(MainActivity.this).post(new Runnable() {
					public void run() {
						if (isMenuShow) {
							mIvPublish.setImageResource(R.drawable.publish_close);
						} else {
							mIvPublish.setImageResource(R.drawable.publish_open);
						}
					}
				});
			}
		});
	}
	
	/**
     * 快拍菜单进入
     */
    protected void photoMenuIn() {
        mTakePhotoMenuView.fanIn();
        mIvPublish.startAnimation(rotateIn);
        mIvPublish_close.startAnimation(rotateOut);
        mPageMask.setVisibility(View.GONE);
        isMenuShow = false;
    }

    /**
     * 快拍菜单弹出
     */
    private void photoMenuOut() {
        mTakePhotoMenuView.fanOut();
        mIvPublish.startAnimation(rotateOut);
        mIvPublish_close.startAnimation(rotateIn);
        mPageMask.setVisibility(View.VISIBLE);
        isMenuShow = true;
    }
	
    ////////////////////////进行选择按钮后的相应业务的跳转/////////////////////
    /** 发表随笔*/
	@Override
	public void postSuiBi() {
		Toast.makeText(MainActivity.this, "postSuiBi", Toast.LENGTH_LONG).show();
	}
    /** 拍照片*/
	@Override
	public void takePhoto() {
		Toast.makeText(MainActivity.this, "takePhoto", Toast.LENGTH_LONG).show();
	}
    /** 发表图片*/
	@Override
	public void selectPhoto() {
		Toast.makeText(MainActivity.this, "selectPhoto", Toast.LENGTH_LONG).show();
	}

	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
	        case R.id.iv_publish:

	            mIvPublish.setVisibility(View.VISIBLE);
	            mIvPublish_close.setVisibility(View.VISIBLE);
	            if (!isMenuShow) {
	                photoMenuOut();
	            } else {
	                photoMenuIn();
	            }
	            break;
	        case R.id.pageMask:
	            if (isMenuShow) {
	                photoMenuIn();
	            }
	            break;
	        }
		}
}