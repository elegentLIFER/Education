package com.sxmh.wt.education.activity.question_lib;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sxmh.wt.education.R;
import com.sxmh.wt.education.base.BaseActivity;
import com.sxmh.wt.education.model.TransBean;
import com.sxmh.wt.education.model.response.questionlib.QuestionIdsResponse;
import com.sxmh.wt.education.model.response.questionlib.QuestionInfoResponse;
import com.sxmh.wt.education.util.Constant;
import com.sxmh.wt.education.util.Net;
import com.sxmh.wt.education.util.ToastUtil;
import com.sxmh.wt.education.view.QuestionView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import butterknife.InjectView;
import butterknife.OnClick;

public class QuesAnalyseActivity extends BaseActivity {
    private static final String TAG = "ExerciseActivity";
    @InjectView(R.id.iv_back)
    ImageView ivBack;
    @InjectView(R.id.tv_name)
    TextView tvTitle;
    @InjectView(R.id.question_view)
    QuestionView questionView;
    @InjectView(R.id.tv_current_ques)
    TextView tvCurrentQues;
    @InjectView(R.id.tv_sum_num)
    TextView tvSumNum;
    @InjectView(R.id.tv_pre_question)
    TextView tvPreQuestion;
    @InjectView(R.id.tv_question_card)
    TextView tvQuestionCard;
    @InjectView(R.id.tv_next_question)
    TextView tvNextQuestion;
    @InjectView(R.id.ll_bottom)
    LinearLayout llBottom;

    public static final String SPLIT_IDS = "splitIds";
    public static final String REST_TIME = "rest_time";
    @InjectView(R.id.see_video)
    TextView seeVideo;
    @InjectView(R.id.see_doc)
    TextView seeDoc;
    @InjectView(R.id.see_gram)
    TextView seeGram;
    @InjectView(R.id.iv_collect)
    ImageView ivCollect;
    @InjectView(R.id.tv_line)
    TextView tvLine;

    private ArrayList<TransBean> transBeanList;

    private QuestionInfoResponse infoResponse;
    private QuestionIdsResponse idsResponse;

    private String paperListId;
    private String id;

    private boolean click;
    private int currentPosition;
    private String flagOprate;

    private boolean isCollected;

    @Override
    protected int initLayoutId() {
        return R.layout.activity_wrongs_analyse;
    }

    @Override
    protected void initData() {
        transBeanList = new ArrayList<>();

        getIntentData();
        tvCurrentQues.setText(currentPosition + 1 + "");
        questionView.showAnalyse(true);

        if (TextUtils.isEmpty(id)) {
            net.getQuestionInfo(paperListId, true);
            ivCollect.setVisibility(View.VISIBLE);
            tvCurrentQues.setVisibility(View.GONE);
            tvSumNum.setVisibility(View.GONE);
            tvLine.setVisibility(View.GONE);
            return;
        }
        net.getPaperQuestion(id, paperListId, flagOprate);
    }

    private void getIntentData() {
        Intent intent = getIntent();
        paperListId = intent.getStringExtra(PaperListActivity.PAPER_LIST_ID);
        id = intent.getStringExtra(PaperListActivity.PAPER_CATALOG_LIST_BEAN);
        flagOprate = intent.getStringExtra(PaperListActivity.FLAG_WHICH_OPERATE);
        currentPosition = intent.getIntExtra(TestResultActivity.POSITION, 0);
    }

