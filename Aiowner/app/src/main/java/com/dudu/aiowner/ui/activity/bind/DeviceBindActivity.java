package com.dudu.aiowner.ui.activity.bind;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.dudu.aiowner.R;
import com.dudu.aiowner.rest.model.BindRequest;
import com.dudu.aiowner.rest.model.BindResponse;
import com.dudu.aiowner.ui.activity.bind.decoding.CaptureActivityHandler;
import com.dudu.aiowner.ui.activity.bind.decoding.InactivityTimer;
import com.dudu.aiowner.ui.activity.user.DeviceMatchAcitivty;
import com.dudu.aiowner.ui.base.RBaseActivity;
import com.dudu.workflow.common.CommonParams;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.yo.libs.app.ScanParams;
import com.yo.libs.dimenscode.ScanHelper;
import com.yo.libs.utils.AssetsUtils;
import com.yo.libs.utils.FileUtils;
import com.zxing.android.MessageIDs;
import com.zxing.android.camera.CameraManager;
import com.zxing.android.view.ViewfinderView;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Vector;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Robi on 2016-03-0917:27.
 */
public class DeviceBindActivity extends RBaseActivity implements SurfaceHolder.Callback {

    /**
     * 文本选中的颜色
     */
    private static final int TEXT_COLOR_PRESSED = Color.parseColor("#4BCAF7");
    /**
     * 默认扫描
     */
    private static final int DEFAULT_SCAN = -1;
    /**
     * 默认二维码扫描框的宽度
     */
    private static final int DEFAULT_QRCODE_WIDTH = 700;
    /**
     * 默认二维码扫描框的高度
     */
    private static final int DEFAULT_QRCODE_HEIGHT = 700;
    /**
     * 默认条形码扫描框的宽度
     */
    private static final int DEFAULT_BARCODE_WIDTH = 500;
    /**
     * 默认条形码扫描框的高度
     */
    private static final int DEFAULT_BARCODE_HEIGHT = 250;
    /**
     * 最小二维码扫描框的宽度
     */
    private static final int MIN_QRCODE_WIDTH = 400;
    /**
     * 最小二维码扫描框的高度
     */
    private static final int MIN_QRCODE_HEIGHT = 400;
    /**
     * 最小条形码扫描框的宽度
     */
    private static final int MIN_BARCODE_WIDTH = 400;
    /**
     * 最小条形码扫描框的高度
     */
    private static final int MIN_BARODE_HEIGHT = 200;
    /**
     * 震动时长
     */
    private static final long VIBRATE_DURATION = 200L;
    /**
     * 启动相册 Tag
     */
    private static final int SELECT_PICTURE = 0;
    /**
     * When the beep has finished playing, rewind to queue up another one.
     */
    private final MediaPlayer.OnCompletionListener beepListener = new MediaPlayer.OnCompletionListener() {
        public void onCompletion(MediaPlayer mediaPlayer) {
            mediaPlayer.seekTo(0);
        }
    };
    /**
     * 处理 捕获相机消息
     */
    private CaptureActivityHandler handler;
    private ViewfinderView viewfinderView;
    private SurfaceView surfaceView;
    private boolean hasSurface;
    /**
     * 条形码编码集合
     */
    private Vector<BarcodeFormat> decodeFormats;
    /**
     * 字符编码
     */
    private String characterSet;
    /**
     * 限时关闭Activity
     */
    private InactivityTimer inactivityTimer;
    private MediaPlayer mediaPlayer;
    /**
     * 是否播放声音(Bi...)
     */
    private boolean playBeep;
    // private static final float BEEP_VOLUME = 0.10f;
    private boolean vibrate;
    /**
     * 相机管理类
     */
    private CameraManager cameraManager;
    /**
     * 图片路径
     */
    private String mPhotoPath;
    /**
     * 功能,即二维码扫描 或者 条形码
     */
    private int mFeatures;
    /**
     * 二维码宽高
     */
    private int mQRCodeWidth, mQRCodeHeight;
    /**
     * 条形码宽高
     */
    private int mBarCodeWidth, mBarCodeHeight;
    /**
     * 解析的图片
     */
    private Bitmap mScanBitmap;
    /**
     * 闪光灯标记
     */
    private boolean isFlash;
    /**
     * 图片资源
     */
    private Drawable mBackImg, mBarcodeNormalImg, mBarcodePressedImg,
            mQRCodeNormalImg, mQRCodePressedImg, mFlashNormalImg,
            mFlashPressedImg, mGalleryNormalImg, mGalleryPressedImg;

