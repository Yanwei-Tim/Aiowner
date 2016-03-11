package com.dudu.aiowner.ui.activity.preventTheft;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.dudu.aiowner.R;
import com.dudu.aiowner.databinding.ActivityOwnersCredentialsUploadBinding;
import com.dudu.aiowner.ui.activity.user.UserInfoActivity;
import com.dudu.aiowner.ui.base.BaseActivity;
import com.dudu.aiowner.utils.PhotoUtils.PhotoUtil;
import com.dudu.workflow.common.RequestFactory;
import com.dudu.workflow.guard.GuardRequest;

import net.bytebuddy.jar.asm.Handle;

import java.io.ByteArrayOutputStream;
import java.io.File;

/**
 * Created by sunny_zhang on 2016/2/2.
 */
public class OwnersCredentialsUploadActivity extends BaseActivity {

    private ActivityOwnersCredentialsUploadBinding ownersCredentialsUploadBinding;

    private PopupWindow mSetPhotoPop;

    private File mCurrentPhotoFile;

    private Bitmap imageBitmap;

    private static final int PHOTO_PICKED_WITH_DATA = 1881;
    private static final int CAMERA_WITH_DATA = 1882;
    private static final int CAMERA_CROP_RESULT = 1883;
    private static final int PHOTO_CROP_RESOULT = 1884;
    private static final int ICON_SIZE = 96;

    private static String clickType = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ownersCredentialsUploadBinding = ActivityOwnersCredentialsUploadBinding.bind(childView);
    }

    @Override
    protected View getChildView() {

        return LayoutInflater.from(OwnersCredentialsUploadActivity.this).inflate(R.layout.activity_owners_credentials_upload, null);
    }

    public void userInfo(View view) {
        startActivity(new Intent(OwnersCredentialsUploadActivity.this, UserInfoActivity.class));
    }

    @Override
    protected void onResume() {
        // 设置背景变亮
        windowsBackground(1.0f);

        observableFactory.getTitleObservable().titleText.set("车辆防盗");
        observableFactory.getTitleObservable().userIcon.set(true);
        observableFactory.getCommonObservable().hasBottomIcon.set(false);
        super.onResume();
    }

    public void startOwnersCredentialsUploadOk(View view) {
        RequestFactory.getGuardRequest().getTheftLicenseResult(new GuardRequest.TheftLicenceCallBack() {
            @Override
            public void LicenceSucceed(boolean succeed) {
                Log.d("LicenceSucceed", "认证成功！");
            }

            @Override
            public void LicenceError(String error) {
                Log.d("LicenceError", "认证失败！");
            }
        });

    }

    public void chooseDrivingLicense(View view) {

        Log.d("upload", "上传驾驶证图片");

        //显示popWindow
        showPhotoPop("driving");

        // 设置背景颜色变暗
        windowsBackground(.5f);

//        RequestFactory.getGuardRequest().getTheftUploadResult(new GuardRequest.UploadLicenceCallBack() {
//            @Override
//            public void uploadSucceed(boolean succeed) {
//                Log.d("uploadSucceed", "上传成功！");
//                startActivity(new Intent(OwnersCredentialsUploadActivity.this, OwnersCredentialsUploadOkActivity.class));
//            }
//
//            @Override
//            public void uploadError(String error) {
//                Log.d("uploadError", "上传失败！");
//            }
//        });
    }

    public void chooseTravelLicense(View view) {

        Log.d("upload", "上传行驶证图片");

        //显示popWindow
        showPhotoPop("trave");

        // 设置背景颜色变暗
        windowsBackground(.5f);

    }

    // 设置窗口背景颜色
    private void windowsBackground(float alpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = alpha;
        getWindow().setAttributes(lp);
    }


    private void showPhotoPop(String type) {
        View mainView = LayoutInflater.from(this).inflate(R.layout.alert_setphoto_menu_layout, null);
        mSetPhotoPop = new PopupWindow(OwnersCredentialsUploadActivity.this);
        mSetPhotoPop.setBackgroundDrawable(new BitmapDrawable());
        mSetPhotoPop.setFocusable(true);
        mSetPhotoPop.setTouchable(true);
        mSetPhotoPop.setOutsideTouchable(true);
        mSetPhotoPop.setContentView(mainView);
        mSetPhotoPop.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        mSetPhotoPop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        mSetPhotoPop.setAnimationStyle(R.style.bottomStyle);
        mSetPhotoPop.showAtLocation(ownersCredentialsUploadBinding.uploadlicenseRl, Gravity.BOTTOM, 0, 0);
        mSetPhotoPop.update();
        clickType = type;
    }

    /**
     * 拍照获取
     */
    public void doTakePhoto(View view) {
        mSetPhotoPop.dismiss();
        try {
            // Launch camera to take photo for selected contact
            File file = new File(Environment.getExternalStorageDirectory() + "/DCIM/Photo");
            if (!file.exists()) {
                file.mkdirs();
            }
            mCurrentPhotoFile = new File(file, PhotoUtil.getRandomFileName());
            final Intent intent = getTakePickIntent(mCurrentPhotoFile);
            intent.putExtra("type", clickType);
            startActivityForResult(intent, CAMERA_WITH_DATA);
        } catch (ActivityNotFoundException e) {

        }
    }

    /**
     * 从手机相册选择图片
     */
    public void doPickPhotoFromGallery(View view) {
        mSetPhotoPop.dismiss();
        try {
            // Launch picker to choose photo for selected contact
            final Intent intent = getPhotoPickIntent();
            intent.putExtra("type", clickType);
            startActivityForResult(intent, PHOTO_PICKED_WITH_DATA);
        } catch (ActivityNotFoundException e) {

        }
    }

    /**
     * 取消
     */
    public void cancle(View view) {
        mSetPhotoPop.dismiss();
        // 设置背景颜色变亮
        windowsBackground(1.0f);
    }


    private Intent getTakePickIntent(File mCurrentPhotoFile) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE, null);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mCurrentPhotoFile));