    @Override
    public void updateContent(int request, Object content) {
        switch (request) {
            case Net.REQUEST_QUESTION_IDS:
                idsResponse = (QuestionIdsResponse) content;
                transBeanList.clear();

                String beforeQueidString = idsResponse.getBeforeQueids();
                String idString = idsResponse.getIdString();
                if (TextUtils.isEmpty(idString)) return;

                String[] splitIds = idString.split(Constant.SEPARATER_COMMA);

                if (TextUtils.isEmpty(beforeQueidString)) {
                    int len = splitIds.length;
                    for (int i = 0; i < len; i++) {
                        String[] split = splitIds[i].split(Constant.SEPARATER_ID);

                        TransBean transBean = new TransBean();
                        transBean.setId(split[0]);
                        transBean.setQuesType(split[1]);
                        transBean.setHasDone(true);
                        transBean.setCorrect(false);
                        transBeanList.add(transBean);
                    }
                } else {
                    String[] beforeQueids = beforeQueidString.split(Constant.SEPARATER_COMMA);

                    int len = splitIds.length;
                    for (int i = 0; i < len; i++) {
                        String[] split = splitIds[i].split(Constant.SEPARATER_ID);

                        TransBean transBean = new TransBean();
                        transBean.setId(split[0]);
                        transBean.setQuesType(split[1]);

                        int beforeIdsLen = beforeQueids.length;
                        for (int j = 0; j < beforeIdsLen; j++) {
                            String beforeQueid = beforeQueids[j];
                            if (beforeQueid.contains(split[0])) {
                                transBean.setHasDone(true);
                                String[] split1 = beforeQueid.split(Constant.SEPARATER_ID);
                                String[] split2 = split1[1].split(Constant.SEPARATER_STAR);
                                transBean.setCorrect("0".equals(split2[0]));
                                transBean.setScore(split2[1]);
                            }
                        }
                        transBeanList.add(transBean);
                    }
                }

                net.getQuestionInfo(transBeanList.get(currentPosition).getId(), true);
                tvSumNum.setText(idsResponse.getQuestionSum() + "");

                if (click) {
                    Intent questionCardActIntent = new Intent(this, QuestionCardActivity.class);
                    questionCardActIntent.putParcelableArrayListExtra(SPLIT_IDS, transBeanList);
                    questionCardActIntent.putExtra(QuestionCardActivity.SHOW_RIGHT_OR_WRONG, true);
                    startActivityForResult(questionCardActIntent, 1);
                }
                break;
            case Net.REQUEST_QUESTION_INFO:
                infoResponse = (QuestionInfoResponse) content;
                questionView.setContent(infoResponse);
                setCollected(infoResponse.isIsCollect());

                String video = infoResponse.getVideo();
                String image = infoResponse.getImage();
                String document = infoResponse.getDocument();
                seeVideo.setVisibility(TextUtils.isEmpty(video) ? View.INVISIBLE : View.VISIBLE);
                seeVideo.setOnClickListener(v -> playVideo(video));
                seeGram.setVisibility(TextUtils.isEmpty(image) ? View.INVISIBLE : View.VISIBLE);
                seeGram.setOnClickListener(v -> showGram(image));
                seeDoc.setVisibility(TextUtils.isEmpty(document) ? View.INVISIBLE : View.VISIBLE);
                seeDoc.setOnClickListener(v -> showDoc(document));
                break;
            case Net.REQUEST_COLLECT:
                setCollected((boolean) content);
                break;
            case Net.REQUEST_CANCEL_COLLECT:
                setCollected(!(boolean) content);
                break;
        }
    }

    private void showGram(String image) {
        ImageView im = new ImageView(this);
        im.setBackgroundResource(R.color.transparent);
        Glide.with(this).load(image).into(im);
        PopupWindow popupWindow = new PopupWindow();
        popupWindow.setContentView(im);
        im.setBackgroundColor(Color.BLACK);
        popupWindow.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(LinearLayout.LayoutParams.MATCH_PARENT);
        popupWindow.showAtLocation(getWindow().getDecorView(), Gravity.CENTER, 0, 0);
        popupWindow.setFocusable(false);
        im.setOnClickListener(v1 -> popupWindow.dismiss());
    }

    private void playVideo(String video) {
        String extension = MimeTypeMap.getFileExtensionFromUrl(video);
        String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        Intent mediaIntent = new Intent(Intent.ACTION_VIEW);
        mediaIntent.setDataAndType(Uri.parse(video), mimeType);
        startActivity(mediaIntent);
    }

    private void showDoc(String url) {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show(); //截取最后14位 作为文件名
        String s = url.substring(url.length() - 14); //文件存储
        file1 = new File(Environment.getExternalStorageDirectory(), getFileName(s));
        new Thread() {
            public void run() {
                File haha = new File(file1.getAbsolutePath()); //判断是否有此文件
                if (haha.exists()) { //有缓存文件,拿到路径 直接打开
                    Message msg = Message.obtain();
                    msg.obj = haha;
                    msg.what = DOWNLOAD_SUCCESS;
                    handler.sendMessage(msg);
                    mProgressDialog.dismiss();
                    return;
                } //              本地没有此文件 则从网上下载打开
                File downloadfile = downLoad(url, file1.getAbsolutePath(), mProgressDialog); //
                Message msg = Message.obtain();
                if (downloadfile != null) { // 下载成功,安装....
                    msg.obj = downloadfile;
                    msg.what = DOWNLOAD_SUCCESS;
                } else { // 提示用户下载失败.
                    msg.what = DOWNLOAD_ERROR;
                }
                handler.sendMessage(msg);
                mProgressDialog.dismiss();
            }

            ;
        }.start();
    }


