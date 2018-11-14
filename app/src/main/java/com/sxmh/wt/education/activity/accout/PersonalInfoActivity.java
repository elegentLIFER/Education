package com.sxmh.wt.education.activity.accout;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.sxmh.wt.education.MyApplication;
import com.sxmh.wt.education.R;
import com.sxmh.wt.education.activity.accout.ForgetPswActivity;
import com.sxmh.wt.education.base.BaseActivity;
import com.sxmh.wt.education.model.User;
import com.sxmh.wt.education.model.response.FileUploadResponse;
import com.sxmh.wt.education.model.response.PersonInfoChangeResponse;
import com.sxmh.wt.education.model.response.PersonInfoResponse;
import com.sxmh.wt.education.util.BitMapUtils;
import com.sxmh.wt.education.util.Constant;
import com.sxmh.wt.education.util.FileUtils;
import com.sxmh.wt.education.util.NUtil;
import com.sxmh.wt.education.util.Net;
import com.sxmh.wt.education.util.PhotoDialog;
import com.sxmh.wt.education.util.ToastUtil;
import com.sxmh.wt.education.view.CircleImageView;
import com.sxmh.wt.education.view.TitleView;

import java.io.File;
import java.io.IOException;

import butterknife.InjectView;
import butterknife.OnClick;

public class PersonalInfoActivity extends BaseActivity implements TitleView.OnTitleViewClickListener {
    private static final String TAG = "PersonalInfoActivity";
    @InjectView(R.id.title_view)
    TitleView titleView;
    @InjectView(R.id.ll_head)
    LinearLayout llHead;
    @InjectView(R.id.iv_header)
    CircleImageView ivHeader;
    @InjectView(R.id.tv_name)
    TextView tvName;
    @InjectView(R.id.et_email)
    EditText tvEmail;
    @InjectView(R.id.tv_phone)
    TextView tvPhone;
    @InjectView(R.id.ll_change_psw)
    LinearLayout llChangePsw;

    private static final int ALBUM = 919;
    private static final int TAKE_PHOTO = 918;
    private File outputImage;

    @Override
    protected int initLayoutId() {
        return R.layout.activity_personal_info;
    }

    @Override
    protected void initData() {
        titleView.setOnTitleViewClickListener(this);

        net.getMemberInfo();
    }

    @Override
    public void onBackClick() {
        finish();
    }

    @Override
    public void onTitleRightClick() {
        String email = tvEmail.getText().toString();
        if (TextUtils.isEmpty(email)) {
            ToastUtil.newToast(this, "未填写要修改的信息");
            return;
        }
        net.changeEmail(email);
    }

    @OnClick({R.id.ll_head, R.id.ll_change_psw})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_head:
                new PhotoDialog(this, R.style.defaultDialogStyle, new PhotoDialog.OnClickListener() {
                    @Override
                    public void onPhotoClick() {
                        fromPhoto(TAKE_PHOTO);
                    }

                    @Override
                    public void onGalleryClick() {
                        fromAlbum(ALBUM);
                    }
                }).show();
                break;
            case R.id.ll_change_psw:
                startActivity(new Intent(this, ForgetPswActivity.class));
                break;
        }
    }

    private void fromPhoto(int flag) {
        outputImage = new File(Constant.CACHE_PATH, FileUtils.generateImageName());
        try {
            if (outputImage.exists()) {
                outputImage.delete();
            }
            outputImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Uri imageUri;
        if (Build.VERSION.SDK_INT < 24) {
            imageUri = Uri.fromFile(outputImage);
        } else {
            imageUri = FileProvider.getUriForFile(MyApplication.getInstance(), "com.sxmh.wt.education", outputImage);
        }
        // 启动相机程序
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, flag);
    }

    private void fromAlbum(int flag) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        // 开启一个带有返回值的Activity，请求码为ALBUM
        startActivityForResult(intent, flag);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    String imgPath = outputImage.getAbsolutePath();
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 4;
                    Bitmap bitmap = BitmapFactory.decodeFile(imgPath, options);
                    NUtil.saveBitmapFile(bitmap, imgPath);
                    net.upLoadImage(imgPath);
                }
                break;
            case ALBUM:
                if (resultCode == RESULT_OK) {
                    // 从相册返回的数据
                    if (data != null) {
                        // 得到图片的全路径
                        Uri uri = data.getData();
                        String imagePath = BitMapUtils.getRealPathFromUri(this, uri);
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inSampleSize = 4;
                        Bitmap bitmap = BitmapFactory.decodeFile(imagePath, options);
                        String path = Constant.CACHE_PATH + "/" + imagePath.substring(imagePath.lastIndexOf("/"));
                        NUtil.saveBitmapFile(bitmap, path);
                        net.upLoadImage(path);
                    }
                }
                break;
        }
    }

    @Override
    public void updateContent(int request, Object content) {
        User user = User.getInstance();
        if (request == Net.REQUEST_PERSON_INFO) {
            PersonInfoResponse response = (PersonInfoResponse) content;
            User instance = user;
            instance.setState(response.getState());
            instance.setPhoneNum(response.getPhoneBind());
            instance.setPosition(response.getPosition());
            instance.setCompany(response.getCompany());
            instance.setEmailBind(response.getEmailBind());

            RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(R.drawable.touxaing).error(R.drawable.touxaing);
            Glide.with(this).load(instance.getPhoto()).apply(requestOptions).into(ivHeader);
            tvEmail.setText(instance.getEmailBind());
            tvPhone.setText(instance.getPhoneNum());
            tvName.setText(instance.getUserName());
        } else if (request == Net.REQUEST_UPLOAD_IMAGE) {
            FileUploadResponse response = (FileUploadResponse) content;
            net.doAlterMemberInfo(response.getUrl());
            user.setPhoto(response.getUrl());
        } else if (request == Net.REQUEST_CHANGE_HEADER) {
            PersonInfoChangeResponse response = (PersonInfoChangeResponse) content;
            if (response.isResult()) {
                Glide.with(this).load(user.getPhoto()).into(ivHeader);
                ToastUtil.newToast(this, "头像更改成功");
            }
        }
    }
}
