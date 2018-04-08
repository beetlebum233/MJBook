package cn.mister.mjbook.tallylist;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.mister.mjbook.R;

public class TallyViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.layout_basic_info)
    RelativeLayout basicInfoLayout;

    @BindView(R.id.note)
    LinearLayout noteView;



    public TallyViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
