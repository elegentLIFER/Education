package com.sxmh.wt.education.activity.ask_answer;

import android.app.AlertDialog;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.sxmh.wt.education.MyApplication;
import com.sxmh.wt.education.R;
import com.sxmh.wt.education.base.BaseActivity;
import com.sxmh.wt.education.model.response.AllCourseClassResponse;
import com.sxmh.wt.education.model.response.ask_answer.NetProblemClassResponse;
import com.sxmh.wt.education.util.Constant;
import com.sxmh.wt.education.util.Net;
import com.sxmh.wt.education.util.ToastUtil;
import com.sxmh.wt.education.view.TitleView;

import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;

public class AskActivity extends BaseActivity implements TitleView.OnTitleViewClickListener, AdapterView.OnItemClickListener {
    @InjectView(R.id.title_view)
    TitleView titleView;
    @InjectView(R.id.tv_type)
    TextView tvType;
    @InjectView(R.id.et_title)
    EditText etTitle;
    @InjectView(R.id.et_content)
    EditText etContent;
    @InjectView(R.id.tv_commit)
    TextView tvCommit;

    private AlertDialog alertDialog;
    private NetProblemClassResponse.NetProblemClassListBean netProblemClassListBean;
    private String className;
    private List<NetProblemClassResponse.NetProblemClassListBean> netProblemClassList;

    @Override
    protected int initLayoutId() {
        return R.layout.activity_ask;
    }

    @Override
    protected void initData() {
        titleView.setOnTitleViewClickListener(this);

        Intent intent = getIntent();
        String classId = intent.getStringExtra(Constant.CLASS_ID);
        net.getNetProblemClass(classId);

        List<AllCourseClassResponse.CourseClassListBean> courseClassList = MyApplication.getCourseClassList();
        for (int i = 0; i < courseClassList.size(); i++) {
            AllCourseClassResponse.CourseClassListBean bean = courseClassList.get(i);
            if (classId.equals(bean.getId())) {
                this.className = bean.getCourseClassName();
            }
        }
    }

    @Override
    public void updateContent(int request, Object content) {
        if (request == Net.REQUEST_NET_PROBLEM) {
            NetProblemClassResponse response = (NetProblemClassResponse) content;
            netProblemClassList = response.getNetProblemClassList();
        }
    }

    @OnClick({R.id.tv_type, R.id.tv_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_type:
                typeSelect();
                break;
            case R.id.tv_commit:
                commitQuestion();
                break;
        }
    }

    private void commitQuestion() {
        String problemTitle = etTitle.getText().toString();
        String problemContent = etContent.getText().toString();
        if (TextUtils.isEmpty(problemTitle) || TextUtils.isEmpty(problemContent)) {
            ToastUtil.newToast(this, "问题标题和内容不能为空");
            return;
        }
        net.doNetProblem(netProblemClassListBean.getNetProblemClassId(), problemTitle, problemContent);
    }

    private void typeSelect() {
        if (alertDialog == null) {
            ListView listView = new ListView(this);
            String[] stringArray = new String[netProblemClassList.size()];
            for (int i = 0; i < netProblemClassList.size(); i++) {
                stringArray[i] = className + " . " + netProblemClassList.get(i).getNetProblemClassName();
            }
            listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, stringArray));
            alertDialog = new AlertDialog.Builder(this)
                    .setView(listView)
                    .setTitle("请选择问题类型")
                    .create();
            listView.setOnItemClickListener(this);
        }
        alertDialog.show();
    }

    @Override
    public void onBackClick() {
        finish();
    }

    @Override
    public void onTitleRightClick() {
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        netProblemClassListBean = netProblemClassList.get(position);
        tvType.setText(className + " . " + netProblemClassListBean.getNetProblemClassName());
        if (alertDialog.isShowing()) alertDialog.dismiss();
    }
}
