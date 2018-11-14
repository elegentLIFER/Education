package com.sxmh.wt.education.activity.ask_answer;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.sxmh.wt.education.MyApplication;
import com.sxmh.wt.education.R;
import com.sxmh.wt.education.adapter.ask_answer.RvHistoryReplyAdapter;
import com.sxmh.wt.education.base.BaseActivity;
import com.sxmh.wt.education.model.response.FileUploadResponse;
import com.sxmh.wt.education.model.response.ask_answer.NetAnswerListResponse;
import com.sxmh.wt.education.model.response.ask_answer.NetProblemInfoResponse;
import com.sxmh.wt.education.util.BitMapUtils;
import com.sxmh.wt.education.util.Constant;
import com.sxmh.wt.education.util.FileUtils;
import com.sxmh.wt.education.util.NUtil;
import com.sxmh.wt.education.util.Net;
import com.sxmh.wt.education.util.PhotoDialog;
import com.sxmh.wt.education.util.ToastUtil;
import com.sxmh.wt.education.view.TitleView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import jp.wasabeef.richeditor.RichEditor;

import static cn.jpush.android.api.b.v;

public class QuestionContentActivity extends BaseActivity implements TitleView.OnTitleViewClickListener {
    @InjectView(R.id.title_view)
    TitleView titleView;
    @InjectView(R.id.tv_type)
    TextView tvType;
    @InjectView(R.id.tv_problem_title)
    TextView tvProblemTitle;
    @InjectView(R.id.tv_author)
    TextView tvAuthor;
    @InjectView(R.id.tv_teacher)
    TextView tvTime;
    @InjectView(R.id.webview_question)
    WebView webviewQuestion;
    @InjectView(R.id.iv_header)
    ImageView ivHeader;
    @InjectView(R.id.tv_commit_reply)
    TextView tvCommitReply;
    @InjectView(R.id.rv_reply_history)
    RecyclerView rvReplyHistory;
    @InjectView(R.id.et_content)
    RichEditor etContent;
    @InjectView(R.id.iv_add_pic)
    ImageView ivAddPic;

    private static final int ALBUM = 919;
    private static final int TAKE_PHOTO = 918;
    private File outputImage;

    private String netProblemClassId;
    private List<NetAnswerListResponse.NetAnswerListBean> answerBeanList;
    private RvHistoryReplyAdapter replyAdapter;

    @Override
    protected int initLayoutId() {
        return R.layout.activity_question_content;
    }

    @Override
    protected void initData() {
        titleView.setOnTitleViewClickListener(this);
        netProblemClassId = getIntent().getStringExtra(QuestionListActivity.NET_PROBLEM_CLASS_ID);

        net.getNetProblemInfo(netProblemClassId);
        net.getNetAnswerList(netProblemClassId);
        initAnswerList();

        richEditorInit();
    }

    private void richEditorInit() {
        etContent.setEditorHeight(250);
        etContent.setEditorFontSize(16);
        etContent.setEditorFontColor(Color.BLACK);
        etContent.setPadding(10, 10, 10, 10);
        etContent.setPlaceholder("在此输入回复内容（最多不超过600字）");
        etContent.setInputEnabled(true);
    }

    private void initAnswerList() {
        answerBeanList = new ArrayList<>();
        rvReplyHistory.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        replyAdapter = new RvHistoryReplyAdapter(this, answerBeanList);
        rvReplyHistory.setAdapter(replyAdapter);
    }

    @Override
    public void updateContent(int request, Object content) {
        if (request == Net.REQUEST_NET_PROBLEM_INFO) {
            NetProblemInfoResponse response = (NetProblemInfoResponse) content;
            tvProblemTitle.setText(response.getProblemTitle());
            tvAuthor.setText(response.getCreateName());
            tvTime.setText(response.getCreateDate());
            tvType.setText(response.getCourseClassName());
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.error(R.drawable.ask_answer_header).placeholder(R.drawable.ask_answer_header);
            Glide.with(this).applyDefaultRequestOptions(requestOptions).load(response.getPhoto()).into(ivHeader);
            webviewQuestion.loadDataWithBaseURL(null, response.getProblemContent(), "text/html", "UTF-8", null);
        } else if (request == Net.REQUEST_NET_ANSWER_LIST) {
            NetAnswerListResponse response = (NetAnswerListResponse) content;
            List<NetAnswerListResponse.NetAnswerListBean> netAnswerList = response.getNetAnswerList();
            answerBeanList.clear();
            answerBeanList.addAll(netAnswerList);
            replyAdapter.notifyDataSetChanged();
        } else if (request == Net.REQUEST_UPLOAD_IMAGE) {
            FileUploadResponse response = (FileUploadResponse) content;
            etContent.insertImage(response.getUrl(), "alt");
        }
    }

    @Override
    public void onBackClick() {
        finish();
    }

    @Override
    public void onTitleRightClick() {
    }

    @OnClick({R.id.tv_commit_reply, R.id.iv_add_pic})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_commit_reply:
                String html = etContent.getHtml();
                if (TextUtils.isEmpty(html)) ToastUtil.newToast(this, "请输入回复内容");
                net.doNetAnswer(netProblemClassId, html);
                break;
            case R.id.iv_add_pic:
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
        }
    }

    private void fromAlbum(int flag) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        // 开启一个带有返回值的Activity，请求码为ALBUM
        startActivityForResult(intent, flag);
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
}
