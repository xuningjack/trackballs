package com.example.android_trackball;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * 自定义快拍、选照片、随笔 按钮菜单
 * @author Jack
 */
public class TakePhotoMenuVeiw extends RelativeLayout implements android.view.View.OnClickListener{

	private Context mContext;
	/** 快拍、选照片、随笔 **/
	private ImageView mIvTakePhoto;
	private ImageView mIvPostSuiBi;
	private ImageView mIvSelectPhoto;
	/** RelativeLayout包含快拍、选照片、随笔三个按钮 **/
	private RelativeLayout mUgcLayout;
	/** 标识是否显示 **/
	public boolean mIsShowing = false;
	/** 快拍、选照片、随笔点击动画 **/
	private Animation mBtnDismissAnim;
	/** 点击哪一项 **/
	private int mClickItem;
	/** 点击项常量 **/
	private static final int TAKE_PHOTO_ITEM = 0X1;
	private static final int SELECT_PHOTO_ITEM = 0X2;
	private static final int POST_SUIBI_ITEM = 0X3;
	/**轨迹球动画的加载时间*/
	private static final int DURATION = 500;
	/**快拍菜单三个按钮单击Listener*/
	private OnClickPhtoMenuListener onClickPhtoMenuListener;

	
	public void setOnClickPhtoMenuListener(OnClickPhtoMenuListener onClickPhtoMenuListener) {
		
		this.onClickPhtoMenuListener = onClickPhtoMenuListener;
	}

	/**必须有否则报错*/
	public TakePhotoMenuVeiw(Context context, AttributeSet attrs) {
		
		super(context, attrs);
		mContext = context;
		init();
	}
	
	private void init(){
		
		LayoutInflater inflater=(LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.take_photo_menu_layout, this);
		mUgcLayout = (RelativeLayout) findViewById(R.id.ugc_layout);
		mIvTakePhoto = (ImageView) findViewById(R.id.btn_take_photo);
		mIvSelectPhoto = (ImageView) findViewById(R.id.btn_photo_direct);
		mIvPostSuiBi = (ImageView) findViewById(R.id.btn_post_suibi);
		mIvTakePhoto.setOnClickListener(this);
		mIvSelectPhoto.setOnClickListener(this);
		mIvPostSuiBi.setOnClickListener(this);
		
		mBtnDismissAnim = TrackballAnimation.clickAnimation(DURATION);
		mBtnDismissAnim.setAnimationListener(new AnimationListener() {
			public void onAnimationStart(Animation animation) {
			}
			public void onAnimationRepeat(Animation animation) {
			}
			public void onAnimationEnd(Animation animation) {
				switch (mClickItem) {
				case TAKE_PHOTO_ITEM:
					onClickPhtoMenuListener.takePhoto();
					fanIn();
					break;
				case SELECT_PHOTO_ITEM:
					onClickPhtoMenuListener.selectPhoto();
					fanIn();
					break;
				case POST_SUIBI_ITEM:
					onClickPhtoMenuListener.postSuiBi();
					fanIn();
					break;
				default:
					break;
				}
			}
		});
	}
	
	
	/**
	 *  快拍菜单按钮Listener
	 * @author DuanWenqiang
	 */
	public interface OnClickPhtoMenuListener{
		/**
		 * 点击第一个按钮发布随笔
		 */
		public abstract void postSuiBi();
		/**
		 * 点击第二个按钮拍照
		 */
		public abstract void takePhoto();
		/**
		 * 点击第三个按钮选择照片
		 */
		public abstract void selectPhoto();
	}

	//TODO 单击按钮后相应的业务跳转
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_take_photo:
			mClickItem = TAKE_PHOTO_ITEM;
			mIvTakePhoto.startAnimation(mBtnDismissAnim);
			break;
		case R.id.btn_photo_direct:
			mClickItem = SELECT_PHOTO_ITEM;
			mIvSelectPhoto.startAnimation(mBtnDismissAnim);
			break;
		case R.id.btn_post_suibi:
			mClickItem = POST_SUIBI_ITEM;
			mIvPostSuiBi.startAnimation(mBtnDismissAnim);
			break;
		}
	}
	
	
	/**
	 * 关闭菜单
	 */
	public void fanIn() {
		
		mIsShowing = false;
		TrackballAnimation.startCloseAnimation(mUgcLayout, DURATION, mContext);
	}
	/**
	 * 打开菜单
	 */
	public void fanOut() {
		
		mIsShowing = true;
		TrackballAnimation.startOpenAnimation(mUgcLayout, DURATION, mContext);
	}
}