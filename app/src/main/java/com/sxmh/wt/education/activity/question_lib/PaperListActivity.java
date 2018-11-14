package com.sxmh.wt.education.activity.question_lib;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.sxmh.wt.education.R;
import com.sxmh.wt.education.activity.accout.LoginActivity;
import com.sxmh.wt.education.activity.accout.MyCollectionActivity;
import com.sxmh.wt.education.adapter.questionlib.RvPaperListItemAdapter;
import com.sxmh.wt.education.adapter.questionlib.RvQuestionLibItemItemAdapter;
import com.sxmh.wt.education.base.BaseActivity;
import com.sxmh.wt.education.model.User;
import com.sxmh.wt.education.model.response.PaperCatalogResponse;
import com.sxmh.wt.education.util.Constant;
import com.sxmh.wt.education.util.Net;
import com.sxmh.wt.education.view.TitleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

public class PaperListActivity extends BaseActivity implements TitleView.OnTitleViewClickListener, RvPaperListItemAdapter.OnItemClickListener {
    private static final String TAG = "PaperListActivity";
    @InjectView(R.id.title_view)
    TitleView titleView;
    @InjectView(R.id.rv_paper_list)
    RecyclerView rvPaperList;

    public static final String PAPER_LIST_ID = "paper_list_id";
    public static final String PAPER_LIST_NAME = "paper_list_name";
    public static final String FLAG_WHICH_OPERATE = "flag_which_operate";
    public static final String PAPER_CATALOG_LIST_BEAN = "paper_catalog_list_bean";

    private RvPaperListItemAdapter itemAdapter;
    private List<PaperCatalogResponse.PaperCatalogListBean> beanList;
    private String paperListName;
    private String paperListId;
    private String paperTypeName;

    private String whichOperate;
    private int clickedPosition;

    @Override
    protected int initLayoutId() {
        return R.layout.activity_paper_list;
    }

    @Override
    protected void initData() {
        titleView.setOnTitleViewClickListener(this);
        getIntentData();
        titleView.setTvTitle(paperListName + "." + paperTypeName);

        beanList = new ArrayList<>();
        itemAdapter = new RvPaperListItemAdapter(this, beanList);
        rvPaperList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvPaperList.setAdapter(itemAdapter);
        itemAdapter.setItemClickListener(this);
    }

    protected void getIntentData() {
        Intent intent = getIntent();
        paperTypeName = intent.getStringExtra(RvQuestionLibItemItemAdapter.PAPER_TYPE_NAME);
        paperListName = intent.getStringExtra(PAPER_LIST_NAME);
        paperListId = intent.getStringExtra(PAPER_LIST_ID);
    }

    @Override
    protected void onResume() {
        super.onResume();
        net.getPaperCatalog(paperListId, User.getInstance().getMemberId());
    }

    @Override
    public void updateContent(int request, Object content) {
        if (request == Net.REQUEST_SPEC_TYPE_PAPER_CATALOG) {
            List<PaperCatalogResponse.PaperCatalogListBean> list = (List<PaperCatalogResponse.PaperCatalogListBean>) content;
            beanList.clear();
            beanList.addAll(list);
            itemAdapter.notifyDataSetChanged();
        } else if (request == Net.REQUEST_IS_QUES_LOG) {
            boolean isQuesLog = (boolean) content;
            showBottomDialog(isQuesLog);
        } else if (request == Net.REQUEST_DO_QUES_POWER) {
            boolean hasPower = (boolean) content;
            if (hasPower) {
                net.isQuestionLog(paperListId, beanList.get(clickedPosition).getId());
            }
        }
    }

    private void showBottomDialog(boolean isQuesLog) {
        final Dialog dialog = new Dialog(this, R.style.dialog);
        View view = View.inflate(this, R.layout.dialog_bottom_paper, null);
        TextView tvBeginDo = view.findViewById(R.id.tv_begin_do);
        TextView tvSeeAnalysis = view.findViewById(R.id.tv_see_analysis);
        TextView tvMyCollection = view.findViewById(R.id.tv_my_collection);
        TextView tvDoAgain = view.findViewById(R.id.tv_do_again);
        TextView tvMyWrongs = view.findViewById(R.id.tv_my_wrongs);
        TextView tvDoContinue = view.findViewById(R.id.tv_do_continue);

        if (!isQuesLog) {
            tvDoAgain.setVisibility(View.GONE);
            tvMyWrongs.setVisibility(View.GONE);
            tvDoContinue.setVisibility(View.GONE);

            tvBeginDo.setOnClickListener(v -> {
                whichOperate = Constant.FLAG_BEGIN_DO;
                startExeciseActivity();
                dialog.dismiss();
            });
        } else {
            tvBeginDo.setVisibility(View.INVISIBLE);
            tvDoAgain.setOnClickListener(v -> {
                whichOperate = Constant.FLAG_DO_AGAIN;
                startExeciseActivity();
                dialog.dismiss();
            });
            tvMyWrongs.setOnClickListener(v -> {
                whichOperate = Constant.FLAG_SEE_WRONGS;
                startQuesAnalyseActivity();
                dialog.dismiss();
            });
            tvDoContinue.setOnClickListener(v -> {
                whichOperate = Constant.FLAG_DO_CONTINUE;
                startExeciseActivity();
                dialog.dismiss();
            });
        }

        tvSeeAnalysis.setOnClickListener(v -> {
            dialog.dismiss();
            whichOperate = Constant.FLAG_ALL_ANALYSE;
            startQuesAnalyseActivity();
        });
        tvMyCollection.setOnClickListener(v -> {
            startActivity(new Intent(PaperListActivity.this, MyCollectionActivity.class));
            dialog.dismiss();
        });
        dialog.setContentView(view);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.BOTTOM);
        dialog.show();
    }

    private void startQuesAnalyseActivity() {
        Intent intent = new Intent(PaperListActivity.this, QuesAnalyseActivity.class);
        PaperCatalogResponse.PaperCatalogListBean bean = beanList.get(clickedPosition);
        intent.putExtra(PAPER_CATALOG_LIST_BEAN, bean.getId());
        intent.putExtra(PAPER_LIST_ID, paperListId);
        intent.putExtra(PaperListActivity.FLAG_WHICH_OPERATE, whichOperate);
        intent.putExtra(TestResultActivity.POSITION, 0);
        startActivity(intent);
    }

    private void startExeciseActivity() {
        Intent intent = new Intent(PaperListActivity.this, ExerciseActivity.class);
        PaperCatalogResponse.PaperCatalogListBean bean = beanList.get(clickedPosition);
        intent.putExtra(PAPER_CATALOG_LIST_BEAN, bean);
        intent.putExtra(PAPER_LIST_ID, paperListId);
        intent.putExtra(FLAG_WHICH_OPERATE, whichOperate);
        startActivity(intent);
    }

    @Override
    public void onBackClick() {
        finish();
    }

    @Override
    public void onTitleRightClick() {
    }

    @Override
    public void onItemClick(int position) {
        clickedPosition = position;

        String memberId = User.getInstance().getMemberId();
        if (TextUtils.isEmpty(memberId)) startActivity(new Intent(this, LoginActivity.class));
        else net.doQuestionPower(memberId, paperListId, beanList.get(clickedPosition).getId());
    }
}
