package cn.mister.mjbook.tallylist;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.AlertDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import cn.mister.mjbook.R;
import cn.mister.mjbook.data.Tally;
import cn.mister.mjbook.data.TallyTag;

public class TallyItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Tally> tallies;
    private Context context;
    private TallyListContract.TallyListPresenter mPresenter;

    public TallyItemAdapter(Context context, List<Tally> tallies, TallyListContract.TallyListPresenter mPresenter) {
        this.tallies = tallies;
        this.context = context;
        this.mPresenter = mPresenter;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.collapsible_item, parent, false);
        return new TallyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Tally tally = tallies.get(position);
        TallyViewHolder tallyHolder = (TallyViewHolder) holder;
        String amountStr = "";
        if (!tally.getIncome()) {
            amountStr += "-";
        }
        DecimalFormat df = new DecimalFormat("0.00");
        amountStr += df.format(tally.getAmount());
        tallyHolder.amountView.setText(amountStr);

        SimpleDateFormat dd = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = dd.format(tally.getCreatedTime());
        tallyHolder.timeView.setText(dateStr);

        tallyHolder.noteView.setText(tally.getNote());

        if (tally.getTags() != null && !tally.getTags().isEmpty()) {
            for (TallyTag tag : tally.getTags()) {
                tallyHolder.tagsView.setText(tag.getName());
            }
        }else{
            tallyHolder.tagsView.setText("无标签");
        }

        RelativeLayout basicInfoLayout = tallyHolder.basicInfoLayout;
        final LinearLayout noteView = tallyHolder.noteLayout;
        basicInfoLayout.setOnClickListener(new View.OnClickListener() {

            boolean isAnimating = false;

            @Override
            public void onClick(View v) {
                if (isAnimating) return;
                isAnimating = true;

                if (noteView.getVisibility() == View.GONE) {
                    animateOpen(noteView);
                } else {
                    animateClose(noteView);
                }
            }

            private void animateOpen(LinearLayout view) {
                view.setVisibility(View.VISIBLE);
                ValueAnimator animator = createDropAnimator(view, 0, 50);
                animator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        isAnimating = false;
                    }
                });
                animator.start();
            }

            private void animateClose(final LinearLayout view) {
                int origHeight = view.getHeight();
                ValueAnimator animator = createDropAnimator(view, origHeight, 0);
                animator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        view.setVisibility(View.GONE);
                        isAnimating = false;
                    }
                });
                animator.start();
            }

            private ValueAnimator createDropAnimator(final View view, int start, int end) {
                ValueAnimator animator = ValueAnimator.ofInt(start, end);
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        int value = (int) animation.getAnimatedValue();
                        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                        layoutParams.height = value;
                        view.setLayoutParams(layoutParams);
                    }
                });
                return animator;
            }
        });

        tallyHolder.basicInfoLayout.setOnLongClickListener((listener) -> {
            final AlertDialog.Builder normalDialog =
                    new AlertDialog.Builder(context);
            normalDialog.setTitle("是否确认删除该记录？");
            normalDialog.setPositiveButton("确定",
                    (dialog, which) -> mPresenter.delete(tally.getId()));
            normalDialog.setNegativeButton("关闭",
                    (dialog, which) -> {
                    });
            normalDialog.show();
            return true;
        });

        tallyHolder.noteLayout.setOnLongClickListener((listener) -> {
            final AlertDialog.Builder normalDialog =
                    new AlertDialog.Builder(context);
            normalDialog.setTitle("是否确认删除该记录？");
            normalDialog.setPositiveButton("确定",
                    (dialog, which) -> mPresenter.delete(tally.getId()));
            normalDialog.setNegativeButton("关闭",
                    (dialog, which) -> {
                    });
            normalDialog.show();
            return true;
        });

    }

    @Override
    public int getItemCount() {
        return tallies.size();
    }

    public void setData(List<Tally> data) {
        this.tallies = data;
    }
}