//        intent.putExtra("type", clickType);
        return intent;
    }

    /**
     * 获取调用相册的Intent
     */
    public static Intent getPhotoPickIntent() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.putExtra("type", clickType);
        return intent;
    }

    /**
     * 相机剪切图片
     */
    protected void doCropPhoto(File f) {
        try {
            // Add the image to the media store
            MediaScannerConnection.scanFile(this, new String[]{
                    f.getAbsolutePath()
            }, new String[]{
                    null
            }, null);

            // Launch gallery to crop the photo
            final Intent intent = getCropImageIntent(Uri.fromFile(f));
            intent.putExtra("type", clickType);
            startActivityForResult(intent, CAMERA_CROP_RESULT);
        } catch (Exception e) {
        }
    }

    /**
     * 获取系统剪裁图片的Intent.
     */
    public static Intent getCropImageIntent(Uri photoUri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(photoUri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", ICON_SIZE);
        intent.putExtra("outputY", ICON_SIZE);
        intent.putExtra("type", clickType);
        intent.putExtra("return-data", true);
        return intent;
    }

    /**
     * 相册裁剪图片
     *
     * @param uri
     */
    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");//调用Android系统自带的一个图片剪裁页面,
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");//进行修剪
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", ICON_SIZE);
        intent.putExtra("outputY", ICON_SIZE);
        intent.putExtra("type", clickType);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, PHOTO_CROP_RESOULT);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            Bundle extras;
            String type = null;
            switch (requestCode) {
                case PHOTO_PICKED_WITH_DATA:
                    // 相册选择图片后裁剪图片
                    startPhotoZoom(data.getData());
                    break;
                case PHOTO_CROP_RESOULT:
                    extras = data.getExtras();
                    if (extras != null) {
                        imageBitmap = extras.getParcelable("data");
                        //imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);

                        type = extras.getString("type");
//                        String type = data.getStringExtra("type");

                        System.out.println("--------PHOTO_CROP_RESOULT--------" + type);

                        if (type == "driving") {
                            // 设置背景变亮
                            windowsBackground(1.0f);
                            ownersCredentialsUploadBinding.drivingLicenseIv.setImageBitmap(imageBitmap);
                            clickType = "";
                            return;
                        }
                        if (type == "trave") {
                            // 设置背景变亮
                            windowsBackground(1.0f);
                            ownersCredentialsUploadBinding.travelLicenseIv.setImageBitmap(imageBitmap);
                            clickType = "";
                            return;
                        }

                    }
                    break;
                case CAMERA_WITH_DATA:
                    // 相机拍照后裁剪图片
                    doCropPhoto(mCurrentPhotoFile);
                    break;
                case CAMERA_CROP_RESULT:
                    imageBitmap = data.getParcelableExtra("data");
                    // imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
//                        String type = data.getStringExtra("type");
                    System.out.println("---------CAMERA_CROP_RESULT-------" + type);
                    if (type == "driving") {
                        // 设置背景变亮
                        windowsBackground(1.0f);
                        ownersCredentialsUploadBinding.drivingLicenseIv.setImageBitmap(imageBitmap);
                        clickType = "";
                        return;
                    }
                    if (type == "trave") {
                        // 设置背景变亮
                        windowsBackground(1.0f);
                        ownersCredentialsUploadBinding.travelLicenseIv.setImageBitmap(imageBitmap);
                        clickType = "";
                        return;
                    }
                    break;
            }
        }
    }
}
