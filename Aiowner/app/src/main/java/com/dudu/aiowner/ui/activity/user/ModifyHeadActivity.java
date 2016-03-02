package com.dudu.aiowner.ui.activity.user;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.dudu.aiowner.R;
<<<<<<< HEAD
<<<<<<< HEAD
import com.dudu.aiowner.databinding.ModifyHeadDataBinding;
=======
import com.dudu.aiowner.databinding.ModifyHeadBinding;
>>>>>>> 52ada4f... 增加防盗防劫-查看上传资料页面；个人中心-手势设置、密码设置相关页面
=======
import com.dudu.aiowner.databinding.ModifyHeadBinding;
>>>>>>> 579f22a0aafce27064d4cc20effa448db2a10642
import com.dudu.aiowner.ui.activity.user.observable.ModifyHeadObservable;
import com.dudu.aiowner.ui.base.BaseActivity;
import com.dudu.aiowner.utils.PhotoUtils.PhotoUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;

/**
 * Created by Administrator on 2016/2/2.
 */
public class ModifyHeadActivity extends BaseActivity {

<<<<<<< HEAD
<<<<<<< HEAD
    private ModifyHeadDataBinding modifyheadBinding;
=======
    private ModifyHeadBinding modifyheadBinding;
>>>>>>> 52ada4f... 增加防盗防劫-查看上传资料页面；个人中心-手势设置、密码设置相关页面
=======
    private ModifyHeadBinding modifyheadBinding;
>>>>>>> 579f22a0aafce27064d4cc20effa448db2a10642
    private ModifyHeadObservable modifyheadObservable;

    private PopupWindow mSetPhotoPop;
    private File mCurrentPhotoFile;
    private Bitmap imageBitmap;
    private static final int PHOTO_PICKED_WITH_DATA = 1881;
    private static final int CAMERA_WITH_DATA = 1882;
    private static final int CAMERA_CROP_RESULT = 1883;
    private static final int PHOTO_CROP_RESOULT = 1884;
    private static final int ICON_SIZE = 96;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        modifyheadObservable = new ModifyHeadObservable();

        modifyheadBinding = ModifyHeadBinding.bind(childView);
        modifyheadBinding.setModifyHeadPage(modifyheadObservable);
        modifyheadBinding.setTitle(observableFactory.getTitleObservable());

    }

    @Override
    protected View getChildView() {
        return LayoutInflater.from(this).inflate(R.layout.activity_modifyhead, null);
    }

    public void setNickName(View view){
        startActivity(new Intent(ModifyHeadActivity.this,SetNickNameActivity.class));
    }

    public void choseHeadImage(View view) {

        View mainView = LayoutInflater.from(this).inflate(R.layout.alert_setphoto_menu_layout, null);
        mSetPhotoPop = new PopupWindow(ModifyHeadActivity.this);
        mSetPhotoPop.setBackgroundDrawable(new BitmapDrawable());
        mSetPhotoPop.setFocusable(true);
        mSetPhotoPop.setTouchable(true);
        mSetPhotoPop.setOutsideTouchable(true);
        mSetPhotoPop.setContentView(mainView);
        mSetPhotoPop.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        mSetPhotoPop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        mSetPhotoPop.setAnimationStyle(R.style.bottomStyle);
        mSetPhotoPop.showAtLocation(modifyheadBinding.mainLayout, Gravity.BOTTOM, 0, 0);
        mSetPhotoPop.update();
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
            startActivityForResult(intent, PHOTO_PICKED_WITH_DATA);
        } catch (ActivityNotFoundException e) {

        }
    }

    /**
     * 取消
     */
    public void cancle(View view) {
        mSetPhotoPop.dismiss();
    }

    private Intent getTakePickIntent(File mCurrentPhotoFile) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE, null);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mCurrentPhotoFile));
        return intent;
    }

    @Override
    protected void onResume() {
        observableFactory.getTitleObservable().titleText.set("");
        observableFactory.getTitleObservable().userIcon.set(false);
        observableFactory.getTitleObservable().userTitleLogo.set(true);
        observableFactory.getTitleObservable().hasBackGround.set(true);
        observableFactory.getTitleObservable().hasUserBackButton.set(true);
        observableFactory.getCommonObservable().hasBottomIcon.set(false);
        observableFactory.getCommonObservable().hasUserBackground.set(true);
        super.onResume();
    }

    /**
     * 获取调用相册的Intent
     */
    public static Intent getPhotoPickIntent() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
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
        intent.putExtra("return-data", true);
        startActivityForResult(intent, PHOTO_CROP_RESOULT);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            switch (requestCode) {
                case PHOTO_PICKED_WITH_DATA:
                    // 相册选择图片后裁剪图片
                    startPhotoZoom(data.getData());
                    break;
                case PHOTO_CROP_RESOULT:
                    Bundle extras = data.getExtras();
                    if (extras != null) {
                        imageBitmap = extras.getParcelable("data");
                        //imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        modifyheadBinding.mainShowImage.setImageBitmap(imageBitmap);
                    }
                    break;
                case CAMERA_WITH_DATA:
                    // 相机拍照后裁剪图片
                    doCropPhoto(mCurrentPhotoFile);
                    break;
                case CAMERA_CROP_RESULT:
                    imageBitmap = data.getParcelableExtra("data");
                    // imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    modifyheadBinding.mainShowImage.setImageBitmap(imageBitmap);
                    break;
            }
        }
    }
}