    private void setCollected(boolean collected) {
        isCollected = collected;
        ivCollect.setImageResource(getCollectImgId());
    }

    private int getCollectImgId() {
        return isCollected ? R.drawable.icon_collection_blue : R.drawable.icon_collection;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == QuestionCardActivity.REQUEST_CODE_POSITION) {
            currentPosition = data.getIntExtra(QuestionCardActivity.POSITION, 0) - 1;
            net.getQuestionInfo(transBeanList.get(currentPosition).getId(), true);
            tvCurrentQues.setText(currentPosition + 1 + "");
        }
    }

    @OnClick({R.id.tv_pre_question, R.id.tv_question_card, R.id.tv_next_question, R.id.iv_back, R.id.iv_collect})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_pre_question:
                if (currentPosition > 0) {
                    currentPosition--;
                    net.getQuestionInfo(transBeanList.get(currentPosition).getId(), true);
                    tvCurrentQues.setText(currentPosition + 1 + "");
                } else {
                    ToastUtil.newToast(this, getString(R.string.has_the_first));
                }
                break;
            case R.id.tv_question_card:
                click = true;
                if (Constant.FLAG_ALL_ANALYSE.equals(flagOprate))
                    net.getPaperQuestion(id, paperListId, Constant.FLAG_DO_CONTINUE);
                else
                    net.getPaperQuestion(id, paperListId, Constant.FLAG_SEE_WRONGS);
                break;
            case R.id.tv_next_question:
                if (currentPosition < transBeanList.size() - 1) {
                    currentPosition++;
                    net.getQuestionInfo(transBeanList.get(currentPosition).getId(), true);
                    tvCurrentQues.setText(currentPosition + 1 + "");
                } else {
                    ToastUtil.newToast(this, getString(R.string.has_the_last));
                }
                break;
            case R.id.iv_collect:
                String quesId = infoResponse.getId();
                if (!isCollected)
                    net.doCollect(quesId, Net.COLLECT_FLAG_EXECISE);
                else
                    net.doCancelCollect(quesId, Net.COLLECT_FLAG_EXECISE);
                break;

        }
    }

    private ProgressDialog mProgressDialog; // 下载失败
    public static final int DOWNLOAD_ERROR = 2; // 下载成功
    public static final int DOWNLOAD_SUCCESS = 1;
    private File file1;
    /**
     * 下载完成后  直接打开文件
     */
    Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case DOWNLOAD_SUCCESS:
                    File file = (File) msg.obj;
                    Intent intent = new Intent("android.intent.action.VIEW");
                    intent.addCategory("android.intent.category.DEFAULT");
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setDataAndType(Uri.fromFile(file), "application/pdf");
                    //              startActivity(intent);
                    startActivity(Intent.createChooser(intent, "标题"));
                    break;
                case DOWNLOAD_ERROR:
                    break;
            }
        }
    }; /**
     * 传入文件 url  文件路径  和 弹出的dialog  进行 下载文档
     */
    /**
     * 传入文件 url  文件路径  和 弹出的dialog  进行 下载文档
     */
    public static File downLoad(String serverpath, String savedfilepath, ProgressDialog pd) {
        try {
            URL url = new URL(serverpath);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            if (conn.getResponseCode() == 200) {
                int max = conn.getContentLength();
                pd.setMax(max);
                InputStream is = conn.getInputStream();
                File file = new File(savedfilepath);
                FileOutputStream fos = new FileOutputStream(file);
                int len = 0;
                byte[] buffer = new byte[1024];
                int total = 0;
                while ((len = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, len);
                    total += len;
                    pd.setProgress(total);
                }
                fos.flush();
                fos.close();
                is.close();
                return file;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getFileName(String serverurl) {
        return serverurl.substring(serverurl.lastIndexOf("/") + 1);
    }
}