    public static void launch(Context context) {
        Intent intent = new Intent(context, DeviceBindActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
//        findViewById(R.id.title_layout).setBackgroundColor(getResources().getColor(R.color.aiowner));
//        baseBinding.baseTitleLayout.
        observableFactory.getTitleObservable().hasBackGround.set(true);
        observableFactory.getTitleObservable().titleText.set("");


        viewfinderView = (ViewfinderView) mViewHolder.v(R.id.finderView);
        surfaceView = (SurfaceView) mViewHolder.v(R.id.surfaceView);

        // 初始化图片
        initImages();
        // 接受传递过来的值
        mFeatures = getIntent().getIntExtra(ScanParams.WHICH, DEFAULT_SCAN);
        mQRCodeWidth = getIntent().getIntExtra(ScanParams.QRCODE_WIDTH_PIX,
                DEFAULT_QRCODE_WIDTH);
        mQRCodeHeight = getIntent().getIntExtra(ScanParams.QRCODE_HEIGHT_PIX,
                DEFAULT_QRCODE_HEIGHT);
        mBarCodeWidth = getIntent().getIntExtra(ScanParams.BARCODE_WIDTH_PIX,
                DEFAULT_BARCODE_WIDTH);
        mBarCodeHeight = getIntent().getIntExtra(ScanParams.BARCODE_HEIGHT_PIX,
                DEFAULT_BARCODE_HEIGHT);
        //检查参数
        checkParams();
        // 屏幕常亮
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        window.setBackgroundDrawable(new ColorDrawable(Color.RED));
        hasSurface = false;
        inactivityTimer = new InactivityTimer(this);
    }


    @Override
    protected void initEvent() {
        super.initEvent();
        mViewHolder.v(R.id.tvInput).setOnClickListener(v -> {
            launchActivity(DeviceMatchAcitivty.class);
            finish();
        });
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_bind;
    }

    /**
     * 检查参数
     */
    private void checkParams() {
        if (mQRCodeWidth < MIN_QRCODE_WIDTH
                || mQRCodeWidth > getScreenPix()[0]) {
            mQRCodeWidth = DEFAULT_QRCODE_WIDTH;
        }
        if (mQRCodeHeight < MIN_QRCODE_HEIGHT
                || mQRCodeHeight > getScreenPix()[1] / 2) {
            mQRCodeHeight = DEFAULT_QRCODE_HEIGHT;
        }
        if (mBarCodeWidth < MIN_BARCODE_WIDTH
                || mBarCodeWidth > getScreenPix()[0]) {
            mBarCodeWidth = DEFAULT_BARCODE_WIDTH;
        }
        if (mBarCodeHeight < MIN_BARODE_HEIGHT
                || mBarCodeHeight > getScreenPix()[1] / 2) {
            mBarCodeHeight = DEFAULT_BARCODE_HEIGHT;
        }

    }

    /**
     * 获取屏幕的像素
     *
     * @return 宽和高的数组
     */
    private int[] getScreenPix() {
        DisplayMetrics outMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        int[] screenPix = new int[2];
        screenPix[0] = outMetrics.widthPixels;
        screenPix[1] = outMetrics.heightPixels;
        return screenPix;
    }

    /**
     * 初始化图片
     */
    private void initImages() {
        mBackImg = AssetsUtils.assets2Drawable(this, "back.png");
        mBarcodeNormalImg = AssetsUtils.assets2Drawable(this,
                "barcode_normal.png");
        mBarcodePressedImg = AssetsUtils.assets2Drawable(this,
                "barcode_pressed.png");
        mQRCodeNormalImg = AssetsUtils.assets2Drawable(this,
                "qrcode_normal.png");
        mQRCodePressedImg = AssetsUtils.assets2Drawable(this,
                "qrcode_pressed.png");
        mGalleryNormalImg = AssetsUtils.assets2Drawable(this,
                "gallery_normal.png");
        mGalleryPressedImg = AssetsUtils.assets2Drawable(this,
                "gallery_pressed.png");
        mFlashNormalImg = AssetsUtils.assets2Drawable(this,
                "flash_light_normal.png");
        mFlashPressedImg = AssetsUtils.assets2Drawable(this,
                "falsh_ligh_pressed.png");
    }

    /**
     * 根据传入的features，选择 进行二维码or条码 进行扫描
     *
     * @param features 值可能为 Constant.QRCODE_FEATURES(二维码) or
     *                 Constant.BARCODE_FEATURES(条码)
     */
    private void chooseWhichFeatures(int features) {
        switch (features) {
            case ScanParams.QRCODE_FEATURES:
                chooseQRCodeScan();
                break;
            case ScanParams.BARCODE_FEATURES:
                chooseBarCodeScan();
                break;
            case DEFAULT_SCAN:
                chooseQRCodeScan();
                break;
            default:
                break;
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void onResume() {
        super.onResume();
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        cameraManager = new CameraManager(getApplication());
        // 初始化扫描框
        chooseWhichFeatures(mFeatures);

        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        if (hasSurface) {
            // 初始化相机
            initCamera(surfaceHolder);
        } else {
            surfaceHolder.addCallback(this);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }
        decodeFormats = null;
        characterSet = null;
        playBeep = true;
        AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
        if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
            playBeep = false;
        }
        // 初始化BiBi..声音
        initBeepSound();
        vibrate = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (handler != null) {
            // 关闭相机预览
            handler.quitSynchronously();
            handler = null;
        }
        // 关闭闪光灯
        cameraManager.offFlashLight();
        // 释放相机
        cameraManager.closeDriver();
    }

    @Override
    protected void onDestroy() {
        inactivityTimer.shutdown();
        super.onDestroy();
    }

    /**
     * 初始化 打开相机 启动线程解析
     *
     * @param surfaceHolder
     */
    private void initCamera(SurfaceHolder surfaceHolder) {
        try {
            cameraManager.openDriver(surfaceHolder);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        // 初始化 Handler 并开启线程解析
        if (handler == null) {
            handler = new CaptureActivityHandler(this, decodeFormats,
                    characterSet);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
    }

    /**
     * 当SurFace 创建完成后回调此函数
     */
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!hasSurface) {
            hasSurface = true;
            initCamera(holder);
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;
    }

    /**
     * 获取 相机管理
     *
     * @return CameraManger
     */
    public CameraManager getCameraManager() {
        return cameraManager;
    }

    /**
     * 获取 扫描框
     *
     * @return
     */
    public ViewfinderView getViewfinderView() {
        return viewfinderView;
    }

    /**
     * 获取 消息处理
     *
     * @return
     */
    public Handler getHandler() {
        return handler;
    }

    /**
     * 重绘
     */
    public void drawViewfinder() {
        viewfinderView.drawViewfinder();
    }

    /**
     * 处理解析
     *
     * @param obj
     * @param barcode
     */
    public void handleDecode(Result obj, Bitmap barcode) {
        inactivityTimer.onActivity();//
        playBeepSoundAndVibrate();
        showResult(obj, barcode);
    }

    /**
     * 处理中文乱码问题
     *
     * @param text
     * @return
     */
    private String recode(String text) {
        String format = "";
        final boolean ISO = Charset.forName("ISO-8859-1").newEncoder()
                .canEncode(text);
        try {
            if (ISO) {
                // 编码转成 GB2312
                format = new String(text.getBytes("ISO-8859-1"), "GB2312");
            } else {
                format = text;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return format;
    }

    /**
     * 回调 onActivityResult
     *
     * @param rawResult 扫描结果
     * @param barcode
     */
    private void showResult(final Result rawResult, Bitmap barcode) {
        String result = recode(rawResult.getText());
        Intent intent = new Intent();
        intent.putExtra(ScanParams.RESULT, result);
        setResult(ScanParams.RESULT_OK, intent);
        onScanSuccess(result);
//        finish();
    }

    private void onScanSuccess(String result) {
        final String phone = CommonParams.getInstance().getUserName();

        if (!TextUtils.isEmpty(result)) {
            BindServiceImpl.bind(new BindRequest(phone, "android", result), new Callback<BindResponse>() {
                @Override
                public void onResponse(Call<BindResponse> call, Response<BindResponse> response) {
                    BindResponse body = response.body();
                    if (body != null) {
                        showToast(body.resultMsg);
                        if (body.resultCode == 0) {
                            //绑定成功
                        } else {
                            onPause();
                            onResume();
                        }
                    }
                }

                @Override
                public void onFailure(Call<BindResponse> call, Throwable t) {
                    showToast(t.toString());
                }
            });
        }
    }

    /**
     * 重置相机预览
     *
     * @param delayMS 延迟重置的时间，mm为单位
     */
    public void restartPreviewAfterDelay(long delayMS) {
        if (handler != null) {
            handler.sendEmptyMessageDelayed(MessageIDs.restart_preview, delayMS);
        }
    }

    /**
     * 初始化 声音
     */
    private void initBeepSound() {
        if (playBeep && mediaPlayer == null) {
            setVolumeControlStream(AudioManager.STREAM_MUSIC);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnCompletionListener(beepListener);

            try {
                AssetFileDescriptor fileDescriptor = getAssets().openFd(
                        "qrbeep.ogg");
                this.mediaPlayer.setDataSource(
                        fileDescriptor.getFileDescriptor(),
                        fileDescriptor.getStartOffset(),
                        fileDescriptor.getLength());
                this.mediaPlayer.setVolume(0.1F, 0.1F);
                this.mediaPlayer.prepare();
            } catch (IOException e) {
                this.mediaPlayer = null;
            }
        }
    }

    /**
     * 播放声音并震动
     */
    private void playBeepSoundAndVibrate() {
        // 播放声音
        if (playBeep && mediaPlayer != null) {
            mediaPlayer.start();
        }
        // 震动
        if (vibrate) {
            Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(VIBRATE_DURATION);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        boolean res = super.onKeyDown(keyCode, event);
        try {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                setResult(RESULT_CANCELED);
                finish();
                res = true;
            } else if (keyCode == KeyEvent.KEYCODE_FOCUS
                    || keyCode == KeyEvent.KEYCODE_CAMERA) {
                res = true;
            }
        } catch (Exception e) {
            Toast.makeText(this, "异常错误", Toast.LENGTH_SHORT).show();
            Log.e("Key Down ", "Key Down Error ");
        }
        return res;
    }

    /**
     * 退出
     */
    private void backHome() {
        finish();
    }

    /**
     * 闪光灯开关
     */
    @SuppressWarnings("deprecation")
    private void switchLight() {
        cameraManager.switchFlashLight();
        if (!isFlash) {
            isFlash = true;
        } else {
            isFlash = false;
        }
    }

    /**
     * 选择相册
     */
    @SuppressWarnings("deprecation")
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void chooseGallery() {
        // 重置 底部所有的view
        resetAllView();
//        mGalleryText.setTextColor(TEXT_COLOR_PRESSED);
//        mGalleryIcon.setBackgroundDrawable(mGalleryPressedImg);
        Intent intent = new Intent();
        if (Build.VERSION.SDK_INT < 19) {
            intent.setAction(Intent.ACTION_GET_CONTENT);
        } else {
            intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        }
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "选择图片"),
                SELECT_PICTURE);
    }

    /**
     * 选择扫描 条形码
     */
    @SuppressWarnings("deprecation")
    private void chooseBarCodeScan() {
        resetAllView();
//        mScanInfo.setText("请将条码置于取景框内扫描");
//        mBarCodeText.setTextColor(TEXT_COLOR_PRESSED);
//        mBarCodeIcon.setBackgroundDrawable(mBarcodePressedImg);
        // 设置扫描框的大小
        cameraManager.setManualFramingRect(mBarCodeWidth, mBarCodeHeight);
        // 重新绘制 扫描线
        viewfinderView.reDraw();
        viewfinderView.setCameraManager(cameraManager);
        drawViewfinder();
    }

    /**
     * 选择扫描
     */
    @SuppressWarnings("deprecation")
    private void chooseQRCodeScan() {
        resetAllView();
//        mScanInfo.setText("将二维码/条形码放入框内,完成绑定");
//        mQRCodeText.setTextColor(TEXT_COLOR_PRESSED);
//        mQRCodeIcon.setBackgroundDrawable(mQRCodePressedImg);
        ;
        // 设置 扫描框的大小
        cameraManager.setManualFramingRect(mQRCodeWidth, mQRCodeHeight);
        // 重新绘制 扫描线
        viewfinderView.reDraw();
        viewfinderView.setCameraManager(cameraManager);
        drawViewfinder();
    }

    /**
     * 还原化底部三个view
     */
    @SuppressWarnings("deprecation")
    private void resetAllView() {
//        mBarCodeText.setTextColor(Color.WHITE);
//        mQRCodeText.setTextColor(Color.WHITE);
//        mGalleryText.setTextColor(Color.WHITE);
//
//        mBarCodeIcon.setBackgroundDrawable(mBarcodeNormalImg);
//        mQRCodeIcon.setBackgroundDrawable(mQRCodeNormalImg);
//        mGalleryIcon.setBackgroundDrawable(mGalleryNormalImg);
    }

    /**
     * 得到选中图片 并解析
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == SELECT_PICTURE) {
            String[] proj = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(data.getData(), proj,
                    null, null, null);
            if (cursor.moveToFirst()) {
                int columnIndex = cursor
                        .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                mPhotoPath = cursor.getString(columnIndex);
                if (TextUtils.isEmpty(mPhotoPath)) {
                    mPhotoPath = FileUtils.getPath(this, data.getData());
                    Log.i("Photo path", "the path : " + mPhotoPath);
                }
                Log.i("Photo path", "the path : " + mPhotoPath);
            }
            cursor.close();
            // 解析
            startScanning();
        }
    }

    /**
     * 开启线程 解析图片
     */
    private void startScanning() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                Result result = scanningImage(mPhotoPath);
                if (result == null) {
                    // 子线程 需要Looper才能更新主视图
                    Looper.prepare();
                    Toast.makeText(getApplicationContext(), "图片格式有误",
                            Toast.LENGTH_LONG).show();
                    Looper.loop();
                } else {
                    Log.i("Result", "result : " + result.toString());
                    // 显示结果
                    showResult(result, null);
                }
            }
        }).start();
    }

    /**
     * 解析二维码图片 or 条形码图片
     *
     * @param path
     * @return
     */
    protected Result scanningImage(String path) {
        Result result;
        if (TextUtils.isEmpty(path)) {
            return null;
        }
        BitmapFactory.Options options = getSampleOptions(path);
        mScanBitmap = BitmapFactory.decodeFile(path, options);
        Result resultBarCode = null;
        try {
            // 扫描条形码
            resultBarCode = ScanHelper.scanningBarCode(mScanBitmap);
            Log.i("Content", "content : " + resultBarCode.getText());
        } catch (NotFoundException e1) {
            e1.printStackTrace();
            Log.e("NotFoundException", "The error : " + e1);
        }
        Result resultQRCode = null;
        try {
            // 扫描二维码
            resultQRCode = ScanHelper.scanningQRCode(mScanBitmap);
        } catch (NotFoundException e) {
            e.printStackTrace();
        } catch (ChecksumException e) {
            e.printStackTrace();
        } catch (FormatException e) {
            e.printStackTrace();
        }
        // 返回不为空的结果
        if (resultBarCode != null) {
            result = resultBarCode;
        } else if (resultQRCode != null) {
            result = resultQRCode;
        } else {
            result = null;
        }
        return result;
    }

    /**
     * 获得缩小比例的 options
     *
     * @param path 文件路径
     * @return
     */
    private BitmapFactory.Options getSampleOptions(String path) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        mScanBitmap = BitmapFactory.decodeFile(path, options);
        options.inJustDecodeBounds = false;
        int sampleSize = (int) (options.outHeight / (float) 200);
        if (sampleSize <= 0) {
            sampleSize = 1;
        }
        options.inSampleSize = sampleSize;
        return options;
    }

}
