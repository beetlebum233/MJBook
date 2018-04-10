package cn.mister.mjbook.taglist;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.mister.mjbook.R;

public class TallyTagViewHolder extends RecyclerView.ViewHolder {
    @BindView(android.R.id.text1)
    TextView textView;

    public TallyTagViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
