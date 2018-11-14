package com.sxmh.wt.education.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.sxmh.wt.education.R;

public class RankView extends AppCompatImageView {
    private static final String TAG = "RankView";
    public RankView(Context context) {
        super(context);
    }

    public RankView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RankView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setRank(int rank) {
        switch (rank) {
            case 0:
                setImageResource(R.drawable.rank0);
                break;
            case 1:
                setImageResource(R.drawable.rank1);
                break;
            case 2:
                setImageResource(R.drawable.rank2);
                break;
            case 3:
                setImageResource(R.drawable.rank3);
                break;
            case 4:
                setImageResource(R.drawable.rank4);
                break;
            case 5:
                setImageResource(R.drawable.rank5);
                break;
        }
    }
}
