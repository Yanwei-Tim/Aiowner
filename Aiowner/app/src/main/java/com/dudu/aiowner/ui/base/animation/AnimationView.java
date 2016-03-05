package com.dudu.aiowner.ui.base.animation;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


import com.dudu.aiowner.commonlib.commonutils.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2016/2/2.
 */
public class AnimationView extends SurfaceView implements SurfaceHolder.Callback {

    private static final String TAG = "AnimationView";

    private AnimationThread mThread;

    private OnAnimPlayListener onAnimPlayListener;

    private String category;

    private int model;

    private boolean isAppear = true;

    private Context context;

    private int maxImageCycleCount;

    public AnimationView(Context context, String category, int model, int maxImageCycleCount) {
        super(context);
        this.category = category;
        this.model = model;
        this.maxImageCycleCount = maxImageCycleCount;
        initView(context);
    }

    public AnimationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public void setIsAppear(boolean isAppear) {
        this.isAppear = isAppear;
    }

    private void initView(Context context) {
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);
        setFocusable(true);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        startAnim();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.e(TAG, "surfaceChanged");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        stopAnim();
    }

    public void startAnim() {
        if (mThread == null) {
            mThread = new AnimationThread(getContext(), getHolder());
            mThread.setRunning(true);
            mThread.start();
        } else {
            mThread.setRunning(true);
        }
    }

    public void stopAnim() {
        if (mThread != null) {
            mThread.setRunning(false);
            try {
                mThread.join();
            } catch (InterruptedException e) {
                Log.e("CarCheckingView", e.getMessage());
            }
            mThread = null;
        }
    }

    private class AnimationThread extends Thread {

        private static final int MAXIMUM_FRAME_COUNT = 101;

        private  int maxImageCycleCount = 101;

        private static final String PICTURE_PREFIX = "Ani_";


        private static final String VEHICLE_CATEGORY_DIR = "/animation/";

        private Context mContext;

        private boolean mRunning;

        private SurfaceHolder mHolder;

        private Paint mPaint;

        private int mFrameCounter = 0;

        public AnimationThread(Context context, SurfaceHolder holder) {
            mContext = context;
            mHolder = holder;
            mPaint = new Paint();
            mPaint.setAntiAlias(true);
        }

        public void setRunning(boolean running) {
            mRunning = running;
        }

        @Override
        public void run() {
//            if (isAppear) {
//                Log.v("vehicle","---------------动画1");
//                doAppearAnimation();
//            }
//            Log.v("vehicle","---------------动画2");
            doCycleAnimation();
        }

        private void doCycleAnimation() {
            mFrameCounter = 0;
//            path = "cycle";
            while (mRunning && mFrameCounter < maxImageCycleCount) {
                mFrameCounter++;
                if (mFrameCounter % model == 0) {
                    Canvas c = null;
                    try {
                        synchronized (mHolder) {
                            Log.v("CarCheckingView1", "当前播放帧数: " + mFrameCounter);
                            c = mHolder.lockCanvas();
                            doAnimation(c);

                            if (mFrameCounter == maxImageCycleCount) {
                                boolean play = onAnimPlayListener.play();
                                if (!play) {
                                    mFrameCounter = 0;
                                }
                            }
                        }
                    } finally {
                        if (c != null) {
                            mHolder.unlockCanvasAndPost(c);
                        }
                    }
                }
            }
        }

        private void doAppearAnimation() {
            while (mRunning && mFrameCounter < MAXIMUM_FRAME_COUNT) {
                mFrameCounter++;
                if (mFrameCounter % model != 0) {
                    Canvas c = null;
                    try {
                        synchronized (mHolder) {
                            Log.v("CarCheckingView", "当前播放帧数: " + mFrameCounter);
                            c = mHolder.lockCanvas();

                            doAnimation(c);
                        }
                    } finally {
                        if (c != null) {
                            mHolder.unlockCanvasAndPost(c);
                        }
                    }
                }
            }
        }

        private void doAnimation(Canvas c) {

            c.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            Bitmap bitmap = loadAnimationBitmap();
            if (bitmap != null) {
                c.drawBitmap(bitmap, 0, 0, mPaint);
            }

        }

        private Bitmap loadAnimationBitmap() {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inMutable = true;

            // AssetManager am = mContext.getAssets();
            String stCount;
            if (mFrameCounter < 10) {
                stCount = "00" + mFrameCounter;
            } else if (mFrameCounter < 100) {
                stCount = "0" + mFrameCounter;
            } else {
                stCount = "" + mFrameCounter;
            }

            InputStream is;
            File file = new File(FileUtils.getStorageDir(), VEHICLE_CATEGORY_DIR + category + "/" + PICTURE_PREFIX + stCount + ".png");

            // is = am.open("car_checking/" + PICTURE_PREFIX + mFrameCounter + ".png");
            Log.v("vehicle", "count:" + stCount);
            Log.v("vehicle1", "path:" + VEHICLE_CATEGORY_DIR + category + "/" + PICTURE_PREFIX + stCount + ".png");
            if (file.exists()) {
                Log.v("vehicle", file.getPath() + "存在");
                try {
                    is = new FileInputStream(file);
                    return BitmapFactory.decodeStream(is);
                } catch (IOException e) {
                    Log.e("vehicle", e.getMessage());
                    return null;
                }
            } else {
                Log.v("vehicle", file.getPath() + "不存在");
            }
            return null;
        }
    }

    public void setOnAnimPlayListener(OnAnimPlayListener listener) {
        this.onAnimPlayListener = listener;
    }

    public interface OnAnimPlayListener {
        boolean play();
    }
}
