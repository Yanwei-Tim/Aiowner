package com.dudu.aiowner.ui.activity.maintenanceAssistant;

/**
 * Created by Administrator on 2015/12/11.
 */

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.ImageView;


public class WaterAnimation {
    private ImageView mImageView;
    private int[] mFrameRess;
    private int mDuration;
    private FrameLayout login_layout;

    private int mLastFrameNo;
    private Context mContext;

    public void play(ImageView pImageView, int[] pFrameRess, int pDuration, Context mContext, FrameLayout login_layout) {
        mImageView = pImageView;
        mFrameRess = pFrameRess;
        mDuration = pDuration;
        mLastFrameNo = pFrameRess.length - 1;
        this.mContext = mContext;
        this.login_layout = login_layout;
        mImageView.setBackgroundResource(mFrameRess[0]);
        playConstant(1);
    }

    private void playConstant(final int pFrameNo) {
        mImageView.postDelayed(new Runnable() {
            public void run() {
                mImageView.setBackgroundResource(mFrameRess[pFrameNo]);
                if (pFrameNo == mLastFrameNo) {

                    //设置循环播放
                    playConstant(0);

                    ImageView finalImage = new ImageView(mContext);
                    finalImage.setAlpha(10);

                    /*通知imageView 刷新屏幕*/
                    finalImage.postInvalidate();
                    return;
                } else {
                    playConstant(pFrameNo + 1);
                }
            }
        }, 1);
    }
}
