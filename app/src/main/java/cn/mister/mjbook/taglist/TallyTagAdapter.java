package cn.mister.mjbook.taglist;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import cn.mister.mjbook.R;
import cn.mister.mjbook.data.TallyTag;
import cn.mister.mjbook.tallylist.TallyViewHolder;

public class TallyTagAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<TallyTag> tags;
    private Context context;
    private TagListContract.TagListPresenter mPresenter;

    public TallyTagAdapter(List<TallyTag> tags, Context context, TagListContract.TagListPresenter presenter) {
        this.tags = tags;
        this.context = context;
        this.mPresenter = presenter;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.tag_item, parent, false);
        return new TallyTagViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        TallyTag tag = tags.get(position);
        TallyTagViewHolder viewHolder = (TallyTagViewHolder) holder;
        viewHolder.textView.setText(tag.getName());
        viewHolder.textView.setOnLongClickListener((listener) -> {
                final AlertDialog.Builder normalDialog =
                        new AlertDialog.Builder(context);
                normalDialog.setTitle("是否确认删除该标签？");
                normalDialog.setPositiveButton("确定",
                        (dialog, which) -> mPresenter.delete(tag.getId()));
                normalDialog.setNegativeButton("关闭",
                        (dialog, which) -> {
                        });
                normalDialog.show();
                return true;
            }
        );
    }

    @Override
    public int getItemCount() {
        return tags.size();
    }

    public void setData(List<TallyTag> data) {
        this.tags = data;
    }
}
