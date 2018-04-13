package cn.mister.mjbook.tallylist;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.mister.mjbook.R;

public class TallyViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.layout_basic_info)
    RelativeLayout basicInfoLayout;

    @BindView(R.id.layout_note)
    LinearLayout noteLayout;

    @BindView(R.id.tv_time)
    TextView timeView;

    @BindView(R.id.tv_amount)
    TextView amountView;

    @BindView(R.id.tv_tags)
    TextView tagsView;

    @BindView(R.id.tv_note)
    TextView noteView;

    @BindView(R.id.collapsible_item)
    LinearLayout mainLayout;

    public TallyViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
